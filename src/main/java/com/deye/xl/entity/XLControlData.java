package com.deye.xl.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "XL_ControlData")
public class XLControlData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id")
    BigDecimal rowId;
    String HxzFactory;//varchar
    String HxzId;//	varchar
    String UnloaderType;//	char
    String Weight1SetExist;//	char
    String Weight2SetExist;//	char
    String ObliguityXExist;//	char
    String ObliguityYExist;//	char
    String GPSExist;//	char
    String WirelessExist;//	char
    String Weight1Disabled;//	char
    String Weight2Disabled;//	char
    String ObliguityXDisabled;//	char
    String ObliguityYDisabled;//	char
    String WeightPreAlarmValue;//	varchar
    String ObliguityXPreAlarmValue;//	varchar
    String ObliguityYPreAlarmValue;//	varchar
    String BatteryPreAlarmValue;//	varchar
    String WeightAlarmValue;//	varchar
    String ObliguityXAlarmValue;//	varchar
    String ObliguityYAlarmValue;//	varchar
    String BatteryAlarmValue;//	varchar
    String Weight1Zero;//	varchar
    String Weight2Zero;//	varchar
    String ObliguityXZero;// varchar
    String ObliguityYZero;// varchar
    String HeartBeatInterval;// int
    Integer WorkInterval;// int
    Integer NoWorkInterval;// int
    String ServerIp;// varchar
    String ServerPort;// varchar
    String ModifyServer;// char
    String GetFlag;// char
    String SendFlag;// char
}
