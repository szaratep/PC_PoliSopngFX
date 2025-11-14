package co.edu.poli.model;

/**
 * Representa un usuario dentro del sistema PoliSongStock.
 * Cada usuario puede realizar pedidos, crear playlists y administrar su carrito de compras.
 * 
 * Contiene información básica de autenticación como nombre, correo y contraseña.
 *
 * Proyecto: PoliSongStock
 * Autor: Sebastian Zarate
 */
public class usuario {

    /**
     * Identificador único del usuario
     */
    private int id_usuario;

    /**
     * Nombre del usuario
     */
    private String nombre;

    /**
     * Correo electrónico del usuario
     */
    private String correo;

    /**
     * Contraseña del usuario para acceder al sistema
     */
    private String contrasena;

    /**
     * Constructor vacío requerido para operaciones JDBC/DAO.
     * Permite instanciar un objeto usuario y luego asignar sus valores mediante setters.
     */
    public usuario() {
        // Constructor vacío
    }

    /**
     * Constructor que permite inicializar un usuario con todos sus atributos.
     *
     * @param id_usuario Identificador único del usuario
     * @param nombre Nombre del usuario
     * @param correo Correo del usuario
     * @param contrasena Contraseña del usuario
     */
    public usuario(int id_usuario, String nombre, String correo, String contrasena) {
        super();
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    /** @return ID del usuario */
    public int getId_usuario() {
        return id_usuario;
    }

    /** @param id_usuario Asigna el ID del usuario */
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    /** @return Nombre del usuario */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre Asigna el nombre del usuario */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return Correo del usuario */
    public String getCorreo() {
        return correo;
    }

    /** @param correo Asigna el correo del usuario */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /** @return Contraseña del usuario */
    public String getContrasena() {
        return contrasena;
    }

    /** @param contrasena Asigna la contraseña del usuario */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
