package com.empresa.civishop.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.civishop.backend.Entity.Product;
import com.empresa.civishop.backend.Service.ProductService;

/**
 * Controlador REST que gestiona las peticiones HTTP externas relacionadas con los productos.
 * Define los endpoints públicos bajo la ruta raíz "/api/products" y configura el mecanismo 
 * de seguridad CORS para habilitar el intercambio de recursos con el frontend de React.
 * @author Mariluz Gómez
 * @version 1.0
 */
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    /** Servicio inyectado de forma automática para ejecutar las reglas de negocio de los productos. */
    @Autowired
    private ProductService productService;

    /**
     * Recupera el listado completo de todos los productos y artículos disponibles en el catálogo.
     * Mapea peticiones HTTP GET directas a la raíz del recurso.
     * @return Una lista {@link List} que contiene todos los objetos {@link Product}.
     */
    @GetMapping
    public List<Product> getAllProducts(){
        return productService.findAll();
    }

    /**
     * Busca y recupera la ficha de un producto concreto utilizando su ID dinámico en la ruta URL.
     * @param id El identificador único del producto enviado en la ruta de red.
     * @return El objeto {@link Product} localizado, o {@code null} si el ID no existe en el catálogo.
     */
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        if (productService.findById(id).isPresent()) {
            return productService.findById(id).get();
        }
        return null;
    }

    /**
     * Añade y persiste un nuevo artículo en el catálogo de productos a partir de un objeto JSON.
     * Mapea peticiones HTTP POST externas. 
     * @param product Objeto {@link Product} deserializado de manera automática desde el cuerpo de la petición.
     * @return El objeto {@link Product} guardado con éxito y con su identificador único asignado.
     */
    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return productService.save(product);
    }

    /**
     * Modifica las propiedades de un producto del catálogo (nombre, descripción, precio, stock o categoría).
     * Realiza un mapeo de peticiones HTTP PUT externas localizando el registro original mediante su ID.
     * @param id El identificador único del producto que se desea modificar.
     * @param product Objeto que contiene las nuevas propiedades comerciales enviadas desde React en formato JSON.
     * @return El objeto {@link Product} actualizado y guardado en la base de datos, o {@code null} si el ID no es válido.
     */
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        if (productService.findById(id).isPresent()) {
            Product productoExistente = productService.findById(id).get();
            productoExistente.setName(product.getName());
            productoExistente.setDescription(product.getDescription());
            productoExistente.setPrice(product.getPrice());
            productoExistente.setStock(product.getStock());
            productoExistente.setCategory(product.getCategory());
            return productService.save(productoExistente);
        }
        return null;
    }

    /**
     * Elimina físicamente un producto del catálogo del sistema haciendo uso de su identificador único.
     * Mapea peticiones HTTP DELETE externas en la ruta de red. 
     * @param id El identificador único del artículo que se procederá a borrar.
     */
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        if (productService.findById(id).isPresent()) {
            productService.deleteById(id);
        }
    }
}
