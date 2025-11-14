package co.edu.poli.negocio;

import co.edu.poli.datos.usuarioDAO;
import co.edu.poli.datos.proveedorDAO;
import co.edu.poli.model.usuario;
import co.edu.poli.model.proveedor;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de negocio para la administración del sistema.
 * Gestiona usuarios y proveedores a través de sus DAO,
 * sin acceder directamente a la base de datos.
 */
public class adminManager {

    private usuarioDAO usuarioDao;
    private proveedorDAO proveedorDao;

    public adminManager() {
        this.usuarioDao = new usuarioDAO();
        this.proveedorDao = new proveedorDAO();
    }

    /**
     * Lista todos los usuarios registrados en el sistema.
     * Usa los métodos del DAO para leer usuarios uno por uno,
     * y se detiene cuando no encuentra más registros.
     */
    public void listarUsuarios() {
        System.out.println("\n---- LISTA DE USUARIOS ----");

        List<usuario> usuarios = new ArrayList<>();

        int id = 1;
        while (true) {
            usuario u = usuarioDao.readUsuario(id);
            if (u == null) break; // si no existe más, se detiene
            usuarios.add(u);
            id++;
        }

        if (usuarios.isEmpty()) {
            System.out.println("No existen usuarios registrados en el sistema.");
            return;
        }

        for (usuario u : usuarios) {
            System.out.println("ID: " + u.getId_usuario()
                    + " | Nombre: " + u.getNombre()
                    + " | Correo: " + u.getCorreo()
                    + " | Contraseña: " + u.getContrasena());
        }
    }

    /**
     * Lista todos los proveedores registrados en el sistema.
     * Recorre los IDs en orden hasta que ya no existan.
     */
    public void listarProveedores() {
        System.out.println("\n---- LISTA DE PROVEEDORES ----");

        List<proveedor> proveedores = new ArrayList<>();

        int id = 1;
        while (true) {
            proveedor p = proveedorDao.readProveedor(id);
            if (p == null) break;
            proveedores.add(p);
            id++;
        }

        if (proveedores.isEmpty()) {
            System.out.println("No existen proveedores registrados en el sistema.");
            return;
        }

        for (proveedor p : proveedores) {
            System.out.println("ID: " + p.getId_proveedor()
                    + " | Nombre: " + p.getNombre()
                    + " | Correo: " + p.getCorreo()
                    + " | Contraseña: " + p.getContrasena()
                    + " | Calificaciones: " + p.getCalificaciones());
        }
    }

    /**
     * Elimina un usuario (usa el método delete del DAO).
     */
    public void eliminarUsuario(int idUsuario) {
        usuarioDao.deleteUsuario(idUsuario);
    }

    /**
     * Elimina un proveedor (usa el método delete del DAO).
     */
    public void eliminarProveedor(int idProveedor) {
        proveedorDao.deleteProveedor(idProveedor);
    }
}
