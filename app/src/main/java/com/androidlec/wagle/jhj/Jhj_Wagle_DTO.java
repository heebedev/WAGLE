package com.androidlec.wagle.jhj;

public class Jhj_Wagle_DTO {

    String wcSeqno;
    String wcName;
    String wcDueDate;

    String wcStartDate;
    String wcEndDate;
    String wcLocate;
    String wcEntryFee;

    // HomeFragment 에서 사용하는 와글 생성자
    public Jhj_Wagle_DTO(String wcSeqno, String wcName, String wcDueDate) {
        this.wcSeqno = wcSeqno;
        this.wcName = wcName;
        this.wcDueDate = wcDueDate;
    }

    // MyPage 에서 사용하는 와글 생성자
    public Jhj_Wagle_DTO(String wcSeqno, String wcName, String wcStartDate, String wcEndDate, String wcLocate, String wcEntryFee) {
        this.wcSeqno = wcSeqno;
        this.wcName = wcName;
        this.wcStartDate = wcStartDate;
        this.wcEndDate = wcEndDate;
        this.wcLocate = wcLocate;
        this.wcEntryFee = wcEntryFee;
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

    public String getWcDueDate() {
        return wcDueDate;
    }

    public void setWcDueDate(String wcDueDate) {
        this.wcDueDate = wcDueDate;
    }

    public String getWcStartDate() {
        return wcStartDate;
    }

    public void setWcStartDate(String wcStartDate) {
        this.wcStartDate = wcStartDate;
    }

    public String getWcEndDate() {
        return wcEndDate;
    }

    public void setWcEndDate(String wcEndDate) {
        this.wcEndDate = wcEndDate;
    }

    public String getWcLocate() {
        return wcLocate;
    }

    public void setWcLocate(String wcLocate) {
        this.wcLocate = wcLocate;
    }

    public String getWcEntryFee() {
        return wcEntryFee;
    }

    public void setWcEntryFee(String wcEntryFee) {
        this.wcEntryFee = wcEntryFee;
    }
}
