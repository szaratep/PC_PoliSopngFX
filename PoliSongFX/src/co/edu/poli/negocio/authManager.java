package co.edu.poli.negocio;

import co.edu.poli.datos.usuarioDAO;
import co.edu.poli.datos.proveedorDAO;
import co.edu.poli.datos.administradorDAO;
import co.edu.poli.model.usuario;
import co.edu.poli.model.proveedor;
import co.edu.poli.model.administrador;

public class authManager {

    private usuarioDAO usuarioDao;
    private proveedorDAO proveedorDao;
    private administradorDAO adminDao;

    private static usuario usuarioActivo = null;
    private static proveedor proveedorActivo = null;
    private static administrador adminActivo = null;
    private static String rolActivo = null;

    public authManager() {
        this.usuarioDao = new usuarioDAO();
        this.proveedorDao = new proveedorDAO();
        this.adminDao = new administradorDAO();
    }

    // --------------------------
    // LOGIN USUARIO
    // --------------------------
    public boolean loginUsuario(String correo, String contrasena) {

        usuario u = usuarioDao.readUsuario(correo, contrasena);

        if (u != null) {
            usuarioActivo = u;
            rolActivo = "usuario";
            System.out.println("✅ Sesión iniciada como USUARIO: " + u.getNombre());
            return true;
        }

        System.out.println("❌ Usuario no encontrado o contraseña incorrecta");
        return false;
    }

    // --------------------------
    // LOGIN PROVEEDOR
    // --------------------------
    public boolean loginProveedor(String correo, String contrasena) {

        proveedor p = proveedorDao.readProveedor(correo, contrasena);

        if (p != null) {
            proveedorActivo = p;
            rolActivo = "proveedor";
            System.out.println("✅ Sesión iniciada como PROVEEDOR: " + p.getNombre());
            return true;
        }

        System.out.println("❌ Proveedor no encontrado o contraseña incorrecta");
        return false;
    }

    // --------------------------
    // LOGIN ADMIN
    // --------------------------
    public boolean loginAdmin(String correo, String contrasena) {

        administrador a = adminDao.readAdmin(correo, contrasena);

        if (a != null) {
            adminActivo = a;
            rolActivo = "admin";
            System.out.println("✅ Sesión iniciada como ADMINISTRADOR: " + a.getNombre());
            return true;
        }

        System.out.println("❌ Administrador no encontrado o contraseña incorrecta");
        return false;
    }

    // --------------------------
    // LOGOUT
    // --------------------------
    public void logout() {
        usuarioActivo = null;
        proveedorActivo = null;
        adminActivo = null;
        rolActivo = null;
        System.out.println("✅ Sesión cerrada");
    }

    // --------------------------
    // GETTERS SESIÓN
    // --------------------------
    public static usuario getUsuarioActivo() { return usuarioActivo; }
    public static proveedor getProveedorActivo() { return proveedorActivo; }
    public static administrador getAdminActivo() { return adminActivo; }
    public static String getRolActivo() { return rolActivo; }

}
