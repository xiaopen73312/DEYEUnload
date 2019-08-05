package com.deye.xl.repo;


import com.deye.xl.entity.RuntimeData;
import java.math.BigDecimal;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RuntimeDataRepository extends JpaRepository<RuntimeData, BigDecimal> {

    @Query(value = "SELECT *  FROM RuntimeData where  HxzFactory=:hxzFactory and HxzId=:hxzId", nativeQuery = true)
    RuntimeData getRuntimeDataByHxzFactory(@Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId);

    /**
     * OnlineTime=@NowTime, DownlineTime=@Null, RunTime='0'
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update RuntimeData set OnlineTime =:OnlineTime,DownlineTime =null," +
            "RunTime =:RunTime where  HxzFactory=:hxzFactory and HxzId=:hxzId", nativeQuery = true)
    void upRuntime(
            @Param("OnlineTime") String OnlineTime,
            @Param("RunTime") String RunTime,
            @Param("hxzFactory") String hxzFactory,
            @Param("hxzId") String hxzId);
}
