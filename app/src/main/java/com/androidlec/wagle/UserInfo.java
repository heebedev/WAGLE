package com.androidlec.wagle;

public class UserInfo {

    public static int uSeqno;
    public static String uId;
    public static String uEmail;
    public static String uName;
    public static String uLoginType;
    public static String CONNECTED_MOIM_SEQ = "3";

    public UserInfo() {
    }

    public static String getuEmail() {
        return uEmail;
    }

    public static void setuEmail(String uEmail) {
        UserInfo.uEmail = uEmail;
    }

    public static String getuId() {
        return uId;
    }

    public static void setuId(String uId) {
        UserInfo.uId = uId;
    }

    public static int getuSeqno() {
        return uSeqno;
    }

    public static void setuSeqno(int uSeqno) {
        UserInfo.uSeqno = uSeqno;
    }

    public static String getuName() {
        return uName;
    }

    public static void setuName(String uName) {
        UserInfo.uName = uName;
    }

    public static String getuLoginType() {
        return uLoginType;
    }

    public static void setuLoginType(String uLoginType) {
        UserInfo.uLoginType = uLoginType;
    }
}
