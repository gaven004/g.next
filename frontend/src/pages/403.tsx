import {Button, Result} from 'antd';
import React from 'react';
import {Link} from 'umi';

const NoFoundPage: React.FC<{}> = () => (
  <Result
    status="403"
    title="403"
    subTitle="抱歉，没有您权限访问此页面！"
    extra={
      <Link to="/">
        <Button type="primary">返回首页</Button>
      </Link>
    }
  />
);

export default NoFoundPage;
