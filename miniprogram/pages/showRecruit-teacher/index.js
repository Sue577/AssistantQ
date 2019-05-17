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
    copyright: getApp().globalData.copyright,//底部版权
    link: getApp().globalData.link,//底部链接

    objectId: '', //选择的招聘信息编号
    createTime: '', //创建时间
    recrTitle: '', //招聘信息标题
    recrCourse: '', //相关课程
    recrDesc: '', //招聘信息描述
    recrSubmitterName: '', //提交者姓名
    recrSubmitterId: '', //提交者ID
    recrDeadLine: '', //截止时间

  },
  /**
 * 生命周期函数--监听页面加载
 */
  onLoad: function (options) {
    // 界面跳转参数传递
    if (options && options.objectId) {
      this.setData({
        objectId: options.objectId
      });
    }
    //所有人根据招聘信息编号查找招聘信息 返回招聘信息
    this.findByObjectId()
  },
  //所有人根据招聘信息编号查找招聘信息 返回招聘信息
  findByObjectId: function () {
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