package com.deye.demo.entity;


import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AlarmDataCrane")
//@NamedStoredProcedureQueries({
//        @NamedStoredProcedureQuery(name = "pAlarmDataCraneVF", procedureName = "dbo.pAlarmDataCraneVF", parameters = {
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "HxzFactory", type = String.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "HxzId", type = String.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "StartTime", type = Date.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "EndTime", type = Date.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "MaxRadiusAlarm", type = Integer.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "MinRadiusAlarm", type = Integer.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "HeightUpAlarm", type = Integer.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "HeightDownAlarm", type = Integer.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "AngleLeftAlarm", type = Integer.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "AngleRightAlarm", type = Integer.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "TorqueAlarm", type = Integer.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "WindAlarm", type = Integer.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "ObliqueAlarm", type = Integer.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "EnvironmentAlarm", type = Integer.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "MultiAlarm", type = Integer.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "HxzIp", type = String.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "NowTime", type = String.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "WorkNo", type = Integer.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "Name", type = String.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "IdNo", type = String.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "Alarm1", type = Integer.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "Alarm2", type = Integer.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "Alarm3", type = Integer.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "Alarm4", type = Integer.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "Alarm5", type = Integer.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "MultiNegAlarm", type = String.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "MultiPosAlarm", type = String.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "MultiOutAlarm", type = String.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "MultiInAlarm", type = String.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "EnvtNegAlarm", type = String.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "EnvtPosAlarm", type = String.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "EnvtOutAlarm", type = String.class),
//                @StoredProcedureParameter(mode = ParameterMode.IN, name = "EnvtInAlarm", type = String.class)
//        })})

