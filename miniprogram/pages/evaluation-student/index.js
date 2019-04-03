const { $Toast } = require('../../dist/base/index');
const { $Message } = require('../../dist/base/index');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    copyright: getApp().globalData.copyright,//底部版权
    link: getApp().globalData.link,//底部链接

    addModal: false,//新增对话框
    detailModalBefore: false,//未审核的详情对话框
    detailModalAfter: false,//已审核的详情对话框

    current: 'before', //当前tab
    before: true, //未审核
    after: false, //已审核

    //未审核的详情对话框的操作
    actionsDetailBefore: [
      {
        name: '删除',
        color: '#ed3f14',
      },
      {
        name: '修改',
        color: '#2d8cf0'
      },
      {
        name: '取消'
      }
    ],

    //未审核的详情对话框的操作
    actionsDetailAfter: [
      {
        name: '确定',
        color: '#2d8cf0'
      },
    ],

  },

  //切换tab
  handleChange({
    detail
  }) {
    this.setData({
      current: detail.key
    });
    if (this.data.current == "before") {
      this.setData({
        before: true,
        after: false,
      });
    } else if (this.data.current == "after") {
      this.setData({
        before: false,
        after: true,
      });
    }
  },

  //新增对话框
  handleOpenAdd() {
    this.setData({
      addModal: true
    });
  },
  handleOkAdd() {
    this.setData({
      addModal: false
    });
  },
  handleCancelAdd() {
    this.setData({
      addModal: false
    });
  },

  //未审核的详情对话框
  handleOpenDetailBefore() {
    this.setData({
      detailModalBefore: true
    });
  },
  handleDetailClickBefore({ detail }) {
    const index = detail.index;

    if (index === 0) {
      $Message({
        content: '点击了删除'
      });
    } else if (index === 1) {
      $Message({
        content: '点击了修改'
      });
    }

    this.setData({
      detailModalBefore: false
    });
  },

  //已审核的详情对话框
  handleOpenDetailAfter() {
    this.setData({
      detailModalAfter: true
    });
  },
  handleDetailClickAfter({ detail }) {
    this.setData({
      detailModalAfter: false
    });
  },
})