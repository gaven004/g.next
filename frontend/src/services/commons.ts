import request from 'umi-request';

export async function getStatusOptions() {
  return await request('/api/enums/Status')
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
