import { Navigate } from 'react-router-dom';
import { useAuthStore } from '../store/useAuthStore';

export default function AdminRoute({ children }) {
  const token = useAuthStore((state) => state.token);
  const roles = useAuthStore((state) => state.user?.roles ?? []);

  if (!token) return <Navigate to="/login" replace />;
  if (!roles.includes('ADMIN')) return <Navigate to="/" replace />;
  return children;
}
