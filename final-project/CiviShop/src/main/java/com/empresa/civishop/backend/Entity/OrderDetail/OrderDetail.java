package com.empresa.civishop.backend.Entity.OrderDetail;

import com.empresa.civishop.backend.Entity.Order;
import com.empresa.civishop.backend.Entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

/**
 * Entidad que representa la línea de detalle desglosada de un pedido en el sistema CiviShop.
 * Mapea directamente con la tabla intermedia "orders_details" en la base de datos.
 * Vincula un producto específico con un pedido utilizando una clave compuesta incrustada,
 * registrando la cantidad adquirida y el precio unitario en el momento de la compra.
 * 
 * @author Mariluz Gómez
 * @version 1.0
 */
@Entity
@Table(name = "orders_details")
public class OrderDetail {

    /** Identificador compuesto incrustado de la línea de detalle (ID de pedido + ID de producto). */
    @EmbeddedId
    private OrderDetailId id = new OrderDetailId();

    /** Producto asociado a esta línea de detalle de la compra (Relación Muchos a Uno). */
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    /** 
     * Pedido al que pertenece esta línea de detalle (Relación Muchos a Uno).
     * Se marca con JsonIgnore para evitar la serialización cíclica infinita con Jackson.
     */
    @JsonIgnore
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;
    
    /** Cantidad comprada de este producto. No puede ser nula. */
    @Column(nullable = false)
    private int quantity;

    /** Precio unitario del producto fijado en el momento exacto de la compra. No puede ser nulo. */
    @Column(nullable = false)
    private double unitPrice;

    /**
     * Constructor por defecto requerido por el framework JPA / Hibernate.
     */
    public OrderDetail() {}

    /**
     * Constructor completo para inicializar una línea de detalle con todas sus referencias y valores.
     * El constructor asocia directamente el producto, pedido, cantidad y coste unitario.
     * @param id El identificador compuesto incrustado.
     * @param product El producto comprado.
     * @param order El pedido contenedor.
     * @param quantity La cantidad solicitada del artículo.
     * @param unitPrice El precio unitario histórico de la transacción.
     */
    public OrderDetail(OrderDetailId id, Product product, Order order, int quantity, double unitPrice) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    /**
     * Obtiene el identificador compuesto incrustado de la línea de detalle.
     * @return El objeto {@link OrderDetailId} actual.
     */
    public OrderDetailId getId() {
        return id;
    }
    
    /**
     * Establece el identificador compuesto incrustado si no es nulo.
     * @param id El nuevo identificador compuesto a asignar.
     */
    public void setId(OrderDetailId id) {
        if (id != null) {
            this.id = id;
        }
    }
    
    /**
     * Obtiene el producto asociado a esta línea de compra.
     * @return El objeto {@link Product} de la línea.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Asocia un producto a la línea de detalle siempre que la referencia no sea nula.
     * @param product El nuevo producto a vincular.
     */
    public void setProduct(Product product) {
        if (product != null) {
            this.product = product;
        }
    }

    /**
     * Obtiene el pedido contenedor al que pertenece la línea de compra.
     * @return El objeto {@link Order} padre.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Vincula el pedido contenedor a esta línea de compra si la referencia no es nula.
     * @param order El nuevo pedido padre a asignar.
     */
    public void setOrder(Order order) {
        if (order != null) {
            this.order = order;
        }
    }
    
    /**
     * Obtiene la cantidad de unidades compradas de este producto.

     * @return El número de unidades.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Establece la cantidad de unidades compradas validando que no sea equivalente a cero.
     * @param quantity El nuevo número de unidades.
     */
    public void setQuantity(int quantity) {
        if (quantity != 0) {
            this.quantity = quantity;
        }
    }

    /**
     * Obtiene el precio unitario histórico guardado en esta línea. 
     * @return El coste unitario de la compra en euros (€).
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Establece el precio unitario de la línea de compra validando que sea diferente de cero.
     * @param unitPrice El nuevo precio unitario histórico.
     */
    public void setUnitPrice(double unitPrice) {
         if (unitPrice != 0) {
             this.unitPrice = unitPrice;
        }
    }

    /**
     * Devuelve una representación textual de la línea de detalle con sus identificadores cruzados.
     * Muestra de manera segura las claves numéricas de acceso de los objetos relacionados.
     * @return Cadena de texto descriptiva de la entidad {@code OrderDetail}.
     */
    @Override
    public String toString() {
        return "OrderDetail " + id + ",\n product: " + product.getId() + ",\n order: " + order.getId() + ",\n quantity: " + quantity
                + ",\n unitPrice: " + unitPrice;
    }
}
