package com.wsq.AssistantQ.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author WSQ
 * @date 2019/3/27 12:37
 * 用户信息表
 * 用户ID	userId
 * 用户密码	userPwd
 * 用户新密码	userNewPwd
 * 用户头像	userImage
 * 用户姓名	userName
 * 用户邮箱	userEmail
 * 用户手机号	userPhone
 * 用户类型	userType
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_user")
@Data
public class UserModel extends BaseEntity {
    @Column(nullable = false, length = 32)
    private String userId;
    @Column(nullable = false, length = 32)
    private String userPwd;
    @Column(nullable = false, length = 32)
    private String userNewPwd;
    @Column(nullable = true, length = 255)
    private String userImage;
    @Column(nullable = false, length = 32)
    private String userName;
    @Column(nullable = true, length = 32)
    private String userEmail;
    @Column(nullable = true, length = 32)
    private String userPhone;
    @Column(nullable = false, length = 32)
    private String userType;
}
