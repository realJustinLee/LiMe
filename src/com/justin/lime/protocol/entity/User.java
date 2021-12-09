package com.justin.lime.protocol.entity;

/**
 * @author Justin Lee
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String gender;
    private String email;
    private boolean banned;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String gender, String email) {
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.email = email;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public boolean isBanned() {
        return banned;
    }
}
