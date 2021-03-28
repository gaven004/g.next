import request from 'umi-request';
import {Item} from './data.d';

const findActionUrl: string = "/api/sys/users/current-user";
const updateActionUrl: string = "/api/sys/users/current-user";

export async function getCurrentUser() {
  return request(findActionUrl);
}

export async function updateProfile(params: Item) {
  return request(updateActionUrl, {
    method: 'PUT',
    data: {
      ...params,
    },
  });
}
