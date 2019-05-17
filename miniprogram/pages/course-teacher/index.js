const {
  $Toast
} = require('../../dist/base/index');
const {
  $Message
} = require('../../dist/base/index');
var util = require("../../utils/util.js");

Page({

  /**
   * 页面的初始数据
   */
  data: {
    copyright: getApp().globalData.copyright, //底部版权
    link: getApp().globalData.link, //底部链接

    findMy: [], //我的课程信息列表

    detailModal: false, //详情对话框
    addModal: false, //新增对话框

    //新增对话框内容
    courNameAdd: '', //课程名称
    courDescAdd: '', //课程描述
    courTeacherNameAdd: '', //课程教师姓名

    //详情对话框内容
    objectIdDetail: '', //课程编号
    courNameDetail: '', //课程名称
    courDescDetail: '', //课程描述
    courTeacherNameDetail: '', //课程教师姓名

    //详情对话框的操作
    actionsDetail: [{
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
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    //教师查看自己课程信息 返回自己的课程列表
    this.findMy()
  },
  // 获取输入courDescDetail
  courDescDetailInput: function(e) {
    this.setData({
      courDescDetail: e.detail.detail.value
    })
  },
  // 获取输入courNameAdd
  courNameAddInput: function(e) {
    this.setData({
      courNameAdd: e.detail.detail.value
    })
  },
  // 获取输入courTeacherNameAdd
  courTeacherNameAddInput: function(e) {
    this.setData({
      courTeacherNameAdd: e.detail.detail.value
    })
  },
  // 获取输入courDescAdd
  courDescAddInput: function(e) {
    this.setData({
      courDescAdd: e.detail.detail.value
    })
  },
  //新增对话框
  handleOpenAdd() {
    this.setData({
      addModal: true
    });
  },
  handleOkAdd() {
    //教师新增课程 返回新增后的自己的课程列表
    this.addMy()
    //教师查看自己课程信息 返回自己的课程列表
    this.findMy()
    
    this.setData({
      addModal: false
    });
    
  },
  handleCancelAdd() {
    this.setData({
      addModal: false
    });
  },
  //详情对话框
  handleOpenDetail(e) {
    //显示详情对话框
    this.setData({
      detailModal: true
    });

    //获取传递的参数
    this.setData({
      objectIdDetail: e.currentTarget.dataset.id, //课程编号
      courNameDetail: e.currentTarget.dataset.name, //课程名称
      courDescDetail: e.currentTarget.dataset.desc, //课程描述
      courTeacherNameDetail: e.currentTarget.dataset.teacher, //课程教师姓名
    });
  },
  //详情对话框的操作
  handleDetailClick({
    detail
  }) {
    const index = detail.index;

    if (index === 0) { //点击删除
      //教师根据课程编号删除课程 返回删除后的自己的课程列表
      this.deleteMyByObjectId()
    } else if (index === 1) { //点击修改
      //教师根据课程编号动态更改课程信息 返回更改后的自己的课程列表
      this.modifyMy()
    }
    this.setData({
      detailModal: false
    });

    //教师查看自己课程信息 返回自己的课程列表
    this.findMy()
  },
  //教师查看自己课程信息 返回自己的课程列表
  findMy: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/course/teacher/findMy',
      method: 'GET',
      header: {
        'content-type': 'application/json',
        'Cookie': SessionId
      },
      success: res => {
        console.log(res)
        console.log('submit success');
        if (res.data.msg == "没有权限" | res.data.code == -1) {
          $Toast({
            content: '登录过期，请重新登录嗷！',
            type: 'error'
          });
          setTimeout(() => {
            //要延时执行的代码
            $Toast.hide();
            wx.redirectTo({ //当前页面切换成登录界面
              url: '../login/index',
            })
          }, 1000);
        } else {
          let result = res.data.data
          if (result.length == 0) {
            $Toast({
              content: '您还没有创建课程信息，赶快新增一下吧~',
              type: 'warning'
            });
          } else {
            let findMy = []
            for (let i = 0; i < result.length; i++) {
              findMy.push({
                id: "课程" + (i + 1),
                objectId: result[i].objectId, //课程编号
                courName: result[i].courName, //课程名称
                courDesc: result[i].courDesc, //课程描述
                courTeacherName: result[i].courTeacherName, //课程教师姓名
                courSubmitterId: result[i].courSubmitterId, //提交者ID
                createTime: util.formatDate(result[i].createTime) //时间戳转换为时间
              })
            }
            this.setData({
              findMy: findMy,
            })

          }
        }

      },
      fail: function(err) {
        console.log(err)
        console.log('submit fail');
        $Toast({
          content: '请求失败~',
          type: 'error'
        });
      },
      complete: function(res) {
        console.log('submit complete');
      }

    })
  },
  //教师根据课程编号删除课程 返回删除后的自己的课程列表
  deleteMyByObjectId: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/course/teacher/deleteMyByObjectId',
      method: 'POST',
      data: {
        objectId: this.data.objectIdDetail, //课程编号
      },
      header: {
        'content-type': 'application/json',
        'Cookie': SessionId
      },
      success: res => {
        console.log(res)
        console.log('submit success');
        if (res.data.msg == "没有权限" | res.data.code == -1) {
          $Toast({
            content: '登录过期，请重新登录嗷！',
            type: 'error'
          });
          setTimeout(() => {
            //要延时执行的代码
            $Toast.hide();
            wx.redirectTo({ //当前页面切换成登录界面
              url: '../login/index',
            })
          }, 1000);
        } else {
          $Message({
            content: '删除成功！'
          });

        }

      },
      fail: function(err) {
        console.log(err)
        console.log('submit fail');
        $Toast({
          content: '请求失败~',
          type: 'error'
        });
      },
      complete: function(res) {
        console.log('submit complete');
      }

    })
  },

  //教师根据课程编号动态更改课程信息 返回更改后的自己的课程列表
  modifyMy: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/course/teacher/modifyMy',
      method: 'POST',
      data: {
        objectId: this.data.objectIdDetail, //课程编号
        courDesc: this.data.courDescDetail //课程描述
      },
      header: {
        'content-type': 'application/json',
        'Cookie': SessionId
      },
      success: res => {
        console.log(res)
        console.log('submit success');
        if (res.data.msg == "没有权限" | res.data.code == -1) {
          $Toast({
            content: '登录过期，请重新登录嗷！',
            type: 'error'
          });
          setTimeout(() => {
            //要延时执行的代码
            $Toast.hide();
            wx.redirectTo({ //当前页面切换成登录界面
              url: '../login/index',
            })
          }, 1000);
        } else {
          $Message({
            content: '修改成功！'
          });

        }

      },
      fail: function(err) {
        console.log(err)
        console.log('submit fail');
        $Toast({
          content: '请求失败~',
          type: 'error'
        });
      },
      complete: function(res) {
        console.log('submit complete');
      }

    })
  },

  //教师新增课程 返回新增后的自己的课程列表
  addMy: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/course/teacher/addMy',
      method: 'POST',
      data: {
        courName: this.data.courNameAdd, //课程名称
        courDesc: this.data.courDescAdd, //课程描述
        courTeacherName: this.data.courTeacherNameAdd, //课程教师姓名
      },
      header: {
        'content-type': 'application/json',
        'Cookie': SessionId
      },
      success: res => {
        console.log(res)
        console.log('submit success');
        if (res.data.msg == "没有权限" | res.data.code == -1) {
          $Toast({
            content: '登录过期，请重新登录嗷！',
            type: 'error'
          });
          setTimeout(() => {
            //要延时执行的代码
            $Toast.hide();
            wx.redirectTo({ //当前页面切换成登录界面
              url: '../login/index',
            })
          }, 1000);
        } else {
          $Message({
            content: '新增成功！'
          });

        }

      },
      fail: function(err) {
        console.log(err)
        console.log('submit fail');
        $Toast({
          content: '请求失败~',
          type: 'error'
        });
      },
      complete: function(res) {
        console.log('submit complete');
      }

    })
  }
})