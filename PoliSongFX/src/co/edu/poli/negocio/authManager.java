package co.edu.poli.negocio;

import co.edu.poli.datos.administradorDAO;
import co.edu.poli.datos.proveedorDAO;
import co.edu.poli.datos.usuarioDAO;
import co.edu.poli.model.administrador;
import co.edu.poli.model.proveedor;
import co.edu.poli.model.usuario;

/**
 * Clase de negocio para autenticación y manejo de sesión.
 * Usa los DAO para validar usuarios, proveedores o administradores
 * sin acceder directamente a la base de datos.
 */
public class authManager {

    // Instancias DAO
    private usuarioDAO usuarioDao;
    private proveedorDAO proveedorDao;
    private administradorDAO adminDao;

    // Variables estáticas para mantener sesión activa
    private static usuario usuarioActivo = null;
    private static proveedor proveedorActivo = null;
    private static administrador adminActivo = null;
    private static String rolActivo = null;

    public authManager() {
        this.usuarioDao = new usuarioDAO();
        this.proveedorDao = new proveedorDAO();
        this.adminDao = new administradorDAO();
    }

    /**
     * Inicia sesión como usuario.
     */
    public boolean loginUsuario(String correo, String contrasena) {
        usuario u = buscarUsuarioPorCorreo(correo);

        if (u != null && u.getContrasena().equals(contrasena)) {
            usuarioActivo = u;
            rolActivo = "usuario";
            System.out.println("Sesión iniciada como USUARIO: " + u.getNombre());
            return true;
        } else {
            System.out.println("Credenciales incorrectas: usuario no encontrado o contraseña inválida.");
            return false;
        }
    }

    /**
     * Inicia sesión como proveedor.
     */
    public boolean loginProveedor(String correo, String contrasena) {
        proveedor p = buscarProveedorPorCorreo(correo);

        if (p != null && p.getContrasena().equals(contrasena)) {
            proveedorActivo = p;
            rolActivo = "proveedor";
            System.out.println("Sesión iniciada como PROVEEDOR: " + p.getNombre());
            return true;
        } else {
            System.out.println("Credenciales incorrectas: proveedor no encontrado o contraseña inválida.");
            return false;
        }
    }

    /**
     * Inicia sesión como administrador.
     */
    public boolean loginAdmin(String correo, String contrasena) {
        administrador a = buscarAdminPorCorreo(correo);

        if (a != null && a.getContrasena().equals(contrasena)) {
            adminActivo = a;
            rolActivo = "admin";
            System.out.println("Sesión iniciada como ADMINISTRADOR: " + a.getNombre());
            return true;
        } else {
            System.out.println("Credenciales incorrectas: administrador no encontrado o contraseña inválida.");
            return false;
        }
    }

    /**
     * Cierra cualquier sesión activa.
     */
    public void logout() {
        usuarioActivo = null;
        proveedorActivo = null;
        adminActivo = null;
        rolActivo = null;
        System.out.println("Sesión cerrada correctamente.");
    }

    // --------------------------
    // MÉTODOS AUXILIARES PRIVADOS
    // --------------------------

    /**
     * Busca un usuario por correo usando usuarioDAO.
     */
    private usuario buscarUsuarioPorCorreo(String correo) {
        for (int id = 1; ; id++) {
            usuario u = usuarioDao.readUsuario(id);
            if (u == null) break;
            if (u.getCorreo().equals(correo)) return u;
        }
        return null;
    }

    /**
     * Busca un proveedor por correo usando proveedorDAO.
     */
    private proveedor buscarProveedorPorCorreo(String correo) {
        for (int id = 1; ; id++) {
            proveedor p = proveedorDao.readProveedor(id);
            if (p == null) break;
            if (p.getCorreo().equals(correo)) return p;
        }
        return null;
    }

    /**
     * Busca un administrador por correo usando administradorDAO.
     */
    private administrador buscarAdminPorCorreo(String correo) {
        for (int id = 1; ; id++) {
            administrador a = adminDao.readAdmin(id);
            if (a == null) break;
            if (a.getCorreo().equals(correo)) return a;
        }
        return null;
    }

    // --------------------------
    // GETTERS DE SESIÓN
    // --------------------------

    public static usuario getUsuarioActivo() { return usuarioActivo; }
    public static proveedor getProveedorActivo() { return proveedorActivo; }
    public static administrador getAdminActivo() { return adminActivo; }
    public static String getRolActivo() { return rolActivo; }
}
