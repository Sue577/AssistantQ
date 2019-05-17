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

    addApplicationButton: true, //是否显示报名按钮
    addApplicationModal: false, //是否显示报名对话框
    addApplicationSuccess: false, //是否显示我的报名信息

    objectId: '', //选择的招聘信息编号
    createTime: '', //创建时间
    recrTitle: '', //招聘信息标题
    recrCourse: '', //相关课程
    recrDesc: '', //招聘信息描述
    recrSubmitterName: '', //提交者姓名
    recrSubmitterId: '', //提交者ID
    recrDeadLine: '', //截止时间

    applSubmitterName: '', //报名提交者姓名
    applClass: '', //所属班级
    applPhone: '', //手机号码
    applDesc: '' //报名简历

  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    // 界面跳转参数传递
    if (options && options.objectId) {
      this.setData({
        objectId: options.objectId
      });
    }
    //所有人根据招聘信息编号查找招聘信息 返回招聘信息
    this.findByObjectId()
    //学生根据招聘编号和提交者ID查询查看自己报名信息 返回自己的报名信息
    this.findByApplRecruitIdAndApplSubmitterId()
  },
  // 获取输入applSubmitterName
  applSubmitterNameInput: function(e) {
    this.setData({
      applSubmitterName: e.detail.detail.value
    })
  },
  // 获取输入applClass
  applClassInput: function(e) {
    this.setData({
      applClass: e.detail.detail.value
    })
  },
  // 获取输入applPhone
  applPhoneInput: function(e) {
    this.setData({
      applPhone: e.detail.detail.value
    })
  },
  // 获取输入applDesc
  applDescInput: function(e) {
    this.setData({
      applDesc: e.detail.detail.value
    })
  },
  //打开弹框
  handleOpenApplication() {
    this.setData({
      addApplicationModal: true
    });
  },
  //点击确认
  handleOkApplication() {
    //学生新增报名 返回新增后的自己的报名列表
    this.addMyApplication()
    this.setData({
      addApplicationModal: false
    });
  },
  //点击取消
  handleCancelApplication() {
    this.setData({
      addApplicationModal: false
    });
  },
  //所有人根据招聘信息编号查找招聘信息 返回招聘信息
  findByObjectId: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/recruit/findByObjectId',
      method: 'POST',
      data: {
        objectId: this.data.objectId, //招聘编号
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
          this.setData({
            objectId: result.objectId, //选择的招聘信息编号
            createTime: util.formatDate(result.createTime), //时间戳转换为时间
            recrTitle: result.recrTitle, //招聘信息标题
            recrCourse: result.recrCourse, //相关课程
            recrDesc: result.recrDesc, //招聘信息描述
            recrSubmitterName: result.recrSubmitterName, //提交者姓名
            recrSubmitterId: result.recrSubmitterId, //提交者ID
            recrDeadLine: result.recrDeadLine, //截止时间
          })
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
  //学生新增报名 返回新增后的自己的报名列表
  addMyApplication: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/application/student/addMy',
      method: 'POST',
      data: {
        applRecruitId: this.data.objectId, //所属招聘编号
        applCourse: this.data.recrCourse, //相关课程
        applAuditorId: this.data.recrSubmitterId, //审核者姓名
        applAuditorName: this.data.recrSubmitterName, //审核者ID
        applSubmitterName: this.data.applSubmitterName, //报名提交者姓名
        applClass: this.data.applClass, //所属班级
        applPhone: this.data.applPhone, //手机号码
        applDesc: this.data.applDesc, //报名简历
        applStatus: '待审核' //审核状态
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
          this.setData({
            addApplicationButton: false //隐藏报名按钮
          });
          this.setData({
            addApplicationSuccess: true //显示我的报名信息
          });
          //管理员新增通知 返回新增后的通知列表
          this.addMessage()
          $Message({
            content: '报名成功！'
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
  //学生根据招聘编号和提交者ID查询查看自己报名信息 返回自己的报名信息
  findByApplRecruitIdAndApplSubmitterId: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/application/student/findByApplRecruitIdAndApplSubmitterId',
      method: 'POST',
      data: {
        applRecruitId: this.data.objectId, //所属招聘编号
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
          this.setData({
            applDesc: result.applDesc //报名简历
          })
          this.setData({
            addApplicationButton: false //隐藏报名按钮
          });
          this.setData({
            addApplicationSuccess: true //显示我的报名信息
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
  addMessage: function () {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/message/admin/add',
      method: 'POST',
      data: {
        msgReceiverId: this.data.recrSubmitterId,//通知接收人ID
        msgSenderId: '1',//通知发送人ID
        msgDesc: this.data.applSubmitterName + '报名了您的' + this.data.recrCourse+'，请尽快审核~',//通知内容
        msgStatus: '待查看'//通知状态
      },
      header: {
        'content-type': 'application/json',
        'Cookie': SessionId
      },
      success: res => {
        console.log(res)
        console.log('submit success');
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
})