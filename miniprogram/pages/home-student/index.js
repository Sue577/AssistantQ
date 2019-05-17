const {
  $Toast
} = require('../../dist/base/index');
var util = require("../../utils/util.js");

Page({
  data: {
    copyright: getApp().globalData.copyright, //底部版权
    link: getApp().globalData.link, //底部链接

    current: 'mine', //当前的tab
    recruit: false, //是否为招聘动态
    assistant: false, //是否为助教信息
    mine: true, //是否为我的相关

    //招聘动态tab
    findAllRecruit: [], //所有招聘信息列表
    objectId: '', //选择的招聘信息编号

    //助教信息tab
    findMyAssistant:[],//我的助教信息列表
    assiCourse:'',//选择的助教课程

    //我的相关tab
    stuId: '', //学生ID
    stuName: '', //学生姓名
    stuIsAssistant: '', //是否为助教
    tag: '' //个人名片下的小提示
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    //学生查看自己学生信息 返回学生信息
    this.findMyStudent()
  },

  //切换tab
  handleChange({
    detail
  }) {
    this.setData({
      current: detail.key
    });
    if (this.data.current == 'recruit') {
      //所有人查找所有招聘 返回招聘列表
      this.findAllRecruit()
      this.setData({
        recruit: true
      });
      this.setData({
        assistant: false
      });
      this.setData({
        mine: false
      });
    } else if (this.data.current == 'assistant') {
      //学生查看自己助教信息 返回自己的助教列表
      this.findMyAssistant()
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
      //学生查看自己学生信息 返回学生信息
      this.findMyStudent()
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
  findAllRecruit: function() {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
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
                recrSubmitterName: result[i].recrSubmitterName,
                createTime: util.formatDate(result[i].createTime) //时间戳转换为时间
              })
            }
            this.setData({
              findAllRecruit: findAllRecruit,
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
  //学生查看自己助教信息 返回自己的助教列表
  findMyAssistant: function () {
    var header = getApp().globalData.header; //获取app.js中的请求头
    var SessionId = header.Cookie //获取保存的SessionId
    console.log(SessionId)

    var url = getApp().globalData.url; //获取app.js中的url

    wx.request({
      url: url + '/assistant/findMy',
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
              content: '看来你还不是助教呢~快去招聘动态看看吧',
              type: 'warning'
            });
          } else {
            let findMyAssistant = []
            for (let i = 0; i < result.length; i++) {
              findMyAssistant.push({
                id: "助教课程" + (i + 1),
                objectId: result[i].objectId,
                assiCourse: result[i].assiCourse,
                assiTeacherName: result[i].assiTeacherName,
                createTime: util.formatDate(result[i].createTime) //时间戳转换为时间
              })
            }
            this.setData({
              findMyAssistant: findMyAssistant,
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
  //学生查看自己学生信息 返回学生信息
  findMyStudent: function () {
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
        if (res.data.msg == "没有权限" | res.data.code==-1) {
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
          })
          if (result.stuIsAssistant=='是'){
            this.setData({
              tag: '亲爱的助教，加油工作哟~ღ( ´･ᴗ･` )比心' //个人名片下的小提示
            })
          }else{
            this.setData({
              tag: '你现在还不是助教呢~快去看看招聘动态吧！' //个人名片下的小提示
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