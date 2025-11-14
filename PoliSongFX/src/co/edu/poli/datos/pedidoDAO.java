package co.edu.poli.datos;

import co.edu.poli.model.pedido;
import java.sql.*;

/**
 * DAO (Data Access Object) para la entidad {@link pedido}.
 * Gestiona las operaciones CRUD (Create, Read, Update, Delete)
 * sobre la tabla pedido en la base de datos MySQL.
 * 
 * Relaciones principales:
 * - id_usuario: Clave foránea que referencia a la tabla usuario.
 */
public class pedidoDAO {

    /** Constructor vacío */
    public pedidoDAO() {}

    /**
     * Crea un nuevo pedido en la base de datos.
     * 
     * @param p Objeto {@link pedido} con los datos del pedido a crear.
     */
    public void createPedido(pedido p) {
        String sql = "INSERT INTO pedido (id_pedido, id_usuario, fecha, estado) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getId_pedido());
            stmt.setInt(2, p.getId_usuario());
            stmt.setDate(3, new java.sql.Date(p.getFecha().getTime()));
            stmt.setString(4, p.getEstado());

            stmt.executeUpdate();
            System.out.println("pedidoDAO -> createPedido: Pedido creado correctamente");

        } catch (SQLException e) {
            System.out.println("pedidoDAO -> createPedido: Error al crear pedido");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Lee un pedido desde la base de datos mediante su ID.
     * 
     * @param id Identificador único del pedido.
     * @return Objeto {@link pedido} con los datos encontrados o {@code null} si no existe.
     */
    public pedido readPedido(int id) {
        String sql = "SELECT * FROM pedido WHERE id_pedido = ?";
        pedido ped = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ped = new pedido(
                        rs.getInt("id_pedido"),
                        rs.getInt("id_usuario"),
                        rs.getDate("fecha"),
                        rs.getString("estado")
                );
                System.out.println("pedidoDAO -> readPedido: Pedido encontrado");
            } else {
                System.out.println("pedidoDAO -> readPedido: Pedido no existe");
            }

        } catch (SQLException e) {
            System.out.println("pedidoDAO -> readPedido: Error al leer pedido");
            System.out.println("Detalles: " + e.getMessage());
        }

        return ped;
    }

    /**
     * Actualiza la información de un pedido existente.
     * 
     * @param p Objeto {@link pedido} con los nuevos datos a actualizar.
     */
    public void updatePedido(pedido p) {
        String sql = "UPDATE pedido SET id_usuario = ?, fecha = ?, estado = ? WHERE id_pedido = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getId_usuario());
            stmt.setDate(2, new java.sql.Date(p.getFecha().getTime()));
            stmt.setString(3, p.getEstado());
            stmt.setInt(4, p.getId_pedido());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("pedidoDAO -> updatePedido: Pedido actualizado");
            } else {
                System.out.println("pedidoDAO -> updatePedido: Pedido no existe");
            }

        } catch (SQLException e) {
            System.out.println("pedidoDAO -> updatePedido: Error al actualizar pedido");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Elimina un pedido de la base de datos.
     * 
     * @param id Identificador único del pedido a eliminar.
     */
    public void deletePedido(int id) {
        String sql = "DELETE FROM pedido WHERE id_pedido = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("pedidoDAO -> deletePedido: Pedido eliminado");
            } else {
                System.out.println("pedidoDAO -> deletePedido: Pedido no existe");
            }

        } catch (SQLException e) {
            System.out.println("pedidoDAO -> deletePedido: Error al eliminar pedido");
            System.out.println("Detalles: " + e.getMessage());
        }
    }
}
