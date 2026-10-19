import axios from 'axios';

export default function AdminPanel({ API, data, filteredProducts, selectedCategory, setSelectedCategory, handleUpdatePrice, handleToggleOrderStatus, handleDeleteProduct, handleDeleteCategory, submitForm, setMessage, loadData }) {
  return (
    <>
      <div className="center-column">
        <div className="admin-panel-controllers">
          <h2>🛠️ Admin Panels</h2>
          <div className="admin-categories-container">
            <div className="admin-panel-card">
              <h3>📋 Existing Categories</h3>
              {data.categories && data.categories.length === 0 ? <p>No categories available.</p> : (
                <div className="admin-categories-list">
                  {data.categories.map(category => (
                    <div key={category.id} className="admin-category-item">
                      <span>{category.name}</span>
                      <button className="btn-delete-category" onClick={() => handleDeleteCategory(category.id)} title="Delete Category">🗑️</button>
                    </div>
                  ))}
                </div>
              )}
            </div>

            <div className="admin-panel-card">
              <h3>🔠 Add Category</h3>
              <form className="admin-category-form" onSubmit={event => submitForm(event, "categories", "Category created!")}>
                <input name="name" placeholder="Category Name" required />
                <button type="submit">Create</button>
              </form>
            </div>
          </div>

          <div className="admin-card">
            <h3>➕ Add New Product</h3>
            <form onSubmit={event => {
              event.preventDefault();
              const form = event.target;
              const formData = new FormData(form);
              const body = {
                name: formData.get("name"),
                description: formData.get("description"),
                price: parseFloat(formData.get("price")),
                stock: parseInt(formData.get("stock")),
                imageUrl: formData.get("imageUrl")
              };
              const categoryId = formData.get("productCategoryId");
              if (categoryId && categoryId !== "NONE") body.category = { id: parseInt(categoryId) };

              axios.post(`${API}/products`, body)
                .then(() => {
                  setMessage("Product added to catalog!");
                  form.reset();
                  loadData();
                }).catch(() => alert("Error adding product"));
            }}>
              <input name="name" placeholder="Product Name" required />
              <input name="description" placeholder="Description" required />
              <input name="price" type="number" step="0.01" placeholder="Price (€)" required />
              <input name="stock" type="number" placeholder="Stock" required />
              <input name="imageUrl" placeholder="URL image" required />
              <select name="productCategoryId" required>
                <option value="NONE">-- Choose a Category --</option>
                {data.categories.map(category => (
                  <option key={category.id} value={category.id}>{category.name}</option>
                ))}
              </select>
              <button type="submit">Save Product</button>
            </form>
          </div>
        </div>

        <div className="admin-filter-section">
          <label><strong>🔠 Select Category: </strong></label>
          <select value={selectedCategory} onChange={event => setSelectedCategory(event.target.value)}>
            <option value="ALL">All Products (See All)</option>
            {data.categories.map(category => (
              <option key={category.id} value={category.id}>{category.name}</option>
            ))}
          </select>
        </div>

        <div className="section-product-admin">
          <h2 className="title-product-admin">🏷️Product Catalog</h2>
          <div className="products-grid-layout-admin">
            {filteredProducts.map(product => (
              <div className="product-card" key={product.id} >
                <div className="product-card-image">
                  <img src={product.imageUrl && product.imageUrl.split(".").length > 0 ? product.imageUrl.split(",")[0].trim()
                    : "https://via.placeholder.com/150"}
                    alt={product.name} />
                </div>
                <h3>{product.name} - {product.price}€</h3>
                <p><strong>Description:</strong> {product.description}</p>
                <p><strong>Stock:</strong> {product.stock}</p>
                <button type="button" onClick={() => handleUpdatePrice(product.id)}>🔧 Change Price</button>
                <button
                  type="button"
                  onClick={() => handleDeleteProduct(product.id)}>🗑️ Delete
                </button>
              </div>
            ))}
          </div>
        </div>
        </div>

      <div className="right-column">
        <div>
          <h2>📋 System Global Orders</h2>
          {data.orders.length === 0 ? <p>No orders recorded in the system</p> : data.orders.map(order => (
            <div className="detail-container" key={order.id} >
              <p><strong>📦Order #{order.id}</strong> - <strong>Total: {Number(order.total).toFixed(2)}€ </strong></p>
              <p>Status: <strong>{order.status}</strong> | Client: {order.customer ? order.customer.firstName : "Unknown"}</p>
              <button type="button" onClick={() => handleToggleOrderStatus(order.id, order.status)}>
                🔄 Set as {order.status === "PENDING" ? "COMPLETED" : "PENDING"}
              </button>
            </div>
          ))}
        </div>
      </div>
    </>
  );
}
