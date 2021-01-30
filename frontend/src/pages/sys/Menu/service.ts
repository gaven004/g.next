import request from 'umi-request';

export async function getParentOptions() {
  return await request('/api/sys/menu',
    {params: {sorter: {"parentId": "ascend", "order": "ascend", "id": "ascend"}}}
  )
    .then(response => {
      if (response.success) {
        // @ts-ignore
        return response.data.map(item =>
          ({value: item.id, label: `${item.id} - ${item.label}`})
        );
      }
      return [];
    }).catch(() => {
        return []
      }
    );
}

export async function getMenuTree() {
  return await request('/api/sys/menu/$tree')
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
