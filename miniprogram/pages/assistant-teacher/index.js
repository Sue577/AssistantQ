// pages/assistant/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    copyright: getApp().globalData.copyright,//底部版权
    link: getApp().globalData.link,//底部链接

    objectId:'',//助教编号
    assiStudentId:'',//助教ID
    assiCourse:'',//助教课程
    assiTeacherId:'',//助教教师ID
    assiName:'吴苏琪',//助教姓名
    assiWork:'',//助教工作职责

    detailModal: false,
    //详情对话框的操作
    actionsDetail: [
      // {
      //   name: '删除',
      //   color: '#ed3f14',
      // },
      {
        name: '修改',
        color: '#2d8cf0'
      },
      {
        name: '取消'
      }
    ],
  },
  //详情对话框
  handleOpenDetail() {
    this.setData({
      detailModal: true
    });
  },
  handleDetailClick({ detail }) {
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
      detailModal: false
    });
  },

})