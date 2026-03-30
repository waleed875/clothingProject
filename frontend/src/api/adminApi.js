import { httpClient } from './httpClient';

export const adminCreateCategory = async (payload) => (await httpClient.post('/admin/categories', payload)).data;
export const adminUpdateCategory = async (id, payload) => (await httpClient.put(`/admin/categories/${id}`, payload)).data;
export const adminCategoryStatus = async (id, active) => (await httpClient.patch(`/admin/categories/${id}/status`, { active })).data;

export const adminCreateProduct = async (payload) => (await httpClient.post('/admin/products', payload)).data;
export const adminUpdateProduct = async (id, payload) => (await httpClient.put(`/admin/products/${id}`, payload)).data;
export const adminProductStatus = async (id, status) => (await httpClient.patch(`/admin/products/${id}/status`, { status })).data;

export const getProductVariants = async (productId) => (await httpClient.get(`/products/${productId}/variants`)).data;
export const adminCreateVariant = async (productId, payload) => (await httpClient.post(`/admin/products/${productId}/variants`, payload)).data;
export const adminUpdateVariant = async (variantId, payload) => (await httpClient.put(`/admin/variants/${variantId}`, payload)).data;
export const adminVariantStatus = async (variantId, status) => (await httpClient.patch(`/admin/variants/${variantId}/status`, { status })).data;

export const adminListInventory = async () => (await httpClient.get('/admin/inventory')).data;
export const adminGetVariantInventory = async (variantId) => (await httpClient.get(`/admin/variants/${variantId}/inventory`)).data;
export const adminUpdateVariantInventory = async (variantId, quantity) => (await httpClient.put(`/admin/variants/${variantId}/inventory`, { quantity })).data;
