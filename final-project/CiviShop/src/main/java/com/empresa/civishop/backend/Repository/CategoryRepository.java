package com.empresa.civishop.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empresa.civishop.backend.Entity.Category;

/**
 * Interfaz de repositorio para gestionar la persistencia de la entidad {@link Category}.
 * Al extender de {@link JpaRepository}, hereda de forma automática todas las operaciones 
 * CRUD estándar (guardar, buscar por ID, listar, eliminar, paginar) de Spring Data JPA.
 * Mapea directamente sobre la tabla "categories" de la base de datos.
 * @author Mariluz Gómez
 * @version 1.0
 */
public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}
