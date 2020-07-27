package com.androidlec.wagle.jhj;

public class Jhj_Suggestion_DTO {

    String sSeqno;
    String sContent;
    String wcSeqno;
    String sType;

    public Jhj_Suggestion_DTO(String sSeqno, String sContent, String wcSeqno) {
        this.sSeqno = sSeqno;
        this.sContent = sContent;
        this.wcSeqno = wcSeqno;
    }

    public Jhj_Suggestion_DTO(String sSeqno, String wcSeqno, String sType, String sContent) {
        this.sSeqno = sSeqno;
        this.sContent = sContent;
        this.wcSeqno = wcSeqno;
        this.sType = sType;
    }

    public String getsSeqno() {
        return sSeqno;
    }

    public void setsSeqno(String sSeqno) {
        this.sSeqno = sSeqno;
    }

    public String getsContent() {
        return sContent;
    }

    public void setsContent(String sContent) {
        this.sContent = sContent;
    }

    public String getWcSeqno() {
        return wcSeqno;
    }

    public void setWcSeqno(String wcSeqno) {
        this.wcSeqno = wcSeqno;
    }
}
