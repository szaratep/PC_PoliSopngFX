package co.edu.poli.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class EditarProductoController {

    @FXML
    private TextField txtIdProducto;

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
    private void cargarProducto() {
        if (txtIdProducto.getText().isEmpty()) {
            mostrar("Ingresa el ID del producto.");
            return;
        }

        System.out.println("Cargando datos del producto ID: " + txtIdProducto.getText());

        // Aquí iría la lógica de BD
        // Por ahora un ejemplo simulado:
        txtNombre.setText("Ejemplo Producto");
        comboTipo.setValue("Vinilo");
        txtGenero.setText("Rock");
        txtPrecio.setText("30000");
        txtStock.setText("5");
    }

    @FXML
    private void guardarCambios() {
        if (txtNombre.getText().isEmpty() || comboTipo.getValue() == null ||
            txtGenero.getText().isEmpty() || txtPrecio.getText().isEmpty() ||
            txtStock.getText().isEmpty()) {

            mostrar("Completa todos los campos.");
            return;
        }

        System.out.println("Guardando cambios en el producto...");
        mostrar("Cambios guardados exitosamente.");
    }

    @FXML
    private void volver() {
        System.out.println("Volver al menú de inventario...");
    }

    private void mostrar(String msg) {
        Alert a = new Alert(AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
