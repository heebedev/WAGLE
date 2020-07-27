package com.androidlec.wagle.jhj;

public class Jhj_BookReport_DTO {

    String brSeqno;
    String uSeqno;
    String brContent;
    String sSeqno;
    String sType;
    String sContent;
    String wcSeqno;
    String wcName;
    String uName;

    // 독서감상문 에서 사용하는 DTO
    public Jhj_BookReport_DTO(String brSeqno, String wcSeqno, String wcName, String uName) {
        this.brSeqno = brSeqno;
        this.wcSeqno = wcSeqno;
        this.wcName = wcName;
        this.uName = uName;
    }

    public Jhj_BookReport_DTO(String brSeqno, String uSeqno, String brContent, String sSeqno, String wcSeqno, String sType, String sContent) {
        this.brSeqno = brSeqno;
        this.uSeqno = uSeqno;
        this.brContent = brContent;
        this.sSeqno = sSeqno;
        this.sType = sType;
        this.sContent = sContent;
        this.wcSeqno = wcSeqno;
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

    public String getsSeqno() {
        return sSeqno;
    }

    public void setsSeqno(String sSeqno) {
        this.sSeqno = sSeqno;
    }

    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }

    public String getsContent() {
        return sContent;
    }

    public void setsContent(String sContent) {
        this.sContent = sContent;
    }

    public String getBrSeqno() {
        return brSeqno;
    }

    public void setBrSeqno(String brSeqno) {
        this.brSeqno = brSeqno;
    }

    public String getWcSeqno() {
        return wcSeqno;
    }

    public void setWcSeqno(String wcSeqno) {
        this.wcSeqno = wcSeqno;
    }

    public String getWcName() {
        return wcName;
    }

    public void setWcName(String wcName) {
        this.wcName = wcName;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }
}
