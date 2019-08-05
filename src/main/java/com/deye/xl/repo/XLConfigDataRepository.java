package com.deye.xl.repo;

import com.deye.xl.entity.XLConfigData;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XLConfigDataRepository extends JpaRepository<XLConfigData, BigDecimal> {

}
