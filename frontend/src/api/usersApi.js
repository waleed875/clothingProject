import { httpClient } from './httpClient';

export const getUserProfile = async () => {
  const { data } = await httpClient.get('/users/me');
  return data;
};

export const updateUserProfile = async (payload) => {
  const { data } = await httpClient.put('/users/me', payload);
  return data;
};
