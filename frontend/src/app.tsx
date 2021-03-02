import React from 'react';

import {history, RequestConfig, RunTimeLayoutConfig} from 'umi';
import {ResponseError} from 'umi-request';

import {notification} from 'antd';
import {MenuDataItem, PageLoading, Settings as LayoutSettings} from '@ant-design/pro-layout';
import {HomeOutlined} from "@ant-design/icons";

import RightContent from '@/components/RightContent';
import Footer from '@/components/Footer';
import {buildIcon} from "@/utils/icons";

import defaultSettings from '../config/defaultSettings';
import {queryMenu} from './services/user';
import {API} from "@/services/API";

/**
 * 获取用户信息比较慢的时候会展示一个 loading
 */
export const initialStateConfig = {
  loading: <PageLoading/>,
};

export async function getInitialState(): Promise<{
  settings?: LayoutSettings;
  currentUser?: API.CurrentUser;
  fetchUserInfo?: () => Promise<API.CurrentUser | undefined>;
  menuData: MenuDataItem[];
}> {
  const fetchUserInfo = async () => {
    try {
      // const currentUser = await queryCurrent();
      const currentUser = {
        name: 'Serati Ma',
        avatar: 'https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png',
        userid: '00000001',
        email: 'antdesign@alipay.com',
        signature: '海纳百川，有容乃大',
        title: '交互专家',
        group: '蚂蚁金服－某某某事业群－某某平台部－某某技术部－UED',
        tags: [
          {
            key: '0',
            label: '很有想法的',
          },
          {
            key: '1',
            label: '专注设计',
          },
          {
            key: '2',
            label: '辣~',
          },
          {
            key: '3',
            label: '大长腿',
          },
          {
            key: '4',
            label: '川妹子',
          },
          {
            key: '5',
            label: '海纳百川',
          },
        ],
        notifyCount: 12,
        unreadCount: 11,
        country: 'China',
        access: 'admin',
        geographic: {
          province: {
            label: '浙江省',
            key: '330000',
          },
          city: {
            label: '杭州市',
            key: '330100',
          },
        },
        address: '西湖区工专路 77 号',
        phone: '0752-268888888',
      };
      return currentUser;
    } catch (error) {
      history.push('/login');
    }
    return undefined;
  };

  const queryMenuData = async () => {
    try {
      const fixed = [
        {
          path: '/user',
          layout: false,
          routes: [
            {
              path: '/user',
              routes: [
                {
                  name: 'login',
                  path: '/login',
                  component: './login',
                },
              ],
            },
          ],
        },
        {
          path: '/welcome',
          name: '欢迎',
          icon: <HomeOutlined/>,
          component: './Welcome',
        },
        {
          path: '/',
          redirect: '/welcome',
        },
        {
          component: './404',
        },
      ];
      const menu = await queryMenu();

      if (menu?.success) {
        return fixed.concat(
          // @ts-ignore
          menu.data.map(item => ({
            ...item,
            icon: buildIcon(item.icon)
          }))
        );
      }
    } catch (error) {
      history.push('/login');
    }

    return undefined;
  };

  // 如果是登录页面，不执行
  if (history.location.pathname !== '/login') {
    const currentUser = await fetchUserInfo();
    const menuData = await queryMenuData();
    console.log(menuData);
    return {
      fetchUserInfo,
      currentUser,
      menuData,
      settings: defaultSettings,
    };
  }

  return {
    fetchUserInfo,
    settings: defaultSettings,
    menuData: [],
  };
}

export const layout: RunTimeLayoutConfig = ({initialState}) => {
  return {
    rightContentRender: () => <RightContent/>,
    disableContentMargin: false,
    footerRender: () => <Footer/>,
    onPageChange: () => {
      const {location} = history;
      // 如果没有登录，重定向到 login
      if (!initialState?.currentUser && location.pathname !== '/login') {
        history.push('/login');
      }
    },
    menuHeaderRender: undefined,
    menuDataRender: (menuData) => initialState?.menuData || menuData,
    ...initialState?.settings,
  };
};

const codeMessage = {
  200: '服务器成功返回请求的数据。',
  201: '新建或修改数据成功。',
  202: '一个请求已经进入后台排队（异步任务）。',
  204: '删除数据成功。',
  400: '发出的请求有错误，服务器没有进行新建或修改数据的操作。',
  401: '用户没有权限（令牌、用户名、密码错误）。',
  403: '用户得到授权，但是访问是被禁止的。',
  404: '发出的请求针对的是不存在的记录，服务器没有进行操作。',
  405: '请求方法不被允许。',
  406: '请求的格式不可得。',
  410: '请求的资源被永久删除，且不会再得到的。',
  422: '当创建一个对象时，发生一个验证错误。',
  500: '服务器发生错误，请检查服务器。',
  502: '网关错误。',
  503: '服务不可用，服务器暂时过载或维护。',
  504: '网关超时。',
};

/**
 * 异常处理程序
 */
const errorHandler = (error: ResponseError) => {
  const {response} = error;
  if (response && response.status) {
    const errorText = codeMessage[response.status] || response.statusText;
    const {status, url} = response;

    notification.error({
      message: `请求错误 ${status}: ${url}`,
      description: errorText,
    });
  }

  if (!response) {
    notification.error({
      description: '您的网络发生异常，无法连接服务器',
      message: '网络异常',
    });
  }
  throw error;
};

export const request: RequestConfig = {
  timeout: 3000,
  errorHandler,
};
