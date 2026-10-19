package com.empresa.civishop.backend.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad que representa un producto del catálogo en el sistema CiviShop.
 * Mapea directamente con la tabla "products" en la base de datos.
 * Almacena la información de inventario, precio, descripción y su relación con la categoría.
 * 
 * @author Mariluz Gómez
 * @version 1.0
 */
@Entity
@Table(name = "products")
public class Product {

    /** Identificador único del producto */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre único comercial del artículo. */
    @Column(nullable = false, unique = true)
    private String name;

    /** Descripción detallada de las características del producto. */
    @Column(nullable = false)
    private String description;

    /** Precio de venta unitario del producto en euros  */
    @Column(nullable = false)
    private double price;

    /** Cantidad disponible del artículo en el almacén */
    @Column(nullable = false)
    private int stock;

    /** Categoría a la que pertenece el producto  */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @Column(nullable = false)
    private String imageUrl;
    /**
     * Constructor por defecto requerido por el framework JPA / Hibernate.
     */
    public Product() {}

    /**
     * Constructor completo para inicializar un producto con todos sus atributos, incluyendo su identificador. 
     * @param id El identificador único del producto.
     * @param name El nombre único del producto.
     * @param description La descripción comercial.
     * @param price El precio unitario en euros.
     * @param stock El número inicial de existencias en almacén.
     * @param category La categoría asignada al producto.
     * @param imageUrl La imagen asignada al producto.
     */
    public Product(Long id, String name, String description, double price, int stock, Category category, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.imageUrl = imageUrl;
    }
    
    /**
     * Constructor corto sin ID, utilizado para dar de alta productos nuevos antes de persistirlos en la base de datos.
     * @param name El nombre único del producto.
     * @param description La descripción comercial.
     * @param price El precio unitario en euros.
     * @param stock El numero de existencias en almacén.
     * @param category La categoria asignada al producto.
     * @param imageUrl La imagen asignada al producto.
     */
    public Product(String name, String description, double price, int stock, Category category, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    /**
     * Obtiene el identificador único del producto.
     * @return El ID del producto.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Establece el identificador único del producto si no es nulo.
     * @param id El nuevo identificador a asignar.
     */
    public void setId(Long id) {
        if (id != null) {
            this.id = id;
        }
    }

    /**
     * Obtiene el nombre del producto.
     * @return El nombre comercial actual.
     */
    public String getName() {
        return name;
    }

    
    /**
     * Establece el nombre del producto validando que no sea nulo ni consista en espacios vacíos.
     * @param name El nuevo nombre a asignar.
     */
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }

    /**
     * Obtiene la descripción detallada del producto.
     * @return La descripción del artículo.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Establece la descripción del producto validando que no sea nula ni consista en espacios vacíos.
     * @param description La nueva descripción a asignar.
     */
    public void setDescription(String description) {
        if (description != null && !description.trim().isEmpty()) {
            this.description = description;
        }
    }

    /**
     * Obtiene el precio unitario del producto.
     * @return El precio en euros (€).
     */
    public double getPrice() {
        return price;
    }

    /**
     * Establece el precio unitario del producto siempre que el valor proporcionado sea diferente de cero. 
     * @param price El nuevo precio comercial.
     */
    public void setPrice(double price) {
        if (price != 0) {
            this.price = price;
        }
    }

    /**
     * Obtiene el stock disponible en almacén para este producto. 
     * @return Las unidades en stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece el stock disponible en almacén validando que no sea un valor negativo.
     * @param stock El nuevo número de existencias.
    */
   public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
        }
    }

    /**
     * Obtiene la categoría asociada a la que pertenece el producto.
     * @return Objeto {@link Category} vinculado.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Asocia una categoría al producto si el objeto proporcionado no es nulo.
     * @param category La nueva categoría a asignar.
     */
    public void setCategory(Category category) {
        if (category != null) {
            this.category = category;
        }
    }

    /**
     * Obtiene la imagen para este producto. 
     * @return La imagen del producto
     */
    public String getImageUrl() {
        return imageUrl;
    }
    
    /**
     * Asocia una imagen al producto si el objeto proporcionado no es nulo.
     * @param imageUrl La imagen a asignar.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Devuelve una cadena de texto estructurada con todos los datos clave del producto.
     * Muestra el identificador numérico de su categoría asociada de forma segura.
     * @return Representación textual de la entidad {@code Product}.
     */
    @Override
    public String toString() {
        return "Product" + id + ",\n name: " + name + ",\n description: " + description + ",\n price: " + price
                + ",\n stock: " + stock + ",\n category: " + category.getId();
    }
}
