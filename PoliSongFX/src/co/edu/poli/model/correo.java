package co.edu.poli.model;

/**
 * Clase que representa un correo registrado en el sistema.
 */
public class correo {

    /**
     * Correo electrónico (PK)
     */
    private String correo;

    /**
     * Constructor con parámetros
     * @param correo Correo electrónico único
     */
    public correo(String correo) {
        this.correo = correo;
    }

    /**
     * Constructor vacío necesario para operaciones DAO
     */
    public correo() {}

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
