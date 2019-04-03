const { $Toast } = require('../../dist/base/index');
const { $Message } = require('../../dist/base/index');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    copyright: getApp().globalData.copyright,//底部版权
    link: getApp().globalData.link,//底部链接

    objectId: '',//报名编号
    createTime: '',//报名创建时间
    applCourse: '软件工程',//报名课程
    applDesc: '我很强反正就是很强',//报名简历
    applSubmitterName: '吴苏琪',//报名提交者姓名
    applSubmitterId: '31501284',//报名提交者ID
    applAuditorId: '',//报名审核者ID
    applStatus: '待审核',//报名审核状态
    applStatus2: '通过',//报名审核状态

    detailModalBefore: false,//未审核的详情对话框
    detailModalAfter: false,//已审核的详情对话框

    current: 'before', //当前tab
    before: true, //未审核
    after: false, //已审核

    //未审核的详情对话框的操作
    actionsDetailBefore: [
      // {
      //   name: '删除',
      //   color: '#ed3f14',
      // },
      {
        name: '审核',
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

    //审核状态选项
    applStatusItem: [{
      id: 1,
      name: '通过',
    }, {
      id: 2,
      name: '不通过'
    }],
    applStatus:''
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

  //打开未审核的详情对话框
  handleOpenDetailBefore() {
    this.setData({
      detailModalBefore: true
    });
  },
  handleApplStatusChange({ detail = {} }) {
    this.setData({
      applStatus: detail.value
    });
  },
  handleDetailClickBefore({ detail }) {
    const index = detail.index;

    if (index === 0) {
      // $Message({
      //   content: '点击了删除'
      // });
    } else if (index === 1) {
      // $Message({
      //   content: '点击了修改'
      // });
    }

    this.setData({
      detailModalBefore: false
    });
  },

  //打开已审核的详情对话框
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