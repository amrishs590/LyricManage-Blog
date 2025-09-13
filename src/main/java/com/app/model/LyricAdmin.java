package com.app.model;

import java.sql.Timestamp;

public class LyricAdmin {
    private int lyricId;
    private String lyricText;
    private String username;
    private Timestamp createdAt;
    private boolean isHidden;
    private boolean isOveride;

    public LyricAdmin(int lyricId, String lyricText, String username, Timestamp createdAt, boolean isHidden,boolean isOveride) {
        this.lyricId = lyricId;
        this.lyricText = lyricText;
        this.username = username;
        this.createdAt = createdAt;
        this.isHidden = isHidden; 
        this.isOveride = isOveride;
    }

    public int getLyricId() { return lyricId; }
    public String getLyricText() { return lyricText; }
    public String getUsername() { return username; }
    public Timestamp getCreatedAt() { return createdAt; }
    public boolean isHidden() { return isHidden; }

    public void setHidden(boolean hidden) { this.isHidden = hidden; }
    public boolean isOveride() { return isOveride; }
}
