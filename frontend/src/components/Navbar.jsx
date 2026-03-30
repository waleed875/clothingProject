import { Link } from 'react-router-dom';
import { useAuthStore } from '../store/useAuthStore';

export default function Navbar() {
  const { token, user, logout } = useAuthStore();
  const isAdmin = (user?.roles ?? []).includes('ADMIN');

  return (
    <nav className="nav">
      <Link to="/">Products</Link>
      {token ? (
        <>
          <Link to="/cart">Cart</Link>
          <Link to="/my-orders">My Orders</Link>
          <Link to="/profile">Profile</Link>
          <Link to="/addresses">Addresses</Link>
          {isAdmin && <Link to="/admin">Admin</Link>}
          <button onClick={logout}>Logout</button>
        </>
      ) : (
        <>
          <Link to="/login">Login</Link>
          <Link to="/register">Register</Link>
        </>
      )}
    </nav>
  );
}
