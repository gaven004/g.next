import React, {useRef, useState} from 'react';
import {Button, message, Space} from "antd";
import {PageContainer} from '@ant-design/pro-layout';
import ProTable, {ActionType, ProColumns} from '@ant-design/pro-table';
import {ExclamationCircleOutlined, PlusOutlined} from '@ant-design/icons';

import {confirm} from '../../../utils/utils';
import type {TableListItem} from './data.d';
import {findProperties, removeProperties} from "./service";

/**
 *  删除节点
 * @param selectedRows
 */
const handleRemove = async (selectedRows: TableListItem[]) => {
  if (!selectedRows || selectedRows.length === 0) {
    message.success('请先选择需要删除的记录');
    return true;
  }

  const result = await confirm({
    title: 'Confirm',
    icon: <ExclamationCircleOutlined/>,
    content: '确认删除所选记录？',
    okText: '确认',
    okType: 'danger',
    cancelText: '取消',
  });

  if (!result) {
    return true;
  }

  const hide = message.loading('正在删除');
  try {
    await removeProperties(
      selectedRows.map((row) => {
        return {category: row.category, name: row.name};
      }),
    );
    hide();
    message.success('删除成功，即将刷新');
    return true;
  } catch (error) {
    hide();
    message.error(`删除失败，请重试！详细信息：${error}`);
    return false;
  }
};

export default (): React.ReactNode => {
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<TableListItem>();
  const [selectedRowsState, setSelectedRows] = useState<TableListItem[]>([]);

  const onRemoveClick = async (selectedRows: TableListItem[]) => {
    console.log('selectedRowsState in onRemoveClick', selectedRows);
    const result = await handleRemove(selectedRows);
    if (result) {
      setSelectedRows([]);
      actionRef.current?.reloadAndRest?.();
    }
  };

  const columns: ProColumns<TableListItem>[] = [
    {
      title: '参数分类',
      dataIndex: 'category',
      sorter: true,
      width: '12%',
    },
    {
      title: '参数名称',
      dataIndex: 'name',
      sorter: true,
      width: '12%',
    },
    {
      title: '参数值',
      dataIndex: 'value',
      search: false,
      width: '15%',
    },
    {
      title: '扩展属性',
      dataIndex: 'properties',
      search: false,
      width: '20%',
    },
    {
      title: '排序',
      dataIndex: 'sortOrder',
      search: false,
      sorter: true,
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
      width: '7%',
    },
    {
      title: '说明',
      dataIndex: 'note',
      search: false,
      width: '15%',
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      align: 'center',
      width: '12%',
      render: (_, record) => [
        <a onClick={() => {
          setCurrentRow(record);
          // setSelectedRows()
        }}>
          编辑
        </a>,
        <a onClick={
          async () => {
            await onRemoveClick([record]);
          }
        }>
          删除
        </a>,
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<TableListItem>
        actionRef={actionRef}
        request={(params, sorter, filter) => findProperties({...params, sorter, filter})}
        columns={columns}
        rowKey={(record) => `${record.category}:${record.name}`}
        rowSelection={{
          onChange: (_, selectedRows) => setSelectedRows(selectedRows),
        }}
        toolBarRender={() => [
          <Button type="primary" key="primary" onClick={() => {
          }}>
            <PlusOutlined/> 新建
          </Button>,
        ]}
        tableAlertOptionRender={({onCleanSelected}) => {
          return (
            <Space size={16}>
              <a onClick={() => onRemoveClick(selectedRowsState)}>批量删除</a>
              <a onClick={onCleanSelected}>取消选择</a>
            </Space>
          );
        }}
      />
    </PageContainer>
  );
};
