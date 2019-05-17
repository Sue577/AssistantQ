package com.wsq.AssistantQ.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author WSQ
 * @date 2019/3/27 12:37
 * 报名信息表
 * 所属招聘编号	applRecruitId
 * 报名课程	applCourse
 * 报名简历	applDesc
 * 提交者姓名	applSubmitterName
 * 提交者ID	applSubmitterId
 * 所属班级 applClass
 * 手机号码 applPhone
 * 审核者ID	applAuditorId
 * 审核者姓名	applAuditorName
 * 报名审核状态	applStatus
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_application")
@Data
public class ApplicationModel extends BaseEntity{
    @Column(nullable = true, length = 255)
    private String applRecruitId;
    @Column(nullable = true, length = 32)
    private String applCourse;
    @Column(nullable = true, length = 255)
    private String applDesc;
    @Column(nullable = true, length = 32)
    private String applSubmitterName;
    @Column(nullable = true, length = 32)
    private String applSubmitterId;
    @Column(nullable = true, length = 32)
    private String applAuditorId;
    @Column(nullable = true, length = 32)
    private String applStatus;
    @Column(nullable = true, length = 32)
    private String applClass;
    @Column(nullable = true, length = 32)
    private String applPhone;
    @Column(nullable = true, length = 32)
    private String applAuditorName;
}
