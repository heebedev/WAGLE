package com.androidlec.wagle.dto;

public class Moimlist {

    int moimseq;
    String moimName;

    public Moimlist(int moimseq, String moimName) {
        this.moimseq = moimseq;
        this.moimName = moimName;
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
