import { httpClient } from './httpClient';

export const getAddresses = async () => (await httpClient.get('/addresses')).data;
export const createAddress = async (payload) => (await httpClient.post('/addresses', payload)).data;
export const updateAddress = async (id, payload) => (await httpClient.put(`/addresses/${id}`, payload)).data;
export const deleteAddress = async (id) => httpClient.delete(`/addresses/${id}`);
export const setDefaultAddress = async (id) => (await httpClient.patch(`/addresses/${id}/default`)).data;
