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

    findMy: [], //我的招聘信息列表

    detailModal: false, //详情对话框
    addModal: false, //新增对话框

    //新增对话框内容
    recrTitleAdd: '', //招聘标题
    recrCourseAdd: '', //相关课程
    recrDescAdd: '', //招聘描述
    recrSubmitterNameAdd: '', //提交者姓名
    recrDeadLineAdd: '请选择截止时间', //截止时间

    //详情对话框内容
    objectIdDetail: '', //招聘编号
    recrTitleDetail: '', //招聘标题
    recrCourseDetail: '', //相关课程
    recrDescDetail: '', //招聘描述
    recrSubmitterNameDetail: '', //提交者姓名
    recrDeadLineDetail: '', //截止时间

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
    //教师查看自己招聘信息 返回自己的招聘列表
    this.findMy()
  },
  // 获取输入recrTitleAdd
  recrTitleAddInput: function (e) {
    this.setData({
      recrTitleAdd: e.detail.detail.value
    })
  },
  // 获取输入recrCourseAdd
  recrCourseAddInput: function (e) {
    this.setData({
      recrCourseAdd: e.detail.detail.value
    })
  },
  // 获取输入recrDescAdd
  recrDescAddInput: function (e) {
    this.setData({
      recrDescAdd: e.detail.detail.value
    })
  },
  // 获取输入recrSubmitterNameAdd
  recrSubmitterNameAddInput: function (e) {
    this.setData({
      recrSubmitterNameAdd: e.detail.detail.value
    })
  },
  // 获取输入recrDeadLineAdd
  recrDeadLineAddInput: function (e) {
    this.setData({
      recrDeadLineAdd: e.detail.value
    })
  },
  // 获取输入recrDescDetail
  recrDescDetailInput: function (e) {
    this.setData({
      recrDescDetail: e.detail.detail.value
    })
  },
  // 获取输入recrDeadLineDetail
  recrDeadLineDetailInput: function (e) {
    this.setData({
      recrDeadLineDetail: e.detail.value
    })
  },

//新增对话框
  handleOpenAdd() {
    this.setData({
      addModal: true
    });
  },
  handleOkAdd() {
    //教师新增招聘 返回新增后的自己的招聘列表
    this.addMy()
    //教师查看自己招聘信息 返回自己的招聘列表
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
      objectIdDetail: e.currentTarget.dataset.id //招聘编号
    });
    this.setData({
      recrTitleDetail: e.currentTarget.dataset.title, //招聘标题
    });
    this.setData({
      recrCourseDetail: e.currentTarget.dataset.course, //相关课程
    });
    this.setData({
      recrDescDetail: e.currentTarget.dataset.desc, //招聘描述
    });
    this.setData({
      recrSubmitterNameDetail: e.currentTarget.dataset.submitter, //提交者姓名
    });
    this.setData({
      recrDeadLineDetail: e.currentTarget.dataset.ddl, //截止时间
    });  
            
  },
  handleDetailClick({
    detail
  }) {
    const index = detail.index;

    if (index === 0) { //点击删除
      //教师根据招聘编号删除招聘 返回删除后的自己的招聘列表
      this.deleteMyByObjectId()
    } else if (index === 1) { //点击修改
      //教师根据招聘编号动态更改招聘信息 返回更改后的自己的招聘列表
      this.modifyMy()
    }
    this.setData({
      detailModal: false
    });

    //教师查看自己课程信息 返回自己的课程列表
    this.findMy()
  },
  //教师查看自己招聘信息 返回自己的招聘列表
  findMy: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/recruit/teacher/findMy',
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
              content: '您还没有创建招聘信息，赶快新增一下吧~',
              type: 'warning'
            });
          } else {
            let findMy = []
            for (let i = 0; i < result.length; i++) {
              findMy.push({
                id: "招聘" + (i + 1),
                objectId: result[i].objectId, //编号
                recrTitle: result[i].recrTitle, //招聘标题
                recrCourse: result[i].recrCourse, //相关课程
                recrDesc: result[i].recrDesc, //招聘描述
                recrSubmitterName: result[i].recrSubmitterName, //提交者姓名
                recrSubmitterId: result[i].recrSubmitterId, //提交者ID
                recrDeadLine: result[i].recrDeadLine, //截止时间
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
  //教师根据招聘编号删除招聘 返回删除后的自己的招聘列表
  deleteMyByObjectId: function () {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/recruit/teacher/deleteMyByObjectId',
      method: 'POST',
      data: {
        objectId: this.data.objectIdDetail, //招聘编号
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
      fail: function (err) {
        console.log(err)
        console.log('submit fail');
        $Toast({
          content: '请求失败~',
          type: 'error'
        });
      },
      complete: function (res) {
        console.log('submit complete');
      }

    })
  },

  //教师根据招聘编号动态更改招聘信息 返回更改后的自己的招聘列表
  modifyMy: function () {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/recruit/teacher/modifyMy',
      method: 'POST',
      data: {
        objectId: this.data.objectIdDetail, //招聘编号
        recrDesc: this.data.recrDescDetail, //招聘描述
        recrDeadLine: this.data.recrDeadLineDetail, //截止时间
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
      fail: function (err) {
        console.log(err)
        console.log('submit fail');
        $Toast({
          content: '请求失败~',
          type: 'error'
        });
      },
      complete: function (res) {
        console.log('submit complete');
      }

    })
  },
  //教师新增招聘 返回新增后的自己的招聘列表
  addMy: function () {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/recruit/teacher/addMy',
      method: 'POST',
      data: {
        recrTitle: this.data.recrTitleAdd, //招聘标题
        recrCourse: this.data.recrCourseAdd, //相关课程
        recrDesc: this.data.recrDescAdd, //招聘描述
        recrSubmitterName: this.data.recrSubmitterNameAdd, //提交者姓名
        recrDeadLine: this.data.recrDeadLineAdd, //截止时间
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
      fail: function (err) {
        console.log(err)
        console.log('submit fail');
        $Toast({
          content: '请求失败~',
          type: 'error'
        });
      },
      complete: function (res) {
        console.log('submit complete');
      }

    })
  }
})