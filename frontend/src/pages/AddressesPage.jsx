import { useEffect, useState } from 'react';
import { createAddress, deleteAddress, getAddresses, setDefaultAddress } from '../api/addressesApi';

const emptyAddress = { line1: '', line2: '', city: '', state: '', postalCode: '', country: '', isDefault: false };

export default function AddressesPage() {
  const [addresses, setAddresses] = useState([]);
  const [form, setForm] = useState(emptyAddress);

  const load = async () => {
    const data = await getAddresses();
    setAddresses(data);
  };

  useEffect(() => {
    load();
  }, []);

  const onSubmit = async (e) => {
    e.preventDefault();
    await createAddress(form);
    setForm(emptyAddress);
    load();
  };

  return (
    <main className="container">
      <h1>My Addresses</h1>
      <form onSubmit={onSubmit} className="card" style={{ display: 'grid', gap: '0.5rem', marginBottom: '1rem' }}>
        <input placeholder="Line 1" value={form.line1} onChange={(e) => setForm({ ...form, line1: e.target.value })} />
        <input placeholder="Line 2" value={form.line2} onChange={(e) => setForm({ ...form, line2: e.target.value })} />
        <input placeholder="City" value={form.city} onChange={(e) => setForm({ ...form, city: e.target.value })} />
        <input placeholder="State" value={form.state} onChange={(e) => setForm({ ...form, state: e.target.value })} />
        <input placeholder="Postal code" value={form.postalCode} onChange={(e) => setForm({ ...form, postalCode: e.target.value })} />
        <input placeholder="Country" value={form.country} onChange={(e) => setForm({ ...form, country: e.target.value })} />
        <label><input type="checkbox" checked={form.isDefault} onChange={(e) => setForm({ ...form, isDefault: e.target.checked })} /> Set as default</label>
        <button type="submit">Add address</button>
      </form>

      <section className="grid">
        {addresses.map((address) => (
          <article className="card" key={address.id}>
            <p>{address.line1}</p>
            <p>{address.city}, {address.country}</p>
            {address.isDefault && <span className="badge">Default</span>}
            <div style={{ display: 'flex', gap: '0.5rem', marginTop: '0.5rem' }}>
              <button onClick={() => setDefaultAddress(address.id).then(load)}>Set default</button>
              <button onClick={() => deleteAddress(address.id).then(load)}>Delete</button>
            </div>
          </article>
        ))}
      </section>
    </main>
  );
}
