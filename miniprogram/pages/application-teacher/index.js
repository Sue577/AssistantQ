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

    detailModalBefore: false, //未审核的详情对话框
    detailModalAfter: false, //已审核的详情对话框

    //待审核详情对话框内容
    objectIdBefore: '', //报名编号
    createTimeBefore: '', //报名创建时间
    applCourseBefore: '', //报名课程
    applDescBefore: '', //报名简历
    applSubmitterNameBefore: '', //报名提交者姓名
    applSubmitterIdBefore: '', //报名提交者ID
    applStatusBefore: '', //报名审核状态
    applAuditorIdBefore: '', //审核者ID
    applAuditorNameBefore: '', //审核者姓名
    applClassBefore: '', //所属班级
    applPhoneBefore: '', //手机号码

    //已审核详情对话框内容
    objectIdAfter: '', //报名编号
    createTimeAfter: '', //报名创建时间
    applCourseAfter: '', //报名课程
    applDescAfter: '', //报名简历
    applSubmitterNameAfter: '', //报名提交者姓名
    applSubmitterIdAfter: '', //报名提交者ID
    applStatusAfter: '', //报名审核状态

    current: 'before', //当前tab
    before: true, //未审核
    after: false, //已审核

    findMyBefore: [], //待审核信息列表
    findMyAfter: [], //通过信息列表
    findMyAfter2: [], //不通过信息列表

    countBefore: '',//待审核数量

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
    actionsDetailAfter: [{
      name: '确定',
      color: '#2d8cf0'
    }, ],

    //审核状态选项
    applStatusItem: [{
      id: 1,
      name: '通过',
    }, {
      id: 2,
      name: '不通过'
    }],

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    //教师根据审核状态查看自己需要审核的报名信息 返回自己审核的报名列表
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
      //教师根据审核状态查看自己需要审核的报名信息 返回自己审核的报名列表 待审核
      this.findMyBefore()

      this.setData({
        before: true,
        after: false,
      });
    } else if (this.data.current == "after") {
      //教师根据审核状态查看自己需要审核的报名信息 返回自己审核的报名列表 通过
      this.findMyAfter()

      //教师根据审核状态查看自己需要审核的报名信息 返回自己审核的报名列表 不通过
      this.findMyAfter2()

      this.setData({
        before: false,
        after: true,
      });
    }
  },

  //打开未审核的详情对话框
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
      applAuditorIdBefore: e.currentTarget.dataset.auditorid, //审核者ID
      applAuditorNameBefore: e.currentTarget.dataset.auditorname, //审核者姓名
      applClassBefore: e.currentTarget.dataset.class1, //所属班级
      applPhoneBefore: e.currentTarget.dataset.phone, //手机号码
    });
  },
  handleApplStatusChange({
    detail = {}
  }) {
    this.setData({
      applStatusBefore: detail.value
    });
  },
  handleDetailClickBefore({
    detail
  }) {
    const index = detail.index;

    if (index === 0) { //点击审核
      //学生根据报名编号动态更改报名信息 返回更改后的自己的报名列表
      this.modifyMy()

      if (this.data.applStatusBefore == "通过") {
        //管理员新增助教 返回新增后的助教列表
        this.addAssistant()

        //管理员新增通知 返回新增后的通知列表 新增助教的通知
        this.addMessage2()
      }
    }
    //教师根据审核状态查看自己需要审核的报名信息 返回自己审核的报名列表 待审核
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
  //教师根据审核状态查看自己需要审核的报名信息 返回自己审核的报名列表 待审核
  findMyBefore: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/application/teacher/findMyAuditByStatus',
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
                applAuditorId: result[i].applAuditorId, //审核者ID
                applAuditorName: result[i].applAuditorName, //审核者姓名
                applClass: result[i].applClass, //所属班级
                applPhone: result[i].applPhone, //手机号码
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
  //教师根据审核状态查看自己需要审核的报名信息 返回自己审核的报名列表 通过
  findMyAfter: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/application/teacher/findMyAuditByStatus',
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
                applAuditorId: result[i].applAuditorId, //审核者ID
                applAuditorName: result[i].applAuditorName, //审核者姓名
                applClass: result[i].applClass, //所属班级
                applPhone: result[i].applPhone, //手机号码
                createTime: util.formatDate(result[i].createTime) //时间戳转换为时间
              })
            }
            this.setData({
              findMyAfter: findMyAfter
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
  //教师根据审核状态查看自己需要审核的报名信息 返回自己审核的报名列表 不通过
  findMyAfter2: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/application/teacher/findMyAuditByStatus',
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
              content: '您没有不通过的内容~',
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
                applAuditorId: result[i].applAuditorId, //审核者ID
                applAuditorName: result[i].applAuditorName, //审核者姓名
                applClass: result[i].applClass, //所属班级
                applPhone: result[i].applPhone, //手机号码
                createTime: util.formatDate(result[i].createTime) //时间戳转换为时间
              })
            }
            this.setData({
              findMyAfter2: findMyAfter2
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
  //教师根据报名编号动态更改自己需要审核的报名信息 返回更改后的自己审核的报名列表
  modifyMy: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/application/teacher/modifyMyAudit',
      method: 'POST',
      data: {
        objectId: this.data.objectIdBefore, //编号
        applStatus: this.data.applStatusBefore //报名审核状态
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
          //管理员新增通知 返回新增后的通知列表
          this.addMessage()

          $Message({
            content: '审核成功！'
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
  //管理员新增通知 返回新增后的通知列表
  addMessage: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/message/admin/add',
      method: 'POST',
      data: {
        msgReceiverId: this.data.applSubmitterIdBefore, //通知接收人ID
        msgSenderId: '1', //通知发送人ID
        msgDesc: '您报名的' + this.data.applCourseBefore + '课程助教已被审核,快去看看吧~', //通知内容
        msgStatus: '待查看' //通知状态
      },
      header: {
        'content-type': 'application/json',
        'Cookie': SessionId
      },
      success: res => {
        console.log(res)
        console.log('submit success');
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
  //管理员新增助教 返回新增后的助教列表
  addAssistant: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/assistant/admin/add',
      method: 'POST',
      data: {
        assiCourse: this.data.applCourseBefore, //报名课程
        assiStudentName: this.data.applSubmitterNameBefore, //报名提交者姓名
        assiStudentId: this.data.applSubmitterIdBefore, //报名提交者ID
        assiTeacherId: this.data.applAuditorIdBefore, //审核者ID
        assiTeacherName: this.data.applAuditorNameBefore, //审核者姓名
        assiClass: this.data.applClassBefore, //所属班级
        assiPhone: this.data.applPhoneBefore, //手机号码
        assiWork: '暂无',
        assiHours: 0,
      },
      header: {
        'content-type': 'application/json',
        'Cookie': SessionId
      },
      success: res => {
        console.log(res)
        console.log('submit success');
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
  //管理员新增通知 返回新增后的通知列表 新增助教的通知
  addMessage2: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/message/admin/add',
      method: 'POST',
      data: {
        msgReceiverId: this.data.applSubmitterIdBefore, //通知接收人ID
        msgSenderId: '1', //通知发送人ID
        msgDesc: '恭喜您成为' + this.data.applCourseBefore + '的课程助教，撒花~', //通知内容
        msgStatus: '待查看' //通知状态
      },
      header: {
        'content-type': 'application/json',
        'Cookie': SessionId
      },
      success: res => {
        console.log(res)
        console.log('submit success');
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
})