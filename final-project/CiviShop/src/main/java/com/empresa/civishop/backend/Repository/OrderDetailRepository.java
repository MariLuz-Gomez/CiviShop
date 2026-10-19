package com.empresa.civishop.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empresa.civishop.backend.Entity.OrderDetail.OrderDetail;
import com.empresa.civishop.backend.Entity.OrderDetail.OrderDetailId;

/**
 * Interfaz de repositorio para gestionar la persistencia de la entidad {@link OrderDetail}.
 * Al extender de {@link JpaRepository}, hereda de forma automática todas las operaciones 
 * de acceso a datos estándar para interactuar con la tabla intermedia "orders_details" de SQLite.
 * <p>
 * A diferencia de otros repositorios comunes, utiliza la clase personalizada {@link OrderDetailId} 
 * como segundo tipo genérico debido a que las líneas de detalle se gestionan mediante una clave compuesta.
 * </p>
 * @author Mariluz Gómez
 * @version 1.0
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId>{
    
}
