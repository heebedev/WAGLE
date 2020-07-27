package com.androidlec.wagle.CS.Model;

import java.util.ArrayList;

public class BoardList {
    private String bSeqno, Post_pSeqno, pcTitle, pcContent;

    public BoardList(String bSeqno, String post_pSeqno, String pcTitle, String pcContent) {
        this.bSeqno = bSeqno;
        Post_pSeqno = post_pSeqno;
        this.pcTitle = pcTitle;
        this.pcContent = pcContent;
    }

    public String getbSeqno() {
        return bSeqno;
    }

    public void setbSeqno(String bSeqno) {
        this.bSeqno = bSeqno;
    }

    public String getPost_pSeqno() {
        return Post_pSeqno;
    }

    public void setPost_pSeqno(String post_pSeqno) {
        Post_pSeqno = post_pSeqno;
    }

    public String getPcTitle() {
        return pcTitle;
    }

    public void setPcTitle(String pcTitle) {
        this.pcTitle = pcTitle;
    }

    public String getPcContent() {
        return pcContent;
    }

    public void setPcContent(String pcContent) {
        this.pcContent = pcContent;
    }
}
