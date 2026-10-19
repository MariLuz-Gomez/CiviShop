package com.empresa.civishop.backend.Entity.OrderDetail;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

/**
 * Clase que representa la clave primaria compuesta para la entidad OrderDetail.
 * Al estar marcada con {@link Embeddable}, puede ser incrustada en otras entidades.
 * Implementa {@link Serializable} para cumplir con los requisitos de persistencia de JPA.
 * 
 * @author Mariluz Gómez
 * @version 1.0
 */
@Embeddable
public class OrderDetailId implements Serializable {
    
    /** Identificador único del pedido asociado */
    private Long orderId;
    
    /** Identificador único del producto asociado */
    private Long productId;
    
    /**
     * Constructor por defecto requerido por Hibernate para la correcta instanciación de la clave
     */
    public OrderDetailId() {}

    /**
     * Constructor completo para inicializar la clave compuesta con las identidades del pedido y del producto.
     * @param orderId El identificador del pedido.
     * @param productId El identificador del producto.
     */
    public OrderDetailId(Long orderId, Long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    /**
     * Obtiene el identificador del pedido que forma parte de la clave.
     * @return El ID del pedido.
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * Establece el identificador del pedido dentro de la clave compuesta
     * @param orderId El nuevo ID del pedido a asignar.
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * Obtiene el identificador del producto que forma parte de la clave
     * @return El ID del producto.
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * Establece el identificador del producto dentro de la clave compuesta
     * @param productId El nuevo ID del producto a asignar
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * Compara este objeto de clave compuesta con otro para verificar su igualdad.
     * Es crítico para JPA que este método compare correctamente ambos identificadores
     * para gestionar la caché de primer nivel y las búsquedas en base de datos
     * @param o Objeto con el que se va a comparar
     * @return {@code true} si los identificadores de pedido y producto coinciden; {@code false} en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailId that = (OrderDetailId) o;
        return Objects.equals(orderId, that.orderId) && 
               Objects.equals(productId, that.productId);
    }

    /**
     * Genera un código hash único basado en el par de identificadores (orderId y productId).
     * Requerido junto con {@code equals} para el almacenamiento y la gestión óptima de las 
     * claves compuestas dentro de colecciones y mapas de Hibernate.
     * @return El valor numérico hash calculado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }
}
