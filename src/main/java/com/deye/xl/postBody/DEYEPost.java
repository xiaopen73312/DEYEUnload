package com.deye.demo.postBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deye.demo.entity.AlarmDataElevator;
import com.deye.demo.entity.BaseDataElevator;
import com.deye.demo.entity.RealtimeDataElevator;
import com.deye.demo.entity.WorkDataElevator;
import com.deye.demo.httpService.DeyeClinet;
import com.deye.demo.request.AlarmChangeRequest;
import com.deye.demo.response.PostResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 升降机通用接口POST
 */
@Component
public class DEYEPostSJJ {

    private static final Logger log = LoggerFactory.getLogger(DEYEPostSJJ.class);
    @Autowired
    DeyeClinet deyeClinet;
    @Autowired
    Gson gson;

    /**
     * 上报注册信息
     */
    public void postLoginDataElevator(String HxzFactory, String HxzId,
            String HardwareVer, String SoftwareVer, String SimCardNo, String Type)
            throws Exception {

        JSONObject gjson = new JSONObject();
        gjson.put("HxzFactory", HxzFactory);//黑匣子厂家
        gjson.put("HxzId", HxzId);//黑匣子编号
        gjson.put("HardwareVer", HardwareVer);//硬件版本号
        gjson.put("SoftwareVer", SoftwareVer);//软件版本号
        gjson.put("SimCardNo", SimCardNo);//SIM卡号
        gjson.put("Type", Type);//设备类型
//        gjson.put("id", id);//设备唯一id
        String str = gjson.toJSONString();
        try {
            String strReturn = deyeClinet.postCustomerAPI(str, "/zhgd/LoginDataElevator");
//            PostResponse postResponse = gson.fromJson(strReturn, PostResponse.class);
//            String status = postResponse.getStatus();
        } catch (Exception e) {
            log.error(e.toString());
        }


    }

    /**
     * 上报升降机基础参数
     */
    public void BaseDataElevator(BaseDataElevator baseDataCrane) {
        String str = gson.toJson(baseDataCrane);
        JSONObject tmpstrsonObject = JSONArray.parseObject(str);
        String ElevatorType = tmpstrsonObject.getString("CraneType");
        tmpstrsonObject.remove("rowId");
        tmpstrsonObject.put("ElevatorType", ElevatorType);

        str = tmpstrsonObject.toString();
        try {
            String strReturn = deyeClinet.postCustomerAPI(str, "/zhgd/BaseDataElevator");
            PostResponse postResponse = gson.fromJson(strReturn, PostResponse.class);
            String status = postResponse.getStatus();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 上报升降机实时数据
     */
    public void RealtimeDataElevator(RealtimeDataElevator realtimeDataElevator) {
        String str = gson.toJson(realtimeDataElevator);
        JSONObject tmpstrsonObject = JSONArray.parseObject(str);
        tmpstrsonObject.remove("rowId");
        str = tmpstrsonObject.toString();
        try {
            String strReturn = deyeClinet.postCustomerAPI(str, "/zhgd/RealtimeDataElevator");
            PostResponse postResponse = gson.fromJson(strReturn, PostResponse.class);
            String status = postResponse.getStatus();
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
        String str = gjson.toJSONString();
        try {
            String strReturn = deyeClinet.postCustomerAPI(str, "/zhgd/GpsData");
            PostResponse postResponse = gson.fromJson(strReturn, PostResponse.class);
            String status = postResponse.getStatus();
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
        String str = gjson.toJSONString();
        try {
            String strReturn = deyeClinet.postCustomerAPI(str, "/zhgd/RuntimeData");
            PostResponse postResponse = gson.fromJson(strReturn, PostResponse.class);
            String status = postResponse.getStatus();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 上报 工作循环
     */
    public void WorkDataElevator(WorkDataElevator workDataCrane) {
        String str = gson.toJson(workDataCrane);
        JSONObject tmpstrsonObject = JSONArray.parseObject(str);
        tmpstrsonObject.remove("rowId");
        str = tmpstrsonObject.toString();
        try {
            String strReturn = deyeClinet.postCustomerAPI(str, "/zhgd/WorkDataElevator");
            PostResponse postResponse = gson.fromJson(strReturn, PostResponse.class);
            String status = postResponse.getStatus();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 上报 报警
     */
    public void AlarmDataElevator(AlarmDataElevator alarmDataElevator) {
        String str = gson.toJson(alarmDataElevator);
        JSONObject tmpstrsonObject = JSONArray.parseObject(str);
        tmpstrsonObject.remove("rowId");
        str = tmpstrsonObject.toString();
        try {
            String strReturn = deyeClinet.postCustomerAPI(str, "/zhgd/AlarmDataElevator");
            PostResponse postResponse = gson.fromJson(strReturn, PostResponse.class);
            String status = postResponse.getStatus();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 上报司机打卡记录信息
     */
    public void OperatorRecord(String HxzFactory, String HxzId, int Type, int WorkNo, String Name,
            String IdNo, String PunchTime, String ClosingTime) {
        JSONObject gjson = new JSONObject();
        gjson.put("HxzFactory", HxzFactory);//黑匣子厂家
        gjson.put("HxzId", HxzId);//黑匣子编号
        gjson.put("Type", Type);//设备类型1：塔机2：升降机3：扬尘监测4：卸料平台
        gjson.put("WorkNo", WorkNo);//
        gjson.put("Name", Name);//
        gjson.put("IdNo", IdNo);//身份证号
        gjson.put("PunchTime", PunchTime);//打卡（上机）时间YYYY-MM-DD HH:MM:SS
        gjson.put("ClosingTime", ClosingTime);//下班（关机）时间YYYY-MM-DD HH:MM:SS
        String str = gjson.toJSONString();
        try {
            String strReturn = deyeClinet.postCustomerAPI(str, "/zhgd/OperatorRecord");
            PostResponse postResponse = gson.fromJson(strReturn, PostResponse.class);
            String status = postResponse.getStatus();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    /**
     * 上报心跳数据
     */
    public void HeartBeatDataCrane(String HxzFactory, String HxzId, float WindSpeed, int WindLevel,
            String WindSpeedAlarm,
            int WorkNo, String Name, String IdNo) {
        JSONObject gjson = new JSONObject();
        gjson.put("HxzFactory", HxzFactory);//黑匣子厂家
        gjson.put("HxzId", HxzId);//黑匣子编号
        gjson.put("WindSpeed", WindSpeed);//风速
        gjson.put("WindLevel", WindLevel);//风级
        gjson.put("WindSpeedAlarm", WindSpeedAlarm);//风速报警
        gjson.put("WorkNo", WorkNo);//
        gjson.put("Name", Name);//
        gjson.put("IdNo", IdNo);//
        String str = gjson.toJSONString();
        try {
            String strReturn = deyeClinet.postCustomerAPI(str, "/zhgd/HeartBeatDataCrane");
            PostResponse postResponse = gson.fromJson(strReturn, PostResponse.class);
            String status = postResponse.getStatus();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 上报报警状态改变数据
     */
    public void AlarmChangeDataCrane(AlarmChangeRequest alarmChangeRequest) {
        String str = gson.toJson(alarmChangeRequest);
        JSONObject tmpstrsonObject = JSONArray.parseObject(str);
        tmpstrsonObject.remove("rowId");
        str = tmpstrsonObject.toString();
        try {
            String strReturn = deyeClinet.postCustomerAPI(str, "/zhgd/AlarmChangeDataCrane");
            PostResponse postResponse = gson.fromJson(strReturn, PostResponse.class);
            String status = postResponse.getStatus();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}
