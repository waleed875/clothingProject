import { create } from 'zustand';
import { addCartItem, clearCart, deleteCartItem, getCart, updateCartItem } from '../api/cartApi';

export const useCartStore = create((set) => ({
  cart: { items: [], cartTotal: 0 },
  loadCart: async () => {
    const cart = await getCart();
    set({ cart });
  },
  addItem: async (variantId, quantity = 1) => {
    const cart = await addCartItem({ variantId, quantity });
    set({ cart });
  },
  updateItemQty: async (itemId, quantity) => {
    const cart = await updateCartItem(itemId, { quantity });
    set({ cart });
  },
  removeItem: async (itemId) => {
    const cart = await deleteCartItem(itemId);
    set({ cart });
  },
  clear: async () => {
    await clearCart();
    set({ cart: { items: [], cartTotal: 0 } });
  },
}));
