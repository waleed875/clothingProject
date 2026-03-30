import { Link } from 'react-router-dom';

export default function NotFoundPage() {
  return (
    <main className="container">
      <h1>404 - Page Not Found</h1>
      <p>The page you requested does not exist.</p>
      <Link to="/">Go back home</Link>
    </main>
  );
}
