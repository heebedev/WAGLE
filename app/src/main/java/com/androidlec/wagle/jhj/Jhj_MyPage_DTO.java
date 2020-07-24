package com.androidlec.wagle.jhj;

import com.androidlec.wagle.CS.Model.WagleList;

import java.util.ArrayList;

public class Jhj_MyPage_DTO {

    String wagleNum;
    String wagleBookReportNum;
    String wagleScore;

    String totalWagle;
    String totalBookReport;

    ArrayList<WagleList> wagle;
    ArrayList<Jhj_Suggestion_DTO> suggestion;

    public Jhj_MyPage_DTO(String wagleNum, String wagleBookReportNum, String wagleScore, String totalWagle, String totalBookReport, ArrayList<WagleList> wagle, ArrayList<Jhj_Suggestion_DTO> suggestion) {
        this.wagleNum = wagleNum;
        this.wagleBookReportNum = wagleBookReportNum;
        this.wagleScore = wagleScore;
        this.totalWagle = totalWagle;
        this.totalBookReport = totalBookReport;
        this.wagle = wagle;
        this.suggestion = suggestion;
    }

    public String getWagleNum() {
        return wagleNum;
    }

    public void setWagleNum(String wagleNum) {
        this.wagleNum = wagleNum;
    }

    public String getWagleBookReportNum() {
        return wagleBookReportNum;
    }

    public void setWagleBookReportNum(String wagleBookReportNum) {
        this.wagleBookReportNum = wagleBookReportNum;
    }

    public String getWagleScore() {
        return wagleScore;
    }

    public void setWagleScore(String wagleScore) {
        this.wagleScore = wagleScore;
    }

    public String getTotalWagle() {
        return totalWagle;
    }

    public void setTotalWagle(String totalWagle) {
        this.totalWagle = totalWagle;
    }

    public String getTotalBookReport() {
        return totalBookReport;
    }

    public void setTotalBookReport(String totalBookReport) {
        this.totalBookReport = totalBookReport;
    }

    public ArrayList<Jhj_Suggestion_DTO> getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(ArrayList<Jhj_Suggestion_DTO> suggestion) {
        this.suggestion = suggestion;
    }

    public ArrayList<WagleList> getWagle() {
        return wagle;
    }

    public void setWagle(ArrayList<WagleList> wagle) {
        this.wagle = wagle;
    }
}
