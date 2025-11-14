package co.edu.poli.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import co.edu.poli.model.correo;

/**
 * DAO (Data Access Object) para la entidad {@link correo}.
 * Gestiona las operaciones básicas sobre la tabla correo
 * en la base de datos MySQL.
 */
public class correoDAO {

    /** Constructor vacío */
    public correoDAO() {}

    /**
     * Crea un nuevo registro de correo en la base de datos.
     * 
     * @param obj Objeto {@link correo} con la información del correo a registrar.
     */
    public void createCorreo(correo obj) {
        String sql = "INSERT INTO correo (correo) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, obj.getCorreo());
            ps.executeUpdate();

            System.out.println("correoDAO -> createCorreo: Correo registrado correctamente");

        } catch (SQLException e) {
            System.out.println("correoDAO -> createCorreo: Error al registrar correo");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Busca un correo en la base de datos.
     * 
     * @param correo Dirección de correo a buscar.
     * @return Objeto {@link correo} si existe, o {@code null} si no se encontró.
     */
    public correo readCorreo(String correo) {
        String sql = "SELECT correo FROM correo WHERE correo = ?";
        correo resultado = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                resultado = new correo(rs.getString("correo"));
                System.out.println("correoDAO -> readCorreo: Correo encontrado");
            } else {
                System.out.println("correoDAO -> readCorreo: Correo no existe");
            }

        } catch (SQLException e) {
            System.out.println("correoDAO -> readCorreo: Error al leer correo");
            System.out.println("Detalles: " + e.getMessage());
        }

        return resultado;
    }

    /**
     * Elimina un correo de la base de datos.
     * 
     * @param correo Dirección de correo a eliminar.
     */
    public void deleteCorreo(String correo) {
        String sql = "DELETE FROM correo WHERE correo = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("correoDAO -> deleteCorreo: Correo eliminado");
            } else {
                System.out.println("correoDAO -> deleteCorreo: Correo no encontrado");
            }

        } catch (SQLException e) {
            System.out.println("correoDAO -> deleteCorreo: Error al eliminar correo");
            System.out.println("Detalles: " + e.getMessage());
        }
    }
}
