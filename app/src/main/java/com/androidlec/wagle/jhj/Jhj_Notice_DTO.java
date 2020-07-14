package com.androidlec.wagle.jhj;

public class Jhj_Notice_DTO {

    String noticeSeqno;
    String noticeTitle;
    String noticeContent;
    String postUserSeqno;

    public Jhj_Notice_DTO(String noticeSeqno, String noticeTitle, String noticeContent, String postUserSeqno) {
        this.noticeSeqno = noticeSeqno;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.postUserSeqno = postUserSeqno;
    }

    public String getNoticeSeqno() {
        return noticeSeqno;
    }

    public void setNoticeSeqno(String noticeSeqno) {
        this.noticeSeqno = noticeSeqno;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getPostUserSeqno() {
        return postUserSeqno;
    }

    public void setPostUserSeqno(String postUserSeqno) {
        this.postUserSeqno = postUserSeqno;
    }
}
