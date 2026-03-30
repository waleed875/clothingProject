import { httpClient } from './httpClient';

export const fetchCategories = async () => {
  const { data } = await httpClient.get('/public/categories');
  return data;
};
