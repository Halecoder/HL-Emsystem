package com.hl.emsystem.model.pojo;

import java.util.Date;

public class Recruit {
    private String title;

    private String firmname;

    private String publisher;

    private String feeds;

    private Date recutime;

    private String recuaddress;

    private String recucontent;

    private String recruitmajor;

    private String requetacademic;

    private Integer requestpeople;

    private String publish;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getFirmname() {
        return firmname;
    }

    public void setFirmname(String firmname) {
        this.firmname = firmname == null ? null : firmname.trim();
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher == null ? null : publisher.trim();
    }

    public String getFeeds() {
        return feeds;
    }

    public void setFeeds(String feeds) {
        this.feeds = feeds == null ? null : feeds.trim();
    }

    public Date getRecutime() {
        return recutime;
    }

    public void setRecutime(Date recutime) {
        this.recutime = recutime;
    }

    public String getRecuaddress() {
        return recuaddress;
    }

    public void setRecuaddress(String recuaddress) {
        this.recuaddress = recuaddress == null ? null : recuaddress.trim();
    }

    public String getRecucontent() {
        return recucontent;
    }

    public void setRecucontent(String recucontent) {
        this.recucontent = recucontent == null ? null : recucontent.trim();
    }

    public String getRecruitmajor() {
        return recruitmajor;
    }

    public void setRecruitmajor(String recruitmajor) {
        this.recruitmajor = recruitmajor == null ? null : recruitmajor.trim();
    }

    public String getRequetacademic() {
        return requetacademic;
    }

    public void setRequetacademic(String requetacademic) {
        this.requetacademic = requetacademic == null ? null : requetacademic.trim();
    }

    public Integer getRequestpeople() {
        return requestpeople;
    }

    public void setRequestpeople(Integer requestpeople) {
        this.requestpeople = requestpeople;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish == null ? null : publish.trim();
    }
}