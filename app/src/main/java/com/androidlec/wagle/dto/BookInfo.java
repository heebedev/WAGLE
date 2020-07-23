package com.androidlec.wagle.dto;

public class BookInfo {

    String title;
    String writer;
    int maxpage;
    String intro;
    String data;
    String imgName;

    public BookInfo(String title, String writer, int maxpage, String intro, String data, String imgName) {
        this.title = title;
        this.writer = writer;
        this.maxpage = maxpage;
        this.intro = intro;
        this.data = data;
        this.imgName = imgName;
    }

    public BookInfo() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getMaxpage() {
        return maxpage;
    }

    public void setMaxpage(int maxpage) {
        this.maxpage = maxpage;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}
