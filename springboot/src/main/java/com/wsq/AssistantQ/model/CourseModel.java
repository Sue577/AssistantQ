package com.wsq.AssistantQ.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author WSQ
 * @date 2019/3/28 0:05
 * 课程信息表
 * 课程名称	courName
 * 课程描述	courDesc
 * 课程教师姓名	courTeacherName
 * 提交者ID	courSubmitterId
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_course")
@Data
public class CourseModel extends BaseEntity {
    @Column(nullable = false, length = 32)
    private String courName;
    @Column(nullable = false, length = 255)
    private String courDesc;
    @Column(nullable = false, length = 32)
    private String courTeacherName;
    @Column(nullable = false, length = 32)
    private String courSubmitterId;
}
