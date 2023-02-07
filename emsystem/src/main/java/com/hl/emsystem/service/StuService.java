package com.hl.emsystem.service;

import com.hl.emsystem.model.pojo.GraStudent;
import com.hl.emsystem.model.pojo.ResumeWithBLOBs;

public interface StuService {
    void upStuInfo(GraStudent stuInfo);

    GraStudent getInfo(String stuno);

    int updateStuInfo(GraStudent stuInfo);

    void upResInfo(ResumeWithBLOBs resume);

    ResumeWithBLOBs getResInfo(String stuno);

    void updateResInfo(ResumeWithBLOBs resInfo);

}
