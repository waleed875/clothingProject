import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getAddresses } from '../api/addressesApi';
import { createOrder } from '../api/ordersApi';
import { useCartStore } from '../store/useCartStore';

export default function CheckoutPage() {
  const navigate = useNavigate();
  const [addresses, setAddresses] = useState([]);
  const [addressId, setAddressId] = useState('');
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');
  const cart = useCartStore((s) => s.cart);
  const loadCart = useCartStore((s) => s.loadCart);

  useEffect(() => {
    const load = async () => {
      try {
        await loadCart();
        const data = await getAddresses();
        setAddresses(data);
        const defaultAddr = data.find((a) => a.isDefault) || data[0];
        if (defaultAddr) setAddressId(String(defaultAddr.id));
      } finally {
        setLoading(false);
      }
    };
    load();
  }, []);

  const submit = async () => {
    setSubmitting(true);
    setError('');
    try {
      const order = await createOrder({ addressId: Number(addressId) });
      navigate(`/orders/${order.id}`);
    } catch (e) {
      setError(e.response?.data?.message ?? 'Checkout failed.');
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) return <main className="container"><p>Loading checkout...</p></main>;

  return (
    <main className="container">
      <h1>Checkout</h1>
      <p>Cart Total: ${cart.cartTotal ?? 0}</p>
      {!addresses.length && <p>No addresses found. Please add an address first.</p>}
      {!!addresses.length && (
        <select value={addressId} onChange={(e) => setAddressId(e.target.value)}>
          {addresses.map((a) => <option key={a.id} value={a.id}>{a.line1} - {a.city}</option>)}
        </select>
      )}
      <button onClick={submit} disabled={submitting || !addressId || !(cart.items?.length)}>{submitting ? 'Placing order...' : 'Place Order'}</button>
      {error && <p>{error}</p>}
    </main>
  );
}
