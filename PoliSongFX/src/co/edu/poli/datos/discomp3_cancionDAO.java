package co.edu.poli.datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar la relación muchos-a-muchos entre discomp3 y cancion.
 * Tabla intermedia: discomp3_cancion
 */
public class discomp3_cancionDAO {

    public discomp3_cancionDAO() {}

    /**
     * Agrega una canción a un disco MP3.
     */
    public void addCancionToDisco(int id_MP3, int id_cancion) {
        String sql = "INSERT INTO discomp3_cancion (id_MP3, id_cancion) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_MP3);
            stmt.setInt(2, id_cancion);
            stmt.executeUpdate();

            System.out.println("discomp3_cancionDAO -> addCancionToDisco: Cancion agregada al disco");

        } catch (SQLException e) {
            System.out.println("discomp3_cancionDAO -> addCancionToDisco: Error al agregar cancion al disco");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Elimina una canción de un disco MP3.
     */
    public void removeCancionFromDisco(int id_MP3, int id_cancion) {
        String sql = "DELETE FROM discomp3_cancion WHERE id_MP3 = ? AND id_cancion = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_MP3);
            stmt.setInt(2, id_cancion);
            stmt.executeUpdate();

            System.out.println("discomp3_cancionDAO -> removeCancionFromDisco: Cancion eliminada del disco");

        } catch (SQLException e) {
            System.out.println("discomp3_cancionDAO -> removeCancionFromDisco: Error al eliminar cancion del disco");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Devuelve las canciones asociadas a un disco MP3.
     */
    public List<Integer> getCancionesByDisco(int id_MP3) {
        List<Integer> canciones = new ArrayList<>();
        String sql = "SELECT id_cancion FROM discomp3_cancion WHERE id_MP3 = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_MP3);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                canciones.add(rs.getInt("id_cancion"));
            }

            System.out.println("discomp3_cancionDAO -> getCancionesByDisco: Canciones recuperadas");

        } catch (SQLException e) {
            System.out.println("discomp3_cancionDAO -> getCancionesByDisco: Error al consultar canciones");
            System.out.println("Detalles: " + e.getMessage());
        }

        return canciones;
    }

    /**
     * Devuelve los discos MP3 donde aparece una canción.
     */
    public List<Integer> getDiscosByCancion(int id_cancion) {
        List<Integer> discos = new ArrayList<>();
        String sql = "SELECT id_MP3 FROM discomp3_cancion WHERE id_cancion = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_cancion);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                discos.add(rs.getInt("id_MP3"));
            }

            System.out.println("discomp3_cancionDAO -> getDiscosByCancion: Discos recuperados");

        } catch (SQLException e) {
            System.out.println("discomp3_cancionDAO -> getDiscosByCancion: Error al consultar discos");
            System.out.println("Detalles: " + e.getMessage());
        }

        return discos;
    }
}
