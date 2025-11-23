package co.edu.poli.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class HistorialComprasController {

    @FXML
    private TableView<?> tablaHistorial;

    @FXML
    private void initialize() {
        // Aquí puedes cargar datos reales cuando tengas la base de datos conectada
        System.out.println("HistorialComprasController -> Cargando historial...");
    }

    @FXML
    private void volverMenu() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/co/edu/poli/views/UserMenu.fxml"));
            Stage stage = (Stage) tablaHistorial.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
