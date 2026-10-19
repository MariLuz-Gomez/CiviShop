package com.empresa.civishop.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.empresa.civishop.backend.Entity.Product;

/**
 * Interfaz de repositorio para gestionar la persistencia de la entidad {@link Product}.
 * Extiende de {@link JpaRepository} para heredar de forma automática todas las operaciones 
 * de acceso a datos estándar (CRUD) sobre la tabla "products" en la base de datos SQLite. 
 * @author Mariluz Gómez
 * @version 1.0
 */
public interface ProductRepository extends JpaRepository<Product, Long>{
    /**
     * Obtiene un producto de manera aleatoria directamente desde la base de datos.
     * Utiliza una consulta SQL nativa personalizada (Native Query) delegada al motor físico
     * de la base de datos, desordenando las filas de la tabla de forma aleatoria
     * y limitando el resultado de retorno a un único registro.
     * @return Un objeto {@link Product} seleccionado al azar, o {@code null} si la tabla está vacía.
     */
    @Query(value = "SELECT * FROM products ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Product findRandomProduct();

    /**
     * Busca un producto en el catálogo a través de su nombre único comercial.
     * Utiliza las convenciones de nomenclatura automáticas de Spring Data JPA (Query Method)
     * para generar la consulta SQL en tiempo de ejecución.
     * @param name El nombre comercial del producto a buscar.
     * @return Un objeto {@link Optional} que envuelve al {@link Product} si fue localizado,
     * o un contenedor vacío si no se encuentra ningún registro que coincida.
     */
    Optional<Product> findByName(String name);
}
