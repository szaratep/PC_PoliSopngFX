package co.edu.poli.negocio;

import co.edu.poli.datos.*;
import co.edu.poli.model.*;

/**
 * Clase de lógica de negocio para la gestión del catálogo de vinilos.
 * 
 * Esta clase NO realiza consultas SQL directamente.
 * Todas las operaciones con la base de datos se manejan
 * a través de viniloDAO.
 */
public class catalogoManager {

    private final viniloDAO viniloDao;

    /**
     * Constructor que inicializa el DAO necesario.
     */
    public catalogoManager() {
        this.viniloDao = new viniloDAO();
    }

    /**
     * Agrega un vinilo nuevo al catálogo.
     */
    public void agregarViniloCatalogo(int idVinilo, String nombre, String artista, int anio, double precio, int inventario) {
        vinilo v = new vinilo(idVinilo, nombre, artista, anio, precio, inventario, null);
        viniloDao.createVinilo(v);
        System.out.println("catalogoManager -> agregarViniloCatalogo: Vinilo agregado al catálogo (" + nombre + ")");
    }

    /**
     * Elimina un vinilo existente del catálogo.
     */
    public void eliminarViniloCatalogo(int idVinilo) {
        viniloDao.deleteVinilo(idVinilo);
        System.out.println("catalogoManager -> eliminarViniloCatalogo: Vinilo eliminado (ID: " + idVinilo + ")");
    }

    /**
     * Actualiza la información de un vinilo existente.
     */
    public void actualizarViniloCatalogo(int idVinilo, String nombre, String artista, int anio, double precio, int inventario) {
        vinilo v = new vinilo(idVinilo, nombre, artista, anio, precio, inventario, null);
        viniloDao.updateVinilo(v);
        System.out.println("catalogoManager -> actualizarViniloCatalogo: Vinilo actualizado (ID: " + idVinilo + ")");
    }

    /**
     * Lista todos los vinilos del catálogo.
     */

    /**
     * Consulta un vinilo específico por ID.
     */
    public void verVinilo(int idVinilo) {
        vinilo v = viniloDao.readVinilo(idVinilo);
        if (v != null) {
            System.out.printf("catalogoManager -> verVinilo: ID: %d | Nombre: %s | Artista: %s | Año: %d | Precio: %.2f | Stock: %d\n",
                    v.getId_vinilo(), v.getNombre(), v.getArtista(), v.getAnio(), v.getPrecio(), v.getInventario());
        } else {
            System.out.println("catalogoManager -> verVinilo: No se encontró el vinilo con ID " + idVinilo);
        }
    }
}
