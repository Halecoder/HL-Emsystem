package com.hl.emsystem.model.pojo;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

@TableName( autoResultMap=true)
public class ResumeWithBLOBs extends Resume {
    @TableField(typeHandler = JacksonTypeHandler.class)
    private JSONArray experience;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private JSONArray education;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private JSONArray projects;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private JSONArray skills;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private JSONArray contributions;

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