const { $Message } = require('../../dist/base/index');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    copyright: getApp().globalData.copyright,//底部版权
    link: getApp().globalData.link,//底部链接

    addApplicationButton: true,//是否显示报名按钮
    addApplicationModal: false,//是否显示报名对话框
    addApplicationSuccess:false,//是否显示我的报名信息

    applSubmitterName: '',//报名提交者姓名
    applDesc: ''//报名简历

  },
  // 获取输入applSubmitterName
  applSubmitterNameInput: function (e) {
    this.setData({
      applSubmitterName: e.detail.detail.value
    })
  },
  // 获取输入applDesc
  applDescInput: function (e) {
    this.setData({
      applDesc: e.detail.detail.value
    })
  },
  handleOpenApplication() {
    this.setData({
      addApplicationModal: true
    });
  },
  handleOkApplication() {
    this.setData({
      addApplicationModal: false
    });
  },
  handleCancelApplication() {
    this.setData({
      addApplicationModal: false
    });
  },
})