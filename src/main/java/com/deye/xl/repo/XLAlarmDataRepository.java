package com.deye.xl.repo;

import com.deye.xl.entity.XLAlarmData;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XLAlarmDataRepository extends JpaRepository<XLAlarmData, BigDecimal> {

}
