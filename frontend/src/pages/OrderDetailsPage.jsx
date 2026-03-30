import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getOrderDetails } from '../api/ordersApi';

export default function OrderDetailsPage() {
  const { orderId } = useParams();
  const [order, setOrder] = useState(null);

  useEffect(() => {
    getOrderDetails(orderId).then(setOrder);
  }, [orderId]);

  if (!order) return <main className="container">Loading...</main>;

  return (
    <main className="container">
      <h1>Order {order.orderNumber}</h1>
      <p>Status: {order.status}</p>
      <p>Total: ${order.totalAmount}</p>
      <section className="grid">
        {order.items.map((item) => (
          <article className="card" key={item.id}>
            <h3>{item.productName}</h3>
            <p>{item.size} / {item.color}</p>
            <p>{item.quantity} x ${item.unitPrice}</p>
            <p>${item.totalPrice}</p>
          </article>
        ))}
      </section>
    </main>
  );
}
