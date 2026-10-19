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

import com.empresa.civishop.backend.Entity.Customer;
import com.empresa.civishop.backend.Entity.Order;
import com.empresa.civishop.backend.Service.CustomerService;

/**
 * Controlador REST que gestiona las operaciones HTTP externas para el recurso "Customers" (Clientes).
 * Expone la API pública bajo el punto de acceso centralizado "/api/customers" y habilita 
 * la directiva de seguridad CORS únicamente para las solicitudes originadas desde el frontend de React.
 * @author Mariluz Gómez
 * @version 1.0
 */
@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

    /** Servicio de lógica de negocio inyectado de forma automática para la gestión de clientes. */
    @Autowired
    private CustomerService customerService;

    /**
     * Recupera el listado completo de todos los clientes registrados en la base de datos.
     * Mapea peticiones HTTP GET directas a la raíz del recurso.
     * 
     * @return Una lista {@link List} que contiene a todos los objetos {@link Customer}.
     */
    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.findAll();
    }

    /**
     * Busca y recupera el perfil de un cliente específico utilizando su ID dinámico en la ruta URL.
     * @param id El identificador único del cliente enviado en el parámetro de la ruta.
     * @return El objeto {@link Customer} localizado, o {@code null} si el ID no existe en el sistema.
     */
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        if (customerService.findById(id).isPresent()) {
            return customerService.findById(id).get();
        }
        return null;
    }

    /**
     * Crea y da de alta un nuevo cliente en el sistema a partir de un cuerpo de datos JSON.
     * Mapea peticiones HTTP POST externas.
     * @param customer Objeto {@link Customer} deserializado de forma automática desde la petición HTTP.
     * @return El objeto {@link Customer} guardado con éxito con su clave primaria autoincremental asignada.
     */
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer){
        return customerService.save(customer);
    }

    /**
     * Actualiza por completo las propiedades de un cliente existente localizándolo por su ID.
     * Mapea peticiones HTTP PUT externas y sobreescribe los campos validados de contacto.
     * @param id El identificador único del cliente que se desea actualizar.
     * @param customer Objeto que contiene las nuevas propiedades del perfil en formato JSON.
     * @return El objeto {@link Customer} modificado y persistido, o {@code null} si el cliente no existe.
     */
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        if (customerService.findById(id).isPresent()) {
            Customer clienteExistente = customerService.findById(id).get();
            clienteExistente.setFirstName(customer.getFirstName());
            clienteExistente.setLastName(customer.getLastName());
            clienteExistente.setEmail(customer.getEmail());
            clienteExistente.setPhone(customer.getPhone());
            clienteExistente.setRegistrationDate(customer.getRegistrationDate());
            return customerService.save(clienteExistente);
        }
        return null;
    }

    /**
     * Elimina físicamente a un cliente de la base de datos haciendo uso de su identificador.
     * Mapea peticiones HTTP DELETE en la ruta de red.
     * @param id El identificador único del cliente que se procederá a borrar.
     */
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id){
        if (customerService.findById(id).isPresent()) {
            customerService.deleteById(id);
        }
    }

    /**
     * Recupera en exclusiva el historial desglosado de pedidos pertenecientes a un cliente concreto.
     * Mapea peticiones HTTP GET combinadas bajo la ruta complementaria del recurso.
     * @param id El identificador único del cliente del cual se quieren inspeccionar las compras.
     * @return Una lista {@link List} que contiene las compras {@link Order} del usuario, 
     *         o {@code null} si no existe el registro de dicho cliente.
     */
    @GetMapping("/{id}/orders")
    public List<Order> getOrdersCustomerById(@PathVariable Long id) {
        if (customerService.findById(id).isPresent()) {
            return customerService.findById(id).get().getOrdersList();
        }
        return null;
    }
}
