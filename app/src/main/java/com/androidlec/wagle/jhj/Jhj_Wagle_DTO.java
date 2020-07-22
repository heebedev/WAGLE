package com.androidlec.wagle.jhj;

public class Jhj_Wagle_DTO {

    String wcSeqno;
    String wcName;
    String wcDueDate;

    public Jhj_Wagle_DTO(String wcSeqno, String wcName, String wcDueDate) {
        this.wcSeqno = wcSeqno;
        this.wcName = wcName;
        this.wcDueDate = wcDueDate;
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
}
