package com.deye.xl.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "XL_RealtimeData")
public class XLRealtimeData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id")
    BigDecimal rowId;
    String HxzFactory;
    String HxzId;
    BigDecimal Projectid;
    BigDecimal LoginData_row_id;
    String RTime;
    Float Weight1;//载重1
    Float Weight2;//载重2
    Float Weight;//合成载重
    Float WeightPercent;//载重百分比
    Float ObliguityX;//倾角X轴
    Float ObliguityY;//倾角Y轴
    Float Obliguity;//合成倾角
    Integer BatteryPercent;//电量百分比
    String Weight1Status;//载重1状态
    String Weight2Status;//载重2状态
    String ObliguityXStatus;//倾角X状态
    String ObliguityYStatus;//倾角Y状态
    String Wireless1Status;//无线连接1状态
    String Wireless2Status;//无线连接2状态
    String GpsStatus;//GPS状态
    String BatteryStatus;//电池状态
}
