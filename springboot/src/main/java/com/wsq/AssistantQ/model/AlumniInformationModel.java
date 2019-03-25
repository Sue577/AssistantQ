package com.wsq.AssistantQ.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author CYann
 * @date 2018-02-26 0:32
 *  校友信息
 * -所属用户编号 stuNumber(String varchar) 外键
 * -所在公司 company(String varchar)
 * -公司地址 companyAddress(Stirng varchar)
 * -行业 industry(String varchar)
 * -职位 occupation(String varchar)
 * -薪水 salary(String varchar)
 */
@Entity
@Table(name = "tb_alumniinformation")
public class AlumniInformationModel extends BaseEntity{
    @Column(nullable = false , length = 32)
    private String stuNumber;
    @Column(nullable = false , length = 256)
    private String company;
    @Column(nullable = false , length = 256)
    private String companyAddress;
    @Column(nullable = false , length = 32)
    private String industry;
    @Column(nullable = false , length = 32)
    private String occupation;
    @Column(nullable = false , length = 32)
    private String startDate;
    @Column(nullable = true , length = 32)
    private String endDate;
    @Column(nullable = true , length = 32)
    private String salary;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

}
