package com.wsq.AssistantQ.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author WSQ
 * @date 2019/3/27 12:37
 * 教师信息表
 * 教师ID	teachId
 * 教师姓名	teachName
 * 教师职称	teachLevel
 * 所属分院	teachBranch
 * 所属学院	teachSchool
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_teacher")
@Data
public class TeacherModel extends BaseEntity {
    @Column(nullable = true, length = 32)
    private String teachId;
    @Column(nullable = true, length = 32)
    private String teachName;
    @Column(nullable = true, length = 32)
    private String teachLevel;
    @Column(nullable = true, length = 32)
    private String teachBranch;
    @Column(nullable = true, length = 32)
    private String teachSchool;
}
