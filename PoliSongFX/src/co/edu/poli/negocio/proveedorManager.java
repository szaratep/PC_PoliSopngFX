package co.edu.poli.negocio;

import co.edu.poli.datos.*;
import co.edu.poli.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Lógica de negocio para la gestión de proveedores.
 * Esta clase usa los DAOs existentes (sin SQL directo salvo consultas específicas).
 */
public class proveedorManager {

    private final proveedorDAO proveedorDao;
    private final pedidoDAO pedidoDao;
    private final pedidoDetalleDAO pedidoDetalleDao;

    /**
     * Constructor: inicializa los DAOs.
     */
    public proveedorManager() {
        this.proveedorDao = new proveedorDAO();
        this.pedidoDao = new pedidoDAO();
        this.pedidoDetalleDao = new pedidoDetalleDAO();
    }

    /**
     * Registra un nuevo proveedor.
     * El correo debe existir previamente en la tabla correo.
     */
    public void registrarProveedor(int idProveedor, String nombre, String correo, String contrasena, int calificaciones) {
        proveedor p = new proveedor(idProveedor, nombre, correo, contrasena, calificaciones);
        proveedorDao.createProveedor(p);
        System.out.println("proveedorManager -> registrarProveedor: Proveedor registrado correctamente.");
    }

    /**
     * Actualiza un proveedor existente.
     */
    public void actualizarProveedor(int idProveedor, String nombre, String correo, String contrasena, int calificaciones) {
        proveedor p = new proveedor(idProveedor, nombre, correo, contrasena, calificaciones);
        proveedorDao.updateProveedor(p);
        System.out.println("proveedorManager -> actualizarProveedor: Proveedor actualizado correctamente.");
    }

    /**
     * Elimina un proveedor por su ID.
     */
    public void eliminarProveedor(int idProveedor) {
        proveedorDao.deleteProveedor(idProveedor);
        System.out.println("proveedorManager -> eliminarProveedor: Proveedor eliminado correctamente.");
    }

    /**
     * Lee un proveedor por su ID.
     */
    public void verProveedor(int idProveedor) {
        proveedor p = proveedorDao.readProveedor(idProveedor);
        if (p != null) {
            System.out.println("proveedorManager -> verProveedor: Proveedor encontrado.");
            System.out.println("ID: " + p.getId_proveedor() + " | Nombre: " + p.getNombre() +
                    " | Correo: " + p.getCorreo() + " | Calificación: " + p.getCalificaciones());
        } else {
            System.out.println("proveedorManager -> verProveedor: No existe el proveedor con ID " + idProveedor);
        }
    }

    /**
     * Lista los pedidos con estado "Pendiente".
     * (Esta consulta es especial, así que se mantiene SQL directa controlada aquí).
     */
    public void verPedidosPendientes() {
        System.out.println("---- LISTA DE PEDIDOS PENDIENTES ----");

        String sql = """
            SELECT pd.id_pedido, pd.id_detalle, pd.tipo_producto, pd.id_producto, pd.cantidad, pd.precio_unitario
            FROM pedido_detalle pd
            INNER JOIN pedido p ON pd.id_pedido = p.id_pedido
            WHERE p.estado = 'Pendiente'
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                System.out.printf(
                        "Pedido ID: %d | Detalle ID: %d | Tipo: %s | Producto ID: %d | Cantidad: %d | Precio: %.2f%n",
                        rs.getInt("id_pedido"),
                        rs.getInt("id_detalle"),
                        rs.getString("tipo_producto"),
                        rs.getInt("id_producto"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio_unitario")
                );
            }

            if (!hayResultados) {
                System.out.println("No hay pedidos pendientes en este momento.");
            }

        } catch (SQLException e) {
            System.err.println("Error al listar pedidos pendientes: " + e.getMessage());
        }
    }

    /**
     * Actualiza el inventario de un vinilo.
     * Usa SQL directa porque no hay viniloDAO implementado.
     */
    public void gestionarInventarioVinilo(int idVinilo, int nuevoInventario) {
        String sql = "UPDATE vinilo SET inventario = ? WHERE id_vinilo = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nuevoInventario);
            ps.setInt(2, idVinilo);
            int filas = ps.executeUpdate();

            if (filas > 0)
                System.out.println("Inventario actualizado correctamente para vinilo ID: " + idVinilo);
            else
                System.out.println("No se encontró el vinilo con ID: " + idVinilo);

        } catch (SQLException e) {
            System.err.println("Error al actualizar inventario de vinilo: " + e.getMessage());
        }
    }
}
