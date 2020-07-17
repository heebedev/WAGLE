package com.androidlec.wagle.CS.Model;

public class User {

    private String uSeqno, uId, uEmail, uName, uImageName, uBirthDate, uLoginType, uPassword, uDate;

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

    public User(String uSeqno, String uId, String uEmail, String uName, String uImageName, String uBirthDate, String uLoginType, String uPassword, String uDate) {
        this.uSeqno = uSeqno;
        this.uId = uId;
        this.uEmail = uEmail;
        this.uName = uName;
        this.uImageName = uImageName;
        this.uBirthDate = uBirthDate;
        this.uLoginType = uLoginType;
        this.uPassword = uPassword;
        this.uDate = uDate;
    }

    public String getuSeqno() {
        return uSeqno;
    }

    public void setuSeqno(String uSeqno) {
        this.uSeqno = uSeqno;
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

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuDate() {
        return uDate;
    }

    public void setuDate(String uDate) {
        this.uDate = uDate;
    }
}
