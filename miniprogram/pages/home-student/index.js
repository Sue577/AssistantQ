const { $Toast } = require('../../dist/base/index');
var util = require("../../utils/util.js");

Page({
  data: {
    copyright: getApp().globalData.copyright,//底部版权
    link: getApp().globalData.link,//底部链接
    
    current: 'mine',//当前的tab
    recruit: false,//是否为招聘动态
    assistant: false,//是否为助教相关
    mine: true,//是否为我的相关

    findAllRecruit:[],//所有招聘信息列表
    objectId:''//选择的招聘信息编号
  },

/**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // this.findAllRecruit()
  },

  //切换tab
  handleChange({ detail }) {
    this.setData({
      current: detail.key
    });
    if (this.data.current =='recruit'){
      // this.findAllRecruit()
      this.setData({
        recruit: true
      });
      this.setData({
        assistant: false
      });
      this.setData({
        mine: false
      });
    } else if (this.data.current == 'assistant'){
      this.setData({
        recruit: false
      });
      this.setData({
        assistant: true
      });
      this.setData({
        mine: false
      });
    } else if (this.data.current == 'mine') {
      this.setData({
        recruit: false
      });
      this.setData({
        assistant: false
      });
      this.setData({
        mine: true
      });
    }
  },

  //所有人查找所有招聘 返回招聘列表
  findAllRecruit: function () {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie//获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/recruit/findAllRecruit',
      method: 'GET',
      header: {
        'content-type': 'application/json',
        'Cookie': SessionId
      },
      success: res => {
        console.log(res)
        console.log('submit success');
        if (res.data.msg == "没有权限") {
          $Toast({
            content: '登录过期，请重新登录嗷！',
            type: 'error'
          });
          setTimeout(() => {
            //要延时执行的代码
            $Toast.hide();
            wx.redirectTo({//当前页面切换成登录界面
              url: '../login/index',
            })
          }, 1000);
        } else {
          let result = res.data.data
          if (result.length == 0) {
            $Toast({
              content: '招聘信息空空如也~',
              type: 'warning'
            });
          } else {
            let findAllRecruit = []
            for (let i = 0; i < result.length; i++) {
              findAllRecruit.push({
                id: "招聘信息" + (i + 1),
                objectId: result[i].objectId,
                recrTitle: result[i].recrTitle,
                createTime: util.formatTimeTwo(result[i].createTime, 'M/D h:m:s')//时间戳转换为时间
              })
            }
            this.setData({
              findAllRecruit: findAllRecruit,
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
});