package com.wsq.AssistantQ.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author WSQ
 * @date 2019/3/27 12:37
 * 通知信息表
 * 通知发送人ID	msgSenderId
 * 通知接收人ID	msgReceiverId
 * 通知内容	msgDesc
 * 通知状态 msgStatus
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_message")
@Data
public class MessageModel extends BaseEntity {
    @Column(nullable = true, length = 32)
    private String msgSenderId;
    @Column(nullable = true, length = 32)
    private String msgReceiverId;
    @Column(nullable = true, length = 255)
    private String msgDesc;
    @Column(nullable = true, length = 32)
    private String msgStatus;
}
