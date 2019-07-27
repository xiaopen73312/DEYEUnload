package com.deye.demo.manager;

import com.deye.demo.repo.AlarmDataRepository;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
public class AlarmDataManager {

    @Autowired
    AlarmDataRepository alarmDataRepository;
    @PersistenceContext //注入的是实体管理器,执行持久化操作
    private EntityManager entityManager;

    @Transactional
    @Modifying
    public void insetSql(String TableName, String HxzFactory,
            String HxzId,
            Date StartTime,
            Date EndTime,
            Integer MomentAlarm,
            Integer WindSpeedAlarm,
            Integer HeightAlarm,
            Integer MinRangeAlarm,
            Integer MaxRangeAlarm,
            Integer PosAngleAlarm,
            Integer NegAngleAlarm,
            Integer ObliguityAlarm,
            Integer EnvironmentAlarm,
            Integer MultiAlarm,
            Integer WorkNo,
            String Name,
            String IdNo,
            Integer Alarm1,
            Integer Alarm2,
            Integer Alarm3,
            Integer Alarm4,
            Integer Alarm5,
            String MultiNegAlarm,
            String MultiPosAlarm,
            String MultiOutAlarm,
            String MultiInAlarm,
            String EnvtNegAlarm,
            String EnvtPosAlarm,
            String EnvtOutAlarm,
            String EnvtInAlarm) {
        Query query = entityManager.createNativeQuery(
                "insert into AlarmData_" + TableName
                        + " (HxzFactory, HxzId,StartTime,EndTime,MomentAlarm,WindSpeedAlarm,HeightAlarm,MinRangeAlarm,"
                        + "MaxRangeAlarm,PosAngleAlarm,NegAngleAlarm,ObliguityAlarm,EnvironmentAlarm,MultiAlarm,WorkNo,"
                        + "Name,IdNo,Alarm1,Alarm2,Alarm3,Alarm4,Alarm5,MultiNegAlarm,MultiPosAlarm,MultiOutAlarm,"
                        + "MultiInAlarm,EnvtNegAlarm,EnvtPosAlarm,EnvtOutAlarm,EnvtInAlarm)"
                        + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
                .setParameter(1, HxzFactory).setParameter(2, HxzId).setParameter(3, StartTime)
                .setParameter(4, EndTime).setParameter(5, MomentAlarm)
                .setParameter(6, WindSpeedAlarm).setParameter(7, HeightAlarm)
                .setParameter(8, MinRangeAlarm).setParameter(9, MaxRangeAlarm)
                .setParameter(10, PosAngleAlarm)
                .setParameter(11, NegAngleAlarm)
                .setParameter(12, ObliguityAlarm).setParameter(13, EnvironmentAlarm)
                .setParameter(14, MultiAlarm)
                .setParameter(15, WorkNo)
                .setParameter(16, Name).setParameter(17, IdNo)
                .setParameter(18, Alarm1).setParameter(19, Alarm2)
                .setParameter(20, Alarm3).setParameter(21, Alarm4)
                .setParameter(22, Alarm5).setParameter(23, MultiNegAlarm)
                .setParameter(24, MultiPosAlarm).setParameter(25, MultiOutAlarm)
                .setParameter(26, MultiInAlarm).setParameter(27, EnvtNegAlarm)
                .setParameter(28, EnvtPosAlarm).setParameter(29, EnvtOutAlarm)
                .setParameter(30, EnvtInAlarm);
        query.executeUpdate();
        entityManager.close();
    }
//    public void pAlarmDataCraneVF(String HxzFactory,
//            String HxzId,
//            Date StartTime,
//            Date EndTime,
//            Integer MaxRadiusAlarm,
//            Integer MinRadiusAlarm,
//            Integer HeightUpAlarm,
//            Integer HeightDownAlarm,
//            Integer AngleLeftAlarm,
//            Integer AngleRightAlarm,
//            Integer TorqueAlarm,
//            Integer WindAlarm,
//            Integer ObliqueAlarm,
//            Integer EnvironmentAlarm,
//            Integer MultiAlarm,
//            String HxzIp,
//            String NowTime,
//            Integer WorkNo,
//            String Name,
//            String IdNo,
//            Integer Alarm1,
//            Integer Alarm2,
//            Integer Alarm3,
//            Integer Alarm4,
//            Integer Alarm5,
//            String MultiNegAlarm,
//            String MultiPosAlarm,
//            String MultiOutAlarm,
//            String MultiInAlarm,
//            String EnvtNegAlarm,
//            String EnvtPosAlarm,
//            String EnvtOutAlarm,
//            String EnvtInAlarm) {
//        alarmDataRepository.pAlarmDataCraneVF(HxzFactory,
//                HxzId,
//                StartTime,
//                EndTime,
//                MaxRadiusAlarm,
//                MinRadiusAlarm,
//                HeightUpAlarm,
//                HeightDownAlarm,
//                AngleLeftAlarm,
//                AngleRightAlarm,
//                TorqueAlarm,
//                WindAlarm,
//                ObliqueAlarm,
//                EnvironmentAlarm,
//                MultiAlarm,
//                HxzIp,
//                NowTime,
//                WorkNo,
//                Name,
//                IdNo,
//                Alarm1,
//                Alarm2,
//                Alarm3,
//                Alarm4,
//                Alarm5,
//                MultiNegAlarm,
//                MultiPosAlarm,
//                MultiOutAlarm,
//                MultiInAlarm,
//                EnvtNegAlarm,
//                EnvtPosAlarm,
//                EnvtOutAlarm,
//                EnvtInAlarm);
//    }
}
