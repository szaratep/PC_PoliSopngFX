package co.edu.poli.model;

/**
 * Representa una canción dentro del sistema PoliSongStock.
 * Contiene información general como nombre, duración, precio y tamaño del archivo.
 * 
 * Esta clase forma parte del modelo de datos y se relaciona con productos
 * como vinilos y discos MP3 en el marketplace musical.
 * 
 * Proyecto: PoliSongStock
 * Autor: Sebastian Zarate
 */
public class cancion {

    /**
     * Identificador único de la canción
     */
    private int id_cancion;

    /**
     * Nombre o título de la canción
     */
    private String nombre;

    /**
     * Duración de la canción en segundos o minutos
     */
    private double duracion;

    /**
     * Precio de compra de la canción como producto digital
     */
    private double precio;

    /**
     * Tamaño del archivo de audio en megabytes (MB)
     */
    private double tamano_mb;

    /**
     * Constructor vacío requerido para operaciones JDBC/DAO
     * cuando se construye el objeto y luego se llenan los atributos mediante setters.
     */
   

    /**
     * Constructor que inicializa todos los atributos de la canción.
     *
     * @param id_cancion Identificador de la canción
     * @param nombre Nombre de la canción
     * @param duracion Duración en formato numérico
     * @param precio Precio de venta
     * @param tamano_mb Tamaño en MB del archivo de la canción
     */
    public cancion(String nombre, double duracion, double precio, double tamano_mb) {
        super();
       	this.nombre = nombre;
        this.duracion = duracion;
        this.precio = precio;
        this.tamano_mb = tamano_mb;
    }

    /** @return id de la canción */
    public int getId_cancion() {
        return id_cancion;
    }

    /** @param id_cancion nuevo id para la canción */
    public void setId_cancion(int id_cancion) {
        this.id_cancion = id_cancion;
    }

    /** @return nombre de la canción */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre nuevo nombre de canción */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return duración de la canción */
    public double getDuracion() {
        return duracion;
    }

    /** @param duracion valor numérico de duración */
    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    /** @return precio de la canción */
    public double getPrecio() {
        return precio;
    }

    /** @param precio nuevo precio de la canción */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /** @return tamaño del archivo de la canción en MB */
    public double getTamano_mb() {
        return tamano_mb;
    }

    /** @param tamano_mb nuevo tamaño del archivo en MB */
    public void setTamano_mb(double tamano_mb) {
        this.tamano_mb = tamano_mb;
    }
}
