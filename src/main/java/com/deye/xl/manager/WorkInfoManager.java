package com.deye.demo.manager;

import com.deye.demo.entity.WorkInfo;
import com.deye.demo.repo.WorkInfoRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
public class WorkInfoManager {

    @Autowired
    WorkInfoRepository workInfoRepository;
    @PersistenceContext //注入的是实体管理器,执行持久化操作
    private EntityManager entityManager;
    public WorkInfo getWorkInfoByHxzFactory(String hxzFactory,
            String hxzId, String OnlineTime) {
        return workInfoRepository.getWorkInfoByHxzFactory(hxzFactory, hxzId, OnlineTime);
    }

    public String getTowerCrane_CraneId(String hxzFactory,
            String hxzId, String OnlineTime) {
        return workInfoRepository.getTowerCrane_CraneId(hxzFactory, hxzId, OnlineTime);
    }
    public void save(WorkInfo workInfo) {
        workInfoRepository.save(workInfo);
    }


    public void updateRunTime(String hxzFactory,
            String hxzId, String RunTime, String DownlineTime) {
        workInfoRepository.updateRunTime(hxzFactory,
                hxzId, RunTime, DownlineTime);
    }

    /**
     * 更新运行时间
     */
    @Transactional
    @Modifying
    public void upRunTimeDataSql(String RTime, String HxzFactory, String HxzId) {
        String sql =
                "UPDATE WorkInfo"
                        + "    set  RunTime=DATEDIFF(ss,OnlineTime,?) ,DownlineTime = ? where TowerCrane_Factory= ? AND TowerCrane_HxzId= ? and DownlineTime is null  ";
        Query query = entityManager.createNativeQuery(sql).setParameter(1, RTime)
                .setParameter(2, RTime)
                .setParameter(3, HxzFactory)
                .setParameter(4, HxzId);
        query.executeUpdate();
        entityManager.close();
    }
}
