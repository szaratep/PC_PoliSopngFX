package co.edu.poli.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RegistrarProductoController {

    @FXML
    private TextField txtNombre;

    @FXML
    private ComboBox<String> comboTipo;

    @FXML
    private TextField txtGenero;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;

    @FXML
    public void initialize() {
        comboTipo.getItems().addAll("Vinilo", "MP3");
    }

    @FXML
    private void registrarProducto() {
        if (txtNombre.getText().isEmpty() || comboTipo.getValue() == null ||
            txtGenero.getText().isEmpty() || txtPrecio.getText().isEmpty() ||
            txtStock.getText().isEmpty()) {

            mostrarAlerta("Por favor llena todos los campos.");
            return;
        }

        System.out.println("Registrando producto nuevo...");
        // Lógica para registrar en BD

        mostrarAlerta("Producto registrado exitosamente.");
    }

    @FXML
    private void volver() {
        System.out.println("Volver al menú de inventario...");
    }

    private void mostrarAlerta(String msg) {
        Alert a = new Alert(AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
