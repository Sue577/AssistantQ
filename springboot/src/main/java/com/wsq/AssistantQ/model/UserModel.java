package com.wsq.AssistantQ.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author CYann
 * @date 2018-02-25 22:50
 *  用户
 * -学生学号 stuNumber(String varchar)
 * -学生名称 stuName(String varchar)
 * -学生班级 stuClass(String varchar)
 * -学生专业 stuMajor(String varchar)
 * -学生入学年份 stuStartYear(String varchar)
 * -学生毕业年份 stuEndYear(String varchar)
 * -是否为党员 redParty(String varchar)
 * -现在邮箱 currentEmail(String varchar)
 * -现在联系方式 currentPhone(String varchar)
 * -用户权限 stuPower(int int) 1:实习生  2:毕业生
 */
@Entity
@DynamicUpdate(true)
@Table(name = "tb_user")
public class UserModel extends BaseEntity {
    @Column(nullable = false , length = 32)
    private String stuNumber;
    @Column(nullable = false , length = 16)
    private String stuName;
    @Column(nullable = false , length = 16)
    private String stuClass;
    @Column(nullable = false , length = 16)
    private String stuMajor;
    @Column(nullable = false , length = 32)
    private String stuStartYear;
    @Column(nullable = false , length = 32)
    private String stuEndYear;
    @Column(nullable = false )
    private int redParty;
    @Column(length = 64)
    private String currentEmail;
    @Column(length = 32)
    private String currentPhone;
    @Column(nullable = false)
    private int stuPower;

    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuClass() {
        return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }

    public String getStuMajor() {
        return stuMajor;
    }

    public void setStuMajor(String stuMajor) {
        this.stuMajor = stuMajor;
    }

    public String getStuStartYear() {
        return stuStartYear;
    }

    public void setStuStartYear(String stuStartYear) {
        this.stuStartYear = stuStartYear;
    }

    public String getStuEndYear() {
        return stuEndYear;
    }

    public void setStuEndYear(String stuEndYear) {
        this.stuEndYear = stuEndYear;
    }

    public int getRedParty() {
        return redParty;
    }

    public void setRedParty(int redParty) {
        this.redParty = redParty;
    }

    public String getCurrentEmail() {
        return currentEmail;
    }

    public void setCurrentEmail(String currentEmail) {
        this.currentEmail = currentEmail;
    }

    public String getCurrentPhone() {
        return currentPhone;
    }

    public void setCurrentPhone(String currentPhone) {
        this.currentPhone = currentPhone;
    }

    public int getStuPower() {
        return stuPower;
    }

    public void setStuPower(int stuPower) {
        this.stuPower = stuPower;
    }
}
