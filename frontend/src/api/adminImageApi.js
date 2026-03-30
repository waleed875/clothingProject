import { httpClient } from './httpClient';

export const addProductImage = async (productId, payload) => (await httpClient.post(`/admin/products/${productId}/images`, payload)).data;
export const addVariantImage = async (variantId, payload) => (await httpClient.post(`/admin/variants/${variantId}/images`, payload)).data;
export const deleteImage = async (imageId) => httpClient.delete(`/admin/images/${imageId}`);
export const setPrimaryImage = async (imageId, isPrimary) => (await httpClient.patch(`/admin/images/${imageId}/primary`, { isPrimary })).data;
export const updateImageSortOrder = async (imageId, sortOrder) => (await httpClient.patch(`/admin/images/${imageId}/sort-order`, { sortOrder })).data;
