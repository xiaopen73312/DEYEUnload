package com.deye.xl.manager;

import com.deye.xl.entity.XLControlData;
import com.deye.xl.repo.XLControlDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XLControlDataManager {

    @Autowired
    XLControlDataRepository xlControlDataRepository;

    public XLControlData getEntity(String hxzFactory,
            String hxzId) {
        return xlControlDataRepository.getEntity(hxzFactory,
                hxzId);
    }

    public void save(XLControlData xlControlData) {
        xlControlDataRepository.save(xlControlData);
    }

    public void upModifyServer(String ModifyServer, String SendFlag, String hxzFactory,
            String hxzId) {

        xlControlDataRepository.upModifyServer(ModifyServer, SendFlag, hxzFactory, hxzId);
    }

    public void upSendFlag(String SendFlag, String hxzFactory,
            String hxzId) {

        xlControlDataRepository.upSendFlag(SendFlag, hxzFactory, hxzId);
    }
}
