package co.edu.poli.negocio;

import co.edu.poli.datos.*;
import co.edu.poli.model.*;

/**
 * Clase de negocio que gestiona los usuarios del sistema.
 * Se apoya en los DAO {@link usuarioDAO} y {@link correoDAO}.
 */
public class usuarioManager {

    private final usuarioDAO usuarioDao;
    private final correoDAO correoDao;

    /** Constructor por defecto */
    public usuarioManager() {
        this.usuarioDao = new usuarioDAO();
        this.correoDao = new correoDAO();
    }

    /**
     * Registra un nuevo usuario.
     * Si el correo no existe en la base de datos, se crea automáticamente.
     *
     * @param idUsuario ID del usuario
     * @param nombre Nombre del usuario
     * @param correo Dirección de correo
     * @param contrasena Contraseña del usuario
     */
    public void registrarUsuario(int idUsuario, String nombre, String correo, String contrasena) {
        System.out.println("usuarioManager -> registrarUsuario: Intentando registrar usuario...");

        // Verificar si el correo existe, si no, lo crea
        correo correoExistente = correoDao.readCorreo(correo);
        if (correoExistente == null) {
            System.out.println("usuarioManager -> registrarUsuario: Correo no existe. Creando nuevo correo...");
            correo nuevoCorreo = new correo(correo);
            correoDao.createCorreo(nuevoCorreo);
        }

        usuario u = new usuario(idUsuario, nombre, correo, contrasena);
        usuarioDao.createUsuario(u);
        System.out.println("usuarioManager -> registrarUsuario: Usuario registrado correctamente.");
    }

    /**
     * Consulta la información de un usuario por su ID.
     */
    public void verUsuario(int idUsuario) {
        System.out.println("usuarioManager -> verUsuario: Consultando usuario ID " + idUsuario);
        usuario u = usuarioDao.readUsuario(idUsuario);

        if (u != null) {
            System.out.println("ID Usuario: " + u.getId_usuario() +
                    " | Nombre: " + u.getNombre() +
                    " | Correo: " + u.getCorreo() +
                    " | Contraseña: " + u.getContrasena());
        } else {
            System.out.println("usuarioManager -> verUsuario: No existe el usuario con ID " + idUsuario);
        }
    }

    /**
     * Actualiza la información de un usuario existente.
     * Si el nuevo correo no existe, se crea automáticamente.
     */
    public void actualizarUsuario(int idUsuario, String nuevoNombre, String nuevoCorreo, String nuevaContrasena) {
        System.out.println("usuarioManager -> actualizarUsuario: Intentando actualizar usuario ID " + idUsuario);

        // Verificar si el nuevo correo existe, si no, se crea
        correo correoExistente = correoDao.readCorreo(nuevoCorreo);
        if (correoExistente == null) {
            System.out.println("usuarioManager -> actualizarUsuario: Correo no existe. Creando nuevo correo...");
            correo nuevo = new correo(nuevoCorreo);
            correoDao.createCorreo(nuevo);
        }

        usuario u = new usuario(idUsuario, nuevoNombre, nuevoCorreo, nuevaContrasena);
        usuarioDao.updateUsuario(u);
        System.out.println("usuarioManager -> actualizarUsuario: Usuario actualizado correctamente.");
    }

    /**
     * Elimina un usuario por su ID.
     */
    public void eliminarUsuario(int idUsuario) {
        System.out.println("usuarioManager -> eliminarUsuario: Eliminando usuario ID " + idUsuario);
        usuarioDao.deleteUsuario(idUsuario);
        System.out.println("usuarioManager -> eliminarUsuario: Usuario eliminado correctamente.");
    }

    /**
     * Lista todos los usuarios con sus correos asociados.
     * (Consulta realizada directamente con SQL JOIN dentro de usuarioDAO)
     */
    public void listarUsuarios() {
        System.out.println("usuarioManager -> listarUsuarios: Mostrando lista de usuarios...");
        try (var conn = DBConnection.getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(
                     "SELECT u.id_usuario, u.nombre, c.correo " +
                     "FROM usuario u INNER JOIN correo c ON u.correo_id = c.id_correo")) {

            System.out.println("---- LISTA DE USUARIOS ----");
            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                System.out.println("ID: " + id + " | Nombre: " + nombre + " | Correo: " + correo);
            }
        } catch (Exception e) {
            System.out.println("usuarioManager -> listarUsuarios: Error al listar usuarios");
            System.out.println("Detalles: " + e.getMessage());
        }
    }
}
