package com.deye.xl.request;

/**
 * 注册请求参数
 */
public class XLRegisterRequest {

    String hxzFactory; //黑匣子厂家 大写英文缩写 DEYE
    String hxzId;//黑匣子编号 格式YYMMDDXX 19012104
    String protocolVer;//协议版本号 V2.F
    String Cmd;//命令 0
    String UnloaderType;//卸料平台类型
    String Weight1SetExist;////载重1检测功能配置
    String Weight2SetExist;//载重2检测功能配置
    String ObliguityXExist;//倾角X检测功能配置
    String ObliguityYExist;//倾角y检测功能配置
    String GPSExist;//GPS定位功能配置
    String WirelessExist;//载重无线连接功能配置
    String Weight1Disabled;//载重1检测功能禁用
    String Weight2Disabled;//载重2检测功能禁用
    String ObliguityXDisabled;//倾角X检测功能禁用
    String ObliguityYDisabled;//倾角Y检测功能禁用
    String WeightPreAlarmValue;//String【2.00】//载重预警值
    String ObliguityXPreAlarmValue;//String【2.00】//倾角X预警值
    String ObliguityYPreAlarmValue;//String【2.00】//倾角Y预警值
    Integer BatteryPreAlarmValue;//电池电量预警值
    String WeightAlarmValue;//载重报警值
    String ObliguityXAlarmValue;//倾角X报警值
    String ObliguityYAlarmValue;//倾角Y报警值
    Integer BatteryAlarmValue;//电池电量报警值
    String HardwareVer;//硬件版本号 DYL-I-TC-H-V2.00
    String SoftwareVer;//软件版本号 DYL-I-TC-S-V4.10
    String SimCardNo;//SIM卡号 133970797906

    public XLRegisterRequest() {
    }

    public XLRegisterRequest(String hxzFactory, String hxzId, String protocolVer, String cmd,
            String unloaderType, String weight1SetExist, String weight2SetExist,
            String obliguityXExist, String obliguityYExist, String GPSExist,
            String wirelessExist, String weight1Disabled, String weight2Disabled,
            String obliguityXDisabled, String obliguityYDisabled, String weightPreAlarmValue,
            String obliguityXPreAlarmValue, String obliguityYPreAlarmValue,
            Integer batteryPreAlarmValue, String weightAlarmValue, String obliguityXAlarmValue,
            String obliguityYAlarmValue, Integer batteryAlarmValue, String hardwareVer,
            String softwareVer, String simCardNo) {
        this.hxzFactory = hxzFactory;
        this.hxzId = hxzId;
        this.protocolVer = protocolVer;
        Cmd = cmd;
        UnloaderType = unloaderType;
        Weight1SetExist = weight1SetExist;
        Weight2SetExist = weight2SetExist;
        ObliguityXExist = obliguityXExist;
        ObliguityYExist = obliguityYExist;
        this.GPSExist = GPSExist;
        WirelessExist = wirelessExist;
        Weight1Disabled = weight1Disabled;
        Weight2Disabled = weight2Disabled;
        ObliguityXDisabled = obliguityXDisabled;
        ObliguityYDisabled = obliguityYDisabled;
        WeightPreAlarmValue = weightPreAlarmValue;
        ObliguityXPreAlarmValue = obliguityXPreAlarmValue;
        ObliguityYPreAlarmValue = obliguityYPreAlarmValue;
        BatteryPreAlarmValue = batteryPreAlarmValue;
        WeightAlarmValue = weightAlarmValue;
        ObliguityXAlarmValue = obliguityXAlarmValue;
        ObliguityYAlarmValue = obliguityYAlarmValue;
        BatteryAlarmValue = batteryAlarmValue;
        HardwareVer = hardwareVer;
        SoftwareVer = softwareVer;
        SimCardNo = simCardNo;
    }

    public String getHxzFactory() {
        return hxzFactory;
    }

    public void setHxzFactory(String hxzFactory) {
        this.hxzFactory = hxzFactory;
    }

    public String getHxzId() {
        return hxzId;
    }

    public void setHxzId(String hxzId) {
        this.hxzId = hxzId;
    }

    public String getProtocolVer() {
        return protocolVer;
    }

    public void setProtocolVer(String protocolVer) {
        this.protocolVer = protocolVer;
    }

    public String getCmd() {
        return Cmd;
    }

    public void setCmd(String cmd) {
        Cmd = cmd;
    }

    public String getUnloaderType() {
        return UnloaderType;
    }

