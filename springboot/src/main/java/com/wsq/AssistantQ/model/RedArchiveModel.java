package com.wsq.AssistantQ.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author CYann
 * @date 2018-02-26 0:30
 *  红色档案
 * -所属用户编号 stuNumber(String varchar) 外键
 * -加入党日期 joinDate(String varchar)
 * -成为积极分子日期 becomeActivistDate(Stirng varchar)
 * -介绍人 Introducer(String varchar)
 */


@Entity
@DynamicUpdate(true)
@Table(name = "tb_redarchive")
public class RedArchiveModel extends BaseEntity{
    @Column(nullable = false , length = 32)
    private String stuNumber;
    @Column(length = 32)
    private String joinDate;
    @Column(length = 32)
    private String activistDate;
    @Column(nullable = false , length = 32)
    private String introducer;
    @Column(nullable = false , length = 32)
    private String introducerB;

    public String getIntroducerB() {
        return introducerB;
    }

    public void setIntroducerB(String introducerB) {
        this.introducerB = introducerB;
    }

    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getIntroducer() {
        return introducer;
    }

    public String getActivistDate() {
        return activistDate;
    }

    public void setActivistDate(String activistDate) {
        this.activistDate = activistDate;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer;
    }
}
