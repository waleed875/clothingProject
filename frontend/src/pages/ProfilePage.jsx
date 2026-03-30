import { useEffect, useState } from 'react';
import { useAuthStore } from '../store/useAuthStore';
import { getUserProfile, updateUserProfile } from '../api/usersApi';

export default function ProfilePage() {
  const authUser = useAuthStore((state) => state.user);
  const [form, setForm] = useState({ firstName: '', lastName: '' });
  const [message, setMessage] = useState('');

  useEffect(() => {
    const load = async () => {
      const me = await getUserProfile();
      setForm({ firstName: me.firstName ?? '', lastName: me.lastName ?? '' });
    };
    load();
  }, []);

  const onSubmit = async (e) => {
    e.preventDefault();
    await updateUserProfile(form);
    setMessage('Profile updated');
  };

  return (
    <main className="container">
      <h1>Profile</h1>
      <p>Email: {authUser?.email}</p>
      <form onSubmit={onSubmit} className="card" style={{ maxWidth: 420 }}>
        <input value={form.firstName} onChange={(e) => setForm({ ...form, firstName: e.target.value })} />
        <input value={form.lastName} onChange={(e) => setForm({ ...form, lastName: e.target.value })} />
        <button type="submit">Save</button>
        {message && <p>{message}</p>}
      </form>
    </main>
  );
}
