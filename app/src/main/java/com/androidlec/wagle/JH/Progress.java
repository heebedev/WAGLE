package com.androidlec.wagle.JH;

public class Progress {

    private String uImageName;
    private int wpReadPage;
    private int uSeqno;
    private String uLoginType;
    private int wpSeqno;


    public Progress(String uImageName, int wpReadPage, int uSeqno, String uLoginType, int wpSeqno) {
        this.uImageName = uImageName;
        this.wpReadPage = wpReadPage;
        this.uSeqno = uSeqno;
        this.uLoginType = uLoginType;
        this.wpSeqno = wpSeqno;
    }

    public int getWpSeqno() {
        return wpSeqno;
    }

    public void setWpSeqno(int wpSeqno) {
        this.wpSeqno = wpSeqno;
    }

    public String getuImageName() {
        return uImageName;
    }

    public void setuImageName(String uImageName) {
        this.uImageName = uImageName;
    }

    public int getWpReadPage() {
        return wpReadPage;
    }

    public void setWpReadPage(int wpReadPage) {
        this.wpReadPage = wpReadPage;
    }

    public int getuSeqno() {
        return uSeqno;
    }

    public void setuSeqno(int uSeqno) {
        this.uSeqno = uSeqno;
    }

    public String getuLoginType() {
        return uLoginType;
    }

    public void setuLoginType(String uLoginType) {
        this.uLoginType = uLoginType;
    }
}//----
