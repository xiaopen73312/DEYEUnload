package com.deye.demo.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "GpsData")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "pGpsDataVF", procedureName = "dbo.pGpsDataVF", parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "HxzFactory", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "HxzId", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "Latitude", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "Longitude", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "HxzIp", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "NowTime", type = String.class)
        })})
public class GpsData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id")
    BigDecimal rowId;
    @Column(name = "HxzFactory")
    String hxzFactory;
    @Column(name = "HxzId")
    String hxzId;
    @Column(name = "Latitude")
    String Latitude; //GPS纬度
    @Column(name = "Longitude")
    String Longitude; //GPS经度
    @Column(name = "LocateLock")
    int LocateLock;//手动定位标志
    @Column(name = "WorkSiteNo")
    int WorkSiteNo; //工地编号

    public BigDecimal getRowId() {
        return rowId;
    }

    public void setRowId(BigDecimal rowId) {
        this.rowId = rowId;
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

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public int getLocateLock() {
        return LocateLock;
    }

    public void setLocateLock(int locateLock) {
        LocateLock = locateLock;
    }

    public int getWorkSiteNo() {
        return WorkSiteNo;
    }

    public void setWorkSiteNo(int workSiteNo) {
        WorkSiteNo = workSiteNo;
    }
}
