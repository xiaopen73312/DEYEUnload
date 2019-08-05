package com.deye.xl.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RuntimeData")
public class RuntimeData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id")
    BigDecimal rowId;
    @Column(name = "HxzFactory")
    String hxzFactory;//黑匣子厂家
    @Column(name = "HxzId")
    String hxzId;//黑匣子编号
    @Column(name = "OnlineTime")
    String OnlineTime; //上线时间
    @Column(name = "DownlineTime")
    String DownlineTime; //下线时间
    @Column(name = "RunTime")
    String RunTime; //运行时长

    public RuntimeData() {
    }

    public RuntimeData(String hxzFactory, String hxzId, String onlineTime,
            String downlineTime, String runTime) {
        this.hxzFactory = hxzFactory;
        this.hxzId = hxzId;
        OnlineTime = onlineTime;
        DownlineTime = downlineTime;
        RunTime = runTime;
    }

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

    public String getOnlineTime() {
        return OnlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        OnlineTime = onlineTime;
    }

    public String getDownlineTime() {
        return DownlineTime;
    }

    public void setDownlineTime(String downlineTime) {
        DownlineTime = downlineTime;
    }

    public String getRunTime() {
        return RunTime;
    }

    public void setRunTime(String runTime) {
        RunTime = runTime;
    }

    public static String getRunTimeStr(String RunTime) {
        if ((" ").equals(RunTime) || ("").equals(RunTime) || RunTime == null) {
            return "0分钟";
        }
        long l = Long.valueOf(RunTime);
        String data = "";
        try {
            long day = l / (24 * 60 * 60);
            long hour = (l / (60 * 60) - day * 24);
            long min = ((l / (60)) - day * 24 * 60 - hour * 60);
            long s = (l - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            if (day != 0) {
                data = day + "天";
            }
            if (hour != 0) {
                data += hour + "小时";
            }
            if (min != 0) {
                data += min + "分钟";
            }
            if (day == 0 && hour == 0 && min == 0) {
                data += "不到1分钟";
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return data;
    }
}
