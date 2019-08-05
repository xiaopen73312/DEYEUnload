package com.deye.demo.request;

/**
 * 升降机实时数据参数
 */
public class ElevatorRealtimeDataRequest {

    String HxzFactory;//	char(4)	黑匣子厂家	大写英文缩写	DEYE
    String HxzId;//	char(8)	黑匣子编号	格式YYMMDDXX	17120901
    String ProtocolVer;//	char(4)	协议版本号		V1.4
    String Cmd;//	char(1)	命令		3
    String Date;//	char(10)	采集日期	YYYY-MM-DD	2018-01-05
    String Time;//	char(8)	采集时间	HH:MM:SS	11:49:01
    String WorkNo;//	int	工号	管理员：0 操作员：1~99 未登陆：255 255
    Integer RealtimePeople;//	float	实时运载人数	1~20人	0
    Float RealtimeWeight;//	float	实时载重	0.00~9.99t	0.50
    Float RealtimeSpeed;//	float	实时运行速度	0.00~9.99m/s	0.00
    Float RealtimeHeight;//	float		0.0~999.9m	14.1
    Integer RealtimeFloor;//	float		0~100层	5
    Float RealtimeDipX;//	 		0.0~2.00°	-1.13
    Float RealtimeDipY;//	float	幅度	0.0~2.00°	0.00
    Float RealtimeWindSpeed;//	float	实时风速	0.00~36.90m/s	0.00
    Integer RealtimeWindLevel;//	float	实时风级	0~12级	0
    String NoError;//	char(1)	无任何外设故障	0:有外设故障1:无任何外设故障	1
    String IdError;//	char(1)	身份识别传感器故障	0:无故障1:有故障	0
    String PeopleCntSetError;//	char(1)	运载人数传感器故障	0:无故障1:有故障	0
    String WeightError;//	char(1)	载重传感器故障	0:无故障1:有故障	0
    String SpeedError;//	char(1)	运行速度传感器故障	0:无故障1:有故障	0
    String HeightError;//	char(1)	高度传感器故障	0:无故障1:有故障	0
    String FloorSetError;//	char(1)	楼层传感器故障	0:无故障1:有故障	0
    String ObliguityXError;//	char(1)	X轴倾角传感器故障	0:无故障1:有故障	0
    String ObliguityYError;//	char(1)	X轴倾角传感器故障	0:无故障1:有故障	0
    String WindSpeedSetError;//	char(1)	风速传感器故障	0:无故障1:有故障	0
    String GpsError;//	char(1)	GPS定位模块故障	0:无故障1:有故障	0
    String WirelessSetError;//	char(1)	无线模块故障	0:无故障1:有故障	0
    String NoPreAlarm;//	char(1)	无任何预警	0:有预警1:无任何预警	1
    String WeightPreAlarm;//	char(1)	载重预警	0: 正常1:预警	0
    String SpeedPreAlarm;//	char(1)	运行速度预警	0: 正常1:预警	0
    String HeightPreAlarm;//	char(1)	高度预警	0: 正常1:预警	0
    String ObliguityXPreAlarm;//	char(1)	X轴倾角预警	0: 正常1:预警	0
    String ObliguityYPreAlarm;//	char(1)	Y轴倾角预警	0: 正常1:预警	0
    String WindSpeedPreAlarm;//	char(1)	风速预警	0: 正常1:预警	0
    String NoAlarm;//	char(1)	无任何报警	0:有报警1:无任何报警	1
    String PeopleAlarm;//	char(1)	运载人数报警	0:正常1:报警	0
    String WeightAlarm;//	char(1)	载重报警	0: 正常1:报警	0
    String SpeedAlarm;//   char(1)	运行速度报警	0:正常1:报警	0
    String HeightAlarm;// char(1)	高度报警	0:正常1:报警	0
    String ObliguityXAlarm;//char(1)	X轴倾角报警	0:正常1:报警	0
    String ObliguityYAlarm;//char(1)	Y轴倾角报警	0:正常1:报警	0
    String WindSpeedAlarm;//  char(1)	风速报警	0:正常1:报警	0
    String Motor1Alarm;//char(1)	保留	0:正常1:报警	0
    String Motor2Alarm;//char(1)	保留	0:正常1:报警	0
    String Motor3Alarm;// char(1)	保留	0:正常1:报警	0
    String LimitAlarm;//char(1)	上下限位报警	0:正常1:上限位报警2:下限位报警	0
    String AntiDropAlarmt;// char(1)	防坠器检测不在位报警	0:正常1:报警	0
    String OutDoorStatus;//char(1)	笼门状态	0:全部关闭1:全部打开2:内笼门打开3:外笼门打开	0
    String Name;//varchar(26)	操作人员姓名 UTF-8编码规则 空
    String IdNo;//char(18)	身份证号	18位 空

