package com.wsq.AssistantQ.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author WSQ
 * @date 2019/3/27 12:37
 * 工作考核表
 * 相关课程	evalCourse
 * 考核学期	evalTerm
 * 考核工作描述	evalDesc
 * 提交者姓名	evalSubmitterName
 * 提交者ID	evalSubmitterId
 * 考核等级	evalLevel
 * 审核者ID	evalAuditorId
 * 考核审核状态	evalStatus
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_evaluation")
@Data
public class EvaluationModel extends BaseEntity {
    @Column(nullable = false, length = 32)
    private String evalCourse;
    @Column(nullable = false, length = 32)
    private String evalTerm;
    @Column(nullable = false, length = 255)
    private String evalDesc;
    @Column(nullable = false, length = 32)
    private String evalSubmitterName;
    @Column(nullable = false, length = 32)
    private String evalSubmitterId;
    @Column(nullable = false, length = 32)
    private String evalLevel;
    @Column(nullable = false, length = 32)
    private String evalAuditorId;
    @Column(nullable = false, length = 32)
    private String evalStatus;
}
