package com.androidlec.wagle.jhj;

public class Jhj_BookReport_DTO {

    String brSeqno;
    String uSeqno;
    String brContent;
    String wcSeqno;

    public Jhj_BookReport_DTO(String brSeqno, String uSeqno, String brContent, String wcSeqno) {
        this.brSeqno = brSeqno;
        this.uSeqno = uSeqno;
        this.brContent = brContent;
        this.wcSeqno = wcSeqno;
    }

    public String getBrSeqno() {
        return brSeqno;
    }

    public void setBrSeqno(String brSeqno) {
        this.brSeqno = brSeqno;
    }

    public String getuSeqno() {
        return uSeqno;
    }

    public void setuSeqno(String uSeqno) {
        this.uSeqno = uSeqno;
    }

    public String getBrContent() {
        return brContent;
    }

    public void setBrContent(String brContent) {
        this.brContent = brContent;
    }

    public String getWcSeqno() {
        return wcSeqno;
    }

    public void setWcSeqno(String wcSeqno) {
        this.wcSeqno = wcSeqno;
    }
}
