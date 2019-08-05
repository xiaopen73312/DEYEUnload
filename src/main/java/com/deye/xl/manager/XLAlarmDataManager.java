package com.deye.xl.manager;


import com.deye.xl.entity.XLAlarmData;
import com.deye.xl.repo.XLAlarmDataRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
public class XLAlarmDataManager {

    @Autowired
    XLAlarmDataRepository xlAlarmDataRepository;
    @PersistenceContext //注入的是实体管理器,执行持久化操作
    private EntityManager entityManager;

    public void save(XLAlarmData xlAlarmData) {
        xlAlarmDataRepository.save(xlAlarmData);
    }


    @Transactional
    @Modifying
    public void saveSql(String HxzFactory,
            String HxzId,
            String Projectid,
            String LoginData_row_id,
            String StartTime,
            String EndTime,
            String AlarmType,
            String MaxValue,
            String MaxValuePercent
    ) {

        Query query = entityManager.createNativeQuery(
                "insert into XL_AlarmData"
                        + " (HxzFactory,HxzId,Projectid,LoginData_row_id,StartTime,"
                        + "EndTime,AlarmType,MaxValue,MaxValuePercent)"
                        + " values (?,?,?,?,?,?,?,?,?)")
                .setParameter(1, HxzFactory).setParameter(2, HxzId).setParameter(3, Projectid)
                .setParameter(4, LoginData_row_id).setParameter(5, StartTime)
                .setParameter(6, EndTime)
                .setParameter(7, AlarmType).setParameter(8, MaxValue)
                .setParameter(9, MaxValuePercent);
        query.executeUpdate();
        entityManager.close();
    }
}
