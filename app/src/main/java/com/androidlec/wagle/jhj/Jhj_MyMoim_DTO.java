package com.androidlec.wagle.jhj;

public class Jhj_MyMoim_DTO {

    String maSeqno;
    String maGrade;
    String muSeqno;
    String uName;
    String uEmail;
    String uImageName;

    public Jhj_MyMoim_DTO(String maSeqno, String maGrade, String muSeqno, String uName, String uEmail, String uImageName) {
        this.maSeqno = maSeqno;
        this.maGrade = maGrade;
        this.muSeqno = muSeqno;
        this.uName = uName;
        this.uEmail = uEmail;
        this.uImageName = uImageName;
    }

    public String getMaSeqno() {
        return maSeqno;
    }

    public void setMaSeqno(String maSeqno) {
        this.maSeqno = maSeqno;
    }

    public String getMaGrade() {
        return maGrade;
    }

    public void setMaGrade(String maGrade) {
        this.maGrade = maGrade;
    }

    public String getMuSeqno() {
        return muSeqno;
    }

    public void setMuSeqno(String muSeqno) {
        this.muSeqno = muSeqno;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuImageName() {
        return uImageName;
    }

    public void setuImageName(String uImageName) {
        this.uImageName = uImageName;
    }
}
