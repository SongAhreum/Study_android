package com.example.day07;

public class ChatVO {
    private String key;
    private String email;
    private String date;
    private String contents;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "ChatVO{" +
                "key='" + key + '\'' +
                ", email='" + email + '\'' +
                ", date='" + date + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
