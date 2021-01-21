import React from 'react';

import {ProColumns} from "@ant-design/pro-table";
import {ProFormSelect, ProFormText} from "@ant-design/pro-form";

import GenericPage from "@/pages/commons/General";
import {getStatusOptions} from "@/services/commons"
import type {TableListItem} from './data.d';
import {getActionMethodOptions} from './service'

export default (): React.ReactNode => {
  const addActionUrl: string = "/api/sys/action";
  const removeActionUrl: string = "/api/sys/action/$batch";
  const updateActionUrl: string = "/api/sys/action";
  const findActionUrl: string = "/api/sys/action";

  const tableColumns: ProColumns<TableListItem>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
      sorter: true,
      width: '10%',
    },
    {
      title: '资源',
      dataIndex: 'resource',
      sorter: true,
      width: '35%',
    },
    {
      title: '请求方法',
      dataIndex: 'method',
      valueType: 'select',
      request: getActionMethodOptions,
      params: {},
      sorter: true,
      width: '10%',
    },
    {
      title: '描述',
      dataIndex: 'description',
      sorter: true,
      width: '20%',
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueType: 'select',
      request: getStatusOptions,
      params: {},
      align: 'center',
      width: '10%',
    },
  ];

  const fromChildren = (
    <>
      <ProFormText
        name="id"
        label="ID"
        // @ts-ignore
        hidden={() => isNew}
        readonly={true}
      />
      <ProFormText
        name="resource"
        label="资源"
        rules={[
          {
            required: true,
            message: '资源为必填项',
          },
        ]}
      />
      <ProFormSelect
        name="method"
        label="请求方法"
        request={getActionMethodOptions}
        rules={[
          {
            required: true,
            message: '请求方法为必填项',
          },
        ]}
      />
      <ProFormText
        name="description"
        label="描述"
      />
      <ProFormSelect
        name="status"
        label="状态"
        request={getStatusOptions}
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
      tableColumns={tableColumns}
      fromChildren={fromChildren}
      initValue={initValue}
    />
  );
};
