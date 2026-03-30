import { useEffect, useState } from 'react';
import { fetchCategories } from '../api/categoriesApi';
import { adminCategoryStatus, adminCreateCategory } from '../api/adminApi';

export default function AdminCategoriesPage() {
  const [categories, setCategories] = useState([]);
  const [form, setForm] = useState({ name: '', slug: '' });

  const load = async () => {
    const data = await fetchCategories();
    setCategories(data);
  };

  useEffect(() => { load(); }, []);

  const onCreate = async (e) => {
    e.preventDefault();
    await adminCreateCategory(form);
    setForm({ name: '', slug: '' });
    load();
  };

  return (
    <main className="container">
      <h1>Admin Categories</h1>
      <form className="card" onSubmit={onCreate}>
        <input placeholder="Name" value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} />
        <input placeholder="Slug" value={form.slug} onChange={(e) => setForm({ ...form, slug: e.target.value })} />
        <button type="submit">Create category</button>
      </form>

      <section className="grid">
        {categories.map((category) => (
          <article className="card" key={category.id}>
            <h3>{category.name}</h3>
            <p>{category.slug}</p>
            <button onClick={() => adminCategoryStatus(category.id, false).then(load)}>Disable</button>
            <button onClick={() => adminCategoryStatus(category.id, true).then(load)}>Enable</button>
          </article>
        ))}
      </section>
    </main>
  );
}
