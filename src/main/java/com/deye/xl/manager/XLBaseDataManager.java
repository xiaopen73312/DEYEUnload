package com.deye.xl.manager;

import com.deye.xl.repo.XLBaseDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XLBaseDataManager {

    @Autowired
    XLBaseDataRepository xlBaseDataRepository;


}
