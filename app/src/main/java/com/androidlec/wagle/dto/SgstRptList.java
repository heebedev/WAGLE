package com.androidlec.wagle.dto;

public class SgstRptList {

    int srSeqno;
    String sContent;
    String rContent;

    public SgstRptList(int srSeqno, String sContent, String rContent) {
        this.srSeqno = srSeqno;
        this.sContent = sContent;
        this.rContent = rContent;
    }

    public SgstRptList(int srSeqno, String sContent) {
        this.srSeqno = srSeqno;
        this.sContent = sContent;
    }

    public int getSrSeqno() {
        return srSeqno;
    }

    public void setSrSeqno(int srSeqno) {
        this.srSeqno = srSeqno;
    }

    public String getSContent() {
        return sContent;
    }

    public void setSContent(String sContent) {
        this.sContent = sContent;
    }

    public String getRContent() {
        return rContent;
    }

    public void setRContent(String rContent) {
        this.rContent = rContent;
    }


}
