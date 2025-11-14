package co.edu.poli.model;

/**
 * 
 */
public class administrador {
	
    /** Identificador único del administrador */
    private int id_admin;

    /** Nombre del administrador */
    private String nombre;

    /** Correo electrónico del administrador */
    private String correo;

    /** Contraseña del administrador */
    private String contrasena;
	
    /**
     * Constructor vacío requerido para operaciones JDBC/DAO
     * y frameworks que necesitan instanciar el objeto antes de asignar valores.
     */
    public administrador() {
        // Constructor vacío
    }
	
	/**
     * Constructor con parámetros
     * 
     * @param idAdmin ID del administrador
     * @param nombre Nombre del administrador
     * @param correo Correo electrónico
     * @param contrasena Contraseña de acceso
     */
    public administrador(int id_admin, String nombre, String correo, String contrasena) {
		super();
		this.id_admin = id_admin;
		this.nombre = nombre;
		this.correo = correo;
		this.contrasena = contrasena;
	}

    /** Getters y Setters */
	public int getId_admin() {
		return id_admin;
	}

	public void setId_admin(int id_admin) {
		this.id_admin = id_admin;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}