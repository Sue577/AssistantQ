package com.wsq.AssistantQ.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author WSQ
 * @date 2019/3/27 12:37
 * 招聘信息表
 * 招聘信息标题	recrTitle
 * 相关课程	recrCourse
 * 招聘信息描述	recrDesc
 * 提交者姓名 recrSubmitterName
 * 提交者ID	recrSubmitterId
 * 截止时间 	recrDeadLine
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_recruit")
@Data
public class RecruitModel extends BaseEntity {
    @Column(nullable = true, length = 32)
    private String recrTitle;
    @Column(nullable = true, length = 32)
    private String recrCourse;
    @Column(nullable = true, length = 1024)
    private String recrDesc;
    @Column(nullable = true, length = 32)
    private String recrSubmitterName;
    @Column(nullable = true, length = 32)
    private String recrSubmitterId;
    @Column(nullable = true, length = 32)
    private String recrDeadLine;
}
