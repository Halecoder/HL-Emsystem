package com.hl.emsystem.model.vo.Json;

import cn.hutool.core.annotation.Alias;
import cn.hutool.json.JSONArray;

import java.util.Map;

public class MyJsonResume {
    @Alias(value = "stuname")
    private String name;

    private String about;

    @Alias(value = "job")
    private String position;

    private Map<String,Object> birth;

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Map<String, Object> getBirth() {
        return birth;
    }

    public void setBirth(Map<String, Object> birth) {
        this.birth = birth;
    }

    public String getLang() {
        return lang == null? "cn":lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    private JSONArray experience;


    private JSONArray education;


    private JSONArray projects;


    private JSONArray skills;


    private JSONArray contributions;

    private Map<String,Object> contact;

    private String lang;
    public Map<String, Object> getContact() {
        return contact;
    }

    public void setContact(Map<String, Object> contact) {
        this.contact = contact;
    }

    public JSONArray getExperience() {
        return experience;
    }

    public void setExperience(JSONArray experience) {
        this.experience = experience;
    }

    public JSONArray getEducation() {
        return education;
    }

    public void setEducation(JSONArray education) {
        this.education = education;
    }

    public JSONArray getProjects() {
        return projects;
    }

    public void setProjects(JSONArray projects) {
        this.projects = projects;
    }

    public JSONArray getSkills() {
        return skills;
    }

    public void setSkills(JSONArray skills) {
        this.skills = skills;
    }

    public JSONArray getContributions() {
        return contributions;
    }

    public void setContributions(JSONArray contributions) {
        this.contributions = contributions;
    }
}
