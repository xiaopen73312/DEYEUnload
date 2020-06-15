package com.deye.xl.postBody;

import com.alibaba.fastjson.JSONObject;
import com.deye.xl.entity.Project;
import com.deye.xl.entity.TowerCrane;
import com.deye.xl.entity.XLBaseData;
import com.deye.xl.entity.XLControlData;
import com.deye.xl.httpService.GLDClinet;
import com.deye.xl.manager.ProjectManager;
import com.deye.xl.manager.TowerCraneManager;
import com.deye.xl.manager.XLBaseDataManager;
import com.deye.xl.manager.XLControlDataManager;
import com.deye.xl.request.XLRealtimeDataRequest;
import com.deye.xl.util.DateUtils;
import com.deye.xl.util.NullUtil;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 广联达 独立接口POST
 */
@Component
@PropertySource(value = "CustomerConfig.properties")
public class GLDPost {

    private static final Logger log = LoggerFactory.getLogger(GLDPost.class);
    @Autowired
    GLDClinet gldClinet;
    @Autowired
    TowerCraneManager towerCraneManager;
    @Autowired
    XLControlDataManager xlControlDataManager;
    @Autowired
    XLBaseDataManager xlBaseDataManager;
    @Autowired
    ProjectManager projectManager;
    @Value("${gld.Bearer.Toke}")
    private String BearerToke;//项目 toke


    /**
     * 卸料平台
     */
    public String postElvatorWorkdata(XLRealtimeDataRequest xlRealtimeDataRequest)
            throws Exception {
        String HxzId = xlRealtimeDataRequest.getHxzId();
        String HxzFactory = xlRealtimeDataRequest.getHxzFactory();
        TowerCrane towerCrane = towerCraneManager
                .getTowerCraneByHxzIdAndHxzFactory(HxzFactory, HxzId);
        String nickname = towerCrane.getName();
        Project project = projectManager.getProjectById(towerCrane.getProject_id().toString());
        String Premark = project.getRemark();
        Premark = "{" + Premark + "}";
        JSONObject myJson = JSONObject.parseObject(Premark);
        String Ttoke = myJson.getString("卸料访问令牌");
//        List<String> pjlist = SubStr.getRamk(Premark);
        if (NullUtil.isNotEmpty(Ttoke)) {
//            TBearerToke = Ttoke;
            Ttoke = "Bearer" + " " + Ttoke;
        } else {
            Ttoke = BearerToke;
        }
        log.info("Ttoke={}", Ttoke);
        XLControlData xlControlData = xlControlDataManager
                .getEntity(HxzFactory, HxzId);
        XLBaseData xlBaseData = xlBaseDataManager.getEntity(HxzFactory, HxzId);
        Integer interval = xlControlData.getWorkInterval();
        String time = DateUtils.getSSZZDate();
        String alarmAngleX = xlRealtimeDataRequest.getObliguityXStatus();//倾角X状态 0正常  1预警  2报警  3故障
        String alarmAngleY = xlRealtimeDataRequest.getObliguityYStatus();//倾角X状态 0正常  1预警  2报警  3故障
        String alarmMainLoad = xlRealtimeDataRequest.getWeight1Status();//载重1状态 0正常  1预警  2报警  3故障
//        String alarmSubLoad = xlRealtimeDataRequest.getWeight2Status();//载重2状态 0正常  1预警  2报警  3故障
        String GpsStatus = xlRealtimeDataRequest.getGpsStatus();//
        String BatteryStatus = xlRealtimeDataRequest
                .getBatteryStatus();//char(1)电池状态 0正常  1预警  2报警  3故障
        String Wireless1Status = xlRealtimeDataRequest
                .getWireless1Status();//	char(1)	无线连接1状态0正常  1预警  2报警  3故障
        Float angleX = xlRealtimeDataRequest.getObliguityX();//
        Float angleY = xlRealtimeDataRequest.getObliguityY();//
        Boolean isAlarm = false;
        if (!"0".equals(alarmAngleX) || !"0".equals(alarmAngleY) || !"0".equals(alarmMainLoad)
                || !"0".equals(GpsStatus) || !"0".equals(BatteryStatus) || !"0"
                .equals(Wireless1Status)) {
            isAlarm = true;
        }
        Float mainLoad = xlRealtimeDataRequest.getWeight();

        Float loadPercent = xlRealtimeDataRequest.getWeightPercent();
        JSONObject gjson = new JSONObject();
        gjson.put("uuid", "DEYE" + HxzId);// 设备标识
        gjson.put("time", time);//  数据时间
        gjson.put("interval", interval);// 数据周期

        JSONObject rjson = new JSONObject();
        rjson.put("alarmAngleX", Integer.valueOf(alarmAngleX));//  Integer 否 X倾角报警级别 0-正常;1-预警;2-报警

        rjson.put("alarmAngleY", Integer.valueOf(alarmAngleY));// Integer 否 Y倾角报警级别 0-正常;1-预警;2-报警

        rjson.put("alarmMainLoad", Integer.valueOf(alarmMainLoad));//主绳报警级别 0-正常;1-预警;2-报警

        rjson.put("alarmSubLoad", 0);//  否 副绳报警级别 0-正常;1-预警;2-报警

        rjson.put("angleX", angleX);// X倾角 单位°

        rjson.put("angleY", angleY);// Y倾角 单位°

        rjson.put("isAlarm", isAlarm);// Boolean 否 是否报警
        rjson.put("mainLoad", Math.abs(mainLoad));// Float 否 主绳载重 单位t

        rjson.put("online", true);//  Boolean 否 是否在线
        rjson.put("subLoad", 0.f);//  Float 否 副绳载重 单位t

        rjson.put("loadPercent", loadPercent);//  Float 否 重量百分比 单位%
        gjson.put("current", rjson);// 实时数据
        String ratedAngleX = xlBaseData.getObliguityXAlarmValue();
        String ratedAngleY = xlBaseData.getObliguityYAlarmValue();
        String ratedMainLoad = xlBaseData.getWeightAlarmValue();
        JSONObject mjson = new JSONObject();
        mjson.put("org_name", "DEYE");// 厂商简称 监测设备厂商简称
        mjson.put("nickname", nickname);// 设备昵称 厂商定义

        mjson.put("ratedAngleX", Float.valueOf(ratedAngleX));//Float X倾角额定值 单位°

        mjson.put("ratedAngleY", Float.valueOf(ratedAngleY));//Float  Y倾角额定值 单位°

        mjson.put("ratedMainLoad", Float.valueOf(ratedMainLoad));//Float  主绳额定载重 单位t
//        mjson.put("ratedSubLoad", 0.f);//Float  副绳额定载重
        gjson.put("metadata", mjson);// 静态数据

        List plist = new ArrayList();
        plist.add(gjson);

        String str = JSONObject.toJSONString(plist);
        try {

            String strReturn = gldClinet.postCustomerAPI(str,
                    "events", Ttoke);
            return strReturn;
        } catch (Exception e) {
            log.error(e.toString());
            return "";
        }

    }

}
