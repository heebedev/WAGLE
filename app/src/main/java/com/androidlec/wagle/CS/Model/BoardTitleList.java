package com.androidlec.wagle.CS.Model;

public class BoardTitleList {
    private String bSeqno, bName, bDate, bOrder;

    public BoardTitleList(String bSeqno, String bName, String bDate, String bOrder) {
        this.bSeqno = bSeqno;
        this.bName = bName;
        this.bDate = bDate;
        this.bOrder = bOrder;
    }

    public String getbSeqno() {
        return bSeqno;
    }

    public void setbSeqno(String bSeqno) {
        this.bSeqno = bSeqno;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }

    public String getbOrder() {
        return bOrder;
    }

    public void setbOrder(String bOrder) {
        this.bOrder = bOrder;
    }
}