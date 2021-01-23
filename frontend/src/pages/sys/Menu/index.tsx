import React from 'react';

import {Button} from "antd";
import {ProColumns} from "@ant-design/pro-table";
import ProForm, {ProFormDigit, ProFormSelect, ProFormText} from "@ant-design/pro-form";
import {FileSearchOutlined} from "@ant-design/icons";

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
      title: 'URL',
      dataIndex: 'url',
      ellipsis: true,
      sorter: true,
      width: '20%',
    },
    {
      title: '页面组件',
      dataIndex: 'component',
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

  const toolbarNodes = [
    <Button key="primary" onClick={() => {
      alert('预览');
    }}>
      <FileSearchOutlined/> 预览
    </Button>,
  ];

  const fromChildren = (
    <>
      <ProFormText
        name="id"
        label="ID"
        width="md"
        readonly
      />
      <ProForm.Group>
        <ProFormSelect
          name="parentId"
          label="父节点ID"
          width="md"
          request={getParentOptions}
          rules={[
            {
              required: true,
              message: '父节点ID为必填项',
            },
          ]}
        />
        <ProFormSelect
          name="status"
          label="状态"
          width="md"
          valueEnum={{
            'VALID': '有效',
            'INVALID': '无效',
          }}
        />
      </ProForm.Group>
      <ProForm.Group>
        <ProFormText
          name="label"
          label="菜单文本"
          width="md"
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
          width="md"
        />
      </ProForm.Group>
      <ProForm.Group>
        <ProFormText
          name="url"
          label="URL"
          width="md"
          rules={[
            {
              type: 'regexp',
              pattern: new RegExp('(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?'),
              message: 'URL必须合符规格',
            },
          ]}
        />
        <ProFormText
          name="component"
          label="页面组件"
          width="md"
          rules={[
            {
              type: 'regexp',
              pattern: new RegExp('(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?'),
              message: '页面组件定义必须合符规格',
            },
          ]}
        />
      </ProForm.Group>
      <ProForm.Group>
        <ProFormText
          name="icon"
          label="图标"
          width="md"
        />
        <ProFormDigit
          name="order"
          label="排序"
          width="md"
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
      </ProForm.Group>
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
      toolbarNodes={toolbarNodes}
      fromChildren={fromChildren}
      initValue={initValue}
      modalWidth='740px'
    />
  );
};
