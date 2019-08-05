package com.deye.xl.repo;

import com.deye.xl.entity.XLRealtimeData;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface XLRealtimeDataRepository extends JpaRepository<XLRealtimeData, BigDecimal> {


    @Query(value = "SELECT *  FROM XL_RealtimeData where  HxzFactory=:hxzFactory and HxzId=:hxzId", nativeQuery = true)
    XLRealtimeData getEntity(@Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId);

}
