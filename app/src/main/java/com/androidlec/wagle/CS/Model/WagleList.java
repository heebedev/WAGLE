package com.androidlec.wagle.CS.Model;

public class WagleList {
    private String title, date, location, fee;

    public WagleList(String title, String date, String location, String fee) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.fee = fee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
