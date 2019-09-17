package com.deye.xl.tcp.server;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deye.xl.entity.GpsData;
import com.deye.xl.entity.IpData;
import com.deye.xl.entity.LoginData;
import com.deye.xl.entity.RuntimeData;
import com.deye.xl.entity.TowerCrane;
import com.deye.xl.entity.XLBaseData;
import com.deye.xl.entity.XLControlData;
import com.deye.xl.httpService.BaiduClient;
import com.deye.xl.manager.GpsDataManager;
import com.deye.xl.manager.IpDataManager;
import com.deye.xl.manager.LoginDataManager;
import com.deye.xl.manager.RuntimeDataManager;
import com.deye.xl.manager.TowerCraneManager;
import com.deye.xl.manager.XLAlarmDataManager;
import com.deye.xl.manager.XLAlarmDataStartManager;
import com.deye.xl.manager.XLBaseDataManager;
import com.deye.xl.manager.XLControlDataManager;
import com.deye.xl.manager.XLRealtimeDataManager;
import com.deye.xl.request.XLRealtimeDataRequest;
import com.deye.xl.request.XLRegisterRequest;
import com.deye.xl.util.DateUtils;
import com.deye.xl.util.NullUtil;
import com.deye.xl.util.RegexMatches;
import com.deye.xl.util.SubStr;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;


/**
 * description: author:jackie date: 2019.8.1
 **/
@Component
@ChannelHandler.Sharable