    public ElevatorRealtimeDataRequest() {
    }

    public ElevatorRealtimeDataRequest(String hxzFactory, String hxzId, String protocolVer,
            String cmd, String date, String time, String workNo, Integer realtimePeople,
            Float realtimeWeight, Float realtimeSpeed, Float realtimeHeight,
            Integer realtimeFloor, Float realtimeDipX, Float realtimeDipY,
            Float realtimeWindSpeed, Integer realtimeWindLevel, String noError, String idError,
            String peopleCntSetError, String weightError, String speedError,
            String heightError, String floorSetError, String obliguityXError,
            String obliguityYError, String windSpeedSetError, String gpsError,
            String wirelessSetError, String noPreAlarm, String weightPreAlarm,
            String speedPreAlarm, String heightPreAlarm, String obliguityXPreAlarm,
            String obliguityYPreAlarm, String windSpeedPreAlarm, String noAlarm,
            String peopleAlarm, String weightAlarm, String speedAlarm, String heightAlarm,
            String obliguityXAlarm, String obliguityYAlarm, String windSpeedAlarm,
            String motor1Alarm, String motor2Alarm, String motor3Alarm, String limitAlarm,
            String antiDropAlarmt, String outDoorStatus) {
        HxzFactory = hxzFactory;
        HxzId = hxzId;
        ProtocolVer = protocolVer;
        Cmd = cmd;
        Date = date;
        Time = time;
        WorkNo = workNo;
        RealtimePeople = realtimePeople;
        RealtimeWeight = realtimeWeight;
        RealtimeSpeed = realtimeSpeed;
        RealtimeHeight = realtimeHeight;
        RealtimeFloor = realtimeFloor;
        RealtimeDipX = realtimeDipX;
        RealtimeDipY = realtimeDipY;
        RealtimeWindSpeed = realtimeWindSpeed;
        RealtimeWindLevel = realtimeWindLevel;
        NoError = noError;
        IdError = idError;
        PeopleCntSetError = peopleCntSetError;
        WeightError = weightError;
        SpeedError = speedError;
        HeightError = heightError;
        FloorSetError = floorSetError;
        ObliguityXError = obliguityXError;
        ObliguityYError = obliguityYError;
        WindSpeedSetError = windSpeedSetError;
        GpsError = gpsError;
        WirelessSetError = wirelessSetError;
        NoPreAlarm = noPreAlarm;
        WeightPreAlarm = weightPreAlarm;
        SpeedPreAlarm = speedPreAlarm;
        HeightPreAlarm = heightPreAlarm;
        ObliguityXPreAlarm = obliguityXPreAlarm;
        ObliguityYPreAlarm = obliguityYPreAlarm;
        WindSpeedPreAlarm = windSpeedPreAlarm;
        NoAlarm = noAlarm;
        PeopleAlarm = peopleAlarm;
        WeightAlarm = weightAlarm;
        SpeedAlarm = speedAlarm;
        HeightAlarm = heightAlarm;
        ObliguityXAlarm = obliguityXAlarm;
        ObliguityYAlarm = obliguityYAlarm;
        WindSpeedAlarm = windSpeedAlarm;
        Motor1Alarm = motor1Alarm;
        Motor2Alarm = motor2Alarm;
        Motor3Alarm = motor3Alarm;
        LimitAlarm = limitAlarm;
        AntiDropAlarmt = antiDropAlarmt;
        OutDoorStatus = outDoorStatus;
    }

    public String getHxzFactory() {
        return HxzFactory;
    }

    public void setHxzFactory(String hxzFactory) {
        HxzFactory = hxzFactory;
    }

    public String getHxzId() {
        return HxzId;
    }

    public void setHxzId(String hxzId) {
        HxzId = hxzId;
    }

