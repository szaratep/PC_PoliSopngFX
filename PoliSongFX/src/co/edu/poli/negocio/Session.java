package co.edu.poli.negocio;

public class Session {

    private static Object usuarioActual;  
    private static String rolActual;      

    public static void setSesion(Object obj, String rol) {
        usuarioActual = obj;
        rolActual = rol;
    }

    public static Object getUsuarioActual() {
        return usuarioActual;
    }

    public static String getRolActual() {
        return rolActual;
    }

    public static boolean haySesion() {
        return usuarioActual != null;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
        rolActual = null;
    }
}
