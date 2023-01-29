package com.hl.emsystem.model.pojo;

public class Booth {
    private String title;

    private String recuaddress;

    private Integer boothnum;

    private String boothlayout;

    private Integer jobfairplace;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getRecuaddress() {
        return recuaddress;
    }

    public void setRecuaddress(String recuaddress) {
        this.recuaddress = recuaddress == null ? null : recuaddress.trim();
    }

    public Integer getBoothnum() {
        return boothnum;
    }

    public void setBoothnum(Integer boothnum) {
        this.boothnum = boothnum;
    }

    public String getBoothlayout() {
        return boothlayout;
    }

    public void setBoothlayout(String boothlayout) {
        this.boothlayout = boothlayout == null ? null : boothlayout.trim();
    }

    public Integer getJobfairplace() {
        return jobfairplace;
    }

    public void setJobfairplace(Integer jobfairplace) {
        this.jobfairplace = jobfairplace;
    }
}