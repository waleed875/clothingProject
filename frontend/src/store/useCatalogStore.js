import { create } from 'zustand';
import { fetchCategories } from '../api/categoriesApi';
import { fetchProducts } from '../api/productsApi';

export const useCatalogStore = create((set) => ({
  categories: [],
  products: [],
  loading: false,
  error: null,
  selectedCategory: '',

  loadCategories: async () => {
    const categories = await fetchCategories();
    set({ categories });
  },

  loadProducts: async (category) => {
    set({ loading: true, error: null, selectedCategory: category ?? '' });
    try {
      const products = await fetchProducts(category);
      set({ products, loading: false });
    } catch (error) {
      set({ error: error.message, loading: false });
    }
  },
}));
