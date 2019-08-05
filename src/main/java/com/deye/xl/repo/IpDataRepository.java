package com.deye.xl.repo;

import com.deye.xl.entity.IpData;
import java.math.BigDecimal;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IpDataRepository extends JpaRepository<IpData, BigDecimal> {


    @Query(value = "SELECT *  FROM IpData", nativeQuery = true)
    List<IpData> getIpDatas();


    @Query(value = "SELECT *  FROM IpData where  HxzFactory=:hxzFactory and HxzId=:hxzId", nativeQuery = true)
    IpData getIpDataByHxzFactory(@Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update IpData set HxzIp =:hxzIp,DateTime =:dateTime where  HxzFactory=:hxzFactory and HxzId=:hxzId", nativeQuery = true)
    void upDateIp(@Param("hxzIp") String hxzIp,
            @Param("dateTime") String dateTime,
            @Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId);

    @Procedure(name = "pDownlineVF")
    void pDownlineVF(@Param("HxzFactory") String HxzFactory,
            @Param("HxzId") String HxzId);


    @Query(value = "SELECT DateTime FROM IpData where  HxzFactory=:hxzFactory and HxzId=:hxzId", nativeQuery = true)
    String getdateTime(@Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId);
}
