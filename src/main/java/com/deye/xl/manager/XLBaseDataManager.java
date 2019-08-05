package com.deye.xl.manager;

import com.deye.xl.entity.XLBaseData;
import com.deye.xl.repo.XLBaseDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XLBaseDataManager {

    @Autowired
    XLBaseDataRepository xlBaseDataRepository;

    public XLBaseData getEntity(String hxzFactory,
            String hxzId) {
        return xlBaseDataRepository.getEntity(hxzFactory,
                hxzId);
    }

    public void save(XLBaseData xlBaseData) {
        xlBaseDataRepository.save(xlBaseData);
    }
}
