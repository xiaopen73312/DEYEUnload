package com.deye.xl.repo;

import com.deye.xl.entity.WorkInfo;
import java.math.BigDecimal;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkInfoRepository extends JpaRepository<WorkInfo, BigDecimal> {


    @Query(value = "SELECT *  FROM WorkInfo where  TowerCrane_Factory=:hxzFactory and TowerCrane_HxzId=:hxzId and OnlineTime=:OnlineTime", nativeQuery = true)
    WorkInfo getWorkInfoByHxzFactory(@Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId, @Param("OnlineTime") String OnlineTime);

    @Query(value = "SELECT TowerCrane_CraneId FROM WorkInfo where  TowerCrane_Factory=:hxzFactory and TowerCrane_HxzId=:hxzId and OnlineTime=:OnlineTime", nativeQuery = true)
    String getTowerCrane_CraneId(@Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId, @Param("OnlineTime") String OnlineTime);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update WorkInfo set RunTime =:RunTime where  TowerCrane_Factory=:hxzFactory and TowerCrane_HxzId=:hxzId  and DownlineTime=:DownlineTime", nativeQuery = true)
    void updateRunTime(@Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId, @Param("RunTime") String RunTime,
            @Param("DownlineTime") String DownlineTime);


}
