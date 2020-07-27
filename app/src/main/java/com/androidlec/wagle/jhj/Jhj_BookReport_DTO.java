package com.androidlec.wagle.jhj;

public class Jhj_BookReport_DTO {

    String brSeqno;
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
