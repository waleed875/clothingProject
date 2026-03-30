export default function CategoryFilter({ categories, selectedCategory, onChange }) {
  return (
    <div style={{ display: 'flex', gap: '0.5rem', marginBottom: '1rem', flexWrap: 'wrap' }}>
      <button onClick={() => onChange('')} className="badge">All</button>
      {categories.map((category) => (
        <button
          key={category.id}
          onClick={() => onChange(category.slug)}
          className="badge"
          style={{ fontWeight: selectedCategory === category.slug ? 'bold' : 'normal' }}
        >
          {category.name}
        </button>
      ))}
    </div>
  );
}
