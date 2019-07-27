package com.deye.xl.manager;


import com.deye.xl.repo.XLAlarmDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XLAlarmDataManager {

    @Autowired
    XLAlarmDataRepository xlAlarmDataRepository;


}
