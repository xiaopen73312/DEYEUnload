package com.deye.xl.request;

/**
 * 升降机实时数据参数
 */
public class XLRealtimeDataRequest {

    String HxzFactory;//	char(4)	黑匣子厂家	大写英文缩写	DEYE
    String HxzId;//	char(8)	黑匣子编号	格式YYMMDDXX	17120901
    String ProtocolVer;//	char(4)	协议版本号		V1.4
    String Cmd;//	char(1)	命令		3
    String Date;//	char(10)	采集日期	YYYY-MM-DD	2018-01-05
    String Time;//	char(8)	采集时间	HH:MM:SS	11:49:01
    Float Weight1;//	载重1
    Float Weight2;//	载重2
    Float Weight;//	float	合成载重
    Float WeightPercent;//	载重百分比
    Float ObliguityX;//	float		倾角X轴
    Float ObliguityY;//	float		倾角Y轴
    Float Obliguity;//	 		合成倾角
    Integer BatteryPercent;//	电量百分比
    String Weight1Status;//	载重1状态 0正常  1预警  2报警  3故障
    String Weight2Status;//	载重2状态 0正常  1预警  2报警  3故障
    String ObliguityXStatus;//	char(1)	倾角X状态 0正常  1预警  2报警  3故障
    String ObliguityYStatus;//	char(1)	倾角Y状态 0正常  1预警  2报警  3故障
    String Wireless1Status;//	char(1)	无线连接1状态0正常  1预警  2报警  3故障
    String Wireless2Status;//	char(1)	无线连接2状态 0正常  1预警  2报警  3故障
    String GpsStatus;//	char(1)	GPS状态 0正常  1预警  2报警  3故障
    String BatteryStatus;//	char(1)电池状态 0正常  1预警  2报警  3故障


    public XLRealtimeDataRequest() {
    }

    public XLRealtimeDataRequest(String hxzFactory, String hxzId, String protocolVer,
            String cmd, String date, String time, Float weight1, Float weight2, Float weight,
            Float weightPercent, Float obliguityX, Float obliguityY, Float obliguity,
            Integer batteryPercent, String weight1Status, String weight2Status,
            String obliguityXStatus, String obliguityYStatus, String wireless1Status,
            String wireless2Status, String gpsStatus, String batteryStatus) {
        HxzFactory = hxzFactory;
        HxzId = hxzId;
        ProtocolVer = protocolVer;
        Cmd = cmd;
        Date = date;
        Time = time;
        Weight1 = weight1;
        Weight2 = weight2;
        Weight = weight;
        WeightPercent = weightPercent;
        ObliguityX = obliguityX;
        ObliguityY = obliguityY;
        Obliguity = obliguity;
        BatteryPercent = batteryPercent;
        Weight1Status = weight1Status;
        Weight2Status = weight2Status;
        ObliguityXStatus = obliguityXStatus;
        ObliguityYStatus = obliguityYStatus;
        Wireless1Status = wireless1Status;
        Wireless2Status = wireless2Status;
        GpsStatus = gpsStatus;
        BatteryStatus = batteryStatus;
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

    public Float getWeight1() {
        return Weight1;
    }

    public void setWeight1(Float weight1) {
        Weight1 = weight1;
    }

    public Float getWeight2() {
        return Weight2;
    }

    public void setWeight2(Float weight2) {
        Weight2 = weight2;
    }

    public Float getWeight() {
        return Weight;
    }

    public void setWeight(Float weight) {
        Weight = weight;
    }

    public Float getWeightPercent() {
        return WeightPercent;
    }

    public void setWeightPercent(Float weightPercent) {
        WeightPercent = weightPercent;
    }

    public Float getObliguityX() {
        return ObliguityX;
    }

    public void setObliguityX(Float obliguityX) {
        ObliguityX = obliguityX;
    }

    public Float getObliguityY() {
        return ObliguityY;
    }

    public void setObliguityY(Float obliguityY) {
        ObliguityY = obliguityY;
    }

    public Float getObliguity() {
        return Obliguity;
    }

    public void setObliguity(Float obliguity) {
        Obliguity = obliguity;
    }

    public Integer getBatteryPercent() {
        return BatteryPercent;
    }

    public void setBatteryPercent(Integer batteryPercent) {
        BatteryPercent = batteryPercent;
    }

    public String getWeight1Status() {
        return Weight1Status;
    }

    public void setWeight1Status(String weight1Status) {
        Weight1Status = weight1Status;
    }

    public String getWeight2Status() {
        return Weight2Status;
    }

    public void setWeight2Status(String weight2Status) {
        Weight2Status = weight2Status;
    }

    public String getObliguityXStatus() {
        return ObliguityXStatus;
    }

    public void setObliguityXStatus(String obliguityXStatus) {
        ObliguityXStatus = obliguityXStatus;
    }

    public String getObliguityYStatus() {
        return ObliguityYStatus;
    }

    public void setObliguityYStatus(String obliguityYStatus) {
        ObliguityYStatus = obliguityYStatus;
    }

    public String getWireless1Status() {
        return Wireless1Status;
    }

    public void setWireless1Status(String wireless1Status) {
        Wireless1Status = wireless1Status;
    }

    public String getWireless2Status() {
        return Wireless2Status;
    }

    public void setWireless2Status(String wireless2Status) {
        Wireless2Status = wireless2Status;
    }

    public String getGpsStatus() {
        return GpsStatus;
    }

    public void setGpsStatus(String gpsStatus) {
        GpsStatus = gpsStatus;
    }

    public String getBatteryStatus() {
        return BatteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        BatteryStatus = batteryStatus;
    }
}
