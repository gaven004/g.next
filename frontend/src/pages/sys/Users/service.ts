import request from 'umi-request';

export async function findEntities(url: string, params?: any) {
  return request(url, {
    params,
  }).then(
    response => {
      if (response && response.success && response.data) {
        response.data.forEach(
          // @ts-ignore
          record => {
            if (record.roles) {
              let roles: [] = [];
              record.roles.forEach(
                // @ts-ignore
                role => roles.push(role.id)
              );
              record.roles = roles;
            }
          }
        );
      }
      return response;
    }
  );
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
