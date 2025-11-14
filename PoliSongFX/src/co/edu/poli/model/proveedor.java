package co.edu.poli.model;

/**
 * Representa un proveedor dentro del sistema PoliSongStock.
 * Un proveedor es responsable de publicar productos como vinilos o discos
 * en la plataforma y gestionar su información de venta.
 * 
 * Cada proveedor cuenta con información básica de autenticación y un valor
 * de calificación que refleja su reputación.
 *
 * Proyecto: PoliSongStock
 * Autor: Sebastian Zarate
 */
public class proveedor {

    /**
     * Identificador único del proveedor
     */
    private int id_proveedor;

    /**
     * Nombre del proveedor
     */
    private String nombre;

    /**
     * Correo electrónico del proveedor
     */
    private String correo;

    /**
     * Contraseña del proveedor para acceder al sistema
     */
    private String contrasena;

    /**
     * Calificación acumulada del proveedor (basada en desempeño y reputación)
     */
    private int calificaciones;

    /**
     * Constructor vacío requerido para JDBC/DAO.
     * Permite crear la instancia y posteriormente asignar sus valores.
     */
    public proveedor() {
        // Constructor vacío
    }

    /**
     * Constructor que permite crear un proveedor e inicializar sus atributos.
     *
     * @param id_proveedor Identificador único del proveedor
     * @param nombre Nombre del proveedor
     * @param correo Correo del proveedor
     * @param contrasena Contraseña del proveedor
     * @param calificaciones Calificación acumulada del proveedor
     */
    public proveedor(int id_proveedor, String nombre, String correo, String contrasena, int calificaciones) {
        super();
        this.id_proveedor = id_proveedor;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.calificaciones = calificaciones;
    }

    /** @return ID del proveedor */
    public int getId_proveedor() {
        return id_proveedor;
    }

    /** @param id_proveedor Asigna el ID del proveedor */
    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    /** @return Nombre del proveedor */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre Asigna el nombre del proveedor */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return Correo del proveedor */
    public String getCorreo() {
        return correo;
    }

    /** @param correo Asigna el correo del proveedor */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /** @return Contraseña del proveedor */
    public String getContrasena() {
        return contrasena;
    }

    /** @param contrasena Asigna la contraseña del proveedor */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /** @return Calificaciones del proveedor */
    public int getCalificaciones() {
        return calificaciones;
    }

    /** @param calificaciones Asigna el puntaje de calificación del proveedor */
    public void setCalificaciones(int calificaciones) {
        this.calificaciones = calificaciones;
    }
}
