# Clothing Store MVP

Monorepo for a clothing e-commerce MVP.

## 1) Setup Instructions
### Backend
```bash
cd backend
mvn spring-boot:run
```
Default API base URL: `http://localhost:8080/api/v1`.

### Frontend
```bash
cd frontend
npm install
npm run dev
```
Frontend runs on `http://localhost:5173`.

### Database
- PostgreSQL database expected: `clothing_store`.
- Flyway runs automatically on backend startup (`V1..V3`).

## 2) Demo Accounts
- ADMIN: `admin@demo.com` / `password`
- CUSTOMER: `customer@demo.com` / `password`

## 3) API / Module Overview
### Public APIs
- Catalog categories/products
- Product variants and product images (read-only)

### Auth & User
- Register/Login/JWT profile endpoints
- User profile and address book

### Admin APIs (`/api/v1/admin/**`)
- Categories, products, variants, inventory
- Product/variant image metadata management

### Commerce APIs
- Cart CRUD + clear
- Checkout and customer order history/details

### Frontend modules
- `api/`: Axios API wrappers
- `store/`: Zustand stores (`auth`, `catalog`, `cart`)
- `pages/`: customer and admin pages
- `routes/`: route wiring + protection

## 4) Demo Flow
1. Login as admin and manage products/variants/images.
2. Login as customer and browse catalog with images.
3. Add to cart, checkout with address, open order details.

## 5) Known Limitations
- Image upload is URL-based only (no binary upload/storage integration).
- UI is intentionally simple and not fully responsive/polished.
- No automated unit/integration/e2e tests are included yet.
- No payment/shipping gateway integration (checkout is internal simulation).
- Inventory locking/concurrency is basic and suitable for MVP demo, not high-scale production.

## 6) Next Steps
- Add automated tests (backend service/controller + frontend integration).
- Add refresh-token flow and stricter JWT lifecycle handling.
- Add real file storage provider integration for images.
- Improve admin UX with editable tables/modals and better validation hints.
- Add payment provider and shipment workflow.
