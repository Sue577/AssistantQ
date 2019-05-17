package com.wsq.AssistantQ.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author WSQ
 * @date 2019/3/27 12:37
 * 助教信息表
 * 助教学生ID	assiStudentId
 * 助教学生姓名	assiStudentName
 * 所属班级 assiClass
 * 手机号码 assiPhone
 * 助教课程	assiCourse
 * 助教教师ID assiTeacherId
 * 助教教师姓名	assiTeacherName
 * 工作职责	assiWork
 * 助教工作时长 assiHours
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_assistant")
@Data
public class AssistantModel extends BaseEntity {
    @Column(nullable = true, length = 32)
    private String assiStudentId;
    @Column(nullable = true, length = 32)
    private String assiStudentName;
    @Column(nullable = true, length = 32)
    private String assiCourse;
    @Column(nullable = true, length = 32)
    private String assiTeacherId;
    @Column(nullable = true, length = 255)
    private String assiWork;
    @Column(nullable = true, length = 32)
    private String assiClass;
    @Column(nullable = true, length = 32)
    private String assiPhone;
    @Column(nullable = true, length = 32)
    private String assiTeacherName;
    @Column(nullable = true, length = 32)
    private String assiHours;

}
