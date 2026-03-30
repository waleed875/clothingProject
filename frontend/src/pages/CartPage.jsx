import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useCartStore } from '../store/useCartStore';

export default function CartPage() {
  const navigate = useNavigate();
  const { cart, loadCart, updateItemQty, removeItem, clear } = useCartStore();
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState('');
  const [pending, setPending] = useState(false);

  const refresh = async () => {
    setLoading(true);
    await loadCart();
    setLoading(false);
  };

  useEffect(() => { refresh(); }, []);

  const handleAction = async (fn, successMessage) => {
    setPending(true);
    setMessage('');
    try {
      await fn();
      setMessage(successMessage);
    } catch {
      setMessage('Action failed. Please try again.');
    } finally {
      setPending(false);
    }
  };

  return (
    <main className="container">
      <h1>My Cart</h1>
      {message && <p>{message}</p>}
      <button onClick={() => handleAction(clear, 'Cart cleared.')} disabled={pending}>Clear cart</button>
      {loading && <p>Loading cart...</p>}
      {!loading && !cart.items?.length && <p>Your cart is empty.</p>}
      <section className="grid" style={{ marginTop: '1rem' }}>
        {cart.items?.map((item) => (
          <article key={item.itemId} className="card">
            <h3>{item.productName}</h3>
            <p>{item.size} / {item.color}</p>
            <p>Unit: ${item.unitPrice}</p>
            <p>Line: ${item.lineTotal}</p>
            <div style={{ display: 'flex', gap: '0.5rem' }}>
              <button disabled={pending} onClick={() => handleAction(() => updateItemQty(item.itemId, item.quantity + 1), 'Quantity updated.')}>+</button>
              <span>{item.quantity}</span>
              <button disabled={pending || item.quantity <= 1} onClick={() => handleAction(() => updateItemQty(item.itemId, item.quantity - 1), 'Quantity updated.')}>-</button>
              <button disabled={pending} onClick={() => handleAction(() => removeItem(item.itemId), 'Item removed.')}>Delete</button>
            </div>
          </article>
        ))}
      </section>
      <h3>Total: ${cart.cartTotal ?? 0}</h3>
      <div style={{ display: 'flex', gap: '0.5rem' }}>
        <Link to="/checkout" className="badge">Go to Checkout</Link>
        <button onClick={() => navigate('/my-orders')}>My Orders</button>
      </div>
    </main>
  );
}
