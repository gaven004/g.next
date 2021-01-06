import request from 'umi-request';
import {TableListParams} from './data.d';

export async function findProperties(params?: TableListParams) {
  return request('/api/sys/property/categories', {
    params,
  });
}

export async function removeProperties(params: string[]) {
  return request('/api/sys/property/categories/$batch', {
    method: 'DELETE',
    data: {
      ...params,
    },
  });
}

export async function addProperty(params: TableListParams) {
  return request('/api/sys/property/categories', {
    method: 'POST',
    data: {
      ...params,
    },
  });
}

export async function updateProperty(params: TableListParams) {
  return request('/api/sys/property/categories', {
    method: 'PUT',
    data: {
      ...params,
    },
  });
}
