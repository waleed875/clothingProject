-- Demo users (password: password)
INSERT INTO users(email, password_hash, first_name, last_name, is_active)
VALUES
('admin@demo.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO4qV6wQq5hIwTZYBFvTo95OfzmiEJeZy', 'Demo', 'Admin', TRUE),
('customer@demo.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO4qV6wQq5hIwTZYBFvTo95OfzmiEJeZy', 'Demo', 'Customer', TRUE)
ON CONFLICT (email) DO NOTHING;

INSERT INTO user_roles(user_id, role_id)
SELECT u.id, r.id FROM users u JOIN roles r ON r.name = 'ADMIN' WHERE u.email = 'admin@demo.com'
ON CONFLICT DO NOTHING;

INSERT INTO user_roles(user_id, role_id)
SELECT u.id, r.id FROM users u JOIN roles r ON r.name = 'CUSTOMER' WHERE u.email = 'customer@demo.com'
ON CONFLICT DO NOTHING;

INSERT INTO addresses(user_id, line1, city, state, postal_code, country, is_default)
SELECT id, '123 Demo St', 'Austin', 'TX', '73301', 'USA', TRUE FROM users WHERE email='customer@demo.com'
ON CONFLICT DO NOTHING;

INSERT INTO categories(name, slug, is_active) VALUES
('Sportswear', 'sportswear', TRUE),
('Outerwear', 'outerwear', TRUE),
('Accessories', 'accessories', TRUE)
ON CONFLICT (slug) DO NOTHING;

INSERT INTO products(category_id, name, slug, description, status)
SELECT c.id, x.name, x.slug, x.description, x.status::varchar
FROM (
  VALUES
  ('men', 'Slim Fit Jeans', 'slim-fit-jeans', 'Stretch denim jeans', 'ACTIVE'),
  ('women', 'Classic White Shirt', 'classic-white-shirt', 'Everyday formal shirt', 'ACTIVE'),
  ('kids', 'Kids Jogger Set', 'kids-jogger-set', 'Comfortable cotton jogger set', 'ACTIVE'),
  ('sportswear', 'Training Shorts', 'training-shorts', 'Lightweight training shorts', 'ACTIVE'),
  ('outerwear', 'Puffer Jacket', 'puffer-jacket', 'Warm winter puffer', 'ACTIVE'),
  ('accessories', 'Baseball Cap', 'baseball-cap', 'Adjustable cap', 'ACTIVE'),
  ('women', 'Linen Dress', 'linen-dress', 'Breathable summer linen dress', 'ACTIVE'),
  ('men', 'Formal Blazer', 'formal-blazer', 'Slim fit office blazer', 'INACTIVE')
) AS x(category_slug, name, slug, description, status)
JOIN categories c ON c.slug = x.category_slug
ON CONFLICT (slug) DO NOTHING;

INSERT INTO product_variants(product_id, sku, size, color, price, status)
SELECT p.id, v.sku, v.size, v.color, v.price, v.status::varchar
FROM (
  VALUES
  ('slim-fit-jeans', 'JEANS-BLU-32', '32', 'Blue', 49.99, 'ACTIVE'),
  ('slim-fit-jeans', 'JEANS-BLU-34', '34', 'Blue', 49.99, 'ACTIVE'),
  ('classic-white-shirt', 'SHIRT-WHT-M', 'M', 'White', 29.99, 'ACTIVE'),
  ('classic-white-shirt', 'SHIRT-WHT-L', 'L', 'White', 29.99, 'OUT_OF_STOCK'),
  ('kids-jogger-set', 'KID-JOG-GRN-8', '8', 'Green', 24.99, 'ACTIVE'),
  ('training-shorts', 'SHORTS-BLK-M', 'M', 'Black', 19.99, 'ACTIVE'),
  ('puffer-jacket', 'PUFFER-BRN-L', 'L', 'Brown', 89.99, 'ACTIVE'),
  ('baseball-cap', 'CAP-NAVY-STD', 'STD', 'Navy', 14.99, 'ACTIVE'),
  ('linen-dress', 'DRESS-BGE-S', 'S', 'Beige', 59.99, 'ACTIVE'),
  ('formal-blazer', 'BLAZER-BLK-50', '50', 'Black', 119.99, 'INACTIVE')
) AS v(product_slug, sku, size, color, price, status)
JOIN products p ON p.slug = v.product_slug
ON CONFLICT (sku) DO NOTHING;

INSERT INTO inventories(variant_id, quantity, reserved_quantity)
SELECT pv.id, x.qty, 0
FROM (
  VALUES
  ('JEANS-BLU-32', 12),
  ('JEANS-BLU-34', 5),
  ('SHIRT-WHT-M', 20),
  ('SHIRT-WHT-L', 0),
  ('KID-JOG-GRN-8', 7),
  ('SHORTS-BLK-M', 16),
  ('PUFFER-BRN-L', 4),
  ('CAP-NAVY-STD', 25),
  ('DRESS-BGE-S', 9),
  ('BLAZER-BLK-50', 0)
) AS x(sku, qty)
JOIN product_variants pv ON pv.sku = x.sku
ON CONFLICT (variant_id) DO NOTHING;

INSERT INTO product_images(product_id, variant_id, image_url, storage_key, alt_text, is_primary, sort_order)
SELECT p.id, NULL, i.image_url, i.storage_key, i.alt_text, i.is_primary, i.sort_order
FROM (
  VALUES
  ('slim-fit-jeans', 'https://images.unsplash.com/photo-1541099649105-f69ad21f3246', 'demo/slim-fit-jeans/main', 'Slim Fit Jeans', TRUE, 1),
  ('classic-white-shirt', 'https://images.unsplash.com/photo-1596755094514-f87e34085b2c', 'demo/classic-shirt/main', 'Classic White Shirt', TRUE, 1),
  ('kids-jogger-set', 'https://images.unsplash.com/photo-1519238362236-5f0c7e0f93fe', 'demo/kids-jogger/main', 'Kids Jogger Set', TRUE, 1),
  ('training-shorts', 'https://images.unsplash.com/photo-1506629905607-e723a1f5f4cf', 'demo/training-shorts/main', 'Training Shorts', TRUE, 1),
  ('puffer-jacket', 'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab', 'demo/puffer-jacket/main', 'Puffer Jacket', TRUE, 1),
  ('baseball-cap', 'https://images.unsplash.com/photo-1521369909029-2afed882baee', 'demo/baseball-cap/main', 'Baseball Cap', TRUE, 1),
  ('linen-dress', 'https://images.unsplash.com/photo-1496747611176-843222e1e57c', 'demo/linen-dress/main', 'Linen Dress', TRUE, 1),
  ('formal-blazer', 'https://images.unsplash.com/photo-1593032465171-8bd7ee9b5f3f', 'demo/formal-blazer/main', 'Formal Blazer', TRUE, 1)
) AS i(product_slug, image_url, storage_key, alt_text, is_primary, sort_order)
JOIN products p ON p.slug = i.product_slug
ON CONFLICT DO NOTHING;
