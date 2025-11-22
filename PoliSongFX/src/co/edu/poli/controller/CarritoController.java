package co.edu.poli.controller;

import co.edu.poli.model.*;
import co.edu.poli.negocio.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class CarritoController {

    @FXML private TableView<ItemCarrito> tablaCarrito;
    @FXML private TableColumn<ItemCarrito, Integer> colIdItem;
    @FXML private TableColumn<ItemCarrito, String> colTipoProducto;
    @FXML private TableColumn<ItemCarrito, Integer> colIdCancion;
    @FXML private TableColumn<ItemCarrito, Integer> colIdVinilo;
    @FXML private TableColumn<ItemCarrito, Integer> colIdMp3;
    @FXML private TableColumn<ItemCarrito, Integer> colCantidad;

    private carritoManager manager;
    private ObservableList<ItemCarrito> itemsObservable;

    @FXML
    public void initialize() {
        manager = new carritoManager();
        configurarColumnas();
        cargarCarrito();
    }

    private void configurarColumnas() {
        colIdItem.setCellValueFactory(new PropertyValueFactory<>("idItem"));
        colTipoProducto.setCellValueFactory(new PropertyValueFactory<>("tipoProducto"));
        colIdCancion.setCellValueFactory(new PropertyValueFactory<>("idCancion"));
        colIdVinilo.setCellValueFactory(new PropertyValueFactory<>("idVinilo"));
        colIdMp3.setCellValueFactory(new PropertyValueFactory<>("idMp3"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
    }

    private void cargarCarrito() {
        if (!Session.haySesion()) return;

        usuario user = (usuario) Session.getUsuarioActual();
        List<carritoItem> items = manager.listarItems(user.getId_usuario());

        itemsObservable = FXCollections.observableArrayList();

        for (carritoItem ci : items) {
            itemsObservable.add(new ItemCarrito(
                    ci.getId_item(),
                    ci.getTipo_producto(),
                    ci.getId_cancion(),
                    ci.getId_vinilo(),
                    ci.getId_mp3(),
                    ci.getCantidad()
            ));
        }

        tablaCarrito.setItems(itemsObservable);
    }

    @FXML
    void onActualizarCantidad(ActionEvent event) {
        // Aquí se puede implementar la actualización de cantidad usando manager.actualizarItem(...)
    }

    @FXML
    void onAgregarProducto(ActionEvent event) {
        // Se puede redirigir a la MainPage para agregar más productos
    }

    @FXML
    void onEliminarItem(ActionEvent event) {
        // Eliminar el item seleccionado usando manager.eliminarItem(...)
    }

    @FXML
    void onVaciarCarrito(ActionEvent event) {
        // Vaciar el carrito completo usando manager.eliminarCarrito(...)
    }

    // ======================================================
    // Clase interna para mostrar los items en la tabla
    // ======================================================
    public static class ItemCarrito {
        private final Integer idItem;
        private final String tipoProducto;
        private final Integer idCancion;
        private final Integer idVinilo;
        private final Integer idMp3;
        private final Integer cantidad;

        public ItemCarrito(Integer idItem, String tipoProducto, Integer idCancion, Integer idVinilo, Integer idMp3, Integer cantidad) {
            this.idItem = idItem;
            this.tipoProducto = tipoProducto;
            this.idCancion = idCancion;
            this.idVinilo = idVinilo;
            this.idMp3 = idMp3;
            this.cantidad = cantidad;
        }

        public Integer getIdItem() { return idItem; }
        public String getTipoProducto() { return tipoProducto; }
        public Integer getIdCancion() { return idCancion; }
        public Integer getIdVinilo() { return idVinilo; }
        public Integer getIdMp3() { return idMp3; }
        public Integer getCantidad() { return cantidad; }
    }
}
