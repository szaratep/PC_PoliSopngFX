package co.edu.poli.negocio;

import co.edu.poli.datos.*;
import co.edu.poli.model.*;

/**
 * Lógica de negocio para la gestión de pedidos y sus detalles.
 * 
 * Esta clase usa exclusivamente los DAOs para comunicarse con la base de datos.
 */
public class pedidoManager {

    private final pedidoDAO pedidoDao;
    private final pedidoDetalleDAO detalleDao;

    /**
     * Constructor: inicializa los DAOs.
     */
    public pedidoManager() {
        this.pedidoDao = new pedidoDAO();
        this.detalleDao = new pedidoDetalleDAO();
    }

    /**
     * Crea un nuevo pedido en la base de datos.
     */
    public int crearPedido(int idUsuario, String estado) {

        pedido p = new pedido(0, idUsuario, new java.sql.Date(System.currentTimeMillis()), estado);

        int idPedido = pedidoDao.createPedido(p);

        if (idPedido <= 0) {
            System.out.println("pedidoManager -> crearPedido: ERROR");
        } else {
            System.out.println("pedidoManager -> crearPedido: Pedido ID=" + idPedido);
        }

        return idPedido;
    }

    public void agregarDetalleAuto(
            int idPedido,
            String tipoProducto,
            int idProducto,
            int cantidad,
            double precioUnitario) {

        pedidoDetalle det = new pedidoDetalle(0, idPedido, tipoProducto, idProducto, cantidad, precioUnitario);

        detalleDao.createPedDetalle(det);
    }


    /**
     * Agrega un detalle al pedido.
     */
    public void agregarDetalle(int idDetalle, int idPedido, String tipoProducto, int idProducto, int cantidad, double precioUnitario) {
        pedidoDetalle detalle = new pedidoDetalle(idDetalle, idPedido, tipoProducto, idProducto, cantidad, precioUnitario);
        detalleDao.createPedDetalle(detalle);
        System.out.println("pedidoManager -> agregarDetalle: Detalle agregado al pedido ID " + idPedido);
    }

    /**
     * Verifica un pedido existente en la base de datos.
     */
    public void verPedido(int idPedido) {
        pedido p = pedidoDao.readPedido(idPedido);
        if (p != null) {
            System.out.println("pedidoManager -> verPedido: Pedido encontrado");
            System.out.println("ID Pedido: " + p.getId_pedido() +
                    " | ID Usuario: " + p.getId_usuario() +
                    " | Fecha: " + p.getFecha() +
                    " | Estado: " + p.getEstado());
        } else {
            System.out.println("pedidoManager -> verPedido: Pedido no encontrado (ID: " + idPedido + ")");
        }
    }

    /**
     * Verifica un detalle de pedido existente en la base de datos.
     */
    public void verDetalle(int idDetalle) {
        pedidoDetalle det = detalleDao.readPedDetalle(idDetalle);
        if (det != null) {
            System.out.println("pedidoManager -> verDetalle: Detalle encontrado");
            System.out.println("ID Detalle: " + det.getId_detalle() +
                    " | Pedido ID: " + det.getId_pedido() +
                    " | Tipo: " + det.getTipo_Producto() +
                    " | Producto ID: " + det.getId_producto() +
                    " | Cantidad: " + det.getCantidad() +
                    " | Precio: $" + det.getPrecio_unitario());
        } else {
            System.out.println("pedidoManager -> verDetalle: Detalle no encontrado (ID: " + idDetalle + ")");
        }
    }

    /**
     * Actualiza el estado de un pedido.
     */
    public void actualizarEstadoPedido(int idPedido, String nuevoEstado) {
        pedido p = pedidoDao.readPedido(idPedido);
        if (p == null) {
            System.out.println("pedidoManager -> actualizarEstadoPedido: Pedido no encontrado (ID: " + idPedido + ")");
            return;
        }

        p.setEstado(nuevoEstado);
        pedidoDao.updatePedido(p);
        System.out.println("pedidoManager -> actualizarEstadoPedido: Pedido actualizado a estado '" + nuevoEstado + "'");
    }

    /**
     * Actualiza un detalle específico de pedido.
     */
    public void actualizarDetalle(int idDetalle, int idPedido, String tipoProducto, int idProducto, int cantidad, double precioUnitario) {
        pedidoDetalle det = new pedidoDetalle(idDetalle, idPedido, tipoProducto, idProducto, cantidad, precioUnitario);
        detalleDao.updatePedDetalle(det);
        System.out.println("pedidoManager -> actualizarDetalle: Detalle actualizado (ID: " + idDetalle + ")");
    }

    /**
     * Elimina un detalle de pedido.
     */
    public void eliminarDetalle(int idDetalle) {
        detalleDao.deletePedDetalle(idDetalle);
        System.out.println("pedidoManager -> eliminarDetalle: Detalle eliminado (ID: " + idDetalle + ")");
    }

    /**
     * Elimina un pedido completo.
     */
    public void eliminarPedido(int idPedido) {
        pedido p = pedidoDao.readPedido(idPedido);
        if (p == null) {
            System.out.println("pedidoManager -> eliminarPedido: Pedido no encontrado (ID: " + idPedido + ")");
            return;
        }

        pedidoDao.deletePedido(idPedido);
        System.out.println("pedidoManager -> eliminarPedido: Pedido eliminado (ID: " + idPedido + ")");
    }
    
    // Dentro de la clase pedidoManager

    /**
     * Devuelve todos los pedidos de un usuario.
     */
    public java.util.List<pedido> getPedidosUsuario(int idUsuario) {
        return pedidoDao.readPedidosUsuario(idUsuario);
    }

    /**
     * Devuelve todos los detalles de un pedido.
     */
    public java.util.List<pedidoDetalle> getDetallesPedido(int idPedido) {
        return detalleDao.readDetallesPedido(idPedido);
    }

}
