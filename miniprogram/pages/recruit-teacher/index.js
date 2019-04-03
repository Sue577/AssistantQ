const { $Toast } = require('../../dist/base/index');
const { $Message } = require('../../dist/base/index');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    copyright: getApp().globalData.copyright,//底部版权
    link: getApp().globalData.link,//底部链接

    detailModal: false,//详情对话框
    addModal: false,//新增对话框

    //详情对话框的操作
    actionsDetail: [
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

  },
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
  handleOpenDetail() {
    this.setData({
      detailModal: true
    });
  },
  handleDetailClick({ detail }) {
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
      detailModal: false
    });
  },
})