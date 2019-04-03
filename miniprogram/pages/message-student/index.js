Page({
  data: {
    copyright: getApp().globalData.copyright,//底部版权
    link: getApp().globalData.link,//底部链接

    current: 'before', //当前tab
    before: true, //未查看通知信息列表
    after: false, //已查看通知信息列表
  },

  //切换tab
  handleChange({
    detail
  }) {
    this.setData({
      current: detail.key
    });
    if (this.data.current == "before") {
      this.setData({
        before: true,
        after: false,
      });
    } else if (this.data.current == "after") {
      this.setData({
        before: false,
        after: true,
      });
    }
  },

});