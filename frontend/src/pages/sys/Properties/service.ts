import request from 'umi-request';
import {TableListItem, TableListItemKey, TableListParams} from './data.d';

export async function findProperties(params?: TableListParams) {
  return request('/api/sys/properties', {
    params,
  });
}

export async function removeProperties(params: TableListItemKey[]) {
  return request('/api/sys/properties/$batch', {
    method: 'DELETE',
    data: {
      ...params,
    },
  });
}

export async function addRule(params: TableListParams) {
  return request('/api/rule', {
    method: 'POST',
    data: {
      ...params,
      method: 'post',
    },
  });
}

export async function updateRule(params: TableListParams) {
  return request('/api/rule', {
    method: 'POST',
    data: {
      ...params,
      method: 'update',
    },
  });
}
