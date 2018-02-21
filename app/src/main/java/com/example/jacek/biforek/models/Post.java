package com.example.jacek.biforek.models;

import com.example.jacek.biforek.models.User;

import java.io.Serializable;

/**
 * Created by Jacek on 2018-02-07.
 */

public class Post implements Serializable {
    private User UName, USurname, user;
    private String postText, Name, Surname, Where, When, Which, Alko, Club;
    private String postId;
    private long numLikes;
    private long numComments;
    private long timeCreated;

    public Post() {
    }

    public Post(User user, String postText, String Name, String Surname, String Where, String When, String Which, String Alko, String Club, String postId, long numLikes, long numComments, long timeCreated) {

        this.UName = UName;
        this.USurname = USurname;
        this.user = user;
        this.postText = postText;
        this.Name = Name;
        this.Surname = Surname;
        this.Where = Where;
        this.When = When;
        this.Which = Which;
        this.Alko = Alko;
        this.Club = Club;
        this.postId = postId;
        this.numLikes = numLikes;
        this.numComments = numComments;
        this.timeCreated = timeCreated;
    }





    public User getUser() {

        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void setUSurname(User USurname) {
        this.USurname = USurname;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getNameText() {
        return Name;
    }

    public void setNameText(String Name) {
        this.Name = Name;
    }

    public String getSurnameText() {
        return Surname;
    }

    public void setSurnameText(String Surname) {
        this.Surname = Surname;
    }

    public String getWhereText() {
        return Where;
    }

    public void setWhereText(String Where) {
        this.Where = Where;
    }

    public String getWhenText() {
        return When;
    }

    public void setWhenText(String When) {
        this.When = When;
    }

    public String getWhichText() {
        return Which;
    }

    public void setWhichText(String Which) {
        this.Which = Which;
    }

    public String getAlkoText() {
        return Alko;
    }

    public void setAlkoText(String Alko) {
        this.Alko = Alko;
    }

    public String getClubText() {
        return Club;
    }

    public void setClubText(String Club) {
        this.Club = Club;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public long getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(long numLikes) {
        this.numLikes = numLikes;
    }

    public long getNumComments() {
        return numComments;
    }

    public void setNumComments(long numComments) {
        this.numComments = numComments;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }
}