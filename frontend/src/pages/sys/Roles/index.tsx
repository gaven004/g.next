import React from 'react';

import {ProColumns} from "@ant-design/pro-table";
import {ProFormSelect, ProFormText} from "@ant-design/pro-form";

import GenericPage from "@/pages/commons/General";
import type {TableListItem} from './data.d';

export default (): React.ReactNode => {
  const addActionUrl: string = "/api/sys/roles";
  const removeActionUrl: string = "/api/sys/roles/$batch";
  const updateActionUrl: string = "/api/sys/roles";
  const findActionUrl: string = "/api/sys/roles";

  const tableColumns: ProColumns<TableListItem>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
      width: '35%',
    },
    {
      title: '角色名称',
      dataIndex: 'name',
      sorter: true,
      width: '35%',
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueEnum: {
        'VALID': {text: '有效', status: 'Success'},
        'INVALID': {text: '无效', status: 'Error'},
      },
      align: 'center',
      width: '15%',
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
        name="name"
        label="角色名称"
        rules={[
          {
            required: true,
            message: '角色名称为必填项',
          },
        ]}
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
      tableColumns={tableColumns}
      fromChildren={fromChildren}
      initValue={initValue}
    />
  );
};
