package com.deye.xl.repo;

import com.deye.xl.entity.XLWorkData;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XLWorkDataRepository extends JpaRepository<XLWorkData, BigDecimal> {

}
