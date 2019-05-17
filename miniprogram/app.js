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
    header: { 'Cookie': 'JSESSIONID=40288b8169cd7df20169cd9e7d4e0008' },//请求头 保存sessionId

    url: "http://localhost:8081",//请求访问的url
    localhost: "http://localhost:8081",//本地
    ip: "http://47.100.213.163:443",//公网IP地址
    domain: "https://www.assistantq.cn:443",//域名

    link: "退出登录",//底部链接
    copyright: "Copyright © 2019 ZUCC-WSQ",//底部版权信息

    hasLogin: false
  }
})