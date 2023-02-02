package com.hl.emsystem.service.Impl;

import com.hl.emsystem.model.dao.GraStudentMapper;
import com.hl.emsystem.model.dao.ResumeMapper;
import com.hl.emsystem.model.pojo.GraStudent;
import com.hl.emsystem.model.pojo.ResumeWithBLOBs;
import com.hl.emsystem.service.StuService;
import com.hl.emsystem.utils.SnowFlakeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private GraStudentMapper graStudentMapper;
    @Autowired
    private ResumeMapper resumeMapper;

    @Override
    public void upStuInfo(GraStudent stuInfo) {

        graStudentMapper.insertSelective(stuInfo);

    }

    @Override
    public GraStudent getInfo(String stuno) {

       return graStudentMapper.selectByPrimaryKey(stuno);

    }

    @Override
    public int updateStuInfo(GraStudent stuInfo) {
        return graStudentMapper.updateByPrimaryKeySelective(stuInfo);
    }

    @Override
    public void upResInfo(ResumeWithBLOBs resume) {
        SnowFlakeUtils sfId = new SnowFlakeUtils();
        resume.setResid(sfId.nextId(resume));
        resumeMapper.insertSelective(resume);
    }

    @Override
    public ResumeWithBLOBs getResInfo(String stuno) {
        return resumeMapper.selectByPrimaryKey(stuno);
    }

    @Override
    public void updateResInfo(ResumeWithBLOBs resInfo) {
        resumeMapper.updateByPrimaryKeySelective(resInfo);
    }
}

