import request from 'umi-request';
import {TableListParams} from './data.d';

export async function getOptions() {
  return await request('/api/sys/property/categories/$options')
    .then(response => {
      if (response.success) {
        return response.data;
      }
      return [];
    }).catch(() => {
        return []
      }
    );
}

export async function findCategories(params?: TableListParams) {
  return request('/api/sys/property/categories', {
    params,
  });
}

export async function removeCategories(params: string[]) {
  return request('/api/sys/property/categories/$batch', {
    method: 'DELETE',
    data: params,
  });
}

export async function addCategory(params: TableListParams) {
  return request('/api/sys/property/categories', {
    method: 'POST',
    data: {
      ...params,
    },
  });
}

export async function updateCategory(params: TableListParams) {
  return request('/api/sys/property/categories', {
    method: 'PUT',
    data: {
      ...params,
    },
  });
}
