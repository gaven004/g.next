import request from 'umi-request';

export async function getActionMethodOptions() {
  return await request('/api/enums/ActionMethod')
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

export async function getOptions() {
  return await request('/api/sys/action/$options')
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

export async function findEntities(url: string, params?: any) {
  return request(url, {
    params,
  }).then(
    response => {
      if (response && response.success && response.data) {
        response.data.forEach(
          // @ts-ignore
          record => {
            if (record.authorities) {
              let authorities: [] = [];
              record.authorities.forEach(
                // @ts-ignore
                action => authorities.push(action.id)
              );
              record.authorities = authorities;
            }
          }
        );
      }
      return response;
    }
  );
}
