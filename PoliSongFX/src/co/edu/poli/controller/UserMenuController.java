package co.edu.poli.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class UserMenuController {

    // --------------------------
    // MÉTODO BASE PARA CAMBIAR DE VISTA
    // --------------------------
    private void cambiarVista(String rutaFXML, Node nodo) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));
            Stage stage = (Stage) nodo.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } 
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error cargando vista: " + rutaFXML);
        }
    }

    // --------------------------
    // BOTONES PRINCIPALES
    // --------------------------

    @FXML
    private void irAMiPerfil(javafx.event.ActionEvent event) {
        cambiarVista("/co/edu/poli/views/MiPerfilView.fxml", (Node) event.getSource());
    }

    @FXML
    private void irACarrito(javafx.event.ActionEvent event) {
        cambiarVista("/co/edu/poli/views/CarritoView.fxml", (Node) event.getSource());
    }

    @FXML
    private void irACatalogo(javafx.event.ActionEvent event) {
        cambiarVista("/co/edu/poli/views/Catalogo.fxml", (Node) event.getSource());
    }

    @FXML
    private void irAPlaylist(javafx.event.ActionEvent event) {
        cambiarVista("/co/edu/poli/views/PlaylistView.fxml", (Node) event.getSource());
    }

    @FXML
    private void irAHistorialCompras(javafx.event.ActionEvent event) {
        cambiarVista("/co/edu/poli/views/HistorialComprasView.fxml", (Node) event.getSource());
    }

    @FXML
    private void irACalificarProductos(javafx.event.ActionEvent event) {
        cambiarVista("/co/edu/poli/views/CalificarProductosView.fxml", (Node) event.getSource());
    }

    // --------------------------
    // CERRAR SESIÓN
    // --------------------------

    @FXML
    private void cerrarSesion(javafx.event.ActionEvent event) {
        cambiarVista("/co/edu/poli/views/MainPage.fxml", (Node) event.getSource());
    }
}

