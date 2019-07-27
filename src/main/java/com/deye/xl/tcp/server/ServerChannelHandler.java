package com.deye.demo.tcp.server;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deye.demo.entity.BaseDataCrane;
import com.deye.demo.entity.BaseDataElevator;
import com.deye.demo.entity.ControlDataCrane;
import com.deye.demo.entity.ControlDataElevator;
import com.deye.demo.entity.GpsData;
import com.deye.demo.entity.IpData;
import com.deye.demo.entity.KeySettingsCrane;
import com.deye.demo.entity.LoginData;
import com.deye.demo.entity.MultiSettingsCrane;
import com.deye.demo.entity.Operator;
import com.deye.demo.entity.OperatorData;
import com.deye.demo.entity.Project;
import com.deye.demo.entity.RealtimeDataCrane;
import com.deye.demo.entity.RealtimeDataElevator;
import com.deye.demo.entity.RuntimeData;
import com.deye.demo.entity.TowerCrane;
import com.deye.demo.entity.WorkInfo;
import com.deye.demo.entity.WorkNo;
import com.deye.demo.httpService.UserClient;
import com.deye.demo.manager.AlarmDataElevatorManager;
import com.deye.demo.manager.AlarmDataManager;
import com.deye.demo.manager.BaseDataCraneManager;
import com.deye.demo.manager.BaseDataElevatorManager;
import com.deye.demo.manager.ControlDataCraneManager;
import com.deye.demo.manager.ControlDataElevatorManager;
import com.deye.demo.manager.GpsDataManager;
import com.deye.demo.manager.IpDataManager;
import com.deye.demo.manager.KeySettingsCraneManager;
import com.deye.demo.manager.LoginDataManager;
import com.deye.demo.manager.MultiSettingsCraneManager;
import com.deye.demo.manager.OperatorDataManager;
import com.deye.demo.manager.OperatorManager;
import com.deye.demo.manager.ProjectManager;
import com.deye.demo.manager.RealtimeDataCraneManager;
import com.deye.demo.manager.RealtimeDataElevatorManager;
import com.deye.demo.manager.RuntimeDataManager;
import com.deye.demo.manager.TowerCraneManager;
import com.deye.demo.manager.WorkDataCraneManager;
import com.deye.demo.manager.WorkDataElevatorManager;
import com.deye.demo.manager.WorkInfoManager;
import com.deye.demo.manager.WorkNoManager;
import com.deye.demo.postBody.ChenDuPost;
import com.deye.demo.postBody.HaiNanPost;
import com.deye.demo.request.AlarmChangeRequest;
import com.deye.demo.request.AlarmElevatorRequest;
import com.deye.demo.request.AlarmRequest;
import com.deye.demo.request.BaseDataRequest;
import com.deye.demo.request.ElevatorBaseDataRequest;
import com.deye.demo.request.ElevatorRealtimeDataRequest;
import com.deye.demo.request.ElevatorWorkDataRequest;
import com.deye.demo.request.RealtimeDataRequest;
import com.deye.demo.request.RegisterRequest;
import com.deye.demo.request.WorkDataRequest;
import com.deye.demo.util.DateUtils;
import com.deye.demo.util.NullUtil;
import com.deye.demo.util.RegexMatches;
import com.deye.demo.util.SubStr;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;


