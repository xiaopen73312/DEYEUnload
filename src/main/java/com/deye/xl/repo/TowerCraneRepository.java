package com.deye.demo.repo;


import com.deye.demo.entity.TowerCrane;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TowerCraneRepository extends JpaRepository<TowerCrane, BigDecimal> {


    @Query(value = "SELECT *  FROM TowerCrane where  Factory=:Factory and HxzId=:hxzId", nativeQuery = true)
    TowerCrane getTowerCraneDataByHxzFactory(@Param("Factory") String Factory,
            @Param("hxzId") String hxzId);

    @Query(value = "SELECT Project_id  FROM TowerCrane where  Factory=:Factory and HxzId=:hxzId", nativeQuery = true)
    String getProjectid(@Param("Factory") String Factory,
            @Param("hxzId") String hxzId);

    @Query(value = "SELECT Remark  FROM TowerCrane where  Factory=:Factory and HxzId=:hxzId", nativeQuery = true)
    String getRemark(@Param("Factory") String Factory,
            @Param("hxzId") String hxzId);

}
