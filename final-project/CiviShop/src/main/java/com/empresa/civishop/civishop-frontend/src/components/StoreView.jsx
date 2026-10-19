export default function StoreView({ data, filteredProducts, cart, customerOrders, selectedCategory, setSelectedCategory, addToCart, removeFromCart, handleCheckout, showCart }) {
  return (
    <>
      {!showCart && (
        <div className="center-column catalog-full-width">
          <div>
            <h2 className="category-select-box">🔠Select Category</h2>
            <select value={selectedCategory} onChange={event => setSelectedCategory(event.target.value)}>
              <option value="ALL">All Products (See All)</option>
              {data.categories.map(category => (
                <option key={category.id} value={category.id}>{category.name}</option>
              ))}
            </select>
          </div>

          <h2 className="section-product">🏷️Product Catalog</h2>
          <div className="products-grid-layout">
            {filteredProducts.map(product => (
              <div className="product-card" key={product.id}>
                <div className="product-card-image">
                  <img src={product.imageUrl && product.imageUrl.split(".").length > 0 ? product.imageUrl.split(",")[0].trim()
                    : "https://via.placeholder.com/150"}
                    alt={product.name} />
                </div>
                <div className="product-card-body">
                  <h3>{product.name}</h3>
                  <p><strong>Description:</strong> {product.description}</p>
                  <p><strong>Stock:</strong> {product.stock} units</p>
                </div>
                <div className="product-card-footer">
                  <span className="product-price-label"><strong>{product.price}€</strong></span>
                  <button className="button-addToCart" onClick={() => addToCart(product)} disabled={product.stock <= 0}>
                    {product.stock <= 0 ? "Out of Stock" : "Add to Cart"}
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {showCart && (
        <div className="right-column cart-full-width">
          <div>
            <h2 className="section-shopping">🛒Shopping Cart</h2>
            {cart.length == 0 ? <p>Cart is empty</p> : (
              <div>
                {cart.map(i => (
                  <div className="product-cart" key={i.productId}>
                    <div className="cart-product-mini-image">
                      <img
                        src={i.imageUrl && typeof i.imageUrl === 'string' && i.imageUrl.trim() !== ""
                          ? i.imageUrl.split(",")[0].trim()
                          : "https://via.placeholder.com/150"}
                        alt={i.name} />
                    </div>
                    <p>{i.name} x{i.quantity} - {i.price * i.quantity}€</p>
                    <button onClick={() => removeFromCart(i.productId)}>Remove 🗑️</button>
                  </div>
                ))}
                <br />
                <button onClick={handleCheckout}>Checkout</button>
              </div>
            )}

            <div className="section-order-history">
              <h2 className="title-order-history">📋Order History</h2>
              {customerOrders.length == 0 ? <p>No orders recorded for this customer</p> : customerOrders.map(order => (
                <div className="detail-container" key={order.id}>
                  <p><strong>📦Order #{order.id}</strong> - <strong>Total: {Number(order.total).toFixed(2)}€ </strong>- Status: {order.status}</p>
                  {order.orderDetailList && order.orderDetailList.map(detail => (
                    <p key={detail.id?.productId}>
                      Product ID: {detail.id?.productId} | Quant: {detail.quantity} | Unit Price: {detail.unitPrice}€
                    </p>
                  ))}
                </div>
              ))}
            </div>
          </div>
        </div>
      )}
    </>
  );
}
