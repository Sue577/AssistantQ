const {
  $Toast
} = require('../../dist/base/index');

Page({
  data: {
    copyright: getApp().globalData.copyright, //版权
    userId: '',//用户ID
    userPwd: '',//用户密码
  },
  // 获取输入userId  
  userIdInput: function(e) {
    this.setData({
      userId: e.detail.detail.value
    })
  },
  // 获取输入userPwd 
  userPwdInput: function(e) {
    this.setData({
      userPwd: e.detail.detail.value
    })
  },
  // 登录  
  login: function() {

    if (this.data.userId.length == 0 || this.data.userPwd.length == 0) {
      $Toast({
        content: '用户ID或密码不能为空',
        type: 'warning'
      });
    } else {
      // 假登录
      // if (this.data.userId == '31501284') {
      //   //学生登录成功提示
      //   $Toast({
      //     content: '小同志好，欢迎进入本系统',
      //     type: 'success'
      //   });
      //   setTimeout(() => {
      //     //要延时执行的代码
      //     $Toast.hide();
      //     wx.redirectTo({ //当前页面切换成主界面-学生
      //       url: '../home-student/index',
      //     })
      //   }, 1000);
      // } else if (this.data.userId == 'J10000') {
      //   //教师登录成功提示
      //   $Toast({
      //     content: '老师您好，欢迎进入本系统',
      //     type: 'success'
      //   });
      //   setTimeout(() => {
      //     //要延时执行的代码
      //     $Toast.hide();
      //     wx.redirectTo({ //当前页面切换成主界面-教师
      //       url: '../home-teacher/index',
      //     })

      //   }, 1000);
      // }

      //上传登录信息并获取服务器登录数据，判断是否登录成功
      this.postLoginData()
    }
  },
  //上传登录信息并获取服务器登录数据，判断是否登录成功
  postLoginData:function() {
    var that = this
    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/user/login',
      data: {
        userId: this.data.userId,
        userPwd: this.data.userPwd
      },
      method: 'POST',
      header: {
        'content-type': 'application/json;charset=utf-8'
      },
      success: res => {
        console.log(res)
        console.log('submit success');
        if (parseInt(res.statusCode) === 200) { //网络  请求成功
          let result = res.data.data
          // 保存SessionId到app.js
          getApp().globalData.header.Cookie = 'JSESSIONID=' + result.SessionId;

          if (result.userType == '学生') {
            //学生登录成功提示
            $Toast({
              content: '小同志好，欢迎进入本系统',
              type: 'success'
            });
            setTimeout(() => {
              //要延时执行的代码
              $Toast.hide();
              wx.redirectTo({ //当前页面切换成主界面-学生
                url: '../home-student/index',
              })
            }, 1000);
          } else if (result.userType == '教师') {
            //教师登录成功提示
            $Toast({
              content: '老师您好，欢迎进入本系统',
              type: 'success'
            });
            setTimeout(() => {
              //要延时执行的代码
              $Toast.hide();
              wx.redirectTo({ //当前页面切换成主界面-教师
                url: '../home-teacher/index',
              })

            }, 1000);
          } else {
            //登录失败提示
            $Toast({
              content: '登录失败',
              type: 'error'
            });
          }

        }

      },
      fail: function(err) {
        console.log(err)
        console.log('submit fail');
        //请求失败提示
        $Toast({
          content: '请求失败',
          type: 'error'
        });
      },
      complete: function(res) {
        console.log('submit complete');
      }

    })
  },
});