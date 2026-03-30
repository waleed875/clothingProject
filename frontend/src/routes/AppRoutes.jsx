import { Route, Routes } from 'react-router-dom';
import Navbar from '../components/Navbar';
import ProductListPage from '../pages/ProductListPage';
import ProductDetailsPage from '../pages/ProductDetailsPage';
import LoginPage from '../pages/LoginPage';
import RegisterPage from '../pages/RegisterPage';
import ProfilePage from '../pages/ProfilePage';
import AddressesPage from '../pages/AddressesPage';
import ProtectedRoute from '../components/ProtectedRoute';
import AdminRoute from '../components/AdminRoute';
import AdminDashboardPage from '../pages/AdminDashboardPage';
import AdminCategoriesPage from '../pages/AdminCategoriesPage';
import AdminProductsPage from '../pages/AdminProductsPage';
import AdminProductFormPage from '../pages/AdminProductFormPage';
import AdminProductVariantsPage from '../pages/AdminProductVariantsPage';
import AdminInventoryPage from '../pages/AdminInventoryPage';
import CartPage from '../pages/CartPage';
import CheckoutPage from '../pages/CheckoutPage';
import MyOrdersPage from '../pages/MyOrdersPage';
import OrderDetailsPage from '../pages/OrderDetailsPage';
import NotFoundPage from '../pages/NotFoundPage';

export default function AppRoutes() {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<ProductListPage />} />
        <Route path="/products/:slug" element={<ProductDetailsPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />

        <Route path="/cart" element={<ProtectedRoute><CartPage /></ProtectedRoute>} />
        <Route path="/checkout" element={<ProtectedRoute><CheckoutPage /></ProtectedRoute>} />
        <Route path="/my-orders" element={<ProtectedRoute><MyOrdersPage /></ProtectedRoute>} />
        <Route path="/orders/:orderId" element={<ProtectedRoute><OrderDetailsPage /></ProtectedRoute>} />
        <Route path="/profile" element={<ProtectedRoute><ProfilePage /></ProtectedRoute>} />
        <Route path="/addresses" element={<ProtectedRoute><AddressesPage /></ProtectedRoute>} />

        <Route path="/admin" element={<AdminRoute><AdminDashboardPage /></AdminRoute>} />
        <Route path="/admin/categories" element={<AdminRoute><AdminCategoriesPage /></AdminRoute>} />
        <Route path="/admin/products" element={<AdminRoute><AdminProductsPage /></AdminRoute>} />
        <Route path="/admin/products/new" element={<AdminRoute><AdminProductFormPage /></AdminRoute>} />
        <Route path="/admin/products/:id/edit" element={<AdminRoute><AdminProductFormPage /></AdminRoute>} />
        <Route path="/admin/products/:id/variants" element={<AdminRoute><AdminProductVariantsPage /></AdminRoute>} />
        <Route path="/admin/inventory" element={<AdminRoute><AdminInventoryPage /></AdminRoute>} />

        <Route path="*" element={<NotFoundPage />} />
      </Routes>
    </>
  );
}
