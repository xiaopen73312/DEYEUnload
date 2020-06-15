package com.deye.xl.repo;


import com.deye.xl.entity.Project;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, BigDecimal> {

    @Query(value = "SELECT *  FROM Project where  id=:id  ", nativeQuery = true)
    Project getProjectById(@Param("id") String id);

    @Query(value = "SELECT Remark  FROM Project where  id=:id  ", nativeQuery = true)
    String getProjectInfo(@Param("id") String id);
}
