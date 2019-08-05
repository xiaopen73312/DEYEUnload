package com.deye.xl.entity;

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
@Table(name = "IpData")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "pDownlineVF", procedureName = "dbo.pDownlineVF", parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "HxzFactory", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "HxzId", type = String.class)
        })})
public class IpData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id")
    BigDecimal rowId;
    @Column(name = "HxzFactory")
    String hxzFactory;
    @Column(name = "HxzId")
    String hxzId;
    @Column(name = "HxzIp")
    String hxzIp;
    @Column(name = "DateTime")
    String dateTime;

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

    public String getHxzIp() {
        return hxzIp;
    }

    public void setHxzIp(String hxzIp) {
        this.hxzIp = hxzIp;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
