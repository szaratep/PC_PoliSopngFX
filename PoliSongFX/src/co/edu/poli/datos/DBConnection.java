package co.edu.poli.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase responsable de gestionar la conexión a la base de datos MySQL.
 * Implementa el patrón Singleton para garantizar que solo exista una
 * conexión activa durante el ciclo de vida de la aplicación.
 *
 * <p><b>Características:</b></p>
 * <ul>
 *     <li>Carga del driver JDBC al iniciar la clase</li>
 *     <li>Gestión de una única instancia de conexión</li>
 *     <li>Método para cerrar la conexión al final del programa</li>
 * </ul>
 *
 * <p><b>Uso recomendado:</b></p>
 * <pre>
 * Connection conn = DBConnection.getConnection();
 * // ejecutar consultas
 * DBConnection.closeConnection();
 * </pre>
 *
 * @author Sebastian
 * @version 1.0
 */
public class DBConnection {

    /** Driver JDBC para MySQL */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    /** URL de conexión a la base de datos */
    private static final String URL = "jdbc:mysql://localhost:3306/polisongdb";

    /** Usuario de la base de datos */
    private static final String USER = "root";

    /** Contraseña del usuario de base de datos */
    private static final String PASSWORD = "szaratep";

    /** Instancia de conexión (Singleton) */
    private static Connection connection;

    /**
     * Bloque estático que carga el driver JDBC al iniciar la clase.
     * Se ejecuta una sola vez.
     */
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Error cargando el driver MySQL: " + e.getMessage());
        }
    }

    /**
     * Obtiene una conexión activa a la base de datos.
     * Si la conexión no existe o ha sido cerrada, crea una nueva.
     *
     * @return objeto {@link Connection} para realizar consultas SQL
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión a BD establecida correctamente");
            }
        } catch (SQLException e) {
            System.err.println("Error conectando a la BD: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Cierra la conexión activa a la base de datos, si existe y está abierta.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("Error cerrando conexión: " + e.getMessage());
        }
    }
}
