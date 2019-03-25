package com.wsq.AssistantQ.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author CYann
 * @date 2018-02-25 23:50
 *  档案
 * -所属用户编号 stuNumber(String varchar) 外键
 * -档案单位 unit(String varchar)
 * -档案单位地址 unitAddress(String varchar)
 * -档案流向下级时间 flowDate(String varchar)
 */
@Entity
@DynamicUpdate(true)
@Table(name = "tb_archive")
public class ArchiveModel extends BaseEntity {
    @Column(nullable = false , length = 32)
    private String stuNumber;
    @Column(nullable = false , length = 256)
    private String unit;
    @Column(nullable = false , length = 256)
    private String unitAddress;
    @Column(nullable = false , length = 32)
    private String flowDate;

    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public String getFlowDate() {
        return flowDate;
    }

    public void setFlowDate(String flowDate) {
        this.flowDate = flowDate;
    }
}