package co.edu.poli.model;

/**
 * Representa una playlist creada por un usuario dentro del sistema PoliSongStock.
 * Una playlist puede contener varias canciones y ser pública o privada. 
 * La relación con las canciones se maneja en una tabla intermedia en la base de datos,
 * por lo que este arreglo se utiliza para representación en memoria.
 *
 * Proyecto: PoliSongStock
 * Autor: Sebastian Zarate
 */
public class playList {

    /**
     * Identificador único de la playlist
     */
    private int id_playlist;

    /**
     * Identificador del usuario dueño de la playlist
     */
    private int id_usuario;

    /**
     * Nombre de la playlist
     */
    private String nombre;

    /**
     * Indica si la playlist es pública o privada
     */
    private boolean publica;

    /**
     * Arreglo de canciones asociadas a la playlist
     * (relación almacenada en tabla intermedia playlist_cancion)
     */
    private cancion[] cancion;

    /**
     * Constructor vacío requerido para operaciones JDBC/DAO.
     * Permite crear la instancia y luego llenar los valores mediante setters.
     */
    public playList() {
        // Constructor vacío
    }

    /**
     * Constructor principal, permite crear la playlist sin asociar canciones inicialmente.
     * La relación de canciones se realiza posteriormente vía base de datos.
     *
     * @param id_playlist Identificador de la playlist
     * @param id_usuario Identificador del usuario creador
     * @param nombre Nombre de la playlist
     * @param publica Indicador de playlist pública o privada
     */
    public playList(int id_playlist, int id_usuario, String nombre, boolean publica) {
        super();
        this.id_playlist = id_playlist;
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.publica = publica;
    }

    /**
     * Constructor alternativo, permite crear la playlist y asociar canciones 
     * directamente en memoria (si ya existen y fueron consultadas previamente).
     *
     * @param id_playlist Identificador de la playlist
     * @param id_usuario Identificador del usuario creador
     * @param nombre Nombre de la playlist
     * @param publica Publicidad de la playlist (true/false)
     * @param cancion Arreglo de canciones asociadas
     */
    public playList(int id_playlist, int id_usuario, String nombre, boolean publica,
                    co.edu.poli.model.cancion[] cancion) {
        super();
        this.id_playlist = id_playlist;
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.publica = publica;
        this.cancion = cancion;
    }

    /** @return ID de la playlist */
    public int getId_playlist() {
        return id_playlist;
    }

    /** @param id_playlist Asigna el ID de la playlist */
    public void setId_playlist(int id_playlist) {
        this.id_playlist = id_playlist;
    }

    /** @return ID del usuario dueño de la playlist */
    public int getId_usuario() {
        return id_usuario;
    }

    /** @param id_usuario Asigna el ID del usuario */
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    /** @return Nombre de la playlist */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre Asigna el nombre de la playlist */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return true si la playlist es pública, false si es privada */
    public boolean isPublica() {
        return publica;
    }

    /** @param publica Establece si la playlist es pública o privada */
    public void setPublica(boolean publica) {
        this.publica = publica;
    }

    /** @return Arreglo de canciones asociadas a la playlist */
    public cancion[] getCancion() {
        return cancion;
    }

    /** @param cancion Asigna el arreglo de canciones de la playlist */
    public void setCancion(cancion[] cancion) {
        this.cancion = cancion;
    }
}
