package co.edu.poli.negocio;

import co.edu.poli.datos.*;
import co.edu.poli.model.*;

/**
 * Clase que gestiona las búsquedas globales del sistema PoliSongStock.
 * Permite listar o buscar usuarios, proveedores, canciones, vinilos
 * y playlists públicas usando los DAO correspondientes.
 */
public class busquedaManager {

    private usuarioDAO usuarioDao;
    private proveedorDAO proveedorDao;
    private cancionDAO cancionDao;
    private viniloDAO viniloDao;
    private playListDAO playlistDao;

    public busquedaManager() {
        usuarioDao = new usuarioDAO();
        proveedorDao = new proveedorDAO();
        cancionDao = new cancionDAO();
        viniloDao = new viniloDAO();
        playlistDao = new playListDAO();
    }

    // ----------------------------------------------------------------
    // MÉTODOS DE BÚSQUEDA GENERAL
    // ----------------------------------------------------------------

    public void buscarUsuarios() {
        System.out.println("=== BUSCANDO USUARIOS ===");

        // Idealmente tendrías un método en usuarioDAO que devuelva todos los usuarios.
        // Aquí simularemos una lista para el ejemplo.
        for (int id = 1; ; id++) {
            usuario u = usuarioDao.readUsuario(id);
            if (u == null) break;
            System.out.println("ID: " + u.getId_usuario() +
                               " | Nombre: " + u.getNombre() +
                               " | Correo: " + u.getCorreo());
        }
    }

    public void buscarProveedores() {
        System.out.println("=== BUSCANDO PROVEEDORES ===");

        for (int id = 1; ; id++) {
            proveedor p = proveedorDao.readProveedor(id);
            if (p == null) break;
            System.out.println("ID: " + p.getId_proveedor() +
                               " | Nombre: " + p.getNombre() +
                               " | Correo: " + p.getCorreo() +
                               " | Calificaciones: " + p.getCalificaciones());
        }
    }

    public void buscarCanciones(String filtro) {
        System.out.println("=== BUSCANDO CANCIONES ===");

        try (var conn = DBConnection.getConnection();
             var stmt = conn.prepareStatement(
                 "SELECT * FROM cancion WHERE nombre LIKE ?")) {

            stmt.setString(1, "%" + filtro + "%");
            var rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_cancion") +
                        " | Nombre: " + rs.getString("nombre") +
                        " | Duración: " + rs.getDouble("duracion") +
                        " | Precio: $" + rs.getDouble("precio") +
                        " | Tamaño: " + rs.getDouble("tamano_mb") + " MB");
            }

        } catch (Exception e) {
            System.out.println("Error al buscar canciones: " + e.getMessage());
        }
    }

    public void buscarVinilos(String filtro) {
        System.out.println("=== BUSCANDO VINILOS ===");

        try (var conn = DBConnection.getConnection();
             var stmt = conn.prepareStatement(
                 "SELECT * FROM vinilo WHERE nombre LIKE ? OR artista LIKE ?")) {

            stmt.setString(1, "%" + filtro + "%");
            stmt.setString(2, "%" + filtro + "%");
            var rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_vinilo") +
                        " | Nombre: " + rs.getString("nombre") +
                        " | Artista: " + rs.getString("artista") +
                        " | Año: " + rs.getInt("anio") +
                        " | Precio: $" + rs.getDouble("precio") +
                        " | Inventario: " + rs.getInt("inventario"));
            }

        } catch (Exception e) {
            System.out.println("Error al buscar vinilos: " + e.getMessage());
        }
    }

    public void buscarPlaylistsPublicas(String filtro) {
        System.out.println("=== BUSCANDO PLAYLISTS PÚBLICAS ===");

        try (var conn = DBConnection.getConnection();
             var stmt = conn.prepareStatement(
                 "SELECT * FROM playlist WHERE publica = 1 AND nombre LIKE ?")) {

            stmt.setString(1, "%" + filtro + "%");
            var rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_playlist") +
                        " | Nombre: " + rs.getString("nombre") +
                        " | ID Usuario: " + rs.getInt("id_usuario"));
            }

        } catch (Exception e) {
            System.out.println("Error al buscar playlists públicas: " + e.getMessage());
        }
    }
}
