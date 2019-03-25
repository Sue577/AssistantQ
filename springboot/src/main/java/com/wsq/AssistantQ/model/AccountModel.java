package com.wsq.AssistantQ.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author CYann
 * @date 2018-02-26 0:31
 *  户口
 * -所属用户编号 stuNumber(String varchar) 外键
 * -户口所在地 accountAddress(String varchar)
 * -户口更换时间 accountDate(String varchar)
 */
@Entity
@DynamicUpdate(true)
@Table(name ="tb_account")
public class AccountModel extends BaseEntity{
    @Column(nullable = false , length = 32)
    private String stuNumber;
    @Column(nullable = false , length = 256)
    private String accountAddress;
    @Column(nullable = false , length = 32)
    private String accountDate;

    public String getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(String accountDate) {
        this.accountDate = accountDate;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }
}
