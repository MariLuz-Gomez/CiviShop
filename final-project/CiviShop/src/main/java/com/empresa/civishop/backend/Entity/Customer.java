package com.empresa.civishop.backend.Entity;

import java.time.LocalDate;
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
 * Entidad que representa a un cliente dentro del sistema CiviShop.
 * Mapea directamente con la tabla customers en la base de datos.
 * Incluye campos únicos de contacto y la relación hacia sus pedidos.
 * @author Mariluz Gómez
 * @version 1.0
 */
@Entity
@Table(name = "customers")
public class Customer {

    /** Identificador único del cliente */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del cliente */
    @Column(nullable = false)
    private String firstName;

    /** Apellido del cliente */
    @Column(nullable = false)
    private String lastName;

    /** Correo electrónico único de contacto del cliente*/
    @Column(nullable = false, unique = true)
    private String email;
    
    /** Número de teléfono único de contacto*/
    @Column(nullable = false, unique = true)
    private int phone;
    
    /** Fecha exacta en la que el cliente se registró en la plataforma*/
    @Column(nullable = false)
    private LocalDate registrationDate;

    /** 
     * Historial de pedidos realizados por este cliente en la tienda.
     * Utiliza carga perezosa (LAZY) y borrado en cascada (ALL).
     * Se oculta del JSON usando JsonIgnore para prevenir bucles cíclicos infinitos de Jackson.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> ordersList = new ArrayList<>();
    
    /**
     * Constructor por defecto requerido por el framework JPA / Hibernate.
     */
    public Customer() {}

    /**
     * Constructor completo para inicializar un cliente con todos sus atributos, incluyendo ID e historial.
     * @param id El identificador único.
     * @param firstName Nombre del cliente.
     * @param lastName Apellido del cliente.
     * @param email Correo electrónico de contacto.
     * @param phone Número telefónico.
     * @param registrationDate Fecha de registro.
     * @param ordersList Lista inicial de pedidos asociados.
     */
    public Customer(Long id, String firstName, String lastName, String email, int phone, LocalDate registrationDate,
            List<Order> ordersList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.registrationDate = registrationDate;
        this.ordersList = ordersList;
    }

    /**
     * Constructor corto sin ID, utilizado para dar de alta nuevos clientes antes de persistirlos en la base de datos.
     * @param firstName Nombre del cliente.
     * @param lastName Apellido del cliente.
     * @param email Correo electrónico de contacto.
     * @param phone Número telefónico.
     * @param registrationDate Fecha de alta.
     * @param ordersList Lista de pedidos asociados.
     */
    public Customer(String firstName, String lastName, String email, int phone, LocalDate registrationDate,
            List<Order> ordersList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.registrationDate = registrationDate;
        this.ordersList = ordersList;
    }

    /**
     * Obtiene el identificador único del cliente.
     * @return El ID del cliente.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del cliente si no es nulo.
     * @param id El nuevo identificador a asignar.
     */
    public void setId(Long id) {
        if (id != null) {
            this.id = id;
        }
    }

    /**
     * Obtiene el nombre del cliente.
     * @return El nombre actual del cliente.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Establece el nombre del cliente validando que no sea nulo ni esté vacío.
     * @param firstName El nuevo nombre a asignar.
     */
    public void setFirstName(String firstName) {
        if (firstName != null && !firstName.trim().isEmpty()) {
            this.firstName = firstName;
        }
    }

    /**
     * Obtiene el apellido del cliente.
     * @return El apellido actual del cliente.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Establece el apellido del cliente validando que no sea nulo ni esté vacío.
     * @param lastName El nuevo apellido a asignar.
     */
    public void setLastName(String lastName) {
        if (lastName != null && !lastName.trim().isEmpty()) {
            this.lastName = lastName;
        }
    }

    /**
     * Obtiene el correo electrónico de contacto del cliente.
     * @return El e-mail de contacto.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico de contacto del cliente validando que no sea nulo ni esté vacío.
     * @param email El nuevo correo electrónico a asignar.
     */
    public void setEmail(String email) {
        if (email != null && !email.trim().isEmpty()) {
            this.email = email;
        }
    }

    /**
     * Obtiene el número de teléfono del cliente.
     * @return El número telefónico como valor entero.
     */
    public int getPhone() {
        return phone;
    }

    /**
     * Establece el número de teléfono del cliente impidiendo que se asigne el valor cero.
     * @param phone El nuevo número telefónico a asignar.
     */
    public void setPhone(int phone) {
        if (phone != 0 ) {
            this.phone = phone;
        }
    }

    /**
     * Obtiene la fecha de registro del cliente en la plataforma.
     * @return La fecha de registro actual.
     */
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Establece la fecha de registro del cliente en la plataforma si el objeto proporcionado no es nulo.
     * @param registrationDate La nueva fecha de registro.
     */
    public void setRegistrationDate(LocalDate registrationDate) {
        if (registrationDate != null) {
            this.registrationDate = registrationDate;
        }
    }

    /**
     * Obtiene el historial completo de pedidos del cliente.
     * Inicializa una lista vacía si el objeto interno es nulo para prevenir excepciones.
     * @return Lista de objetos {@link Order}.
     */
    public List<Order> getOrdersList() {
        if (ordersList == null) {
            ordersList = new ArrayList<>();
        }
        return ordersList;
    }

    /**
     * Reemplaza por completo la lista de pedidos del cliente.
     * @param ordersList La nueva lista de pedidos a asociar.
     */
    public void setOrdersList(List<Order> ordersList) {
        this.ordersList = ordersList;
    }

    /**
     * Devuelve una cadena de texto detallada con el estado y los datos actuales del cliente.
     * @return Representación textual de la entidad {@code Customer}.
     */
    @Override
    public String toString() {
        return "Customer " + id + ",\n firstName: " + firstName + ",\n lastName: " + lastName + ",\n email: " + email
                + ",\n phone: " + phone + ",\n registrationDate: " + registrationDate  + ",\n ordersList: " + ordersList.size();
    }

}
