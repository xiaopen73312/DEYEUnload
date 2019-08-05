package com.deye.xl.repo;

import com.deye.xl.entity.XLBaseData;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface XLBaseDataRepository extends JpaRepository<XLBaseData, BigDecimal> {

    @Query(value = "SELECT *  FROM XL_BaseData where  HxzFactory=:hxzFactory and HxzId=:hxzId", nativeQuery = true)
    XLBaseData getEntity(@Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId);
}
