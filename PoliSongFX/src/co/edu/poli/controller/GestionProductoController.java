package co.edu.poli.controller;

import java.io.IOException;

import co.edu.poli.model.vinilo;
import co.edu.poli.negocio.viniloManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class GestionProductoController {

    @FXML
    private TableView<vinilo> tablaProductos;

    @FXML
    private TableColumn<vinilo, Integer> colId;

    @FXML
    private TableColumn<vinilo, String> colNombre;

    @FXML
    private TableColumn<vinilo, String> colArtista;

    @FXML
    private TableColumn<vinilo, Double> colPrecio;

    @FXML
    private TableColumn<vinilo, String> colGenero;

    private viniloManager manager = new viniloManager();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idVinilo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));

        cargarProductos();
    }

    private void cargarProductos() {
        ObservableList<vinilo> lista =
                FXCollections.observableArrayList(manager.listarVinilos());
        tablaProductos.setItems(lista);
    }

    @FXML
    private void eliminarProducto() {
        vinilo seleccionado = tablaProductos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Debe seleccionar un producto para eliminar.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar este producto permanentemente?",
                ButtonType.YES, ButtonType.NO);

        confirm.showAndWait();

        if (confirm.getResult() == ButtonType.YES) {
            manager.eliminarVinilo(seleccionado.getIdVinilo());
            cargarProductos();
        }
    }

    @FXML
    private void volverMenu() {
        cambiarVista("/co/edu/poli/view/AdminMenu.fxml");
    }

    private void cambiarVista(String ruta) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(ruta));
            Stage stage = (Stage) tablaProductos.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
