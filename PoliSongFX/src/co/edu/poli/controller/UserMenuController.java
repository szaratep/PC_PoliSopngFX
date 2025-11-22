package co.edu.poli.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UserMenuController {

    @FXML
    private Label lblUsuario;

    private String usuario;

    public void setUsuario(String usuario) {
        this.usuario = usuario;
        lblUsuario.setText("Usuario: " + usuario);
    }

    private void abrirVista(String nombreFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/" + nombreFxml));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onPlaylists() {
        abrirVista("PlayListView.fxml");
    }

    @FXML
    private void onCarrito() {
        abrirVista("CarritoView.fxml");
    }

    @FXML
    private void onBuscar() {
        abrirVista("BuscarCancionesView.fxml"); // Si no existe la creo
    }

    @FXML
    private void onCatalogo() {
        abrirVista("CatalogoView.fxml"); // Si no existe la creo
    }

    @FXML
    private void onPerfil() {
        abrirVista("PerfilView.fxml"); // Si no existe la creo
    }

    @FXML
    private void onCerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/LoginView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) lblUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
