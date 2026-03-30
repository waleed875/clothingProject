import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { adminCreateVariant, adminUpdateVariant, getProductVariants } from '../api/adminApi';
import { addVariantImage, deleteImage, setPrimaryImage } from '../api/adminImageApi';
import { fetchProductImages } from '../api/productsApi';

const initial = { sku: '', size: '', color: '', price: 0, status: 'ACTIVE', quantity: 0 };

export default function AdminProductVariantsPage() {
  const { id } = useParams();
  const [variants, setVariants] = useState([]);
  const [images, setImages] = useState([]);
  const [form, setForm] = useState(initial);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');

  const load = async () => {
    setLoading(true);
    const [variantData, imageData] = await Promise.all([getProductVariants(id), fetchProductImages(id)]);
    setVariants(variantData);
    setImages(imageData);
    setLoading(false);
  };

  useEffect(() => { load(); }, [id]);

  const onCreate = async (e) => {
    e.preventDefault();
    await adminCreateVariant(id, { ...form, price: Number(form.price), quantity: Number(form.quantity) });
    setForm(initial);
    setMessage('Variant created successfully.');
    load();
  };

  const addImage = async (variantId) => {
    const imageUrl = prompt('Image URL');
    if (!imageUrl) return;
    await addVariantImage(variantId, {
      imageUrl,
      storageKey: `manual/${variantId}/${Date.now()}`,
      altText: 'Variant image',
      sortOrder: 1,
      isPrimary: false,
    });
    load();
  };

  return (
    <main className="container">
      <h1>Product Variants</h1>
      {message && <p>{message}</p>}
      <form className="card" onSubmit={onCreate}>
        <input placeholder="SKU" value={form.sku} onChange={(e) => setForm({ ...form, sku: e.target.value })} />
        <input placeholder="Size" value={form.size} onChange={(e) => setForm({ ...form, size: e.target.value })} />
        <input placeholder="Color" value={form.color} onChange={(e) => setForm({ ...form, color: e.target.value })} />
        <input placeholder="Price" type="number" value={form.price} onChange={(e) => setForm({ ...form, price: e.target.value })} />
        <input placeholder="Quantity" type="number" value={form.quantity} onChange={(e) => setForm({ ...form, quantity: e.target.value })} />
        <button type="submit" disabled={loading}>{loading ? 'Saving...' : 'Create variant'}</button>
      </form>

      {!variants.length && !loading && <p>No variants yet.</p>}
      {loading && <p>Loading variants...</p>}
      <section className="grid">
        {variants.map((variant) => {
          const variantImages = images.filter((img) => img.variantId === variant.id);
          return (
            <article className="card" key={variant.id}>
              <p>{variant.sku}</p>
              <p>{variant.size} / {variant.color}</p>
              <p>Qty: {variant.quantity}</p>
              <div style={{ display: 'flex', gap: '0.5rem', marginBottom: '0.5rem' }}>
                <button onClick={() => adminUpdateVariant(variant.id, { ...variant, status: 'INACTIVE' }).then(load)}>Mark Inactive</button>
                <button onClick={() => addImage(variant.id)}>Add Image URL</button>
              </div>
              {variantImages.map((img) => (
                <div key={img.id} style={{ display: 'flex', gap: '0.5rem', alignItems: 'center' }}>
                  <img src={img.imageUrl} alt={img.altText || 'variant'} width="48" height="48" />
                  <button onClick={() => setPrimaryImage(img.id, true).then(load)}>Primary</button>
                  <button onClick={() => deleteImage(img.id).then(load)}>Delete</button>
                </div>
              ))}
            </article>
          );
        })}
      </section>
    </main>
  );
}
