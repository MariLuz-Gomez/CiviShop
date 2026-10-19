package com.empresa.civishop.backend.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.empresa.civishop.backend.Entity.OrderDetail.OrderDetail;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidad que representa un pedido de compra en el sistema CiviShop.
 * Mapea directamente con la tabla "orders" en la base de datos.
 * Guarda el cliente asociado, las fechas de la transacción, el estado actual,
 * el coste total y la lista desglosada de líneas de detalle del pedido.
 * 
 * @author Mariluz Gómez
 * @version 1.0
 */
@Entity
@Table(name = "orders")
public class Order {

     /** Identificador único del pedido (Clave Primaria autoincremental). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Cliente que realiza el pedido */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /** Fecha exacta en la que se registró el pedido*/
    @Column(nullable = false)
    private LocalDate orderDate;

    /** Estado actual del pedido */
    @Column(nullable = false)
    private String status;

    /** Importe total económico acumulado del pedido en euros */
    @Column(nullable = false)
    private double total;

    /** 
     * Lista desglosada con los detalles y productos del pedido 
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    /**
     * Constructor por defecto requerido por el framework JPA / Hibernate.
     */
    public Order() {
    }

    /**
     * Constructor completo para inicializar un pedido con todos sus campos
     * @param id El identificador único del pedido.
     * @param customer El cliente asociado.
     * @param orderDate La fecha del pedido.
     * @param status El estado actual del pedido.
     * @param total El coste total acumulado.
     * @param orderDetailList La lista con el desglose de productos.
     */
    public Order(Long id, Customer customer, LocalDate orderDate, String status, double total,
            List<OrderDetail> orderDetailList) {
        this.id = id;
        this.customer = customer;
        this.orderDate = orderDate;
        this.status = status;
        this.total = total;
        this.orderDetailList = orderDetailList;
    }

    /**
     * Constructor corto sin ID, utilizado para crear pedidos nuevos en memoria antes de persistirlos.
     * @param customer El cliente asociado.
     * @param orderDate La fecha del pedido.
     * @param status El estado actual del pedido.
     * @param total El coste total acumulado.
     * @param orderDetailList La lista con el desglose de productos.
     */
    public Order(Customer customer, LocalDate orderDate, String status, double total,
            List<OrderDetail> orderDetailList) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.status = status;
        this.total = total;
        this.orderDetailList = orderDetailList;
    }

    /**
     * Obtiene el identificador único del pedido.
     * @return El ID del pedido.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del pedido si no es nulo.
     * @param id El nuevo identificador a asignar.
     */
    public void setId(Long id) {
        if (id != null) {
            this.id = id;
        }
    }

    /**
     * Obtiene la fecha en la que se tramitó el pedido.
     * @return Objeto {@link LocalDate} con la fecha.
     */
    public LocalDate getOrderDate() {
        return orderDate;
    }

    /**
     * Establece la fecha del pedido si el objeto proporcionado no es nulo.
     * @param orderDate La nueva fecha de registro.
     */
    public void setOrderDate(LocalDate orderDate) {
        if (orderDate != null) {
            this.orderDate = orderDate;
        }
    }

    /**
     * Obtiene el estado de gestión actual del pedido.
     * @return El estado del pedido.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Establece el estado del pedido validando que la cadena no sea nula ni vacía.
     * @param status El nuevo estado a asignar.
     */
    public void setStatus(String status) {
        if (status != null && !status.trim().isEmpty()) {
            this.status = status;
        }
    }

    /**
     * Obtiene el coste económico total calculado del pedido.
     * @return El total en euros (€).
     */
    public double getTotal() {
        return total;
    }

    /**
     * Establece el coste económico total del pedido siempre que sea un valor diferente de cero.
     * @param total El nuevo importe total.
     */
    public void setTotal(double total) {
        if (total != 0) {
            this.total = total;
        }
    }

    /**
     * Obtiene el cliente asociado que realizó la compra.
     * @return Objeto {@link Customer} dueño del pedido.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Asocia un cliente al pedido si el objeto proporcionado no es nulo.
     * @param customer El nuevo cliente a vincular.
     */
    public void setCustomer(Customer customer) {
        if (customer != null) {
            this.customer = customer;
        }
    }

    /**
     * Obtiene la lista desglosada con los detalles y artículos comprados en el pedido.
     * Inicializa una lista vacía si el objeto interno es nulo para prevenir NullPointerException.
     * @return Lista de objetos {@link OrderDetail}.
     */
    public List<OrderDetail> getOrderDetailList() {
        if (orderDetailList == null) {
            orderDetailList = new ArrayList<>();
        }
        return orderDetailList;
    }

    /**
     * Reemplaza la lista completa de detalles del pedido.
     * @param orderDetailList Nueva lista desglosada de compras.
     */
    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    /**
     * Método helper utilitario para añadir de forma bidireccional un producto al pedido.
     * Crea de manera automática la línea de detalle, asigna precios unitarios dinámicos
     * y recalcula de forma incremental el importe total en euros de la factura del pedido.
     * @param product El objeto {@link Product} que el cliente añade a su cesta.
     * @param quantity La cantidad solicitada de dicho artículo.
     */
    public void addProduct(Product product, int quantity) {
        OrderDetail detail = new OrderDetail();
        detail.setOrder(this);
        detail.setProduct(product);
        detail.setQuantity(quantity);
        detail.setUnitPrice(product.getPrice());

        this.orderDetailList.add(detail);
        this.total = this.total + (product.getPrice() * quantity);
    }

    /**
     * Devuelve una representación en texto del pedido con sus campos clave estructurados.
     * Muestra el identificador numérico de su cliente y la cantidad de líneas registradas.
     * @return Cadena de texto descriptiva de la entidad {@code Order}.
     */
    @Override
    public String toString() {
        return "Order " + id + ",\n customer: " + customer.getId() + ",\n orderDate: " + orderDate + ",\n status: " + status + ",\n total: " + total + ",\n orderDetailList: " + orderDetailList.size();
    }

}
