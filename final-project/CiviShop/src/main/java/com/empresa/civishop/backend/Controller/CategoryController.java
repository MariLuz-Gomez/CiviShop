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

import com.empresa.civishop.backend.Entity.Category;
import com.empresa.civishop.backend.Entity.Product;
import com.empresa.civishop.backend.Service.CategoryService;

/**
 * Controlador REST que gestiona las peticiones HTTP externas relacionadas con las categorías.
 * Define los endpoints de la API bajo la ruta básica "/api/categories" y habilita 
 * el intercambio de recursos de origen cruzado (CORS) exclusivamente para el puerto de React. 
 * @author Mariluz Gómez
 * @version 1.0
 */
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {
    
    /** Servicio inyectado de forma automática para ejecutar la lógica de negocio de categorías. */
    @Autowired
    private CategoryService categoryService;

    /**
     * Recupera el listado completo de categorías registradas en la plataforma.
     * Mapea peticiones HTTP GET directas a la raíz del recurso.
     * @return Una lista {@link List} que contiene todos los objetos {@link Category}.
     */
    @GetMapping
    public List<Category> getAllCategories(){
        return categoryService.findAll();
    }

    /**
     * Busca y recupera una categoría concreta utilizando su identificador en la URL.
     * @param id El identificador único de la categoría enviado en la ruta.
     * @return El objeto {@link Category} localizado, o {@code null} si el ID no existe.
     */
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id){
        if (categoryService.findById(id).isPresent()) {
            return categoryService.findById(id).get();
        }
        return null;
    }

    /**
     * Registra y persiste una nueva categoría enviada en el cuerpo de la petición.
     * Mapea peticiones HTTP POST externas.
     * @param category Objeto {@link Category} deserializado automáticamente desde el JSON del frontend.
     * @return El objeto {@link Category} guardado de forma exitosa con su ID asignado.
     */
    @PostMapping
    public Category createCategory(@RequestBody Category category){
        return categoryService.save(category);
    }

    /**
     * Modifica los datos de una categoría existente localizándola a través de su ID.
     * Realiza un mapeo de peticiones HTTP PUT para actualizar las propiedades modificadas.
     * @param id El identificador único de la categoría que se desea actualizar.
     * @param category Objeto que contiene las nuevas propiedades enviadas en formato JSON.
     * @return El objeto {@link Category} modificado y guardado, o {@code null} si el ID proporcionado es inválido.
     */
    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        if (categoryService.findById(id).isPresent()) {
            Category categoriaExistente = categoryService.findById(id).get();
            categoriaExistente.setName(category.getName()); // Cambiado por negocio
            return categoryService.save(categoriaExistente);
        }
        return null;
    }

    /**
     * Elimina físicamente una categoría del sistema haciendo uso de su identificador.
     * Mapea peticiones HTTP DELETE en la ruta.
     * @param id El identificador único de la categoría que se procederá a borrar.
     */
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        if (categoryService.findById(id).isPresent()) {
            categoryService.deleteById(id);
        }
    }

    /**
     * Recupera en exclusiva la sublista de productos asociados pertenecientes a una categoría concreta.
     * @param id El identificador único de la categoría de la cual se quieren filtrar los artículos.
     * @return Una lista {@link List} que contiene los objetos {@link Product} de dicha categoría,
     *         o {@code null} si la categoría no existe.
     */
    @GetMapping("/{id}/products")
    public List<Product> getProductsCategoryById(@PathVariable Long id) {
        if (categoryService.findById(id).isPresent()) {
            return categoryService.findById(id).get().getProducts();
        }
        return null;
    }
}
