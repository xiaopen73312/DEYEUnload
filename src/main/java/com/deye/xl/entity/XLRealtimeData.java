package com.deye.xl.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "XL_RealtimeData")
public class XLRealtimeData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id")
    BigDecimal rowId;
    String HxzFactory;
    String HxzId;
    BigDecimal Projectid;
    BigDecimal LoginData_row_id;
    String RTime;
    Float Weight1;//载重1
    Float Weight2;//载重2
    Float Weight;//合成载重
    Float WeightPercent;//载重百分比
    Float ObliguityX;//倾角X轴
    Float ObliguityY;//倾角Y轴
    Float Obliguity;//合成倾角
    Integer BatteryPercent;//电量百分比
    String Weight1Status;//载重1状态
    String Weight2Status;//载重2状态
    String ObliguityXStatus;//倾角X状态
    String ObliguityYStatus;//倾角Y状态
    String Wireless1Status;//无线连接1状态
    String Wireless2Status;//无线连接2状态
    String GpsStatus;//GPS状态
    String BatteryStatus;//电池状态

    public XLRealtimeData() {
    }

    public XLRealtimeData(String hxzFactory, String hxzId, BigDecimal projectid,
            BigDecimal loginData_row_id, String RTime, Float weight1, Float weight2,
            Float weight, Float weightPercent, Float obliguityX, Float obliguityY,
            Float obliguity, Integer batteryPercent, String weight1Status,
            String weight2Status, String obliguityXStatus, String obliguityYStatus,
            String wireless1Status, String wireless2Status, String gpsStatus,
            String batteryStatus) {
        HxzFactory = hxzFactory;
        HxzId = hxzId;
        Projectid = projectid;
        LoginData_row_id = loginData_row_id;
        this.RTime = RTime;
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

    public BigDecimal getProjectid() {
        return Projectid;
    }

    public void setProjectid(BigDecimal projectid) {
        Projectid = projectid;
    }

    public BigDecimal getLoginData_row_id() {
        return LoginData_row_id;
    }

    public void setLoginData_row_id(BigDecimal loginData_row_id) {
        LoginData_row_id = loginData_row_id;
    }

    public String getRTime() {
        return RTime;
    }

    public void setRTime(String RTime) {
        this.RTime = RTime;
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
