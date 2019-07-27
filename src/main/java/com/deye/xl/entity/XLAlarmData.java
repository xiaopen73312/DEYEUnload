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
public class XLAlarmData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id")
    BigDecimal rowId;
    String HxzFactory;
    String HxzId;
    Integer Projectid;
    BigDecimal LoginData_row_id;
    String StartTime;
    String EndTime;
    Integer AlarmType;//报警类型
    Float MaxValue; //最大报警值
    String MaxValuePercent; //载重百分比

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

    public Integer getProjectid() {
        return Projectid;
    }

    public void setProjectid(Integer projectid) {
        Projectid = projectid;
    }

    public BigDecimal getLoginData_row_id() {
        return LoginData_row_id;
    }

    public void setLoginData_row_id(BigDecimal loginData_row_id) {
        LoginData_row_id = loginData_row_id;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public Integer getAlarmType() {
        return AlarmType;
    }

    public void setAlarmType(Integer alarmType) {
        AlarmType = alarmType;
    }

    public Float getMaxValue() {
        return MaxValue;
    }

    public void setMaxValue(Float maxValue) {
        MaxValue = maxValue;
    }

    public String getMaxValuePercent() {
        return MaxValuePercent;
    }

    public void setMaxValuePercent(String maxValuePercent) {
        MaxValuePercent = maxValuePercent;
    }
}
