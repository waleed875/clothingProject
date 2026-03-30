import { httpClient } from './httpClient';

export const getCart = async () => (await httpClient.get('/cart')).data;
export const addCartItem = async (payload) => (await httpClient.post('/cart/items', payload)).data;
export const updateCartItem = async (itemId, payload) => (await httpClient.put(`/cart/items/${itemId}`, payload)).data;
export const deleteCartItem = async (itemId) => (await httpClient.delete(`/cart/items/${itemId}`)).data;
export const clearCart = async () => httpClient.delete('/cart/clear');
