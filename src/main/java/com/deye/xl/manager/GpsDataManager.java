package com.deye.demo.manager;

import com.deye.demo.entity.GpsData;
import com.deye.demo.repo.GpsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GpsDataManager {

    @Autowired
    GpsDataRepository gpsDataRepository;

    public void save(GpsData gpsData) {
        gpsDataRepository.save(gpsData);
    }

    public GpsData getGpsData(String hxzFactory, String hxzId) {
        return gpsDataRepository.getGPSData(hxzFactory, hxzId);
    }

    public void upDateGps(String hxzFactory, String hxzId, int LocateLock, int WorkSiteNo) {
        gpsDataRepository.upDateGPS(WorkSiteNo, LocateLock, hxzFactory, hxzId);
    }

    public void pGpsDataVF(String HxzFactory,
            String HxzId,
            String Latitude,
            String Longitude,
            String HxzIp,
            String NowTime

    ) {
        gpsDataRepository.pGpsDataVF(HxzFactory,
                HxzId,
                Latitude,
                Longitude,
                HxzIp,
                NowTime);
    }
}
