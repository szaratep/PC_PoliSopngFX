package co.edu.poli.model;

/**
 * Representa un disco de vinilo disponible en el sistema PoliSongStock.
 * Un vinilo contiene información sobre su artista, año de lanzamiento,
 * precio, inventario disponible y las canciones que lo componen.
 *
 * Esta clase modela un producto físico dentro de la plataforma.
 *
 * Proyecto: PoliSongStock
 * Autor: Sebastian Zarate
 */
public class vinilo {

    /**
     * Identificador único del vinilo
     */
    private int id_vinilo;

    /**
     * Nombre del vinilo
     */
    private String nombre;

    /**
     * Artista del vinilo
     */
    private String artista;

    /**
     * Año de lanzamiento del vinilo
     */
    private int anio;

    /**
     * Precio de venta del vinilo
     */
    private double precio;

    /**
     * Cantidad disponible en inventario
     */
    private int inventario;

    /**
     * Arreglo de canciones que contiene el vinilo
     * (la relación se maneja mediante tabla intermedia en DB)
     */
    private cancion[] cancion;

    /**
     * Constructor vacío requerido para operaciones JDBC/DAO.
     * Permite crear un objeto vinilo y asignar luego sus atributos mediante setters.
     */
    public vinilo() {
        // Constructor vacío
    }

    /**
     * Constructor para inicializar todos los atributos del vinilo.
     *
     * @param id_vinilo Identificador del vinilo
     * @param nombre Nombre del vinilo
     * @param artista Artista del vinilo
     * @param anio Año de lanzamiento
     * @param precio Precio del vinilo
     * @param inventario Cantidad disponible
     * @param cancion Arreglo de canciones contenidas en el vinilo
     */
    public vinilo(int id_vinilo, String nombre, String artista, int anio, double precio, int inventario,
                  co.edu.poli.model.cancion[] cancion) {
        super();
        this.id_vinilo = id_vinilo;
        this.nombre = nombre;
        this.artista = artista;
        this.anio = anio;
        this.precio = precio;
        this.inventario = inventario;
        this.cancion = cancion;
    }

    /** @return ID del vinilo */
    public int getId_vinilo() {
        return id_vinilo;
    }

    /** @param id_vinilo Asigna el ID del vinilo */
    public void setId_vinilo(int id_vinilo) {
        this.id_vinilo = id_vinilo;
    }

    /** @return Nombre del vinilo */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre Asigna el nombre del vinilo */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return Artista del vinilo */
    public String getArtista() {
        return artista;
    }

    /** @param artista Asigna el artista del vinilo */
    public void setArtista(String artista) {
        this.artista = artista;
    }

    /** @return Año del vinilo */
    public int getAnio() {
        return anio;
    }

    /** @param anio Asigna el año de lanzamiento del vinilo */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /** @return Precio del vinilo */
    public double getPrecio() {
        return precio;
    }

    /** @param precio Asigna el precio del vinilo */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /** @return Inventario disponible */
    public int getInventario() {
        return inventario;
    }

    /** @param inventario Asigna la cantidad disponible */
    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

    /** @return Arreglo de canciones del vinilo */
    public cancion[] getCancion() {
        return cancion;
    }

    /** @param cancion Asigna el arreglo de canciones del vinilo */
    public void setCancion(cancion[] cancion) {
        this.cancion = cancion;
    }
}
