package com.laowang.tokenbrowserbackend.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class TokenUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username = null;
    private String password = null;
    private String level = null;
    private Date date = null;

    @Override
    public String toString() {
        return "TokenUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", level='" + level + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenUser)) return false;
        TokenUser tokenUser = (TokenUser) o;
        return Objects.equals(getUsername(), tokenUser.getUsername()) && Objects.equals(getPassword(), tokenUser.getPassword()) && Objects.equals(getLevel(), tokenUser.getLevel()) && Objects.equals(getDate(), tokenUser.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getLevel(), getDate());
    }

    public TokenUser() {
    }

    public TokenUser(String username, String password, String level, Date date) {
        this.username = username;
        this.password = password;
        this.level = level;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
