package co.edu.poli.datos;

import co.edu.poli.model.usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad usuario (corregido para usar correo_id FK).
 */
public class usuarioDAO {

    public usuarioDAO() {}

    public void createUsuario(usuario u) {
        String findCorreoSQL = "SELECT id_correo FROM correo WHERE correo = ?";
        String insertSQL = "INSERT INTO usuario (id_usuario, nombre, correo_id, contrasena) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement findStmt = conn.prepareStatement(findCorreoSQL);
            findStmt.setString(1, u.getCorreo());
            ResultSet rs = findStmt.executeQuery();

            Integer idCorreo = null;
            if (rs.next()) {
                idCorreo = rs.getInt("id_correo");
            } else {
                System.out.println("usuarioDAO -> createUsuario: Correo no existe, créelo antes de registrar usuario");
                return;
            }

            PreparedStatement insertStmt = conn.prepareStatement(insertSQL);
            insertStmt.setInt(1, u.getId_usuario());
            insertStmt.setString(2, u.getNombre());
            insertStmt.setInt(3, idCorreo);
            insertStmt.setString(4, u.getContrasena());

            insertStmt.executeUpdate();
            System.out.println("usuarioDAO -> createUsuario: Usuario creado correctamente");

        } catch (SQLException e) {
            System.out.println("usuarioDAO -> createUsuario: Error al crear usuario");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    public usuario readUsuario(int id) {
        String sql = """
            SELECT u.id_usuario, u.nombre, u.contrasena, c.correo
            FROM usuario u
            LEFT JOIN correo c ON u.correo_id = c.id_correo
            WHERE u.id_usuario = ?
        """;
        usuario u = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                u = new usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("contrasena")
                );
                System.out.println("usuarioDAO -> readUsuario: Usuario encontrado");
            } else {
                System.out.println("usuarioDAO -> readUsuario: Usuario no existe");
            }

        } catch (SQLException e) {
            System.out.println("usuarioDAO -> readUsuario: Error al leer usuario");
            System.out.println("Detalles: " + e.getMessage());
        }

        return u;
    }
    
    public usuario readUsuario(String correo, String pass) {
        String sql = """
            SELECT u.id_usuario, u.nombre, u.contrasena, c.correo
            FROM usuario u
            INNER JOIN correo c ON u.correo_id = c.id_correo
            WHERE c.correo = ? AND u.contrasena = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.setString(2, pass);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("usuarioDAO -> readUsuario: Usuario encontrado");
                return new usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("contrasena")
                );
            } else {
                System.out.println("usuarioDAO -> readUsuario: Usuario no existe");
            }

        } catch (SQLException e) {
            System.out.println("usuarioDAO -> readUsuario ERROR: " + e.getMessage());
        }

        return null;
    }


    public void updateUsuario(usuario u) {
        String findCorreoSQL = "SELECT id_correo FROM correo WHERE correo = ?";
        String updateSQL = "UPDATE usuario SET nombre = ?, correo_id = ?, contrasena = ? WHERE id_usuario = ?";

        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement findStmt = conn.prepareStatement(findCorreoSQL);
            findStmt.setString(1, u.getCorreo());
            ResultSet rs = findStmt.executeQuery();

            Integer idCorreo = null;
            if (rs.next()) {
                idCorreo = rs.getInt("id_correo");
            } else {
                System.out.println("usuarioDAO -> updateUsuario: Correo no existe, no se puede actualizar");
                return;
            }

            PreparedStatement stmt = conn.prepareStatement(updateSQL);
            stmt.setString(1, u.getNombre());
            stmt.setInt(2, idCorreo);
            stmt.setString(3, u.getContrasena());
            stmt.setInt(4, u.getId_usuario());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("usuarioDAO -> updateUsuario: Usuario actualizado");
            } else {
                System.out.println("usuarioDAO -> updateUsuario: Usuario no existe");
            }

        } catch (SQLException e) {
            System.out.println("usuarioDAO -> updateUsuario: Error al actualizar usuario");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    public void deleteUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("usuarioDAO -> deleteUsuario: Usuario eliminado");
            else System.out.println("usuarioDAO -> deleteUsuario: Usuario no existe");

        } catch (SQLException e) {
            System.out.println("usuarioDAO -> deleteUsuario: Error al eliminar usuario");
            System.out.println("Detalles: " + e.getMessage());
        }
    }
    
    public List<usuario> listarUsuarios() {
        List<usuario> lista = new ArrayList<>();

        String sql = "SELECT u.id_usuario, u.nombre, c.correo " +
                     "FROM usuario u INNER JOIN correo c ON u.correo_id = c.id_correo";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usuario u = new usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        "N/A"
                );
                lista.add(u);
            }

        } catch (Exception e) {
            System.out.println("usuarioDAO -> listarUsuarios ERROR: " + e.getMessage());
        }

        return lista;
    }

}
