export default [
  {
    path: '/welcome',
    name: '欢迎',
    icon: 'home',
    component: './Welcome',
  },
  {
    layout: false,
    path: '/login',
    component: './login',
  },
  {
    path: '/',
    redirect: '/welcome',
  },
  {
    path: '/profile',
    component: './sys/Profile',
    hideInMenu: true,
    "menu": {
      "name": "个人中心"
    },
  },
  {
    path: '/403',
    component: './403',
  },
  {
    path: '/404',
    component: './404',
  },
  {
    path: '/500',
    component: './500',
  },
  {
    "name": "140416512530841600",
    "icon": "setting",
    "menu": {
      "name": "系统管理"
    },
    "routes": [
      {
        "name": "140606123509022720",
        "path": "/sys/action",
        "component": "./sys/Action",
        "menu": {
          "name": "系统功能"
        }
      },
      {
        "name": "140606307425058816",
        "path": "/sys/menu",
        "component": "./sys/Menu",
        "menu": {
          "name": "系统菜单"
        }
      },
      {
        "name": "143129379252207616",
        "path": "/sys/property/categories",
        "component": "./sys/PropertyCategories",
        "menu": {
          "name": "参数类型"
        }
      },
      {
        "name": "143130889742712832",
        "path": "/sys/properties",
        "component": "./sys/Properties",
        "menu": {
          "name": "系统参数"
        }
      },
      {
        "name": "143131111843692544",
        "path": "/sys/roles",
        "component": "./sys/Roles",
        "menu": {
          "name": "角色管理"
        }
      },
      {
        "name": "143131272363900928",
        "path": "/sys/users",
        "component": "./sys/Users",
        "menu": {
          "name": "用户管理"
        }
      }
    ]
  }
];
