import React, {useRef, useState} from 'react';

import {Button, message, Space} from "antd";
import type {FormInstance} from "antd/lib/form/Form";
import {PageContainer} from '@ant-design/pro-layout';
import ProTable, {ActionType, ProColumns} from '@ant-design/pro-table';
import {ModalForm} from "@ant-design/pro-form";
import {ExclamationCircleOutlined, PlusOutlined} from '@ant-design/icons';

import {confirm, getErrorMessage} from '@/utils/utils';
import {addEntity, findEntities, removeEntities, updateEntity} from "./service";

export declare type GenericPageProps = {
  addActionUrl: string;
  removeActionUrl: string;
  updateActionUrl: string;
  findActionUrl: string;
  addService?: (url: string, params?: any) => Promise<any>;
  findService?: (url: string, params?: any) => Promise<any>;
  removeService?: (url: string, params?: any) => Promise<any>;
  updateService?: (url: string, params?: any) => Promise<any>;
  addHandler?: () => any;
  removeHandler?: () => any;
  updateHandler?: () => any;
  tableColumns: ProColumns[];
  rowKey?: string | (() => string);
  toolbarNodes?: React.ReactNode[];
  fromChildren: React.ReactNode;
  initValue?: {};
  modalWidth?: string | number;
};

const GenericPage: React.FC<GenericPageProps> = (props: GenericPageProps) => {
  const {
    addActionUrl,
    removeActionUrl,
    updateActionUrl,
    findActionUrl,
    addService,
    findService,
    removeService,
    updateService,
    addHandler,
    removeHandler,
    updateHandler,
    tableColumns,
    rowKey,
    toolbarNodes,
    fromChildren,
    initValue,
    modalWidth
  } = props;

  const doAdd = addService || addEntity;
  const doFind = findService || findEntities;
  const doRemove = removeService || removeEntities;
  const doUpdate = updateService || updateEntity;

  const actionRef = useRef<ActionType>();
  const formRef = useRef<FormInstance>();
  const [selectedRowsState, setSelectedRows] = useState<any[]>([]);
  const [modalVisible, handleModalVisible] = useState<boolean>(false);
  const [isNew, setNew] = useState<boolean>(false);

  /**
   * 添加节点
   * @param fields
   */
  const handleAdd = addHandler || (async (fields: any) => {
    const hide = message.loading('正在添加');

    try {
      await doAdd(addActionUrl, {...fields});
      hide();
      message.success('添加成功');
      return true;
    } catch (error) {
      hide();
      message.error(getErrorMessage('添加失败，请重试！', error), 10);
      return false;
    }
  });

  /**
   * 更新节点
   * @param fields
   */
  const handleUpdate = updateHandler || (async (fields: any) => {
    const hide = message.loading('正在更新');

    try {
      await doUpdate(updateActionUrl, {...fields});
      hide();
      message.success('更新成功');
      return true;
    } catch (error) {
      hide();
      message.error(getErrorMessage('更新失败，请重试！', error), 10);
      return false;
    }
  });

  /**
   *  删除节点
   * @param selectedRows
   */
  const handleRemove = removeHandler || (async (selectedRows: any[]) => {
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
      await doRemove(
        removeActionUrl,
        selectedRows.map((row) => row.id)
      );
      hide();
      message.success('删除成功，即将刷新');
      return true;
    } catch (error) {
      hide();
      message.error(getErrorMessage('删除失败，请重试！', error), 10);
      return false;
    }
  });

  const onRemoveClick = async (selectedRows: any[]) => {
    const result = await handleRemove(selectedRows);
    if (result) {
      setSelectedRows([]);
      actionRef.current?.reloadAndRest?.();
    }
  };

  const optionColumn: ProColumns[] = [
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      align: 'center',
      render: (_, record) => [
        <a onClick={() => {
          setNew(false);
          formRef.current?.resetFields();
          formRef.current?.setFieldsValue(record);
          handleModalVisible(true);
        }}>
          编辑
        </a>,
        <a onClick={async () => {
          await onRemoveClick([record]);
        }}>
          删除
        </a>,
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable
        actionRef={actionRef}
        request={(params, sorter, filter) =>
          doFind(findActionUrl, {...params, sorter, filter})}
        columns={tableColumns.concat(optionColumn)}
        bordered
        rowKey={rowKey || "id"}
        rowSelection={{
          onChange: (_, selectedRows) => setSelectedRows(selectedRows),
        }}
        toolBarRender={() => [
          <Button type="primary" key="primary" onClick={() => {
            setNew(true);
            formRef.current?.resetFields();
            if (initValue) {
              formRef.current?.setFieldsValue(initValue);
            }
            handleModalVisible(true)
          }}>
            <PlusOutlined/> 新建
          </Button>,
          // @ts-ignore
        ].concat(toolbarNodes || [])}
        tableAlertOptionRender={({onCleanSelected}) => {
          return (
            <Space size={16}>
              <a onClick={() => onRemoveClick(selectedRowsState)}>批量删除</a>
              <a onClick={onCleanSelected}>取消选择</a>
            </Space>
          );
        }}
      />
      <ModalForm
        formRef={formRef}
        name="form"
        title={isNew ? "新建" : "修改"}
        width={modalWidth || "400px"}
        visible={modalVisible}
        onVisibleChange={handleModalVisible}
        onFinish={async (value) => {
          const success = isNew ?
            await handleAdd(value) :
            await handleUpdate(value);

          if (success) {
            handleModalVisible(false);

            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      >
        {fromChildren}
      </ModalForm>
    </PageContainer>
  );
};

export default GenericPage;
