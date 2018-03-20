package com.example.webservicetest;

import java.io.Serializable;

/**
 * Created by JackR on 2018/3/15.
 */

public class UserModel implements Serializable {
    public int userId;
    public String userName;
    public String telPhone;
    public String password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