public class ServerChannelHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger log = LoggerFactory.getLogger(ServerChannelHandler.class);
    @Value("${netty.tcp.server.port}")
    int PORT;

    @Autowired
    IpDataManager ipDataManager;
    @Autowired
    LoginDataManager loginDataManager;

    @Autowired
    GpsDataManager gpsDataManager;
    @Autowired
    XLControlDataManager xlControlDataManager;
    @Autowired
    RuntimeDataManager runtimeDataManager;
    @Autowired
    XLRealtimeDataManager xlRealtimeDataManager;
    @Autowired
    XLBaseDataManager xlBaseDataManager;
    @Autowired
    TowerCraneManager towerCraneManager;
    @Autowired
    BaiduClient baiduClient;

    @Autowired
    XLAlarmDataManager xlAlarmDataManager;
    @Autowired
    XLAlarmDataStartManager xlAlarmDataStartManager;
    /**
     * 拿到传过来的msg数据，开始处理
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        String strMsg = msg.toString().trim();

        String clientIP = getIPString(ctx);//客户端ip地址
        clientIP = SubStr.getip(clientIP);

        String ReturnServerIp = "";//服务器获取IP地址
        String ReturnServerPort = "";//服务器端口号

        log.info("收到Received message:" + strMsg);
        // 字符串校验头尾 &！
        boolean isMsg = RegexMatches.regexMes(strMsg);
        log.info("是否满意isFormat(&!):" + isMsg);
        //当符合规则时
        if (isMsg) {

            //字符串提取 $
            strMsg = strMsg.substring(1, strMsg.length() - 1);
            String[] strings = SubStr.get(strMsg);
            int msgLength = strings.length;
            String HxzFactory = strings[1];//黑匣子厂家
            String HxzId = strings[2];//黑匣子编号
            String ProtocolVer = strings[3];//版本号
            String ChildProtocol = ProtocolVer.substring(3, 4);//子类型 G F
            String VerNum = ProtocolVer.substring(1, 2);//设备类型 1升降机  塔机2 3扬尘 4 卸料
            String Latitude = "0.000000";
            String Longitude = "0.000000";
            log.info("设备类型VerNum:" + VerNum);
            String cmd = strings[4];      //提取命令 更具命令分类 cmd
            log.info("cmd:" + cmd);
            String online_flag = "0";
            boolean isBA = false;//是否备案
            LoginData loginDataAll = loginDataManager
                    .getLoginDataByHxzIdAndHxzFactory(HxzFactory, HxzId);
            log.info("select loginData where HxzFactory={},HxzId={}", HxzFactory, HxzId);
            if (NullUtil.isNotEmpty(loginDataAll) && NullUtil
                    .isNotEmpty(loginDataAll.getCraneId())) {
                online_flag = loginDataAll.getOnLine();  //获取是否在线 在线才出来 除注册针以为的命令
                isBA = true;
                log.info("online_flag={},isBA={}", online_flag, isBA);
            }
            switch (cmd) {
                case "0":
                    log.info("注册:");
                    XLRegisterRequest xlRegisterRequest = new XLRegisterRequest(HxzFactory, HxzId,
                            ProtocolVer, cmd,
                            strings[5], strings[6], strings[7],
                            strings[8], strings[9], strings[10],
                            strings[11], strings[12], strings[13],
                            strings[14], strings[15], strings[16],
                            strings[17], strings[18],
                            Integer.valueOf(strings[19]), strings[20],
                            strings[21],
                            strings[22], Integer.valueOf(strings[23]), strings[24],
                            strings[25], strings[26]);
                    //根据黑匣子查询 ip数据是否存在
                    chackIpData(HxzFactory, HxzId, clientIP);
                    if (VerNum.equals("4")) {
                        XLBaseData xlBaseDataNew = new XLBaseData(HxzFactory, HxzId,
                                xlRegisterRequest.getUnloaderType(),
                                xlRegisterRequest.getWirelessExist(),
                                xlRegisterRequest.getWeight2SetExist(),
                                xlRegisterRequest.getObliguityXExist(),
                                xlRegisterRequest.getObliguityYExist(),
                                xlRegisterRequest.getGPSExist(),
                                xlRegisterRequest.getWirelessExist(),
                                xlRegisterRequest.getWeight1Disabled(),
                                xlRegisterRequest.getWeight2Disabled(),
                                xlRegisterRequest.getObliguityXDisabled(),
                                xlRegisterRequest.getObliguityYDisabled(),
                                xlRegisterRequest.getWeightPreAlarmValue().trim(),
                                xlRegisterRequest.getObliguityXPreAlarmValue().trim(),
                                xlRegisterRequest.getObliguityYPreAlarmValue().trim(),
                                xlRegisterRequest.getBatteryPreAlarmValue().toString().trim(),
                                xlRegisterRequest.getWeightAlarmValue().trim(),
                                xlRegisterRequest.getObliguityXAlarmValue().trim(),
                                xlRegisterRequest.getObliguityYAlarmValue().trim(),
                                xlRegisterRequest.getBatteryAlarmValue().toString().trim());
                        XLBaseData xlBaseData = xlBaseDataManager.getEntity(HxzFactory, HxzId);
                        log.info("getXLbaseDate by HxzFactory={}  HxzId={}", HxzFactory, HxzId);
                        if (NullUtil.isNotEmpty(xlBaseData)) {
                            xlBaseDataNew.setRowId(xlBaseData.getRowId());
                            xlBaseDataManager.save(xlBaseDataNew);
                            log.info("update XLbaseDate by HxzFactory={}  HxzId={}", HxzFactory,
                                    HxzId);
                        } else {
                            xlBaseDataManager.save(xlBaseDataNew);
                            log.info("save new XLbaseDate by HxzFactory={}  HxzId={}", HxzFactory,
                                    HxzId);
                        }
                        //根据HxzFactory和HxzId 查询LoginData 是否存在CraneId 已备案时
                        if (isBA) {
                            //更新数据 设置OnLine='1'
                            loginDataManager
                                    .upDateLoginData(xlRegisterRequest.getHardwareVer().trim(),
                                            xlRegisterRequest.getSoftwareVer().trim(),
                                            xlRegisterRequest.getSimCardNo().trim(),
                                            HxzFactory,
                                            HxzId, "1", 4);
                            log.info("upDateLoginData:OnLine='1' HxzFactory:{},HxzId:{}",
                                    HxzFactory,
                                    HxzId);
                            //不在线插入一条新的在线数据 设置在线时间为当前时间 下线时间为空
                            if ("0".equals(online_flag)) {
                                //向RuntimeData_CraneId 插入数据
                                RuntimeData runtimeData = new RuntimeData(HxzFactory, HxzId,
                                        DateUtils.getCurrentTime(),
                                        "", "0");
                                runtimeDataManager.save(runtimeData);
                                log.info("insert NEW INTO RunTimeData ", HxzFactory, HxzId);

                            }
                            String HeartBeatInterval = "";
                            String WorkInterval = "";
                            String NoWorkInterval = "";
                            XLControlData xlControlData = xlControlDataManager
                                    .getEntity(HxzFactory, HxzId);
                            log.info("select  xlControlData HxzFactory:{},HxzId:{}", HxzFactory,
                                    HxzId);

                            if (NullUtil.isNotEmpty(xlControlData)) {

                                xlControlData.setUnloaderType(xlRegisterRequest.getUnloaderType());
                                xlControlData
                                        .setWeight1SetExist(xlRegisterRequest.getWeight1SetExist());
                                xlControlData
                                        .setWeight1SetExist(xlRegisterRequest.getWeight1SetExist());
                                xlControlData
                                        .setObliguityXExist(xlRegisterRequest.getObliguityXExist());
                                xlControlData
                                        .setObliguityYExist(xlRegisterRequest.getObliguityYExist());
                                xlControlData.setGPSExist(xlRegisterRequest.getGPSExist());
                                xlControlData
                                        .setWirelessExist(xlRegisterRequest.getWirelessExist());
                                xlControlData
                                        .setWeight1Disabled(xlRegisterRequest.getWeight1Disabled());
                                xlControlData
                                        .setWeight2Disabled(xlRegisterRequest.getWeight2Disabled());
                                xlControlData.setObliguityXDisabled(
                                        xlRegisterRequest.getObliguityXDisabled());
                                xlControlData.setObliguityYDisabled(
                                        xlRegisterRequest.getObliguityYDisabled());
                                xlControlData.setWeightPreAlarmValue(
                                        xlRegisterRequest.getWeightPreAlarmValue().toString()
                                                .trim());
                                xlControlData.setObliguityXPreAlarmValue(
                                        xlRegisterRequest.getObliguityXPreAlarmValue().toString()
                                                .trim());
                                xlControlData.setObliguityYPreAlarmValue(
                                        xlRegisterRequest.getObliguityYPreAlarmValue().toString()
                                                .trim());
                                xlControlData.setBatteryPreAlarmValue(
                                        xlRegisterRequest.getBatteryPreAlarmValue().toString()
                                                .trim());
                                xlControlData.setWeightAlarmValue(
                                        xlRegisterRequest.getWeightAlarmValue().toString().trim());
                                xlControlData.setObliguityXAlarmValue(
                                        xlRegisterRequest.getObliguityXAlarmValue().toString()
                                                .trim());
                                xlControlData.setObliguityYAlarmValue(
                                        xlRegisterRequest.getObliguityYAlarmValue().toString()
                                                .trim());
                                xlControlData.setBatteryAlarmValue(
                                        xlRegisterRequest.getBatteryAlarmValue().toString().trim());

                                xlControlDataManager.save(xlControlData);
                                log.info("UPdate  xlControlData HxzFactory:{},HxzId:{}", HxzFactory,
                                        HxzId);
                                ReturnServerIp = xlControlData.getServerIp().trim();
                                ReturnServerPort = xlControlData.getServerPort().trim();
                                //心跳
                                HeartBeatInterval = xlControlData.getHeartBeatInterval();

                                //补0
                                WorkInterval = xlControlData.getWorkInterval().toString();
                                if (Integer.valueOf(WorkInterval) < 10) {
                                    WorkInterval = "0" + WorkInterval;
                                }
                                NoWorkInterval = xlControlData.getNoWorkInterval().toString();
                                if (Integer.valueOf(NoWorkInterval) < 10) {
                                    NoWorkInterval = "0" + NoWorkInterval;
                                }
                            }

                            String Result =
                                    "*$" + HxzFactory + "$" + HxzId + "$" + ProtocolVer
                                            + "$0$"
                                            + xlRegisterRequest.getUnloaderType() + "$"
                                            + xlRegisterRequest.getWeight1SetExist() + "$"
                                            + xlRegisterRequest.getWeight2SetExist() + "$"
                                            + xlRegisterRequest.getObliguityXExist() + "$"
                                            + xlRegisterRequest.getObliguityYExist() + "$"
                                            + xlRegisterRequest.getGPSExist() + "$"
                                            + xlRegisterRequest.getWirelessExist() + "$"
                                            + xlRegisterRequest.getWeight1Disabled() + "$"
                                            + xlRegisterRequest.getWeight2Disabled() + "$"
                                            + xlRegisterRequest.getObliguityXDisabled() + "$"
                                            + xlRegisterRequest.getObliguityYDisabled() + "$"
                                            + SubStr
                                            .getStr(xlRegisterRequest.getWeightPreAlarmValue())
                                            + "$"
                                            + SubStr
                                            .getStr(xlRegisterRequest.getObliguityXPreAlarmValue())
                                            + "$"
                                            + SubStr
                                            .getStr(xlRegisterRequest.getObliguityYPreAlarmValue())
                                            + "$"
                                            + xlRegisterRequest.getBatteryPreAlarmValue() + "$"
                                            + SubStr.getStr(xlRegisterRequest.getWeightAlarmValue())
                                            + "$"
                                            + SubStr
                                            .getStr(xlRegisterRequest.getObliguityXAlarmValue())
                                            + "$"
                                            + SubStr
                                            .getStr(xlRegisterRequest.getObliguityYAlarmValue())
                                            + "$"
                                            + xlRegisterRequest.getBatteryAlarmValue() + "$"
                                            + HeartBeatInterval + "$" + WorkInterval + "$"
                                            + NoWorkInterval + "$"
                                            + DateUtils.getYearStr() + "$"
                                            + DateUtils.getMonthStr() + "$" + DateUtils.getDayStr()

                                            + "$" + DateUtils.getHHStr() + "$" + DateUtils
                                            .getMMStr() + "$"
                                            + DateUtils.getSSStr() + "$" + ReturnServerIp + "$"
                                            + ReturnServerPort
                                            + "$@";
                            // 已备案返回数据
                            log.info("Result:" + Result);
                            // 通道channel绑定业务编号
                            AttributeKey<String> KEY_HXZFACTORY_ID = AttributeKey
                                    .valueOf("HxzFactory");
                            ctx.channel().attr(KEY_HXZFACTORY_ID).set(HxzFactory);
                            AttributeKey<String> KEY_HXZ_ID = AttributeKey.valueOf("HxzId");
                            ctx.channel().attr(KEY_HXZ_ID).set(HxzId);
                            ctx.channel().writeAndFlush(Result).syncUninterruptibly();
                        } else if (NullUtil.isNotEmpty(loginDataAll)) {
                            //有数据没有备份时更新 LoginData GpsData
                            loginDataManager.upDateLoginData(xlRegisterRequest.getHardwareVer(),
                                    xlRegisterRequest.getSoftwareVer(),
                                    xlRegisterRequest.getSimCardNo(), HxzFactory, HxzId, "1", 4);
                            log.info("update  upDateLoginData HxzFactory={},HxzId={}", HxzFactory,
                                    HxzId);
                            //更新GPS
                            gpsDataManager.upDateGps(HxzFactory, HxzId, 0, 0);
                            log.info("update  upDateGps HxzFactory={},HxzId={}", HxzFactory, HxzId);
                        } else {
                            //无数据时插入数据LoginData
                            LoginData loginData1 = new LoginData();
                            loginData1.setHxzFactory(xlRegisterRequest.getHxzFactory());
                            loginData1.setHxzId(xlRegisterRequest.getHxzId());
                            loginData1.setHardwareVer(xlRegisterRequest.getHardwareVer());
                            loginData1.setSoftwareVer(xlRegisterRequest.getSoftwareVer());
                            loginData1.setSimCardNo(xlRegisterRequest.getSimCardNo());
                            loginData1.setOnLine("1");
                            loginData1.setType(4);
                            loginDataManager.save(loginData1);
                            log.info("Insert  LoginData HxzFactory={},HxzId={}", HxzFactory, HxzId);
                            //插入数据到 GpsData
                            GpsData gpsData = new GpsData();
                            gpsData.setHxzFactory(xlRegisterRequest.getHxzFactory());
                            gpsData.setHxzId(xlRegisterRequest.getHxzId());
                            gpsData.setLocateLock(0);
                            gpsData.setWorkSiteNo(0);
                            gpsDataManager.save(gpsData);
                            log.info("Insert  gpsData HxzFactory={},HxzId={}", HxzFactory, HxzId);
                        }

                    }
                    break;
                case "2":
                    log.info("心跳:");
                    //为升降机时
                    if (VerNum.equals("4") && online_flag.equals("1")) {
                        String Result = "";
                        chackIpData(HxzFactory, HxzId, clientIP);
                        XLControlData xlControlData = xlControlDataManager
                                .getEntity(HxzFactory, HxzId);
                        log.info("GET XLControlData by HxzFactory={},HxzId={} ", HxzFactory, HxzId);
                        String ModifyServer = xlControlData.getModifyServer();
                        String GetFlag = xlControlData.getGetFlag();
                        String SendFlag = xlControlData.getSendFlag();
                        //
                        if (GetFlag.equals("1") || ModifyServer.equals("1")) {
                            Result = "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + '9'
                                    + "$@";

                        } else if (SendFlag.equals("1")) {
                            //if DataBaseFlag = 1 是否存公司数据库  then  pXL_ControlData
                            String DataBaseFlag = "1"; //存公司数据库
                            if (DataBaseFlag.equals("1")) {
//                                XL_ControlData
                                String HeartBeatInterval = xlControlData.getHeartBeatInterval();
                                if (Integer.valueOf(HeartBeatInterval) < 10) {
                                    HeartBeatInterval = "0" + HeartBeatInterval;
                                }
                                String BatteryPreAlarmValue = xlControlData
                                        .getBatteryPreAlarmValue();
                                if (Integer.valueOf(BatteryPreAlarmValue) < 10) {
                                    BatteryPreAlarmValue = "0" + BatteryPreAlarmValue;
                                }
                                String BatteryAlarmValue = xlControlData.getBatteryAlarmValue();
                                String WorkInterval = xlControlData.getWorkInterval().toString();
                                if (Integer.valueOf(WorkInterval) < 10) {
                                    WorkInterval = "0" + WorkInterval;
                                }
                                String NoWorkInterval = xlControlData.getNoWorkInterval()
                                        .toString();
                                if (Integer.valueOf(NoWorkInterval) < 10) {
                                    NoWorkInterval = "0" + NoWorkInterval;
                                }
                                String WeightPreAlarmValue = String
                                        .format("%.3d", Float.valueOf(
                                                xlControlData.getWeightPreAlarmValue()) * 100);
                                String ObliguityXPreAlarmValue = String
                                        .format("%.3d", Float.valueOf(
                                                xlControlData.getObliguityXPreAlarmValue()) * 100);
                                String ObliguityYPreAlarmValue = String
                                        .format("%.3d", Float.valueOf(
                                                xlControlData.getObliguityYPreAlarmValue()) * 100);

                                String WeightAlarmValue = String
                                        .format("%.3d", Float.valueOf(
                                                xlControlData.getWeightAlarmValue()) * 100);
                                String ObliguityXAlarmValue = String
                                        .format("%.3d", Float.valueOf(
                                                xlControlData.getObliguityXAlarmValue()) * 100);

                                String ObliguityYAlarmValue = String
                                        .format("%.3d", Float.valueOf(
                                                xlControlData.getObliguityYAlarmValue()) * 100);

//                                Weight1Zero:=Format5_100(Weight1Zero);
//                                Weight2Zero:=Format5_100(Weight2Zero);
//                                ObliguityXZero:=Format5_100(ObliguityXZero);
//                                ObliguityYZero:=Format5_100(ObliguityYZero);
                                String Weight1Zero = xlControlData.getWeight1Zero();
                                String ObliguityXZero = xlControlData.getObliguityXZero();
                                String ObliguityYZero = xlControlData.getObliguityYZero();
                                Result = "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer
                                        + "$6$"
                                        +
                                        xlControlData.getUnloaderType() + '$'
                                        + xlControlData
                                        .getWeight1SetExist()
                                        + '$' + xlControlData.getWeight2SetExist()
                                        + '$' + xlControlData.getObliguityXExist() + '$'
                                        + xlControlData.getObliguityYExist() + '$'
                                        + xlControlData.getGPSExist() + '$' +
                                        xlControlData.getWirelessExist() + '$'
                                        + xlControlData
                                        .getWeight1Disabled() + '$'
                                        + xlControlData.getWeight2Disabled() + '$'
                                        + xlControlData
                                        .getObliguityXDisabled() + '$' +
                                        xlControlData.getObliguityYDisabled() + '$'
                                        + WeightPreAlarmValue + '$'
                                        + ObliguityXPreAlarmValue + '$'
                                        + ObliguityYPreAlarmValue
                                        + '$' + BatteryPreAlarmValue + '$' +
                                        WeightAlarmValue + '$' + ObliguityXAlarmValue + '$'
                                        + ObliguityYAlarmValue + '$' + BatteryAlarmValue
                                        + '$' +
                                        HeartBeatInterval + '$' + WorkInterval + '$'
                                        + NoWorkInterval + '$'
                                        + DateUtils.getYearStr() + "$"
                                        + DateUtils.getMonthStr() + "$" + DateUtils
                                        .getDayStr()

                                        + "$" + DateUtils.getHHStr() + "$" + DateUtils
                                        .getMMStr() + "$"
                                        + DateUtils.getSSStr() + "$";
                                Result = Result + '$' + ReturnServerIp + '$' + ReturnServerPort;
                                Result = Result + '$' + Weight1Zero + '$' + ObliguityXZero + '$'
                                        + ObliguityYZero;
                                Result = Result + "$@";
                            } else {
                                Result = "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$'
                                        + cmd
                                        + "$@";
                            }

                        }
                        if (Result.equals("")) {
                            Result = "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd
                                    + "$@";
                        }

                        log.info("心跳Result:" + Result);
                        ctx.channel().writeAndFlush(Result).syncUninterruptibly();

                    }
                    break;
                case "3":
                    log.info("实时数据命令");
                    if (VerNum.equals("4") && online_flag.equals("1")) {
                        XLRealtimeDataRequest xlRealtimeDataRequest = new XLRealtimeDataRequest(
                                HxzFactory, HxzId, ProtocolVer,
                                cmd, strings[5], strings[6], Float.valueOf(strings[7]),
                                Float.valueOf(strings[8]),
                                Float.valueOf(strings[9]),
                                Float.valueOf(strings[10]), Float.valueOf(strings[11]),
                                Float.valueOf(strings[12]),
                                Float.valueOf(strings[13]),
                                Integer.valueOf(strings[14]), strings[15], strings[16],
                                strings[17], strings[18], strings[19],
                                strings[20], strings[21], strings[22]);
                        chackIpData(HxzFactory, HxzId, clientIP);
                        String RTime = strings[5] + ' ' + strings[6];

                        chackIpData(HxzFactory, HxzId, clientIP);

                        if (NullUtil.isNotEmpty(loginDataAll)) {
//                            String CraneId = loginDataAll.getCraneId();//
                            TowerCrane towerCrane = towerCraneManager
                                    .getTowerCraneByHxzIdAndHxzFactory(HxzFactory, HxzId);
                            BigDecimal LoginData_row_id = towerCrane.getLoginData_row_id();
                            BigDecimal projectid = towerCrane.getProject_id();
                            //更新runtimeData
                            runtimeDataManager
                                    .upXLRunTimeSql(RTime, HxzFactory,
                                            HxzId);
                            log.info("upXLRunTimeSql  set  RunTime {}", RTime);

                            xlRealtimeDataManager.saveSql(HxzFactory, HxzId,
                                    projectid,
                                    LoginData_row_id, RTime,
                                    xlRealtimeDataRequest.getWeight1().toString().trim(),
                                    xlRealtimeDataRequest.getWeight2().toString().trim(),
                                    xlRealtimeDataRequest.getWeight().toString().trim(),
                                    xlRealtimeDataRequest.getWeightPercent().toString().trim(),
                                    xlRealtimeDataRequest.getObliguityX().toString().trim(),
                                    xlRealtimeDataRequest.getObliguityY().toString().trim(),
                                    xlRealtimeDataRequest.getObliguity().toString().trim(),
                                    xlRealtimeDataRequest.getBatteryPercent(),
                                    xlRealtimeDataRequest.getWeight1Status(),
                                    xlRealtimeDataRequest.getWeight2Status(),
                                    xlRealtimeDataRequest.getObliguityXStatus(),
                                    xlRealtimeDataRequest.getObliguityYStatus(),
                                    xlRealtimeDataRequest.getWireless1Status(),
                                    xlRealtimeDataRequest.getWireless2Status(),
                                    xlRealtimeDataRequest.getGpsStatus(),
                                    xlRealtimeDataRequest.getBatteryStatus());
                        }
                        log.info("xlRealtimeDataManager.saveSql");
                        String Resultt =
                                "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd
                                        + "$@";
                        log.info("Result:" + Resultt);
                        ctx.channel().writeAndFlush(Resultt).syncUninterruptibly();
                    }
                    break;
                case "5":
                    log.info("GPS定位:");
                    log.info("定位");
                    Latitude = strings[5];
                    Longitude = strings[6];
                    String tmpstr = "";//偏移量

                    tmpstr = baiduClient.gettmpstr(Latitude, Longitude);
                    log.info("baidu tmpstr:{}", tmpstr);
                    ///Json字符串转Json对象 判断返回信息是否错误 值为0时，则为成功，非0时，则为失败

                    JSONArray jsonArray = JSONArray.parseArray(tmpstr);
                    JSONObject tmpstrsonObject = jsonArray.getJSONObject(0);
                    String error = tmpstrsonObject.getString("error");

                    if ("0".equals(error)) {
                        Latitude = tmpstrsonObject.getString("y");
                        byte[] bs1 = Base64Utils.decodeFromString(Latitude);
                        Latitude = new String(bs1);

                        Longitude = tmpstrsonObject.getString("x");
                        byte[] bs2 = Base64Utils.decodeFromString(Longitude);
                        Longitude = new String(bs2);
                        Latitude = String.format("%.6f", Float.valueOf(Latitude));
                        Longitude = String.format("%.6f", Float.valueOf(Longitude));
                        //检查IP数据
                        chackIpData(HxzFactory, HxzId, clientIP);

                        GpsData gpsData1 = gpsDataManager.getGpsData(HxzFactory, HxzId);
                        log.info("select from  GpsData HxzId:{}", HxzId);
                        int LocateLock;
                        if (NullUtil.isNotEmpty(gpsData1)) {
                            LocateLock = gpsData1.getLocateLock();
                            //更新
                            if (LocateLock == 0) {
                                gpsData1.setLatitude(Latitude);
                                gpsData1.setLongitude(Longitude);
                                gpsDataManager.save(gpsData1);
                                log.info("update   GpsData Latitude:{},Longitude:{}", Latitude,
                                        Longitude);
                            }
                        } else {
                            //插入新值
                            GpsData gpsData2 = new GpsData();
                            gpsData2.setLatitude(Latitude);
                            gpsData2.setLongitude(Longitude);
                            gpsData2.setHxzFactory(HxzFactory);
                            gpsData2.setHxzId(HxzId);
                            gpsData2.setWorkSiteNo(0);
                            gpsData2.setLocateLock(0);
                            gpsDataManager.save(gpsData2);
                            log.info("save   GpsData Latitude:{},Longitude:{}", Latitude,
                                    Longitude);
                        }
                        gpsDataManager.pGpsDataVF(HxzFactory,
                                HxzId,
                                Latitude,
                                Longitude,
                                clientIP,
                                DateUtils.getCurrentTime());
                        log.info("call pGpsDataVF Latitude={},Longitude={} ", Latitude, Longitude);
                    }
                    String Resultd =
                            "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd + "$@";
                    log.info("Result:" + Resultd);
                    ctx.channel().writeAndFlush(Resultd).syncUninterruptibly();

                    break;
                case "7":
                    log.info("报警时段数据:");

                    if (VerNum.equals("4") && online_flag.equals("1") && isBA) {
                        String t1 = strings[5];
                        String t2 = strings[6];
                        String StartTime = t1 + ' ' + t2;
                        String t11 = strings[7];
                        String t21 = strings[8];
                        String EndTime = t11 + ' ' + t21;
                        String AlarmType = strings[9];
                        String MaxValue = strings[10];
                        String MaxValuePercent = strings[11];
                        chackIpData(HxzFactory, HxzId, clientIP);

                        TowerCrane towerCrane = towerCraneManager
                                .getTowerCraneByHxzIdAndHxzFactory(HxzFactory, HxzId);
                        log.info("getTowerCraneByHxzFactory={}  HxzId={}", HxzFactory, HxzId);

                        BigDecimal Projectid = towerCrane.getProject_id();
                        BigDecimal LoginData_row_id = towerCrane.getLoginData_row_id();

                        xlAlarmDataManager.saveSql(HxzFactory,
                                HxzId,
                                Projectid.toString().trim(),
                                LoginData_row_id.toString().trim(),
                                StartTime,
                                EndTime,
                                AlarmType,
                                MaxValue,
                                MaxValuePercent);
                        log.info("xlAlarmDataManager.saveSql HxzFactory={},HxzId={}", HxzFactory,
                                HxzId);

                        String Result =
                                "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd
                                        + "$@";
                        log.info("Result:" + Result);
                        ctx.channel().writeAndFlush(Result).syncUninterruptibly();
                    }
                    break;
                case "8":
                    log.info("修改IP和端口:");
                    if (VerNum.equals("4") && online_flag.equals("1") && isBA) {
                        String IpString = strings[5];
                        String PortString = strings[6];
                        String CServerIp = "000.000.000.000";
                        String CServerPort = "0000";
                        XLControlData xlControlData = xlControlDataManager
                                .getEntity(HxzFactory, HxzId);
                        log.info("select xlControlData HxzFactory={},HxzId={}", HxzFactory,
                                HxzId);
                        if (NullUtil.isNotEmpty(xlControlData)) {
                            CServerIp = xlControlData.getServerIp();
                            CServerPort = xlControlData.getServerPort();
                        }
                        if (IpString.equals(CServerIp) && PortString.equals(CServerPort)) {
                            //调用pModifyServer UPDATE xlControlDataManager SET ModifyServer = '0'
                            xlControlDataManager.upModifyServer("0", "0", HxzFactory, HxzId);
                            log.info(" xlControlDataManager HxzFactory={},HxzId={}", HxzFactory,
                                    HxzId);
                            String Result =
                                    "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd
                                            + "$@";
                            log.info("Result:" + Result);
                            ctx.channel().writeAndFlush(Result).syncUninterruptibly();
                        }
                    }
                    break;
                case "I":
                    log.info("报警开始时刻数据:");
                    if (VerNum.equals("4") && online_flag.equals("1") && isBA) {
                        String t1 = strings[5];
                        String t2 = strings[6];
                        String StartTime = t1 + ' ' + t2;
                        String AlarmType = strings[7];
                        String AlarmValue = strings[8];
                        chackIpData(HxzFactory, HxzId, clientIP);

                        TowerCrane towerCrane = towerCraneManager
                                .getTowerCraneByHxzIdAndHxzFactory(HxzFactory, HxzId);
                        log.info("getTowerCraneByHxzFactory={}  HxzId={}", HxzFactory, HxzId);

                        BigDecimal Projectid = towerCrane.getProject_id();
                        BigDecimal LoginData_row_id = towerCrane.getLoginData_row_id();

                        xlAlarmDataStartManager.saveSql(HxzFactory,
                                HxzId,
                                Projectid.toString().trim(),
                                LoginData_row_id.toString().trim(),
                                StartTime,
                                AlarmType,
                                AlarmValue);
                        log.info("xlAlarmDataStartManager.saveSql HxzFactory={},HxzId={}",
                                HxzFactory,
                                HxzId);
                        String Result =
                                "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd
                                        + "$@";
                        log.info("Result:" + Result);
                        ctx.channel().writeAndFlush(Result).syncUninterruptibly();
                    }
                    break;
                case "9":
                    log.info("9载重零漂:");
                    XLRegisterRequest xlRegisterRequest1 = new XLRegisterRequest(HxzFactory, HxzId,
                            ProtocolVer, cmd,
                            strings[5], strings[6], strings[7],
                            strings[8], strings[9], strings[10],
                            strings[11], strings[12], strings[13],
                            strings[14], strings[15], strings[16],
                            strings[17], strings[18],
                            Integer.valueOf(strings[19]), strings[20],
                            strings[21],
                            strings[22], Integer.valueOf(strings[23]), strings[24],
                            strings[25], strings[26]);
                    String Weight1Zero = strings[27];//	varchar载重1零漂
                    String Weight2Zero = strings[28];//	varchar载重2零漂
                    String ObliguityXZero = strings[29];// varchar倾角X零漂
                    String ObliguityYZero = strings[30];// varchar倾角Y零漂

                    chackIpData(HxzFactory, HxzId, clientIP);
                    XLControlData xlControlData = xlControlDataManager.getEntity(HxzFactory, HxzId);
                    xlControlData.setGetFlag("0");//还原设置标记为0
                    xlControlData.setWeight1Zero(Weight1Zero);
                    xlControlData.setWeight2Zero(Weight2Zero);
                    xlControlData.setObliguityXZero(ObliguityXZero);
                    xlControlData.setObliguityYZero(ObliguityYZero);
                    xlControlDataManager.save(xlControlData);
                    String Result =
                            "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd
                                    + "$@";
                    log.info("Result:" + Result);
                    ctx.channel().writeAndFlush(Result).syncUninterruptibly();
                    break;
                default:
                    log.info("无此命令:");
                    System.out.print("无此命令");
            }

        }
    }

    /**
     * 活跃的、有效的通道 第一次连接成功后进入的方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        //连接
        log.info("已建立新连接Established connection with the remote client.:" + getIPString(ctx) + ctx
                .channel());
        //往channel map中添加channel信息
        NettyTcpServer.map.put(getIPString(ctx), ctx.channel());
        //通道激活
        ctx.fireChannelActive();
    }

    /**
     * 不活动的通道 （客户端断电断网）连接丢失后执行的方法（client端可据此实现断线重连）
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //删除Channel Map中的失效Client
        //删除Channel Map中的失效Client

        NettyTcpServer.map.remove(getIPString(ctx));
        AttributeKey<String> KEY_HXZ_ID = AttributeKey.valueOf("HxzId");
        String HxzId = ctx.channel().attr(KEY_HXZ_ID).get();
        AttributeKey<String> KEY_HXZFACTORY_ID = AttributeKey.valueOf("HxzFactory");
        String HxzFactory = ctx.channel().attr(KEY_HXZFACTORY_ID).get();
        if (NullUtil.isNotEmpty(HxzId)) {
            ipDataManager.pDownlineVF(HxzFactory, HxzId);
            log.info("call pDownlineVF");
        }
        log.info("断开连接Disconnected with the remote client.:{}{}HxzId={}", getIPString(ctx), ctx
                .channel(), HxzId);
        ctx.fireChannelInactive();
        ctx.close();
    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        //发生异常，关闭连接
        log.error("引擎 {} 的通道发生异常，即将断开连接", getRemoteAddress(ctx));

        NettyTcpServer.map.remove(getIPString(ctx));
        AttributeKey<String> KEY_HXZ_ID = AttributeKey.valueOf("HxzId");
        String HxzId = ctx.channel().attr(KEY_HXZ_ID).get();
        AttributeKey<String> KEY_HXZFACTORY_ID = AttributeKey.valueOf("HxzFactory");
        String HxzFactory = ctx.channel().attr(KEY_HXZFACTORY_ID).get();
        if (NullUtil.isNotEmpty(HxzId)) {
            ipDataManager.pDownlineVF(HxzFactory, HxzId);
            log.info("call pDownlineVF");
        }

        ctx.close();//再次建议close
    }

    /**
     * 心跳机制，超时处理 客户端 一段时间内未做任何读写操作
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        String socketString = ctx.channel().remoteAddress().toString();
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("Client: " + socketString + " READER_IDLE 读超时");
                ctx.disconnect();//
                NettyTcpServer.map.remove(getIPString(ctx));

            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("Client: " + socketString + " WRITER_IDLE 写超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.info("Client: " + socketString + " ALL_IDLE 总超时");

                NettyTcpServer.map.remove(getIPString(ctx));
                AttributeKey<String> KEY_HXZ_ID = AttributeKey.valueOf("HxzId");
                String HxzId = ctx.channel().attr(KEY_HXZ_ID).get();
                AttributeKey<String> KEY_HXZFACTORY_ID = AttributeKey.valueOf("HxzFactory");
                String HxzFactory = ctx.channel().attr(KEY_HXZFACTORY_ID).get();
                if (NullUtil.isNotEmpty(HxzId)) {
                    ipDataManager.pDownlineVF(HxzFactory, HxzId);
                    log.info("call pDownlineVF");
                }

                ctx.disconnect();
                NettyTcpServer.map.remove(getIPString(ctx));


            }
        }
    }


    /**
     * 获取client对象：ip+port
     */
    public String getRemoteAddress(ChannelHandlerContext ctx) {
        String socketString = "";
        socketString = ctx.channel().remoteAddress().toString();
        return socketString;
    }

    /**
     * 获取client的ip
     */
    public String getIPString(ChannelHandlerContext ctx) {
        String ipString = "";
        String socketString = ctx.channel().remoteAddress().toString();
        int colonAt = socketString.indexOf(":");
        ipString = socketString.substring(1, colonAt);
        return ipString;
    }

    //检查ipdata数据情况
    public void chackIpData(String HxzFactory, String HxzId, String clientIP) {
        //根据黑匣子查询 ip数据是否存在
        IpData ipDataH = ipDataManager.getIpByHxzIdAndHxzFactory(HxzFactory, HxzId);
        if (NullUtil.isNotEmpty(ipDataH)) {
            //存在更新时间和IP
            ipDataManager.upDateIp(clientIP, HxzFactory, HxzId);
            log.info("upDateIp:{}", clientIP);
        } else {
            //不存在插入一条新数据
            IpData ipData = new IpData();
            ipData.setHxzId(HxzId);
            ipData.setHxzFactory(HxzFactory);
            ipData.setHxzIp(clientIP);
            ipData.setDateTime(DateUtils.getCurrentTime());
            ipDataManager.save(ipData);
            log.info("insert new ipdate", clientIP);
        }
    }

    /**
     * 更新runtimedata
     */
