import { useEffect, useMemo, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { fetchCategories } from '../api/categoriesApi';
import { addProductImage, deleteImage, setPrimaryImage, updateImageSortOrder } from '../api/adminImageApi';
import { adminCreateProduct, adminUpdateProduct } from '../api/adminApi';
import { fetchProductImages } from '../api/productsApi';

export default function AdminProductFormPage() {
  const { id } = useParams();
  const isEdit = useMemo(() => Boolean(id), [id]);
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);
  const [form, setForm] = useState({ name: '', slug: '', description: '', categoryId: '', status: 'ACTIVE' });
  const [images, setImages] = useState([]);
  const [imageForm, setImageForm] = useState({ imageUrl: '', storageKey: '', altText: '', sortOrder: 1, isPrimary: false });

  const loadImages = async () => {
    if (!id) return;
    const all = await fetchProductImages(id);
    setImages(all.filter((x) => x.productId === Number(id) && !x.variantId));
  };

  useEffect(() => {
    fetchCategories().then((data) => {
      setCategories(data);
      if (!form.categoryId && data[0]) {
        setForm((prev) => ({ ...prev, categoryId: String(data[0].id) }));
      }
    });
    loadImages();
  }, [id]);

  const onSubmit = async (e) => {
    e.preventDefault();
    const payload = { ...form, categoryId: Number(form.categoryId) };
    if (isEdit) {
      await adminUpdateProduct(id, payload);
    } else {
      await adminCreateProduct(payload);
    }
    navigate('/admin/products');
  };

  const addImage = async (e) => {
    e.preventDefault();
    await addProductImage(id, { ...imageForm, sortOrder: Number(imageForm.sortOrder) });
    setImageForm({ imageUrl: '', storageKey: '', altText: '', sortOrder: 1, isPrimary: false });
    loadImages();
  };

  return (
    <main className="container">
      <h1>{isEdit ? 'Edit Product' : 'New Product'}</h1>
      <form className="card" onSubmit={onSubmit}>
        <input placeholder="Name" value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} />
        <input placeholder="Slug" value={form.slug} onChange={(e) => setForm({ ...form, slug: e.target.value })} />
        <input placeholder="Description" value={form.description} onChange={(e) => setForm({ ...form, description: e.target.value })} />
        <select value={form.categoryId} onChange={(e) => setForm({ ...form, categoryId: e.target.value })}>
          {categories.map((category) => <option key={category.id} value={category.id}>{category.name}</option>)}
        </select>
        <select value={form.status} onChange={(e) => setForm({ ...form, status: e.target.value })}>
          <option value="ACTIVE">ACTIVE</option>
          <option value="INACTIVE">INACTIVE</option>
          <option value="OUT_OF_STOCK">OUT_OF_STOCK</option>
        </select>
        <button type="submit">Save</button>
      </form>

      {isEdit && (
        <>
          <h2>Product Images</h2>
          <form className="card" onSubmit={addImage}>
            <input placeholder="Image URL" value={imageForm.imageUrl} onChange={(e) => setImageForm({ ...imageForm, imageUrl: e.target.value })} />
            <input placeholder="Storage Key" value={imageForm.storageKey} onChange={(e) => setImageForm({ ...imageForm, storageKey: e.target.value })} />
            <input placeholder="Alt text" value={imageForm.altText} onChange={(e) => setImageForm({ ...imageForm, altText: e.target.value })} />
            <input type="number" placeholder="Sort order" value={imageForm.sortOrder} onChange={(e) => setImageForm({ ...imageForm, sortOrder: e.target.value })} />
            <label><input type="checkbox" checked={imageForm.isPrimary} onChange={(e) => setImageForm({ ...imageForm, isPrimary: e.target.checked })} /> Primary</label>
            <button type="submit">Add Image</button>
          </form>

          <section className="grid">
            {images.map((img) => (
              <article key={img.id} className="card">
                <img src={img.imageUrl} alt={img.altText || 'product'} style={{ width: '100%', height: 140, objectFit: 'cover', borderRadius: 6 }} />
                <p>Sort: {img.sortOrder}</p>
                <p>{img.isPrimary ? 'Primary' : 'Secondary'}</p>
                <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
                  <button onClick={() => setPrimaryImage(img.id, true).then(loadImages)}>Set Primary</button>
                  <button onClick={() => updateImageSortOrder(img.id, img.sortOrder + 1).then(loadImages)}>Sort +1</button>
                  <button onClick={() => deleteImage(img.id).then(loadImages)}>Delete</button>
                </div>
              </article>
            ))}
          </section>
        </>
      )}
    </main>
  );
}
