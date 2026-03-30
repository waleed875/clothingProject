import { useEffect } from 'react';
import { useCatalogStore } from '../store/useCatalogStore';
import ProductCard from '../components/ProductCard';
import CategoryFilter from '../components/CategoryFilter';

export default function ProductListPage() {
  const {
    categories,
    products,
    loading,
    error,
    selectedCategory,
    loadCategories,
    loadProducts,
  } = useCatalogStore();

  useEffect(() => {
    loadCategories();
    loadProducts('');
  }, [loadCategories, loadProducts]);

  return (
    <main className="container">
      <h1>Products</h1>
      <CategoryFilter
        categories={categories}
        selectedCategory={selectedCategory}
        onChange={loadProducts}
      />

      {loading && <p>Loading products...</p>}
      {error && <p>Failed to load data: {error}</p>}

      <section className="grid">
        {products.map((product) => (
          <ProductCard key={product.id} product={product} />
        ))}
      </section>
    </main>
  );
}
