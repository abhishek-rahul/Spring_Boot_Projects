package com.abhicom.userservice.service;

import com.abhicom.userservice.config.AppProperties;
import com.abhicom.userservice.exception.BadRequestException;
import com.abhicom.userservice.exception.NotFoundException;
import com.abhicom.userservice.model.UserDocument;
import com.abhicom.userservice.repository.UserDocumentRepository;
import com.abhicom.userservice.repository.UserRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UserDocumentService {

    private final UserRepository userRepo;
    private final UserDocumentRepository docRepo;
    private final AppProperties props;

    public UserDocumentService(UserRepository userRepo, UserDocumentRepository docRepo, AppProperties props) {
        this.userRepo = userRepo;
        this.docRepo = docRepo;
        this.props = props;
    }

    public UserDocument upload(String userId, MultipartFile file) {
        // 1) user must exist
        long useridlong = Long.parseLong(userId);
        userRepo.findById(useridlong).orElseThrow(() -> new NotFoundException("user not found: " + userId));

        // 2) file must exist
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("file is required");
        }

        // 3) (optional but good) allow only specific content types
        // You can relax this later.
        String ct = file.getContentType();
        if (ct == null || !(ct.startsWith("image/") || ct.equals("application/pdf") || ct.equals("text/plain"))) {
            throw new BadRequestException("unsupported file type: " + ct);
        }

        try {
            String docId = UUID.randomUUID().toString();

            Path baseDir = Paths.get(props.getUploadDir(), userId);
            Files.createDirectories(baseDir);

            String safeOriginal = safeName(file.getOriginalFilename());
            Path storedPath = baseDir.resolve(docId + "_" + safeOriginal);

            // copy stream to disk (no buffering whole file in memory)
            Files.copy(file.getInputStream(), storedPath, StandardCopyOption.REPLACE_EXISTING);

            UserDocument meta = new UserDocument(
                    docId,
                    userId,
                    file.getOriginalFilename(),
                    ct,
                    file.getSize(),
                    storedPath.toString(),
                    Instant.now()
            );

            return docRepo.save(meta);
        } catch (IOException e) {
            throw new RuntimeException("upload failed", e);
        }
    }

    public List<UserDocument> list(String userId) {
        long useridlong = Long.parseLong(userId);
        userRepo.findById(useridlong).orElseThrow(() -> new NotFoundException("user not found: " + userId));
        return docRepo.findByUserId(userId);
    }

    public UserDocument getMeta(String userId, String docId) {
        return docRepo.findByDocId(docId)
                .filter(d -> d.getUserId().equals(userId))
                .orElseThrow(() -> new NotFoundException("document not found"));
    }

    public Resource getFileResource(String userId, String docId) {
        UserDocument meta = getMeta(userId, docId);

        Path p = Paths.get(meta.getStoredPath());
        if (!Files.exists(p)) throw new NotFoundException("stored file missing on disk");

        return new FileSystemResource(p);
    }

    private String safeName(String name) {
        if (name == null || name.isBlank()) return "file";
        return name.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
