package com.deye.xl.entity;

import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    BigDecimal id;
    @Column(name = "Address")
    String Address;
    @Column(name = "EndTime")
    Date EndTime;
    @Column(name = "Latitude")
    Float Latitude;
    @Column(name = "LinkMan")
    String LinkMan;
    @Column(name = "Longitude")
    Float Longitude;
    @Column(name = "Name")
    String Name;
    @Column(name = "Phone")
    String Phone;
    @Column(name = "Remark")
    String Remark;//备注
    @Column(name = "StartTime")
    Date StartTime;
    @Column(name = "BuildUnit_id")
    BigDecimal BuildUnit_id; //监理单位
    @Column(name = "SafetyStation_id")
    BigDecimal SafetyStation_id;//所属安监站
    @Column(name = "SupervisorUnit_id")
    BigDecimal SupervisorUnit_id;
    @Column(name = "WorkUnit_id")
    BigDecimal WorkUnit_id;//施工单位
    @Column(name = "JobLinkMan")
    String JobLinkMan;
    @Column(name = "JobTel")
    String JobTel;
    @Column(name = "VideoCount")
    Integer VideoCount;//通道数
    @Column(name = "VideoId")
    String VideoId;//视频编号

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Date getEndTime() {
        return EndTime;
    }

    public void setEndTime(Date endTime) {
        EndTime = endTime;
    }

    public Float getLatitude() {
        return Latitude;
    }

    public void setLatitude(Float latitude) {
        Latitude = latitude;
    }

    public String getLinkMan() {
        return LinkMan;
    }

    public void setLinkMan(String linkMan) {
        LinkMan = linkMan;
    }

    public Float getLongitude() {
        return Longitude;
    }

    public void setLongitude(Float longitude) {
        Longitude = longitude;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }

    public BigDecimal getBuildUnit_id() {
        return BuildUnit_id;
    }

    public void setBuildUnit_id(BigDecimal buildUnit_id) {
        BuildUnit_id = buildUnit_id;
    }

    public BigDecimal getSafetyStation_id() {
        return SafetyStation_id;
    }

    public void setSafetyStation_id(BigDecimal safetyStation_id) {
        SafetyStation_id = safetyStation_id;
    }

    public BigDecimal getSupervisorUnit_id() {
        return SupervisorUnit_id;
    }

    public void setSupervisorUnit_id(BigDecimal supervisorUnit_id) {
        SupervisorUnit_id = supervisorUnit_id;
    }

    public BigDecimal getWorkUnit_id() {
        return WorkUnit_id;
    }

    public void setWorkUnit_id(BigDecimal workUnit_id) {
        WorkUnit_id = workUnit_id;
    }

    public String getJobLinkMan() {
        return JobLinkMan;
    }

    public void setJobLinkMan(String jobLinkMan) {
        JobLinkMan = jobLinkMan;
    }

    public String getJobTel() {
        return JobTel;
    }

    public void setJobTel(String jobTel) {
        JobTel = jobTel;
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
}
