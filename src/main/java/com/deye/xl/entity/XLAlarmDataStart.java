package com.deye.xl.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "XL_AlarmDataStart")
public class XLAlarmDataStart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id")
    BigDecimal rowId;
    String HxzFactory;
    String HxzId;
    Integer Projectid;//	int
    BigDecimal LoginData_row_id;// decimal
    String StartTime;// varchar
    Integer AlarmType;//int 报警类型
    Float AlarmValue;//float 报警值

    public XLAlarmDataStart() {

    }

    public XLAlarmDataStart(String hxzFactory, String hxzId, Integer projectid,
            BigDecimal loginData_row_id, String startTime, Integer alarmType,
            Float alarmValue) {
        HxzFactory = hxzFactory;
        HxzId = hxzId;
        Projectid = projectid;
        LoginData_row_id = loginData_row_id;
        StartTime = startTime;
        AlarmType = alarmType;
        AlarmValue = alarmValue;
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

    public Integer getAlarmType() {
        return AlarmType;
    }

    public void setAlarmType(Integer alarmType) {
        AlarmType = alarmType;
    }

    public Float getAlarmValue() {
        return AlarmValue;
    }

    public void setAlarmValue(Float alarmValue) {
        AlarmValue = alarmValue;
    }
}
