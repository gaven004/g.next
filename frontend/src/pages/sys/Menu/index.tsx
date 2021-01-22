import React from 'react';

import {ProColumns} from "@ant-design/pro-table";
import {ProFormDigit, ProFormSelect, ProFormText} from "@ant-design/pro-form";

import GenericPage from "@/pages/commons/General";
import type {TableListItem} from './data.d';
import {getParentOptions} from "./service";

export default (): React.ReactNode => {
  const addActionUrl: string = "/api/sys/menu";
  const removeActionUrl: string = "/api/sys/menu/$batch";
  const updateActionUrl: string = "/api/sys/menu";
  const findActionUrl: string = "/api/sys/menu";

  const tableColumns: ProColumns<TableListItem>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
      ellipsis: true,
      sorter: true,
      width: '10%',
    },
    {
      title: '父节点ID',
      dataIndex: 'parentId',
      ellipsis: true,
      sorter: true,
      width: '10%',
    },
    {
      title: '菜单文本',
      dataIndex: 'label',
      sorter: true,
      width: '12%',
    },
    {
      title: '提示',
      dataIndex: 'title',
      search: false,
      width: '12%',
    },
    {
      title: '图标',
      dataIndex: 'icon',
      sorter: true,
      search: false,
      align: 'center',
      width: '7%',
    },
    {
      title: 'URL',
      dataIndex: 'url',
      ellipsis: true,
      sorter: true,
      width: '20%',
    },
    {
      title: '排序',
      dataIndex: 'order',
      sorter: true,
      search: false,
      align: 'center',
      width: '7%',
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueEnum: {
        'VALID': {text: '有效', status: 'Success'},
        'INVALID': {text: '无效', status: 'Error'},
      },
      align: 'center',
      width: '8%',
    },
  ];

  const fromChildren = (
    <>
      <ProFormText
        name="id"
        label="ID"
        readonly={true}
      />
      <ProFormSelect
        name="parentId"
        label="父节点ID"
        request={getParentOptions}
        rules={[
          {
            required: true,
            message: '父节点ID为必填项',
          },
        ]}
      />
      <ProFormText
        name="label"
        label="菜单文本"
        rules={[
          {
            required: true,
            message: '菜单文本为必填项',
          },
        ]}
      />
      <ProFormText
        name="title"
        label="提示"
      />
      <ProFormText
        name="icon"
        label="图标"
      />
      <ProFormText
        name="url"
        label="URL"
        rules={[
          {
            type: 'regexp',
            pattern: new RegExp('(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?'),
            message: 'URL必须合符规格',
          },
        ]}
      />
      <ProFormDigit
        name="order"
        label="排序"
        tooltip="按升序排列"
        min={0}
        fieldProps={{precision: 0}}
        rules={[
          {
            type: "number",
            min: 0,
            message: '排序为自然数',
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

  const initValue = {'parentId': '0', 'status': 'VALID'};

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
