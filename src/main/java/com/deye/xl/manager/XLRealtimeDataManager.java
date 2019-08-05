package com.deye.xl.manager;

import com.deye.xl.entity.XLRealtimeData;
import com.deye.xl.repo.XLRealtimeDataRepository;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
public class XLRealtimeDataManager {

    @Autowired
    XLRealtimeDataRepository xlRealtimeDataRepository;
    @PersistenceContext //注入的是实体管理器,执行持久化操作
    private EntityManager entityManager;

    public void save(XLRealtimeData xlRealtimeData) {
        xlRealtimeDataRepository.save(xlRealtimeData);
    }


    @Transactional
    @Modifying
    public void saveSql(String HxzFactory,
            String HxzId,
            BigDecimal Projectid,
            BigDecimal LoginData_row_id,
            String RTime,
            String Weight1,
            String Weight2,
            String Weight,
            String WeightPercent,
            String ObliguityX,
            String ObliguityY,
            String Obliguity,
            Integer BatteryPercent,
            String Weight1Status,
            String Weight2Status,
            String ObliguityXStatus,
            String ObliguityYStatus,
            String Wireless1Status,
            String Wireless2Status,
            String GpsStatus,
            String BatteryStatus
    ) {

        Query query = entityManager.createNativeQuery(
                "insert into XL_RealtimeData"
                        + " (HxzFactory,HxzId,Projectid,LoginData_row_id,RTime,Weight1,Weight2,Weight,"
                        + "WeightPercent,ObliguityX,ObliguityY,Obliguity,BatteryPercent,Weight1Status,"
                        + "Weight2Status,ObliguityXStatus,"
                        + "ObliguityYStatus,Wireless1Status,Wireless2Status,GpsStatus,BatteryStatus)"
                        + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
                .setParameter(1, HxzFactory).setParameter(2, HxzId).setParameter(3, Projectid)
                .setParameter(4, LoginData_row_id).setParameter(5, RTime).setParameter(6, Weight1)
                .setParameter(7, Weight2).setParameter(8, Weight)
                .setParameter(9, WeightPercent).setParameter(10, ObliguityX)
                .setParameter(11, ObliguityY)
                .setParameter(12, Obliguity)
                .setParameter(13, BatteryPercent).setParameter(14, Weight1Status)
                .setParameter(15, Weight2Status)
                .setParameter(16, ObliguityXStatus)
                .setParameter(17, ObliguityYStatus).setParameter(18, Wireless1Status)
                .setParameter(19, Wireless2Status).setParameter(20, GpsStatus)
                .setParameter(21, BatteryStatus);
        query.executeUpdate();
        entityManager.close();
    }

    public XLRealtimeData getEntity(String hxzFactory,
            String hxzId) {
        return xlRealtimeDataRepository.getEntity(hxzFactory,
                hxzId);
    }

}
