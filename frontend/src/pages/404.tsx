import {Button, Result} from 'antd';
import React from 'react';
import {Link} from 'umi';

const NoFoundPage: React.FC<{}> = () => (
  <Result
    status="404"
    title="404"
    subTitle="抱歉，没有您访问的页面！"
    extra={
      <Link to="/">
        <Button type="primary">返回首页</Button>
      </Link>
    }
  />
);

export default NoFoundPage;
