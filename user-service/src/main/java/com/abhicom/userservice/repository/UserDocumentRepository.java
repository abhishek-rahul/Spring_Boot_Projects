package com.abhicom.userservice.repository;

import com.abhicom.userservice.model.UserDocument;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserDocumentRepository {

    // key = docId
    private final Map<String, UserDocument> docs = new ConcurrentHashMap<>();

    public UserDocument save(UserDocument doc) {
        docs.put(doc.getDocId(), doc);
        return doc;
    }

    public Optional<UserDocument> findByDocId(String docId) {
        return Optional.ofNullable(docs.get(docId));
    }

    public List<UserDocument> findByUserId(String userId) {
        return docs.values().stream()
                .filter(d -> d.getUserId().equals(userId))
                .sorted(Comparator.comparing(UserDocument::getUploadedAt).reversed())
                .toList();
    }
}
