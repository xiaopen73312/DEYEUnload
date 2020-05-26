package com.deye.xl.postBody;

import com.alibaba.fastjson.JSON;
import com.deye.xl.entity.GpsData;
import com.deye.xl.entity.XLBaseData;
import com.deye.xl.manager.GpsDataManager;
import com.deye.xl.request.XLRealtimeDataRequest;
import com.yzw.ibuild.open.api.IbuildOpenClient;
import com.yzw.ibuild.open.api.model.unloadingplatform.UnloadingPlatformDeviceParams;
import com.yzw.ibuild.open.api.model.unloadingplatform.UnloadingPlatformLiveData;
import com.yzw.ibuild.open.api.request.UnloadingPlatformDeviceParamsUploadRequest;
import com.yzw.ibuild.open.api.request.UnloadingPlatformLiveDataUploadRequest;
import com.yzw.ibuild.open.api.response.IbuildOpenResponse;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 筑智云
 */
@Component
@PropertySource(value = "CustomerConfig.properties")
public class ZZYPost {

    private static final Logger log = LoggerFactory.getLogger(ZZYPost.class);
    @Value("${zzy.appid}")
    private String appid;
    @Value("${zzy.appsecret}")
    private String appsecret;
    @Autowired
    GpsDataManager gpsDataManager;
    /**
     * 基础参数
     */
    public void BaseDataUnloadingPlatform(XLBaseData baseDataCrane) {
        String HxzFactory = baseDataCrane.getHxzFactory();
        String HxzId = baseDataCrane.getHxzId();
        GpsData gpsData = gpsDataManager.getGpsData(HxzFactory, HxzId);
        String Longitude = gpsData.getLongitude();
        String Latitude = gpsData.getLatitude();
        Double LoadWeight = Double.valueOf(baseDataCrane.getWeightAlarmValue());
        Double MaxDipAngleX = Double.valueOf(baseDataCrane.getObliguityXAlarmValue());
        Double MaxDipAngleY = Double.valueOf(baseDataCrane.getObliguityYAlarmValue());
        Double EarlyWarningDipAngle = Double.valueOf(baseDataCrane.getObliguityXPreAlarmValue());
        Double WarningDipAngle = Double.valueOf(baseDataCrane.getObliguityYPreAlarmValue());
        UnloadingPlatformDeviceParamsUploadRequest request = new UnloadingPlatformDeviceParamsUploadRequest();
        UnloadingPlatformDeviceParams unloadingPlatformDeviceParams = new UnloadingPlatformDeviceParams();
        unloadingPlatformDeviceParams.setDeviceId(HxzFactory + HxzId);
        unloadingPlatformDeviceParams.setLongitude(Double.valueOf(Longitude));//设备所在经度
        unloadingPlatformDeviceParams.setLatitude(Double.valueOf(Latitude));//设备所在纬度
        unloadingPlatformDeviceParams.setStdLoadWeight(LoadWeight);//标准载重，单位：吨，支持2位小数
        unloadingPlatformDeviceParams.setMaxDipAngleX(MaxDipAngleX);//最大倾角X，单位：度，支持1位小数
        unloadingPlatformDeviceParams.setMaxDipAngleY(MaxDipAngleY);//最大倾角Y，单位：度，支持1位小数
        unloadingPlatformDeviceParams.setEarlyWarningCoefficient(80.0);//预警系数，单位：%，支持2位小数
        unloadingPlatformDeviceParams.setWarningCoefficient(100.0);//报警系数，单位：%，支持2位小数
        unloadingPlatformDeviceParams
                .setEarlyWarningDipAngle(EarlyWarningDipAngle);//倾角预警值，单位：度，支持1位小数
        unloadingPlatformDeviceParams.setWarningDipAngle(WarningDipAngle);//倾角报警值，单位：度，支持1位小数
        request.setUnloadingPlatformDeviceParams(unloadingPlatformDeviceParams);
        try {
            IbuildOpenClient client = new IbuildOpenClient(appid, appsecret);
            IbuildOpenResponse response = client.execute(request);
            System.out.println(JSON.toJSONString(response));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 实时数据
     */
    public void RealtimeDataElevator(XLRealtimeDataRequest xlRealtimeData) {
        String HxzFactory = xlRealtimeData.getHxzFactory();
        String HxzId = xlRealtimeData.getHxzId();
        Double LoadWeight = Double.valueOf(xlRealtimeData.getWeight());
        Double DipAngleX = Double.valueOf(xlRealtimeData.getObliguityX());
        Double DipAngleY = Double.valueOf(xlRealtimeData.getObliguityY());
        String END = "0000";
        String Star = "";
        String Weight1Status = xlRealtimeData.getWeight1Status();
        //载重1状态 0正常  1预警  2报警  3故障
        String Weight2Status = xlRealtimeData.getWeight2Status();
        //预警
        if ("1".equals(Weight1Status) || "1".equals(Weight2Status)) {
            Star = "1";
        } else {
            Star = "0";
        }
        //报警
        if ("2".equals(Weight1Status) || "2".equals(Weight2Status)) {
            Star = "1" + Star;
        } else {
            Star = "0" + Star;
        }
        String ObliguityXStatus = xlRealtimeData.getObliguityXStatus();
        String ObliguityYStatus = xlRealtimeData.getObliguityYStatus();
        //预警
        if ("1".equals(ObliguityXStatus) || "1".equals(ObliguityXStatus)) {
            Star = "1" + Star;
        } else {
            Star = "0" + Star;
        }
        //报警
        if ("2".equals(ObliguityXStatus) || "2".equals(ObliguityXStatus)) {
            Star = "1" + Star;
        } else {
            Star = "0" + Star;
        }
        UnloadingPlatformLiveDataUploadRequest request
                = new UnloadingPlatformLiveDataUploadRequest();
        UnloadingPlatformLiveData unloadingPlatformLiveData = new UnloadingPlatformLiveData();
        unloadingPlatformLiveData.setDeviceId(HxzFactory + HxzId);
        unloadingPlatformLiveData.setDeviceTime(new Date());
        unloadingPlatformLiveData.setWarning(END + Star);

        unloadingPlatformLiveData.setLoadWeight(LoadWeight);
        unloadingPlatformLiveData.setDipAngleX(DipAngleX);
        unloadingPlatformLiveData.setDipAngleY(DipAngleY);
        request.setUnloadingPlatformLiveData(unloadingPlatformLiveData);
        try {
            IbuildOpenClient client = new IbuildOpenClient(appid, appsecret);
            IbuildOpenResponse response = client.execute(request);
            System.out.println(JSON.toJSONString(response));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

//    public static void main(String[] args) {
//        IbuildOpenClient client = new IbuildOpenClient("JGZZFVQPKL", "N4ZEF5EKFT2JLYPW2AUU");
//        UnloadingPlatformDeviceParamsUploadRequest request = new UnloadingPlatformDeviceParamsUploadRequest();
//        UnloadingPlatformDeviceParams unloadingPlatformDeviceParams = new UnloadingPlatformDeviceParams();
//        unloadingPlatformDeviceParams.setDeviceId("SDBG20051325");
//        unloadingPlatformDeviceParams.setStdLoadWeight(50.0);
//        request.setUnloadingPlatformDeviceParams(unloadingPlatformDeviceParams);
//        IbuildOpenResponse response = client.execute(request);
//        System.out.println(JSON.toJSONString(response));
//    }
}
