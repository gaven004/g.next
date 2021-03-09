import React, {useState} from 'react';
import {history, Link, useModel} from 'umi';

import {Alert, message} from 'antd';
import {CoffeeOutlined, LockTwoTone, MailOutlined, UserOutlined,} from '@ant-design/icons';
import ProForm, {ProFormCheckbox, ProFormText} from '@ant-design/pro-form';

import Footer from '@/components/Footer';
import {API} from "@/services/API";
import {accountLogin, LoginParamsType, resetPassword} from '@/services/login';
import styles from './index.less';

const AlertMessage: React.FC<{
  content: string;
}> = ({content}) => (
  <Alert
    style={{
      marginBottom: 24,
    }}
    message={content}
    type="error"
    showIcon
  />
);

/**
 * 此方法会跳转到 redirect 参数所在的位置
 */
const goto = () => {
  if (!history) return;
  setTimeout(() => {
    const {query} = history.location;
    const {redirect} = query as {
      redirect: string;
    };
    history.push(redirect || '/');
  }, 10);
};

const Login: React.FC<{}> = () => {
  const [submitting, setSubmitting] = useState(false);
  const [userLoginState, setUserLoginState] = useState<API.Response>({});
  const [type, setType] = useState<string>('login');
  const {initialState, setInitialState} = useModel('@@initialState');

  const doLogin = async (values: LoginParamsType) => {
    try {
      // 登录
      localStorage.removeItem("AuthorizationToken");
      const response = await accountLogin(values);

      if (response && response.success) {
        message.success('登录成功！');
        // @ts-ignore
        setInitialState({...initialState, currentUser: response.data});
        if (response?.data?.token) {
          localStorage.setItem("AuthorizationToken", response.data.token);
        }
        goto();
        return;
      }

      // 如果失败去设置用户错误信息
      setUserLoginState({...response, success: false});
    } catch (error) {
      message.error('登录失败，请重试！');
    }
  };

  const doResetPassword = async (values: LoginParamsType) => {
    try {
      const response = await resetPassword(values);

      if (response && response.success) {
        message.success('密码重置成功，请到电子邮箱查收新密码！');
        return;
      }
    } catch (error) {
      message.error('密码重置失败，请重试！');
    }
  };

  const handleSubmit = async (values: LoginParamsType) => {
    setSubmitting(true);

    if (type === 'login') {
      await doLogin(values);
    } else if (type === 'reset') {
      await doResetPassword(values);
    }

    setSubmitting(false);
  };

  const {success, errorMessage} = userLoginState;
  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <div className={styles.top}>
          <div className={styles.header}>
            <Link to="/">
              <img alt="logo" className={styles.logo} src="/logo.svg"/>
              <span className={styles.title}>G.Next</span>
            </Link>
          </div>
          <div className={styles.desc}>新一代业务管理系统，由心创造，不断进步！</div>
        </div>

        <div className={styles.main}>
          <ProForm
            initialValues={{
              autoLogin: true,
            }}
            submitter={{
              searchConfig: {
                submitText: type === 'login' ? '登录' : '重置',
              },
              render: (_, dom) => dom.pop(),
              submitButtonProps: {
                loading: submitting,
                size: 'large',
                style: {
                  width: '100%',
                },
              },
            }}
            onFinish={async (values) => {
              await handleSubmit(values);
            }}
          >
            {success != undefined && !success && type === 'login' && (
              <AlertMessage
                content={`登录失败，${errorMessage}`}
              />
            )}
            {success != undefined && !success && type === 'reset' && (
              <AlertMessage
                content={`密码重置失败，${errorMessage}`}
              />
            )}
            {type === 'login' && (
              <>
                <div className={styles.subtitle}>
                  <CoffeeOutlined className={styles.subtitleIcon}/>
                  请输入您的账户信息
                </div>
                <ProFormText
                  name="username"
                  placeholder="用户名"
                  fieldProps={{
                    size: 'large',
                    prefix: <UserOutlined className={styles.prefixIcon}/>,
                  }}
                  rules={[
                    {
                      required: true,
                      message: '用户名是必填项！',
                    },
                  ]}
                />
                <ProFormText.Password
                  name="password"
                  placeholder="密码"
                  fieldProps={{
                    size: 'large',
                    prefix: <LockTwoTone className={styles.prefixIcon}/>,
                  }}
                  rules={[
                    {
                      required: true,
                      message: '密码是必填项！',
                    },
                  ]}
                />
                <div
                  style={{
                    marginBottom: 24,
                  }}
                >
                  <ProFormCheckbox noStyle name="autoLogin">
                    自动登录
                  </ProFormCheckbox>
                  <a
                    style={{
                      float: 'right',
                    }}
                    onClick={() => {
                      setType('reset');
                    }}
                  >
                    忘记密码 ?
                  </a>
                </div>
              </>
            )}

            {type === 'reset' && (
              <>
                <div className={styles.subtitle}>
                  <CoffeeOutlined className={styles.subtitleIcon}/>
                  请输入您登记的邮箱
                </div>
                <ProFormText
                  name="email"
                  placeholder="邮箱"
                  fieldProps={{
                    size: 'large',
                    prefix: <MailOutlined className={styles.prefixIcon}/>,
                  }}
                  rules={[
                    {
                      required: true,
                      type: 'email',
                      message: '邮箱是必填项！',
                    },
                  ]}
                />
                <div
                  style={{
                    marginBottom: 24,
                  }}
                >
                  <ProFormCheckbox noStyle name="autoLogin" disabled>
                    自动登录
                  </ProFormCheckbox>
                  <a
                    style={{
                      float: 'right',
                    }}
                    onClick={() => {
                      setType('login');
                    }}
                  >
                    返回登录
                  </a>
                </div>
              </>
            )}
          </ProForm>
        </div>
      </div>
      <Footer/>
    </div>
  );
};

export default Login;
