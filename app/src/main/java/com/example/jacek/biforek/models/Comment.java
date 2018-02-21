package com.example.jacek.biforek.models;

import com.example.jacek.biforek.models.User;

import java.io.Serializable;

/**
 * Created by Jacek on 2018-02-07.
 */

public class Comment implements Serializable {
    private User user;
    private String commentId, USurname, UName;
    private long timeCreated;
    private String comment;

    public Comment() {
    }

    public Comment(User user, String UName, String USurname, String commentId, long timeCreated, String comment) {

        this.UName = UName;
        this.USurname = USurname;
        this.user = user;
        this.commentId = commentId;
        this.timeCreated = timeCreated;
        this.comment = comment;
    }





    public String getUName() {

        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }




    public String getUSurname() {

        return USurname;
    }

    public void setUSurname(String USurname) {
        this.USurname = USurname;
    }




    public User getUser(){
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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