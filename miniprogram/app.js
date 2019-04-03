//app.js
App({
  onLaunch: function () {
    console.log('App Launch')
  },
  onShow: function () {
    console.log('App Show')
  },
  onHide: function () {
    console.log('App Hide')
  },
  globalData: {
    header: { 'Cookie': 'JSESSIONID=9EBF43D024812737410BF756EAD1B714' },//请求头 保存sessionId

    url: "http://localhost:8081",//请求访问的url
    localhost: "http://localhost:8081",//本地
    ip: "http://139.196.122.103:443",//IP地址
    domain: "https://www.archivebook.top:443",//域名

    link: "退出登录",//底部链接
    copyright: "Copyright © 2019 ZUCC-WSQ",//底部版权信息

    hasLogin: false
  }
})