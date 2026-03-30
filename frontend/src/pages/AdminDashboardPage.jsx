import { Link } from 'react-router-dom';

export default function AdminDashboardPage() {
  return (
    <main className="container">
      <h1>Admin Dashboard</h1>
      <div className="grid">
        <Link className="card" to="/admin/categories">Manage Categories</Link>
        <Link className="card" to="/admin/products">Manage Products</Link>
        <Link className="card" to="/admin/inventory">Manage Inventory</Link>
      </div>
    </main>
  );
}
