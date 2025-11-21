package co.edu.poli.negocio;

import co.edu.poli.datos.*;
import co.edu.poli.model.playList;
import java.util.List;

/**
 * Lógica de negocio para la gestión de Playlists y sus canciones.
 * 
 * Esta clase NO realiza consultas SQL directamente.
 * Usa los DAO: playListDAO y playlist_cancionDAO.
 */
public class playListManager {

    public final playListDAO playListDao;
    public final playlist_cancionDAO playlistCancionDao;

    /**
     * Constructor: inicializa los DAOs.
     */
    public playListManager() {
        this.playListDao = new playListDAO();
        this.playlistCancionDao = new playlist_cancionDAO();
    }

    /**
     * Crea una nueva playlist en la base de datos.
     */
    public void crearPlayList(int idPlayList, String nombre, boolean publica, int idUsuario) {
        playList p = new playList(idPlayList, idUsuario, nombre, publica);
        playListDao.createPlayList(p);
        System.out.println("playListManager -> crearPlayList: Playlist creada correctamente (" + nombre + ")");
    }

    /**
     * Agrega una canción a una playlist existente.
     */
    public void agregarCancion(int idPlayList, int idCancion) {
        playlistCancionDao.addCancionToPlaylist(idPlayList, idCancion);
        System.out.println("playListManager -> agregarCancion: Canción agregada a la playlist ID " + idPlayList);
    }

    /**
     * Elimina una canción de una playlist.
     */
    public void eliminarCancion(int idPlayList, int idCancion) {
        playlistCancionDao.removeCancionFromPlaylist(idPlayList, idCancion);
        System.out.println("playListManager -> eliminarCancion: Canción eliminada de la playlist ID " + idPlayList);
    }

    /**
     * Muestra una playlist específica con sus canciones asociadas.
     */
    public void verPlayList(int idPlayList) {
        playList p = playListDao.readPlayList(idPlayList);
        if (p == null) {
            System.out.println("playListManager -> verPlayList: No se encontró la playlist con ID " + idPlayList);
            return;
        }

        System.out.println("---- PLAYLIST ----");
        System.out.println("ID: " + p.getId_playlist() +
                " | Nombre: " + p.getNombre() +
                " | Pública: " + (p.isPublica() ? "Sí" : "No") +
                " | Usuario ID: " + p.getId_usuario());

        List<Integer> canciones = playlistCancionDao.getCancionesByPlaylist(idPlayList);
        if (canciones.isEmpty()) {
            System.out.println("Sin canciones asociadas.");
        } else {
            System.out.println("Canciones asociadas:");
            for (Integer idCancion : canciones) {
                System.out.println(" - Canción ID: " + idCancion);
            }
        }
    }

    /**
     * Actualiza los datos de una playlist existente.
     */
    public void actualizarPlayList(int idPlayList, int idUsuario, String nuevoNombre, boolean publica) {
        playList p = new playList(idPlayList, idUsuario, nuevoNombre, publica);
        playListDao.updatePlayList(p);
        System.out.println("playListManager -> actualizarPlayList: Playlist actualizada (ID: " + idPlayList + ")");
    }

    /**
     * Elimina completamente una playlist de la base de datos.
     */
    public void eliminarPlayList(int idPlayList) {
        playListDao.deletePlayList(idPlayList);
        System.out.println("playListManager -> eliminarPlayList: Playlist eliminada (ID: " + idPlayList + ")");
    }
}
