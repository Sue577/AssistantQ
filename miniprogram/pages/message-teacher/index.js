Page({
  data: {
    copyright: getApp().globalData.copyright,//底部版权
    link: getApp().globalData.link,//底部链接

    current: 'beforeLook', //当前tab
    beforeLook: true, //未查看通知信息列表
    afterLook: false, //已查看通知信息列表
  },

  //切换tab
  handleChange({
    detail
  }) {
    this.setData({
      current: detail.key
    });
    if (this.data.current == "beforeLook") {
      this.setData({
        beforeLook: true,
        afterLook: false,
      });
    } else if (this.data.current == "afterLook") {
      this.setData({
        beforeLook: false,
        afterLook: true,
      });
    }
  },

});