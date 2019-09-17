package com.deye.xl.repo;

import com.deye.xl.entity.XLControlData;
import java.math.BigDecimal;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface XLControlDataRepository extends JpaRepository<XLControlData, BigDecimal> {

    @Query(value = "SELECT *  FROM XL_ControlData where  HxzFactory=:hxzFactory and HxzId=:hxzId", nativeQuery = true)
    XLControlData getEntity(@Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update XL_ControlData set ModifyServer =:ModifyServer ,SendFlag=:SendFlag  where  HxzFactory=:hxzFactory and HxzId=:hxzId", nativeQuery = true)
    void upModifyServer(@Param("ModifyServer") String ModifyServer,
            @Param("SendFlag") String SendFlag,
            @Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update XL_ControlData set  SendFlag=:SendFlag  where  HxzFactory=:hxzFactory and HxzId=:hxzId", nativeQuery = true)
    void upSendFlag(
            @Param("SendFlag") String SendFlag,
            @Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId);
}
