package com.app.model;

public class Participant {
    private int id;
    private String username;
    public Participant(int id, String username, String email) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
}
