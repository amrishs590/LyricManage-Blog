package com.app.model;

import java.sql.Timestamp;
import java.util.List;

public class Lyric {
    private int lyricId;
    private String lyricText;
    private String username;
    private Timestamp createdAt;
    private int upvotes;
    private int downvotes;
    private int commentCount;
    private List<Comment> comments;
    private String genre;
    private String mood;


    public Lyric(int lyricId, String lyricText, String username, Timestamp createdAt,
                 int upvotes, int downvotes, int commentCount, List<Comment> comments, String genre, String mood) {
        this.lyricId = lyricId;
        this.lyricText = lyricText;
        this.username = username;
        this.createdAt = createdAt;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.commentCount = commentCount;
        this.comments = comments;
        this.genre = genre;
        this.mood = mood;
    }

    public int getLyricId() { return lyricId; }
    public String getLyricText() { return lyricText; }
    public String getUsername() { return username; }
    public Timestamp getCreatedAt() { return createdAt; }
    public int getUpvotes() { return upvotes; }
    public int getDownvotes() { return downvotes; }
    public int getCommentCount() { return commentCount; }
    public List<Comment> getComments() { return comments; }
    public String getGenre() { return genre; }
    public String getMood() { return mood; }

}
