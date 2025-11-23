package co.edu.poli.controller;

import co.edu.poli.model.administrador;
import co.edu.poli.negocio.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class AdminMenuController {

    @FXML private javafx.scene.control.Label labelAdminNombre;
    @FXML private Button btnGestionUsuarios;
    @FXML private Button btnGestionProductos;
    @FXML private Button btnMiPerfil;
    @FXML private Button btnCerrarSesion;
    @FXML private Button btnVolverMenu;

    // --------------------------
    // INICIALIZACIÓN
    // --------------------------
    @FXML
    public void initialize() {
        cargarNombreAdministrador();
    }

    private void cargarNombreAdministrador() {
        Object obj = Session.getUsuarioActual();
        if (obj != null && obj instanceof administrador) {
            administrador admin = (administrador) obj;
            labelAdminNombre.setText("Administrador: " + admin.getNombre());
        } else {
            labelAdminNombre.setText("Administrador: ---");
        }
    }

    // --------------------------
    // NAVEGACIÓN
    // --------------------------
    
    @FXML
    private void volverMenuPrincipal() {
        try {
            Stage stageActual = (Stage) btnVolverMenu.getScene().getWindow();
            stageActual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/MainPage.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("PoliSong");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirMiPerfil() {
        try {
            Stage stageActual = (Stage) btnMiPerfil.getScene().getWindow();
            stageActual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/MiPerfilView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Mi Perfil");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarSesion() {
        Session.cerrarSesion();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sesión cerrada");
        alert.setHeaderText(null);
        alert.setContentText("Sesión cerrada correctamente ✅");
        alert.showAndWait();

        try {
            Stage stageActual = (Stage) btnCerrarSesion.getScene().getWindow();
            stageActual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/MainPage.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("PoliSong");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --------------------------
    // MÉTODOS VACÍOS POR COMPLETAR
    // --------------------------
    
    @FXML
    private void abrirGestionUsuarios() {
        /*try {
            // Cerrar la ventana actual
            Stage stageActual = (Stage) btnGestionUsuarios.getScene().getWindow();
            stageActual.close();

            // Abrir ventana de gestión de usuarios (pendiente implementar FXML)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/GestionarUsuariosView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Gestión de Usuarios");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @FXML
    private void abrirGestionProductos() {
        try {
            // Cerrar la ventana actual (menú del admin)
            Stage stageActual = (Stage) btnGestionProductos.getScene().getWindow();
            stageActual.close();

            // Abrir ventana de gestión de productos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/GestionProductosAdminView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Gestión de Productos");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