//    public void updateRuntime(String HxzFactory, String HxzId, String online_flag) {
//        //查询RuntimeData 是否存在数据 存在且 不在线更新 在线数据
//        RuntimeData runtimeData = runtimeDataManager
//                .getIpByHxzIdAndHxzFactory(HxzFactory, HxzId);
//        log.info("select runtimeData", HxzFactory, HxzId);
//        if (NullUtil.isNotEmpty(runtimeData)) {
//            if ("0".equals(online_flag)) {
//                //存在且online_flag=0是更新时间状态 RunTime=0
//                String OnlineTime = DateUtils.getCurrentTime();
//                runtimeDataManager
//                        .upRuntime(OnlineTime, "0", HxzFactory, HxzId);
//                log.info(
//                        "update runtimeData OnlineTime:{},HxzFactory:{},HxzId:{}",
//                        OnlineTime, HxzFactory, HxzId);
//            }
//
//        } else {
//            RuntimeData runtimeData1 = new RuntimeData();
//            runtimeData1.setHxzFactory(HxzFactory);
//            runtimeData1.setHxzId(HxzId);
//            runtimeData1.setOnlineTime(DateUtils.getCurrentTime());
//            runtimeData1.setRunTime("0");
//            runtimeDataManager.save(runtimeData1);
//            log.info("Insert runtimeData HxzFactory:{},HxzId:{}", HxzFactory,
//                    HxzId);
//        }
//    }
}