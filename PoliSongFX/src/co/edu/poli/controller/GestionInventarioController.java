package co.edu.poli.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GestionInventarioController {

    @FXML
    private TableView<?> tablaProductos;

    @FXML
    private TableColumn<?, ?> colNombre;

    @FXML
    private TableColumn<?, ?> colTipo;

    @FXML
    private TableColumn<?, ?> colGenero;

    @FXML
    private TableColumn<?, ?> colPrecio;

    @FXML
    private TableColumn<?, ?> colStock;

    @FXML
    public void initialize() {
        System.out.println("Inicializando gestión de inventario...");
        // Aquí más adelante cargamos datos reales desde la BD
    }

    @FXML
    private void abrirRegistrarProducto() {
        System.out.println("Abrir vista: RegistrarProducto.fxml");
        // cambiarVista("/co/edu/poli/view/RegistrarProducto.fxml");
    }

    @FXML
    private void abrirEditarProducto() {
        System.out.println("Abrir vista: EditarProducto.fxml");

        if (tablaProductos.getSelectionModel().getSelectedItem() == null) {
            mostrarAlerta("Selecciona un producto primero.");
            return;
        }

        // cambiarVista("/co/edu/poli/view/EditarProducto.fxml");
    }

    @FXML
    private void eliminarProducto() {
        if (tablaProductos.getSelectionModel().getSelectedItem() == null) {
            mostrarAlerta("Selecciona un producto para eliminar.");
            return;
        }

        System.out.println("Eliminando producto seleccionado...");
        // eliminar en BD
    }

    @FXML
    private void actualizarLista() {
        System.out.println("Actualizando tabla del inventario...");
        // Recargar desde BD
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
