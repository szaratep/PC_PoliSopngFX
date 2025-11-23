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
        // TODO: Implementar gestión de usuarios
    }

    @FXML
    private void abrirGestionProductos() {
        // TODO: Implementar gestión de productos
    }
}
