package com.deye.xl.manager;

import com.deye.xl.entity.GpsData;
import com.deye.xl.repo.GpsDataRepository;
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