/**
 * description: author:jackie date: 2019.6.13
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
    TowerCraneManager towerCraneManager;
    @Autowired
    GpsDataManager gpsDataManager;

    @Autowired
    RuntimeDataManager runtimeDataManager;
    @Autowired
    ControlDataCraneManager controlDataCraneManager;
    @Autowired
    ProjectManager projectManager;
    @Autowired
    KeySettingsCraneManager keySettingsCraneManager;
    @Autowired
    BaseDataCraneManager baseDataCraneManager;
    @Autowired
    MultiSettingsCraneManager multiSettingsCraneManager;
    @Autowired
    RealtimeDataCraneManager realtimeDataCraneManager;

    @Autowired
    WorkNoManager workNoManager;
    @Autowired
    WorkInfoManager workInfoManager;
    @Autowired
    OperatorManager operatorManager;
    @Autowired
    WorkDataCraneManager workDataCraneManager;
    @Autowired
    UserClient userClient;
    @Autowired
    AlarmDataManager alarmDataManager;

    @Autowired
    OperatorDataManager operatorDataManager;
    @Autowired
    HaiNanPost haiNanPost;
    @Autowired
    ControlDataElevatorManager controlDataElevatorManager;
    @Autowired
    BaseDataElevatorManager baseDataElevatorManager;

    @Autowired
    RealtimeDataElevatorManager realtimeDataElevatorManager;

    @Autowired
    WorkDataElevatorManager workDataElevatorManager;
    @Autowired
    AlarmDataElevatorManager alarmDataElevatorManager;
    @Autowired
    ChenDuPost chenDuPost;

    public static Map<String, Channel> HxzIdChannelmap = new ConcurrentHashMap<String, Channel>();
    /**
     * 拿到传过来的msg数据，开始处理
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        String strMsg = msg.toString().trim();

        String clientIP = getIPString(ctx);//客户端ip地址
        clientIP = SubStr.getip(clientIP);
        InetAddress address = InetAddress.getLocalHost();
        String ReturnServerIp = "";//服务器获取IP地址
        String ReturnServerPort = "";//服务器端口号
//        ReturnServerIp = SubStr.getip(ReturnServerIp);
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
                    RegisterRequest registerRequest = new RegisterRequest();
                    registerRequest.setHxzFactory(HxzFactory);
                    registerRequest.setHxzId(HxzId);
                    registerRequest.setProtocolVer(ProtocolVer);
                    registerRequest.setCmd(cmd);
                    registerRequest.setHardwareVer(strings[5]);
                    registerRequest.setSoftwareVer(strings[6]);
                    registerRequest.setSimCardNo(strings[7]);
                    //根据黑匣子查询 ip数据是否存在
                    chackIpData(HxzFactory, HxzId, clientIP);
//                    LoginData loginData = loginDataManager
//                            .getLoginDataByHxzIdAndHxzFactory(HxzFactory, HxzId);
                    log.info("select loginData HxzFactory:{},HxzId:{}", HxzFactory, HxzId);
                    //塔机注册  更新loginDate 和runtimedate 设置在线状态
                    if (VerNum.equals("2")) {
                        //根据HxzFactory和HxzId 查询LoginData 是否存在CraneId 已备案时
                        if (isBA) {
                            //存在 从TowerCrane 表查询项目数据
                            TowerCrane towerCrane = towerCraneManager
                                    .getTowerCraneByHxzIdAndHxzFactory(HxzFactory, HxzId);
                            log.info("select towerCrane: HxzFactory:{},HxzId:{}", HxzFactory,
                                    HxzId);
                            //查LoginData  online_flag 是否在线 不在线更新logindata数据
//                            String online_flag = loginDataAll.getOnLine();
                            //更新数据 设置OnLine='1'
                            loginDataManager.upDateLoginData(registerRequest.getHardwareVer(),
                                    registerRequest.getSoftwareVer(),
                                    registerRequest.getSimCardNo(), registerRequest.getHxzFactory(),
                                    registerRequest.getHxzId(), "1", 1);
                            log.info("upDateLoginData:OnLine='1' HxzFactory:{},HxzId:{}",
                                    HxzFactory,
                                    HxzId);
                            //查询RuntimeData 是否存在数据 存在且 不在线更新 在线数据
                            updateRuntime(HxzFactory, HxzId, online_flag);
                            //不在线插入一条新的在线数据 设置在线时间为当前时间 下线时间为空
                            if ("0".equals(online_flag)) {
                                //向RuntimeData_CraneId 插入数据
                                runtimeDataManager
                                        .insertRunTimeDataSql(loginDataAll.getCraneId(), HxzFactory,
                                                HxzId,
                                                DateUtils.getCurrentTime(),
                                                "", online_flag);
                                log.info("insertRunTimeDataSql", HxzFactory, HxzId);
                            }
                            //查询ControlDataCrane表是否存在数据 并计算租赁时间间隔
                            ControlDataCrane controlDataCrane = controlDataCraneManager
                                    .getControlDataCraneByHxzIdAndHxzFactory(HxzFactory, HxzId);
                            log.info("select  controlDataCrane HxzFactory:{},HxzId:{}", HxzFactory,
                                    HxzId);
                            if (NullUtil.isNotEmpty(controlDataCrane)) {
                                ReturnServerIp = controlDataCrane.getServerIp();
                                ReturnServerPort = controlDataCrane.getServerPort();
                                String LeaseStartDate = controlDataCrane.getLeaseStartDate();
                                String LeaseEndDate = controlDataCrane.getLeaseEndDate();
                                int dateDiffer = DateUtils
                                        .differentDaysByMillisecond(
                                                DateUtils.StrToDate(LeaseStartDate),
                                                DateUtils.StrToDate(LeaseEndDate));
                                String LeaseFlag;
                                if (dateDiffer > 0) {
                                    LeaseFlag = "0";
                                } else {
                                    LeaseFlag = "1";
                                }
                                //更新 LeaseFlag = '1'
                                controlDataCraneManager
                                        .upControlDataCrane(LeaseFlag, HxzFactory, HxzId);
                                log.info(
                                        "update  controlDataCrane set LeaseFlag = '1' HxzFactory:{},HxzId:{}",
                                        HxzFactory,
                                        HxzId);
                            }
                            //返回 数据到单片机
                            String leaseEndDate;
                            int diffDate = 9;
                            String endyear = "";
                            String endmonth = "";
                            String endday = "";
                            String LeasePhone = towerCrane.getLeasePhone();
                            String StationPhone = towerCrane.getStationPhone();
                            String WorkPhone = towerCrane.getWorkPhone();

                            if (NullUtil.isNotEmpty(controlDataCrane.getLeaseEndDate())) {
                                leaseEndDate = controlDataCrane.getLeaseEndDate();
                                endyear = leaseEndDate.substring(2, 4);
                                endmonth = leaseEndDate.substring(5, 7);
                                endday = leaseEndDate.substring(8, 10);
                                diffDate = DateUtils
                                        .differentDaysByMillisecond(new Date(),
                                                DateUtils.StrToDate(leaseEndDate));
                                if (diffDate <= 0) {
                                    diffDate = 0;
                                }
                                if (diffDate > 7) {
                                    diffDate = 9;
                                }
                            }
                            //心跳
                            String HeartBeatInterval = controlDataCrane.getHeartBeatInterval()
                                    .toString();
                            //补0
                            String WorkInterval = controlDataCrane.getWorkInterval().toString();
                            if (Integer.valueOf(WorkInterval) < 10) {
                                WorkInterval = "0" + WorkInterval;
                            }
                            String NoWorkInterval = controlDataCrane.getNoWorkInterval().toString();
                            if (Integer.valueOf(NoWorkInterval) < 10) {
                                NoWorkInterval = "0" + NoWorkInterval;
                            }
                            if (HxzFactory.equals("HN01")) {
                                HeartBeatInterval = "60";
                                WorkInterval = "30";
                                NoWorkInterval = "30";
                                //更新心跳频率
                                controlDataCraneManager.upInterval(HeartBeatInterval, WorkInterval,
                                        NoWorkInterval, HxzFactory, HxzId);
                                log.info("controlDataCraneManager upInterval");
                            }
                            String resultEnd = "$@";
                            if (ChildProtocol.compareTo("G") >= 0) { //海南V2.G是返回更多
//                                resultEnd = "$" + SteelCableSetError + "$" + SteelCableCntTimes+ "$" +
//                                        SteelCableAlarmValue+ "$"  + SteelCableLevel1 + "$" + SteelCableLevel2
//                                        + "$" + SteelCableLevel3 + "$" + SteelCableLevel4 + "$" +SteelCableLevel5+ "$@";
                                resultEnd =
                                        "$" + "0" + "$" + "05" + "$" +
                                                "03" + "$" + "03" + "$"
                                                + "05"
                                                + "$" + "07" + "$" + "10"
                                                + "$" + "12" + "$@";
                            }
                            String Result =
                                    "*$" + HxzFactory + "$" + HxzId + "$" + ProtocolVer
                                            + "$0$"
                                            + HeartBeatInterval + "$"
                                            + WorkInterval + "$"
                                            + NoWorkInterval + "$"
                                            + controlDataCrane.getErrorDelay() + "$"
                                            + DateUtils.getYearStr() + "$" + DateUtils
                                            .getMonthStr() + "$" + DateUtils.getDayStr()
                                            + "$" + DateUtils.getHHStr() + "$" + DateUtils
                                            .getMMStr() + "$"
                                            + DateUtils.getSSStr() + "$" + controlDataCrane
                                            .getLockFlag() + "$" + endyear + "$" + endmonth
                                            + "$" + endday + "$" + diffDate + "$" + controlDataCrane
                                            .getWeightSetError() + "$" + controlDataCrane
                                            .getWindSpeedSetError()
                                            + "$" + controlDataCrane.getRangeSetError() + "$"
                                            + controlDataCrane.getHeightSetError() + "$"
                                            + controlDataCrane.getAngleSetError()
                                            + "$" + controlDataCrane.getObliguitySetError() + "$"
                                            + controlDataCrane.getGpsSetError() + "$"
                                            + controlDataCrane
                                            .getIdSetError()
                                            + "$" + LeasePhone + "$" + StationPhone + "$"
                                            + WorkPhone
                                            + "$" + ReturnServerIp + "$" + ReturnServerPort
                                            + resultEnd;
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
                            loginDataManager.upDateLoginData(registerRequest.getHardwareVer(),
                                    registerRequest.getSoftwareVer(),
                                    registerRequest.getSimCardNo(), HxzFactory, HxzId, "1", 1);
                            log.info("update  upDateLoginData HxzFactory={},HxzId={}", HxzFactory,
                                    HxzId);
                            //更新GPS
                            gpsDataManager.upDateGps(HxzFactory, HxzId, 0, 0);
                            log.info("update  upDateGps HxzFactory={},HxzId={}", HxzFactory, HxzId);
                        } else {
                            //无数据时插入数据LoginData
                            LoginData loginData1 = new LoginData();
                            loginData1.setHxzFactory(registerRequest.getHxzFactory());
                            loginData1.setHxzId(registerRequest.getHxzId());
                            loginData1.setHardwareVer(registerRequest.getHardwareVer());
                            loginData1.setSoftwareVer(registerRequest.getSoftwareVer());
                            loginData1.setSimCardNo(registerRequest.getSimCardNo());
                            loginData1.setOnLine("1");
                            loginData1.setType(1);
                            loginDataManager.save(loginData1);
                            log.info("Insert  LoginData HxzFactory={},HxzId={}", HxzFactory, HxzId);
                            //插入数据到 GpsData
                            GpsData gpsData = new GpsData();
                            gpsData.setHxzFactory(registerRequest.getHxzFactory());
                            gpsData.setHxzId(registerRequest.getHxzId());
                            gpsData.setLocateLock(0);
                            gpsData.setWorkSiteNo(0);
                            gpsDataManager.save(gpsData);
                            log.info("Insert  gpsData HxzFactory={},HxzId={}", HxzFactory, HxzId);
                        }
                    }
                    //升降机注册
                    if (VerNum.equals("1")) {
                        //根据HxzFactory和HxzId 查询LoginData 是否存在CraneId 已备案时

                        if (isBA) {
                            //存在 从TowerCrane 表查询项目数据
                            TowerCrane towerCrane = towerCraneManager
                                    .getTowerCraneByHxzIdAndHxzFactory(HxzFactory, HxzId);
                            log.info("select towerCrane: HxzFactory:{},HxzId:{}", HxzFactory,
                                    HxzId);
                            //查LoginData  online_flag 是否在线 不在线更新logindata数据
//                            String online_flag = loginData.getOnLine();
                            //更新数据 设置OnLine='1'
                            loginDataManager.upDateLoginData(registerRequest.getHardwareVer(),
                                    registerRequest.getSoftwareVer(),
                                    registerRequest.getSimCardNo(), registerRequest.getHxzFactory(),
                                    registerRequest.getHxzId(), "1", 2);
                            log.info("upDateLoginData:OnLine='1' HxzFactory:{},HxzId:{}",
                                    HxzFactory,
                                    HxzId);
                            //查询RuntimeData 是否存在数据 存在且 不在线更新 在线数据
                            updateRuntime(HxzFactory, HxzId, online_flag);

                            if ("0".equals(online_flag)) {
                                //向RuntimeData_CraneId 插入数据
                                runtimeDataManager
                                        .insertRunTimeDataSql(loginDataAll.getCraneId(), HxzFactory,
                                                HxzId,
                                                DateUtils.getCurrentTime(),
                                                "", online_flag);
                                log.info("insertRunTimeDataSql", HxzFactory, HxzId);
                            }
                            //查询ControlDataCrane表是否存在数据 并计算租赁时间间隔
                            ControlDataElevator controlDataElevator = controlDataElevatorManager
                                    .getEntityByHxzFactory(HxzFactory, HxzId);
                            log.info("select  controlDataElevator HxzFactory:{},HxzId:{}",
                                    HxzFactory,
                                    HxzId);
                            if (NullUtil.isNotEmpty(controlDataElevator)) {
                                ReturnServerIp = controlDataElevator.getServerIp();
                                ReturnServerPort = controlDataElevator.getServerPort();
                                String LeaseStartDate = controlDataElevator.getLeaseStartDate();
                                String LeaseEndDate = controlDataElevator.getLeaseEndDate();
                                int dateDiffer = DateUtils
                                        .differentDaysByMillisecond(
                                                DateUtils.StrToDate(LeaseStartDate),
                                                DateUtils.StrToDate(LeaseEndDate));
                                String LeaseFlag;
                                if (dateDiffer > 0) {
                                    LeaseFlag = "0";
                                } else {
                                    LeaseFlag = "1";
                                }
                                //更新 LeaseFlag = '1'
                                controlDataElevatorManager
                                        .upLeaseFlag(LeaseFlag, HxzFactory, HxzId);
                                log.info(
                                        "update  controlDataElevator set LeaseFlag = {}' HxzFactory:{},HxzId:{}",
                                        LeaseFlag, HxzFactory,
                                        HxzId);
                            }
                            //返回 数据到单片机
                            String leaseEndDate;
                            int diffDate = 9;
                            String endyear = "";
                            String endmonth = "";
                            String endday = "";
                            String LeasePhone = towerCrane.getLeasePhone();
                            String StationPhone = towerCrane.getStationPhone();
                            String WorkPhone = towerCrane.getWorkPhone();
//                            String ReturnServerPort = String.valueOf(PORT);//服务器端口号
                            if (NullUtil.isNotEmpty(controlDataElevator.getLeaseEndDate())) {
                                leaseEndDate = controlDataElevator.getLeaseEndDate();
                                endyear = leaseEndDate.substring(2, 4);
                                endmonth = leaseEndDate.substring(5, 7);
                                endday = leaseEndDate.substring(8, 10);
                                diffDate = DateUtils
                                        .differentDaysByMillisecond(new Date(),
                                                DateUtils.StrToDate(leaseEndDate));
                                if (diffDate <= 0) {
                                    diffDate = 0;
                                }
                                if (diffDate > 7) {
                                    diffDate = 9;
                                }
                            }
                            //补0
                            String WorkInterval = controlDataElevator.getWorkInterval().toString();
                            if (Integer.valueOf(WorkInterval) < 10) {
                                WorkInterval = "0" + WorkInterval;
                            }
                            String NoWorkInterval = controlDataElevator.getNoWorkInterval()
                                    .toString();
                            if (Integer.valueOf(NoWorkInterval) < 10) {
                                NoWorkInterval = "0" + NoWorkInterval;
                            }
                            String HeartBeatInterval = controlDataElevator.getHeartBeatInterval()
                                    .toString();
                            if (HxzFactory.equals("HN01")) {
                                HeartBeatInterval = "60";
                                WorkInterval = "30";
                                NoWorkInterval = "30";
                                //更新心跳频率
                                controlDataElevatorManager
                                        .upInterval(HeartBeatInterval, WorkInterval,
                                                NoWorkInterval, HxzFactory, HxzId);
                            }
                            String Result =
                                    "*$" + HxzFactory + "$" + HxzId + "$" + ProtocolVer
                                            + "$0$"
                                            + HeartBeatInterval + "$"
                                            + WorkInterval + "$"
                                            + NoWorkInterval + "$"
                                            + controlDataElevator.getErrorDelay() + "$"
                                            + DateUtils.getYearStr() + "$" + DateUtils
                                            .getMonthStr() + "$" + DateUtils.getDayStr()
                                            + "$" + DateUtils.getHHStr() + "$" + DateUtils
                                            .getMMStr() + "$"
                                            + DateUtils.getSSStr() + "$" + controlDataElevator
                                            .getLockFlag() + "$" + endyear + "$" + endmonth
                                            + "$" + endday + "$" + diffDate + "$"
                                            + controlDataElevator
                                            .getIdSetError() + "$" + controlDataElevator
                                            .getPeopleCntSetError()
                                            + "$" + controlDataElevator.getWeightSetError() + "$"
                                            + controlDataElevator.getSpeedSetError() + "$"
                                            + controlDataElevator.getHeightSetError()
                                            + "$" + controlDataElevator.getFloorSetError() + "$"
                                            + controlDataElevator.getObliguityXSetError() + "$"
                                            + controlDataElevator
                                            .getObliguityYSetError() + "$" + controlDataElevator
                                            .getWindSpeedSetError()
                                            + "$" + controlDataElevator.getGpsSetError() + "$"
                                            + controlDataElevator.getWirelessSetError() + "$"
                                            + LeasePhone + "$" + StationPhone + "$"
                                            + WorkPhone
                                            + "$" + ReturnServerIp + "$" + ReturnServerPort + "$@";
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
                            loginDataManager.upDateLoginData(registerRequest.getHardwareVer(),
                                    registerRequest.getSoftwareVer(),
                                    registerRequest.getSimCardNo(), HxzFactory, HxzId, "1", 2);
                            log.info("update  upDateLoginData HxzFactory={},HxzId={}", HxzFactory,
                                    HxzId);
                            //更新GPS
                            gpsDataManager.upDateGps(HxzFactory, HxzId, 0, 0);
                            log.info("update  upDateGps HxzFactory={},HxzId={}", HxzFactory, HxzId);
                        } else {
                            //无数据时插入数据LoginData
                            LoginData loginData1 = new LoginData();
                            loginData1.setHxzFactory(registerRequest.getHxzFactory());
                            loginData1.setHxzId(registerRequest.getHxzId());
                            loginData1.setHardwareVer(registerRequest.getHardwareVer());
                            loginData1.setSoftwareVer(registerRequest.getSoftwareVer());
                            loginData1.setSimCardNo(registerRequest.getSimCardNo());
                            loginData1.setOnLine("1");
                            loginData1.setType(2);
                            loginDataManager.save(loginData1);
                            log.info("Insert  LoginData HxzFactory={},HxzId={}", HxzFactory, HxzId);
                            //插入数据到 GpsData
                            GpsData gpsData = new GpsData();
                            gpsData.setHxzFactory(registerRequest.getHxzFactory());
                            gpsData.setHxzId(registerRequest.getHxzId());
                            gpsData.setLocateLock(0);
                            gpsData.setWorkSiteNo(0);
                            gpsDataManager.save(gpsData);
                            log.info("Insert  gpsData HxzFactory={},HxzId={}", HxzFactory, HxzId);
                        }
                    }
                    break;
                case "1":
                    System.out.println("基础参数命令");
                    log.info("基础参数命令:");
                    if (VerNum.equals("2") && online_flag.equals("1")) {
                        String MultiNoStr = strings[119];
                        switch (MultiNoStr) {
                            case ":":
                                MultiNoStr = "10";
                                break;
                            case ";":
                                MultiNoStr = "11";
                                break;
                            case "<":
                                MultiNoStr = "12";
                                break;
                            case "=":
                                MultiNoStr = "13";
                                break;
                            case ">":
                                MultiNoStr = "14";
                                break;
                            case "?":
                                MultiNoStr = "15";
                                break;

                        }
                        BaseDataRequest baseDataRequest = new BaseDataRequest(
                                strings[1], strings[2], strings[3], strings[4],
                                Integer.valueOf(strings[5]), Float.valueOf(strings[6]),
                                Float.valueOf(strings[7]),
                                strings[8], strings[9], strings[10], strings[11], strings[12],
                                strings[13], strings[14], strings[15],
                                strings[16], strings[17], strings[18], strings[19], strings[20],
                                Float.valueOf(strings[21]), Float.valueOf(strings[22]),
                                Integer.valueOf(strings[23]),
                                Float.valueOf(strings[24]), Float.valueOf(strings[25]),
                                Float.valueOf(strings[26]), Float.valueOf(strings[27]),
                                Float.valueOf(strings[28]), Float.valueOf(strings[29]),
                                Float.valueOf(strings[30]), strings[31],
                                Float.valueOf(strings[32]), Float.valueOf(strings[33]), strings[34],
                                Float.valueOf(strings[35]), Float.valueOf(strings[36]), strings[37],
                                Float.valueOf(strings[38]), Float.valueOf(strings[39]),
                                strings[40], Float.valueOf(strings[41]), Float.valueOf(strings[42]),
                                strings[43], Float.valueOf(strings[44]), Float.valueOf(strings[45]),
                                strings[46], Float.valueOf(strings[47]),
                                Float.valueOf(strings[48]), strings[49], Float.valueOf(strings[50]),
                                Float.valueOf(strings[51]), Float.valueOf(strings[52]),
                                Float.valueOf(strings[53]), strings[54], Float.valueOf(strings[55]),
                                Float.valueOf(strings[56]), Float.valueOf(strings[57]),
                                Float.valueOf(strings[58]), strings[59], Float.valueOf(strings[60]),
                                Float.valueOf(strings[61]), Float.valueOf(strings[62]),
                                Float.valueOf(strings[63]),
                                strings[64], Float.valueOf(strings[65]), Float.valueOf(strings[66]),
                                Float.valueOf(strings[67]), Float.valueOf(strings[68]), strings[69],
                                Float.valueOf(strings[70]), Float.valueOf(strings[71]),
                                Float.valueOf(strings[72]), Float.valueOf(strings[73]), strings[74],
                                Float.valueOf(strings[75]), Float.valueOf(strings[76]),
                                Float.valueOf(strings[77]), Float.valueOf(strings[78]),
                                Float.valueOf(strings[79]),
                                Float.valueOf(strings[80]), Float.valueOf(strings[81]),
                                Float.valueOf(strings[82]), strings[83], Float.valueOf(strings[84]),
                                Float.valueOf(strings[85]), Float.valueOf(strings[86]),
                                Float.valueOf(strings[87]),
                                Float.valueOf(strings[88]), Float.valueOf(strings[89]),
                                Float.valueOf(strings[90]), Float.valueOf(strings[91]), strings[92],
                                Float.valueOf(strings[93]), Float.valueOf(strings[94]),
                                Float.valueOf(strings[95]),
                                Float.valueOf(strings[96]), Float.valueOf(strings[97]),
                                Float.valueOf(strings[98]), Float.valueOf(strings[99]),
                                Float.valueOf(strings[100]), strings[101],
                                Float.valueOf(strings[102]),
                                Float.valueOf(strings[103]),
                                Float.valueOf(strings[104]), Float.valueOf(strings[105]),
                                Float.valueOf(strings[106]), Float.valueOf(strings[107]),
                                Float.valueOf(strings[108]), Float.valueOf(strings[109]),
                                strings[110],
                                Float.valueOf(strings[111]),
                                Float.valueOf(strings[112]), Float.valueOf(strings[113]),
                                Float.valueOf(strings[114]), Float.valueOf(strings[115]),
                                Float.valueOf(strings[116]), Float.valueOf(strings[117]),
                                Float.valueOf(strings[118]), MultiNoStr,
                                Integer.valueOf(strings[120]), strings[121],
                                Float.valueOf(strings[122]), Float.valueOf(strings[123]),
                                Float.valueOf(strings[124]), Float.valueOf(strings[125]),
                                strings[126],
                                Float.valueOf(strings[127]),
                                Float.valueOf(strings[128]), Float.valueOf(strings[129]),
                                Float.valueOf(strings[130]), strings[131],
                                Float.valueOf(strings[132]),
                                Float.valueOf(strings[133]), Float.valueOf(strings[134]),
                                Float.valueOf(strings[135]),
                                strings[136], Float.valueOf(strings[137]),
                                Float.valueOf(strings[138]),
                                Float.valueOf(strings[139]), Float.valueOf(strings[140]),
                                strings[141],
                                Float.valueOf(strings[142]), Float.valueOf(strings[143]),
                                Float.valueOf(strings[144]), Float.valueOf(strings[145]),
                                strings[146],
                                Float.valueOf(strings[147]), Float.valueOf(strings[148]),
                                Float.valueOf(strings[149]), Float.valueOf(strings[150]),
                                strings[151],
                                Float.valueOf(strings[152]), Float.valueOf(strings[153]),
                                Float.valueOf(strings[154]), Float.valueOf(strings[155]),
                                strings[156],
                                Float.valueOf(strings[157]), Float.valueOf(strings[158]),
                                Float.valueOf(strings[159]),
                                Float.valueOf(strings[160]), strings[161],
                                Float.valueOf(strings[162]),
                                Float.valueOf(strings[163]), Float.valueOf(strings[164]),
                                Float.valueOf(strings[165]), strings[166],
                                Float.valueOf(strings[167]),
                                Float.valueOf(strings[168]), Float.valueOf(strings[169]),
                                Float.valueOf(strings[170]), strings[171],
                                Float.valueOf(strings[172]),
                                Float.valueOf(strings[173]), Float.valueOf(strings[174]),
                                Float.valueOf(strings[175]),
                                strings[176], Float.valueOf(strings[177]),
                                Float.valueOf(strings[178]),
                                Float.valueOf(strings[179]), Float.valueOf(strings[180]),
                                strings[181],
                                Float.valueOf(strings[182]), Float.valueOf(strings[183]),
                                Float.valueOf(strings[184]), Float.valueOf(strings[185]),
                                strings[186],
                                Float.valueOf(strings[187]), Float.valueOf(strings[188]),
                                Float.valueOf(strings[189]), Float.valueOf(strings[190]),
                                strings[191],
                                Float.valueOf(strings[192]), Float.valueOf(strings[193]),
                                Float.valueOf(strings[194]), Float.valueOf(strings[195]),
                                strings[196], Float.valueOf(strings[197]),
                                Float.valueOf(strings[198]),
                                Float.valueOf(strings[199]), Float.valueOf(strings[200]),
                                strings[201], strings[202],
                                strings[203],
                                strings[204], strings[205], strings[206], strings[207],
                                strings[208], strings[209], strings[210], strings[211],
                                strings[212], strings[213], strings[214], strings[215],
                                strings[216], strings[217], strings[218], strings[219],
                                strings[220], strings[221], strings[222], strings[223],
                                strings[224], strings[225], strings[226], strings[227],
                                strings[228], strings[229], strings[230], strings[231],
                                strings[232], Float.valueOf(strings[233]),
                                Float.valueOf(strings[234]),
                                Integer.valueOf(strings[235]), Integer.valueOf(strings[236]),
                                Integer.valueOf(strings[237]), Integer.valueOf(strings[238]),
                                Float.valueOf(strings[239]),
                                Float.valueOf(strings[240]), Float.valueOf(strings[241]),
                                Float.valueOf(strings[242]), Float.valueOf(strings[243]),
                                Float.valueOf(strings[244]), Float.valueOf(strings[245])
                        );
                        Integer WorkNoJ = 255;
                        String WorkNameJ = "";
                        String IdNo = "";
                        if (msgLength >= 248) {
                            WorkNoJ = Integer.valueOf(strings[246]);
                            WorkNameJ = strings[247];
                            IdNo = strings[248];
                        }
                        if (WorkNoJ == 255) {
                            WorkNameJ = "E69CAAE799BBE5BD95";
                        }
                        if (WorkNoJ == 0) {
                            WorkNameJ = "E7AEA1E79086E59198";
                        }
                        //版本为V2.G(ProtocolVer.equals("V2.G")
                        if (ChildProtocol.compareTo("G") >= 0) {
                            baseDataRequest.setSteelCableExist(strings[249]);
                            baseDataRequest.setSteelCableCntTimes(Integer.valueOf(strings[250]));
                            baseDataRequest.setSteelCableAlarmValue(Integer.valueOf(strings[251]));
                            baseDataRequest.setSteelCableLevel1(Integer.valueOf(strings[252]));
                            baseDataRequest.setSteelCableLevel2(Integer.valueOf(strings[253]));
                            baseDataRequest.setSteelCableLevel3(Integer.valueOf(strings[254]));
                            baseDataRequest.setSteelCableLevel4(Integer.valueOf(strings[255]));
                            baseDataRequest.setSteelCableLevel5(Integer.valueOf(strings[256]));
                        }
                        //调用OperatorData 操作员记录
                        upoperatorData(HxzFactory, HxzId, WorkNoJ.toString(), WorkNameJ,
                                IdNo);
                        log.info("upoperatorData HxzFactory={},HxzId={}", HxzFactory, HxzId);
                        //检查ipdata数据
                        chackIpData(HxzFactory, HxzId, clientIP);
                        //更新ControlDataCrane
                        ControlDataCrane controlDataCrane = controlDataCraneManager
                                .getControlDataCraneByHxzIdAndHxzFactory(HxzFactory, HxzId);
                        log.info("select ControlDataCrane HxzFactory={},HxzId={}", HxzFactory,
                                HxzId);
                        if (NullUtil.isNotEmpty(controlDataCrane)) {
                            String WeightSetError = "1";
                            String WeightSet = baseDataRequest.getWeightSet();
                            if (WeightSet == "0") {
                                WeightSetError = "0";
                            }
                            String WindSpeedSetError = "1";
                            String WindSpeedSet = baseDataRequest.getWindSpeedSet();
                            if (WindSpeedSet == "0") {
                                WindSpeedSetError = "0";
                            }
                            String RangeSetError = "1";
                            String RangeSet = baseDataRequest.getRangeSet();
                            if (RangeSet == "0") {
                                RangeSetError = "0";
                            }
                            String HeightSetError = "1";
                            String HeightSet = baseDataRequest.getHeightSet();
                            if (HeightSet == "0") {
                                HeightSetError = "0";
                            }
                            String AngleSetError = "1";
                            String AngleSet = baseDataRequest.getAngleSet();
                            if (AngleSet == "0") {
                                AngleSetError = "0";
                            }
                            String ObliguitySetError = "1";
                            String ObliguitySet = baseDataRequest.getObliguitySet();
                            if (ObliguitySet == "0") {
                                ObliguitySetError = "0";
                            }
                            String GpsSetError = "1";
                            String MultiFunExist = baseDataRequest.getMultiFunExist();
                            if (MultiFunExist == "0") {
                                GpsSetError = "0";
                            }
                            String IdSetError = "1";
                            String IdSet = baseDataRequest.getIdSet();
                            if (IdSet == "0") {
                                IdSetError = "0";
                            }

                            controlDataCraneManager
                                    .updataError(WeightSetError, WindSpeedSetError, RangeSetError,
                                            HeightSetError
                                            , AngleSetError, ObliguitySetError, GpsSetError,
                                            IdSetError,
                                            HxzFactory, HxzId);
                            log.info("controlDataCrane updataError HxzFactory={},HxzId={}",
                                    HxzFactory,
                                    HxzId);
                        }
                        //更新重机关键设置起
                        KeySettingsCrane keySettingsCrane = keySettingsCraneManager
                                .getKeySettingsCrane(HxzFactory, HxzId);
                        log.info("select KeySettingsCrane HxzFactory={},HxzId={}", HxzFactory,
                                HxzId);
                        if (NullUtil.isNotEmpty(keySettingsCrane)) {

                            keySettingsCraneManager.upKeySettingsCrane(
                                    baseDataRequest.getKeepWorkingHours().toString(),
                                    baseDataRequest.getDifferInput().toString(),
                                    baseDataRequest.getSettingMinHeight(),
                                    baseDataRequest.getWeightZero(),
                                    baseDataRequest.getWindSpeedZero(),
                                    baseDataRequest.getAngleZero(),
                                    baseDataRequest.getDipXZero(), baseDataRequest.getDipYZero(),
                                    baseDataRequest.getElevationAngleZero(), "0", HxzFactory,
                                    HxzId);
                        } else {
                            KeySettingsCrane keySettingsCrane1 = new KeySettingsCrane();
                            keySettingsCrane1.setHxzFactory(HxzFactory);
                            keySettingsCrane1.setHxzId(HxzId);
                            keySettingsCrane1.setAngleZero(baseDataRequest.getAngleZero());
                            keySettingsCrane1
                                    .setDifferInput(baseDataRequest.getDifferInput().toString());
                            keySettingsCrane1.setDipXZero(baseDataRequest.getDipXZero());
                            keySettingsCrane1.setDipYZero(baseDataRequest.getDipYZero());
                            keySettingsCrane1.setKeepWorkingHours(
                                    baseDataRequest.getKeepWorkingHours().toString());
                            keySettingsCrane1
                                    .setSettingMinHeight(baseDataRequest.getSettingMinHeight());
                            keySettingsCrane1.setWeightZero(baseDataRequest.getWeightZero());
                            keySettingsCrane1.setWindSpeedZero(baseDataRequest.getWindSpeedZero());
                            keySettingsCrane1
                                    .setElevationAngleZero(baseDataRequest.getElevationAngleZero());
                            keySettingsCrane1.setModifyKeySettings("0");
                            keySettingsCraneManager.save(keySettingsCrane1);
                        }
                        //设置禁入区 禁吊区
                        String ForbidEntryExist = baseDataRequest.getForbidEntryExist();
                        if (!ForbidEntryExist.equals("1")) {
                            baseDataRequest.setForbidEntryExist("0");
                            baseDataRequest.setForbidEntryAngle0(0.0f);
                            baseDataRequest.setForbidEntryAngle1(0.0f);
                        }
                        String ForbidSuspend2Exist0 = baseDataRequest.getForbidSuspend2Exist0();
                        if (!ForbidSuspend2Exist0.equals("1")) {
                            baseDataRequest.setForbidSuspend2Exist0("0");
                            baseDataRequest.setForbidSuspend2Angle00(0.0f);
                            baseDataRequest.setForbidSuspend2Angle01(0.0f);
                        }
                        String ForbidSuspend2Exist1 = baseDataRequest.getForbidSuspend2Exist1();
                        if (!ForbidSuspend2Exist1.equals("1")) {
                            baseDataRequest.setForbidSuspend2Exist1("0");
                            baseDataRequest.setForbidSuspend2Angle10(0.0f);
                            baseDataRequest.setForbidSuspend2Angle11(0.0f);
                        }
                        String ForbidSuspend2Exist2 = baseDataRequest.getForbidSuspend2Exist2();
                        if (!ForbidSuspend2Exist2.equals("1")) {
                            baseDataRequest.setForbidSuspend2Exist2("0");
                            baseDataRequest.setForbidSuspend2Angle20(0.0f);
                            baseDataRequest.setForbidSuspend2Angle21(0.0f);
                        }
                        String ForbidSuspend2Exist3 = baseDataRequest.getForbidSuspend2Exist3();
                        if (!ForbidSuspend2Exist3.equals("1")) {
                            baseDataRequest.setForbidSuspend2Exist3("0");
                            baseDataRequest.setForbidSuspend2Angle30(0.0f);
                            baseDataRequest.setForbidSuspend2Angle31(0.0f);
                        }
                        String ForbidSuspend2Exist4 = baseDataRequest.getForbidSuspend2Exist4();
                        if (!ForbidSuspend2Exist4.equals("1")) {
                            baseDataRequest.setForbidSuspend2Exist4("0");
                            baseDataRequest.setForbidSuspend2Angle40(0.0f);
                            baseDataRequest.setForbidSuspend2Angle41(0.0f);
                        }

                        String ForbidSuspend4Exist0 = baseDataRequest.getForbidSuspend4Exist0();
                        if (!ForbidSuspend4Exist0.equals("1")) {
                            baseDataRequest.setForbidSuspend4Exist0("0");
                            baseDataRequest.setForbidSuspend4Angle00(0.0f);
                            baseDataRequest.setForbidSuspend4Range00(0.0f);
                            baseDataRequest.setForbidSuspend4Angle01(0.0f);
                            baseDataRequest.setForbidSuspend4Range01(0.0f);
                            baseDataRequest.setForbidSuspend4Angle02(0.0f);
                            baseDataRequest.setForbidSuspend4Range02(0.0f);
                            baseDataRequest.setForbidSuspend4Angle03(0.0f);
                            baseDataRequest.setForbidSuspend4Range03(0.0f);
                        }
                        String ForbidSuspend4Exist1 = baseDataRequest.getForbidSuspend4Exist1();
                        if (!ForbidSuspend4Exist1.equals("1")) {
                            baseDataRequest.setForbidSuspend4Exist1("0");
                            baseDataRequest.setForbidSuspend4Angle10(0.0f);
                            baseDataRequest.setForbidSuspend4Range10(0.0f);
                            baseDataRequest.setForbidSuspend4Angle11(0.0f);
                            baseDataRequest.setForbidSuspend4Range11(0.0f);
                            baseDataRequest.setForbidSuspend4Angle12(0.0f);
                            baseDataRequest.setForbidSuspend4Range12(0.0f);
                            baseDataRequest.setForbidSuspend4Angle13(0.0f);
                            baseDataRequest.setForbidSuspend4Range13(0.0f);
                        }
                        String ForbidSuspend4Exist2 = baseDataRequest.getForbidSuspend4Exist2();
                        if (!ForbidSuspend4Exist2.equals("1")) {
                            baseDataRequest.setForbidSuspend4Exist2("0");
                            baseDataRequest.setForbidSuspend4Angle20(0.0f);
                            baseDataRequest.setForbidSuspend4Range20(0.0f);
                            baseDataRequest.setForbidSuspend4Angle21(0.0f);
                            baseDataRequest.setForbidSuspend4Range21(0.0f);
                            baseDataRequest.setForbidSuspend4Angle22(0.0f);
                            baseDataRequest.setForbidSuspend4Range22(0.0f);
                            baseDataRequest.setForbidSuspend4Angle23(0.0f);
                            baseDataRequest.setForbidSuspend4Range23(0.0f);
                        }
                        String ForbidSuspend4Exist3 = baseDataRequest.getForbidSuspend4Exist3();
                        if (!ForbidSuspend4Exist3.equals("1")) {
                            baseDataRequest.setForbidSuspend4Exist3("0");
                            baseDataRequest.setForbidSuspend4Angle30(0.0f);
                            baseDataRequest.setForbidSuspend4Range30(0.0f);
                            baseDataRequest.setForbidSuspend4Angle31(0.0f);
                            baseDataRequest.setForbidSuspend4Range31(0.0f);
                            baseDataRequest.setForbidSuspend4Angle32(0.0f);
                            baseDataRequest.setForbidSuspend4Range32(0.0f);
                            baseDataRequest.setForbidSuspend4Angle33(0.0f);
                            baseDataRequest.setForbidSuspend4Range33(0.0f);
                        }
                        String ForbidSuspend4Exist4 = baseDataRequest.getForbidSuspend4Exist4();
                        if (!ForbidSuspend4Exist4.equals("1")) {
                            baseDataRequest.setForbidSuspend4Exist4("0");
                            baseDataRequest.setForbidSuspend4Angle40(0.0f);
                            baseDataRequest.setForbidSuspend4Range40(0.0f);
                            baseDataRequest.setForbidSuspend4Angle41(0.0f);
                            baseDataRequest.setForbidSuspend4Range41(0.0f);
                            baseDataRequest.setForbidSuspend4Angle42(0.0f);
                            baseDataRequest.setForbidSuspend4Range42(0.0f);
                            baseDataRequest.setForbidSuspend4Angle43(0.0f);
                            baseDataRequest.setForbidSuspend4Range43(0.0f);
                        }

                        //查询BaseDataCrane 更新或插入新值
                        BaseDataCrane baseDataCrane1 = new BaseDataCrane(
                                HxzFactory, HxzId, baseDataRequest.getCraneType(),
                                baseDataRequest.getFrontArmLength().toString(),
                                baseDataRequest.getBackArmLength().toString(),
                                baseDataRequest.getWeightSet(),
                                baseDataRequest.getWindSpeedSet(), baseDataRequest.getRangeSet(),
                                baseDataRequest.getHeightSet(),
                                baseDataRequest.getAngleSet(), baseDataRequest.getObliguitySet(),
                                baseDataRequest.getGpsSet(),
                                baseDataRequest.getIdSet(),
                                baseDataRequest.getForbidEntryFunExist(),
                                baseDataRequest.getForbidSuspend2FunExist(),
                                baseDataRequest.getForbidSuspend3FunExist(),
                                baseDataRequest.getForbidSuspend4FunExist(),
                                baseDataRequest.getMultiFunExist(),
                                baseDataRequest.getMaxWeight().toString(),
                                baseDataRequest.getRatedWindSpeed().toString(),
                                baseDataRequest.getRatedWindLevel().toString(),
                                baseDataRequest.getMinRange().toString(),
                                baseDataRequest.getMaxRange().toString(),
                                baseDataRequest.getArmHeight().toString(),
                                baseDataRequest.getMaxHeight().toString(),
                                baseDataRequest.getMaxAngle().toString(),
                                baseDataRequest.getMinAngle().toString(),
                                baseDataRequest.getRatedObliguity().toString(),
                                baseDataRequest.getForbidEntryExist(),
                                baseDataRequest.getForbidEntryAngle0().toString(),
                                baseDataRequest.getForbidEntryAngle1().toString(),
                                baseDataRequest.getForbidSuspend2Exist0(),
                                baseDataRequest.getForbidSuspend2Angle00().toString(),
                                baseDataRequest.getForbidSuspend2Angle01().toString(),
                                baseDataRequest.getForbidSuspend2Exist1(),
                                baseDataRequest.getForbidSuspend2Angle10().toString(),
                                baseDataRequest.getForbidSuspend2Angle11().toString(),
                                baseDataRequest.getForbidSuspend2Exist2(),
                                baseDataRequest.getForbidSuspend2Angle20().toString(),
                                baseDataRequest.getForbidSuspend2Angle21().toString(),
                                baseDataRequest.getForbidSuspend2Exist3(),
                                baseDataRequest.getForbidSuspend2Angle30().toString(),
                                baseDataRequest.getForbidSuspend2Angle31().toString(),
                                baseDataRequest.getForbidSuspend2Exist4(),
                                baseDataRequest.getForbidSuspend2Angle40().toString(),
                                baseDataRequest.getForbidSuspend2Angle41().toString(),
                                baseDataRequest.getForbidSuspend3Exist0(),
                                baseDataRequest.getForbidSuspend3Angle00().toString(),
                                baseDataRequest.getForbidSuspend3Range00().toString(),
                                baseDataRequest.getForbidSuspend3Angle01().toString(),
                                baseDataRequest.getForbidSuspend3Angle02().toString(),
                                baseDataRequest.getForbidSuspend3Exist1(),
                                baseDataRequest.getForbidSuspend3Angle10().toString(),
                                baseDataRequest.getForbidSuspend3Range10().toString(),
                                baseDataRequest.getForbidSuspend3Angle11().toString(),
                                baseDataRequest.getForbidSuspend3Angle12().toString(),
                                baseDataRequest.getForbidSuspend3Exist2(),
                                baseDataRequest.getForbidSuspend3Angle20().toString(),
                                baseDataRequest.getForbidSuspend3Range20().toString(),
                                baseDataRequest.getForbidSuspend3Angle21().toString(),
                                baseDataRequest.getForbidSuspend3Angle22().toString(),
                                baseDataRequest.getForbidSuspend3Exist3(),
                                baseDataRequest.getForbidSuspend3Angle30().toString(),
                                baseDataRequest.getForbidSuspend3Range30().toString(),
                                baseDataRequest.getForbidSuspend3Angle31().toString(),
                                baseDataRequest.getForbidSuspend3Angle32().toString(),
                                baseDataRequest.getForbidSuspend3Exist4(),
                                baseDataRequest.getForbidSuspend3Angle40().toString(),
                                baseDataRequest.getForbidSuspend3Range40().toString(),
                                baseDataRequest.getForbidSuspend3Angle41().toString(),
                                baseDataRequest.getForbidSuspend3Angle42().toString(),
                                baseDataRequest.getForbidSuspend4Exist0(),
                                baseDataRequest.getForbidSuspend4Angle00().toString(),
                                baseDataRequest.getForbidSuspend4Range00().toString(),
                                baseDataRequest.getForbidSuspend4Angle01().toString(),
                                baseDataRequest.getForbidSuspend4Range01().toString(),
                                baseDataRequest.getForbidSuspend4Angle02().toString(),
                                baseDataRequest.getForbidSuspend4Range02().toString(),
                                baseDataRequest.getForbidSuspend4Angle03().toString(),
                                baseDataRequest.getForbidSuspend4Range03().toString(),
                                baseDataRequest.getForbidSuspend4Exist1(),
                                baseDataRequest.getForbidSuspend4Angle10().toString(),
                                baseDataRequest.getForbidSuspend4Range10().toString(),
                                baseDataRequest.getForbidSuspend4Angle11().toString(),
                                baseDataRequest.getForbidSuspend4Range11().toString(),
                                baseDataRequest.getForbidSuspend4Angle12().toString(),
                                baseDataRequest.getForbidSuspend4Range12().toString(),
                                baseDataRequest.getForbidSuspend4Angle13().toString(),
                                baseDataRequest.getForbidSuspend4Range13().toString(),
                                baseDataRequest.getForbidSuspend4Exist2(),
                                baseDataRequest.getForbidSuspend4Angle20().toString(),
                                baseDataRequest.getForbidSuspend4Range20().toString(),
                                baseDataRequest.getForbidSuspend4Angle21().toString(),
                                baseDataRequest.getForbidSuspend4Range21().toString(),
                                baseDataRequest.getForbidSuspend4Angle22().toString(),
                                baseDataRequest.getForbidSuspend4Range22().toString(),
                                baseDataRequest.getForbidSuspend4Angle23().toString(),
                                baseDataRequest.getForbidSuspend4Range23().toString(),
                                baseDataRequest.getForbidSuspend4Exist3(),
                                baseDataRequest.getForbidSuspend4Angle30().toString(),
                                baseDataRequest.getForbidSuspend4Range30().toString(),
                                baseDataRequest.getForbidSuspend4Angle31().toString(),
                                baseDataRequest.getForbidSuspend4Range31().toString(),
                                baseDataRequest.getForbidSuspend4Angle32().toString(),
                                baseDataRequest.getForbidSuspend4Range32().toString(),
                                baseDataRequest.getForbidSuspend4Angle33().toString(),
                                baseDataRequest.getForbidSuspend4Range33().toString(),
                                baseDataRequest.getForbidSuspend4Exist4(),
                                baseDataRequest.getForbidSuspend4Angle40().toString(),
                                baseDataRequest.getForbidSuspend4Range40().toString(),
                                baseDataRequest.getForbidSuspend4Angle41().toString(),
                                baseDataRequest.getForbidSuspend4Range41().toString(),
                                baseDataRequest.getForbidSuspend4Angle42().toString(),
                                baseDataRequest.getForbidSuspend4Range42().toString(),
                                baseDataRequest.getForbidSuspend4Angle43().toString(),
                                baseDataRequest.getForbidSuspend4Range43().toString(),
                                baseDataRequest.getMultiNo(), baseDataRequest.getMultiChannel(),
                                baseDataRequest.getMultiExist0(),
                                baseDataRequest.getMultiX0().toString(),
                                baseDataRequest.getMultiY0().toString(),
                                baseDataRequest.getMultiFrontArmLength0().toString(),
                                baseDataRequest.getMultiBackArmLength0().toString(),
                                baseDataRequest.getMultiExist1(),
                                baseDataRequest.getMultiX1().toString(),
                                baseDataRequest.getMultiY1().toString(),
                                baseDataRequest.getMultiFrontArmLength1().toString(),
                                baseDataRequest.getMultiBackArmLength1().toString(),
                                baseDataRequest.getMultiExist2(),
                                baseDataRequest.getMultiX2().toString(),
                                baseDataRequest.getMultiY2().toString(),
                                baseDataRequest.getMultiFrontArmLength2().toString(),
                                baseDataRequest.getMultiBackArmLength2().toString(),
                                baseDataRequest.getMultiExist3(),
                                baseDataRequest.getMultiX3().toString(),
                                baseDataRequest.getMultiY3().toString(),
                                baseDataRequest.getMultiFrontArmLength3().toString(),
                                baseDataRequest.getMultiBackArmLength3().toString(),
                                baseDataRequest.getMultiExist4(),
                                baseDataRequest.getMultiX4().toString(),
                                baseDataRequest.getMultiY4().toString(),
                                baseDataRequest.getMultiFrontArmLength4().toString(),
                                baseDataRequest.getMultiBackArmLength4().toString(),
                                baseDataRequest.getMultiExist5(),
                                baseDataRequest.getMultiX5().toString(),
                                baseDataRequest.getMultiY5().toString(),
                                baseDataRequest.getMultiFrontArmLength5().toString(),
                                baseDataRequest.getMultiBackArmLength5().toString(),
                                baseDataRequest.getMultiExist6(),
                                baseDataRequest.getMultiX6().toString(),
                                baseDataRequest.getMultiY6().toString(),
                                baseDataRequest.getMultiFrontArmLength6().toString(),
                                baseDataRequest.getMultiBackArmLength6().toString(),
                                baseDataRequest.getMultiExist7(),
                                baseDataRequest.getMultiX7().toString(),
                                baseDataRequest.getMultiY7().toString(),
                                baseDataRequest.getMultiFrontArmLength7().toString(),
                                baseDataRequest.getMultiBackArmLength7().toString(),
                                baseDataRequest.getMultiExist8(),
                                baseDataRequest.getMultiX8().toString(),
                                baseDataRequest.getMultiY8().toString(),
                                baseDataRequest.getMultiFrontArmLength8().toString(),
                                baseDataRequest.getMultiBackArmLength8().toString(),
                                baseDataRequest.getMultiExist9(),
                                baseDataRequest.getMultiX9().toString(),
                                baseDataRequest.getMultiY9().toString(),
                                baseDataRequest.getMultiFrontArmLength9().toString(),
                                baseDataRequest.getMultiBackArmLength9().toString(),
                                baseDataRequest.getMultiExist10(),
                                baseDataRequest.getMultiX10().toString(),
                                baseDataRequest.getMultiY10().toString(),
                                baseDataRequest.getMultiFrontArmLength10().toString(),
                                baseDataRequest.getMultiBackArmLength10().toString(),
                                baseDataRequest.getMultiExist11(),
                                baseDataRequest.getMultiX11().toString(),
                                baseDataRequest.getMultiY11().toString(),
                                baseDataRequest.getMultiFrontArmLength11().toString(),
                                baseDataRequest.getMultiBackArmLength11().toString(),
                                baseDataRequest.getMultiExist12(),
                                baseDataRequest.getMultiX12().toString(),
                                baseDataRequest.getMultiY12().toString(),
                                baseDataRequest.getMultiFrontArmLength12().toString(),
                                baseDataRequest.getMultiBackArmLength12().toString(),
                                baseDataRequest.getMultiExist13(),
                                baseDataRequest.getMultiX13().toString(),
                                baseDataRequest.getMultiY13().toString(),
                                baseDataRequest.getMultiFrontArmLength13().toString(),
                                baseDataRequest.getMultiBackArmLength13().toString(),
                                baseDataRequest.getMultiExist14(),
                                baseDataRequest.getMultiX14().toString(),
                                baseDataRequest.getMultiY14().toString(),
                                baseDataRequest.getMultiFrontArmLength14().toString(),
                                baseDataRequest.getMultiBackArmLength14().toString(),
                                baseDataRequest.getMultiExist15(),
                                baseDataRequest.getMultiX15().toString(),
                                baseDataRequest.getMultiY15().toString(),
                                baseDataRequest.getMultiFrontArmLength15().toString(),
                                baseDataRequest.getMultiBackArmLength15().toString(),
                                baseDataRequest.getMultiHxzFactory0(),
                                baseDataRequest.getMultiHxzId0(),
                                baseDataRequest.getMultiHxzFactory1(),
                                baseDataRequest.getMultiHxzId1(),
                                baseDataRequest.getMultiHxzFactory2(),
                                baseDataRequest.getMultiHxzId2(),
                                baseDataRequest.getMultiHxzFactory3(),
                                baseDataRequest.getMultiHxzId3(),
                                baseDataRequest.getMultiHxzFactory4(),
                                baseDataRequest.getMultiHxzId4(),
                                baseDataRequest.getMultiHxzFactory5(),
                                baseDataRequest.getMultiHxzId5(),
                                baseDataRequest.getMultiHxzFactory6(),
                                baseDataRequest.getMultiHxzId6(),
                                baseDataRequest.getMultiHxzFactory7(),
                                baseDataRequest.getMultiHxzId7(),
                                baseDataRequest.getMultiHxzFactory8(),
                                baseDataRequest.getMultiHxzId8(),
                                baseDataRequest.getMultiHxzFactory9(),
                                baseDataRequest.getMultiHxzId9(),
                                baseDataRequest.getMultiHxzFactory10(),
                                baseDataRequest.getMultiHxzId10(),
                                baseDataRequest.getMultiHxzFactory11(),
                                baseDataRequest.getMultiHxzId11(),
                                baseDataRequest.getMultiHxzFactory12(),
                                baseDataRequest.getMultiHxzId12(),
                                baseDataRequest.getMultiHxzFactory13(),
                                baseDataRequest.getMultiHxzId13(),
                                baseDataRequest.getMultiHxzFactory14(),
                                baseDataRequest.getMultiHxzId14(),
                                baseDataRequest.getMultiHxzFactory15(),
                                baseDataRequest.getMultiHxzId15(),
                                baseDataRequest.getTC_MS_LoadCapacity().toString(),
                                baseDataRequest.getTC_ML_MaxScope().toString(),
                                baseDataRequest.getTC_Multiple(), baseDataRequest.getSelfCraneNo(),
                                baseDataRequest.getKeepWorkingHours().toString(),
                                baseDataRequest.getDifferInput().toString(),
                                baseDataRequest.getSettingMinHeight().toString(),
                                baseDataRequest.getWeightZero().toString(),
                                baseDataRequest.getWindSpeedZero().toString(),
                                baseDataRequest.getAngleZero().toString(),
                                baseDataRequest.getDipXZero().toString(),
                                baseDataRequest.getDipYZero().toString(),
                                baseDataRequest.getElevationAngleZero().toString());
                        //新加钢丝绳
                        baseDataCrane1.setSteelCableExist(baseDataRequest.getSteelCableExist());
                        baseDataCrane1
                                .setSteelCableCntTimes(baseDataRequest.getSteelCableCntTimes());
                        baseDataCrane1
                                .setSteelCableAlarmValue(baseDataRequest.getSteelCableAlarmValue());
                        baseDataCrane1.setSteelCableLevel1(baseDataRequest.getSteelCableLevel1());
                        baseDataCrane1.setSteelCableLevel2(baseDataRequest.getSteelCableLevel2());
                        baseDataCrane1.setSteelCableLevel3(baseDataRequest.getSteelCableLevel3());
                        baseDataCrane1.setSteelCableLevel4(baseDataRequest.getSteelCableLevel4());
                        baseDataCrane1.setSteelCableLevel5(baseDataRequest.getSteelCableLevel5());

                        BaseDataCrane baseDataCrane = baseDataCraneManager
                                .getBaseDataCraneByHxzId(HxzFactory, HxzId);
                        if (NullUtil.isNotEmpty(baseDataCrane)) {
                            //更新
                            baseDataCrane1.setRowId(baseDataCrane.getRowId());
                            baseDataCraneManager.save(baseDataCrane1);
                        } else {
                            //插入一条新值
                            baseDataCraneManager.save(baseDataCrane1);
                        }
                        //对外推送数据
                        if (HxzFactory.equals("HN01")) {
                            //海南参数设置
//                            haiNanPost.pushAlarmCfg(HxzFactory, HxzId, baseDataRequest);
                        }

                        String Result =
                                "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd
                                        + "$@";
                        log.info("Result:" + Result);
                        ctx.channel().writeAndFlush(Result).syncUninterruptibly();
                    }
                    if (VerNum.equals("1")) {
                        //升降机时
                        ElevatorBaseDataRequest elevatorBaseDataRequest = new ElevatorBaseDataRequest(
                                HxzFactory, HxzId, ProtocolVer, cmd,
                                Integer.valueOf(strings[5]), strings[6], strings[7],
                                strings[8], strings[9], strings[10], strings[11],
                                strings[12], strings[13], strings[14],
                                strings[15], strings[16], strings[17], strings[18], strings[19],
                                strings[20], Integer.valueOf(strings[21]),
                                Float.valueOf(strings[22]),
                                Float.valueOf(strings[23]),
                                Float.valueOf(strings[24]), Float.valueOf(strings[25]),
                                Integer.valueOf(strings[26]),
                                Integer.valueOf(strings[27]), Float.valueOf(strings[28]),
                                Float.valueOf(strings[29]), Float.valueOf(strings[30]),
                                Integer.valueOf(strings[31]), strings[32], strings[33]);
                        Integer WorkNoJ = 255;
                        String WorkNameJ = "";
                        String IdNo = "";
                        if (msgLength >= 35) {
                            WorkNoJ = Integer.valueOf(strings[33]);
                            WorkNameJ = strings[34];

                        }
                        if (msgLength > 35) {
                            IdNo = strings[35];
                        }
                        if (WorkNoJ == 255) {
                            WorkNameJ = "E69CAAE799BBE5BD95";
                        }
                        if (WorkNoJ == 0) {
                            WorkNameJ = "E7AEA1E79086E59198";
                        }

                        //更新操作记录
                        upoperatorData(HxzFactory, HxzId, WorkNoJ.toString(), WorkNameJ,
                                IdNo);
                        log.info("upoperatorData HxzFactory={},HxzId={}", HxzFactory, HxzId);
                        //更新ipData
                        chackIpData(HxzFactory, HxzId, clientIP);
                        BaseDataElevator baseDataElevatorNew = new BaseDataElevator(HxzFactory,
                                HxzId, elevatorBaseDataRequest.getCraneType().toString(),
                                elevatorBaseDataRequest.getIdExist(),
                                elevatorBaseDataRequest.getPeopleExist(),
                                elevatorBaseDataRequest.getWeightExist(),
                                elevatorBaseDataRequest.getSpeedExist(),
                                elevatorBaseDataRequest.getHeightExist(),
                                elevatorBaseDataRequest.getFloorExist(),
                                elevatorBaseDataRequest.getObliguityXExist(),
                                elevatorBaseDataRequest.getObliguityYExist(),
                                elevatorBaseDataRequest.getWindExist(),
                                elevatorBaseDataRequest.getGpsExist(),
                                elevatorBaseDataRequest.getWirelessExist(),
                                elevatorBaseDataRequest.getMotor1Exist(),
                                elevatorBaseDataRequest.getMotor2Exist(),
                                elevatorBaseDataRequest.getMotor3Exist(),
                                elevatorBaseDataRequest.getTopExist(),
                                elevatorBaseDataRequest.getRatedPeople().toString(),
                                elevatorBaseDataRequest.getRatedWeight().toString(),
                                elevatorBaseDataRequest.getRatedUpSpeed().toString(),
                                elevatorBaseDataRequest.getRatedDownSpeed().toString(),
                                elevatorBaseDataRequest.getRatedHeight().toString(),
                                elevatorBaseDataRequest.getMaxFloor().toString(),
                                elevatorBaseDataRequest.getMinFloor().toString(),
                                elevatorBaseDataRequest.getRatedObliguityX().toString(),
                                elevatorBaseDataRequest.getObliguityYExist(),
                                elevatorBaseDataRequest.getRatedWindSpeed().toString(),
                                elevatorBaseDataRequest.getRatedWindLevel().toString(),
                                "0",
                                "0");
                        BaseDataElevator baseDataElevator = baseDataElevatorManager
                                .getEntity(HxzFactory, HxzId);
                        if (NullUtil.isNotEmpty(baseDataElevator)) {
                            baseDataElevatorNew.setRowId(baseDataElevator.getRowId());
                            //更新
                            baseDataElevatorManager.save(baseDataElevatorNew);
                        } else {
                            baseDataElevatorManager.save(baseDataElevatorNew);
                        }
                        String Result =
                                "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd
                                        + "$@";
                        log.info("Result:" + Result);
                        ctx.channel().writeAndFlush(Result).syncUninterruptibly();
                    }
                    break;
                case "2":

                    log.info("心跳");
                    String Result1 = "";
                    //为塔机时
                    if (VerNum.equals("2") && online_flag.equals("1")) {
                        String ModifyServer = "0";
                        String ModifyKeySettings = "0";
                        String ModifyMultiSettings = "0";

                        //查询ControlDataCrane
                        ControlDataCrane controlDataCrane = controlDataCraneManager
                                .getControlDataCraneByHxzIdAndHxzFactory(HxzFactory, HxzId);
                        log.info("select ControlDataCrane HxzFactory={},HxzId={}", HxzFactory,
                                HxzId);
                        if (NullUtil.isNotEmpty(controlDataCrane)) {
                            ModifyServer = controlDataCrane.getModifyServer();
                        }
                        //查询 KeySettingsCrane
                        KeySettingsCrane keySettingsCrane = keySettingsCraneManager
                                .getKeySettingsCrane(HxzFactory, HxzId);
                        log.info("select KeySettingsCrane HxzFactory={},HxzId={}", HxzFactory,
                                HxzId);
                        if (NullUtil.isNotEmpty(keySettingsCrane)) {
                            ModifyKeySettings = keySettingsCrane.getModifyKeySettings();
                        }
                        //查询MultiSettingsCrane
                        MultiSettingsCrane multiSettingsCrane = multiSettingsCraneManager
                                .getEntityByHxzIdAndHxzFactory(HxzFactory, HxzId);
                        log.info("select MultiSettingsCrane HxzFactory={},HxzId={}", HxzFactory,
                                HxzId);
                        if (NullUtil.isNotEmpty(multiSettingsCrane)) {
                            ModifyMultiSettings = multiSettingsCrane.getModifyMultiSettings();
                        }
                        //检查ipData
                        chackIpData(HxzFactory, HxzId, clientIP);

                        //ModifyServer = '1'  调用pModifyServer  更新ControlDataCrane  ModifyServer = '0'
                        if (ModifyServer.equals("1")) {
                            if (NullUtil.isNotEmpty(controlDataCrane)) {
                                controlDataCraneManager.upModifyServer("0", HxzFactory, HxzId);
                            }
                            Result1 =
                                    "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + '9'
                                    + "$@";

                        } else if ("1".equals(ModifyKeySettings)) {
                            // if ModifyKeySettings = '1' 调用 pKeySettingsCraneV2
                            String KeepWorkingHours = "0";
                            String DifferInput = "0";
                            String SettingMinHeight = "0";
                            String WeightZero = "0";
                            String WindSpeedZero = "0";
                            String AngleZero = "0";
                            String DipXZero = "0";
                            String DipYZero = "0";
                            String ElevationAngleZero = "0";

                            if (NullUtil.isNotEmpty(keySettingsCrane)) {
                                KeepWorkingHours = keySettingsCrane.getKeepWorkingHours();
                                DifferInput = keySettingsCrane.getDifferInput();
                                SettingMinHeight = String
                                        .valueOf(keySettingsCrane.getSettingMinHeight());
                                WeightZero = keySettingsCrane.getModifyKeySettings();
                                WindSpeedZero = String
                                        .valueOf(keySettingsCrane.getWindSpeedZero());
                                AngleZero = String.valueOf(keySettingsCrane.getAngleZero());
                                DipXZero = String.valueOf(keySettingsCrane.getDipXZero());
                                DipYZero = String.valueOf(keySettingsCrane.getDipYZero());
                                ElevationAngleZero = String
                                        .valueOf(keySettingsCrane.getElevationAngleZero());
                            }
                            String WeightSetError = "0";
                            String WindSpeedSetError = "0";
                            String RangeSetError = "0";
                            String HeightSetError = "0";
                            String AngleSetError = "0";
                            String ObliguitySetError = "0";
                            String GpsSetError = "0";
                            String IdSetError = "0";

                            if (NullUtil.isNotEmpty(controlDataCrane)) {
                                WeightSetError = controlDataCrane.getWeightSetError();
                                WindSpeedSetError = controlDataCrane.getWindSpeedSetError();
                                RangeSetError = controlDataCrane.getRangeSetError();
                                HeightSetError = controlDataCrane.getHeightSetError();
                                AngleSetError = controlDataCrane.getAngleSetError();
                                ObliguitySetError = controlDataCrane.getObliguitySetError();
                                GpsSetError = controlDataCrane.getGpsSetError();
                                IdSetError = controlDataCrane.getIdSetError();
                            }
                            SettingMinHeight = String
                                    .format("%.5d", Float.valueOf(SettingMinHeight) * 100);
                            if (Float.valueOf(WeightZero) < 0) {
                                WeightZero = String
                                        .format("%.5d", 65536 + Float.valueOf(WeightZero) * 100);
                            } else {
                                WeightZero = String
                                        .format("%.5d", Float.valueOf(WeightZero) * 100);
                            }

                            if (Float.valueOf(WindSpeedZero) < 0) {
                                WindSpeedZero = String
                                        .format("%.5d", 65536 + Float.valueOf(WindSpeedZero) * 100);
                            } else {
                                WindSpeedZero = String
                                        .format("%.5d", Float.valueOf(WindSpeedZero) * 100);
                            }

                            if (Float.valueOf(AngleZero) < 0) {
                                AngleZero = String
                                        .format("%.5d", 65536 + Float.valueOf(AngleZero) * 100);
                            } else {
                                AngleZero = String
                                        .format("%.5d", Float.valueOf(AngleZero) * 100);
                            }

                            if (Float.valueOf(DipXZero) < 0) {
                                DipXZero = String
                                        .format("%.5d", 65536 + Float.valueOf(DipXZero) * 100);
                            } else {
                                DipXZero = String
                                        .format("%.5d", Float.valueOf(DipXZero) * 100);
                            }

                            if (Float.valueOf(DipYZero) < 0) {
                                DipYZero = String
                                        .format("%.5d", 65536 + Float.valueOf(DipYZero) * 100);
                            } else {
                                DipYZero = String
                                        .format("%.5d", Float.valueOf(DipYZero) * 100);
                            }

                            if (Float.valueOf(ElevationAngleZero) < 0) {
                                ElevationAngleZero = String
                                        .format("%.5d",
                                                65536 + Float.valueOf(ElevationAngleZero) * 100);
                            } else {
                                ElevationAngleZero = String
                                        .format("%.5d", Float.valueOf(ElevationAngleZero) * 100);
                            }

                            Result1 =
                                    "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + '6'
                                    + '$' + KeepWorkingHours + '$' + DifferInput + '$'
                                    + SettingMinHeight + '$' + WeightZero + '$' + WindSpeedZero
                                    + '$' + AngleZero + '$' + DipXZero + '$' + DipYZero + '$'
                                    + ElevationAngleZero
                                    + '$' + WeightSetError + '$' + WindSpeedSetError + '$'
                                    + RangeSetError + '$' + HeightSetError
                                    + '$' + AngleSetError + '$' + ObliguitySetError + '$'
                                    + GpsSetError + '$' + IdSetError
                                    + "$@";

                        } else if (!"0".equals(ModifyMultiSettings)) {
                            //单 ModifyMultiSettings <> '0' 时 调用pMultiSettingsCrane

                            String newModifyMultiSettings = "";
                            String MultiChannel = "";
                            String MultiNo = "";
                            String SelfCraneNo = "";
                            String CraneNoAck1 = "";
                            String CraneHeightLevelAck1 = "";
                            String MultiHxzIdAck1 = "";
                            String MultiCraneTypeAck1 = "";
                            String MultiXAck1 = "";
                            String MultiYAck1 = "";
                            String MultiFrontArmLengthAck1 = "";
                            String MultiBackArmLengthAck1 = "";
                            String CraneNoAck2 = "";
                            String CraneHeightLevelAck2 = "";
                            String MultiHxzIdAck2 = "";
                            String MultiCraneTypeAck2 = "";
                            String MultiXAck2 = "";
                            String MultiYAck2 = "";
                            String MultiFrontArmLengthAck2 = "";
                            String MultiBackArmLengthAck2 = "";
                            MultiChannel = multiSettingsCrane.getMultiChannel().toString();
                            MultiNo = multiSettingsCrane.getMultiNo().toString();
                            SelfCraneNo = multiSettingsCrane.getSelfCraneNo().toString();
                            switch (ModifyMultiSettings) {
                                case "1":
                                    newModifyMultiSettings = "2";
                                    CraneNoAck1 = multiSettingsCrane.getCraneNo0().toString();
                                    CraneHeightLevelAck1 = multiSettingsCrane
                                            .getCraneHeightLevel0().toString();
                                    MultiHxzIdAck1 = multiSettingsCrane.getMultiHxzId0();
                                    MultiCraneTypeAck1 = multiSettingsCrane
                                            .getMultiCraneType0();
                                    MultiXAck1 = multiSettingsCrane.getMultiX0().toString();
                                    MultiYAck1 = multiSettingsCrane.getMultiY0().toString();
                                    MultiFrontArmLengthAck1 = multiSettingsCrane
                                            .getMultiFrontArmLength0().toString();
                                    MultiBackArmLengthAck1 = multiSettingsCrane
                                            .getMultiBackArmLength0().toString();
                                    CraneNoAck2 = multiSettingsCrane.getCraneNo1().toString();
                                    CraneHeightLevelAck2 = multiSettingsCrane
                                            .getCraneHeightLevel1().toString();
                                    MultiHxzIdAck2 = multiSettingsCrane.getMultiHxzId1();
                                    MultiCraneTypeAck2 = multiSettingsCrane
                                            .getMultiCraneType1();
                                    MultiXAck2 = multiSettingsCrane.getMultiX1().toString();
                                    MultiYAck2 = multiSettingsCrane.getMultiY1().toString();
                                    MultiFrontArmLengthAck2 = multiSettingsCrane
                                            .getMultiFrontArmLength1().toString();
                                    MultiBackArmLengthAck2 = multiSettingsCrane
                                            .getMultiBackArmLength1().toString();
                                    break;
                                case "2":
                                    newModifyMultiSettings = "3";
                                    CraneNoAck1 = multiSettingsCrane.getCraneNo2().toString();
                                    CraneHeightLevelAck1 = multiSettingsCrane
                                            .getCraneHeightLevel2().toString();
                                    MultiHxzIdAck1 = multiSettingsCrane.getMultiHxzId2();
                                    MultiCraneTypeAck1 = multiSettingsCrane
                                            .getMultiCraneType2();
                                    MultiXAck1 = multiSettingsCrane.getMultiX2().toString();
                                    MultiYAck1 = multiSettingsCrane.getMultiY2().toString();
                                    MultiFrontArmLengthAck1 = multiSettingsCrane
                                            .getMultiFrontArmLength2().toString();
                                    MultiBackArmLengthAck1 = multiSettingsCrane
                                            .getMultiBackArmLength2().toString();
                                    CraneNoAck2 = multiSettingsCrane.getCraneNo3().toString();
                                    CraneHeightLevelAck2 = multiSettingsCrane
                                            .getCraneHeightLevel3().toString();
                                    MultiHxzIdAck2 = multiSettingsCrane.getMultiHxzId3();
                                    MultiCraneTypeAck2 = multiSettingsCrane
                                            .getMultiCraneType3();
                                    MultiXAck2 = multiSettingsCrane.getMultiX3().toString();
                                    MultiYAck2 = multiSettingsCrane.getMultiY3().toString();
                                    MultiFrontArmLengthAck2 = multiSettingsCrane
                                            .getMultiFrontArmLength3().toString();
                                    MultiBackArmLengthAck2 = multiSettingsCrane
                                            .getMultiBackArmLength3().toString();

                                    break;
                                case "3":
                                    newModifyMultiSettings = "4";
                                    CraneNoAck1 = multiSettingsCrane.getCraneNo4().toString();
                                    CraneHeightLevelAck1 = multiSettingsCrane
                                            .getCraneHeightLevel4().toString();
                                    MultiHxzIdAck1 = multiSettingsCrane.getMultiHxzId4();
                                    MultiCraneTypeAck1 = multiSettingsCrane
                                            .getMultiCraneType4();
                                    MultiXAck1 = multiSettingsCrane.getMultiX4().toString();
                                    MultiYAck1 = multiSettingsCrane.getMultiY4().toString();
                                    MultiFrontArmLengthAck1 = multiSettingsCrane
                                            .getMultiFrontArmLength4().toString();
                                    MultiBackArmLengthAck1 = multiSettingsCrane
                                            .getMultiBackArmLength4().toString();
                                    CraneNoAck2 = multiSettingsCrane.getCraneNo5().toString();
                                    CraneHeightLevelAck2 = multiSettingsCrane
                                            .getCraneHeightLevel5().toString();
                                    MultiHxzIdAck2 = multiSettingsCrane.getMultiHxzId5();
                                    MultiCraneTypeAck2 = multiSettingsCrane
                                            .getMultiCraneType5();
                                    MultiXAck2 = multiSettingsCrane.getMultiX5().toString();
                                    MultiYAck2 = multiSettingsCrane.getMultiY5().toString();
                                    MultiFrontArmLengthAck2 = multiSettingsCrane
                                            .getMultiFrontArmLength5().toString();
                                    MultiBackArmLengthAck2 = multiSettingsCrane
                                            .getMultiBackArmLength5().toString();
                                    break;
                                case "4":
                                    newModifyMultiSettings = "5";
                                    CraneNoAck1 = multiSettingsCrane.getCraneNo6().toString();
                                    CraneHeightLevelAck1 = multiSettingsCrane
                                            .getCraneHeightLevel6().toString();
                                    MultiHxzIdAck1 = multiSettingsCrane.getMultiHxzId6();
                                    MultiCraneTypeAck1 = multiSettingsCrane
                                            .getMultiCraneType6();
                                    MultiXAck1 = multiSettingsCrane.getMultiX6().toString();
                                    MultiYAck1 = multiSettingsCrane.getMultiY6().toString();
                                    MultiFrontArmLengthAck1 = multiSettingsCrane
                                            .getMultiFrontArmLength6().toString();
                                    MultiBackArmLengthAck1 = multiSettingsCrane
                                            .getMultiBackArmLength6().toString();
                                    CraneNoAck2 = multiSettingsCrane.getCraneNo7().toString();
                                    CraneHeightLevelAck2 = multiSettingsCrane
                                            .getCraneHeightLevel7().toString();
                                    MultiHxzIdAck2 = multiSettingsCrane.getMultiHxzId7();
                                    MultiCraneTypeAck2 = multiSettingsCrane
                                            .getMultiCraneType7();
                                    MultiXAck2 = multiSettingsCrane.getMultiX7().toString();
                                    MultiYAck2 = multiSettingsCrane.getMultiY7().toString();
                                    MultiFrontArmLengthAck2 = multiSettingsCrane
                                            .getMultiFrontArmLength7().toString();
                                    MultiBackArmLengthAck2 = multiSettingsCrane
                                            .getMultiBackArmLength7().toString();
                                    break;
                                case "5":
                                    newModifyMultiSettings = "6";
                                    CraneNoAck1 = multiSettingsCrane.getCraneNo8().toString();
                                    CraneHeightLevelAck1 = multiSettingsCrane
                                            .getCraneHeightLevel8().toString();
                                    MultiHxzIdAck1 = multiSettingsCrane.getMultiHxzId8();
                                    MultiCraneTypeAck1 = multiSettingsCrane
                                            .getMultiCraneType8();
                                    MultiXAck1 = multiSettingsCrane.getMultiX8().toString();
                                    MultiYAck1 = multiSettingsCrane.getMultiY8().toString();
                                    MultiFrontArmLengthAck1 = multiSettingsCrane
                                            .getMultiFrontArmLength8().toString();
                                    MultiBackArmLengthAck1 = multiSettingsCrane
                                            .getMultiBackArmLength8().toString();
                                    CraneNoAck2 = multiSettingsCrane.getCraneNo9().toString();
                                    CraneHeightLevelAck2 = multiSettingsCrane
                                            .getCraneHeightLevel9().toString();
                                    MultiHxzIdAck2 = multiSettingsCrane.getMultiHxzId9();
                                    MultiCraneTypeAck2 = multiSettingsCrane
                                            .getMultiCraneType9();
                                    MultiXAck2 = multiSettingsCrane.getMultiX9().toString();
                                    MultiYAck2 = multiSettingsCrane.getMultiY9().toString();
                                    MultiFrontArmLengthAck2 = multiSettingsCrane
                                            .getMultiFrontArmLength9().toString();
                                    MultiBackArmLengthAck2 = multiSettingsCrane
                                            .getMultiBackArmLength9().toString();
                                    break;
                                case "6":
                                    newModifyMultiSettings = "7";
                                    CraneNoAck1 = multiSettingsCrane.getCraneNo10().toString();
                                    CraneHeightLevelAck1 = multiSettingsCrane
                                            .getCraneHeightLevel10().toString();
                                    MultiHxzIdAck1 = multiSettingsCrane.getMultiHxzId10();
                                    MultiCraneTypeAck1 = multiSettingsCrane
                                            .getMultiCraneType10();
                                    MultiXAck1 = multiSettingsCrane.getMultiX10().toString();
                                    MultiYAck1 = multiSettingsCrane.getMultiY10().toString();
                                    MultiFrontArmLengthAck1 = multiSettingsCrane
                                            .getMultiFrontArmLength10().toString();
                                    MultiBackArmLengthAck1 = multiSettingsCrane
                                            .getMultiBackArmLength10().toString();
                                    CraneNoAck2 = multiSettingsCrane.getCraneNo11().toString();
                                    CraneHeightLevelAck2 = multiSettingsCrane
                                            .getCraneHeightLevel11().toString();
                                    MultiHxzIdAck2 = multiSettingsCrane.getMultiHxzId11();
                                    MultiCraneTypeAck2 = multiSettingsCrane
                                            .getMultiCraneType11();
                                    MultiXAck2 = multiSettingsCrane.getMultiX11().toString();
                                    MultiYAck2 = multiSettingsCrane.getMultiY11().toString();
                                    MultiFrontArmLengthAck2 = multiSettingsCrane
                                            .getMultiFrontArmLength11().toString();
                                    MultiBackArmLengthAck2 = multiSettingsCrane
                                            .getMultiBackArmLength11().toString();
                                    break;
                                case "7":
                                    newModifyMultiSettings = "8";
                                    CraneNoAck1 = multiSettingsCrane.getCraneNo12().toString();
                                    CraneHeightLevelAck1 = multiSettingsCrane
                                            .getCraneHeightLevel12().toString();
                                    MultiHxzIdAck1 = multiSettingsCrane.getMultiHxzId12();
                                    MultiCraneTypeAck1 = multiSettingsCrane
                                            .getMultiCraneType12();
                                    MultiXAck1 = multiSettingsCrane.getMultiX12().toString();
                                    MultiYAck1 = multiSettingsCrane.getMultiY12().toString();
                                    MultiFrontArmLengthAck1 = multiSettingsCrane
                                            .getMultiFrontArmLength12().toString();
                                    MultiBackArmLengthAck1 = multiSettingsCrane
                                            .getMultiBackArmLength12().toString();
                                    CraneNoAck2 = multiSettingsCrane.getCraneNo13().toString();
                                    CraneHeightLevelAck2 = multiSettingsCrane
                                            .getCraneHeightLevel13().toString();
                                    MultiHxzIdAck2 = multiSettingsCrane.getMultiHxzId13();
                                    MultiCraneTypeAck2 = multiSettingsCrane
                                            .getMultiCraneType13();
                                    MultiXAck2 = multiSettingsCrane.getMultiX13().toString();
                                    MultiYAck2 = multiSettingsCrane.getMultiY13().toString();
                                    MultiFrontArmLengthAck2 = multiSettingsCrane
                                            .getMultiFrontArmLength13().toString();
                                    MultiBackArmLengthAck2 = multiSettingsCrane
                                            .getMultiBackArmLength13().toString();
                                    break;
                                case "8":
                                    newModifyMultiSettings = "9";
                                    CraneNoAck1 = multiSettingsCrane.getCraneNo14().toString();
                                    CraneHeightLevelAck1 = multiSettingsCrane
                                            .getCraneHeightLevel14().toString();
                                    MultiHxzIdAck1 = multiSettingsCrane.getMultiHxzId14();
                                    MultiCraneTypeAck1 = multiSettingsCrane
                                            .getMultiCraneType14();
                                    MultiXAck1 = multiSettingsCrane.getMultiX14().toString();
                                    MultiYAck1 = multiSettingsCrane.getMultiY14().toString();
                                    MultiFrontArmLengthAck1 = multiSettingsCrane
                                            .getMultiFrontArmLength14().toString();
                                    MultiBackArmLengthAck1 = multiSettingsCrane
                                            .getMultiBackArmLength14().toString();
                                    CraneNoAck2 = multiSettingsCrane.getCraneNo15().toString();
                                    CraneHeightLevelAck2 = multiSettingsCrane
                                            .getCraneHeightLevel15().toString();
                                    MultiHxzIdAck2 = multiSettingsCrane.getMultiHxzId15();
                                    MultiCraneTypeAck2 = multiSettingsCrane
                                            .getMultiCraneType15();
                                    MultiXAck2 = multiSettingsCrane.getMultiX15().toString();
                                    MultiYAck2 = multiSettingsCrane.getMultiY15().toString();
                                    MultiFrontArmLengthAck2 = multiSettingsCrane
                                            .getMultiFrontArmLength15().toString();
                                    MultiBackArmLengthAck2 = multiSettingsCrane
                                            .getMultiBackArmLength15().toString();
                                    break;
                            }
                            multiSettingsCraneManager
                                    .upDateModifyMultiSettings(newModifyMultiSettings, HxzFactory,
                                            HxzId);
                            log.info(
                                    "update MultiSettingsCrane newModifyMultiSettings={} HxzFactory={},HxzId={}",
                                    newModifyMultiSettings, HxzFactory,
                                    HxzId);

                            if (NullUtil.isEmpty(CraneNoAck1)) {
                                CraneNoAck1 = "0";
                            }
                            if (NullUtil.isEmpty(CraneHeightLevelAck1)) {
                                CraneHeightLevelAck1 = "1";
                            }
                            if (NullUtil.isEmpty(MultiHxzIdAck1)) {
                                MultiHxzIdAck1 = "00000000";
                            }
                            if (NullUtil.isEmpty(MultiCraneTypeAck1)) {
                                MultiCraneTypeAck1 = "1";
                            }
                            if (NullUtil.isEmpty(MultiXAck1)) {
                                MultiXAck1 = "0";
                            }
                            if (NullUtil.isEmpty(MultiYAck1)) {
                                MultiYAck1 = "0";
                            }
                            if (NullUtil.isEmpty(MultiFrontArmLengthAck1)) {
                                MultiFrontArmLengthAck1 = "0";
                            }
                            if (NullUtil.isEmpty(MultiBackArmLengthAck1)) {
                                MultiBackArmLengthAck1 = "0";
                            }
                            if (NullUtil.isEmpty(CraneNoAck2)) {
                                CraneNoAck2 = "0";
                            }
                            if (NullUtil.isEmpty(CraneHeightLevelAck2)) {
                                CraneHeightLevelAck2 = "1";
                            }
                            if (NullUtil.isEmpty(MultiHxzIdAck2)) {
                                MultiHxzIdAck2 = "00000000";
                            }
                            if (NullUtil.isEmpty(MultiCraneTypeAck2)) {
                                MultiCraneTypeAck2 = "1";
                            }
                            if (NullUtil.isEmpty(MultiXAck2)) {
                                MultiXAck2 = "0";
                            }
                            if (NullUtil.isEmpty(MultiYAck2)) {
                                MultiYAck2 = "0";
                            }
                            if (NullUtil.isEmpty(MultiFrontArmLengthAck2)) {
                                MultiFrontArmLengthAck2 = "0";
                            }
                            if (NullUtil.isEmpty(MultiBackArmLengthAck2)) {
                                MultiBackArmLengthAck2 = "0";
                            }

                            MultiChannel = String
                                    .format("%.2d", Integer.valueOf(MultiChannel));
                            MultiNo = String
                                    .format("%.2d", Integer.valueOf(MultiNo));
                            SelfCraneNo = String
                                    .format("%.2d", Integer.valueOf(SelfCraneNo));
                            CraneNoAck1 = String
                                    .format("%.2d", Integer.valueOf(CraneNoAck1));
                            CraneHeightLevelAck1 = String
                                    .format("%.2d", Integer.valueOf(CraneHeightLevelAck1));

                            MultiXAck1 = String
                                    .format("%.5d", Float.valueOf(MultiXAck1) * 100);
                            MultiYAck1 = String
                                    .format("%.5d", Float.valueOf(MultiYAck1) * 100);
                            MultiFrontArmLengthAck1 = String
                                    .format("%.5d", Float.valueOf(MultiFrontArmLengthAck1) * 100);
                            MultiBackArmLengthAck1 = String
                                    .format("%.5d", Float.valueOf(MultiBackArmLengthAck1) * 100);
                            CraneNoAck2 = String
                                    .format("%.2d", Integer.valueOf(CraneNoAck2));
                            CraneHeightLevelAck2 = String
                                    .format("%.2d", Integer.valueOf(CraneHeightLevelAck2));
                            MultiXAck2 = String
                                    .format("%.5d", Float.valueOf(MultiXAck2) * 100);
                            MultiYAck2 = String
                                    .format("%.5d", Float.valueOf(MultiYAck2) * 100);
                            MultiFrontArmLengthAck2 = String
                                    .format("%.5d", Float.valueOf(MultiFrontArmLengthAck2) * 100);
                            MultiBackArmLengthAck2 = String
                                    .format("%.5d", Float.valueOf(MultiBackArmLengthAck2) * 100);

                            String cmd_temp = String.valueOf(
                                    (Integer.valueOf('A') + (Integer.valueOf(ModifyMultiSettings)
                                            - 1)));
                            Result1 = "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$'
                                    + cmd_temp
                                    + '$' + MultiChannel + '$' + MultiNo + '$' + SelfCraneNo
                                    + '$' + CraneNoAck1 + '$' + CraneHeightLevelAck1 + '$'
                                    + MultiHxzIdAck1 + '$' + MultiCraneTypeAck1
                                    + '$' + MultiXAck1 + '$' + MultiYAck1 + '$'
                                    + MultiFrontArmLengthAck1 + '$' + MultiBackArmLengthAck1
                                    + '$' + CraneNoAck2 + '$' + CraneHeightLevelAck2 + '$'
                                    + MultiHxzIdAck2 + '$' + MultiCraneTypeAck2
                                    + '$' + MultiXAck2 + '$' + MultiYAck2 + '$'
                                    + MultiFrontArmLengthAck2 + '$' + MultiBackArmLengthAck2
                                    + "$@";

                        }
                        if (Result1.equals("")) {
                            Result1 =
                                    "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd
                                    + "$@";
                        }
                        log.info("Result:" + Result1);
                        ctx.channel().writeAndFlush(Result1).syncUninterruptibly();
                    }
                    //为升降机时
                    if (VerNum.equals("1") && online_flag.equals("1")) {
                        chackIpData(HxzFactory, HxzId, clientIP);
                        String Result =
                                "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + '2'
                                        + "$@";
                        log.info("Result:" + Result);
                        ctx.channel().writeAndFlush(Result).syncUninterruptibly();
                    }
                    break;
                case "3":
                    System.out.println("实时数据命令");
                    log.info("实时数据命令");
                    if (VerNum.equals("2") && online_flag.equals("1")) {
                        RealtimeDataRequest realtimeDataRequest = new RealtimeDataRequest(
                                strings[1], strings[2], strings[3], strings[4],
                                strings[5], strings[6], Integer.valueOf(strings[7]),
                                Integer.valueOf(strings[8]),
                                Float.valueOf(strings[9]), Float.valueOf(strings[10]),
                                Float.valueOf(strings[11]), Float.valueOf(strings[12]),
                                Integer.valueOf(strings[13]), Float.valueOf(strings[14]),
                                Float.valueOf(strings[15]), Float.valueOf(strings[16]),
                                Float.valueOf(strings[17]), strings[18], strings[19], strings[20],
                                strings[21], strings[22], strings[23], strings[24],
                                strings[25], strings[26], strings[27], strings[28],
                                strings[29], strings[30], strings[31], strings[32],
                                strings[33], strings[34], strings[35], strings[36],
                                Integer.valueOf(strings[37]), Integer.valueOf(strings[38]),
                                Integer.valueOf(strings[39]), strings[40],
                                strings[41], Float.valueOf(strings[42]), Float.valueOf(strings[43]),
                                strings[44],
                                strings[45], Float.valueOf(strings[46]), Float.valueOf(strings[47]),
                                strings[48],
                                strings[49], Float.valueOf(strings[50]), Float.valueOf(strings[51]),
                                strings[52],
                                strings[53], Float.valueOf(strings[54]), Float.valueOf(strings[55]),
                                strings[56],
                                strings[57], Float.valueOf(strings[58]), Float.valueOf(strings[59]),
                                strings[60],
                                strings[61], Float.valueOf(strings[62]), Float.valueOf(strings[63]),
                                strings[64],
                                strings[65], Float.valueOf(strings[66]), Float.valueOf(strings[67]),
                                strings[68],
                                strings[69], Float.valueOf(strings[70]), Float.valueOf(strings[71]),
                                strings[72],
                                strings[73], Float.valueOf(strings[74]), Float.valueOf(strings[75]),
                                strings[76],
                                strings[77], Float.valueOf(strings[78]), Float.valueOf(strings[79]),
                                strings[80],
                                strings[81], Float.valueOf(strings[82]), Float.valueOf(strings[83]),
                                strings[84],
                                strings[85], Float.valueOf(strings[86]), Float.valueOf(strings[87]),
                                strings[88],
                                strings[89], Float.valueOf(strings[90]), Float.valueOf(strings[91]),
                                strings[92],
                                strings[93], Float.valueOf(strings[94]), Float.valueOf(strings[95]),
                                strings[96],
                                strings[97], Float.valueOf(strings[98]), Float.valueOf(strings[99]),
                                strings[100],
                                strings[101], Float.valueOf(strings[102]),
                                Float.valueOf(strings[103]), strings[104],
                                Integer.valueOf(strings[105]), strings[106], strings[107],
                                strings[108],
                                Float.valueOf(strings[109]), Float.valueOf(strings[110]),
                                strings[111], strings[112],
                                strings[113], strings[114], strings[115], strings[116],
                                strings[117], strings[118], strings[119], strings[120],
                                strings[121], strings[122], strings[123]);
                        String ObliguityXAlarm = "0";
                        String ObliguityYAlarm = "0";
                        Float SteelCableHeight = 0.0f;
                        Integer SteelCableValue = 0;
                        Integer SteelCableLevel = 0;
                        //海南版本新加钢丝绳
                        if (ChildProtocol.compareTo("G") >= 0 && msgLength > 127) {
                            realtimeDataRequest.setObliguityXAlarm(strings[124]);
                            realtimeDataRequest.setObliguityYAlarm(strings[125]);
                            realtimeDataRequest.setSteelCableHeight(Float.valueOf(strings[126]));
                            realtimeDataRequest.setSteelCableValue(Integer.valueOf(strings[127]));
                            realtimeDataRequest.setSteelCableLevel(Integer.valueOf(strings[128]));
                            ObliguityXAlarm = realtimeDataRequest.getObliguityXAlarm();
                            ObliguityYAlarm = realtimeDataRequest.getObliguityYAlarm();
                            SteelCableHeight = realtimeDataRequest.getSteelCableHeight();
                            SteelCableValue = realtimeDataRequest.getSteelCableValue();
                            SteelCableLevel = realtimeDataRequest.getSteelCableLevel();
                        }

                        String WorkNo = realtimeDataRequest.getWorkNo().toString();

                        Float WindSpeed = realtimeDataRequest.getWindSpeed();
                        String WorkName = "";
                        Integer WindLevel = realtimeDataRequest.getWindLevel();
                        Float WindSpeedFloat;
                        if (WorkNo.equals("255")) {
                            WorkName = "E69CAAE799BBE5BD95";
                            WindSpeedFloat = WindSpeed * 100;
                            if (WindSpeedFloat <= 20) {
                                WindLevel = 0;
                            } else if (WindSpeedFloat <= 150) {
                                WindLevel = 1;
                            } else if (WindSpeedFloat <= 330) {
                                WindLevel = 2;
                            } else if (WindSpeedFloat <= 540) {
                                WindLevel = 3;
                            } else if (WindSpeedFloat <= 790) {
                                WindLevel = 4;
                            } else if (WindSpeedFloat <= 1070) {
                                WindLevel = 5;
                            } else if (WindSpeedFloat <= 1380) {
                                WindLevel = 6;
                            } else if (WindSpeedFloat <= 1710) {
                                WindLevel = 7;
                            } else if (WindSpeedFloat <= 2070) {
                                WindLevel = 8;
                            } else if (WindSpeedFloat <= 2440) {
                                WindLevel = 9;
                            } else if (WindSpeedFloat <= 2840) {
                                WindLevel = 10;
                            } else if (WindSpeedFloat <= 3260) {
                                WindLevel = 11;
                            } else {
                                WindLevel = 12;
                            }
                        }
                        realtimeDataRequest.setWindLevel(WindLevel);
                        if (WorkNo.equals("0")) {
                            WorkName = "E7AEA1E79086E59198";
                        }
                        realtimeDataRequest.setName(WorkName);
                        String old_rtime;
                        String RTime =
                                realtimeDataRequest.getDate() + ' ' + realtimeDataRequest.getTime();
                        int time_diff = 0;

                        RealtimeDataCrane realtimeDataCrane = realtimeDataCraneManager
                                .getEntityByHxzId(HxzFactory, HxzId);
                        log.info("select RealtimeDataCrane  HxzFactory={},HxzId={}", HxzFactory,
                                HxzId);
                        if (NullUtil.isNotEmpty(realtimeDataCrane)) {
                            old_rtime = realtimeDataCrane.getRTime();
                            time_diff = DateUtils
                                    .differentDaysByMillisecond(DateUtils.StrToDate(RTime),
                                            DateUtils.StrToDate(old_rtime));
                        }

                        //检查更新IP数据
                        chackIpData(HxzFactory, HxzId, clientIP);

                        String OnlineTime = "";
                        RuntimeData runtimeData = runtimeDataManager
                                .getIpByHxzIdAndHxzFactory(HxzFactory, HxzId);

                        Integer RunTime = 0;

                        if (NullUtil.isNotEmpty(runtimeData) && NullUtil
                                .isNotEmpty(runtimeData.getOnlineTime())) {
                            OnlineTime = runtimeData.getOnlineTime();
                        }
                        LoginData loginData2 = loginDataManager
                                .getLoginDataByHxzIdAndHxzFactory(HxzFactory, HxzId);
                        log.info("select LoginData  HxzFactory={},HxzId={}", HxzFactory, HxzId);
                        if (NullUtil.isNotEmpty(loginData2) && NullUtil
                                .isNotEmpty(loginData2.getCraneId())) {

                            RunTime = DateUtils
                                    .differentDaysByMillisecond(
                                            DateUtils.StrToDate(OnlineTime),
                                            DateUtils.StrToDate(RTime));

//                            RuntimeData_' + @TableName  SET RunTime=DATEDIFF(ss,OnlineTime,''' + @RTime + ''')
                            runtimeDataManager
                                    .upRunTimeSql(loginData2.getCraneId(), RTime, HxzFactory,
                                            HxzId);
                            log.info("upRunTimeSql  set  RunTime {}", RTime);
                            runtimeDataManager
                                    .upRuntime(runtimeData.getOnlineTime(), RunTime.toString(),
                                            HxzFactory, HxzId);
                            log.info("update runtimeData  RunTime={},HxzId={}", RunTime, HxzId);
                            //更新workinf
                            upWorkin(HxzFactory, HxzId, OnlineTime, RunTime.toString());
                            //insert realtimedate_table
                            realtimeDataCraneManager
                                    .insertRealtimeDataSql(loginData2.getCraneId(), HxzFactory,
                                    HxzId, RTime, Integer.valueOf(WorkNo),
                                    realtimeDataRequest.getMultiple(),
                                            realtimeDataRequest.getMoment().toString(),
                                            realtimeDataRequest.getWeight().toString(),
                                            realtimeDataRequest.getRatedWeight().toString(),
                                            realtimeDataRequest.getWindSpeed().toString(),
                                            WindLevel,
                                            realtimeDataRequest.getRRange().toString(),
                                            realtimeDataRequest.getHeight().toString(),
                                            realtimeDataRequest.getAngle().toString(),
                                            realtimeDataRequest.getObliguity().toString(),
                                    realtimeDataRequest.getNoError(),
                                    realtimeDataRequest.getWeightError(),
                                    realtimeDataRequest.getWindSpeedError(),
                                    realtimeDataRequest.getRangeError(),
                                    realtimeDataRequest.getHeightError(),
                                    realtimeDataRequest.getAngleError(),
                                    realtimeDataRequest.getObliguityError(),
                                    realtimeDataRequest.getGpsError(),
                                    realtimeDataRequest.getIdError(),
                                    realtimeDataRequest.getNoAlarm(),
                                    realtimeDataRequest.getMomentAlarm(),
                                    realtimeDataRequest.getWindSpeedAlarm(),
                                    realtimeDataRequest.getHeightAlarm(),
                                    realtimeDataRequest.getMinRangeAlarm(),
                                    realtimeDataRequest.getMaxRangeAlarm(),
                                    realtimeDataRequest.getPosAngleAlarm(),
                                    realtimeDataRequest.getNegAngleAlarm(),
                                    realtimeDataRequest.getObliguityAlarm(),
                                    realtimeDataRequest.getForbidEntryAlarm(),
                                    realtimeDataRequest.getForbidSuspend2Alarm(),
                                    realtimeDataRequest.getForbidSuspend3Alarm(),
                                    realtimeDataRequest.getForbidSuspend4Alarm(),
                                    realtimeDataRequest.getMultiAlarmAll(),
                                    realtimeDataRequest.getMultiOnline0(),
                                            realtimeDataRequest.getMultiRange0().toString(),
                                            realtimeDataRequest.getMultiAngle0().toString(),
                                    realtimeDataRequest.getMultiAlarm0(),
                                    realtimeDataRequest.getMultiOnline1(),
                                            realtimeDataRequest.getMultiRange1().toString(),
                                            realtimeDataRequest.getMultiAngle1().toString(),
                                    realtimeDataRequest.getMultiAlarm1(),
                                    realtimeDataRequest.getMultiOnline2(),
                                            realtimeDataRequest.getMultiRange2().toString(),
                                            realtimeDataRequest.getMultiAngle2().toString(),
                                    realtimeDataRequest.getMultiAlarm2(),
                                    realtimeDataRequest.getMultiOnline3(),
                                            realtimeDataRequest.getMultiRange3().toString(),
                                            realtimeDataRequest.getMultiAngle3().toString(),
                                    realtimeDataRequest.getMultiAlarm3(),
                                    realtimeDataRequest.getMultiOnline4(),
                                            realtimeDataRequest.getMultiRange4().toString(),
                                            realtimeDataRequest.getMultiAngle4().toString(),
                                    realtimeDataRequest.getMultiAlarm4(),
                                    realtimeDataRequest.getMultiOnline5(),
                                            realtimeDataRequest.getMultiRange5().toString(),
                                            realtimeDataRequest.getMultiAngle5().toString(),
                                    realtimeDataRequest.getMultiAlarm5(),
                                    realtimeDataRequest.getMultiOnline6(),
                                            realtimeDataRequest.getMultiRange6().toString(),
                                            realtimeDataRequest.getMultiAngle6().toString(),
                                    realtimeDataRequest.getMultiAlarm6(),
                                    realtimeDataRequest.getMultiOnline7(),
                                            realtimeDataRequest.getMultiRange7().toString(),
                                            realtimeDataRequest.getMultiAngle7().toString(),
                                    realtimeDataRequest.getMultiAlarm7(),
                                    realtimeDataRequest.getMultiOnline8(),
                                            realtimeDataRequest.getMultiRange8().toString(),
                                            realtimeDataRequest.getMultiAngle8().toString(),
                                    realtimeDataRequest.getMultiAlarm8(),
                                    realtimeDataRequest.getMultiOnline9(),
                                            realtimeDataRequest.getMultiRange9().toString(),
                                            realtimeDataRequest.getMultiAngle9().toString(),
                                    realtimeDataRequest.getMultiAlarm9(),
                                    realtimeDataRequest.getMultiOnline10(),
                                            realtimeDataRequest.getMultiRange10().toString(),
                                            realtimeDataRequest.getMultiAngle10().toString(),
                                    realtimeDataRequest.getMultiAlarm10(),
                                    realtimeDataRequest.getMultiOnline11(),
                                            realtimeDataRequest.getMultiRange11().toString(),
                                            realtimeDataRequest.getMultiAngle11().toString(),
                                    realtimeDataRequest.getMultiAlarm11(),
                                    realtimeDataRequest.getMultiOnline12(),
                                            realtimeDataRequest.getMultiRange12().toString(),
                                            realtimeDataRequest.getMultiAngle12().toString(),
                                    realtimeDataRequest.getMultiAlarm12(),
                                    realtimeDataRequest.getMultiOnline13(),
                                            realtimeDataRequest.getMultiRange13().toString(),
                                            realtimeDataRequest.getMultiAngle13().toString(),
                                    realtimeDataRequest.getMultiAlarm13(),
                                    realtimeDataRequest.getMultiOnline14(),
                                            realtimeDataRequest.getMultiRange14().toString(),
                                            realtimeDataRequest.getMultiAngle14().toString(),
                                    realtimeDataRequest.getMultiAlarm14(),
                                    realtimeDataRequest.getMultiOnline15(),
                                            realtimeDataRequest.getMultiRange15().toString(),
                                            realtimeDataRequest.getMultiAngle15().toString(),
                                            realtimeDataRequest.getMultiAlarm15(),
                                            realtimeDataRequest.getWorkStatus(),
                                    realtimeDataRequest.getName(),
                                    realtimeDataRequest.getIdNo(),
                                    realtimeDataRequest.getMotorStatus(),
                                            realtimeDataRequest.getObliguityX().toString(),
                                            realtimeDataRequest.getObliguityY().toString(),
                                    realtimeDataRequest.getForbidEntryPosAlarm(),
                                    realtimeDataRequest.getForbidEntryNegAlarm(),
                                    realtimeDataRequest.getForbidSuspend2PosAlarm(),
                                    realtimeDataRequest.getForbidSuspend2NegAlarm(),
                                    realtimeDataRequest.getForbidSuspend2OutAlarm(),
                                    realtimeDataRequest.getForbidSuspend4PosAlarm(),
                                    realtimeDataRequest.getForbidSuspend4NegAlarm(),
                                    realtimeDataRequest.getForbidSuspend4OutAlarm(),
                                    realtimeDataRequest.getForbidSuspend4BackAlarm(),
                                    realtimeDataRequest.getMultiPosAlarm(),
                                    realtimeDataRequest.getMultiNegAlarm(),
                                    realtimeDataRequest.getMultiOutAlarm(),
                                            realtimeDataRequest.getMultiBackAlarm(),
                                            ObliguityXAlarm,
                                            ObliguityYAlarm,
                                            SteelCableHeight.toString(),
                                            SteelCableValue,
                                            SteelCableLevel);

                            log.info("insert insertRealtimeDataSql ");
                            //查询更新RealtimeDataCrane 数据
                            RealtimeDataCrane realtimeDataCrane1 = realtimeDataCraneManager
                                    .getEntityByHxzId(HxzFactory, HxzId);
                            log.info("select   RealtimeDataCrane  HxzId={}", HxzId);
                            RealtimeDataCrane realtimeDataCrane2 = new RealtimeDataCrane(HxzFactory,
                                    HxzId, RTime, Integer.valueOf(WorkNo),
                                    realtimeDataRequest.getMultiple(),
                                    realtimeDataRequest.getMoment(),
                                    realtimeDataRequest.getWeight(),
                                    realtimeDataRequest.getRatedWeight(),
                                    realtimeDataRequest.getWindSpeed(), WindLevel,
                                    realtimeDataRequest.getRRange(),
                                    realtimeDataRequest.getHeight(), realtimeDataRequest.getAngle(),
                                    realtimeDataRequest.getObliguity(),
                                    realtimeDataRequest.getNoError(),
                                    realtimeDataRequest.getWeightError(),
                                    realtimeDataRequest.getWindSpeedError(),
                                    realtimeDataRequest.getRangeError(),
                                    realtimeDataRequest.getHeightError(),
                                    realtimeDataRequest.getAngleError(),
                                    realtimeDataRequest.getObliguityError(),
                                    realtimeDataRequest.getGpsError(),
                                    realtimeDataRequest.getIdError(),
                                    realtimeDataRequest.getNoAlarm(),
                                    realtimeDataRequest.getMomentAlarm(),
                                    realtimeDataRequest.getWindSpeedAlarm(),
                                    realtimeDataRequest.getHeightAlarm(),
                                    realtimeDataRequest.getMinRangeAlarm(),
                                    realtimeDataRequest.getMaxRangeAlarm(),
                                    realtimeDataRequest.getPosAngleAlarm(),
                                    realtimeDataRequest.getNegAngleAlarm(),
                                    realtimeDataRequest.getObliguityAlarm(),
                                    realtimeDataRequest.getForbidEntryAlarm(),
                                    realtimeDataRequest.getForbidSuspend2Alarm(),
                                    realtimeDataRequest.getForbidSuspend3Alarm(),
                                    realtimeDataRequest.getForbidSuspend4Alarm(),
                                    realtimeDataRequest.getMultiAlarmAll(),
                                    realtimeDataRequest.getMultiOnline0(),
                                    realtimeDataRequest.getMultiRange0(),
                                    realtimeDataRequest.getMultiAngle0(),
                                    realtimeDataRequest.getMultiAlarm0(),
                                    realtimeDataRequest.getMultiOnline1(),
                                    realtimeDataRequest.getMultiRange1(),
                                    realtimeDataRequest.getMultiAngle1(),
                                    realtimeDataRequest.getMultiAlarm1(),
                                    realtimeDataRequest.getMultiOnline2(),
                                    realtimeDataRequest.getMultiRange2(),
                                    realtimeDataRequest.getMultiAngle2(),
                                    realtimeDataRequest.getMultiAlarm2(),
                                    realtimeDataRequest.getMultiOnline3(),
                                    realtimeDataRequest.getMultiRange3(),
                                    realtimeDataRequest.getMultiAngle3(),
                                    realtimeDataRequest.getMultiAlarm3(),
                                    realtimeDataRequest.getMultiOnline4(),
                                    realtimeDataRequest.getMultiRange4(),
                                    realtimeDataRequest.getMultiAngle4(),
                                    realtimeDataRequest.getMultiAlarm4(),
                                    realtimeDataRequest.getMultiOnline5(),
                                    realtimeDataRequest.getMultiRange5(),
                                    realtimeDataRequest.getMultiAngle5(),
                                    realtimeDataRequest.getMultiAlarm5(),
                                    realtimeDataRequest.getMultiOnline6(),
                                    realtimeDataRequest.getMultiRange6(),
                                    realtimeDataRequest.getMultiAngle6(),
                                    realtimeDataRequest.getMultiAlarm6(),
                                    realtimeDataRequest.getMultiOnline7(),
                                    realtimeDataRequest.getMultiRange7(),
                                    realtimeDataRequest.getMultiAngle7(),
                                    realtimeDataRequest.getMultiAlarm7(),
                                    realtimeDataRequest.getMultiOnline8(),
                                    realtimeDataRequest.getMultiRange8(),
                                    realtimeDataRequest.getMultiAngle8(),
                                    realtimeDataRequest.getMultiAlarm8(),
                                    realtimeDataRequest.getMultiOnline9(),
                                    realtimeDataRequest.getMultiRange9(),
                                    realtimeDataRequest.getMultiAngle9(),
                                    realtimeDataRequest.getMultiAlarm9(),
                                    realtimeDataRequest.getMultiOnline10(),
                                    realtimeDataRequest.getMultiRange10(),
                                    realtimeDataRequest.getMultiAngle10(),
                                    realtimeDataRequest.getMultiAlarm10(),
                                    realtimeDataRequest.getMultiOnline11(),
                                    realtimeDataRequest.getMultiRange11(),
                                    realtimeDataRequest.getMultiAngle11(),
                                    realtimeDataRequest.getMultiAlarm11(),
                                    realtimeDataRequest.getMultiOnline12(),
                                    realtimeDataRequest.getMultiRange12(),
                                    realtimeDataRequest.getMultiAngle12(),
                                    realtimeDataRequest.getMultiAlarm12(),
                                    realtimeDataRequest.getMultiOnline13(),
                                    realtimeDataRequest.getMultiRange13(),
                                    realtimeDataRequest.getMultiAngle13(),
                                    realtimeDataRequest.getMultiAlarm13(),
                                    realtimeDataRequest.getMultiOnline14(),
                                    realtimeDataRequest.getMultiRange14(),
                                    realtimeDataRequest.getMultiAngle14(),
                                    realtimeDataRequest.getMultiAlarm14(),
                                    realtimeDataRequest.getMultiOnline15(),
                                    realtimeDataRequest.getMultiRange15(),
                                    realtimeDataRequest.getMultiAngle15(),
                                    realtimeDataRequest.getMultiAlarm15(),
                                    realtimeDataRequest.getWorkStatus(),
                                    realtimeDataRequest.getName(),
                                    realtimeDataRequest.getIdNo(),
                                    realtimeDataRequest.getMotorStatus(),
                                    realtimeDataRequest.getObliguityX(),
                                    realtimeDataRequest.getObliguityY(),
                                    realtimeDataRequest.getForbidEntryPosAlarm(),
                                    realtimeDataRequest.getForbidEntryNegAlarm(),
                                    realtimeDataRequest.getForbidSuspend2PosAlarm(),
                                    realtimeDataRequest.getForbidSuspend2NegAlarm(),
                                    realtimeDataRequest.getForbidSuspend2OutAlarm(),
                                    realtimeDataRequest.getForbidSuspend4PosAlarm(),
                                    realtimeDataRequest.getForbidSuspend4NegAlarm(),
                                    realtimeDataRequest.getForbidSuspend4OutAlarm(),
                                    realtimeDataRequest.getForbidSuspend4BackAlarm(),
                                    realtimeDataRequest.getMultiPosAlarm(),
                                    realtimeDataRequest.getMultiNegAlarm(),
                                    realtimeDataRequest.getMultiOutAlarm(),
                                    realtimeDataRequest.getMultiBackAlarm());

                            //版本V2.G 新加字段
                            realtimeDataCrane2
                                    .setObliguityXAlarm(realtimeDataRequest.getObliguityXAlarm());
                            realtimeDataCrane2
                                    .setObliguityYAlarm(realtimeDataRequest.getObliguityYAlarm());
                            realtimeDataCrane2
                                    .setSteelCableHeight(realtimeDataRequest.getSteelCableHeight());
                            realtimeDataCrane2
                                    .setSteelCableValue(realtimeDataRequest.getSteelCableValue());
                            realtimeDataCrane2
                                    .setSteelCableLevel(realtimeDataRequest.getSteelCableLevel());
                            if (NullUtil.isNotEmpty(realtimeDataCrane1)) {
                                realtimeDataCrane2.setRowId(realtimeDataCrane1.getRowId());
                                realtimeDataCraneManager.save(realtimeDataCrane2);
                                log.info("update   RealtimeDataCrane  HxzId={}", HxzId);
                            } else {
                                realtimeDataCraneManager.save(realtimeDataCrane2);
                                log.info("insert    RealtimeDataCrane  HxzId={}", HxzId);
                            }
                            if (HxzFactory.equals("HN01")) {
//                                haiNanPost
//                                        .pushTowerRealData(HxzFactory, HxzId, realtimeDataRequest);
                            }


                        }
                        String Resultt =
                                "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd
                                        + "$@";
                        log.info("Result:" + Resultt);
                        ctx.channel().writeAndFlush(Resultt).syncUninterruptibly();
                    }
                    //升降机
                    if (VerNum.equals("1") && online_flag.equals("1")) {
                        String devType = "0x0002";
                        if (HxzId.length() == 8) {
                            String PreHxzId = "1120";
                        }
                        ElevatorRealtimeDataRequest elevatorRequest = new ElevatorRealtimeDataRequest(
                                HxzFactory, HxzId, ProtocolVer, cmd,
                                DateUtils.StrToDate(strings[5]), strings[6], strings[7],
                                Integer.valueOf(strings[8]),
                                Float.valueOf(strings[9]), Float.valueOf(strings[10]),
                                Float.valueOf(strings[11]),
                                Integer.valueOf(strings[12]), Float.valueOf(strings[13]),
                                Float.valueOf(strings[14]),
                                Float.valueOf(strings[15]), Integer.valueOf(strings[16]),
                                strings[17],
                                strings[18], strings[19], strings[20], strings[21],
                                strings[22], strings[23], strings[24],
                                strings[25], strings[26], strings[27],
                                strings[28], strings[29], strings[30],
                                strings[31], strings[32], strings[33],
                                strings[34], strings[35], strings[36],
                                strings[37], strings[38], strings[39], strings[40],
                                strings[41], strings[42], strings[43],
                                strings[44], strings[45], strings[46], strings[47],
                                strings[48], strings[49]);
                        String WorkName = "";
                        String IdNo = "";
                        Integer WorkNo = Integer.valueOf(elevatorRequest.getWorkNo());
                        if (msgLength == 52) {
                            WorkName = strings[50];
                            IdNo = strings[51];
                        }
                        if (WorkNo == 255) {
                            WorkName = "E69CAAE799BBE5BD95";
                        }
                        if (WorkNo == 0) {
                            WorkName = "E7AEA1E79086E59198";
                        }
                        if (NullUtil.isEmpty(WorkName)) {
                            if (WorkNo == 55) {
                                WorkName = "E7AEA1E79086E59198";
                                WorkNo = 255;
                            }
                        }
                        String TopAlarm = elevatorRequest.getLimitAlarm();
                        String BottomAlarm = "0";
                        if ("2".equals(TopAlarm)) {
                            BottomAlarm = "1";
                        }

                        String RTime = strings[5] + ' ' + strings[6];
                        Integer RunTime = 0;

                        String OnlineTime = "";
                        RuntimeData runtimeData = runtimeDataManager
                                .getIpByHxzIdAndHxzFactory(HxzFactory, HxzId);
                        if (NullUtil.isNotEmpty(runtimeData) && NullUtil
                                .isNotEmpty(runtimeData.getOnlineTime())) {
                            OnlineTime = runtimeData.getOnlineTime();
                        }
                        chackIpData(HxzFactory, HxzId, clientIP);

//                        LoginData loginData1 = loginDataManager
//                                .getLoginDataByHxzIdAndHxzFactory(HxzFactory, HxzId);
                        if (NullUtil.isNotEmpty(loginDataAll)) {
                            //更新RuntimeData
                            RunTime = DateUtils
                                    .differentDaysByMillisecond(
                                            DateUtils.StrToDate(OnlineTime),
                                            DateUtils.StrToDate(RTime));
                            //更新runtimeData_crentid
                            runtimeDataManager
                                    .upRunTimeSql(loginDataAll.getCraneId(), RTime, HxzFactory,
                                            HxzId);
                            log.info("upRunTimeSql  set  RunTime {}", RTime);
                            ////更新runtimeData
                            runtimeDataManager
                                    .upRuntime(OnlineTime, RunTime.toString(), HxzFactory,
                                            HxzId);
                            //更新WorkInfo信息
                            upWorkin(HxzFactory, HxzId, OnlineTime, RunTime.toString());

                            //inset RealtimeData_
                            realtimeDataElevatorManager.insertRealtimeDataSql(
                                    loginDataAll.getCraneId(), HxzFactory, HxzId, RTime,
                                    WorkNo.toString(),
                                    elevatorRequest.getPeopleCntSetError(),
                                    elevatorRequest.getRealtimeWeight().toString(),
                                    elevatorRequest.getRealtimeSpeed().toString(),
                                    elevatorRequest.getRealtimeHeight().toString(),
                                    elevatorRequest.getRealtimeFloor().toString(),
                                    elevatorRequest.getRealtimeDipX().toString(),
                                    elevatorRequest.getRealtimeDipY().toString(),
                                    elevatorRequest.getRealtimeWindSpeed().toString(),
                                    elevatorRequest.getRealtimeWindLevel().toString(),
                                    elevatorRequest.getNoError(), elevatorRequest.getIdError(),
                                    elevatorRequest.getPeopleCntSetError(),
                                    elevatorRequest.getWeightError(),
                                    elevatorRequest.getSpeedError(),
                                    elevatorRequest.getHeightError(),
                                    elevatorRequest.getFloorSetError(),
                                    elevatorRequest.getObliguityXError(),
                                    elevatorRequest.getObliguityYError(),
                                    elevatorRequest.getWindSpeedSetError(),
                                    elevatorRequest.getGpsError(),
                                    elevatorRequest.getWirelessSetError(),
                                    elevatorRequest.getNoPreAlarm(),
                                    elevatorRequest.getWeightPreAlarm(),
                                    elevatorRequest.getSpeedPreAlarm(),
                                    elevatorRequest.getHeightPreAlarm(),
                                    elevatorRequest.getObliguityXPreAlarm(),
                                    elevatorRequest.getObliguityYPreAlarm(),
                                    elevatorRequest.getWindSpeedPreAlarm(),
                                    elevatorRequest.getNoAlarm(), elevatorRequest.getPeopleAlarm(),
                                    elevatorRequest.getWeightAlarm(),
                                    elevatorRequest.getSpeedAlarm(),
                                    elevatorRequest.getHeightAlarm(),
                                    elevatorRequest.getObliguityXAlarm(),
                                    elevatorRequest.getObliguityYAlarm(),
                                    elevatorRequest.getWindSpeedAlarm(),
                                    elevatorRequest.getMotor1Alarm(),
                                    elevatorRequest.getMotor2Alarm(),
                                    elevatorRequest.getMotor3Alarm(),
                                    TopAlarm,
                                    elevatorRequest.getAntiDropAlarmt(),
                                    elevatorRequest.getOutDoorStatus(),
                                    WorkName, IdNo, BottomAlarm);
                            //更新保存 RealtimeDataElevator 表
                            RealtimeDataElevator newEntity = new RealtimeDataElevator(
                                    HxzFactory, HxzId, RTime, WorkNo.toString(),
                                    elevatorRequest.getPeopleCntSetError(),
                                    elevatorRequest.getRealtimeWeight().toString(),
                                    elevatorRequest.getRealtimeSpeed().toString(),
                                    elevatorRequest.getRealtimeHeight().toString(),
                                    elevatorRequest.getRealtimeFloor().toString(),
                                    elevatorRequest.getRealtimeDipX().toString(),
                                    elevatorRequest.getRealtimeDipY().toString(),
                                    elevatorRequest.getRealtimeWindSpeed().toString(),
                                    elevatorRequest.getRealtimeWindLevel().toString(),
                                    elevatorRequest.getNoError(), elevatorRequest.getIdError(),
                                    elevatorRequest.getPeopleCntSetError(),
                                    elevatorRequest.getWeightError(),
                                    elevatorRequest.getSpeedError(),
                                    elevatorRequest.getHeightError(),
                                    elevatorRequest.getFloorSetError(),
                                    elevatorRequest.getObliguityXError(),
                                    elevatorRequest.getObliguityYError(),
                                    elevatorRequest.getWindSpeedSetError(),
                                    elevatorRequest.getGpsError(),
                                    elevatorRequest.getWirelessSetError(),
                                    elevatorRequest.getNoPreAlarm(),
                                    elevatorRequest.getWeightPreAlarm(),
                                    elevatorRequest.getSpeedPreAlarm(),
                                    elevatorRequest.getHeightPreAlarm(),
                                    elevatorRequest.getObliguityXPreAlarm(),
                                    elevatorRequest.getObliguityYPreAlarm(),
                                    elevatorRequest.getWindSpeedPreAlarm(),
                                    elevatorRequest.getNoAlarm(), elevatorRequest.getPeopleAlarm(),
                                    elevatorRequest.getWeightAlarm(),
                                    elevatorRequest.getSpeedAlarm(),
                                    elevatorRequest.getHeightAlarm(),
                                    elevatorRequest.getObliguityXAlarm(),
                                    elevatorRequest.getObliguityYAlarm(),
                                    elevatorRequest.getWindSpeedAlarm(),
                                    elevatorRequest.getMotor1Alarm(),
                                    elevatorRequest.getMotor2Alarm(),
                                    elevatorRequest.getMotor3Alarm(),
                                    TopAlarm,
                                    elevatorRequest.getAntiDropAlarmt(),
                                    elevatorRequest.getOutDoorStatus(),
                                    WorkName, IdNo, BottomAlarm);
                            RealtimeDataElevator realtimeDataElevator = realtimeDataElevatorManager
                                    .getEntity(HxzFactory, HxzId);
                            if (NullUtil.isNotEmpty(realtimeDataElevator)) {
                                newEntity.setRowId(realtimeDataElevator.getRowId());
                                realtimeDataElevatorManager.save(newEntity);
                            } else {
                                realtimeDataElevatorManager.save(newEntity);
                            }
                            if (HxzFactory.equals("HN01")) {
//                                haiNanPost.pushLifterRealdata(HxzFactory, HxzId, elevatorRequest);
                            }
                        }
                        String Resultt =
                                "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd
                                        + "$@";
                        log.info("Result:" + Resultt);
                        ctx.channel().writeAndFlush(Resultt).syncUninterruptibly();
                    }
                    break;
                case "4":

                    log.info("工作循环命令");
                    if (VerNum.equals("2") && online_flag.equals("1")) {
                        Integer WorkNo = Integer.valueOf(strings[37]);
                        String WorkName = strings[16];
                        if (WorkNo == 255) {
                            WorkName = "E69CAAE799BBE5BD95";
                        }
                        if (WorkNo == 0) {
                            WorkName = "E7AEA1E79086E59198";
                        }
                        String WorkStartTime = strings[6] + ' ' + strings[7];
                        String WorkEndTime = strings[8] + ' ' + strings[9];
                        int WorkTime = DateUtils
                                .differentDaysByMillisecond(DateUtils.StrToDate2(WorkStartTime),
                                        DateUtils.StrToDate2(WorkEndTime));
                        WorkDataRequest workDataRequest = new WorkDataRequest(
                                HxzFactory, HxzId, ProtocolVer, cmd,
                                Integer.valueOf(strings[5]), strings[6], strings[7],
                                strings[8], strings[9], Float.valueOf(strings[10]),
                                Float.valueOf(strings[11]), Float.valueOf(strings[12]),
                                Float.valueOf(strings[13]),
                                Float.valueOf(strings[14]), strings[15], WorkName,
                                strings[17], Float.valueOf(strings[18]), Float.valueOf(strings[19]),
                                Float.valueOf(strings[20]), Float.valueOf(strings[21]),
                                Float.valueOf(strings[22]), Float.valueOf(strings[23]),
                                Float.valueOf(strings[24]),
                                Float.valueOf(strings[25]), strings[26], strings[27],
                                strings[28], strings[29], strings[30],
                                strings[31], strings[32], strings[33],
                                strings[34], strings[35], strings[36],
                                WorkNo, Float.valueOf(strings[38]),
                                Float.valueOf(strings[39]), Float.valueOf(strings[40]),
                                Float.valueOf(strings[41]));
                        LoginData loginData1 = loginDataManager
                                .getLoginDataByHxzIdAndHxzFactory(HxzFactory, HxzId);
                        if (NullUtil.isNotEmpty(loginData1) && NullUtil
                                .isNotEmpty(loginData1.getCraneId())) {
                            workDataCraneManager
                                    .insertWorkDataSql(loginData1.getCraneId(), HxzFactory, HxzId,
                                            workDataRequest.getWorkMultiple(),
                                            WorkStartTime,
                                            WorkEndTime, String.valueOf(WorkTime),
                                            workDataRequest.getWorkWeight().toString(),
                                            workDataRequest.getWorkRatedWeight().toString(),
                                            workDataRequest.getWorkMaxTorque().toString(),
                                            workDataRequest.getWorkMaxWindSpeed().toString(),
                                            workDataRequest.getWorkWindSpeedAlarm(),
                                            workDataRequest.getName(), workDataRequest.getIdNo(),
                                            workDataRequest.getWorkMaxHeight().toString(),
                                            workDataRequest.getWorkMaxForce().toString(),
                                            workDataRequest.getWorkStartAngle().toString(),
                                            workDataRequest.getWorkEndAngle().toString(),
                                            workDataRequest.getWorkStartRange().toString(),
                                            workDataRequest.getWorkEndRange().toString(),
                                            workDataRequest.getWorkStartHeight().toString(),
                                            workDataRequest.getWorkEndHeight().toString(),
                                            workDataRequest.getWorkMaxRangeAlarm(),
                                            workDataRequest.getWorkMinRangeAlarm(),
                                            workDataRequest.getWorkHeightAlarm(),
                                            workDataRequest.getWorkNegAngleAlarm(),
                                            workDataRequest.getWorkPosAngleAlarm(),
                                            workDataRequest.getWorkMomentAlarm(),
                                            workDataRequest.getWorkObliguityAlarm(),
                                            workDataRequest.getWorkEnvironmentAlarm(),
                                            workDataRequest.getWorkMultiAlarm(),
                                            workDataRequest.getWorkMomentPreAlarm(),
                                            workDataRequest.getWorkWindSpeedPreAlarm(),
                                            workDataRequest.getWorkNo().toString(),
                                            workDataRequest.getWorkMaxHeight1().toString(),
                                            workDataRequest.getWorkMinHeight1().toString(),
                                            workDataRequest.getWorkMaxRange().toString(),
                                            workDataRequest.getWorkMinRange().toString());
                        }
                        log.info("  insertWorkDataSql");
                        chackIpData(HxzFactory, HxzId, clientIP);

                    }
                    //升降机
                    if (VerNum.equals("1") && online_flag.equals("1")) {
                        String WorkName = "";
                        String IdNo = "";
                        Integer WorkNo = Integer.valueOf(strings[9]);
                        if (msgLength > 33) {
                            WorkName = strings[33];
                        }

                        if (WorkNo == 255) {
                            WorkName = "E69CAAE799BBE5BD95";
                        }
                        if (WorkNo == 0) {
                            WorkName = "E7AEA1E79086E59198";
                        }
                        if (NullUtil.isEmpty(WorkName)) {
                            if (WorkNo == 55) {
                                WorkNo = 255;
                                WorkName = "E7AEA1E79086E59198";
                            }
                        }

                        String WorkStartTime = strings[5] + ' ' + strings[6];
                        String WorkEndTime = strings[7] + ' ' + strings[8];
                        int WorkTime = DateUtils
                                .differentDaysByMillisecond(DateUtils.StrToDate2(WorkStartTime),
                                        DateUtils.StrToDate2(WorkEndTime));
                        ElevatorWorkDataRequest elevatorWorkDataRequest = new ElevatorWorkDataRequest(
                                HxzFactory, HxzId, ProtocolVer,
                                cmd, strings[5], strings[6], strings[7],
                                strings[8], Integer.valueOf(strings[9]), strings[10], strings[11],
                                strings[12], strings[13], strings[14], strings[15],
                                strings[16], strings[16], strings[17], strings[18],
                                strings[19], strings[20], strings[21], strings[22],
                                strings[23], strings[24], strings[25],
                                strings[26], strings[27], strings[28],
                                strings[29], strings[30], strings[31], strings[32]);
                        String TopAlarm = elevatorWorkDataRequest.getLimitAlarm();
                        String BottomAlarm = "0";
                        if ("2".equals(TopAlarm)) {
                            BottomAlarm = "1";
                        }

                        //更新ipdata
                        chackIpData(HxzFactory, HxzId, clientIP);
//                        LoginData loginData1 = loginDataManager
//                                .getLoginDataByHxzIdAndHxzFactory(HxzFactory, HxzId);
                        if (isBA) {
                            String CraneId = loginDataAll.getCraneId();
                            workDataElevatorManager.insertWorkDataSql(CraneId,
                                    HxzFactory, HxzId,
                                    WorkStartTime, WorkEndTime, Integer.toString(WorkTime),
                                    WorkNo.toString(),
                                    elevatorWorkDataRequest.getMaxPeople(),
                                    elevatorWorkDataRequest.getMaxWeight(),
                                    elevatorWorkDataRequest.getMaxSpeed(),
                                    elevatorWorkDataRequest.getStartHeight(),
                                    elevatorWorkDataRequest.getEndHeight(),
                                    elevatorWorkDataRequest.getStartFloor(),
                                    elevatorWorkDataRequest.getEndFloor(),
                                    elevatorWorkDataRequest.getMaxDipX(),
                                    elevatorWorkDataRequest.getMaxDipY(),
                                    elevatorWorkDataRequest.getMaxWindSpeed(),
                                    elevatorWorkDataRequest.getMaxWindLevel(),
                                    elevatorWorkDataRequest.getNoAlarm(),
                                    elevatorWorkDataRequest.getPeopleAlarm(),
                                    elevatorWorkDataRequest.getWeightAlarm(),
                                    elevatorWorkDataRequest.getSpeedAlarm(),
                                    elevatorWorkDataRequest.getHeightAlarm(),
                                    elevatorWorkDataRequest.getObliguityXAlarm(),
                                    elevatorWorkDataRequest.getObliguityYAlarm(),
                                    elevatorWorkDataRequest.getWindSpeedAlarm(),
                                    elevatorWorkDataRequest.getMotor1Alarm(),
                                    elevatorWorkDataRequest.getMotor2Alarm(),
                                    elevatorWorkDataRequest.getMotor3Alarm(),
                                    elevatorWorkDataRequest.getLimitAlarm(),
                                    TopAlarm,
                                    WorkName, IdNo, BottomAlarm);
                            if (HxzFactory.equals("HN01")) {
//                                haiNanPost.pushLifterRuncycledata(HxzFactory, HxzId,
//                                        elevatorWorkDataRequest);
                            }
                        }

                    }
                    String Resultx =
                            "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd + "$@";
                    log.info("Result:" + Resultx);
                    ctx.channel().writeAndFlush(Resultx).syncUninterruptibly();
                    break;
                case "5":

                    log.info("定位");
                    Latitude = strings[5];
                    Longitude = strings[6];
                    String tmpstr = "";//偏移量
                    tmpstr = userClient.gettmpstr(Latitude, Longitude);
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

                    log.info("报警 ");
                    if (VerNum.equals("2")) {
                        AlarmRequest alarmRequest = new AlarmRequest(
                                strings[1], strings[2], strings[3], strings[4],
                                strings[5], strings[6], strings[7], strings[8],
                                Integer.valueOf(strings[9]), Integer.valueOf(strings[10]),
                                Integer.valueOf(strings[11]), Integer.valueOf(strings[12]),
                                Integer.valueOf(strings[13]),
                                strings[14], strings[15], strings[16],
                                strings[17], strings[18], strings[19],
                                strings[20], strings[21], strings[22],
                                strings[23], strings[24], strings[25],
                                strings[26], strings[27], strings[28],
                                strings[29], strings[30], strings[31],
                                strings[32]
                        );
                        String StartTime = strings[5] + ' ' + strings[6];
                        String EndTime = strings[7] + ' ' + strings[8];
                        Integer WorkNo = 0;
                        String WorkName = "";
                        String IdNob = "";
                        if (msgLength >= 35) {
                            WorkNo = Integer.valueOf(strings[33]);
                            WorkName = strings[34];
                            IdNob = strings[35];
                        }

                        if (WorkNo == 255) {
                            WorkName = "E69CAAE799BBE5BD95";
                        }
                        if (WorkNo == 0) {
                            WorkName = "E7AEA1E79086E59198";
                        }
                        //检查IP情况
                        chackIpData(HxzFactory, HxzId, clientIP);
                        //插入AlarmData_
//                        LoginData loginData1 = loginDataManager
//                                .getLoginDataByHxzIdAndHxzFactory(HxzFactory, HxzId);
                        if (isBA) {
                            alarmDataManager.insetSql(loginDataAll.getCraneId(), HxzFactory,
                                    HxzId,
                                    DateUtils.StrToDate2(StartTime),
                                    DateUtils.StrToDate2(EndTime),
                                    Integer.valueOf(alarmRequest.getMomentAlarm()),
                                    Integer.valueOf(alarmRequest.getWindSpeedAlarm()),
                                    Integer.valueOf(alarmRequest.getHeightUpAlarm()),
                                    Integer.valueOf(alarmRequest.getMinRangeAlarm()),
                                    Integer.valueOf(alarmRequest.getMaxRangeAlarm()),
                                    Integer.valueOf(alarmRequest.getPosAngleAlarm()),
                                    Integer.valueOf(alarmRequest.getNegAngleAlarm()),
                                    Integer.valueOf(alarmRequest.getObliguityAlarm()),
                                    Integer.valueOf(alarmRequest.getEnvironmentAlarm()),
                                    Integer.valueOf(alarmRequest.getMultiAlarm()),
                                    WorkNo,
                                    WorkName,
                                    IdNob,
                                    alarmRequest.getAlarm1(),
                                    alarmRequest.getAlarm2(),
                                    alarmRequest.getAlarm3(),
                                    alarmRequest.getAlarm4(),
                                    alarmRequest.getAlarm5(),
                                    alarmRequest.getMultiNegAlarm(),
                                    alarmRequest.getMultiPosAlarm(),
                                    alarmRequest.getMultiOutAlarm(),
                                    alarmRequest.getMultiInAlarm(),
                                    alarmRequest.getEnvtNegAlarm(),
                                    alarmRequest.getEnvtPosAlarm(),
                                    alarmRequest.getEnvtOutAlarm(),
                                    alarmRequest.getEnvtInAlarm());
                        }
                        log.info("insert  AlarmData_  ");
                    }
                    //升降机
                    if (VerNum.equals("1") && online_flag.equals("1")) {
                        String WorkName = "";
                        String IdNo = "";
                        Integer WorkNo = Integer.valueOf(strings[9]);
                        if (msgLength > 23) {
                            WorkName = strings[23];
                        }

                        if (WorkNo == 255) {
                            WorkName = "E69CAAE799BBE5BD95";
                        }
                        if (WorkNo == 0) {
                            WorkName = "E7AEA1E79086E59198";
                        }
                        if (NullUtil.isEmpty(WorkName)) {
                            if (WorkNo == 55) {
                                WorkNo = 255;
                                WorkName = "E7AEA1E79086E59198";
                            }
                        }
                        String StartTime = strings[5] + ' ' + strings[6];
                        String EndTime = strings[7] + ' ' + strings[8];
                        AlarmElevatorRequest alarmElevatorRequest = new AlarmElevatorRequest(
                                HxzFactory, HxzId, ProtocolVer,
                                cmd, strings[5], strings[6], strings[7], strings[8],
                                strings[9], strings[10], strings[11], strings[12],
                                strings[13], strings[14], strings[15],
                                strings[16], strings[17], strings[18],
                                strings[19],
                                strings[20], strings[21]);
                        String TopAlarm = alarmElevatorRequest.getLimitAlarm();
                        String BottomAlarm = "0";
                        if ("2".equals(TopAlarm)) {
                            BottomAlarm = "1";
                        }

                        if (isBA) {
                            alarmDataElevatorManager.insetrSql(loginDataAll.getCraneId(),
                                    HxzFactory, HxzId, StartTime,
                                    EndTime, WorkNo.toString(),
                                    alarmElevatorRequest.getPeopleAlarm(),
                                    alarmElevatorRequest.getWeightAlarm(),
                                    alarmElevatorRequest.getSpeedAlarm(),
                                    alarmElevatorRequest.getHeightAlarm(),
                                    alarmElevatorRequest.getObliguityXAlarm(),
                                    alarmElevatorRequest.getObliguityYAlarm(),
                                    alarmElevatorRequest.getWindSpeedAlarm(),
                                    alarmElevatorRequest.getMotor1Alarm(),
                                    alarmElevatorRequest.getMotor2Alarm(),
                                    alarmElevatorRequest.getMotor3Alarm(),
                                    alarmElevatorRequest.getLimitAlarm(),
                                    alarmElevatorRequest.getAntiDropAlarmt(),
                                    WorkName, IdNo, BottomAlarm);
                        }

                    }
                    String Result2 =
                            "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd + "$@";
                    log.info("Result:" + Result2);
                    ctx.channel().writeAndFlush(Result2).syncUninterruptibly();

                    break;
                case "8":

                    log.info("修改通信地址命令");
                    String IpString = strings[5];
                    String PortString = strings[6];
                    String CServerIp = "000.000.000.000";
                    String CServerPort = "0000";
                    if (VerNum.equals("2")) {
                        //查询ControlDataCrane
                        ControlDataCrane controlDataCrane1 = controlDataCraneManager
                                .getControlDataCraneByHxzIdAndHxzFactory(HxzFactory, HxzId);
                        log.info("select ControlDataCrane HxzFactory={},HxzId={}", HxzFactory,
                                HxzId);
                        if (NullUtil.isNotEmpty(controlDataCrane1)) {
                            CServerIp = controlDataCrane1.getServerIp();
                            CServerPort = controlDataCrane1.getServerPort();
                        }
                        if (IpString.equals(CServerIp) && PortString.equals(CServerPort)) {
                            //调用pModifyServer UPDATE ControlDataCrane SET ModifyServer = '0'
                            controlDataCraneManager.upModifyServer("0", HxzFactory, HxzId);
                            log.info(" upModifyServer HxzFactory={},HxzId={}", HxzFactory, HxzId);
                            String Result =
                                    "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd
                                            + "$@";
                            log.info("Result:" + Result);
                            ctx.channel().writeAndFlush(Result).syncUninterruptibly();
                        }
                    }
                    if (VerNum.equals("1")) {
                        //查询ControlDataCrane
                        ControlDataElevator controlDataElevator = controlDataElevatorManager
                                .getEntityByHxzFactory(HxzFactory, HxzId);
                        log.info("select ControlDataCrane HxzFactory={},HxzId={}", HxzFactory,
                                HxzId);
                        if (NullUtil.isNotEmpty(controlDataElevator)) {
                            CServerIp = controlDataElevator.getServerIp();
                            CServerPort = controlDataElevator.getServerPort();
                        }
                        if (IpString.equals(CServerIp) && PortString.equals(CServerPort)) {
                            //调用pModifyServer UPDATE ControlDataCrane SET ModifyServer = '0'
                            controlDataElevatorManager.upModifyServer("0", HxzFactory, HxzId);
                            log.info(" upModifyServer HxzFactory={},HxzId={}", HxzFactory, HxzId);
                            String Result =
                                    "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd
                                            + "$@";
                            log.info("Result:" + Result);
                            ctx.channel().writeAndFlush(Result).syncUninterruptibly();
                        }
                    }
                    break;
                case "J":

                    log.info("报警状态改变命令");
                    if (VerNum.equals("2")) {
                        AlarmChangeRequest alarmChangeRequest = new AlarmChangeRequest(HxzFactory,
                                HxzId, ProtocolVer, cmd,
                                strings[5], strings[6], Integer.valueOf(strings[7]),
                                Integer.valueOf(strings[8]), Float.valueOf(strings[9]),
                                Float.valueOf(strings[10]), Float.valueOf(strings[11]),
                                Float.valueOf(strings[12]), Integer.valueOf(strings[13]),
                                Float.valueOf(strings[14]),
                                Float.valueOf(strings[15]), Float.valueOf(strings[16]),
                                Float.valueOf(strings[17]), Float.valueOf(strings[18]),
                                Float.valueOf(strings[19]),
                                Integer.valueOf(strings[20]));
                        Integer WorkNo = 0;
                        if (msgLength >= 22) {
                            alarmChangeRequest.setName(strings[21]);
                            alarmChangeRequest.setIdNo(strings[22]);
                            WorkNo = alarmChangeRequest.getWorkNo();
                        }

                        String RTime =
                                alarmChangeRequest.getDate() + ' ' + alarmChangeRequest.getTime();

                        String WorkName = alarmChangeRequest.getName();
                        if (WorkNo == 255) {
                            WorkName = "E69CAAE799BBE5BD95";
                        }
                        if (WorkNo == 0) {
                            WorkName = "E7AEA1E79086E59198";
                        }

                        //post jsion
                        String Result =
                                "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd
                                        + "$@";
                        log.info("Result:" + Result);
                        ctx.channel().writeAndFlush(Result).syncUninterruptibly();
                    }
                    break;
                case "M":
                    log.info("照片截图命令");

                    if (HxzFactory.equals("HN01")) {
//                        haiNanPost.uploadImagelog(HxzFactory, HxzId);
                    }

                    String Result =
                            "*$" + HxzFactory + "$" + HxzId + '$' + ProtocolVer + '$' + cmd + "$@";
                    log.info("Result:" + Result);
                    ctx.channel().writeAndFlush(Result).syncUninterruptibly();
                    break;
                default: //可选
                    //语句
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
        NettyTcpServer.map.remove(getIPString(ctx));
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

    /**
     * j检查CraneId 是否存在
     */
    public boolean checkLoginData(String HxzFactory, String HxzId) {
        LoginData loginData = loginDataManager.getLoginDataByHxzIdAndHxzFactory(HxzFactory, HxzId);
        if (NullUtil.isNotEmpty(loginData) && NullUtil.isNotEmpty(loginData.getCraneId())) {
            return true;
        } else {
            return false;
        }
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
     * ProjectInfo 获取项目名称
     */
    public String getProjectInfo(String HxzFactory, String HxzId) {
        String Projectid = towerCraneManager.getProjectid(HxzFactory, HxzId);
        String ProjectInfo = projectManager.getProjectInfo(Projectid);
        return ProjectInfo;

    }

    /**
     * 更新操作记录
     */
    public void upoperatorData(String HxzFactory, String HxzId, String WorkNo, String WorkName,
            String IdNo) {

        OperatorData operatorData1 = new OperatorData(HxzFactory, HxzId,
                WorkNo,
                WorkName, IdNo,
                DateUtils.getCurrentTime());
        OperatorData operatorData = operatorDataManager.getEntity(HxzFactory, HxzId);
        if (NullUtil.isNotEmpty(operatorData)) {
            //根据HxzFactory和HxzId 查询LoginData 是否存在CraneId
            LoginData loginData1 = loginDataManager
                    .getLoginDataByHxzIdAndHxzFactory(HxzFactory, HxzId);
            log.info("Select   LoginData HxzFactory={},HxzId={}", HxzFactory, HxzId);
            String OnLineFlag = "";
            if (NullUtil.isNotEmpty(loginData1)) {
                OnLineFlag = loginData1.getOnLine();
            }

            String OldWorkName = operatorData.getWorkName();
            if (NullUtil.isNotEmpty(OldWorkName)) {
                //用户名 不等 更新
                if (!OnLineFlag.equals("1") || !OldWorkName.equals(WorkName)) {
                    operatorData1.setRowId(operatorData.getRowId());
                    operatorDataManager.save(operatorData1);
                    log.info("update  operatorData", operatorData1);
                }
            }
        } else {
            //插入一条新操作记录

            operatorDataManager.save(operatorData1);
            log.info("insert  operatorData", operatorData1);
        }
    }

    /**
     * 更新runtimedata
     */
    public void updateRuntime(String HxzFactory, String HxzId, String online_flag) {
        //查询RuntimeData 是否存在数据 存在且 不在线更新 在线数据
        RuntimeData runtimeData = runtimeDataManager
                .getIpByHxzIdAndHxzFactory(HxzFactory, HxzId);
        log.info("select runtimeData", HxzFactory, HxzId);
        if (NullUtil.isNotEmpty(runtimeData)) {
            if ("0".equals(online_flag)) {
                //存在且online_flag=0是更新时间状态 RunTime=0
                String OnlineTime = DateUtils.getCurrentTime();
                runtimeDataManager
                        .upRuntime(OnlineTime, "0", HxzFactory, HxzId);
                log.info(
                        "update runtimeData OnlineTime:{},HxzFactory:{},HxzId:{}",
                        OnlineTime, HxzFactory, HxzId);
            }

        } else {
            RuntimeData runtimeData1 = new RuntimeData();
            runtimeData1.setHxzFactory(HxzFactory);
            runtimeData1.setHxzId(HxzId);
            runtimeData1.setOnlineTime(DateUtils.getCurrentTime());
            runtimeData1.setRunTime("0");
            runtimeDataManager.save(runtimeData1);
            log.info("Insert runtimeData HxzFactory:{},HxzId:{}", HxzFactory,
                    HxzId);
        }
    }

    public void upWorkin(String HxzFactory, String HxzId, String OnlineTime, String RunTime) {
        //更新WorkInfo信息
        String TowerCrane_CraneId = "";
        String Project_id;
        String Project_Name;
        BigDecimal TowerCrane_id;
        TowerCrane towerCrane = towerCraneManager
                .getTowerCraneByHxzIdAndHxzFactory(HxzFactory, HxzId);
        log.info("select TowerCrane  HxzFactory={},HxzId={}", HxzFactory,
                HxzId);
        TowerCrane_CraneId = workInfoManager
                .getTowerCrane_CraneId(HxzFactory, HxzId, OnlineTime);
        BigDecimal Operator_id;

        //不存在TowerCrane_CraneId时
        Project_id = towerCrane.getProject_id().toString();
        if (NullUtil.isEmpty(TowerCrane_CraneId)) {
            TowerCrane_CraneId = towerCrane.getCraneId();
            Project project = projectManager
                    .getProjectById(Project_id);

            //RuntimeData
            Project_Name = project.getName();
            TowerCrane_id = towerCrane.getId();
            WorkNo workNo = workNoManager.getEntity(TowerCrane_id);
            log.info("select WorkNo  TowerCrane_id={}", TowerCrane_id, HxzId);
            if (NullUtil.isNotEmpty(workNo) && NullUtil
                    .isNotEmpty(workNo.getOperator_id())) {
                Operator_id = workNo.getOperator_id();
                Operator operator = operatorManager.getEntity(Operator_id);
                log.info("select  Operator  Operator_id={}", Operator_id);
                //插入数据到WorkInfo
                WorkInfo workInfo1 = new WorkInfo();
                workInfo1.setCraneEndDate(operator.getCraneEndDate());
                workInfo1.setCraneOperatorNo(operator.getCraneOperatorNo());
                workInfo1.setCraneStartDate(operator.getCraneStartDate());
                workInfo1.setElevatorEndDate(operator.getElevatorEndDate());
                workInfo1.setElevatorOperatorNo(
                        operator.getElevatorOperatorNo());
                workInfo1.setElevatorStartDate(operator.getElevatorStartDate());
                workInfo1.setGender(operator.getGender());
                workInfo1.setIdCard(operator.getIdCard());
                workInfo1.setName(operator.getName());
                workInfo1.setOnlineTime(OnlineTime);
                workInfo1.setProject_Name(Project_Name);
                workInfo1.setRunTime(RunTime);
                workInfo1.setTel(operator.getTel());
                workInfo1.setTowerCrane_CraneId(TowerCrane_CraneId);
                workInfo1.setTowerCrane_Factory(HxzFactory);
                workInfo1.setTowerCrane_HxzId(HxzId);
                workInfo1.setTowerCrane_Type(towerCrane.getType());
                workInfo1.setWorkNo_WorkNo(workNo.getWorkNo());
                workInfoManager.save(workInfo1);
                log.info("insert   workInfo   ");
            }
        } else {
            //更新WorkInfo
            workInfoManager
                    .updateRunTime(HxzFactory, HxzId, RunTime, " ");
            log.info("update   workInfo  RunTime={} ", RunTime);
        }
    }
}