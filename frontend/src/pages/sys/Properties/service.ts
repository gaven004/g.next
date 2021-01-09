import request from 'umi-request';
import {TableListItemKey, TableListParams} from './data.d';

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

export async function addProperty(params: TableListParams) {
  return request('/api/sys/properties', {
    method: 'POST',
    data: {
      ...params,
    },
  });
}

export async function updateProperty(params: TableListParams) {
  return request('/api/sys/properties', {
    method: 'PUT',
    data: {
      ...params,
    },
  });
}
