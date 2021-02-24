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