    public void setUnloaderType(String unloaderType) {
        UnloaderType = unloaderType;
    }

    public String getWeight1SetExist() {
        return Weight1SetExist;
    }

    public void setWeight1SetExist(String weight1SetExist) {
        Weight1SetExist = weight1SetExist;
    }

    public String getWeight2SetExist() {
        return Weight2SetExist;
    }

    public void setWeight2SetExist(String weight2SetExist) {
        Weight2SetExist = weight2SetExist;
    }

    public String getObliguityXExist() {
        return ObliguityXExist;
    }

    public void setObliguityXExist(String obliguityXExist) {
        ObliguityXExist = obliguityXExist;
    }

    public String getObliguityYExist() {
        return ObliguityYExist;
    }

    public void setObliguityYExist(String obliguityYExist) {
        ObliguityYExist = obliguityYExist;
    }

    public String getGPSExist() {
        return GPSExist;
    }

    public void setGPSExist(String GPSExist) {
        this.GPSExist = GPSExist;
    }

    public String getWirelessExist() {
        return WirelessExist;
    }

    public void setWirelessExist(String wirelessExist) {
        WirelessExist = wirelessExist;
    }

    public String getWeight1Disabled() {
        return Weight1Disabled;
    }

    public void setWeight1Disabled(String weight1Disabled) {
        Weight1Disabled = weight1Disabled;
    }

    public String getWeight2Disabled() {
        return Weight2Disabled;
    }

    public void setWeight2Disabled(String weight2Disabled) {
        Weight2Disabled = weight2Disabled;
    }

    public String getObliguityXDisabled() {
        return ObliguityXDisabled;
    }

    public void setObliguityXDisabled(String obliguityXDisabled) {
        ObliguityXDisabled = obliguityXDisabled;
    }

    public String getObliguityYDisabled() {
        return ObliguityYDisabled;
    }

    public void setObliguityYDisabled(String obliguityYDisabled) {
        ObliguityYDisabled = obliguityYDisabled;
    }

    public String getWeightPreAlarmValue() {
        return WeightPreAlarmValue;
    }

    public void setWeightPreAlarmValue(String weightPreAlarmValue) {
        WeightPreAlarmValue = weightPreAlarmValue;
    }

    public String getObliguityXPreAlarmValue() {
        return ObliguityXPreAlarmValue;
    }

    public void setObliguityXPreAlarmValue(String obliguityXPreAlarmValue) {
        ObliguityXPreAlarmValue = obliguityXPreAlarmValue;
    }

    public String getObliguityYPreAlarmValue() {
        return ObliguityYPreAlarmValue;
    }

    public void setObliguityYPreAlarmValue(String obliguityYPreAlarmValue) {
        ObliguityYPreAlarmValue = obliguityYPreAlarmValue;
    }

    public Integer getBatteryPreAlarmValue() {
        return BatteryPreAlarmValue;
    }

    public void setBatteryPreAlarmValue(Integer batteryPreAlarmValue) {
        BatteryPreAlarmValue = batteryPreAlarmValue;
    }

    public String getWeightAlarmValue() {
        return WeightAlarmValue;
    }

    public void setWeightAlarmValue(String weightAlarmValue) {
        WeightAlarmValue = weightAlarmValue;
    }

    public String getObliguityXAlarmValue() {
        return ObliguityXAlarmValue;
    }

    public void setObliguityXAlarmValue(String obliguityXAlarmValue) {
        ObliguityXAlarmValue = obliguityXAlarmValue;
    }

    public String getObliguityYAlarmValue() {
        return ObliguityYAlarmValue;
    }

    public void setObliguityYAlarmValue(String obliguityYAlarmValue) {
        ObliguityYAlarmValue = obliguityYAlarmValue;
    }

    public Integer getBatteryAlarmValue() {
        return BatteryAlarmValue;
    }

    public void setBatteryAlarmValue(Integer batteryAlarmValue) {
        BatteryAlarmValue = batteryAlarmValue;
    }

    public String getHardwareVer() {
        return HardwareVer;
    }

    public void setHardwareVer(String hardwareVer) {
        HardwareVer = hardwareVer;
    }

    public String getSoftwareVer() {
        return SoftwareVer;
    }

    public void setSoftwareVer(String softwareVer) {
        SoftwareVer = softwareVer;
    }

    public String getSimCardNo() {
        return SimCardNo;
    }

    public void setSimCardNo(String simCardNo) {
        SimCardNo = simCardNo;
    }
}
