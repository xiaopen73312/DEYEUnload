package com.deye.demo.repo;

import com.deye.demo.entity.AlarmDataCrane;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmDataRepository extends JpaRepository<AlarmDataCrane, BigDecimal> {
//
//    @Procedure(name = "pAlarmDataCraneVF")
//    void pAlarmDataCraneVF(@Param("HxzFactory") String HxzFactory, @Param("HxzId") String HxzId,
//            @Param("StartTime") Date StartTime,
//            @Param("EndTime") Date EndTime,
//            @Param("MaxRadiusAlarm") Integer MaxRadiusAlarm,
//            @Param("MinRadiusAlarm") Integer MinRadiusAlarm,
//            @Param("HeightUpAlarm") Integer HeightUpAlarm,
//            @Param("HeightDownAlarm") Integer HeightDownAlarm,
//            @Param("AngleLeftAlarm") Integer AngleLeftAlarm,
//            @Param("AngleRightAlarm") Integer AngleRightAlarm,
//            @Param("TorqueAlarm") Integer TorqueAlarm,
//            @Param("WindAlarm") Integer WindAlarm, @Param("ObliqueAlarm") Integer ObliqueAlarm,
//            @Param("EnvironmentAlarm") Integer EnvironmentAlarm,
//            @Param("MultiAlarm") Integer MultiAlarm, @Param("HxzIp") String HxzIp,
//            @Param("NowTime") String NowTime, @Param("WorkNo") Integer WorkNo,
//            @Param("Name") String Name,
//            @Param("IdNo") String IdNo,
//            @Param("Alarm1") Integer Alarm1,
//            @Param("Alarm2") Integer Alarm2,
//            @Param("Alarm3") Integer Alarm3,
//            @Param("Alarm4") Integer Alarm4,
//            @Param("Alarm5") Integer Alarm5,
//            @Param("MultiNegAlarm") String MultiNegAlarm,
//            @Param("MultiPosAlarm") String MultiPosAlarm,
//            @Param("MultiOutAlarm") String MultiOutAlarm,
//            @Param("MultiInAlarm") String MultiInAlarm,
//            @Param("EnvtNegAlarm") String EnvtNegAlarm,
//            @Param("EnvtPosAlarm") String EnvtPosAlarm,
//            @Param("EnvtOutAlarm") String EnvtOutAlarm,
//            @Param("EnvtInAlarm") String EnvtInAlarm
//    );

}
