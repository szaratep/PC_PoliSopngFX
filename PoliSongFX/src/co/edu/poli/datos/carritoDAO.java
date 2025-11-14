package co.edu.poli.datos;

import co.edu.poli.model.carrito;
import java.sql.*;

/**
 * DAO (Data Access Object) para la entidad {@link carrito}.
 * Gestiona las operaciones CRUD (Create, Read, Update, Delete)
 * sobre la tabla carrito en la base de datos MySQL.
 * 
 * Relaciones principales:
 * - id_usuario: Clave foránea que referencia a la tabla usuario.
 */
public class carritoDAO {

    /** Constructor vacío */
    public carritoDAO() {}

    /**
     * Crea un nuevo registro de carrito en la base de datos.
     * 
     * @param c Objeto {@link carrito} con los datos del carrito a crear.
     */
    public void createCarrito(carrito c) {
        String sql = "INSERT INTO carrito (id_carrito, id_usuario) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getId_carrito());
            stmt.setInt(2, c.getId_usuario());

            stmt.executeUpdate();
            System.out.println("carritoDAO -> createCarrito: Carrito creado correctamente");

        } catch (SQLException e) {
            System.out.println("carritoDAO -> createCarrito: Error al crear carrito");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Lee un carrito específico desde la base de datos mediante su ID.
     * 
     * @param id Identificador único del carrito.
     * @return Objeto {@link carrito} con la información encontrada o {@code null} si no existe.
     */
    public carrito readCarrito(int id) {
        String sql = "SELECT * FROM carrito WHERE id_carrito = ?";
        carrito car = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                car = new carrito(
                        rs.getInt("id_carrito"),
                        rs.getInt("id_usuario"),
                        null // lista de items, se carga en otra consulta si aplica
                );
                System.out.println("carritoDAO -> readCarrito: Carrito encontrado");
            } else {
                System.out.println("carritoDAO -> readCarrito: Carrito no existe");
            }

        } catch (SQLException e) {
            System.out.println("carritoDAO -> readCarrito: Error al leer carrito");
            System.out.println("Detalles: " + e.getMessage());
        }

        return car;
    }

    /**
     * Actualiza los datos de un carrito existente.
     * 
     * @param c Objeto {@link carrito} con los nuevos datos a actualizar.
     */
    public void updateCarrito(carrito c) {
        String sql = "UPDATE carrito SET id_usuario = ? WHERE id_carrito = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getId_usuario());
            stmt.setInt(2, c.getId_carrito());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("carritoDAO -> updateCarrito: Carrito actualizado");
            } else {
                System.out.println("carritoDAO -> updateCarrito: Carrito no existe");
            }

        } catch (SQLException e) {
            System.out.println("carritoDAO -> updateCarrito: Error al actualizar carrito");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Elimina un carrito de la base de datos por su ID.
     * 
     * @param id Identificador único del carrito a eliminar.
     */
    public void deleteCarrito(int id) {
        String sql = "DELETE FROM carrito WHERE id_carrito = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("carritoDAO -> deleteCarrito: Carrito eliminado");
            } else {
                System.out.println("carritoDAO -> deleteCarrito: Carrito no existe");
            }

        } catch (SQLException e) {
            System.out.println("carritoDAO -> deleteCarrito: Error al eliminar carrito");
            System.out.println("Detalles: " + e.getMessage());
        }
    }
}