    public String getProtocolVer() {
        return ProtocolVer;
    }

    public void setProtocolVer(String protocolVer) {
        ProtocolVer = protocolVer;
    }

    public String getCmd() {
        return Cmd;
    }

    public void setCmd(String cmd) {
        Cmd = cmd;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getWorkNo() {
        return WorkNo;
    }

    public void setWorkNo(String workNo) {
        WorkNo = workNo;
    }

    public Integer getRealtimePeople() {
        return RealtimePeople;
    }

    public void setRealtimePeople(Integer realtimePeople) {
        RealtimePeople = realtimePeople;
    }

    public Float getRealtimeWeight() {
        return RealtimeWeight;
    }

    public void setRealtimeWeight(Float realtimeWeight) {
        RealtimeWeight = realtimeWeight;
    }

    public Float getRealtimeSpeed() {
        return RealtimeSpeed;
    }

    public void setRealtimeSpeed(Float realtimeSpeed) {
        RealtimeSpeed = realtimeSpeed;
    }

    public Float getRealtimeHeight() {
        return RealtimeHeight;
    }

    public void setRealtimeHeight(Float realtimeHeight) {
        RealtimeHeight = realtimeHeight;
    }

    public Integer getRealtimeFloor() {
        return RealtimeFloor;
    }

    public void setRealtimeFloor(Integer realtimeFloor) {
        RealtimeFloor = realtimeFloor;
    }

    public Float getRealtimeDipX() {
        return RealtimeDipX;
    }

    public void setRealtimeDipX(Float realtimeDipX) {
        RealtimeDipX = realtimeDipX;
    }

    public Float getRealtimeDipY() {
        return RealtimeDipY;
    }

    public void setRealtimeDipY(Float realtimeDipY) {
        RealtimeDipY = realtimeDipY;
    }

    public Float getRealtimeWindSpeed() {
        return RealtimeWindSpeed;
    }

    public void setRealtimeWindSpeed(Float realtimeWindSpeed) {
        RealtimeWindSpeed = realtimeWindSpeed;
    }

    public Integer getRealtimeWindLevel() {
        return RealtimeWindLevel;
    }

    public void setRealtimeWindLevel(Integer realtimeWindLevel) {
        RealtimeWindLevel = realtimeWindLevel;
    }

    public String getNoError() {
        return NoError;
    }

    public void setNoError(String noError) {
        NoError = noError;
    }

    public String getIdError() {
        return IdError;
    }

    public void setIdError(String idError) {
        IdError = idError;
    }

    public String getPeopleCntSetError() {
        return PeopleCntSetError;
    }

    public void setPeopleCntSetError(String peopleCntSetError) {
        PeopleCntSetError = peopleCntSetError;
    }

    public String getWeightError() {
        return WeightError;
    }

    public void setWeightError(String weightError) {
        WeightError = weightError;
    }

    public String getSpeedError() {
        return SpeedError;
    }

    public void setSpeedError(String speedError) {
        SpeedError = speedError;
    }

    public String getHeightError() {
        return HeightError;
    }

    public void setHeightError(String heightError) {
        HeightError = heightError;
    }

    public String getFloorSetError() {
        return FloorSetError;
    }

    public void setFloorSetError(String floorSetError) {
        FloorSetError = floorSetError;
    }

    public String getObliguityXError() {
        return ObliguityXError;
    }

    public void setObliguityXError(String obliguityXError) {
        ObliguityXError = obliguityXError;
    }

    public String getObliguityYError() {
        return ObliguityYError;
    }

    public void setObliguityYError(String obliguityYError) {
        ObliguityYError = obliguityYError;
    }

    public String getWindSpeedSetError() {
        return WindSpeedSetError;
    }

    public void setWindSpeedSetError(String windSpeedSetError) {
        WindSpeedSetError = windSpeedSetError;
    }

    public String getGpsError() {
        return GpsError;
    }

    public void setGpsError(String gpsError) {
        GpsError = gpsError;
    }

    public String getWirelessSetError() {
        return WirelessSetError;
    }

    public void setWirelessSetError(String wirelessSetError) {
        WirelessSetError = wirelessSetError;
    }

    public String getNoPreAlarm() {
        return NoPreAlarm;
    }

    public void setNoPreAlarm(String noPreAlarm) {
        NoPreAlarm = noPreAlarm;
    }

    public String getWeightPreAlarm() {
        return WeightPreAlarm;
    }

    public void setWeightPreAlarm(String weightPreAlarm) {
        WeightPreAlarm = weightPreAlarm;
    }

    public String getSpeedPreAlarm() {
        return SpeedPreAlarm;
    }

    public void setSpeedPreAlarm(String speedPreAlarm) {
        SpeedPreAlarm = speedPreAlarm;
    }

    public String getHeightPreAlarm() {
        return HeightPreAlarm;
    }

    public void setHeightPreAlarm(String heightPreAlarm) {
        HeightPreAlarm = heightPreAlarm;
    }

    public String getObliguityXPreAlarm() {
        return ObliguityXPreAlarm;
    }

    public void setObliguityXPreAlarm(String obliguityXPreAlarm) {
        ObliguityXPreAlarm = obliguityXPreAlarm;
    }

    public String getObliguityYPreAlarm() {
        return ObliguityYPreAlarm;
    }

    public void setObliguityYPreAlarm(String obliguityYPreAlarm) {
        ObliguityYPreAlarm = obliguityYPreAlarm;
    }

    public String getWindSpeedPreAlarm() {
        return WindSpeedPreAlarm;
    }

    public void setWindSpeedPreAlarm(String windSpeedPreAlarm) {
        WindSpeedPreAlarm = windSpeedPreAlarm;
    }

    public String getNoAlarm() {
        return NoAlarm;
    }

    public void setNoAlarm(String noAlarm) {
        NoAlarm = noAlarm;
    }

    public String getPeopleAlarm() {
        return PeopleAlarm;
    }

    public void setPeopleAlarm(String peopleAlarm) {
        PeopleAlarm = peopleAlarm;
    }

    public String getWeightAlarm() {
        return WeightAlarm;
    }

    public void setWeightAlarm(String weightAlarm) {
        WeightAlarm = weightAlarm;
    }

    public String getSpeedAlarm() {
        return SpeedAlarm;
    }

    public void setSpeedAlarm(String speedAlarm) {
        SpeedAlarm = speedAlarm;
    }

    public String getHeightAlarm() {
        return HeightAlarm;
    }

    public void setHeightAlarm(String heightAlarm) {
        HeightAlarm = heightAlarm;
    }

    public String getObliguityXAlarm() {
        return ObliguityXAlarm;
    }

    public void setObliguityXAlarm(String obliguityXAlarm) {
        ObliguityXAlarm = obliguityXAlarm;
    }

    public String getObliguityYAlarm() {
        return ObliguityYAlarm;
    }

    public void setObliguityYAlarm(String obliguityYAlarm) {
        ObliguityYAlarm = obliguityYAlarm;
    }

    public String getWindSpeedAlarm() {
        return WindSpeedAlarm;
    }

    public void setWindSpeedAlarm(String windSpeedAlarm) {
        WindSpeedAlarm = windSpeedAlarm;
    }

    public String getMotor1Alarm() {
        return Motor1Alarm;
    }

    public void setMotor1Alarm(String motor1Alarm) {
        Motor1Alarm = motor1Alarm;
    }

    public String getMotor2Alarm() {
        return Motor2Alarm;
    }

    public void setMotor2Alarm(String motor2Alarm) {
        Motor2Alarm = motor2Alarm;
    }

    public String getMotor3Alarm() {
        return Motor3Alarm;
    }

    public void setMotor3Alarm(String motor3Alarm) {
        Motor3Alarm = motor3Alarm;
    }

    public String getLimitAlarm() {
        return LimitAlarm;
    }

    public void setLimitAlarm(String limitAlarm) {
        LimitAlarm = limitAlarm;
    }

    public String getAntiDropAlarmt() {
        return AntiDropAlarmt;
    }

    public void setAntiDropAlarmt(String antiDropAlarmt) {
        AntiDropAlarmt = antiDropAlarmt;
    }

    public String getOutDoorStatus() {
        return OutDoorStatus;
    }

    public void setOutDoorStatus(String outDoorStatus) {
        OutDoorStatus = outDoorStatus;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIdNo() {
        return IdNo;
    }

    public void setIdNo(String idNo) {
        IdNo = idNo;
    }
}
