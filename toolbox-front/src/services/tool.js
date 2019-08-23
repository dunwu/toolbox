import request from '@/utils/request';

export async function encryptReq(params) {
  return request('/api/crypto/encrypt', {
    method: 'POST',
    data: {
      ...params,
    },
  });
}

export async function decryptReq(params) {
  return request('/api/crypto/decrypt', {
    method: 'POST',
    data: {
      ...params,
    },
  });
}
