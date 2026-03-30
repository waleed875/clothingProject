import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { fetchProducts } from '../api/productsApi';

export default function AdminProductsPage() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetchProducts('').then(setProducts);
  }, []);

  return (
    <main className="container">
      <h1>Admin Products</h1>
      <Link className="badge" to="/admin/products/new">+ New product</Link>
      <section className="grid" style={{ marginTop: '1rem' }}>
        {products.map((product) => (
          <article className="card" key={product.id}>
            <h3>{product.name}</h3>
            <p>{product.status}</p>
            <div style={{ display: 'flex', gap: '0.5rem' }}>
              <Link to={`/admin/products/${product.id}/edit`}>Edit</Link>
              <Link to={`/admin/products/${product.id}/variants`}>Variants</Link>
            </div>
          </article>
        ))}
      </section>
    </main>
  );
}
