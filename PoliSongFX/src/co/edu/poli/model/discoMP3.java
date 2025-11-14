package co.edu.poli.model;

import java.util.Date;

/**
 * Representa un disco en formato MP3 dentro del sistema PoliSongStock.
 * Un disco MP3 es una recopilación de canciones digitales publicadas en la plataforma.
 * 
 * Esta clase almacena información básica del disco como su nombre, fecha de salida
 * y la lista de canciones asociadas.
 * 
 * Proyecto: PoliSongStock
 * Autor: Sebastian Zarate
 */
public class discoMP3 {

    /**
     * Identificador único del disco MP3
     */
    private int id_MP3;

    /**
     * Nombre del disco MP3
     */
    private String nombre;

    /**
     * Fecha de lanzamiento del disco MP3
     */
    private Date fecha_salida;

    /**
     * Arreglo de canciones que pertenecen al disco MP3
     */
    private cancion[] cancion;

    /**
     * Constructor vacío necesario para operaciones JDBC/DAO.
     * Permite crear una instancia vacía y luego asignar valores mediante setters.
     */
    public discoMP3() {
        // Constructor vacío
    }

    /**
     * Constructor que permite crear un disco MP3 e inicializar todos sus atributos.
     *
     * @param id_MP3 Identificador del disco MP3
     * @param nombre Nombre del disco
     * @param fecha_salida Fecha de lanzamiento del disco
     * @param cancion Arreglo de canciones asociadas al disco
     */
    public discoMP3(int id_MP3, String nombre, Date fecha_salida, co.edu.poli.model.cancion[] cancion) {
        super();
        this.id_MP3 = id_MP3;
        this.nombre = nombre;
        this.fecha_salida = fecha_salida;
        this.cancion = cancion;
    }

    /** @return ID del disco MP3 */
    public int getId_MP3() {
        return id_MP3;
    }

    /** @param id_MP3 Asigna el ID del disco MP3 */
    public void setId_MP3(int id_MP3) {
        this.id_MP3 = id_MP3;
    }

    /** @return Nombre del disco MP3 */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre Asigna el nombre del disco MP3 */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return Fecha de lanzamiento del disco MP3 */
    public Date getFecha_salida() {
        return fecha_salida;
    }

    /** @param fecha_salida Asigna la fecha de lanzamiento del disco MP3 */
    public void setFecha_salida(Date fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    /** @return Arreglo de canciones del disco MP3 */
    public cancion[] getCancion() {
        return cancion;
    }

    /** @param cancion Asigna el arreglo de canciones al disco MP3 */
    public void setCancion(cancion[] cancion) {
        this.cancion = cancion;
    }
}
