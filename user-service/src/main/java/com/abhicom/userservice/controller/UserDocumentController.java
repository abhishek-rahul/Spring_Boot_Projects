package com.abhicom.userservice.controller;

import com.abhicom.userservice.model.UserDocument;
import com.abhicom.userservice.service.UserDocumentService;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/documents")
public class UserDocumentController {

    private final UserDocumentService service;

    public UserDocumentController(UserDocumentService service) {
        this.service = service;
    }

    // POST /api/users/{userId}/documents  (multipart upload)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserDocument> upload(
            @PathVariable String userId,
            @RequestPart("file") MultipartFile file
    ) {
        UserDocument saved = service.upload(userId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // GET /api/users/{userId}/documents  (list metadata)
    @GetMapping
    public ResponseEntity<List<UserDocument>> list(@PathVariable String userId) {
        return ResponseEntity.ok(service.list(userId));
    }

    // GET /api/users/{userId}/documents/{docId}  (stream download)
    @GetMapping("/{docId}")
    public ResponseEntity<Resource> download(@PathVariable String userId, @PathVariable String docId) {
        UserDocument meta = service.getMeta(userId, docId);
        Resource file = service.getFileResource(userId, docId);

        MediaType mt = (meta.getContentType() != null)
                ? MediaType.parseMediaType(meta.getContentType())
                : MediaType.APPLICATION_OCTET_STREAM;

        return ResponseEntity.ok()
                .contentType(mt)
                .contentLength(meta.getSize())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + meta.getOriginalFileName() + "\"")
                .body(file);
    }
}
