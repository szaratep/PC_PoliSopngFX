package co.edu.poli.controller;

import co.edu.poli.datos.viniloDAO;
import co.edu.poli.model.vinilo;
import co.edu.poli.negocio.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.List;

public class GestionInventarioController {

    @FXML private TableView<vinilo> tablaProductos;
    @FXML private TableColumn<vinilo, String> colNombre;
    @FXML private TableColumn<vinilo, String> colTipo;
    @FXML private TableColumn<vinilo, String> colArtista;
    @FXML private TableColumn<vinilo, Integer> colAnio;
    @FXML private TableColumn<vinilo, Double> colPrecio;
    @FXML private TableColumn<vinilo, Integer> colStock;

    private viniloDAO viniloDAO = new viniloDAO();
    private ObservableList<vinilo> listaVinilos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar columnas
        colNombre.setCellValueFactory(cell -> 
            new javafx.beans.property.SimpleStringProperty(cell.getValue().getNombre()));
        colTipo.setCellValueFactory(cell -> 
            new javafx.beans.property.SimpleStringProperty("Vinilo"));
        colArtista.setCellValueFactory(cell -> 
            new javafx.beans.property.SimpleStringProperty(cell.getValue().getArtista()));
        colAnio.setCellValueFactory(cell -> 
            new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getAnio()).asObject());
        colPrecio.setCellValueFactory(cell -> 
            new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getPrecio()).asObject());
        colStock.setCellValueFactory(cell -> 
            new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getInventario()).asObject());

        cargarVinilos();
    }

    private void cargarVinilos() {
        listaVinilos.clear();
        List<vinilo> todosVinilos = viniloDAO.listarVinilos();

        // Filtrar por proveedor activo si vinilo tuviera id_proveedor
        // Por simplicidad, añadimos todos
        listaVinilos.addAll(todosVinilos);

        tablaProductos.setItems(listaVinilos);
    }

    @FXML
    private void abrirRegistrarProducto(ActionEvent event) {
        try {
            // Cerrar la ventana actual
            Stage stageActual = (Stage) tablaProductos.getScene().getWindow();
            stageActual.close();

            // Abrir la ventana de Registrar Producto
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/RegistrarProductoView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Registrar Producto");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("No se pudo abrir la ventana de Registrar Producto.");
        }
    }

    @FXML
    private void abrirEditarProducto(ActionEvent event) {
        vinilo seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Seleccione un vinilo para editar.");
            return;
        }

        // Crear un diálogo personalizado
        Dialog<vinilo> dialog = new Dialog<>();
        dialog.setTitle("Editar Vinilo");
        dialog.setHeaderText("Modifique los datos del vinilo");

        // Botones OK y Cancelar
        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        // Crear un formulario con GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtNombre = new TextField(seleccionado.getNombre());
        TextField txtArtista = new TextField(seleccionado.getArtista());
        TextField txtAnio = new TextField(String.valueOf(seleccionado.getAnio()));
        TextField txtPrecio = new TextField(String.valueOf(seleccionado.getPrecio()));
        TextField txtStock = new TextField(String.valueOf(seleccionado.getInventario()));

        grid.addRow(0, new Label("Nombre:"), txtNombre);
        grid.addRow(1, new Label("Artista:"), txtArtista);
        grid.addRow(2, new Label("Año:"), txtAnio);
        grid.addRow(3, new Label("Precio:"), txtPrecio);
        grid.addRow(4, new Label("Stock:"), txtStock);

        dialog.getDialogPane().setContent(grid);

        // Convertir el resultado del diálogo a un vinilo actualizado
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                try {
                    seleccionado.setNombre(txtNombre.getText());
                    seleccionado.setArtista(txtArtista.getText());
                    seleccionado.setAnio(Integer.parseInt(txtAnio.getText()));
                    seleccionado.setPrecio(Double.parseDouble(txtPrecio.getText()));
                    seleccionado.setInventario(Integer.parseInt(txtStock.getText()));
                    return seleccionado;
                } catch (NumberFormatException e) {
                    mostrarAlerta("Año, Precio y Stock deben ser numéricos.");
                    return null;
                }
            }
            return null;
        });

        // Mostrar diálogo y actualizar tabla si se guardó
        dialog.showAndWait().ifPresent(v -> {
            viniloDAO.updateVinilo(v);
            cargarVinilos();
            mostrarAlerta("Vinilo actualizado correctamente.");
        });
    }


    @FXML
    private void eliminarProducto(ActionEvent event) {
        vinilo seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Seleccione un vinilo para eliminar.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText(null);
        confirm.setContentText("¿Desea eliminar el vinilo " + seleccionado.getNombre() + "?");

        confirm.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                viniloDAO.deleteVinilo(seleccionado.getId_vinilo());
                cargarVinilos();
            }
        });
    }

    @FXML
    private void actualizarLista(ActionEvent event) {
        cargarVinilos();
    }

    @FXML
    private void volverMenuPrincipal(ActionEvent event) {
        try {
            // Cerrar la ventana actual
            Stage stage = (Stage) tablaProductos.getScene().getWindow();
            stage.close();

            // Abrir menú del proveedor
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/ProveedorMenuView.fxml"));
            Stage menuStage = new Stage();
            menuStage.setScene(new Scene(loader.load()));
            menuStage.setTitle("Menú del Proveedor");
            menuStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
