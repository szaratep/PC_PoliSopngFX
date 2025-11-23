package co.edu.poli.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class CalificarProductoController {

    @FXML
    private ComboBox<String> comboProductos;

    @FXML
    private Slider sliderCalificacion;

    @FXML
    private void initialize() {
        // Cuando conectes la BD, aquí cargas los productos ya comprados por el usuario
        comboProductos.getItems().addAll("Vinilo 1", "Canción 2", "Álbum 3");
    }

    @FXML
    private void enviarCalificacion() {
        String producto = comboProductos.getValue();
        int calificacion = (int) sliderCalificacion.getValue();

        if (producto == null) {
            System.out.println("Seleccione un producto");
            return;
        }

        System.out.println("Calificación enviada: " + producto + " → " + calificacion + " estrellas");
    }

    @FXML
    private void volverMenu() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/co/edu/poli/views/UserMenu.fxml"));
            Stage stage = (Stage) comboProductos.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
