package com.app.model;

import java.sql.Timestamp;

public class Comment {
    private int commentId;
    private int lyricId;
    private String username;
    private String commentText;
    private Timestamp createdAt;

    public Comment(int commentId, int lyricId, String username, String commentText, Timestamp createdAt) {
        this.commentId = commentId;
        this.lyricId = lyricId;
        this.username = username;
        this.commentText = commentText;
        this.createdAt = createdAt;
    }

    public int getCommentId() { return commentId; }
    public int getLyricId() { return lyricId; }
    public String getUsername() { return username; }
    public String getCommentText() { return commentText; }
    public Timestamp getCreatedAt() { return createdAt; }
}
