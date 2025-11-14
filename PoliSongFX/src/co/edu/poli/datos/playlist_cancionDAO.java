package co.edu.poli.datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar la relación muchos-a-muchos entre playlist y cancion.
 * Tabla intermedia: playlist_cancion
 */
public class playlist_cancionDAO {

    public playlist_cancionDAO() {}

    /**
     * Agrega una canción a una playlist.
     */
    public void addCancionToPlaylist(int id_playlist, int id_cancion) {
        String sql = "INSERT INTO playlist_cancion (id_playlist, id_cancion) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_playlist);
            stmt.setInt(2, id_cancion);
            stmt.executeUpdate();

            System.out.println("playlist_cancionDAO -> addCancionToPlaylist: Cancion agregada a playlist");

        } catch (SQLException e) {
            System.out.println("playlist_cancionDAO -> addCancionToPlaylist: Error al agregar cancion");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Elimina una canción de una playlist.
     */
    public void removeCancionFromPlaylist(int id_playlist, int id_cancion) {
        String sql = "DELETE FROM playlist_cancion WHERE id_playlist = ? AND id_cancion = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_playlist);
            stmt.setInt(2, id_cancion);
            stmt.executeUpdate();

            System.out.println("playlist_cancionDAO -> removeCancionFromPlaylist: Cancion eliminada de playlist");

        } catch (SQLException e) {
            System.out.println("playlist_cancionDAO -> removeCancionFromPlaylist: Error al eliminar cancion");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Devuelve las canciones asociadas a una playlist.
     */
    public List<Integer> getCancionesByPlaylist(int id_playlist) {
        List<Integer> canciones = new ArrayList<>();
        String sql = "SELECT id_cancion FROM playlist_cancion WHERE id_playlist = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_playlist);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                canciones.add(rs.getInt("id_cancion"));
            }

            System.out.println("playlist_cancionDAO -> getCancionesByPlaylist: Canciones recuperadas");

        } catch (SQLException e) {
            System.out.println("playlist_cancionDAO -> getCancionesByPlaylist: Error al consultar canciones");
            System.out.println("Detalles: " + e.getMessage());
        }

        return canciones;
    }

    /**
     * Devuelve las playlists donde aparece una canción.
     */
    public List<Integer> getPlaylistsByCancion(int id_cancion) {
        List<Integer> playlists = new ArrayList<>();
        String sql = "SELECT id_playlist FROM playlist_cancion WHERE id_cancion = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_cancion);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                playlists.add(rs.getInt("id_playlist"));
            }

            System.out.println("playlist_cancionDAO -> getPlaylistsByCancion: Playlists recuperadas");

        } catch (SQLException e) {
            System.out.println("playlist_cancionDAO -> getPlaylistsByCancion: Error al consultar playlists");
            System.out.println("Detalles: " + e.getMessage());
        }

        return playlists;
    }
}
