import { httpClient } from './httpClient';

export const createOrder = async (payload) => (await httpClient.post('/orders', payload)).data;
export const getMyOrders = async () => (await httpClient.get('/orders/my')).data;
export const getOrderDetails = async (orderId) => (await httpClient.get(`/orders/${orderId}`)).data;
