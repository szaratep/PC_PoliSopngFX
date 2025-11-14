package co.edu.poli.datos;

import co.edu.poli.model.administrador;
import java.sql.*;

/**
 * DAO (Data Access Object) para la entidad {@link administrador}.
 * Gestiona las operaciones CRUD (Create, Read, Update, Delete)
 * sobre la tabla administrador en la base de datos MySQL.
 * 
 * Relaciones principales:
 * - correo_id: Clave foránea que referencia a la tabla correo.
 */
public class administradorDAO {

    /** Constructor vacío */
    public administradorDAO() {}

    /**
     * Crea un nuevo administrador en la base de datos.
     * 
     * @param admin Objeto {@link administrador} con los datos del administrador a registrar.
     */
    public void createAdmin(administrador admin) {
        String findCorreoSQL = "SELECT id_correo FROM correo WHERE correo = ?";
        String insertAdminSQL = "INSERT INTO administrador (id_admin, nombre, correo_id, contrasena) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            // Buscar id_correo correspondiente
            PreparedStatement findStmt = conn.prepareStatement(findCorreoSQL);
            findStmt.setString(1, admin.getCorreo());
            ResultSet rs = findStmt.executeQuery();

            Integer idCorreo = null;
            if (rs.next()) {
                idCorreo = rs.getInt("id_correo");
            } else {
                System.out.println("administradorDAO -> createAdmin: Correo no existe, créelo antes de registrar administrador");
                return;
            }

            // Insertar administrador
            PreparedStatement insertStmt = conn.prepareStatement(insertAdminSQL);
            insertStmt.setInt(1, admin.getId_admin());
            insertStmt.setString(2, admin.getNombre());
            insertStmt.setInt(3, idCorreo);
            insertStmt.setString(4, admin.getContrasena());

            insertStmt.executeUpdate();
            System.out.println("administradorDAO -> createAdmin: Administrador creado correctamente");

        } catch (SQLException e) {
            System.out.println("administradorDAO -> createAdmin: Error al crear administrador");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Lee un administrador desde la base de datos mediante su ID.
     * 
     * @param id Identificador único del administrador.
     * @return Objeto {@link administrador} con los datos encontrados o {@code null} si no existe.
     */
    public administrador readAdmin(int id) {
        String sql = """
            SELECT a.id_admin, a.nombre, a.contrasena, c.correo
            FROM administrador a
            LEFT JOIN correo c ON a.correo_id = c.id_correo
            WHERE a.id_admin = ?
        """;

        administrador admin = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                admin = new administrador(
                        rs.getInt("id_admin"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("contrasena")
                );
                System.out.println("administradorDAO -> readAdmin: Administrador encontrado");
            } else {
                System.out.println("administradorDAO -> readAdmin: Administrador no existe");
            }

        } catch (SQLException e) {
            System.out.println("administradorDAO -> readAdmin: Error al leer administrador");
            System.out.println("Detalles: " + e.getMessage());
        }

        return admin;
    }

    /**
     * Actualiza los datos de un administrador existente.
     * 
     * @param admin Objeto {@link administrador} con los nuevos datos a actualizar.
     */
    public void updateAdmin(administrador admin) {
        String findCorreoSQL = "SELECT id_correo FROM correo WHERE correo = ?";
        String updateSQL = "UPDATE administrador SET nombre = ?, correo_id = ?, contrasena = ? WHERE id_admin = ?";

        try (Connection conn = DBConnection.getConnection()) {

            // Buscar id_correo correspondiente
            PreparedStatement findStmt = conn.prepareStatement(findCorreoSQL);
            findStmt.setString(1, admin.getCorreo());
            ResultSet rs = findStmt.executeQuery();

            Integer idCorreo = null;
            if (rs.next()) {
                idCorreo = rs.getInt("id_correo");
            } else {
                System.out.println("administradorDAO -> updateAdmin: Correo no existe, no se puede actualizar");
                return;
            }

            // Actualizar administrador
            PreparedStatement stmt = conn.prepareStatement(updateSQL);
            stmt.setString(1, admin.getNombre());
            stmt.setInt(2, idCorreo);
            stmt.setString(3, admin.getContrasena());
            stmt.setInt(4, admin.getId_admin());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("administradorDAO -> updateAdmin: Administrador actualizado");
            } else {
                System.out.println("administradorDAO -> updateAdmin: Administrador no existe");
            }

        } catch (SQLException e) {
            System.out.println("administradorDAO -> updateAdmin: Error al actualizar administrador");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Elimina un administrador de la base de datos.
     * 
     * @param id Identificador único del administrador a eliminar.
     */
    public void deleteAdmin(int id) {
        String sql = "DELETE FROM administrador WHERE id_admin = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("administradorDAO -> deleteAdmin: Administrador eliminado");
            } else {
                System.out.println("administradorDAO -> deleteAdmin: Administrador no existe");
            }

        } catch (SQLException e) {
            System.out.println("administradorDAO -> deleteAdmin: Error al eliminar administrador");
            System.out.println("Detalles: " + e.getMessage());
        }
    }
}
