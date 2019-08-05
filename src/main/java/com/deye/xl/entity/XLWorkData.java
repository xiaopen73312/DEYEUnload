package com.deye.xl.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "XL_WorkData")
public class XLWorkData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id")
    BigDecimal rowId;
    String HxzFactory;
    String HxzId;
    Integer Projectid;//	int
    BigDecimal LoginData_row_id;//	decimal
    String StartTime;//	varchar
    String EndTime;//	varchar
    String WorkTime;//	varchar
    Integer WeightStatus;//	int
    Float WorkWeight;//	float
    Float WorkPercent;//	float

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

    public String getWorkTime() {
        return WorkTime;
    }

    public void setWorkTime(String workTime) {
        WorkTime = workTime;
    }

    public Integer getWeightStatus() {
        return WeightStatus;
    }

    public void setWeightStatus(Integer weightStatus) {
        WeightStatus = weightStatus;
    }

    public Float getWorkWeight() {
        return WorkWeight;
    }

    public void setWorkWeight(Float workWeight) {
        WorkWeight = workWeight;
    }

    public Float getWorkPercent() {
        return WorkPercent;
    }

    public void setWorkPercent(Float workPercent) {
        WorkPercent = workPercent;
    }
}
