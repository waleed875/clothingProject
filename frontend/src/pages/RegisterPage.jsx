import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuthStore } from '../store/useAuthStore';

export default function RegisterPage() {
  const navigate = useNavigate();
  const { register, error, loading } = useAuthStore();
  const [form, setForm] = useState({ email: '', password: '', firstName: '', lastName: '' });

  const onSubmit = async (e) => {
    e.preventDefault();
    const user = await register(form);
    if (user) navigate('/profile');
  };

  return (
    <main className="container">
      <h1>Register</h1>
      <form onSubmit={onSubmit} className="card" style={{ maxWidth: 420 }}>
        <input placeholder="First name" value={form.firstName} onChange={(e) => setForm({ ...form, firstName: e.target.value })} />
        <input placeholder="Last name" value={form.lastName} onChange={(e) => setForm({ ...form, lastName: e.target.value })} />
        <input placeholder="Email" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} />
        <input placeholder="Password" type="password" value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} />
        <button type="submit" disabled={loading}>{loading ? 'Loading...' : 'Create account'}</button>
        {error && <p>{error}</p>}
      </form>
      <p>Already have account? <Link to="/login">Login</Link></p>
    </main>
  );
}
