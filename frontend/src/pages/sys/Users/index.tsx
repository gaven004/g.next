import React from 'react';

import {ProColumns} from "@ant-design/pro-table";
import {ProFormSelect, ProFormText} from "@ant-design/pro-form";

import GenericPage from "@/pages/commons/General";
import {getOptions} from "@/pages/sys/Roles/service";
import type {TableListItem} from './data.d';
import {findEntities} from './service';

export default (): React.ReactNode => {
  const addActionUrl: string = "/api/sys/users";
  const removeActionUrl: string = "/api/sys/users/$batch";
  const updateActionUrl: string = "/api/sys/users";
  const findActionUrl: string = "/api/sys/users";

  const tableColumns: ProColumns<TableListItem>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
    },
    {
      title: '用户账号',
      dataIndex: 'account',
      sorter: true,
    },
    {
      title: '用户名称',
      dataIndex: 'username',
      sorter: true,
    },
    {
      title: '邮箱',
      dataIndex: 'email',
      sorter: true,
    },
    {
      title: '所属用户组',
      dataIndex: 'roles',
      valueType: 'select',
      request: getOptions,
      params: {},
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueEnum: {
        'VALID': {text: '有效', status: 'Success'},
        'INVALID': {text: '无效', status: 'Error'},
      },
      align: 'center',
    },
  ];

  const fromChildren = (
    <>
      <ProFormText
        name="id"
        label="ID"
        hidden={true}
        readonly={true}
      />
      <ProFormText
        name="account"
        label="用户账号"
        rules={[
          {
            required: true,
            message: '用户账号为必填项',
          },
        ]}
      />
      <ProFormText
        name="username"
        label="用户名称"
        rules={[
          {
            required: true,
            message: '用户名称为必填项',
          },
        ]}
      />
      <ProFormText
        name="email"
        label="邮箱"
        rules={[
          {
            required: true,
            type: "email",
            message: '邮箱为必填项，且格式正确',
          },
        ]}
      />
      <ProFormSelect
        name="roles"
        label="所属用户组"
        request={getOptions}
        mode="multiple"
      />
      <ProFormSelect
        name="status"
        label="状态"
        valueEnum={{
          'VALID': '有效',
          'INVALID': '无效',
        }}
      />
    </>
  );

  const initValue = {'status': 'VALID'};

  return (
    <GenericPage
      addActionUrl={addActionUrl}
      removeActionUrl={removeActionUrl}
      updateActionUrl={updateActionUrl}
      findActionUrl={findActionUrl}
      findService={findEntities}
      tableColumns={tableColumns}
      fromChildren={fromChildren}
      initValue={initValue}
      modalWidth={'500px'}
    />
  );
};
