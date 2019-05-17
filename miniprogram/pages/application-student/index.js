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

    detailModalBefore: false, //待审核的详情对话框
    detailModalAfter: false, //已审核的详情对话框

    //待审核详情对话框内容
    objectIdBefore: '', //报名编号
    createTimeBefore: '', //报名创建时间
    applCourseBefore: '', //报名课程
    applDescBefore: '', //报名简历
    applSubmitterNameBefore: '', //报名提交者姓名
    applSubmitterIdBefore: '', //报名提交者ID
    applStatusBefore: '', //报名审核状态
    applAuditorIdBefore: '',//审核者ID
    applAuditorNameBefore: '',//审核者姓名
    applClassBefore: '',//所属班级
    applPhoneBefore: '',//手机号码

    //已审核详情对话框内容
    objectIdAfter: '', //报名编号
    createTimeAfter: '', //报名创建时间
    applCourseAfter: '', //报名课程
    applDescAfter: '', //报名简历
    applSubmitterNameAfter: '', //报名提交者姓名
    applSubmitterIdAfter: '', //报名提交者ID
    applStatusAfter: '', //报名审核状态

    current: 'before', //当前tab
    before: true, //待审核
    after: false, //已审核

    countBefore:'',//待审核数量

    findMyBefore: [],//待审核信息列表
    findMyAfter: [],//通过信息列表
    findMyAfter2: [],//不通过信息列表

    //待审核的详情对话框的操作
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

    //待审核的详情对话框的操作
    actionsDetailAfter: [{
      name: '确定',
      color: '#2d8cf0'
    }, ],

  },

  /**
     * 生命周期函数--监听页面加载
     */
  onLoad: function (options) {
    //学生根据审核状态查看自己的报名信息 返回自己的审核状态报名列表
    this.findMyBefore()
  },

  //切换tab
  handleChange({
    detail
  }) {
    this.setData({
      current: detail.key
    });
    if (this.data.current == "before") {
      //学生根据审核状态查看自己的报名信息 返回自己的审核状态报名列表 待审核
      this.findMyBefore()

      this.setData({
        before: true,
        after: false,
      });
    } else if (this.data.current == "after") {
      //学生根据审核状态查看自己的报名信息 返回自己的审核状态报名列表 通过
      this.findMyAfter()

      //学生根据审核状态查看自己的报名信息 返回自己的审核状态报名列表 不通过
      this.findMyAfter2()

      this.setData({
        before: false,
        after: true,
      });
    }
  },

  // applDescBefore
  applDescBeforeInput: function (e) {
    this.setData({
      applDescBefore: e.detail.detail.value
    })
  },
  //打开待审核的详情对话框
  handleOpenDetailBefore(e) {
    this.setData({
      detailModalBefore: true
    });

    //获取传递的参数
    this.setData({
      objectIdBefore: e.currentTarget.dataset.id, //编号
      applCourseBefore: e.currentTarget.dataset.course, //报名课程
      applDescBefore: e.currentTarget.dataset.desc, //报名简历
      applSubmitterNameBefore: e.currentTarget.dataset.submittername, //报名提交者姓名
      applSubmitterIdBefore: e.currentTarget.dataset.submitterid, //报名提交者ID
      applStatusBefore: e.currentTarget.dataset.status, //报名审核状态
      applAuditorIdBefore: e.currentTarget.dataset.auditorid,//审核者ID
      applAuditorNameBefore: e.currentTarget.dataset.auditorname,//审核者姓名
      applClassBefore: e.currentTarget.dataset.class1,//所属班级
      applPhoneBefore: e.currentTarget.dataset.phone,//手机号码
    });
  },
  handleDetailClickBefore({
    detail
  }) {
    const index = detail.index;

    if (index === 0) { //点击删除
      //学生根据报名编号删除报名 返回删除后的自己的报名列表
      this.deleteMyByObjectId()
    } else if (index === 1) { //点击修改
      //学生根据报名编号动态更改报名信息 返回更改后的自己的报名列表
      this.modifyMy()
    }
    //学生根据审核状态查看自己的报名信息 返回自己的审核状态报名列表
    this.findMyBefore()

    this.setData({
      detailModalBefore: false
    });
  },

  //打开已审核的详情对话框
  handleOpenDetailAfter(e) {
    this.setData({
      detailModalAfter: true
    });

    //获取传递的参数
    this.setData({
      objectIdAfter: e.currentTarget.dataset.id, //编号
      applCourseAfter: e.currentTarget.dataset.course, //报名课程
      applDescAfter: e.currentTarget.dataset.desc, //报名简历
      applSubmitterNameAfter: e.currentTarget.dataset.submittername, //报名提交者姓名
      applSubmitterIdAfter: e.currentTarget.dataset.submitterid, //报名提交者ID
      applStatusAfter: e.currentTarget.dataset.status, //报名审核状态
    });
  },
  handleDetailClickAfter({
    detail
  }) {
    this.setData({
      detailModalAfter: false
    });
  },

  //学生根据审核状态查看自己的报名信息 返回自己的审核状态报名列表 待审核
  findMyBefore: function () {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/application/student/findMyByStatus',
      method: 'POST',
      data: {
        applStatus: '待审核',
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
          let result = res.data.data
          if (result.length == 0) {
            $Toast({
              content: '您没有待审核的内容~',
              type: 'warning'
            });
          } else {
            let findMyBefore = []
            for (let i = 0; i < result.length; i++) {
              findMyBefore.push({
                id: "报名" + (i + 1),
                objectId: result[i].objectId, //编号
                applCourse: result[i].applCourse, //报名课程
                applDesc: result[i].applDesc, //报名简历
                applSubmitterName: result[i].applSubmitterName, //报名提交者姓名
                applSubmitterId: result[i].applSubmitterId, //报名提交者ID
                applStatus: result[i].applStatus, //报名审核状态
                applAuditorId: result[i].applAuditorId,//审核者ID
                applAuditorName: result[i].applAuditorName,//审核者姓名
                applClass: result[i].applClass,//所属班级
                applPhone: result[i].applPhone,//手机号码
                createTime: util.formatDate(result[i].createTime) //时间戳转换为时间
              })
            }
            this.setData({
              findMyBefore: findMyBefore,
              countBefore: result.length
            })

          }
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
  //学生根据审核状态查看自己的报名信息 返回自己的审核状态报名列表 通过
  findMyAfter: function () {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/application/student/findMyByStatus',
      method: 'POST',
      data: {
        applStatus: '通过',
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
          let result = res.data.data
          if (result.length == 0) {
            $Toast({
              content: '您没有通过的内容~',
              type: 'warning'
            });
          } else {
            let findMyAfter = []
            for (let i = 0; i < result.length; i++) {
              findMyAfter.push({
                id: "报名" + (i + 1),
                objectId: result[i].objectId, //编号
                applCourse: result[i].applCourse, //报名课程
                applDesc: result[i].applDesc, //报名简历
                applSubmitterName: result[i].applSubmitterName, //报名提交者姓名
                applSubmitterId: result[i].applSubmitterId, //报名提交者ID
                applStatus: result[i].applStatus, //报名审核状态
                applAuditorId: result[i].applAuditorId,//审核者ID
                applAuditorName: result[i].applAuditorName,//审核者姓名
                applClass: result[i].applClass,//所属班级
                applPhone: result[i].applPhone,//手机号码
                createTime: util.formatDate(result[i].createTime) //时间戳转换为时间
              })
            }
            this.setData({
              findMyAfter: findMyAfter
            })

          }
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
  //学生根据审核状态查看自己的报名信息 返回自己的审核状态报名列表 不通过
  findMyAfter2: function () {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/application/student/findMyByStatus',
      method: 'POST',
      data: {
        applStatus: '不通过',
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
          let result = res.data.data
          if (result.length == 0) {
            $Toast({
              content: '您没有未通过的内容~',
              type: 'warning'
            });
          } else {
            let findMyAfter2 = []
            for (let i = 0; i < result.length; i++) {
              findMyAfter2.push({
                id: "报名" + (i + 1),
                objectId: result[i].objectId, //编号
                applCourse: result[i].applCourse, //报名课程
                applDesc: result[i].applDesc, //报名简历
                applSubmitterName: result[i].applSubmitterName, //报名提交者姓名
                applSubmitterId: result[i].applSubmitterId, //报名提交者ID
                applStatus: result[i].applStatus, //报名审核状态
                applAuditorId: result[i].applAuditorId,//审核者ID
                applAuditorName: result[i].applAuditorName,//审核者姓名
                applClass: result[i].applClass,//所属班级
                applPhone: result[i].applPhone,//手机号码
                createTime: util.formatDate(result[i].createTime) //时间戳转换为时间
              })
            }
            this.setData({
              findMyAfter2: findMyAfter2
            })

          }
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
  //学生根据报名编号删除报名 返回删除后的自己的报名列表
  deleteMyByObjectId: function () {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/application/student/deleteMyByObjectId',
      method: 'POST',
      data: {
        objectId: this.data.objectIdBefore, //编号
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
  //学生根据报名编号动态更改报名信息 返回更改后的自己的报名列表
  modifyMy: function () {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/application/student/modifyMy',
      method: 'POST',
      data: {
        objectId: this.data.objectIdBefore, //编号
        applDesc: this.data.applDescBefore //报名简历
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
  }
})