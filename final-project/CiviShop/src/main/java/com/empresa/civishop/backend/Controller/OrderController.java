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

import com.empresa.civishop.backend.Entity.Order;
import com.empresa.civishop.backend.Entity.OrderDetail.OrderDetail;
import com.empresa.civishop.backend.Service.OrderService;

/**
 * Controlador REST que gestiona las peticiones HTTP externas relacionadas con los pedidos (Orders).
 * Define los endpoints bajo la ruta raíz "/api/orders" e introduce la directiva de seguridad 
 * CORS para permitir el intercambio de recursos de origen cruzado únicamente con el puerto de React.
 * @author Mariluz Gómez
 * @version 1.0
 */
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    /** Servicio inyectado automáticamente para procesar la lógica de negocio y reglas de transacciones de pedidos. */
    @Autowired
    private OrderService orderService;

    /**
     * Recupera el listado completo de todas las órdenes e históricos de compra del sistema.
     * Mapea peticiones HTTP GET directas a la raíz del recurso.
     * 
     * @return Una lista {@link List} que contiene todos los objetos {@link Order}.
     */
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    /**
     * Busca y recupera un pedido concreto en la base de datos utilizando su identificador único.
     * @param id El identificador único del pedido enviado en el parámetro de la ruta.
     * @return El objeto {@link Order} localizado, o {@code null} si el ID no existe en el sistema.
     */
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        if (orderService.findById(id).isPresent()) {
            return orderService.findById(id).get();
        }
        return null;
    }

    /**
     * Tramita el proceso de compra de un carrito de artículos (Checkout) vinculándolo a un cliente.
     * Mapea peticiones HTTP POST externas bajo una ruta compuesta que identifica al comprador.
     * @param customerId El identificador único del cliente que realiza la transacción económica.
     * @param items Estructura de mapa con los pares clave/valor del carrito (ID de producto y cantidad).
     * @return El objeto {@link Order} final generado de forma exitosa con sus totales calculados.
     */
    @PostMapping("/customer/{customerId}")
    public Order checkout(@PathVariable Long customerId, @RequestBody List<java.util.Map<String, Object>> items) {
        return orderService.createOrder(customerId, items);
    }

    /**
     * Modifica o actualiza por completo el estado y campos de un pedido existente forzando su ID.
     * Mapea peticiones HTTP PUT externas.
     * @param id El identificador único del pedido que se desea modificar en la base de datos.
     * @param order El objeto {@link Order} que contiene las nuevas propiedades en formato JSON.
     * @return El objeto {@link Order} guardado y actualizado con éxito, o {@code null} si el ID no es válido.
     */
    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order result = null;
        if (orderService.findById(id).isPresent()) {
            order.setId(id);
            result = orderService.save(order);
        }
        return result;
    }

    /**
     * Elimina físicamente un pedido y su rastro de la base de datos haciendo uso de su identificador.
     * Mapea peticiones HTTP DELETE en la ruta de red.
     * @param id El identificador único de la orden que se procederá a borrar.
     */
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        if (orderService.findById(id).isPresent()) {
            orderService.deleteById(id);
        }
    }

    /**
     * Recupera en exclusiva la sublista desglosada con las líneas de detalle pertenecientes a un pedido concreto.
     * Mapea peticiones HTTP GET combinadas bajo la ruta de detalle del recurso.
     * @param id El identificador único del pedido contenedor del cual se quiere extraer el desglose.
     * @return Una lista {@link List} que contiene los objetos {@link OrderDetail} asociados a la compra,
     *         o {@code null} si la orden no existe en el sistema.
     */
    @GetMapping("/{id}/ordersDetails")
    public List<OrderDetail> getOrdersDetailsOrderById(@PathVariable Long id) {
        if (orderService.findById(id).isPresent()) {
            return orderService.findById(id).get().getOrderDetailList();
        }
        return null;
    }
}
