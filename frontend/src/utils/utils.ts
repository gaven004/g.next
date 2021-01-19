/* eslint no-useless-escape:0 import/prefer-default-export:0 */
import {ModalFuncProps} from "antd/lib/modal/Modal";
import {Modal} from "antd";
import {ResponseError} from "umi-request";

const reg = /(((^https?:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+(?::\d+)?|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)$/;

export const isUrl = (path: string): boolean => reg.test(path);

export const confirm = async (config: ModalFuncProps): Promise<boolean> => {
  const promise = new Promise((resolve, reject) => {
    Modal.confirm({
      ...config,
      onOk: () => {
        resolve(true);
      },
      onCancel: () => {
        reject();
      },
    });
  });

  let result: boolean = false;
  await promise.then(
    () => {
      result = true;
    }
  ).catch(
    () => {
      result = false;
    }
  );

  return result;
};

export const getErrorMessage = (title: string, error: ResponseError): string => {
  return `${title} 详细信息：${error.data && error.data.errorMessage ? error.data.errorMessage : error}`;
}

export const isAntDesignPro = (): boolean => {
  if (ANT_DESIGN_PRO_ONLY_DO_NOT_USE_IN_YOUR_PRODUCTION === 'site') {
    return true;
  }
  return window.location.hostname === 'preview.pro.ant.design';
};

// 给官方演示站点用，用于关闭真实开发环境不需要使用的特性
export const isAntDesignProOrDev = (): boolean => {
  const {NODE_ENV} = process.env;
  if (NODE_ENV === 'development') {
    return true;
  }
  return isAntDesignPro();
};
