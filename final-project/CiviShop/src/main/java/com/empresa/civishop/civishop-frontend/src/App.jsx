import { useState, useEffect } from 'react';
import axios from 'axios';
import CustomerSidebar from './components/CustomerSidebar';
import AdminPanel from './components/AdminPanel';
import StoreView from './components/StoreView';
import './App.css';

const API = 'http://localhost:8080/api';

function App() {
  const [data, setData] = useState({ products: [], categories: [], customers: [], orders: [] });
  const [cart, setCart] = useState([]);
  const [view, setView] = useState('welcome');
  const [message, setMessage] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('ALL');
  const [activeCustomer, setActiveCustomer] = useState(null);

  const loadData = () => {
    Promise.all([
      axios.get(`${API}/products`), axios.get(`${API}/categories`),
      axios.get(`${API}/customers`), axios.get(`${API}/orders`)
    ]).then(([products, categories, customer, orders]) => setData({ products: products.data, categories: categories.data, customers: customer.data, orders: orders.data }))
      .catch(e => console.error("Error", e));
  };

  useEffect(() => { loadData(); }, []);

  const addToCart = (product) => {
    if (product.stock <= 0) {
      return alert("Out of Stock");
    }
    const existing = cart.find(i => i.productId == product.id);
    if (existing) {
      if (existing.quantity >= product.stock) {
        return alert("Out of Stock");
      }
      setCart(cart.map(i => i.productId == product.id ? { ...i, quantity: i.quantity + 1 } : i));
    } else {
      setCart([...cart, { productId: product.id, name: product.name, price: product.price, imageUrl: product.imageUrl, quantity: 1 }]);
    }
  };

  const removeFromCart = (id) => {
    setCart(cart.map(i => i.productId === id ? { ...i, quantity: i.quantity - 1 } : i).filter(i => i.quantity > 0));
  };

  const handleCheckout = () => {
    if (!activeCustomer) {
      return alert("Please, chooose user for make a order.");
    }
    if (!cart.length) {
      return alert("Cart is empty");
    }
    const customerId = activeCustomer.id;
    const body = cart.map(i => ({ productId: i.productId, quantity: i.quantity }));

    axios.post(`${API}/orders/customer/${customerId}`, body)
      .then(r => {
        setMessage(`Order #${r.data.id} created!`);
        setCart([]);
        loadData();
      }).catch(() => alert("Error creating order"));
  };

  const submitForm = (event, url, message) => {
    event.preventDefault();
    const body = Object.fromEntries(new FormData(event.target));
    if (!body.registrationDate) {
      body.registrationDate = new Date().toISOString().split('T')[0];
    }

    axios.post(`${API}/${url}`, body)
      .then(() => {
        setMessage(message);
        setTimeout(() => { setMessage(""); }, 8000)
        event.target.reset();
        loadData();
      }).catch(e => console.error(e));
  };

  const handleUpdatePrice = (productId) => {
    const newPrice = prompt("Enter the new price (€):");
    if (!newPrice || isNaN(newPrice)) {
      return alert("Please enter a valid number");
    }
    let productoOriginal = null;
    for (let i = 0; i < data.products.length; i++) {
      if (data.products[i].id == productId) {
        productoOriginal = data.products[i];
      }
    }
    if (!productoOriginal) {
      return alert("Product not found in memory");
    } else if (newPrice <= 0) {
      return alert("Price can`t minor than 0")
    }

    const updatedProductBody = {
      id: productoOriginal.id,
      name: productoOriginal.name,
      description: productoOriginal.description,
      price: parseFloat(newPrice),
      stock: productoOriginal.stock,
      category: productoOriginal.category
    };
    axios.put(API + "/products/" + productId, updatedProductBody)
      .then(() => {
        setMessage("Product price updated successfully!");
        setTimeout(() => { setMessage(""); }, 8000)
        loadData();
      })
      .catch(() => {
        alert("Error updating price.");
      });
  };

  const handleDeleteProduct = (productId) => {
    if (!window.confirm("⚠️ Are you sure you want to delete this product?")) return;
    let inOrder = false;
    for (let i = 0; i < data.orders.length; i++) {
      const orden = data.orders[i];
      if (orden && orden.orderDetailList) {
        for (let j = 0; j < orden.orderDetailList.length; j++) {
          const detalle = orden.orderDetailList[j];
          if (detalle && detalle.id && detalle.id.productId == productId) {
            inOrder = true;
          }
        }
      }
    }
    if (inOrder) {
      alert("❌ Can't delete this product. This product is linked to an existing customer order.");
      return;
    }
    axios.delete(`${API}/products/${productId}`)
      .then(() => {
        setMessage("Product deleted!");
        setTimeout(() => { setMessage(""); }, 8000)
        loadData();
      })
      .catch(() => {
        alert("Error deleting product.");
      });
  };

  const handleDeleteCategory = (categoryId) => {
    if (!window.confirm("⚠️ Are you sure you want to delete this category?")){
      return;
    } 
    let inProduct = false;
    for (let i = 0; i < data.products.length; i++) {
      const producto = data.products[i];
      if (producto && producto.category) {
        if (producto.category.id == categoryId) {
          inProduct = true;
        }
      }
    }

    if (inProduct) {
      alert("❌ Can't delete this category. This category is linked to an existing product.");
    }
    axios.delete(`${API}/categories/${categoryId}`)
      .then(() => {
        setMessage("Category deleted!");
        setTimeout(() => { setMessage(""); }, 8000);
        loadData();
      })
      .catch(() => {
        alert("Error deleting category.");
      });
  };

  const handleToggleOrderStatus = (orderId, currentStatus) => {
    let existingOrder = null;
    for (let i = 0; i < data.orders.length; i++) {
      if (data.orders[i].id == orderId) {
        existingOrder = data.orders[i];
      }
    }
    if (!existingOrder) {
      return alert("Order not found in memory");
    }
    let nuevoEstado = "PENDING";
    if (currentStatus == "PENDING") {
      nuevoEstado = "COMPLETED";
    }
    const updatedOrderBody = {
      id: existingOrder.id,
      status: nuevoEstado,
      orderDate: existingOrder.orderDate,
      total: existingOrder.total,
      customer: existingOrder.customer
    };
    axios.put(`${API}/orders/${orderId}`, updatedOrderBody)
      .then(() => {
        setMessage("Order updated!");
        setTimeout(() => { setMessage(""); }, 8000)
        loadData();
      })
      .catch(() => {
        alert("Error updating order status.");
      });
  };

  const filteredProducts = data.products.filter(product => {
    if (selectedCategory == "ALL") {
      return true;
    }
    if (!product.category) {
      return false;
    }
    return product.category.id == selectedCategory;
  });

  let customerOrders = [];
  if (activeCustomer) {
    for (let i = 0; i < data.orders.length; i++) {
      const order = data.orders[i];
      if (order && order.customer) {
        if (order.customer.id == activeCustomer.id) {
          customerOrders.push(order);
        }
      }
    }
  }

  const isAdmin = activeCustomer && activeCustomer.firstName.toLowerCase() == "admin";

  const goToUsersScreen = () => {
    setActiveCustomer(null);
    setView('welcome');
  };

  const goToCatalogScreen = () => {
    if (!activeCustomer) {
      alert("Please, choose a User or Admin profile first.");
      return;
    }
    setView('catalog');
  };

  const goToCartScreen = () => {
    if (!activeCustomer) {
      alert("Please, choose a User profile first.");
      return;
    }

    if (activeCustomer.firstName.toLowerCase() == "admin") {
      alert("Admin profile doesn't have a shopping cart. Please pick a regular Customer.");
      return;
    }
    setView('cart');
  };

  const handleSelectCustomer = (customer) => {
    if (String(customer.firstName || "").toLowerCase().trim() == "admin") {
      const password = prompt("🔐 Enter admin's password:");
      if (password == "0000") {
        setActiveCustomer(customer);
        setMessage("🔓 Welcome Admin!");
        setTimeout(() => { setMessage(""); }, 8000)
        setView('catalog');
      } else {
        alert("❌ Incorrect password. Access denied");
      }
    } else {
      setActiveCustomer(customer);
      setView('catalog');
    }
  };

  return (
    <div>
      <header className="navbar-header">
        <div className="nav-logo">🛍️ CiviShop</div>
        <nav className="nav-links">
          <button onClick={goToUsersScreen} className={view === 'welcome' ? 'active-nav' : ''}>User</button>
          <button onClick={goToCatalogScreen} className={view === 'catalog' ? 'active-nav' : ''}>Catalog</button>
          <button onClick={goToCartScreen} className={view === 'cart' ? 'active-nav' : ''}>Cart</button>
        </nav>
      </header>

      {isAdmin ? (
        <div className="title-container">
          <h1 className="main-title"> ⚙️ CiviShop Dashboard</h1>
        </div>
      ) : (
        <div className="title-container">
          <h1 className="main-title"> ✨ Smart Shopping, Better Living</h1>
        </div>
      )}

      {message && <div className='message'><p><strong>{message}</strong></p></div>}

      <div className="app-content-wrapper">
        {view === 'welcome' && (
          <div className="welcome-view-box">
            <h2>Welcome To CiviShop</h2>
            <p className="welcome-subtitle">Please choose a profile to start or register a new client</p>

            <CustomerSidebar
              data={data}
              activeCustomer={activeCustomer}
              setActiveCustomer={setActiveCustomer}
              submitForm={submitForm}
              handleSelectCustomer={handleSelectCustomer}
            />
          </div>
        )}

        {view === 'catalog' && (
          <div className="app-container">
            {isAdmin ? (
              <AdminPanel
                API={API}
                data={data}
                filteredProducts={filteredProducts}
                handleUpdatePrice={handleUpdatePrice}
                handleToggleOrderStatus={handleToggleOrderStatus}
                handleDeleteProduct={handleDeleteProduct}
                handleDeleteCategory={handleDeleteCategory}
                submitForm={submitForm}
                setMessage={setMessage}
                loadData={loadData}
                selectedCategory={selectedCategory}
                setSelectedCategory={setSelectedCategory}
              />
            ) : (
              <div>
                <div className="info-buyer-bar">
                  👤 Active Customer: {activeCustomer ? activeCustomer.firstName : ''}
                </div>
                <StoreView
                  data={data} filteredProducts={filteredProducts} cart={cart} customerOrders={customerOrders}
                  selectedCategory={selectedCategory} setSelectedCategory={setSelectedCategory}
                  addToCart={addToCart} removeFromCart={removeFromCart} handleCheckout={handleCheckout}
                  showCart={false}
                />

                <div className="bottom-nav-box">
                  <button onClick={() => setView('cart')} className="go-to-cart-action-btn">
                    🛒 View My Shopping Cart ({cart.length} items) ➡️
                  </button>
                </div>
              </div>
            )}
          </div>
        )}

        {view === 'cart' && activeCustomer && !isAdmin && (
          <div className="cart-view-box">
            <div className="info-success-bar">
              Shopping Cart checkout for: {activeCustomer.firstName} {activeCustomer.lastName}
            </div>

            <StoreView
              data={data} filteredProducts={[]} cart={cart} customerOrders={customerOrders}
              selectedCategory={selectedCategory} setSelectedCategory={setSelectedCategory}
              addToCart={addToCart} removeFromCart={removeFromCart} handleCheckout={handleCheckout}
              showCart={true}
            />
          </div>
        )}
      </div>
      <footer className="footer">
        <p>© 2026 CiviShop — Smart Shopping, Better Living.</p>
        <p className="footer-subtitle">Built with React ⚛️ and Spring Boot ☕</p>
        <div className="social-icons">
          <a href="https://github.com/" target="_blank">🐙 Git Hub</a>
          <a href="https://linkedin.com/" target="_blank">💼 LinkedIn</a>
          <a href="https://instagram.com/" target="_blank">📸 Instagram</a>
          <a href="https://twitter.com/" target="_blank">🐦 Twitter</a>
        </div>
      </footer>
    </div>
  );

}

export default App;
