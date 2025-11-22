package co.edu.poli.controller;

import co.edu.poli.datos.carritoDAO;
import co.edu.poli.datos.carritoItemDAO;
import co.edu.poli.model.carrito;
import co.edu.poli.model.carritoItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CarritoController {

    @FXML private TextField txtCarritoId;
    @FXML private TextField txtUsuarioId;

    @FXML private TableView<carritoItem> tablaItems;
    @FXML private TableColumn<carritoItem, Integer> colId;
    @FXML private TableColumn<carritoItem, String> colTipo;
    @FXML private TableColumn<carritoItem, Integer> colCantidad;

    private carritoDAO carritoDAO = new carritoDAO();
    private carritoItemDAO itemDAO = new carritoItemDAO();

    private ObservableList<carritoItem> itemsObservable = FXCollections.observableArrayList();
    private carrito carritoActual;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId_item()).asObject());
        colTipo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTipo_producto()));
        colCantidad.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getCantidad()).asObject());

        tablaItems.setItems(itemsObservable);
    }

    @FXML
    private void onCargarCarrito() {
        int id = Integer.parseInt(txtCarritoId.getText());

        carritoActual = carritoDAO.readCarrito(id);

        if (carritoActual == null) {
            alert("No existe un carrito con ese ID");
            return;
        }

        txtUsuarioId.setText(String.valueOf(carritoActual.getId_usuario()));

        // Cargar items
        itemsObservable.setAll(carritoActual.getItems());
    }

    @FXML
    private void onAgregarItem() {
        // Aqui debes abrir ventana modal para pedir datos.
        // Ejemplo rápido:
        carritoItem item = new carritoItem(
                0, // id_item
                carritoActual.getId_carrito(),
                "vinilo",
                null,
                1,
                null,
                1
        );

        itemDAO.createItem(item);
        refrescar();
    }

    @FXML
    private void onEditarItem() {
        carritoItem seleccionado = tablaItems.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            alert("Selecciona un item para editar");
            return;
        }

        seleccionado.setCantidad(seleccionado.getCantidad() + 1);
        itemDAO.updateItem(seleccionado);

        refrescar();
    }

    @FXML
    private void onEliminarItem() {
        carritoItem seleccionado = tablaItems.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            alert("Selecciona un item para eliminar");
            return;
        }

        itemDAO.deleteItem(seleccionado.getId_item());
        refrescar();
    }

    private void refrescar() {
        carritoActual = carritoDAO.readCarrito(carritoActual.getId_carrito());
        itemsObservable.setAll(carritoActual.getItems());
    }

    private void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
