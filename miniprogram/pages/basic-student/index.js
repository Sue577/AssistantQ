const { $Toast } = require('../../dist/base/index');
const { $Message } = require('../../dist/base/index');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    copyright: getApp().globalData.copyright,//底部版权
    link: getApp().globalData.link,//底部链接
    
    modifyEmailModal: false,//修改邮箱对话框
    modifyPhoneModal: false,//修改手机号对话框
    modifyPwdModal: false,//修改密码对话框

    userPwd:'',//用户原密码
    userNewPwd:'',//用户新密码
    userConfirmPwd:'',//用户确认密码
    userEmail:'',//用户邮箱
    userPhone	:'',//用户手机号

  },

  // 获取输入userPwd
  userPwdInput: function (e) {
    this.setData({
      userPwd: e.detail.detail.value
    })
  },
  // 获取输入userNewPwd
  userNewPwdInput: function (e) {
    this.setData({
      userNewPwd: e.detail.detail.value
    })
  },
  // 获取输入userConfirmPwd
  userConfirmPwdInput: function (e) {
    this.setData({
      userConfirmPwd: e.detail.detail.value
    })
  },
  // 获取输入userEmail
  userEmailInput: function (e) {
    this.setData({
      userEmail: e.detail.detail.value
    })
  },
  // 获取输入userPhone
  userPhoneInput: function (e) {
    this.setData({
      userPhone: e.detail.detail.value
    })
  },

  // 打开修改邮箱对话框
  handleOpenEmail() {
    this.setData({
      modifyEmailModal: true
    });
  },
  // 确认修改邮箱
  handleOkEmail() {
    this.setData({
      modifyEmailModal: false
    });
  },
  // 取消修改邮箱
  handleCancelEmail() {
    this.setData({
      modifyEmailModal: false
    });
  },

  // 打开修改手机号对话框
  handleOpenPhone() {
    this.setData({
      modifyPhoneModal: true
    });
  },
  // 确认修改手机号
  handleOkPhone() {
    this.setData({
      modifyPhoneModal: false
    });
  },
  // 取消修改手机号
  handleCancelPhone() {
    this.setData({
      modifyPhoneModal: false
    });
  },

  // 打开修改密码对话框
  handleOpenPwd() {
    this.setData({
      modifyPwdModal: true
    });
  },
  // 确认修改密码
  handleOkPwd() {
    this.setData({
      modifyPwdModal: false
    });
  },
  // 取消修改密码
  handleCancelPwd() {
    this.setData({
      modifyPwdModal: false
    });
  },
})