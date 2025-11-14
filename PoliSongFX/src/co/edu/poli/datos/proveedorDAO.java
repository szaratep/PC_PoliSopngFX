package co.edu.poli.datos;

import co.edu.poli.model.proveedor;
import java.sql.*;

/**
 * DAO para proveedor - usa correo_id FK.
 */
public class proveedorDAO {

    public proveedorDAO() {}

    public void createProveedor(proveedor p) {
        String findCorreoSQL = "SELECT id_correo FROM correo WHERE correo = ?";
        String insertSQL = "INSERT INTO proveedor (id_proveedor, nombre, correo_id, contrasena, calificaciones) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement findStmt = conn.prepareStatement(findCorreoSQL);
            findStmt.setString(1, p.getCorreo());
            ResultSet rs = findStmt.executeQuery();

            Integer idCorreo = null;
            if (rs.next()) {
                idCorreo = rs.getInt("id_correo");
            } else {
                System.out.println("proveedorDAO -> createProveedor: Correo no existe, créelo antes de registrar proveedor");
                return;
            }

            PreparedStatement insertStmt = conn.prepareStatement(insertSQL);
            insertStmt.setInt(1, p.getId_proveedor());
            insertStmt.setString(2, p.getNombre());
            insertStmt.setInt(3, idCorreo);
            insertStmt.setString(4, p.getContrasena());
            insertStmt.setInt(5, p.getCalificaciones());

            insertStmt.executeUpdate();
            System.out.println("proveedorDAO -> createProveedor: Proveedor creado correctamente");

        } catch (SQLException e) {
            System.out.println("proveedorDAO -> createProveedor: Error al crear proveedor");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    public proveedor readProveedor(int id) {
        String sql = """
            SELECT p.id_proveedor, p.nombre, p.contrasena, p.calificaciones, c.correo
            FROM proveedor p
            LEFT JOIN correo c ON p.correo_id = c.id_correo
            WHERE p.id_proveedor = ?
        """;
        proveedor p = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                p = new proveedor(
                    rs.getInt("id_proveedor"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("contrasena"),
                    rs.getInt("calificaciones")
                );
                System.out.println("proveedorDAO -> readProveedor: Proveedor encontrado");
            } else {
                System.out.println("proveedorDAO -> readProveedor: Proveedor no existe");
            }

        } catch (SQLException e) {
            System.out.println("proveedorDAO -> readProveedor: Error al leer proveedor");
            System.out.println("Detalles: " + e.getMessage());
        }

        return p;
    }

    public void updateProveedor(proveedor p) {
        String findCorreoSQL = "SELECT id_correo FROM correo WHERE correo = ?";
        String updateSQL = "UPDATE proveedor SET nombre = ?, correo_id = ?, contrasena = ?, calificaciones = ? WHERE id_proveedor = ?";

        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement findStmt = conn.prepareStatement(findCorreoSQL);
            findStmt.setString(1, p.getCorreo());
            ResultSet rs = findStmt.executeQuery();

            Integer idCorreo = null;
            if (rs.next()) idCorreo = rs.getInt("id_correo");
            else {
                System.out.println("proveedorDAO -> updateProveedor: Correo no existe, no se puede actualizar");
                return;
            }

            PreparedStatement stmt = conn.prepareStatement(updateSQL);
            stmt.setString(1, p.getNombre());
            stmt.setInt(2, idCorreo);
            stmt.setString(3, p.getContrasena());
            stmt.setInt(4, p.getCalificaciones());
            stmt.setInt(5, p.getId_proveedor());

            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("proveedorDAO -> updateProveedor: Proveedor actualizado");
            else System.out.println("proveedorDAO -> updateProveedor: Proveedor no existe");

        } catch (SQLException e) {
            System.out.println("proveedorDAO -> updateProveedor: Error al actualizar proveedor");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    public void deleteProveedor(int id) {
        String sql = "DELETE FROM proveedor WHERE id_proveedor = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("proveedorDAO -> deleteProveedor: Proveedor eliminado");
            else System.out.println("proveedorDAO -> deleteProveedor: Proveedor no existe");
        } catch (SQLException e) {
            System.out.println("proveedorDAO -> deleteProveedor: Error al eliminar proveedor");
            System.out.println("Detalles: " + e.getMessage());
        }
    }
}
