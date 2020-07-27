package com.androidlec.wagle.JH;

public class Rank {

    private String Name;
    private int waglenum;
    private int muWagleReport;
    private int muWagleScore;

    public Rank(String name, int waglenum, int muWagleReport, int muWagleScore) {
        Name = name;
        this.waglenum = waglenum;
        this.muWagleReport = muWagleReport;
        this.muWagleScore = muWagleScore;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getWaglenum() {
        return waglenum;
    }

    public void setWaglenum(int waglenum) {
        this.waglenum = waglenum;
    }

    public int getMuWagleReport() {
        return muWagleReport;
    }

    public void setMuWagleReport(int muWagleReport) {
        this.muWagleReport = muWagleReport;
    }

    public int getMuWagleScore() {
        return muWagleScore;
    }

    public void setMuWagleScore(int muWagleScore) {
        this.muWagleScore = muWagleScore;
    }

}//----
