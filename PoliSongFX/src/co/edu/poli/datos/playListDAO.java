package co.edu.poli.datos;

import co.edu.poli.model.playList;
import java.sql.*;

/**
 * DAO (Data Access Object) para la entidad {@link playList}.
 * Gestiona las operaciones CRUD (Create, Read, Update, Delete)
 * sobre la tabla playlist en la base de datos MySQL.
 * 
 * Relaciones principales:
 * - id_usuario: Clave foránea que referencia a la tabla usuario.
 */
public class playListDAO {

    /** Constructor vacío */
    public playListDAO() {}

    /**
     * Crea una nueva playlist en la base de datos.
     * 
     * @param p Objeto {@link playList} con los datos de la playlist a crear.
     */
    public void createPlayList(playList p) {
        String sql = "INSERT INTO playlist (id_playlist, id_usuario, nombre, publica) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getId_playlist());
            stmt.setInt(2, p.getId_usuario());
            stmt.setString(3, p.getNombre());
            stmt.setBoolean(4, p.isPublica());

            stmt.executeUpdate();
            System.out.println("playListDAO -> createPlayList: Playlist creada correctamente");

        } catch (SQLException e) {
            System.out.println("playListDAO -> createPlayList: Error al crear playlist");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Lee una playlist desde la base de datos mediante su ID.
     * 
     * @param id Identificador único de la playlist.
     * @return Objeto {@link playList} con los datos encontrados o {@code null} si no existe.
     */
    public playList readPlayList(int id) {
        String sql = "SELECT * FROM playlist WHERE id_playlist = ?";
        playList pl = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pl = new playList(
                        rs.getInt("id_playlist"),
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getBoolean("publica")
                );
                System.out.println("playListDAO -> readPlayList: Playlist encontrada");
            } else {
                System.out.println("playListDAO -> readPlayList: Playlist no existe");
            }

        } catch (SQLException e) {
            System.out.println("playListDAO -> readPlayList: Error al leer playlist");
            System.out.println("Detalles: " + e.getMessage());
        }

        return pl;
    }

    /**
     * Actualiza la información de una playlist existente.
     * 
     * @param p Objeto {@link playList} con los nuevos datos a actualizar.
     */
    public void updatePlayList(playList p) {
        String sql = "UPDATE playlist SET id_usuario = ?, nombre = ?, publica = ? WHERE id_playlist = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getId_usuario());
            stmt.setString(2, p.getNombre());
            stmt.setBoolean(3, p.isPublica());
            stmt.setInt(4, p.getId_playlist());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("playListDAO -> updatePlayList: Playlist actualizada");
            } else {
                System.out.println("playListDAO -> updatePlayList: Playlist no existe");
            }

        } catch (SQLException e) {
            System.out.println("playListDAO -> updatePlayList: Error al actualizar playlist");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Elimina una playlist de la base de datos.
     * 
     * @param id Identificador único de la playlist a eliminar.
     */
    public void deletePlayList(int id) {
        String sql = "DELETE FROM playlist WHERE id_playlist = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("playListDAO -> deletePlayList: Playlist eliminada");
            } else {
                System.out.println("playListDAO -> deletePlayList: Playlist no existe");
            }

        } catch (SQLException e) {
            System.out.println("playListDAO -> deletePlayList: Error al eliminar playlist");
            System.out.println("Detalles: " + e.getMessage());
        }
    }
}
