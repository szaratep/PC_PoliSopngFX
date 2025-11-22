package co.edu.poli.datos;

import co.edu.poli.model.carritoItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class carritoItemDAO {

    public carritoItemDAO() {}

    public void createItem(carritoItem item) {
        String sql = """
            INSERT INTO carrito_item 
            (id_item, id_carrito, tipo_producto, id_cancion, id_vinilo, id_mp3, cantidad)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getId_item());
            stmt.setInt(2, item.getId_carrito());
            stmt.setString(3, item.getTipo_producto());
            stmt.setObject(4, item.getId_cancion());
            stmt.setObject(5, item.getId_vinilo());
            stmt.setObject(6, item.getId_mp3());
            stmt.setInt(7, item.getCantidad());

            stmt.executeUpdate();
            System.out.println("carritoItemDAO -> createItem: Item creado correctamente");

        } catch (SQLException e) {
            System.out.println("carritoItemDAO -> createItem: Error al crear item");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    public carritoItem readItem(int id) {
        String sql = "SELECT * FROM carrito_item WHERE id_item = ?";
        carritoItem item = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                item = new carritoItem(
                    rs.getInt("id_item"),
                    rs.getInt("id_carrito"),
                    rs.getString("tipo_producto"),
                    (Integer) rs.getObject("id_cancion"),
                    (Integer) rs.getObject("id_vinilo"),
                    (Integer) rs.getObject("id_mp3"),
                    rs.getInt("cantidad")
                );
                System.out.println("carritoItemDAO -> readItem: Item encontrado");
            } else {
                System.out.println("carritoItemDAO -> readItem: Item no existe");
            }

        } catch (SQLException e) {
            System.out.println("carritoItemDAO -> readItem: Error al leer item");
            System.out.println("Detalles: " + e.getMessage());
        }

        return item;
    }

    public void updateItem(carritoItem item) {
        String sql = """
            UPDATE carrito_item 
            SET id_carrito = ?, tipo_producto = ?, id_cancion = ?, id_vinilo = ?, id_mp3 = ?, cantidad = ?
            WHERE id_item = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getId_carrito());
            stmt.setString(2, item.getTipo_producto());
            stmt.setObject(3, item.getId_cancion());
            stmt.setObject(4, item.getId_vinilo());
            stmt.setObject(5, item.getId_mp3());
            stmt.setInt(6, item.getCantidad());
            stmt.setInt(7, item.getId_item());

            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("carritoItemDAO -> updateItem: Item actualizado");
            else System.out.println("carritoItemDAO -> updateItem: Item no existe");

        } catch (SQLException e) {
            System.out.println("carritoItemDAO -> updateItem: Error al actualizar item");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    public void deleteItem(int id) {
        String sql = "DELETE FROM carrito_item WHERE id_item = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("carritoItemDAO -> deleteItem: Item eliminado");
            else System.out.println("carritoItemDAO -> deleteItem: Item no existe");

        } catch (SQLException e) {
            System.out.println("carritoItemDAO -> deleteItem: Error al eliminar item");
            System.out.println("Detalles: " + e.getMessage());
        }
    }
    
    public List<carritoItem> listarItemsPorCarrito(int idCarrito) {
        List<carritoItem> items = new ArrayList<>();
        String sql = "SELECT * FROM carrito_item WHERE id_carrito = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCarrito);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                items.add(new carritoItem(
                    rs.getInt("id_item"),
                    rs.getInt("id_carrito"),
                    rs.getString("tipo_producto"),
                    (Integer) rs.getObject("id_cancion"),
                    (Integer) rs.getObject("id_vinilo"),
                    (Integer) rs.getObject("id_mp3"),
                    rs.getInt("cantidad")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return items;
    }

}
