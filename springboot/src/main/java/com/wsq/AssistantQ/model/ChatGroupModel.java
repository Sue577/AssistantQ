package com.wsq.AssistantQ.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author CYann
 * @date 2018-04-01 17:17
 * 交流群信息
 * -专业 stuMajor
 * -毕业年份 stuEndYear
 * -QQ群 qqNo
 * -微信群 wechatNo
 */
@Entity
@Table(name = "tb_chatgroup")
public class ChatGroupModel extends BaseEntity {
    @Column(nullable = false , length = 32)
    private String stuMajor;
    @Column(nullable = false , length = 32)
    private String stuEndYear;
    @Column(nullable = false , length = 32)
    private String qqNo;
    @Column(length = 108)
    private String wechatNo;

    public String getStuMajor() {
        return stuMajor;
    }

    public void setStuMajor(String stuMajor) {
        this.stuMajor = stuMajor;
    }

    public String getStuEndYear() {
        return stuEndYear;
    }

    public void setStuEndYear(String stuEndYear) {
        this.stuEndYear = stuEndYear;
    }

    public String getQqNo() {
        return qqNo;
    }

    public void setQqNo(String qqNo) {
        this.qqNo = qqNo;
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }
}
