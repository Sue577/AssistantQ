package com.wsq.AssistantQ.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author WSQ
 * @date 2019/3/27 12:37
 * 学生信息表
 * 学生ID	stuId
 * 学生姓名	stuName
 * 所属班级	stuClass
 * 所属分院	stuBranch
 * 所属学院	stuSchool
 * 是否为助教	stuIsAssistant
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_student")
@Data
public class StudentModel  extends BaseEntity{
    @Column(nullable = true, length = 32)
    private String stuId;
    @Column(nullable = true, length = 32)
    private String stuName;
    @Column(nullable = true, length = 32)
    private String stuClass;
    @Column(nullable = true, length = 32)
    private String stuBranch;
    @Column(nullable = true, length = 32)
    private String stuSchool;
    @Column(nullable = true, length = 32)
    private String stuIsAssistant;
}
