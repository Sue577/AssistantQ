package com.wsq.AssistantQ.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author WSQ
 * @date 2019/3/27 12:37
 * 工作记录表
 * 工作日期	recoDate
 * 工作时长	recoHours
 * 工作描述	recoDesc
 * 相关课程	recoCourse
 * 提交者姓名	recoSubmitterName
 * 提交者ID	recoSubmitterId
 * 审核者ID	recoAuditorId
 * 记录审核状态	recoStatus
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_record")
@Data
public class RecordModel extends BaseEntity {
    @Column(nullable = false, length = 32)
    private String recoDate;
    @Column(nullable = false, length = 32)
    private String recoHours;
    @Column(nullable = false, length = 255)
    private String recoDesc;
    @Column(nullable = false, length = 32)
    private String recoCourse;
    @Column(nullable = false, length = 32)
    private String recoSubmitterName;
    @Column(nullable = false, length = 32)
    private String recoSubmitterId;
    @Column(nullable = false, length = 32)
    private String recoAuditorId;
    @Column(nullable = false, length = 32)
    private String recoStatus;
}
