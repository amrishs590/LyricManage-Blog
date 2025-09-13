package com.app.model;

public class Admin {
    private int id;
    private String username;
    private String password;
    public Admin(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }

    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
}
