package co.edu.poli.negocio;

import java.util.ArrayList;
import java.util.List;

import co.edu.poli.datos.*;
import co.edu.poli.model.*;

/**
 * Clase de lógica de negocio para la gestión de carritos.
 * 
 * Esta clase NO ejecuta SQL directamente. 
 * Toda la comunicación con la base de datos se hace a través 
 * de carritoDAO y carritoItemDAO.
 */
public class carritoManager {

    private final carritoDAO carritoDao;
    private final carritoItemDAO itemDao;

    /**
     * Constructor que inicializa los DAOs necesarios.
     */
    public carritoManager() {
        this.carritoDao = new carritoDAO();
        this.itemDao = new carritoItemDAO();
    }

    /**
     * Crea un nuevo carrito asociado a un usuario.
     */
    public void crearCarrito(int idCarrito, int idUsuario) {
        carrito c = new carrito(idCarrito, idUsuario, null);
        carritoDao.createCarrito(c);
        System.out.println("carritoManager -> crearCarrito: Carrito creado para el usuario con ID " + idUsuario);
    }

    /**
     * Agrega una canción al carrito.
     */
    public void agregarCancion(int idItem, int idCarrito, int idCancion, int cantidad) {
        carritoItem item = new carritoItem(idItem, idCarrito, "cancion", idCancion, null, null, cantidad);
        itemDao.createItem(item);
        System.out.println("carritoManager -> agregarCancion: Canción agregada (ID: " + idCancion + ")");
    }

    /**
     * Agrega un vinilo al carrito.
     */
    public void agregarVinilo(int idItem, int idCarrito, int idVinilo, int cantidad) {
        carritoItem item = new carritoItem(idItem, idCarrito, "vinilo", null, idVinilo, null, cantidad);
        itemDao.createItem(item);
        System.out.println("carritoManager -> agregarVinilo: Vinilo agregado (ID: " + idVinilo + ")");
    }

    /**
     * Agrega un disco MP3 al carrito.
     */
    public void agregarMP3(int idItem, int idCarrito, int idMP3, int cantidad) {
        carritoItem item = new carritoItem(idItem, idCarrito, "mp3", null, null, idMP3, cantidad);
        itemDao.createItem(item);
        System.out.println("carritoManager -> agregarMP3: Disco MP3 agregado (ID: " + idMP3 + ")");
    }

    /**
     * Ver el carrito de un usuario.
     */
    public void verCarrito(int idCarrito) {
        carrito c = carritoDao.readCarrito(idCarrito);
        if (c != null) {
            System.out.println("carritoManager -> verCarrito: Carrito encontrado (ID Usuario: " + c.getId_usuario() + ")");
        } else {
            System.out.println("carritoManager -> verCarrito: No se encontró el carrito con ID " + idCarrito);
        }
    }

    /**
     * Ver un ítem específico.
     */
    public void verItem(int idItem) {
        carritoItem item = itemDao.readItem(idItem);
        if (item != null) {
            System.out.println("carritoManager -> verItem: Item encontrado (tipo: " + item.getTipo_producto() + ")");
        } else {
            System.out.println("carritoManager -> verItem: No existe el item con ID " + idItem);
        }
    }

    /**
     * Actualizar un ítem del carrito.
     */
    public void actualizarItem(int idItem, int idCarrito, String tipo, Integer idCancion, Integer idVinilo, Integer idMP3, int cantidad) {
        carritoItem item = new carritoItem(idItem, idCarrito, tipo, idCancion, idVinilo, idMP3, cantidad);
        itemDao.updateItem(item);
        System.out.println("carritoManager -> actualizarItem: Item actualizado (ID: " + idItem + ")");
    }

    /**
     * Eliminar un ítem del carrito.
     */
    public void eliminarItem(int idItem) {
        itemDao.deleteItem(idItem);
        System.out.println("carritoManager -> eliminarItem: Item eliminado (ID: " + idItem + ")");
    }

    /**
     * Eliminar un carrito completo.
     */
    public void eliminarCarrito(int idCarrito) {
        carritoDao.deleteCarrito(idCarrito);
        System.out.println("carritoManager -> eliminarCarrito: Carrito eliminado (ID: " + idCarrito + ")");
    }
    
    public List<carritoItem> listarItems(int idUsuario) {
        carrito c = carritoDao.readCarrito(idUsuario); // asumimos que el id del carrito = idUsuario
        if (c == null) return new ArrayList<>();
        
        // Aquí necesitas un método en carritoItemDAO para listar items por carrito
        return itemDao.listarItemsPorCarrito(c.getId_carrito());
    }
    public boolean actualizarCantidad(int idCarrito, int idItem, int nuevaCantidad) {

        if (nuevaCantidad <= 0) {
            System.out.println("carritoManager -> actualizarCantidad: cantidad inválida");
            return false;
        }

        carritoItemDAO dao = new carritoItemDAO();
        carritoItem item = dao.readItem(idItem);

        if (item == null) {
            System.out.println("carritoManager -> actualizarCantidad: item no existe");
            return false;
        }

        if (item.getId_carrito() != idCarrito) {
            System.out.println("carritoManager -> actualizarCantidad: item no pertenece al carrito");
            return false;
        }

        item.setCantidad(nuevaCantidad);
        dao.updateItem(item);

        System.out.println("carritoManager -> actualizarCantidad: cantidad actualizada");
        return true;
    }


}