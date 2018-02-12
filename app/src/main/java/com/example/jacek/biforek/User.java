package com.example.jacek.biforek;

import java.io.Serializable;

/**
 * Created by Jacek on 2018-02-07.
 */

public class User implements Serializable {
    private String UName, USurname;
    private String email;
    private String uid;

    public User() {
    }

    public User(String UName, String USurname, String email, String uid) {

        this.UName = UName;
        this.USurname = USurname;
        this.email = email;
        this.uid = uid;
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