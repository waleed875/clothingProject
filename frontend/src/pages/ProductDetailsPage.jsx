import { useEffect, useMemo, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { fetchProductDetails, fetchProductImages, fetchProductVariants } from '../api/productsApi';
import { useCartStore } from '../store/useCartStore';
import { useAuthStore } from '../store/useAuthStore';

export default function ProductDetailsPage() {
  const { slug } = useParams();
  const navigate = useNavigate();
  const token = useAuthStore((s) => s.token);
  const addItem = useCartStore((s) => s.addItem);

  const [product, setProduct] = useState(null);
  const [variants, setVariants] = useState([]);
  const [images, setImages] = useState([]);
  const [variantId, setVariantId] = useState('');
  const [loading, setLoading] = useState(true);
  const [pendingAdd, setPendingAdd] = useState(false);
  const [feedback, setFeedback] = useState('');

  useEffect(() => {
    let active = true;

    const load = async () => {
      try {
        const data = await fetchProductDetails(slug);
        const [variantData, imageData] = await Promise.all([
          fetchProductVariants(data.id),
          fetchProductImages(data.id),
        ]);

        if (active) {
          setProduct(data);
          setVariants(variantData);
          setImages(imageData);
          if (variantData[0]) setVariantId(String(variantData[0].id));
        }
      } catch {
        if (active) setFeedback('Failed to load product details.');
      } finally {
        if (active) setLoading(false);
      }
    };

    load();
    return () => {
      active = false;
    };
  }, [slug]);

  const displayedImages = useMemo(() => {
    const selected = Number(variantId);
    const variantImages = images.filter((i) => i.variantId === selected);
    if (variantImages.length) return variantImages;
    return images.filter((i) => i.productId === product?.id);
  }, [images, variantId, product]);

  const onAdd = async () => {
    if (!token) {
      navigate('/login');
      return;
    }
    setPendingAdd(true);
    setFeedback('');
    try {
      await addItem(Number(variantId), 1);
      setFeedback('Added to cart successfully.');
      navigate('/cart');
    } catch {
      setFeedback('Could not add item to cart.');
    } finally {
      setPendingAdd(false);
    }
  };

  if (loading) return <main className="container"><p>Loading...</p></main>;
  if (!product) return <main className="container"><p>Product not found.</p></main>;

  return (
    <main className="container">
      <Link to="/">← Back</Link>
      <h1>{product.name}</h1>
      <p>{product.description}</p>
      <p>Category: <strong>{product.categoryName}</strong></p>
      <p>Status: <span className="badge">{product.status}</span></p>

      <section className="grid" style={{ marginBottom: '1rem' }}>
        {displayedImages.length ? displayedImages.map((img) => (
          <img key={img.id} src={img.imageUrl} alt={img.altText || product.name} style={{ width: '100%', height: 220, objectFit: 'cover', borderRadius: 8 }} />
        )) : <div className="card">No images yet.</div>}
      </section>

      <div className="card" style={{ maxWidth: 420 }}>
        <h3>Select variant</h3>
        <select value={variantId} onChange={(e) => setVariantId(e.target.value)}>
          {variants.map((v) => (
            <option key={v.id} value={v.id}>{v.size} / {v.color} - ${v.price}</option>
          ))}
        </select>
        <button onClick={onAdd} disabled={pendingAdd}>{pendingAdd ? 'Adding...' : 'Add to Cart'}</button>
        {feedback && <p>{feedback}</p>}
      </div>
    </main>
  );
}
