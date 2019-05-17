const {
  $Toast
} = require('../../dist/base/index');
const {
  $Message
} = require('../../dist/base/index');
var util = require("../../utils/util.js");

Page({
  data: {
    copyright: getApp().globalData.copyright,//底部版权
    link: getApp().globalData.link,//底部链接

    current: 'before', //当前tab
    before: true, //待查看通知信息列表
    after: false, //已查看通知信息列表

    findMyBefore: [],//待查看通知信息
    findMyAfter: [],//已查看通知信息

    countBefore: '',//待查看通知数量

    objectId: ''//选中的编号
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //教师和学生根据查看状态查看自己通知信息 返回自己的通知列表
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
      //教师和学生根据查看状态查看自己通知信息 返回自己的通知列表
      this.findMyBefore()

      this.setData({
        before: true,
        after: false,
      });
    } else if (this.data.current == "after") {
      //教师和学生根据查看状态查看自己通知信息 返回自己的通知列表
      this.findMyAfter()

      this.setData({
        before: false,
        after: true,
      });
    }
  },

  //将待查看变为已查看
  handleOpenBeforeDetail(e) {
    //获取传递的参数
    this.setData({
      objectId: e.currentTarget.dataset.id //编号
    });

    //管理员根据通知编号动态更改通知信息 返回更改后的通知列表
    this.modify()

    //教师和学生根据查看状态查看自己通知信息 返回自己的通知列表
    this.findMyBefore()
  },

  //教师和学生根据查看状态查看自己通知信息 返回自己的通知列表
  findMyBefore: function () {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/message/findMyByStatus',
      method: 'POST',
      data: {
        msgStatus: '待查看',
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
              content: '您没有待查看的通知~',
              type: 'warning'
            });
          } else {
            let findMyBefore = []
            for (let i = 0; i < result.length; i++) {
              findMyBefore.push({
                id: "通知" + (i + 1),
                objectId: result[i].objectId, //编号
                msgDesc: result[i].msgDesc,//通知内容
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
  //教师和学生根据查看状态查看自己通知信息 返回自己的通知列表
  findMyAfter: function () {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/message/findMyByStatus',
      method: 'POST',
      data: {
        msgStatus: '已查看',
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
              content: '您没有已查看的通知~',
              type: 'warning'
            });
          } else {
            let findMyAfter = []
            for (let i = 0; i < result.length; i++) {
              findMyAfter.push({
                id: "通知" + (i + 1),
                objectId: result[i].objectId, //编号
                msgDesc: result[i].msgDesc,//通知内容
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

  //管理员根据通知编号动态更改通知信息 返回更改后的通知列表
  modify: function () {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/message/admin/modify',
      method: 'POST',
      data: {
        objectId: this.data.objectId, //编号
        msgStatus: '已查看',
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
            content: '您已查看这条通知~'
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

});