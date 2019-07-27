package com.deye.xl.repo;

import com.deye.xl.entity.XLAlarmData;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XLAlarmDataRepository extends JpaRepository<XLAlarmData, BigDecimal> {

}
