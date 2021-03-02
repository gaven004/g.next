import {Button, Result} from 'antd';
import React from 'react';
import {Link} from 'umi';

const NoFoundPage: React.FC<{}> = () => (
  <Result
    status="500"
    title="500"
    subTitle="抱歉，系统出现故障，请稍后重试，或联系系统管理员！"
    extra={
      <Link to="/">
        <Button type="primary">返回首页</Button>
      </Link>
    }
  />
);

export default NoFoundPage;
