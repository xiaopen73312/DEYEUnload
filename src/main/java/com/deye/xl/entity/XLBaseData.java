package com.deye.xl.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "XL_AlarmData")
public class XLBaseData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id")
    BigDecimal rowId;
    String HxzFactory;
    String HxzId;
    String UnloaderType;
    String Weight1SetExist;
    String Weight2SetExist;
    String ObliguityXExist;
    String ObliguityYExist;
    String GPSExist;
    String WirelessExist;
    String Weight1Disabled;
    String Weight2Disabled;
    String ObliguityXDisabled;
    String ObliguityYDisabled;
    String WeightPreAlarmValue;
    String ObliguityXPreAlarmValue;
    String ObliguityYPreAlarmValue;
    String BatteryPreAlarmValue;
    String WeightAlarmValue;
    String ObliguityXAlarmValue;
    String ObliguityYAlarmValue;
    String BatteryAlarmValue;
    String Weight1Zero;
    String Weight2Zero;
    String ObliguityXZero;
    String ObliguityYZero;

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