public class AlarmDataCrane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id")
    BigDecimal rowId;
    @Column(name = "HxzFactory")
    String HxzFactory;
    @Column(name = "HxzId")
    String HxzId;
    @Column(name = "StartTime")
    String StartTime;
    @Column(name = "EndTime")
    String EndTime;
    @Column(name = "MomentAlarm")
    String MomentAlarm;
    @Column(name = "WindSpeedAlarm")
    String WindSpeedAlarm;
    @Column(name = "HeightAlarm")
    String HeightAlarm;
    @Column(name = "MinRangeAlarm")
    String MinRangeAlarm;
    @Column(name = "MaxRangeAlarm")
    String MaxRangeAlarm;
    @Column(name = "PosAngleAlarm")
    String PosAngleAlarm;
    @Column(name = "NegAngleAlarm")
    String NegAngleAlarm;
    @Column(name = "ObliguityAlarm")
    String ObliguityAlarm;
    @Column(name = "EnvironmentAlarm")
    String EnvironmentAlarm;
    @Column(name = "MultiAlarm")
    String MultiAlarm;
    @Column(name = "WorkNo")
    Integer WorkNo;
    @Column(name = "Name")
    String Name;
    @Column(name = "IdNo")
    String IdNo;
    @Column(name = "Alarm1")
    Integer Alarm1;
    @Column(name = "Alarm2")
    Integer Alarm2;
    @Column(name = "Alarm3")
    Integer Alarm3;
    @Column(name = "Alarm4")
    Integer Alarm4;
    @Column(name = "Alarm5")
    Integer Alarm5;
    @Column(name = "MultiNegAlarm")
    String MultiNegAlarm;
    @Column(name = "MultiPosAlarm")
    String MultiPosAlarm;
    @Column(name = "MultiOutAlarm")
    String MultiOutAlarm;
    @Column(name = "MultiInAlarm")
    String MultiInAlarm;
    @Column(name = "EnvtNegAlarm")
    String EnvtNegAlarm;
    @Column(name = "EnvtPosAlarm")
    String EnvtPosAlarm;
    @Column(name = "EnvtOutAlarm")
    String EnvtOutAlarm;
    @Column(name = "EnvtInAlarm")
    String EnvtInAlarm;


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

    public String getMomentAlarm() {
        return MomentAlarm;
    }

    public void setMomentAlarm(String momentAlarm) {
        MomentAlarm = momentAlarm;
    }

    public String getWindSpeedAlarm() {
        return WindSpeedAlarm;
    }

    public void setWindSpeedAlarm(String windSpeedAlarm) {
        WindSpeedAlarm = windSpeedAlarm;
    }

    public String getHeightAlarm() {
        return HeightAlarm;
    }

    public void setHeightAlarm(String heightAlarm) {
        HeightAlarm = heightAlarm;
    }

    public String getMinRangeAlarm() {
        return MinRangeAlarm;
    }

    public void setMinRangeAlarm(String minRangeAlarm) {
        MinRangeAlarm = minRangeAlarm;
    }

    public String getMaxRangeAlarm() {
        return MaxRangeAlarm;
    }

    public void setMaxRangeAlarm(String maxRangeAlarm) {
        MaxRangeAlarm = maxRangeAlarm;
    }

    public String getPosAngleAlarm() {
        return PosAngleAlarm;
    }

    public void setPosAngleAlarm(String posAngleAlarm) {
        PosAngleAlarm = posAngleAlarm;
    }

    public String getNegAngleAlarm() {
        return NegAngleAlarm;
    }

    public void setNegAngleAlarm(String negAngleAlarm) {
        NegAngleAlarm = negAngleAlarm;
    }

    public String getObliguityAlarm() {
        return ObliguityAlarm;
    }

    public void setObliguityAlarm(String obliguityAlarm) {
        ObliguityAlarm = obliguityAlarm;
    }

    public String getEnvironmentAlarm() {
        return EnvironmentAlarm;
    }

    public void setEnvironmentAlarm(String environmentAlarm) {
        EnvironmentAlarm = environmentAlarm;
    }

    public String getMultiAlarm() {
        return MultiAlarm;
    }

    public void setMultiAlarm(String multiAlarm) {
        MultiAlarm = multiAlarm;
    }

    public Integer getWorkNo() {
        return WorkNo;
    }

    public void setWorkNo(Integer workNo) {
        WorkNo = workNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIdNo() {
        return IdNo;
    }

    public void setIdNo(String idNo) {
        IdNo = idNo;
    }

    public Integer getAlarm1() {
        return Alarm1;
    }

    public void setAlarm1(Integer alarm1) {
        Alarm1 = alarm1;
    }

    public Integer getAlarm2() {
        return Alarm2;
    }

    public void setAlarm2(Integer alarm2) {
        Alarm2 = alarm2;
    }

    public Integer getAlarm3() {
        return Alarm3;
    }

    public void setAlarm3(Integer alarm3) {
        Alarm3 = alarm3;
    }

    public Integer getAlarm4() {
        return Alarm4;
    }

    public void setAlarm4(Integer alarm4) {
        Alarm4 = alarm4;
    }

    public Integer getAlarm5() {
        return Alarm5;
    }

    public void setAlarm5(Integer alarm5) {
        Alarm5 = alarm5;
    }

    public String getMultiNegAlarm() {
        return MultiNegAlarm;
    }

    public void setMultiNegAlarm(String multiNegAlarm) {
        MultiNegAlarm = multiNegAlarm;
    }

    public String getMultiPosAlarm() {
        return MultiPosAlarm;
    }

    public void setMultiPosAlarm(String multiPosAlarm) {
        MultiPosAlarm = multiPosAlarm;
    }

    public String getMultiOutAlarm() {
        return MultiOutAlarm;
    }

    public void setMultiOutAlarm(String multiOutAlarm) {
        MultiOutAlarm = multiOutAlarm;
    }

    public String getMultiInAlarm() {
        return MultiInAlarm;
    }

    public void setMultiInAlarm(String multiInAlarm) {
        MultiInAlarm = multiInAlarm;
    }

    public String getEnvtNegAlarm() {
        return EnvtNegAlarm;
    }

    public void setEnvtNegAlarm(String envtNegAlarm) {
        EnvtNegAlarm = envtNegAlarm;
    }

    public String getEnvtPosAlarm() {
        return EnvtPosAlarm;
    }

    public void setEnvtPosAlarm(String envtPosAlarm) {
        EnvtPosAlarm = envtPosAlarm;
    }

    public String getEnvtOutAlarm() {
        return EnvtOutAlarm;
    }

    public void setEnvtOutAlarm(String envtOutAlarm) {
        EnvtOutAlarm = envtOutAlarm;
    }

    public String getEnvtInAlarm() {
        return EnvtInAlarm;
    }

    public void setEnvtInAlarm(String envtInAlarm) {
        EnvtInAlarm = envtInAlarm;
    }
}
