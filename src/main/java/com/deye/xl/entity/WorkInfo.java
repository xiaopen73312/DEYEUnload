package com.deye.xl.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WorkInfo")
public class WorkInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    BigDecimal id;
    @Column(name = "CraneEndDate")
    String CraneEndDate;
    @Column(name = "CraneOperatorNo")
    String CraneOperatorNo; //塔机证书
    @Column(name = "CraneStartDate")
    String CraneStartDate;
    @Column(name = "DownlineTime")
    String DownlineTime;
    @Column(name = "ElevatorEndDate")
    String ElevatorEndDate;
    @Column(name = "ElevatorOperatorNo")
    String ElevatorOperatorNo;  //升降机证书
    @Column(name = "ElevatorStartDate")
    String ElevatorStartDate;
    @Column(name = "Gender")
    String Gender;
    @Column(name = "IdCard")
    String IdCard;
    @Column(name = "Name")
    String Name;
    @Column(name = "OnlineTime")
    String OnlineTime;
    @Column(name = "Project_Name")
    String Project_Name;
    @Column(name = "RunTime")
    String RunTime;
    @Column(name = "Tel")
    String Tel;
    @Column(name = "TowerCrane_CraneId")
    String TowerCrane_CraneId;
    @Column(name = "TowerCrane_Factory")
    String TowerCrane_Factory;
    @Column(name = "TowerCrane_HxzId")
    String TowerCrane_HxzId;
    @Column(name = "TowerCrane_Type")
    Integer TowerCrane_Type;
    @Column(name = "WorkNo_WorkNo")
    Integer WorkNo_WorkNo;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getCraneEndDate() {
        return CraneEndDate;
    }

    public void setCraneEndDate(String craneEndDate) {
        CraneEndDate = craneEndDate;
    }

    public String getCraneOperatorNo() {
        return CraneOperatorNo;
    }

    public void setCraneOperatorNo(String craneOperatorNo) {
        CraneOperatorNo = craneOperatorNo;
    }

    public String getCraneStartDate() {
        return CraneStartDate;
    }

    public void setCraneStartDate(String craneStartDate) {
        CraneStartDate = craneStartDate;
    }

    public String getDownlineTime() {
        return DownlineTime;
    }

    public void setDownlineTime(String downlineTime) {
        DownlineTime = downlineTime;
    }

    public String getElevatorEndDate() {
        return ElevatorEndDate;
    }

    public void setElevatorEndDate(String elevatorEndDate) {
        ElevatorEndDate = elevatorEndDate;
    }

    public String getElevatorOperatorNo() {
        return ElevatorOperatorNo;
    }

    public void setElevatorOperatorNo(String elevatorOperatorNo) {
        ElevatorOperatorNo = elevatorOperatorNo;
    }

    public String getElevatorStartDate() {
        return ElevatorStartDate;
    }

    public void setElevatorStartDate(String elevatorStartDate) {
        ElevatorStartDate = elevatorStartDate;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getIdCard() {
        return IdCard;
    }

    public void setIdCard(String idCard) {
        IdCard = idCard;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getOnlineTime() {
        return OnlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        OnlineTime = onlineTime;
    }

    public String getProject_Name() {
        return Project_Name;
    }

    public void setProject_Name(String project_Name) {
        Project_Name = project_Name;
    }

    public String getRunTime() {
        return RunTime;
    }

    public void setRunTime(String runTime) {
        RunTime = runTime;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getTowerCrane_CraneId() {
        return TowerCrane_CraneId;
    }

    public void setTowerCrane_CraneId(String towerCrane_CraneId) {
        TowerCrane_CraneId = towerCrane_CraneId;
    }

    public String getTowerCrane_Factory() {
        return TowerCrane_Factory;
    }

    public void setTowerCrane_Factory(String towerCrane_Factory) {
        TowerCrane_Factory = towerCrane_Factory;
    }

    public String getTowerCrane_HxzId() {
        return TowerCrane_HxzId;
    }

    public void setTowerCrane_HxzId(String towerCrane_HxzId) {
        TowerCrane_HxzId = towerCrane_HxzId;
    }

    public Integer getTowerCrane_Type() {
        return TowerCrane_Type;
    }

    public void setTowerCrane_Type(Integer towerCrane_Type) {
        TowerCrane_Type = towerCrane_Type;
    }

    public Integer getWorkNo_WorkNo() {
        return WorkNo_WorkNo;
    }

    public void setWorkNo_WorkNo(Integer workNo_WorkNo) {
        WorkNo_WorkNo = workNo_WorkNo;
    }
}
