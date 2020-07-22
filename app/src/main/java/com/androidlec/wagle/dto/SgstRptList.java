package com.androidlec.wagle.dto;

public class SgstRptList {

    int sSeqno;
    String wcSeqno;
    String sType;
    String sContent;
    String aContent;

    public SgstRptList(int sSeqno, String wcSeqno, String sType, String sContent) {
        this.sSeqno = sSeqno;
        this.wcSeqno = wcSeqno;
        this.sType = sType;
        this.sContent = sContent;
    }

    public int getsSeqno() {
        return sSeqno;
    }

    public void setsSeqno(int sSeqno) {
        this.sSeqno = sSeqno;
    }

    public String getWcSeqno() {
        return wcSeqno;
    }

    public void setWcSeqno(String wcSeqno) {
        this.wcSeqno = wcSeqno;
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

    public String getaContent() {
        return aContent;
    }

    public void setaContent(String aContent) {
        this.aContent = aContent;
    }
}
