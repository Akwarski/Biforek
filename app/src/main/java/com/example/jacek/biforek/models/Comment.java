package com.example.jacek.biforek.models;

import com.example.jacek.biforek.models.User;

import java.io.Serializable;

/**
 * Created by Jacek on 2018-02-07.
 */

public class Comment implements Serializable {
    private User UName, USurname, user;
    private String commentId;
    private long timeCreated;
    private String comment;

    public Comment() {
    }

    public Comment(User UName, User USurname, String commentId, long timeCreated, String comment) {

        this.UName = UName;
        this.USurname = USurname;
        this.user = user;
        this.commentId = commentId;
        this.timeCreated = timeCreated;
        this.comment = comment;
    }







    public User getUName() {

        return UName;
    }

    public void setUName(User UName) {
        this.UName = UName;
    }

    public User getUSurname() {

        return USurname;
    }







    public void setUser(User user) {
        this.user = user;
    }

    public User getUser(){
        return user;
    }







    public void setUSurname(User USurname) {
        this.USurname = USurname;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}