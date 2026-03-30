import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import { loginApi, meApi, registerApi } from '../api/authApi';

export const useAuthStore = create(
  persist(
    (set) => ({
      token: null,
      user: null,
      loading: false,
      error: null,

      login: async (payload) => {
        set({ loading: true, error: null });
        try {
          const data = await loginApi(payload);
          set({ token: data.accessToken, user: data.user, loading: false });
          return data.user;
        } catch (error) {
          set({ error: error.response?.data?.message ?? 'Login failed', loading: false });
          return null;
        }
      },

      register: async (payload) => {
        set({ loading: true, error: null });
        try {
          const data = await registerApi(payload);
          set({ token: data.accessToken, user: data.user, loading: false });
          return data.user;
        } catch (error) {
          set({ error: error.response?.data?.message ?? 'Register failed', loading: false });
          return null;
        }
      },

      fetchMe: async () => {
        try {
          const user = await meApi();
          set({ user });
        } catch {
          set({ token: null, user: null });
        }
      },

      logout: () => set({ token: null, user: null, error: null }),
    }),
    {
      name: 'auth-storage',
      partialize: (state) => ({ token: state.token, user: state.user }),
    }
  )
);
