import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { fetchProductImages } from '../api/productsApi';

export default function ProductCard({ product }) {
  const [image, setImage] = useState(null);

  useEffect(() => {
    fetchProductImages(product.id)
      .then((images) => setImage(images.find((x) => x.isPrimary)?.imageUrl ?? images[0]?.imageUrl ?? null))
      .catch(() => setImage(null));
  }, [product.id]);

  return (
    <article className="card">
      {image ? <img src={image} alt={product.name} style={{ width: '100%', height: 180, objectFit: 'cover', borderRadius: 6 }} /> : <div style={{ height: 180, background: '#eee', borderRadius: 6, display: 'grid', placeItems: 'center' }}>No image</div>}
      <h3>{product.name}</h3>
      <p><span className="badge">{product.categorySlug}</span></p>
      <Link to={`/products/${product.slug}`}>View details</Link>
    </article>
  );
}
