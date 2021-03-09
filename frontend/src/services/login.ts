import {request} from 'umi';

import {API} from "@/services/API";

export interface LoginParamsType {
  username?: string;
  password?: string;
  email?: string;
  type?: string;
}

export async function accountLogin(params: LoginParamsType) {
  return request<API.Response>('/api/login', {
    method: 'POST',
    data: params,
  });
}

export async function resetPassword(params: LoginParamsType) {
  return request<API.Response>('/api/sys/users/reset-password', {
    method: 'PUT',
    data: params,
  });
}
