import React, {useRef, useState} from 'react';

import {Button, message, Space} from "antd";
import {FormInstance} from "antd/lib/form/Form";
import {PageContainer} from '@ant-design/pro-layout';
import ProTable, {ActionType, ProColumns} from '@ant-design/pro-table';
import {ExclamationCircleOutlined, PlusOutlined} from '@ant-design/icons';
import {ModalForm, ProFormDigit, ProFormSelect, ProFormText, ProFormTextArea} from "@ant-design/pro-form";

import {confirm, getErrorMessage} from '@/utils/utils';
import type {TableListItem} from './data.d';
import {addProperty, findProperties, removeProperties, updateProperty} from "./service";
import {getOptions} from "@/pages/sys/PropertyCategories/service";

/**
 * 添加节点
 * @param fields
 */
const handleAdd = async (fields: TableListItem) => {
  const hide = message.loading('正在添加');

  try {
    await addProperty({...fields});
    hide();
    message.success('添加成功');
    return true;
  } catch (error) {
    hide();
    message.error(getErrorMessage('添加失败，请重试！', error), 10);
    return false;
  }
};

/**
 * 更新节点
 * @param fields
 */
const handleUpdate = async (fields: TableListItem) => {
  const hide = message.loading('正在更新');

  try {
    await updateProperty({...fields});
    hide();
    message.success('更新成功');
    return true;
  } catch (error) {
    hide();
    message.error(getErrorMessage('更新失败，请重试！', error), 10);
    return false;
  }
};

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
    message.error(getErrorMessage('删除失败，请重试！', error), 10);
    return false;
  }
};

export default (): React.ReactNode => {
  const actionRef = useRef<ActionType>();
  const formRef = useRef<FormInstance>();
  const [selectedRowsState, setSelectedRows] = useState<TableListItem[]>([]);
  const [modalVisible, handleModalVisible] = useState<boolean>(false);
  const [isNew, setNew] = useState<boolean>(false);

  const onRemoveClick = async (selectedRows: TableListItem[]) => {
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
      valueType: 'select',
      // @ts-ignore
      request: getOptions,
      params: {},
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
      <ProTable<TableListItem>
        actionRef={actionRef}
        request={(params, sorter, filter) => findProperties({...params, sorter, filter})}
        columns={columns}
        bordered
        rowKey={(record) => `${record.category}.${record.name}`}
        rowSelection={{
          onChange: (_, selectedRows) => setSelectedRows(selectedRows),
        }}
        toolBarRender={() => [
          <Button type="primary" key="primary" onClick={() => {
            setNew(true);
            formRef.current?.resetFields();
            handleModalVisible(true)
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
      <ModalForm
        formRef={formRef}
        title={isNew ? "新建" : "修改"}
        width="500px"
        layout="horizontal"
        labelCol={{span: 6}}
        visible={modalVisible}
        initialValues={{'status': 'VALID'}}
        onVisibleChange={handleModalVisible}
        onFinish={async (value) => {
          const success = isNew ?
            await handleAdd(value as TableListItem) :
            await handleUpdate(value as TableListItem);

          if (success) {
            handleModalVisible(false);

            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      >
        <ProFormSelect
          name="category"
          label="参数分类"
          request={getOptions}
          rules={[
            {
              required: true,
              message: '分类为必填项',
            },
          ]}
        />
        <ProFormText
          name="name"
          label="参数名称"
          rules={[
            {
              required: true,
              message: '名称为必填项',
            },
          ]}
        />
        <ProFormText
          name="value"
          label="参数值"
          rules={[
            {
              required: true,
              message: '参数值为必填项',
            },
          ]}
        />
        <ProFormTextArea
          name="properties"
          label="扩展属性"
        />
        <ProFormDigit
          name="sortOrder"
          label="排序"
          fieldProps={{precision: 0}}
        />
        <ProFormSelect
          name="status"
          label="状态"
          valueEnum={{
            'VALID': '有效',
            'INVALID': '无效',
          }}
        />
        <ProFormTextArea
          name="note"
          label="说明"
        />
      </ModalForm>
    </PageContainer>
  );
};
