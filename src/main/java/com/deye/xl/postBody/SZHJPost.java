package com.deye.xl.postBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deye.xl.entity.XLBaseData;
import com.deye.xl.entity.XLRealtimeData;
import com.deye.xl.httpService.SZHJClinet;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 卸料通用接口POST
 */
@Component
public class SZHJPost {

    private static final Logger log = LoggerFactory.getLogger(SZHJPost.class);
    @Autowired
    SZHJClinet deyeClinet;
    @Autowired
    Gson gson;
    @Value("${deye.appid}")
    private String appid;
    @Value("${deye.appsecret}")
    private String appsecret;

    /**
     * 上报注册信息
     */
    public void LoginDataUnloadingPlatform(String HxzFactory, String HxzId,
            String HardwareVer, String SoftwareVer, String SimCardNo, String Type)
            throws Exception {

        JSONObject gjson = new JSONObject();
        gjson.put("HxzFactory", HxzFactory);//黑匣子厂家
        gjson.put("HxzId", HxzId);//黑匣子编号
        gjson.put("HardwareVer", HardwareVer);//硬件版本号
        gjson.put("SoftwareVer", SoftwareVer);//软件版本号
        gjson.put("SimCardNo", SimCardNo);//SIM卡号
        gjson.put("Type", Type);//设备类型
        gjson.put("appid", appid);//
        gjson.put("appsecret", appsecret);//
        String str = gjson.toJSONString();
        try {
            String strReturn = deyeClinet.postCustomerAPI(str, "/zhgd/LoginDataUnloadingPlatform");
        } catch (Exception e) {
            log.error(e.toString());
        }


    }

    /**
     * 上报基础参数
     */
    public void BaseDataUnloadingPlatform(XLBaseData baseDataCrane) {
        String str = gson.toJson(baseDataCrane);
        JSONObject tmpstrsonObject = JSONArray.parseObject(str);

        tmpstrsonObject.remove("rowId");
        tmpstrsonObject.remove("Weight1Zero");
        tmpstrsonObject.remove("Weight2Zero");
        tmpstrsonObject.remove("ObliguityXZero");
        tmpstrsonObject.remove("ObliguityYZero");
        tmpstrsonObject.put("appid", appid);//
        tmpstrsonObject.put("appsecret", appsecret);//
        str = tmpstrsonObject.toString();
        try {
            String strReturn = deyeClinet.postCustomerAPI(str, "/zhgd/BaseDataUnloadingPlatform");

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 上报实时数据
     */
    public void RealtimeData(XLRealtimeData xlRealtimeData) {
        String str = gson.toJson(xlRealtimeData);
        JSONObject tmpstrsonObject = JSONArray.parseObject(str);
        tmpstrsonObject.remove("rowId");
        tmpstrsonObject.put("appid", appid);//
        tmpstrsonObject.put("appsecret", appsecret);//
        str = tmpstrsonObject.toString();
        try {
            String strReturn = deyeClinet
                    .postCustomerAPI(str, "/zhgd/RealtimeDataUnloadingPlatform");

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 上报GPS定位数据
     */
    public void GpsData(String HxzFactory, String HxzId, String Latitude, String Longitude) {
        JSONObject gjson = new JSONObject();
        gjson.put("HxzFactory", HxzFactory);//黑匣子厂家
        gjson.put("HxzId", HxzId);//黑匣子编号
        gjson.put("Latitude", Latitude);//纬度
        gjson.put("Longitude", Longitude);//经度
        gjson.put("appid", appid);//
        gjson.put("appsecret", appsecret);//
        String str = gjson.toJSONString();
        try {
            String strReturn = deyeClinet.postCustomerAPI(str, "/zhgd/GpsData");

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 上报设备运行时长
     */
    public void RuntimeData(String HxzFactory, String HxzId, String OnlineTime, String DownlineTime,
            String RunTime) {
        JSONObject gjson = new JSONObject();
        gjson.put("HxzFactory", HxzFactory);//黑匣子厂家
        gjson.put("HxzId", HxzId);//黑匣子编号
        gjson.put("OnlineTime", OnlineTime);//上线时间时间YYYY-MM-DD HH:MM:SS
        gjson.put("DownlineTime", DownlineTime);//下线时间YYYY-MM-DD HH:MM:SS若在线则值为空
        gjson.put("RunTime", RunTime);//运行时长
        gjson.put("appid", appid);//
        gjson.put("appsecret", appsecret);//
        String str = gjson.toJSONString();
        try {
            String strReturn = deyeClinet.postCustomerAPI(str, "/zhgd/RuntimeData");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 上报2.6	上报报警时段数据
     */
    public void AlarmDataUnloadingPlatform(String HxzFactory,
            String HxzId,
            String StartTime,
            String EndTime,
            String AlarmType,
            String MaxValue,
            String MaxValuePercent) {
        JSONObject gjson = new JSONObject();
        gjson.put("HxzFactory", HxzFactory);//  数据时间
        gjson.put("HxzId", HxzId);//
        gjson.put("StartTime", StartTime);//
        gjson.put("EndTime", EndTime);//
        gjson.put("AlarmType", AlarmType);//
        gjson.put("MaxValue", MaxValue);//
        gjson.put("MaxValuePercent", MaxValuePercent);//
        gjson.put("appid", appid);//
        gjson.put("appsecret", appsecret);//
        try {
            String strReturn = deyeClinet
                    .postCustomerAPI(gjson.toJSONString(), "/zhgd/AlarmDataUnloadingPlatform");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    /**
     * 2.7	上报报警时刻数据
     */
    public void AlarmStartUnloadingPlatform(String HxzFactory,
            String HxzId,
            String StartTime,
            String AlarmType,
            String AlarmValue) {
        JSONObject gjson = new JSONObject();
        gjson.put("HxzFactory", HxzFactory);//  数据时间
        gjson.put("HxzId", HxzId);//
        gjson.put("StartTime", StartTime);//
        gjson.put("AlarmType", AlarmType);//
        gjson.put("AlarmValue", AlarmValue);//
        gjson.put("appid", appid);//
        gjson.put("appsecret", appsecret);//
        try {
            String strReturn = deyeClinet
                    .postCustomerAPI(gjson.toJSONString(), "/zhgd/AlarmStartUnloadingPlatform");

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}
