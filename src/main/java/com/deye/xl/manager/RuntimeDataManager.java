package com.deye.xl.manager;

import com.deye.xl.entity.RuntimeData;
import com.deye.xl.repo.RuntimeDataRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
public class RuntimeDataManager {

    @Autowired
    RuntimeDataRepository runtimeDataRepository;
    @PersistenceContext //注入的是实体管理器,执行持久化操作
    private EntityManager entityManager;

    public void save(RuntimeData runtimeData) {
        runtimeDataRepository.save(runtimeData);
    }

    public RuntimeData getIpByHxzIdAndHxzFactory(String hxzFactory, String hxzId) {
        return runtimeDataRepository.getRuntimeDataByHxzFactory(hxzFactory, hxzId);
    }


    /**
     * 更新运行状态
     */
    public void upRuntime(String OnlineTime, String RunTime,
            String hxzFactory,
            String hxzId) {

        runtimeDataRepository.upRuntime(
                OnlineTime,

                RunTime,
                hxzFactory,
                hxzId);
    }

    /**
     * 更新运行时间
     */
    @Transactional
    @Modifying
    public void upXLRunTimeSql(String RTime, String HxzFactory, String HxzId) {
        String sql =
                "UPDATE RuntimeData"
                        + "    set  RunTime=DATEDIFF(ss,OnlineTime,?) where    DownlineTime =' '  and HxzFactory= ? AND HxzId= ?  ";
        Query query = entityManager.createNativeQuery(sql).setParameter(1, RTime)
                .setParameter(2, HxzFactory)
                .setParameter(3, HxzId);
        query.executeUpdate();
        entityManager.close();
    }

    /**
     * 更新运行时间
     */
    @Transactional
    @Modifying
    public void upRunTimeDataSql(String RTime, String HxzFactory, String HxzId) {
        String sql =
                "UPDATE RuntimeData"
                        + "    set  RunTime=DATEDIFF(ss,OnlineTime,?) ,DownlineTime = ? where HxzFactory= ? AND HxzId= ? and DownlineTime is null ";
        Query query = entityManager.createNativeQuery(sql).setParameter(1, RTime)
                .setParameter(2, RTime)
                .setParameter(3, HxzFactory)
                .setParameter(4, HxzId);
        query.executeUpdate();
        entityManager.close();
    }

    /**
     * 更新运行时间
     */
    @Transactional
    @Modifying
    public void upRunTimeSql(String TableName, String RTime, String HxzFactory, String HxzId) {
        String sql =
                "UPDATE RuntimeData_" + TableName
                        + "  set  RunTime=DATEDIFF(ss,OnlineTime,?) where DownlineTime=' ' and HxzFactory= ? AND HxzId= ?  ";
        Query query = entityManager.createNativeQuery(sql).setParameter(1, RTime)
                .setParameter(2, HxzFactory)
                .setParameter(3, HxzId);
        query.executeUpdate();
        entityManager.close();
    }

    @Transactional
    @Modifying
    public void upDownlineTimeSql(String TableName, String DownlineTime, String HxzFactory,
            String HxzId) {
        String sql =
                "UPDATE RuntimeData_" + TableName
                        + "    set  DownlineTime= ? where   DownlineTime=' '    and  HxzFactory= ? AND HxzId= ?";
        Query query = entityManager.createNativeQuery(sql).setParameter(1, DownlineTime)
                .setParameter(2, HxzFactory)
                .setParameter(3, HxzId);
        query.executeUpdate();
        entityManager.close();
    }

    /**
     * 插入运行时长数据
     */
    @Transactional
    @Modifying
    public void insertRunTimeDataSql(String TableName, String hxzFactory, String hxzId,
            String OnlineTime, String DownlineTime,
            String RunTime) {

        Query query = entityManager.createNativeQuery(
                "insert into RuntimeData_" + TableName
                        + " (HxzFactory, HxzId,OnlineTime,DownlineTime,RunTime)"
                        + " values (?,?,?,?,?)")
                .setParameter(1, hxzFactory).setParameter(2, hxzId).setParameter(3, OnlineTime)
                .setParameter(4, DownlineTime).setParameter(5, RunTime);
        query.executeUpdate();
        entityManager.close();
    }

    /**
     * 获取总运行时长 不含今天
     */
    public String getTotalRuntimeData(String table, String todady
    ) {

        String sql =
                "select sum(convert(int,RunTime))  from RuntimeData_" + table
                        + " where OnlineTime<'"
                        + todady + "' and RunTime != ''";

//        Query query = entityManager.createNativeQuery(sql);
        Query query = entityManager.createNativeQuery(sql);
        String ruanTimeStr = "0";
        List<String> resultList = query.getResultList();

        if (resultList.size() > 0) {
            ruanTimeStr = String.valueOf(resultList.get(0));
        }
        entityManager.close();
        return ruanTimeStr;
    }

    /**
     * 获取时间段内开机时间长 如今天
     */
    public String getDaysRuntime(String table, String startDate, String endDate
    ) {

        String sql =
                "select sum(convert(int,RunTime)) from RuntimeData_" + table
                        + " where OnlineTime>= '"
                        + startDate + "' and DownlineTime <='" + endDate + "' and RunTime != ''";

        Query query = entityManager.createNativeQuery(sql);
        String ruanTimeStr = "0";
        List<String> resultList = query.getResultList();

        if (resultList.size() > 0) {
            ruanTimeStr = String.valueOf(resultList.get(0));
        }

        entityManager.close();
        return ruanTimeStr;
    }
}
