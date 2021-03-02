import {request} from 'umi';

import {API} from "@/services/API";

export interface LoginParamsType {
  username?: string;
  password?: string;
  mobile?: string;
  captcha?: string;
  type?: string;
}

export async function accountLogin(params: LoginParamsType) {
  return request<API.Response>('/api/login', {
    method: 'POST',
    data: params,
  });
}
