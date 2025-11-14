package co.edu.poli.model;

import java.util.Date;

/**
 * Representa un pedido realizado por un usuario dentro del sistema PoliSongStock.
 * Un pedido contiene información básica como el usuario que lo realizó, la fecha
 * y su estado actual. Los detalles del pedido (productos comprados) se controlan
 * desde la entidad `pedidoDetalle`.
 *
 * Proyecto: PoliSongStock
 * Autor: Sebastian Zarate
 */
public class pedido {

    /**
     * Identificador único del pedido
     */
    private int id_pedido;

    /**
     * Identificador del usuario que realizó el pedido
     */
    private int id_usuario;

    /**
     * Fecha en la que se realizó el pedido
     */
    private Date fecha;

    /**
     * Estado actual del pedido (ej: "pendiente", "completado", "cancelado")
     */
    private String estado;

    /**
     * Constructor vacío necesario para operaciones JDBC/DAO,
     * permitiendo instanciar el objeto y luego asignar valores.
     */
    public pedido() {
        // Constructor vacío
    }

    /**
     * Constructor que permite inicializar los atributos del pedido en su creación.
     *
     * @param id_pedido Identificador único del pedido
     * @param id_usuario Identificador del usuario que realizó el pedido
     * @param fecha Fecha del pedido
     * @param estado Estado del pedido
     */
    public pedido(int id_pedido, int id_usuario, Date fecha, String estado) {
        super();
        this.id_pedido = id_pedido;
        this.id_usuario = id_usuario;
        this.fecha = fecha;
       	this.estado = estado;
    }

    /** @return ID del pedido */
    public int getId_pedido() {
        return id_pedido;
    }

    /** @param id_pedido Asigna el ID del pedido */
    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    /** @return ID del usuario que realiza el pedido */
    public int getId_usuario() {
        return id_usuario;
    }

    /** @param id_usuario Asigna el ID del usuario del pedido */
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    /** @return Fecha del pedido */
    public Date getFecha() {
        return fecha;
    }

    /** @param fecha Asigna la fecha en que se realizó el pedido */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /** @return Estado actual del pedido */
    public String getEstado() {
        return estado;
    }

    /** @param estado Asigna el estado del pedido */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
