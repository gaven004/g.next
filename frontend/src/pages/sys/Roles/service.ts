import request from 'umi-request';

export async function getOptions() {
  return await request('/api/sys/roles/$options')
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
