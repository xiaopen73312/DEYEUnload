package com.deye.xl.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "XL_ConfigData")
public class XLConfigData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    BigDecimal Id;
    Integer ErrorDelay;//int
    Integer HeartBeatInterval;// int
    Integer NoWorkInterval;//int
    Integer WorkInterval;// int
    String ServerIp;// varchar
    String ServerPort;//varchar

    public BigDecimal getId() {
        return Id;
    }

    public void setId(BigDecimal id) {
        Id = id;
    }

    public Integer getErrorDelay() {
        return ErrorDelay;
    }

    public void setErrorDelay(Integer errorDelay) {
        ErrorDelay = errorDelay;
    }

    public Integer getHeartBeatInterval() {
        return HeartBeatInterval;
    }

    public void setHeartBeatInterval(Integer heartBeatInterval) {
        HeartBeatInterval = heartBeatInterval;
    }

    public Integer getNoWorkInterval() {
        return NoWorkInterval;
    }

    public void setNoWorkInterval(Integer noWorkInterval) {
        NoWorkInterval = noWorkInterval;
    }

    public Integer getWorkInterval() {
        return WorkInterval;
    }

    public void setWorkInterval(Integer workInterval) {
        WorkInterval = workInterval;
    }

    public String getServerIp() {
        return ServerIp;
    }

    public void setServerIp(String serverIp) {
        ServerIp = serverIp;
    }

    public String getServerPort() {
        return ServerPort;
    }

    public void setServerPort(String serverPort) {
        ServerPort = serverPort;
    }
}
