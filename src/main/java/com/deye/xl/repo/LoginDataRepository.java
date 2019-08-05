package com.deye.xl.repo;

import com.deye.xl.entity.LoginData;
import java.math.BigDecimal;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDataRepository extends JpaRepository<LoginData, BigDecimal> {


    @Query(value = "SELECT  OnLine FROM LoginData where  HxzFactory=:hxzFactory and HxzId=:hxzId", nativeQuery = true)
    String getOnLine(@Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId);

    @Query(value = "SELECT  *  FROM LoginData where OnLine='1' and    Type='4' ", nativeQuery = true)
    List<LoginData> getAllOnLine();

    @Query(value = "SELECT *  FROM LoginData where  HxzFactory=:hxzFactory and HxzId=:hxzId", nativeQuery = true)
    LoginData getLoginDataByHxzFactory(@Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId);
   /* HardwareVer=@HardwareVer,
    SoftwareVer=@SoftwareVer,
    SimCardNo=@SimCardNo,
    OnLine='1',
    Type=1*/

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update LoginData set HardwareVer =:HardwareVer,SoftwareVer =:SoftwareVer," +
            "SimCardNo=:SimCardNo,OnLine=:online,Type=:type" +
            " where  HxzFactory=:hxzFactory and HxzId=:hxzId", nativeQuery = true)
    void upLoginData(@Param("HardwareVer") String HardwareVer,
            @Param("SoftwareVer") String SoftwareVer,
            @Param("SimCardNo") String SimCardNo,
            @Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId,
            @Param("online") String online,
            @Param("type") int type);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update LoginData set OnLine=:online" +
            " where  HxzFactory=:hxzFactory and HxzId=:hxzId", nativeQuery = true)
    void upOnlineFlg(@Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId,
            @Param("online") String online);
}
