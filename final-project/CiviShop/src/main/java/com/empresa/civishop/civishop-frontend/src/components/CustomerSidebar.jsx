export default function CustomerSidebar({ data, activeCustomer, submitForm, handleSelectCustomer }) {
  return (
    <div className="left-column">
      <h2 className="section-list-customer">👤 List Customer</h2>
      {activeCustomer && (
        <p><strong>📌Selected Customer:</strong> {activeCustomer.firstName} {activeCustomer.lastName}</p>
      )}
      
      {data.customers.length == 0 ? <p>No customers registered yet</p> : (
        <ul>
          {data.customers.map(customer => (
            <li key={customer.id}>
              <button type="button" onClick={() => handleSelectCustomer(customer)}>
                Select: {customer.firstName} {customer.lastName}
              </button>
              <br />
              <strong>Email:</strong> {customer.email} | <strong>Phone:</strong> {customer.phone}
            </li>
          ))}
        </ul>
      )}

      <h2 className="section-register-customer">🙋🏻‍♂️Register Customer</h2>
      <form onSubmit={event => submitForm(event, "customers", "Customer registered!")}>
        <input name="firstName" placeholder="First Name" required />
        <input name="lastName" placeholder="Last Name" required />
        <input name="email" type="email" placeholder="Email" required />
        <input name="phone" placeholder="Phone" required />
        <button type="submit">Register</button>
      </form>
    </div>
  );
}
