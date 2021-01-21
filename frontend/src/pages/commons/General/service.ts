import request from 'umi-request';

export async function findEntities(url: string, params?: any) {
  return request(url, {
    params,
  });
}

export async function removeEntities(url: string, params: any[]) {
  return request(url, {
    method: 'DELETE',
    data: params,
  });
}

export async function addEntity(url: string, params: any) {
  return request(url, {
    method: 'POST',
    data: {
      ...params,
    },
  });
}

export async function updateEntity(url: string, params: any) {
  return request(url, {
    method: 'PUT',
    data: {
      ...params,
    },
  });
}
