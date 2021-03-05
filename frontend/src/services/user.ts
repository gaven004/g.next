import {request} from 'umi';

import {API} from "@/services/API";

export async function queryCurrent() {
  return request<API.Response>('/api/refresh-token', {
    method: 'POST',
  });
}

export async function queryMenu() {
  return request<API.Response>('/api/sys/menu/$menu');
}

export async function queryNotices(): Promise<any> {
  return request<{ data: API.NoticeIconData[] }>('/api/notices');
}
