package co.edu.poli.controller;

import co.edu.poli.negocio.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class ProveedorMenuController {

    @FXML private javafx.scene.control.Label labelProveedorNombre;
    @FXML private Button btnMiPerfil;
    @FXML private Button btnRegistrarProducto;
    @FXML private Button btnInventario;
    @FXML private Button btnCerrarSesion;

    // --------------------------
    // NAVEGACIÓN
    // --------------------------
    private void cambiarVista(String rutaFXML, Node nodo) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));
            Stage stageActual = (Stage) nodo.getScene().getWindow();
            stageActual.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error cargando vista: " + rutaFXML);
        }
    }

    @FXML
    private void abrirPerfil() {
        cambiarVista("/co/edu/poli/view/MiPerfilView.fxml", btnMiPerfil);
    }

    @FXML
    private void abrirRegistrarProducto() {
        // TODO: Implementar la navegación al registro de productos
    }

    @FXML
    private void abrirGestionInventario() {
        // TODO: Implementar la navegación al inventario o catálogo
    }

    // --------------------------
    // CERRAR SESIÓN
    // --------------------------
    @FXML
    private void cerrarSesion(javafx.event.ActionEvent event) {
        // Limpiar sesión
        Session.cerrarSesion();

        // Mostrar alerta
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sesión cerrada");
        alert.setHeaderText(null);
        alert.setContentText("Sesión cerrada correctamente ✅");
        alert.showAndWait();

        // Abrir ventana principal
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/MainPage.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("PoliSong");
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar ventana actual
            Stage actual = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            actual.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
