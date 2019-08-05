package com.deye.xl.manager;

import com.deye.xl.repo.XLWorkDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XLWorkDataManager {

    @Autowired
    XLWorkDataRepository xlWorkDataRepository;

}
