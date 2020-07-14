package com.androidlec.wagle.CS.Model;

public class WagleList {

    private String wcSeqno, Moim_wmSeqno, MoimUser_muSeqno, WagleBook_wbSeqno, wcName, wcType, wcStartDate, wcEndDate, wcDueDate, wcLocate, wcEntryFee, wcWagleDetail, wcWagleAgreeRefund;

    public WagleList(String wcSeqno, String moim_wmSeqno, String moimUser_muSeqno, String wagleBook_wbSeqno, String wcName, String wcType, String wcStartDate, String wcEndDate, String wcDueDate, String wcLocate, String wcEntryFee, String wcWagleDetail, String wcWagleAgreeRefund) {
        this.wcSeqno = wcSeqno;
        Moim_wmSeqno = moim_wmSeqno;
        MoimUser_muSeqno = moimUser_muSeqno;
        WagleBook_wbSeqno = wagleBook_wbSeqno;
        this.wcName = wcName;
        this.wcType = wcType;
        this.wcStartDate = wcStartDate;
        this.wcEndDate = wcEndDate;
        this.wcDueDate = wcDueDate;
        this.wcLocate = wcLocate;
        this.wcEntryFee = wcEntryFee;
        this.wcWagleDetail = wcWagleDetail;
        this.wcWagleAgreeRefund = wcWagleAgreeRefund;
    }

    public String getWcSeqno() {
        return wcSeqno;
    }

    public void setWcSeqno(String wcSeqno) {
        this.wcSeqno = wcSeqno;
    }

    public String getMoim_wmSeqno() {
        return Moim_wmSeqno;
    }

    public void setMoim_wmSeqno(String moim_wmSeqno) {
        Moim_wmSeqno = moim_wmSeqno;
    }

    public String getMoimUser_muSeqno() {
        return MoimUser_muSeqno;
    }

    public void setMoimUser_muSeqno(String moimUser_muSeqno) {
        MoimUser_muSeqno = moimUser_muSeqno;
    }

    public String getWagleBook_wbSeqno() {
        return WagleBook_wbSeqno;
    }

    public void setWagleBook_wbSeqno(String wagleBook_wbSeqno) {
        WagleBook_wbSeqno = wagleBook_wbSeqno;
    }

    public String getWcName() {
        return wcName;
    }

    public void setWcName(String wcName) {
        this.wcName = wcName;
    }

    public String getWcType() {
        return wcType;
    }

    public void setWcType(String wcType) {
        this.wcType = wcType;
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

    public String getWcDueDate() {
        return wcDueDate;
    }

    public void setWcDueDate(String wcDueDate) {
        this.wcDueDate = wcDueDate;
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

    public String getWcWagleDetail() {
        return wcWagleDetail;
    }

    public void setWcWagleDetail(String wcWagleDetail) {
        this.wcWagleDetail = wcWagleDetail;
    }

    public String getWcWagleAgreeRefund() {
        return wcWagleAgreeRefund;
    }

    public void setWcWagleAgreeRefund(String wcWagleAgreeRefund) {
        this.wcWagleAgreeRefund = wcWagleAgreeRefund;
    }
}
