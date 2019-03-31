package com.wsq.AssistantQ.enums;

/**
 * @author WSQ
 * @date 2018-02-26 21:06
 */

public enum ResultEnum {
    ERROR_UNKONW(-1, "未知错误"),
    SUCCESS(200, "成功"),
    ERROR_100(100, "没有权限"),
    ERROR_101(101, "没有数据"),
    ERROR_102(102, "登录失败"),
    ERROR_103(103, "文件为空"),
    ERROR_104(104, "文件读取失败"),
    ERROR_105(105,"密码错误"),
    ERROR_106(106, "验证失败"),
    ERROR_107(107, "用户已存在"),
    ERROR_108(108, "无该学生用户"),
    ERROR_109(109, "该验证无效"),
    ERROR_110(110, "非该用户"),
    ERROR_111(111, "用户不存在"),
    ERROR_112(112, "学生已存在"),
    ERROR_113(113, "教师已存在"),
    ERROR_114(114, "课程名称已存在"),;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
