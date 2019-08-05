package com.deye.xl.manager;

import com.deye.xl.repo.XLConfigDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XLConfigDataManager {

    @Autowired
    XLConfigDataRepository xlConfigDataRepository;
}
