import { httpClient } from './httpClient';

export const fetchProducts = async (category) => {
  const { data } = await httpClient.get('/public/products', { params: category ? { category } : {} });
  return data;
};

export const fetchProductDetails = async (slug) => {
  const { data } = await httpClient.get(`/public/products/${slug}`);
  return data;
};

export const fetchProductVariants = async (productId) => {
  const { data } = await httpClient.get(`/products/${productId}/variants`);
  return data;
};

export const fetchProductImages = async (productId) => {
  const { data } = await httpClient.get(`/products/${productId}/images`);
  return data;
};
