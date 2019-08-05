package com.deye.xl.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "XL_BaseData")
public class XLBaseData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id")
    BigDecimal rowId;
    String HxzFactory;
    String HxzId;
    String UnloaderType;//卸料平台类型
    String Weight1SetExist;//载重1检测功能配置
    String Weight2SetExist;//载重2检测功能配置
    String ObliguityXExist;//倾角X检测功能配置
    String ObliguityYExist;//倾角y检测功能配置
    String GPSExist;//GPS定位功能配置
    String WirelessExist;//载重无线连接功能配置
    String Weight1Disabled;//载重1检测功能禁用
    String Weight2Disabled;//载重2检测功能禁用
    String ObliguityXDisabled;//倾角X检测功能禁用
    String ObliguityYDisabled;//倾角Y检测功能禁用
    String WeightPreAlarmValue;//载重预警值
    String ObliguityXPreAlarmValue;//倾角X预警值
    String ObliguityYPreAlarmValue;//倾角Y预警值
    String BatteryPreAlarmValue;//电池电量预警值
    String WeightAlarmValue;//载重报警值
    String ObliguityXAlarmValue;//倾角X报警值
    String ObliguityYAlarmValue;//倾角X报警值
    String BatteryAlarmValue;//电池电量预警值
    String Weight1Zero;//载重1零漂
    String Weight2Zero;//载重2零漂
    String ObliguityXZero;//倾角X零漂
    String ObliguityYZero;//倾角Y零漂

    public XLBaseData() {
    }

    public XLBaseData(String hxzFactory, String hxzId, String unloaderType,
            String weight1SetExist, String weight2SetExist, String obliguityXExist,
            String obliguityYExist, String GPSExist, String wirelessExist,
            String weight1Disabled, String weight2Disabled, String obliguityXDisabled,
            String obliguityYDisabled, String weightPreAlarmValue,
            String obliguityXPreAlarmValue, String obliguityYPreAlarmValue,
            String batteryPreAlarmValue, String weightAlarmValue, String obliguityXAlarmValue,
            String obliguityYAlarmValue, String batteryAlarmValue) {
        HxzFactory = hxzFactory;
        HxzId = hxzId;
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
    }

    public BigDecimal getRowId() {
        return rowId;
    }

    public void setRowId(BigDecimal rowId) {
        this.rowId = rowId;
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

    public String getBatteryPreAlarmValue() {
        return BatteryPreAlarmValue;
    }

    public void setBatteryPreAlarmValue(String batteryPreAlarmValue) {
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

    public String getBatteryAlarmValue() {
        return BatteryAlarmValue;
    }

    public void setBatteryAlarmValue(String batteryAlarmValue) {
        BatteryAlarmValue = batteryAlarmValue;
    }

    public String getWeight1Zero() {
        return Weight1Zero;
    }

    public void setWeight1Zero(String weight1Zero) {
        Weight1Zero = weight1Zero;
    }

    public String getWeight2Zero() {
        return Weight2Zero;
    }

    public void setWeight2Zero(String weight2Zero) {
        Weight2Zero = weight2Zero;
    }

    public String getObliguityXZero() {
        return ObliguityXZero;
    }

    public void setObliguityXZero(String obliguityXZero) {
        ObliguityXZero = obliguityXZero;
    }

    public String getObliguityYZero() {
        return ObliguityYZero;
    }

    public void setObliguityYZero(String obliguityYZero) {
        ObliguityYZero = obliguityYZero;
    }
}
