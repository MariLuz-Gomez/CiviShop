package com.empresa.civishop.backend.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidad que representa una categoría de productos en el sistema CiviShop.
 * Mapea directamente con la tabla "categories" en la base de datos.
 * 
 * @author Mariluz Gómez
 * @version 1.0
 */
@Entity
@Table(name = "categories")
public class Category {
     /** Identificador único de la categoría */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre de la categoría. No puede ser nulo en la base de datos */
    @Column(nullable = false)
    private String name;

    /** 
     * Lista de productos que pertenecen a esta categoría.
     * Utiliza carga perezosa (LAZY) y borrado en cascada (ALL).
     * Se ignora en la serialización JSON para evitar bucles recursivos con Jackson.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Category() {}
    
    /**
     * Constructor completo para inicializar una categoría con ID y nombre.
     * @param id El identificador de la categoría.
     * @param name El nombre de la categoría.
     */
    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    /**
     * Constructor corto para crear categorías nuevas antes de guardarlas en la base de datos.
     * @param name El nombre de la categoría.
     */
    public Category(String name) {
        this.name = name;
    }
    
    /**
     * Obtiene el identificador único de la categoría.
     * @return El ID de la categoría.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Establece el identificador de la categoría si el valor proporcionado no es nulo.
     * @param id El nuevo ID a asignar.
     */
    public void setId(Long id) {
        if (id != null) {
            this.id = id;
        }
    }

    /**
     * Obtiene el nombre de la categoría.
     * @return El nombre actual.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre de la categoría validando que no sea nulo ni esté vacío.
     * @param name El nuevo nombre a asignar.
     */
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }

    /**
     * Obtiene la lista de productos asociados a esta categoría.
     * Si la lista es nula, inicializa una nueva lista vacía para prevenir NullPointerException.
     * @return Lista de objetos {@link Product}.
     */
    public List<Product> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }

    /**
     * Asigna una lista completa de productos a la categoría.
     * @param products Nueva lista de productos.
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Método helper para añadir un producto a la categoría de forma bidireccional.
     * Vincula el producto a esta categoría y añade el producto a la lista local.
     * @param product El objeto {@link Product} que se desea asociar.
     */
    public void addProduct(Product product){
        getProducts().add(product);
        product.setCategory(this);
    }

    /**
     * Método helper para desvincular un producto de esta categoría de forma bidireccional.
     * Rompe el enlace quitando el producto de la lista y limpiando su categoría.
     * @param product El objeto {@link Product} que se desea retirar.
     */
    public void removeProduct(Product product){
        getProducts().remove(product);
        product.setCategory(null);
    }

    /**
     * Genera una representación textual de la categoría con sus datos clave.
     * @return Cadena de texto descriptiva de la categoría.
     */
    @Override
    public String toString() {
        return "Category " + id + ", with name " + name + " and has " + products.size() + " products";
    }

    
}
