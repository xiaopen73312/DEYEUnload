package com.deye.xl.manager;

import com.deye.xl.entity.XLAlarmDataStart;
import com.deye.xl.repo.XLAlarmDataStartRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
public class XLAlarmDataStartManager {

    @Autowired
    XLAlarmDataStartRepository xlAlarmDataStartRepository;
    @PersistenceContext //注入的是实体管理器,执行持久化操作
    private EntityManager entityManager;

    public void save(XLAlarmDataStart xlAlarmDataStart) {
        xlAlarmDataStartRepository.save(xlAlarmDataStart);
    }


    @Transactional
    @Modifying
    public void saveSql(String HxzFactory,
            String HxzId,
            String Projectid,
            String LoginData_row_id,
            String StartTime,
            String AlarmType,
            String AlarmValue

    ) {

        Query query = entityManager.createNativeQuery(
                "insert into XL_AlarmDataStart"
                        + " (HxzFactory,HxzId,Projectid,LoginData_row_id,StartTime,"
                        + "AlarmType,AlarmValue)"
                        + " values (?,?,?,?,?,?,?)")
                .setParameter(1, HxzFactory).setParameter(2, HxzId).setParameter(3, Projectid)
                .setParameter(4, LoginData_row_id).setParameter(5, StartTime)
                .setParameter(6, AlarmType)
                .setParameter(7, AlarmValue);
        query.executeUpdate();
        entityManager.close();
    }
}
