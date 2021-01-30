import React from "react";

import {IconBaseProps} from "react-icons/lib/cjs/iconBase";

import * as ai from 'react-icons/ai';
import * as bs from 'react-icons/bs';
import * as fa from 'react-icons/fa';
import * as io from 'react-icons/io';
import * as io5 from 'react-icons/io5';
import * as md from 'react-icons/md';

export const buildIcon = (name: string, props?: IconBaseProps): JSX.Element => {
  let fn;

  console.log(name);

  if (name.startsWith("Io5")) {
    fn = io5[name];
  } else if (name.startsWith("Io")) {
    fn = io[name];
  } else if (name.startsWith("Ai")) {
    fn = ai[name];
  } else if (name.startsWith("Bs")) {
    fn = bs[name];
  } else if (name.startsWith("Fa")) {
    fn = fa[name];
  } else if (name.startsWith("Md")) {
    fn = md[name];
  }

  if (typeof fn === "function") {
    // 仿Ant Design Icon的样式
    return React.createElement('span',
      {className: 'anticon'},
      fn.call(null, {viewBox: "64 64 896 896", ...props}));
  }

  return React.createElement('span');
}
