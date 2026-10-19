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

import com.empresa.civishop.backend.Entity.OrderDetail.OrderDetail;
import com.empresa.civishop.backend.Entity.OrderDetail.OrderDetailId;
import com.empresa.civishop.backend.Service.OrderDetailService;

/**
 * Controlador REST que gestiona las peticiones HTTP externas relacionadas con las líneas de detalle de los pedidos.
 * Define los endpoints bajo la ruta raíz "/api/ordersDetails" e introduce la directiva de seguridad
 * CORS para permitir solicitudes exclusivamente desde el puerto local de React.
 * @author Mariluz Gómez
 * @version 1.0
 */
@RestController
@RequestMapping("/api/ordersDetails")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderDetailController {

    /** Servicio inyectado de forma automática para ejecutar la lógica de persistencia de las líneas de detalle. */
    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * Recupera el listado completo de todas las líneas de detalle de pedidos guardadas en el sistema.
     * Mapea peticiones HTTP GET directas a la raíz del recurso.
     * @return Una lista {@link List} que contiene todos los objetos {@link OrderDetail}.
     */
    @GetMapping
    public List<OrderDetail> getAllOrdersDetails(){
        return orderDetailService.findAll();
    }

    /**
     * Busca y recupera una línea de detalle específica utilizando las claves del pedido y del producto en la ruta.
     * Combina ambos parámetros en un objeto de ID compuesto para realizar la consulta en el servicio.
     * @param orderId El identificador único del pedido enviado en la ruta URL.
     * @param productId El identificador único del producto enviado en la ruta URL.
     * @return El objeto {@link OrderDetail} localizado, o {@code null} si la clave compuesta no existe.
     */
    @GetMapping("/{orderId}/{productId}")
    public OrderDetail getOrderDetailById(@PathVariable Long orderId, @PathVariable Long productId) {
        OrderDetailId id = new OrderDetailId(orderId, productId);
        if (orderDetailService.findById(id).isPresent()) {
            return orderDetailService.findById(id).get();
        }
        return null;
    }

    /**
     * Registra y persiste una nueva línea de detalle de pedido enviada en el cuerpo de la petición.
     * Mapea peticiones HTTP POST externas.
     * @param orderDetail Objeto {@link OrderDetail} deserializado automáticamente desde el formato JSON.
     * @return El objeto {@link OrderDetail} guardado de forma exitosa en el sistema.
     */
    @PostMapping
    public OrderDetail createOrderDetail(@RequestBody OrderDetail orderDetail){
        return orderDetailService.save(orderDetail);
    }

    /**
     * Modifica las propiedades de una línea de detalle existente localizándola mediante sus dos identificadores de ruta.
     * Realiza un mapeo de peticiones HTTP PUT para actualizar las cantidades y precios históricos de la transacción.
     * @param orderId El identificador único del pedido asociado que se desea buscar.
     * @param productId El identificador único del producto asociado que se desea buscar.
     * @param orderDetail Objeto que contiene los nuevos valores de cantidad y precio en formato JSON.
     * @return El objeto {@link OrderDetail} actualizado y guardado, o {@code null} si la línea original no existe.
     */
    @PutMapping("/{orderId}/{productId}")
    public OrderDetail updateOrderDetail(@PathVariable Long orderId, @PathVariable Long productId, @RequestBody OrderDetail orderDetail) {
        OrderDetailId id = new OrderDetailId(orderId, productId);
        if (orderDetailService.findById(id).isPresent()) {
            OrderDetail detalleExistente = orderDetailService.findById(id).get();
            detalleExistente.setQuantity(orderDetail.getQuantity());
            detalleExistente.setUnitPrice(orderDetail.getUnitPrice());
            return orderDetailService.save(detalleExistente);
        }
        return null;
    }

    /**
     * Elimina físicamente una línea de detalle de la base de datos haciendo uso de sus dos identificadores.
     * Mapea peticiones HTTP DELETE cruzadas en la ruta de red.
     * @param orderId El identificador único del pedido de la línea que se procederá a borrar.
     * @param productId El identificador único del producto de la línea que se procederá a borrar.
     */
    @DeleteMapping("/{orderId}/{productId}")
    public void deleteOrderDetail(@PathVariable Long orderId, @PathVariable Long productId) {
        OrderDetailId id = new OrderDetailId(orderId, productId);
        if (orderDetailService.findById(id).isPresent()) {
            orderDetailService.deleteById(id);
        }
    }
}
