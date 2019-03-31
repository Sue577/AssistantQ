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
 * 助教姓名	assiName
 * 助教课程	assiCourse
 * 助教教师ID assiTeacherId
 * 工作职责	assiWork
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_assistant")
@Data
public class AssistantModel extends BaseEntity {
    @Column(nullable = false, length = 32)
    private String assiStudentId;
    @Column(nullable = false, length = 32)
    private String assiName;
    @Column(nullable = false, length = 32)
    private String assiCourse;
    @Column(nullable = false, length = 32)
    private String assiTeacherId;
    @Column(nullable = false, length = 255)
    private String assiWork;


}
