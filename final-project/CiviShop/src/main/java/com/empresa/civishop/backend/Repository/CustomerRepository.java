package com.empresa.civishop.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empresa.civishop.backend.Entity.Customer;

/**
 * Interfaz de repositorio para gestionar la persistencia de la entidad {@link Customer}.
 * Hereda todas las operaciones CRUD estándar de {@link JpaRepository} para comunicarse 
 * de forma directa con la tabla "customers" en la base de datos SQLite.
 * @author Mariluz Gómez
 * @version 1.0
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    /**
     * Busca un cliente en la base de datos a través de su número de teléfono único.
     * Utiliza la convención de nomenclatura de Spring Data (Query Method) para generar
     * la consulta SQL de manera automática en tiempo de ejecución.
     * 
     * @param phone El número de teléfono del cliente en formato String.
     * @return Un objeto {@link Optional} que contiene al {@link Customer} si fue localizado,
     *         o un contenedor vacío si no existe ningún registro con ese teléfono.
     */
    Optional<Customer> findByPhone(String phone);
}
