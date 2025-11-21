package co.edu.poli.datos;

import co.edu.poli.model.discoMP3;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la gestión de la tabla discomp3 en la base de datos.
 */
public class discoMP3DAO {

    public discoMP3DAO() {}

    /**
     * Crear un disco MP3
     */
    public void createMP3(discoMP3 mp3) {
        String sql = "INSERT INTO discomp3 (id_MP3, nombre, fecha_salida) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mp3.getId_MP3());
            stmt.setString(2, mp3.getNombre());
            stmt.setDate(3, new java.sql.Date(mp3.getFecha_salida().getTime()));

            stmt.executeUpdate();
            System.out.println("discoMP3DAO -> createMP3: Disco MP3 creado correctamente");

        } catch (SQLException e) {
            System.out.println("discoMP3DAO -> createMP3: Error al crear disco MP3");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Leer un disco MP3
     */
    public discoMP3 readMP3(int id) {
        String sql = "SELECT * FROM discomp3 WHERE id_MP3 = ?";
        discoMP3 mp3 = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                mp3 = new discoMP3(
                        rs.getInt("id_MP3"),
                        rs.getString("nombre"),
                        rs.getDate("fecha_salida"),
                        null
                );
                System.out.println("discoMP3DAO -> readMP3: Disco MP3 encontrado");
            } else {
                System.out.println("discoMP3DAO -> readMP3: Disco MP3 no existe");
            }

        } catch (SQLException e) {
            System.out.println("discoMP3DAO -> readMP3: Error al leer disco MP3");
            System.out.println("Detalles: " + e.getMessage());
        }

        return mp3;
    }
    
    public List<discoMP3> listarDiscosMp3() {
        String sql = "SELECT * FROM discomp3";
        List<discoMP3> discos = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                discoMP3 mp3 = new discoMP3(
                        rs.getInt("id_MP3"),
                        rs.getString("nombre"),
                        rs.getDate("fecha_salida"),
                        null // las canciones se gestionan en otro DAO
                );
                discos.add(mp3);
            }

            System.out.println("discoMP3DAO -> listarDiscosMp3: Se encontraron " + discos.size() + " discos MP3");

        } catch (SQLException e) {
            System.out.println("discoMP3DAO -> listarDiscosMp3: Error al listar discos MP3");
            System.out.println("Detalles: " + e.getMessage());
        }

        return discos;
    }

    /**
     * Actualizar un disco MP3
     */
    public void updateMP3(discoMP3 mp3) {
        String sql = "UPDATE discomp3 SET nombre = ?, fecha_salida = ? WHERE id_MP3 = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mp3.getNombre());
            stmt.setDate(2, new java.sql.Date(mp3.getFecha_salida().getTime()));
            stmt.setInt(3, mp3.getId_MP3());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("discoMP3DAO -> updateMP3: Disco MP3 actualizado");
            } else {
                System.out.println("discoMP3DAO -> updateMP3: Disco MP3 no existe");
            }

        } catch (SQLException e) {
            System.out.println("discoMP3DAO -> updateMP3: Error al actualizar disco MP3");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Eliminar un disco MP3
     */
    public void deleteMP3(int id) {
        String sql = "DELETE FROM discomp3 WHERE id_MP3 = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("discoMP3DAO -> deleteMP3: Disco MP3 eliminado");
            } else {
                System.out.println("discoMP3DAO -> deleteMP3: Disco MP3 no existe");
            }

        } catch (SQLException e) {
            System.out.println("discoMP3DAO -> deleteMP3: Error al eliminar disco MP3");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

}
