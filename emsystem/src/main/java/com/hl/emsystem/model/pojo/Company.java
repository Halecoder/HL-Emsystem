package com.hl.emsystem.model.pojo;

import java.util.Date;

public class Company {
    private String firmname;

    private String firmaddress;

    private String firmtype;

    private String ectype;

    private String publisher;

    private String department;

    private String contact;

    private String firmintroduce;

    private String recruitmajor;

    private String requetacademic;

    private Integer requestpeople;

    private String recruitmethod;

    private Integer jobfairplace;

    private String publish;

    private Date expirationdate;

    public String getFirmname() {
        return firmname;
    }

    public void setFirmname(String firmname) {
        this.firmname = firmname == null ? null : firmname.trim();
    }

    public String getFirmaddress() {
        return firmaddress;
    }

    public void setFirmaddress(String firmaddress) {
        this.firmaddress = firmaddress == null ? null : firmaddress.trim();
    }

    public String getFirmtype() {
        return firmtype;
    }

    public void setFirmtype(String firmtype) {
        this.firmtype = firmtype == null ? null : firmtype.trim();
    }

    public String getEctype() {
        return ectype;
    }

    public void setEctype(String ectype) {
        this.ectype = ectype == null ? null : ectype.trim();
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher == null ? null : publisher.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getFirmintroduce() {
        return firmintroduce;
    }

    public void setFirmintroduce(String firmintroduce) {
        this.firmintroduce = firmintroduce == null ? null : firmintroduce.trim();
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

    public String getRecruitmethod() {
        return recruitmethod;
    }

    public void setRecruitmethod(String recruitmethod) {
        this.recruitmethod = recruitmethod == null ? null : recruitmethod.trim();
    }

    public Integer getJobfairplace() {
        return jobfairplace;
    }

    public void setJobfairplace(Integer jobfairplace) {
        this.jobfairplace = jobfairplace;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish == null ? null : publish.trim();
    }

    public Date getExpirationdate() {
        return expirationdate;
    }

    public void setExpirationdate(Date expirationdate) {
        this.expirationdate = expirationdate;
    }
}