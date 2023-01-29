package com.hl.emsystem.service;

import com.hl.emsystem.model.pojo.GraStudent;
import com.hl.emsystem.model.pojo.Resume;

public interface StuService {
    void upStuInfo(GraStudent stuInfo);

    GraStudent getInfo(String stuno);

    int updateStuInfo(GraStudent stuInfo);

    void upResInfo(Resume resume);

    Resume getResInfo(String stuno);

    void updateResInfo(Resume resInfo);
}
