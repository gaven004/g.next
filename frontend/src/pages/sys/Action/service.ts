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
