import axios from 'axios';

export const httpClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080/api/v1',
  timeout: 10000,
});

httpClient.interceptors.request.use((config) => {
  const raw = localStorage.getItem('auth-storage');
  if (!raw) return config;

  try {
    const parsed = JSON.parse(raw);
    const token = parsed?.state?.token;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
  } catch {
    // no-op
  }

  return config;
});
