export default [
  {
    path: '/user',
    layout: false,
    routes: [
      {
        path: '/user',
        routes: [
          {
            name: 'login',
            path: '/user/login',
            component: './user/login',
          },
        ],
      },
    ],
  },
  {
    path: '/welcome',
    name: 'welcome',
    icon: 'smile',
    component: './Welcome',
  },
  {
    path: '/admin',
    name: 'admin',
    icon: 'crown',
    access: 'canAdmin',
    component: './Admin',
    routes: [
      {
        path: '/admin/sub-page',
        name: 'sub-page',
        icon: 'smile',
        component: './Welcome',
      },
    ],
  },
  {
    name: 'list.table-list',
    icon: 'table',
    path: '/list',
    component: './ListTableList',
  },
  {
    name: 'sys',
    icon: 'setting',
    menu: {
      name: '系统管理',
    },
    routes: [
      {
        name: 'action',
        path: '/sys/action',
        component: './sys/Action',
        menu: {
          name: '系统功能',
        },
      },
      {
        name: 'menu',
        path: '/sys/menu',
        component: './sys/Menu',
        menu: {
          name: '系统菜单',
        },
      },
      {
        name: 'categories',
        path: '/sys/property/categories',
        component: './sys/PropertyCategories',
        menu: {
          name: '参数类型',
        },
      },
      {
        name: 'properties',
        path: '/sys/properties',
        component: './sys/Properties',
        menu: {
          name: '系统参数',
        },
      },
      {
        name: 'roles',
        path: '/sys/roles',
        component: './sys/Roles',
        menu: {
          name: '角色管理',
        },
      },
      {
        name: 'users',
        path: '/sys/users',
        component: './sys/Users',
        menu: {
          name: '用户管理',
        },
      },
    ],
  },
  {
    path: '/',
    redirect: '/welcome',
  },
  {
    component: './404',
  },
];
