package co.edu.poli.datos;

import co.edu.poli.model.pedidoDetalle;
import java.sql.*;

/**
 * DAO (Data Access Object) para la entidad {@link pedidoDetalle}.
 * Gestiona las operaciones CRUD (Create, Read, Update, Delete)
 * sobre la tabla pedido_detalle en la base de datos MySQL.
 * 
 * Relaciones principales:
 * - id_pedido: Clave foránea que referencia a la tabla pedido.
 * - id_producto: Clave foránea que puede referenciar a vinilo o canción, según tipo_Producto.
 */
public class pedidoDetalleDAO {

    /** Constructor vacío */
    public pedidoDetalleDAO() {}

    /**
     * Crea un nuevo detalle de pedido en la base de datos.
     * 
     * @param det Objeto {@link pedidoDetalle} con los datos del detalle a crear.
     */
    public void createPedDetalle(pedidoDetalle det) {
        String sql = "INSERT INTO pedido_detalle (id_detalle, id_pedido, tipo_Producto, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, det.getId_detalle());
            stmt.setInt(2, det.getId_pedido());
            stmt.setString(3, det.getTipo_Producto());
            stmt.setInt(4, det.getId_producto());
            stmt.setInt(5, det.getCantidad());
            stmt.setDouble(6, det.getPrecio_unitario());

            stmt.executeUpdate();
            System.out.println("pedidoDetalleDAO -> createPedDetalle: Detalle de pedido creado correctamente");

        } catch (SQLException e) {
            System.out.println("pedidoDetalleDAO -> createPedDetalle: Error al crear detalle de pedido");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Lee un detalle de pedido desde la base de datos mediante su ID.
     * 
     * @param id Identificador único del detalle de pedido.
     * @return Objeto {@link pedidoDetalle} con los datos encontrados o {@code null} si no existe.
     */
    public pedidoDetalle readPedDetalle(int id) {
        String sql = "SELECT * FROM pedido_detalle WHERE id_detalle = ?";
        pedidoDetalle det = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                det = new pedidoDetalle(
                        rs.getInt("id_detalle"),
                        rs.getInt("id_pedido"),
                        rs.getString("tipo_Producto"),
                        rs.getInt("id_producto"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio_unitario")
                );
                System.out.println("pedidoDetalleDAO -> readPedDetalle: Detalle de pedido encontrado");
            } else {
                System.out.println("pedidoDetalleDAO -> readPedDetalle: Detalle de pedido no existe");
            }

        } catch (SQLException e) {
            System.out.println("pedidoDetalleDAO -> readPedDetalle: Error al leer detalle de pedido");
            System.out.println("Detalles: " + e.getMessage());
        }

        return det;
    }

    /**
     * Actualiza la información de un detalle de pedido existente.
     * 
     * @param det Objeto {@link pedidoDetalle} con los nuevos datos a actualizar.
     */
    public void updatePedDetalle(pedidoDetalle det) {
        String sql = "UPDATE pedido_detalle SET id_pedido = ?, tipo_Producto = ?, id_producto = ?, cantidad = ?, precio_unitario = ? WHERE id_detalle = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, det.getId_pedido());
            stmt.setString(2, det.getTipo_Producto());
            stmt.setInt(3, det.getId_producto());
            stmt.setInt(4, det.getCantidad());
            stmt.setDouble(5, det.getPrecio_unitario());
            stmt.setInt(6, det.getId_detalle());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("pedidoDetalleDAO -> updatePedDetalle: Detalle de pedido actualizado");
            } else {
                System.out.println("pedidoDetalleDAO -> updatePedDetalle: Detalle de pedido no existe");
            }

        } catch (SQLException e) {
            System.out.println("pedidoDetalleDAO -> updatePedDetalle: Error al actualizar detalle de pedido");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Elimina un detalle de pedido de la base de datos.
     * 
     * @param id Identificador único del detalle de pedido a eliminar.
     */
    public void deletePedDetalle(int id) {
        String sql = "DELETE FROM pedido_detalle WHERE id_detalle = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("pedidoDetalleDAO -> deletePedDetalle: Detalle de pedido eliminado");
            } else {
                System.out.println("pedidoDetalleDAO -> deletePedDetalle: Detalle de pedido no existe");
            }

        } catch (SQLException e) {
            System.out.println("pedidoDetalleDAO -> deletePedDetalle: Error al eliminar detalle de pedido");
            System.out.println("Detalles: " + e.getMessage());
        }
    }
}
