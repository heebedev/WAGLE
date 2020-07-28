package com.androidlec.wagle.jhj;

public class Jhj_Gallery_DTO {

    String seqno;
    String imageName;
    String user_uSeqno;

    public Jhj_Gallery_DTO(String seqno, String imageName, String user_uSeqno) {
        this.seqno = seqno;
        this.imageName = imageName;
        this.user_uSeqno = user_uSeqno;
    }

    public String getSeqno() {
        return seqno;
    }

    public void setSeqno(String seqno) {
        this.seqno = seqno;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getUser_uSeqno() {
        return user_uSeqno;
    }

    public void setUser_uSeqno(String user_uSeqno) {
        this.user_uSeqno = user_uSeqno;
    }
}