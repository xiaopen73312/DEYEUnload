package com.deye.xl.repo;

import com.deye.xl.entity.XLBaseData;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XLBaseDataRepository extends JpaRepository<XLBaseData, BigDecimal> {

}
