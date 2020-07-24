package com.androidlec.wagle.dto;

public class MoimList {

    int moimseq;
    String moimName;
    String moimSubject;

    public MoimList(int moimseq, String moimName, String moimSubject) {
        this.moimseq = moimseq;
        this.moimName = moimName;
        this.moimSubject = moimSubject;
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
}
