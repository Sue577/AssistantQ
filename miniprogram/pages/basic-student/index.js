const {
  $Toast
} = require('../../dist/base/index');
const {
  $Message
} = require('../../dist/base/index');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    copyright: getApp().globalData.copyright, //底部版权
    link: getApp().globalData.link, //底部链接

    modifyEmailModal: false, //修改邮箱对话框
    modifyPhoneModal: false, //修改手机号对话框
    modifyPwdModal: false, //修改密码对话框

    stuId: '', //学生ID
    stuName: '', //学生姓名
    stuIsAssistant: '', //是否为助教
    tag: '', //个人名片下的小提示
    stuClass: '', //所属班级
    stuBranch: '', //所属分院
    stuSchool: '', //所属学院

    userPwd: '', //用户原密码
    userNewPwd: '', //用户新密码
    userConfirmPwd: '', //用户确认密码
    userEmail: '', //用户邮箱
    userPhone: '', //用户手机号

  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    //学生查看自己学生信息 返回学生信息
    this.findMyStudent()
    //所有人查看自己用户信息 返回用户信息
    this.findMyUser()
  },
  // 获取输入userPwd
  userPwdInput: function(e) {
    this.setData({
      userPwd: e.detail.detail.value
    })
  },
  // 获取输入userNewPwd
  userNewPwdInput: function(e) {
    this.setData({
      userNewPwd: e.detail.detail.value
    })
  },
  // 获取输入userConfirmPwd
  userConfirmPwdInput: function(e) {
    this.setData({
      userConfirmPwd: e.detail.detail.value
    })
  },
  // 获取输入userEmail
  userEmailInput: function(e) {
    this.setData({
      userEmail: e.detail.detail.value
    })
  },
  // 获取输入userPhone
  userPhoneInput: function(e) {
    this.setData({
      userPhone: e.detail.detail.value
    })
  },

  // 打开修改邮箱对话框
  handleOpenEmail() {
    this.setData({
      modifyEmailModal: true
    });
  },
  // 确认修改邮箱
  handleOkEmail() {
    //所有人动态更改自己用户信息 返回修改后的用户信息
    this.modifyMyUser()

  },
  // 取消修改邮箱
  handleCancelEmail() {
    this.setData({
      modifyEmailModal: false
    });
  },

  // 打开修改手机号对话框
  handleOpenPhone() {
    this.setData({
      modifyPhoneModal: true
    });
  },
  // 确认修改手机号
  handleOkPhone() {
    //所有人动态更改自己用户信息 返回修改后的用户信息
    this.modifyMyUser()
  },
  // 取消修改手机号
  handleCancelPhone() {
    this.setData({
      modifyPhoneModal: false
    });
  },

  // 打开修改密码对话框
  handleOpenPwd() {
    this.setData({
      modifyPwdModal: true
    });
  },
  // 确认修改密码
  handleOkPwd() {
    //所有人更改自己用户密码 返回修改后的用户信息
    this.modifyMyPwdUser()
  },
  // 取消修改密码
  handleCancelPwd() {
    this.setData({
      modifyPwdModal: false
    });
  },
  //学生查看自己学生信息 返回学生信息
  findMyStudent: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/student/findMy',
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
          }, 2000);
        } else {
          let result = res.data.data
          this.setData({
            stuId: result.stuId, //学生ID
            stuName: result.stuName, //学生姓名
            stuIsAssistant: result.stuIsAssistant, //是否为助教
            stuClass: result.stuClass, //所属班级
            stuBranch: result.stuBranch, //所属分院
            stuSchool: result.stuSchool //所属学院
          })
          if (result.stuIsAssistant == '是') {
            this.setData({
              tag: '亲爱的助教，加油工作哟~ღ( ´･ᴗ･` )比心' //个人名片下的小提示
            })
          } else {
            this.setData({
              tag: '你现在还不是助教呢~快去看看招聘动态吧！' //个人名片下的小提示
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
  //所有人查看自己用户信息 返回用户信息
  findMyUser: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/user/findMy',
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
          }, 2000);
        } else {
          let result = res.data.data
          this.setData({
            userEmail: result.userEmail, //用户邮箱
            userPhone: result.userPhone //用户手机号
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
  //所有人更改自己用户密码 返回修改后的用户信息
  modifyMyPwdUser: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/user/modifyMyPwd',
      method: 'POST',
      data: {
        userPwd: this.data.userPwd, //用户原密码
        userNewPwd: this.data.userNewPwd, //用户新密码
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
          }, 2000);
        } else if (res.data.code == 105) {
          $Toast({
            content: '密码错误，请重新输入！',
            type: 'error'
          });
        } else {
          $Message({
            content: '密码修改成功~'
          });
          this.setData({
            modifyPwdModal: false
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
  //所有人动态更改自己用户信息 返回修改后的用户信息
  modifyMyUser: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/user/modifyMy',
      method: 'POST',
      data: {
        userEmail: this.data.userEmail, //用户邮箱
        userPhone: this.data.userPhone, //用户手机号
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
          }, 2000);
        } else {
          let result = res.data.data
          this.setData({
            userEmail: result.userEmail, //用户邮箱
            userPhone: result.userPhone //用户手机号
          })
          $Message({
            content: '修改成功~'
          });
          this.setData({
            modifyEmailModal: false,
            modifyPhoneModal: false
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
})