import React, {useEffect, useState} from 'react';

import {Card, Form, message, Space, Spin} from "antd";
import {PageContainer} from "@ant-design/pro-layout";
import ProForm, {ProFormText} from '@ant-design/pro-form';

import {getErrorMessage} from "@/utils/utils";
import {Item} from "./data";
import {getCurrentUser, updateProfile} from "./service";

export default (): React.ReactNode => {
  const [form] = Form.useForm();
  const [currentUser, setCurrentUser] = useState<Item>();
  const [initialized, setInitialized] = useState<boolean>(false);

  const loading = (
    <Space size="middle">
      <Spin/>
    </Space>
  );

  /**
   * 更新节点
   * @param fields
   */
  const handleUpdate = async (fields: Item) => {
    const hide = message.loading('正在更新');

    try {
      await updateProfile({...fields});
      hide();
      message.success('更新成功');
      return true;
    } catch (error) {
      hide();
      message.error(getErrorMessage('更新失败，请重试！', error), 10);
      return false;
    }
  };

  useEffect(() => {
    (async () => {
      const response = await getCurrentUser();
      if (response?.success) {
        setCurrentUser(response.data);
      }
      setInitialized(true);
    })();
  }, []);

  if (!initialized) {
    return loading;
  }

  return (
    <PageContainer>
      <Card>
        <ProForm
          form={form}
          onFinish={async (values) => {
            handleUpdate(values);
          }}
          initialValues={currentUser}
        >
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
        </ProForm>
      </Card>
    </PageContainer>
  );
};
