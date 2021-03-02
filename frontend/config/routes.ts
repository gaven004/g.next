export default [
  {
    path: '/welcome',
    name: 'welcome',
    icon: 'smile',
    component: './Welcome',
  },
  {
    name: 'login',
    layout: false,
    path: '/login',
    component: './login',
  },
  {
    path: '/',
    redirect: '/welcome',
  },
  {
    component: './403',
  },
  {
    component: './404',
  },
  {
    component: './500',
  },
];
