package co.edu.poli.datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar la relación muchos-a-muchos entre vinilo y cancion.
 * Tabla intermedia: vinilo_cancion
 */
public class vinilo_cancionDAO {

    public vinilo_cancionDAO() {}

    /**
     * Agrega una canción a un vinilo.
     */
    public void addCancionToVinilo(int id_vinilo, int id_cancion) {
        String sql = "INSERT INTO vinilo_cancion (id_vinilo, id_cancion) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_vinilo);
            stmt.setInt(2, id_cancion);
            stmt.executeUpdate();

            System.out.println("vinilo_cancionDAO -> addCancionToVinilo: Cancion agregada al vinilo");

        } catch (SQLException e) {
            System.out.println("vinilo_cancionDAO -> addCancionToVinilo: Error al agregar cancion al vinilo");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Elimina una canción de un vinilo.
     */
    public void removeCancionFromVinilo(int id_vinilo, int id_cancion) {
        String sql = "DELETE FROM vinilo_cancion WHERE id_vinilo = ? AND id_cancion = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_vinilo);
            stmt.setInt(2, id_cancion);
            stmt.executeUpdate();

            System.out.println("vinilo_cancionDAO -> removeCancionFromVinilo: Cancion eliminada del vinilo");

        } catch (SQLException e) {
            System.out.println("vinilo_cancionDAO -> removeCancionFromVinilo: Error al eliminar cancion del vinilo");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Devuelve las canciones asociadas a un vinilo.
     */
    public List<Integer> getCancionesByVinilo(int id_vinilo) {
        List<Integer> canciones = new ArrayList<>();
        String sql = "SELECT id_cancion FROM vinilo_cancion WHERE id_vinilo = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_vinilo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                canciones.add(rs.getInt("id_cancion"));
            }

            System.out.println("vinilo_cancionDAO -> getCancionesByVinilo: Canciones recuperadas");

        } catch (SQLException e) {
            System.out.println("vinilo_cancionDAO -> getCancionesByVinilo: Error al consultar canciones");
            System.out.println("Detalles: " + e.getMessage());
        }

        return canciones;
    }

    /**
     * Devuelve los vinilos donde aparece una canción.
     */
    public List<Integer> getVinilosByCancion(int id_cancion) {
        List<Integer> vinilos = new ArrayList<>();
        String sql = "SELECT id_vinilo FROM vinilo_cancion WHERE id_cancion = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_cancion);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vinilos.add(rs.getInt("id_vinilo"));
            }

            System.out.println("vinilo_cancionDAO -> getVinilosByCancion: Vinilos recuperados");

        } catch (SQLException e) {
            System.out.println("vinilo_cancionDAO -> getVinilosByCancion: Error al consultar vinilos");
            System.out.println("Detalles: " + e.getMessage());
        }

        return vinilos;
    }
}
