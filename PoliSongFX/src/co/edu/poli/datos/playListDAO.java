package co.edu.poli.datos;

import co.edu.poli.model.playList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public int crearPlayListReturnId(String nombre, boolean esPublica, int idUsuario) {
        String sql = "INSERT INTO playlist (nombre, publica, id_usuario) VALUES (?, ?, ?)";
        int idGenerado = -1;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nombre);
            stmt.setBoolean(2, esPublica);
            stmt.setInt(3, idUsuario);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("playListDAO -> crearPlayListReturnId: " + e.getMessage());
        }

        return idGenerado;
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
    
    /**
     * Recupera la última playlist creada por un usuario específico.
     * Se asume que el ID más alto corresponde a la última creada.
     *
     * @param idUsuario El ID del usuario.
     * @return La playlist recién creada, o null si no existe.
     */
    public playList readPlayListUltimaCreada(int idUsuario) {
        String sql = "SELECT * FROM playlist WHERE id_usuario = ? ORDER BY id_playlist DESC LIMIT 1";
        playList pl = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pl = new playList(
                        rs.getInt("id_playlist"),
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getBoolean("publica")
                );
            }

        } catch (SQLException e) {
            System.out.println("playListDAO -> readPlayListUltimaCreada: Error al leer playlist");
            System.out.println("Detalles: " + e.getMessage());
        }

        return pl;
    }
    
 // En playListDAO
    public List<playList> readPlaylistsByUsuario(int idUsuario) {
        List<playList> listas = new ArrayList<>();
        String sql = "SELECT * FROM playlist WHERE id_usuario = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                playList p = new playList(
                    rs.getInt("id_playlist"),
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getBoolean("publica")
                );
                listas.add(p);
            }

        } catch (SQLException e) {
            System.out.println("playListDAO -> readPlaylistsByUsuario: Error al leer playlists");
            System.out.println("Detalles: " + e.getMessage());
        }

        return listas;
    }

}
