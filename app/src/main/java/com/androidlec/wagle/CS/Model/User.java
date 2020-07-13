package com.androidlec.wagle.CS.Model;

import com.androidlec.wagle.CS.LoginClass.LoginInfo;

public class User {

    private String uId, uEmail, uName, uImageName, uBirthDate, uLoginType;

    public User(String uId, String uEmail, String uName, String uImageName, String uBirthDate, String uLoginType) {
        this.uId = uId;
        this.uEmail = uEmail;
        this.uName = uName;
        this.uImageName = uImageName;
        this.uBirthDate = uBirthDate;
        this.uLoginType = uLoginType;
    }

    public User(String uId, String uEmail, String uName, String uImageName, String uBirthDate) {
        this.uId = uId;
        this.uEmail = uEmail;
        this.uName = uName;
        this.uImageName = uImageName;
        this.uBirthDate = uBirthDate;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuImageName() {
        return uImageName;
    }

    public void setuImageName(String uImageName) {
        this.uImageName = uImageName;
    }

    public String getuBirthDate() {
        return uBirthDate;
    }

    public void setuBirthDate(String uBirthDate) {
        this.uBirthDate = uBirthDate;
    }

    public String getuLoginType() {
        return uLoginType;
    }

    public void setuLoginType(String uLoginType) {
        this.uLoginType = uLoginType;
    }
}
