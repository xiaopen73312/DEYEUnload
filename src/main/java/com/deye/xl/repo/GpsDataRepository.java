package com.deye.xl.repo;

import com.deye.xl.entity.GpsData;
import java.math.BigDecimal;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GpsDataRepository extends JpaRepository<GpsData, BigDecimal> {


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update GpsData set WorkSiteNo =:WorkSiteNo,LocateLock =:LocateLock where  HxzFactory=:hxzFactory and HxzId=:hxzId", nativeQuery = true)
    void upDateGPS(@Param("WorkSiteNo") int WorkSiteNo,
            @Param("LocateLock") int LocateLock,
            @Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId);


    @Query(value = "SELECT *  FROM GpsData where  HxzFactory=:hxzFactory and HxzId=:hxzId", nativeQuery = true)
    GpsData getGPSData(@Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId);

    @Procedure(name = "pGpsDataVF")
    void pGpsDataVF(@Param("HxzFactory") String HxzFactory, @Param("HxzId") String HxzId,
            @Param("Latitude") String Latitude,
            @Param("Longitude") String Longitude,
            @Param("HxzIp") String HxzIp,
            @Param("NowTime") String NowTime

    );
}
