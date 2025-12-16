package com.abhicom.userservice.model;

import java.time.Instant;

public class UserDocument {
    private String docId;
    private String userId;
    private String originalFileName;
    private String contentType;
    private long size;
    private String storedPath;
    private Instant uploadedAt;

    public UserDocument() {}

    public UserDocument(String docId, String userId, String originalFileName, String contentType,
                        long size, String storedPath, Instant uploadedAt) {
        this.docId = docId;
        this.userId = userId;
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.size = size;
        this.storedPath = storedPath;
        this.uploadedAt = uploadedAt;
    }

    public String getDocId() { return docId; }
    public String getUserId() { return userId; }
    public String getOriginalFileName() { return originalFileName; }
    public String getContentType() { return contentType; }
    public long getSize() { return size; }
    public String getStoredPath() { return storedPath; }
    public Instant getUploadedAt() { return uploadedAt; }
}
