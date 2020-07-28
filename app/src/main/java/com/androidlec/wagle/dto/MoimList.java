package com.androidlec.wagle.dto;

public class MoimList {

    int moimseq;
    String moimName;
    String moimSubject;
    String muSeqno;

    public MoimList(int moimseq, String moimName, String moimSubject, String muSeqno) {
        this.moimseq = moimseq;
        this.moimName = moimName;
        this.moimSubject = moimSubject;
        this.muSeqno = muSeqno;
    }

    public String getMoimSubject() {
        return moimSubject;
    }

    public void setMoimSubject(String moimSubject) {
        this.moimSubject = moimSubject;
    }

    public int getMoimseq() {
        return moimseq;
    }

    public void setMoimseq(int moimseq) {
        this.moimseq = moimseq;
    }

    public String getMoimName() {
        return moimName;
    }

    public void setMoimName(String moimName) {
        this.moimName = moimName;
    }

    public String getMuSeqno() {
        return muSeqno;
    }

    public void setMuSeqno(String muSeqno) {
        this.muSeqno = muSeqno;
    }
}
