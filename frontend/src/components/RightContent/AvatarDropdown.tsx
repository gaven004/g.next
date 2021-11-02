import React, {useCallback} from 'react';

import {history, useModel} from 'umi';
import {Avatar, Menu, Spin} from 'antd';
import {stringify} from 'querystring';

import {LogoutOutlined, SettingOutlined, UserOutlined} from '@ant-design/icons';

import HeaderDropdown from '../HeaderDropdown';
import styles from './index.less';

export interface GlobalHeaderRightProps {
  menu?: boolean;
}

/**
 * 退出登录，并且将当前的 url 保存
 */
const logout = async () => {
  const {query, pathname} = history.location;
  // @ts-ignore
  const {redirect} = query;

  localStorage.removeItem("AuthorizationToken");

  // Note: There may be security issues, please note
  if (window.location.pathname !== '/login' && !redirect) {
    history.replace({
      pathname: '/login',
      search: stringify({
        redirect: pathname,
      }),
    });
  }
};

const AvatarDropdown: React.FC<GlobalHeaderRightProps> = ({menu}) => {
  const {initialState, setInitialState} = useModel('@@initialState');

  const onMenuClick = useCallback(
    (event: {
      key: React.Key;
      keyPath: React.Key[];
      item: React.ReactInstance;
      domEvent: React.MouseEvent<HTMLElement>;
    }) => {
      const {key} = event;
      switch (key) {
        case 'logout':
          if (initialState) {
            setInitialState({...initialState, currentUser: undefined});
            logout();
          }
          break;
        case 'profile':
          history.push(`/profile`);
          break;
      }
    },
    [],
  );

  const loading = (
    <span className={`${styles.action} ${styles.account}`}>
      <Spin
        size="small"
        style={{
          marginLeft: 8,
          marginRight: 8,
        }}
      />
    </span>
  );

  if (!initialState) {
    return loading;
  }

  const {currentUser} = initialState;

  if (!currentUser || !currentUser.username) {
    return loading;
  }

  const menuHeaderDropdown = (
    <Menu className={styles.menu} selectedKeys={[]} onClick={onMenuClick}>
      {menu && (
        <Menu.Item key="profile">
          <UserOutlined/>
          个人中心
        </Menu.Item>
      )}
      {menu && (
        <Menu.Item key="settings">
          <SettingOutlined/>
          个人设置
        </Menu.Item>
      )}
      {menu && <Menu.Divider/>}

      <Menu.Item key="logout">
        <LogoutOutlined/>
        退出登录
      </Menu.Item>
    </Menu>
  );
  return (
    <HeaderDropdown overlay={menuHeaderDropdown}>
      <span className={`${styles.action} ${styles.account}`}>
        <Avatar size="small" className={styles.avatar} src={currentUser?.avatar} icon={<UserOutlined/>} alt="avatar"/>
        <span className={`${styles.name} anticon`}>{currentUser?.username}</span>
      </span>
    </HeaderDropdown>
  );
};

export default AvatarDropdown;