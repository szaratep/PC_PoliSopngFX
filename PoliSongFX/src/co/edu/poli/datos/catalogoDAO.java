package co.edu.poli.datos;

import co.edu.poli.model.catalogo;
import java.sql.*;

/**
 * DAO (Data Access Object) para la entidad {@link catalogo}.
 * Gestiona las operaciones CRUD (Create, Read, Update, Delete)
 * sobre la tabla catalogo en la base de datos MySQL.
 * 
 * Relaciones principales:
 * - id_proveedor: Clave foránea que referencia a la tabla proveedor.
 * - id_vinilo: Clave foránea que referencia a la tabla vinilo.
 */
public class catalogoDAO {

    /** Constructor vacío */
    public catalogoDAO() {}

    /**
     * Crea un nuevo catálogo en la base de datos.
     * 
     * @param c Objeto {@link catalogo} con los datos del catálogo a crear.
     */
    public void createCatalogo(catalogo c) {
        String sql = "INSERT INTO catalogo (id_catalogo, id_proveedor, id_vinilo, fecha_publicacion) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getId_catalogo());
            stmt.setInt(2, c.getId_proveedor());
            stmt.setInt(3, c.getId_vinilo());
            stmt.setDate(4, new java.sql.Date(c.getFecha_publicacion().getTime()));

            stmt.executeUpdate();
            System.out.println("catalogoDAO -> createCatalogo: Catalogo creado correctamente");

        } catch (SQLException e) {
            System.out.println("catalogoDAO -> createCatalogo: Error al crear catalogo");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Lee un catálogo desde la base de datos mediante su ID.
     * 
     * @param id Identificador único del catálogo.
     * @return Objeto {@link catalogo} con los datos encontrados o {@code null} si no existe.
     */
    public catalogo readCatalogo(int id) {
        String sql = "SELECT * FROM catalogo WHERE id_catalogo = ?";
        catalogo cat = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cat = new catalogo(
                        rs.getInt("id_catalogo"),
                        rs.getInt("id_proveedor"),
                        rs.getInt("id_vinilo"),
                        rs.getDate("fecha_publicacion")
                );
                System.out.println("catalogoDAO -> readCatalogo: Catalogo encontrado");
            } else {
                System.out.println("catalogoDAO -> readCatalogo: Catalogo no existe");
            }

        } catch (SQLException e) {
            System.out.println("catalogoDAO -> readCatalogo: Error al leer catalogo");
            System.out.println("Detalles: " + e.getMessage());
        }

        return cat;
    }

    /**
     * Actualiza los datos de un catálogo existente.
     * 
     * @param c Objeto {@link catalogo} con los nuevos datos a actualizar.
     */
    public void updateCatalogo(catalogo c) {
        String sql = "UPDATE catalogo SET id_proveedor = ?, id_vinilo = ?, fecha_publicacion = ? WHERE id_catalogo = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getId_proveedor());
            stmt.setInt(2, c.getId_vinilo());
            stmt.setDate(3, new java.sql.Date(c.getFecha_publicacion().getTime()));
            stmt.setInt(4, c.getId_catalogo());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("catalogoDAO -> updateCatalogo: Catalogo actualizado");
            } else {
                System.out.println("catalogoDAO -> updateCatalogo: Catalogo no existe");
            }

        } catch (SQLException e) {
            System.out.println("catalogoDAO -> updateCatalogo: Error al actualizar catalogo");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Elimina un catálogo de la base de datos.
     * 
     * @param id Identificador único del catálogo a eliminar.
     */
    public void deleteCatalogo(int id) {
        String sql = "DELETE FROM catalogo WHERE id_catalogo = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("catalogoDAO -> deleteCatalogo: Catalogo eliminado");
            } else {
                System.out.println("catalogoDAO -> deleteCatalogo: Catalogo no existe");
            }

        } catch (SQLException e) {
            System.out.println("catalogoDAO -> deleteCatalogo: Error al eliminar catalogo");
            System.out.println("Detalles: " + e.getMessage());
        }
    }
}
