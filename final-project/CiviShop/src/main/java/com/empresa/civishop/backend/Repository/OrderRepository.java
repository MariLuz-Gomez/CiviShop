package com.empresa.civishop.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.empresa.civishop.backend.Entity.Order;

/**
 * Interfaz de repositorio para gestionar la persistencia de la entidad {@link Order}.
 * Hereda de {@link JpaRepository} todas las funciones de acceso a datos tradicionales (CRUD)
 * para realizar operaciones sobre la tabla "orders" en la base de datos SQLite.
 * @author Mariluz Gómez
 * @version 1.0
 */
public interface OrderRepository extends JpaRepository<Order, Long>{
    /**
     * Obtiene un pedido aleatorio directamente desde la base de datos.
     * Utiliza una consulta SQL nativa personalizada (Native Query) delegada al motor físico
     * de la base de datos, desordenando los registros de la tabla de forma aleatoria
     * y limitando el resultado de retorno a una única fila.
     * @return Un objeto {@link Order} seleccionado de manera aleatoria, o {@code null} si la tabla está vacía.
     */
    @Query(value = "SELECT * FROM orders ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Order findRandomOrder();
}
