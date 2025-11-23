package co.edu.poli.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class UserMenuController {

    // --------------------------
    // MÉTODO BASE PARA CAMBIAR DE VISTA
    // --------------------------
    private void cambiarVista(String rutaFXML, Node nodo) {
        try {
            // Cargar la vista
            Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));

            // Crear una nueva ventana (Stage) para la nueva vista
            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.show();

            // Cerrar la ventana actual
            Stage ventanaActual = (Stage) nodo.getScene().getWindow();
            ventanaActual.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error cargando vista: " + rutaFXML);
        }
    }

    // --------------------------
    // BOTONES PRINCIPALES
    // --------------------------
    @FXML
    private void irMiPerfil(javafx.event.ActionEvent event) {
        cambiarVista("/co/edu/poli/view/MiPerfilView.fxml", (Node) event.getSource());
    }

    @FXML
    private void irCarrito(javafx.event.ActionEvent event) {
        cambiarVista("/co/edu/poli/view/CarritoView.fxml", (Node) event.getSource());
    }

    @FXML
    private void irPlaylist(javafx.event.ActionEvent event) {
        cambiarVista("/co/edu/poli/view/PlaylistView.fxml", (Node) event.getSource());
    }

    @FXML
    private void irHistorial(javafx.event.ActionEvent event) {
        cambiarVista("/co/edu/poli/view/HistorialComprasView.fxml", (Node) event.getSource());
    }

    @FXML
    private void irCalificarProductos(javafx.event.ActionEvent event) {
        cambiarVista("/co/edu/poli/view/CalificarProductoView.fxml", (Node) event.getSource());
    }
    
    @FXML
    void onPlayList(javafx.event.ActionEvent event) {
    	cambiarVista("/co/edu/poli/view/PlayListVUserView.fxml", (Node) event.getSource());
    }

    // --------------------------
    // CERRAR SESIÓN
    // --------------------------
    @FXML
    private void cerrarSesion(javafx.event.ActionEvent event) {
        // 1. Cerrar la sesión
        co.edu.poli.negocio.Session.cerrarSesion();

        // 2. Mostrar alerta de sesión cerrada
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Sesión cerrada");
        alerta.setHeaderText(null);
        alerta.setContentText("La sesión se cerró exitosamente.");
        
        // 3. Esperar a que el usuario presione OK antes de cambiar la vista
        alerta.showAndWait().ifPresent(response -> {
            // 4. Cambiar a la vista principal (MainPage)
            cambiarVista("/application/MainPage.fxml", (Node) event.getSource());
        });
    }
}
