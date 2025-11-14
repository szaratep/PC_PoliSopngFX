package co.edu.poli.model;

import java.util.List;

/**
 * Representa el carrito de compras de un usuario dentro del sistema PoliSongStock.
 * Contiene los productos agregados por el usuario antes de generar un pedido.
 * 
 * El carrito pertenece a un único usuario y contiene una lista dinámica de items
 * representados por objetos {@link carritoItem}.
 * 
 * Proyecto: PoliSongStock
 * Autor: Sebastian Zarate
 */
public class carrito {

    /**
     * Identificador único del carrito en la base de datos
     */
    private int id_carrito;

    /**
     * Identificador del usuario dueño del carrito
     */
    private int id_usuario;

    /**
     * Lista de elementos contenidos en el carrito
     */
    private List<carritoItem> items;
    
    /**
     * Constructor vacío necesario para JDBC/DAO
     * permite crear una instancia y luego asignar los valores del carrito.
     */
    public carrito() {
        // Constructor vacío
    }

    /**
     * Constructor para inicializar los atributos del carrito.
     *
     * @param id_carrito ID único del carrito
     * @param id_usuario ID del usuario dueño del carrito
     * @param items Lista de items del carrito
     */
    public carrito(int id_carrito, int id_usuario, List<carritoItem> items) {
        super();
        this.id_carrito = id_carrito;
        this.id_usuario = id_usuario;
        this.items = items;
    }

    /** @return ID del carrito */
    public int getId_carrito() {
        return id_carrito;
    }

    /** @param id_carrito asigna ID del carrito */
    public void setId_carrito(int id_carrito) {
        this.id_carrito = id_carrito;
    }

    /** @return ID del usuario dueño */
    public int getId_usuario() {
        return id_usuario;
    }

    /** @param id_usuario asigna el ID del usuario */
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    /** @return lista de items del carrito */
    public List<carritoItem> getItems() {
        return items;
    }

    /** @param items asigna nueva lista de items */
    public void setItems(List<carritoItem> items) {
        this.items = items;
    }
}
