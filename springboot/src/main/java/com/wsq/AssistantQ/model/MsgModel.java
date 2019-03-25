package com.wsq.AssistantQ.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author CYann
 * @date 2018-04-06 12:11
 * 反馈留言
 * -接收用户编号 recUser(String varchar 外键)
 * -发送用户编号 sendUser(String varchar 外键)
 * -消息类型 msgType(String varchar)
 * -消息内容 msgContent(String varchar)
 * -是否被查看过 msgStats(String varchar )
 */
@Entity
@Table(name = "tb_msg")
public class MsgModel extends BaseEntity {
    @Column(length = 64)
    private String recUser;
    @Column(length = 64)
    private String sendUser;
    @Column(nullable = false,length = 21)
    private String msgType;
    @Column(nullable = false,length = 256)
    private String msgContent;
    @Column(nullable = false,length = 21)
    private String msgStats;

    public String getMsgStats() {
        return msgStats;
    }

    public void setMsgStats(String msgStats) {
        this.msgStats = msgStats;
    }

    public String getRecUser() {
        return recUser;
    }

    public void setRecUser(String recUser) {
        this.recUser = recUser;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}
