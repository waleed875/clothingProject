import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuthStore } from '../store/useAuthStore';

export default function LoginPage() {
  const navigate = useNavigate();
  const { login, error, loading } = useAuthStore();
  const [form, setForm] = useState({ email: '', password: '' });

  const onSubmit = async (e) => {
    e.preventDefault();
    const user = await login(form);
    if (user) {
      const isAdmin = (user.roles ?? []).includes('ADMIN');
      navigate(isAdmin ? '/admin' : '/profile');
    }
  };

  return (
    <main className="container">
      <h1>Login</h1>
      <form onSubmit={onSubmit} className="card" style={{ maxWidth: 420 }}>
        <input placeholder="Email" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} />
        <input placeholder="Password" type="password" value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} />
        <button type="submit" disabled={loading}>{loading ? 'Loading...' : 'Login'}</button>
        {error && <p>{error}</p>}
      </form>
      <p>No account? <Link to="/register">Register</Link></p>
    </main>
  );
}
