import Main from '@/components/main'
// import parentView from '@/components/parent-view'

/**
 * iview-admin中meta除了原生参数外可配置的参数:
 * meta: {
 *  title: { String|Number|Function }
 *         显示在侧边栏、面包屑和标签栏的文字
 *         使用'{{ 多语言字段 }}'形式结合多语言使用，例子看多语言的路由配置;
 *         可以传入一个回调函数，参数是当前路由对象，例子看动态路由和带参路由
 *  hideInBread: (false) 设为true后此级路由将不会出现在面包屑中，示例看QQ群路由配置
 *  hideInMenu: (false) 设为true后在左侧菜单不会显示该页面选项
 *  notCache: (false) 设为true后页面在切换标签后不会缓存，如果需要缓存，无需设置这个字段，而且需要设置页面组件name属性和路由配置的name一致
 *  access: (null) 可访问该页面的权限数组，当前路由设置的权限会影响子路由
 *  icon: (-) 该页面在左侧菜单、面包屑和标签导航处显示的图标，如果是自定义图标，需要在图标名称前加下划线'_'
 *  beforeCloseName: (-) 设置该字段，则在关闭当前tab页时会去'@/router/before-close.js'里寻找该字段名对应的方法，作为关闭前的钩子函数
 * }
 */

export default [
  {
    path: '/login',
    name: 'login',
    meta: {
      title: 'Login - 登录',
      hideInMenu: true
    },
    component: () => import('@/view/login/login.vue')
  },
  {
    path: '/',
    name: '_home',
    redirect: '/home',
    component: Main,
    meta: {
      hideInMenu: true,
      notCache: true
    },
    children: [
      {
        path: '/home',
        name: 'home',
        meta: {
          hideInMenu: true,
          title: '首页',
          notCache: true,
          icon: 'md-home'
        },
        component: () => import('@/view/single-page/home')
      }
    ]
  },
  {
    path: '/message',
    name: 'message',
    component: Main,
    meta: {
      hideInBread: true,
      hideInMenu: true
    },
    children: [
      {
        path: 'message_page',
        name: 'message_page',
        meta: {
          icon: 'md-notifications',
          title: '消息中心'
        },
        component: () => import('@/view/single-page/message/index.vue')
      }
    ]
  },
  {
    path: '/user',
    name: 'user',
    meta: {
      hideInBread: false,
      hideInMenu: false
    },
    component: Main,
    children: [
      {
        path: 'user_page',
        name: '用户信息管理',
        meta: {
          icon: 'md-grid',
          title: '用户信息管理'
        },
        component: () => import('@/view/components/tables/tables.vue')
      }
    ]
  },
  {
    path: '/student',
    name: 'student',
    meta: {
      hideInBread: false,
      hideInMenu: false
    },
    component: Main,
    children: [
      {
        path: 'student_page',
        name: '学生信息管理',
        meta: {
          icon: 'ios-book',
          title: '学生信息管理'
        },
        component: () => import('@/view/components/tables/tables.vue')
      }
    ]
  },
  {
    path: '/teacher',
    name: 'teacher',
    meta: {
      hideInBread: false,
      hideInMenu: false
    },
    component: Main,
    children: [
      {
        path: 'teacher_page',
        name: '教师信息管理',
        meta: {
          icon: 'logo-buffer',
          title: '教师信息管理'
        },
        component: () => import('@/view/components/tables/tables.vue')
      }
    ]
  },
  {
    path: '/course',
    name: 'course',
    meta: {
      hideInBread: false,
      hideInMenu: false
    },
    component: Main,
    children: [
      {
        path: 'course_page',
        name: '课程信息管理',
        meta: {
          icon: 'md-arrow-dropdown-circle',
          title: '课程信息管理'
        },
        component: () => import('@/view/components/tables/tables.vue')
      }
    ]
  },
  {
    path: '/recruit',
    name: 'recruit',
    meta: {
      hideInBread: false,
      hideInMenu: false
    },
    component: Main,
    children: [
      {
        path: 'recruit_page',
        name: '招聘信息管理',
        meta: {
          icon: 'md-trending-up',
          title: '招聘信息管理'
        },
        component: () => import('@/view/components/tables/tables.vue')
      }
    ]
  },
  {
    path: '/application',
    name: 'application',
    meta: {
      hideInBread: false,
      hideInMenu: false
    },
    component: Main,
    children: [
      {
        path: 'application_page',
        name: '报名信息管理',
        meta: {
          icon: 'ios-infinite',
          title: '报名信息管理'
        },
        component: () => import('@/view/components/tables/tables.vue')
      }
    ]
  },
  {
    path: '/assistant',
    name: 'assistant',
    meta: {
      hideInBread: false,
      hideInMenu: false
    },
    component: Main,
    children: [
      {
        path: 'assistant_page',
        name: '助教信息管理',
        meta: {
          icon: 'ios-people',
          title: '助教信息管理'
        },
        component: () => import('@/view/components/tables/tables.vue')
      }
    ]
  },
  {
    path: '/record',
    name: 'record',
    meta: {
      hideInBread: false,
      hideInMenu: false
    },
    component: Main,
    children: [
      {
        path: 'record_page',
        name: '工作记录管理',
        meta: {
          icon: 'md-list',
          title: '工作记录管理'
        },
        component: () => import('@/view/components/tables/tables.vue')
      }
    ]
  },
  {
    path: '/evaluation',
    name: 'evaluation',
    meta: {
      hideInBread: false,
      hideInMenu: false
    },
    component: Main,
    children: [
      {
        path: 'evaluation_page',
        name: '工作考核管理',
        meta: {
          icon: 'md-git-branch',
          title: '工作考核管理'
        },
        component: () => import('@/view/components/tables/tables.vue')
      }
    ]
  },
  {
    path: '/message',
    name: 'message',
    meta: {
      hideInBread: false,
      hideInMenu: false
    },
    component: Main,
    children: [
      {
        path: 'message_page',
        name: '通知信息管理',
        meta: {
          icon: 'md-notifications',
          title: '通知信息管理'
        },
        component: () => import('@/view/components/tables/tables.vue')
      }
    ]
  }
]
