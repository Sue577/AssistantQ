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
 * 审核者ID	applAuditorId
 * 报名审核状态	applStatus
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_application")
@Data
public class ApplicationModel extends BaseEntity{
    @Column(nullable = false, length = 255)
    private String applRecruitId;
    @Column(nullable = false, length = 32)
    private String applCourse;
    @Column(nullable = false, length = 255)
    private String applDesc;
    @Column(nullable = false, length = 32)
    private String applSubmitterName;
    @Column(nullable = false, length = 32)
    private String applSubmitterId;
    @Column(nullable = false, length = 32)
    private String applAuditorId;
    @Column(nullable = false, length = 32)
    private String applStatus;
}
