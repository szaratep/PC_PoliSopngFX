package co.edu.poli.datos;

import co.edu.poli.model.cancion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para la entidad {@link cancion}.
 * Gestiona las operaciones CRUD (Create, Read, Update, Delete)
 * sobre la tabla cancion en la base de datos MySQL.
 */
public class cancionDAO {

    public cancionDAO() {}

    /**
     * Crear Canción (sin especificar ID manual si es autoincrement)
     */
    public int createCancion(cancion song) {
        String sql = "INSERT INTO cancion (nombre, duracion, precio, tamano_mb) VALUES (?, ?, ?, ?)";
        int idGenerado = -1;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, song.getNombre());
            stmt.setDouble(2, song.getDuracion());
            stmt.setDouble(3, song.getPrecio());
            stmt.setDouble(4, song.getTamano_mb());
            stmt.executeUpdate();

            // Recuperar el ID generado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
                System.out.println("cancionDAO -> createCancion: Canción creada con ID " + idGenerado);
            }

        } catch (SQLException e) {
            System.out.println("cancionDAO -> createCancion: Error al crear canción");
            System.out.println("Detalles: " + e.getMessage());
        }

        return idGenerado;
    }

    /**
     * Recuperar una canción por nombre
     */
    public int getIdByNombre(String nombre) {
        String sql = "SELECT id_cancion FROM cancion WHERE nombre = ?";
        int id = -1;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id_cancion");
            }

        } catch (SQLException e) {
            System.out.println("cancionDAO -> getIdByNombre: Error al buscar canción");
            System.out.println("Detalles: " + e.getMessage());
        }

        return id;
    }

    /**
     * Lee una canción desde la base de datos mediante su ID.
     * 
     * @param id Identificador único de la canción.
     * @return Objeto {@link cancion} con los datos encontrados o {@code null} si no existe.
     */
    public cancion readCancion(int id) {
        String sql = "SELECT * FROM cancion WHERE id_cancion = ?";
        cancion song = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                song = new cancion(
                    rs.getString("nombre"),
                    rs.getDouble("duracion"),
                    rs.getDouble("precio"),
                    rs.getDouble("tamano_mb")
                );
                song.setId_cancion(rs.getInt("id_cancion")); // <- esto faltaba
                System.out.println("cancionDAO -> readCancion: Cancion encontrada");
            } else {
                System.out.println("cancionDAO -> readCancion: Cancion no existe");
            }

        } catch (SQLException e) {
            System.out.println("cancionDAO -> readCancion: Error al leer cancion");
            System.out.println("Detalles: " + e.getMessage());
        }

        return song;
    }

    /**
     * Actualiza la información de una canción existente.
     * 
     * @param song Objeto {@link cancion} con los nuevos datos a actualizar.
     */
    public void updateCancion(cancion song) {
        String sql = "UPDATE cancion SET nombre = ?, duracion = ?, precio = ?, tamano_mb = ? WHERE id_cancion = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, song.getNombre());
            stmt.setDouble(2, song.getDuracion());
            stmt.setDouble(3, song.getPrecio());
            stmt.setDouble(4, song.getTamano_mb());
            stmt.setInt(5, song.getId_cancion());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("cancionDAO -> updateCancion: Cancion actualizada");
            } else {
                System.out.println("cancionDAO -> updateCancion: Cancion no existe");
            }

        } catch (SQLException e) {
            System.out.println("cancionDAO -> updateCancion: Error al actualizar cancion");
            System.out.println("Detalles: " + e.getMessage());
        }
    }

    /**
     * Elimina una canción de la base de datos.
     * 
     * @param id Identificador único de la canción a eliminar.
     */
    public void deleteCancion(int id) {
        String sql = "DELETE FROM cancion WHERE id_cancion = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("cancionDAO -> deleteCancion: Cancion eliminada");
            } else {
                System.out.println("cancionDAO -> deleteCancion: Cancion no existe");
            }

        } catch (SQLException e) {
            System.out.println("cancionDAO -> deleteCancion: Error al eliminar cancion");
            System.out.println("Detalles: " + e.getMessage());
        }
    }
    
    public List<cancion> readAllCanciones() {
        List<cancion> lista = new ArrayList<>();
        String sql = "SELECT * FROM cancion";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                cancion c = new cancion(
                    rs.getString("nombre"),
                    rs.getDouble("duracion"),
                    rs.getDouble("precio"),
                    rs.getDouble("tamano_mb")
                );
                c.setId_cancion(rs.getInt("id_cancion")); // importante
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("cancionDAO -> readAllCanciones: " + e.getMessage());
        }
        return lista;
    }

}
