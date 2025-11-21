package co.edu.poli.datos;

import co.edu.poli.model.vinilo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para la entidad {@link vinilo}.
 * Gestiona las operaciones CRUD (Create, Read, Update, Delete)
 * sobre la tabla vinilo en la base de datos MySQL.
 * 
 * Relaciones principales:
 * - Puede estar asociado con canciones (no gestionadas directamente en este DAO).
 */
public class viniloDAO {

    /** Constructor vacío */
    public viniloDAO() {}

    /**
     * Crea un nuevo registro de vinilo en la base de datos.
     * 
     * @param v Objeto {@link vinilo} con los datos del vinilo a registrar.
     */
    public void createVinilo(vinilo v) {
        String sql = "INSERT INTO vinilo (id_vinilo, nombre, artista, anio, precio, inventario) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, v.getId_vinilo());
            stmt.setString(2, v.getNombre());
            stmt.setString(3, v.getArtista());
            stmt.setInt(4, v.getAnio());
            stmt.setDouble(5, v.getPrecio());
            stmt.setInt(6, v.getInventario());

            stmt.executeUpdate();
            System.out.println("viniloDAO -> createVinilo: Vinilo creado correctamente");

        } catch (SQLException e) {
            System.out.println("viniloDAO -> createVinilo: Error al crear vinilo");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Lee un vinilo desde la base de datos mediante su ID.
     * 
     * @param id Identificador único del vinilo.
     * @return Objeto {@link vinilo} con los datos encontrados o {@code null} si no existe.
     */
    public vinilo readVinilo(int id) {
        String sql = "SELECT * FROM vinilo WHERE id_vinilo = ?";
        vinilo v = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                v = new vinilo(
                        rs.getInt("id_vinilo"),
                        rs.getString("nombre"),
                        rs.getString("artista"),
                        rs.getInt("anio"),
                        rs.getDouble("precio"),
                        rs.getInt("inventario"),
                        null // las canciones se gestionan en otro DAO
                );
                System.out.println("viniloDAO -> readVinilo: Vinilo encontrado");
            } else {
                System.out.println("viniloDAO -> readVinilo: Vinilo no existe");
            }

        } catch (SQLException e) {
            System.out.println("viniloDAO -> readVinilo: Error al leer vinilo");
            System.out.println("Detalles: " + e.getMessage());
        }

        return v;
    }
    
    public List<vinilo> listarVinilos() {
        String sql = "SELECT * FROM vinilo";
        List<vinilo> vinilos = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                vinilo v = new vinilo(
                        rs.getInt("id_vinilo"),
                        rs.getString("nombre"),
                        rs.getString("artista"),
                        rs.getInt("anio"),
                        rs.getDouble("precio"),
                        rs.getInt("inventario"),
                        null // las canciones se gestionan en otro DAO
                );
                vinilos.add(v);
            }

            System.out.println("viniloDAO -> listarVinilos: Se encontraron " + vinilos.size() + " vinilos");

        } catch (SQLException e) {
            System.out.println("viniloDAO -> listarVinilos: Error al listar vinilos");
            System.out.println("Detalles: " + e.getMessage());
        }

        return vinilos;
    }

    /**
     * Actualiza la información de un vinilo existente.
     * 
     * @param v Objeto {@link vinilo} con los nuevos datos a actualizar.
     */
    public void updateVinilo(vinilo v) {
        String sql = "UPDATE vinilo SET nombre = ?, artista = ?, anio = ?, precio = ?, inventario = ? WHERE id_vinilo = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, v.getNombre());
            stmt.setString(2, v.getArtista());
            stmt.setInt(3, v.getAnio());
            stmt.setDouble(4, v.getPrecio());
            stmt.setInt(5, v.getInventario());
            stmt.setInt(6, v.getId_vinilo());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("viniloDAO -> updateVinilo: Vinilo actualizado");
            } else {
                System.out.println("viniloDAO -> updateVinilo: Vinilo no existe");
            }

        } catch (SQLException e) {
            System.out.println("viniloDAO -> updateVinilo: Error al actualizar vinilo");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Elimina un vinilo de la base de datos.
     * 
     * @param id Identificador único del vinilo a eliminar.
     */
    public void deleteVinilo(int id) {
        String sql = "DELETE FROM vinilo WHERE id_vinilo = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("viniloDAO -> deleteVinilo: Vinilo eliminado");
            } else {
                System.out.println("viniloDAO -> deleteVinilo: Vinilo no existe");
            }

        } catch (SQLException e) {
            System.out.println("viniloDAO -> deleteVinilo: Error al eliminar vinilo");
            System.out.println("Detalles: " + e.getMessage());
        }
    }
}
