package com.deye.demo.manager;

import com.deye.demo.entity.LoginData;
import com.deye.demo.util.DateUtils;
import com.deye.demo.util.NullUtil;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetOnlineManager {

    private static final Logger log = LoggerFactory.getLogger(SetOnlineManager.class);
    @Autowired
    LoginDataManager loginDataManager;
    @Autowired
    IpDataManager ipDataManager;
    @Autowired
    RuntimeDataManager runtimeDataManager;
    @Autowired
    WorkInfoManager workInfoManager;

    /**
     * 集团下线
     */
    public void setALLDownline() {
        List<LoginData> loginDataList = loginDataManager.getAllOnlineLoginDatas();
        if (!loginDataList.isEmpty()) {
            for (LoginData loginData : loginDataList
            ) {
                String hxzFactory = loginData.getHxzFactory();
                String hxzId = loginData.getHxzId();
                String dateTime = ipDataManager.getdateTime(hxzFactory, hxzId);
                if (NullUtil.isNotEmpty(dateTime)) {
                    Date iptime = DateUtils.StrToDate2(dateTime);
                    //相差几分钟 大于3分下线
                    long difMin = DateUtils.differentMInByMillisecond(iptime, new Date());
                    if (difMin > 3) {
                        ipDataManager.pDownlineVF(hxzFactory, hxzId);
                        log.info("call pDownlineVF hxzFactory={},hxzId={}", hxzFactory, hxzId);
                    }
                } else {
                    ipDataManager.pDownlineVF(hxzFactory, hxzId);
                    log.info("call pDownlineVF hxzFactory={},hxzId={}", hxzFactory, hxzId);
                }


            }
        }
    }

    /**
     * 单台下线
     */
    public void setDownline(String hxzFactory, String hxzId) {
        //获取当前ipDATA 表数据
        String UpdateTime = ipDataManager.getdateTime(hxzFactory, hxzId);
        //设置设备下线	UPDATE LoginData SET OnLine = '0' 	WHERE (HxzFactory=@HxzFactory) AND (HxzId=@HxzId)
        loginDataManager.upOnlineFlg(hxzFactory, hxzId, "0");
        LoginData loginData = loginDataManager.getLoginDataByHxzIdAndHxzFactory(hxzFactory, hxzId);
        if (NullUtil.isNotEmpty(loginData) && NullUtil.isNotEmpty(loginData.getCraneId())) {
            String TableName = loginData.getCraneId();
            //更新RunTime
            runtimeDataManager.upRunTimeSql(TableName, UpdateTime, hxzFactory, hxzId);
            //更新upDownlineTime
            runtimeDataManager.upDownlineTimeSql(TableName, UpdateTime, hxzFactory, hxzId);
            //更新  runtimeData
            runtimeDataManager.upRunTimeDataSql(UpdateTime, hxzFactory, hxzId);

            workInfoManager.upRunTimeDataSql(UpdateTime, hxzFactory, hxzId);
        }

    }
}
