package com.hl.emsystem.service;

import com.hl.emsystem.model.pojo.People;

public interface PeopleService {
    People selectByPrimaryKey(Long peopleId);
}
