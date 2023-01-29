package com.hl.emsystem.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;


public class GraStudent {

    /**
     * 序号，无意义
     */
    private Long stuId;
    /**
     * 学号
     */
    @NotNull(message = "学号不能为空")
    private String stuno;

    /**
     * 学生姓名
     */
    private String stuname;

    /**
     * 年龄
     */
    @Min(value = 0,message = "年龄不能小于0")
    @Max(value = 100,message = "年龄不能大于100")
    private Integer age;


    /**
     * 性别
     */
    private String sex;


    /**
     * 出生日期
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date birthdate;

    /**
     * 民族
     */
    private String nation;

    /**
     * 政治面貌
     */
    private String politic;

    /**
     * 专业
     */
    private String major;

    /**
     * 院系名称
     */
    private String facname;

    /**
     * 学历
     */
    private String degree;

    /**
     * 学制
     */
    private String academic;

    /**
     * 培养方式
     */
    private String cultivate;

    /**
     * 生源地
     */
    private String birthplace;

    public String getStuno() {
        return stuno;
    }

    public void setStuno(String stuno) {
        this.stuno = stuno;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname == null ? null : stuname.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getPolitic() {
        return politic;
    }

    public void setPolitic(String politic) {
        this.politic = politic == null ? null : politic.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getFacname() {
        return facname;
    }

    public void setFacname(String facname) {
        this.facname = facname == null ? null : facname.trim();
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public String getAcademic() {
        return academic;
    }

    public void setAcademic(String academic) {
        this.academic = academic == null ? null : academic.trim();
    }

    public String getCultivate() {
        return cultivate;
    }

    public void setCultivate(String cultivate) {
        this.cultivate = cultivate == null ? null : cultivate.trim();
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace == null ? null : birthplace.trim();
    }

    public Long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }
}