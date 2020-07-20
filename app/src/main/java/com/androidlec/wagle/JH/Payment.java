package com.androidlec.wagle.JH;

public class Payment {

    private int wpSeqno;
    private String item;
    private int price;

    public Payment(int wpSeqno, String item, int price) {
        this.wpSeqno = wpSeqno;
        this.item = item;
        this.price = price;
    }


    public int getWpSeqno() {
        return wpSeqno;
    }

    public void setWpSeqno(int wpSeqno) {
        this.wpSeqno = wpSeqno;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}//----
