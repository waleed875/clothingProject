import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getMyOrders } from '../api/ordersApi';

export default function MyOrdersPage() {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    getMyOrders().then(setOrders);
  }, []);

  return (
    <main className="container">
      <h1>My Orders</h1>
      <section className="grid">
        {orders.map((order) => (
          <article key={order.id} className="card">
            <p>{order.orderNumber}</p>
            <p>{order.status}</p>
            <p>${order.totalAmount}</p>
            <Link to={`/orders/${order.id}`}>Details</Link>
          </article>
        ))}
      </section>
    </main>
  );
}
