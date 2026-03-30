import { useEffect, useState } from 'react';
import { adminListInventory, adminUpdateVariantInventory } from '../api/adminApi';

export default function AdminInventoryPage() {
  const [rows, setRows] = useState([]);

  const load = async () => {
    setRows(await adminListInventory());
  };

  useEffect(() => { load(); }, []);

  return (
    <main className="container">
      <h1>Admin Inventory</h1>
      <table className="card" style={{ width: '100%' }}>
        <thead>
          <tr><th>Variant</th><th>SKU</th><th>Qty</th><th>Action</th></tr>
        </thead>
        <tbody>
          {rows.map((row) => (
            <tr key={row.variantId}>
              <td>{row.variantId}</td>
              <td>{row.sku}</td>
              <td>{row.quantity}</td>
              <td>
                <button onClick={() => adminUpdateVariantInventory(row.variantId, row.quantity + 1).then(load)}>+1</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </main>
  );
}
