package com.example.jacek.biforek.models;

import java.io.Serializable;

/**
 * Created by Jacek on 2018-02-07.
 */

public class User implements Serializable {
    private String UName, USurname, user;
    private String email;
    private String uid;
    private String temp;

    public User() {
    }

    public User(String user, String email, String uid) {

        this.UName = UName;
        this.USurname = USurname;
        this.user = user;
        this.email = email;
        this.uid = uid;
    }









    public String getUser() {

        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public void setNameAndSurname(String UName, String USurname){
        this.UName = UName;
        this.USurname = USurname;
    }

    public String getNameAndSurname(){
        this.temp = UName + " " + USurname;

        return temp;
    }

    public void setUSurname(String USurname) {
        this.USurname = USurname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}