package com.deye.demo.entity;

import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//塔机
@Entity
@Table(name = "TowerCrane")
public class TowerCrane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    BigDecimal Id;

    @Column(name = "CraneId")
    String CraneId;  //备案编号

    @Column(name = "Factory")
    String Factory;//生产商
    @Column(name = "HxzId")
    String HxzId;
    @Column(name = "LeaseDate")
    Date LeaseDate;
    @Column(name = "LeasePhone")
    String LeasePhone;//产权单位电话
    @Column(name = "Name")
    String Name;
    @Column(name = "Remark")
    String Remark;

    @Column(name = "StationPhone")
    String StationPhone;//安监站电话
    @Column(name = "Status")
    int Status;//状态
    @Column(name = "WorkPhone")
    String WorkPhone;//施工单位电话
    @Column(name = "Project_id")
    BigDecimal Project_id; //所属项目
    @Column(name = "RentUnit_id")
    BigDecimal RentUnit_id; //产权单位
    @Column(name = "InstallTime")
    Date InstallTime; //安装时间
    @Column(name = "RecordStatus")
    Integer RecordStatus; //备案状态
    @Column(name = "UnloadTime")
    Date UnloadTime;//拆卸时间
    @Column(name = "LoginData_row_id")
    BigDecimal LoginData_row_id; //租赁到期时间

    @Column(name = "Type")
    Integer Type;
    @Column(name = "VideoCount")
    Integer VideoCount; //通道数
    @Column(name = "VideoId")
    String VideoId;//视频编号
    @Column(name = "smsTime")
    Date smsTime;
    @Column(name = "URLCode")
    String URLCode;
    @Column(name = "URLCodeType")
    String URLCodeType;
    @Column(name = "deviceCode")
    String deviceCode;
    @Column(name = "deviceType")
    String deviceType;
    @Column(name = "tongdaoNum")
    Integer tongdaoNum;
    @Column(name = "images")
    String images;

    public BigDecimal getId() {
        return Id;
    }

    public void setId(BigDecimal id) {
        Id = id;
    }

    public String getCraneId() {
        return CraneId;
    }

    public void setCraneId(String craneId) {
        CraneId = craneId;
    }

    public String getFactory() {
        return Factory;
    }

    public void setFactory(String factory) {
        Factory = factory;
    }

    public String getHxzId() {
        return HxzId;
    }

    public void setHxzId(String hxzId) {
        HxzId = hxzId;
    }

    public Date getLeaseDate() {
        return LeaseDate;
    }

    public void setLeaseDate(Date leaseDate) {
        LeaseDate = leaseDate;
    }

    public String getLeasePhone() {
        return LeasePhone;
    }

    public void setLeasePhone(String leasePhone) {
        LeasePhone = leasePhone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getStationPhone() {
        return StationPhone;
    }

    public void setStationPhone(String stationPhone) {
        StationPhone = stationPhone;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getWorkPhone() {
        return WorkPhone;
    }

    public void setWorkPhone(String workPhone) {
        WorkPhone = workPhone;
    }

    public BigDecimal getProject_id() {
        return Project_id;
    }

    public void setProject_id(BigDecimal project_id) {
        Project_id = project_id;
    }

    public BigDecimal getRentUnit_id() {
        return RentUnit_id;
    }

    public void setRentUnit_id(BigDecimal rentUnit_id) {
        RentUnit_id = rentUnit_id;
    }

    public Date getInstallTime() {
        return InstallTime;
    }

    public void setInstallTime(Date installTime) {
        InstallTime = installTime;
    }

    public Integer getRecordStatus() {
        return RecordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        RecordStatus = recordStatus;
    }

    public Date getUnloadTime() {
        return UnloadTime;
    }

    public void setUnloadTime(Date unloadTime) {
        UnloadTime = unloadTime;
    }

    public BigDecimal getLoginData_row_id() {
        return LoginData_row_id;
    }

    public void setLoginData_row_id(BigDecimal loginData_row_id) {
        LoginData_row_id = loginData_row_id;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public Integer getVideoCount() {
        return VideoCount;
    }

    public void setVideoCount(Integer videoCount) {
        VideoCount = videoCount;
    }

    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String videoId) {
        VideoId = videoId;
    }

    public Date getSmsTime() {
        return smsTime;
    }

    public void setSmsTime(Date smsTime) {
        this.smsTime = smsTime;
    }

    public String getURLCode() {
        return URLCode;
    }

    public void setURLCode(String URLCode) {
        this.URLCode = URLCode;
    }

    public String getURLCodeType() {
        return URLCodeType;
    }

    public void setURLCodeType(String URLCodeType) {
        this.URLCodeType = URLCodeType;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getTongdaoNum() {
        return tongdaoNum;
    }

    public void setTongdaoNum(Integer tongdaoNum) {
        this.tongdaoNum = tongdaoNum;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
