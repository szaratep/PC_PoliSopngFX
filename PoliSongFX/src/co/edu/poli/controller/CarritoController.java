package co.edu.poli.controller;

import co.edu.poli.model.*;
import co.edu.poli.negocio.*;
import co.edu.poli.datos.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.util.List;

public class CarritoController {

    @FXML private TableView<carritoItem> tablaCarrito;
    @FXML private TableColumn<carritoItem, String> colTipo;
    @FXML private TableColumn<carritoItem, String> colNombre;
    @FXML private TableColumn<carritoItem, Integer> colCantidad;
    @FXML private TableColumn<carritoItem, Double> colPrecio;

    @FXML private Label lblCarritoId;
    @FXML private Label lblTotal;

    private carritoManager manager = new carritoManager();
    private viniloDAO viniloDao = new viniloDAO();
    private discoMP3DAO mp3Dao = new discoMP3DAO();
    private cancionDAO cancionDao = new cancionDAO();

    private ObservableList<carritoItem> itemsObservable;
    private int idCarrito;

    @FXML
    public void initialize() {

        usuario user = (usuario) Session.getUsuarioActual();
        idCarrito = user.getId_usuario();

        lblCarritoId.setText("Carrito #" + idCarrito);

        configurarColumnas();
        cargarCarrito();
    }

    private void configurarColumnas() {

        colTipo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipo_producto()));

        colNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(obtenerNombre(cellData.getValue())));

        colCantidad.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getCantidad()).asObject());

        colPrecio.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(obtenerPrecio(cellData.getValue())).asObject());
    }

    private void cargarCarrito() {

        List<carritoItem> items = manager.listarItems(idCarrito);
        itemsObservable = FXCollections.observableArrayList(items);

        tablaCarrito.setItems(itemsObservable);

        calcularTotal();
    }

    private String obtenerNombre(carritoItem item) {

        String tipo = item.getTipo_producto();

        if (tipo == null) return "Producto desconocido";

        // VINILO
        if (tipo.equalsIgnoreCase("Vinilo")) {
            if (item.getId_vinilo() != null) {
                vinilo v = viniloDao.readVinilo(item.getId_vinilo());
                return v != null ? v.getNombre() : "Vinilo desconocido";
            }
            return "Vinilo desconocido";
        }

        // MP3
        if (tipo.equalsIgnoreCase("MP3")) {
            if (item.getId_cancion() != null) {
                cancion c = cancionDao.readCancion(item.getId_cancion());
                return c != null ? c.getNombre() + " (MP3)" : "MP3 desconocido";
            }
            return "MP3 desconocido";
        }

        // CANCIÓN
        if (tipo.equalsIgnoreCase("Cancion")) {
            if (item.getId_cancion() != null) {
                cancion c = cancionDao.readCancion(item.getId_cancion());
                return c != null ? c.getNombre() : "Canción desconocida";
            }
            return "Canción desconocida";
        }

        return "Producto desconocido";
    }

    private double obtenerPrecio(carritoItem item) {

        String tipo = item.getTipo_producto();
        if (tipo == null) return 0;

        // VINILO
        if (tipo.equalsIgnoreCase("Vinilo")) {
            vinilo v = viniloDao.readVinilo(item.getId_vinilo());
            return (v != null && v.getPrecio() > 0) ? v.getPrecio() : 0;
        }

        // MP3
        if (tipo.equalsIgnoreCase("MP3")) {

            if (item.getId_cancion() == null) {
                return 2000;
            }

            cancion c = cancionDao.readCancion(item.getId_cancion());
            return (c != null ? c.getPrecio() : 0) + 2000;
        }

        // ✅ CANCIÓN (ESTO FALTABA)
        if (tipo.equalsIgnoreCase("Canción") || tipo.equalsIgnoreCase("Cancion")) {
            cancion c = cancionDao.readCancion(item.getId_cancion());
            return (c != null ? c.getPrecio() : 0);
        }

        return 0;
    }

    private void calcularTotal() {
        double total = 0;

        for (carritoItem item : itemsObservable) {
            total += obtenerPrecio(item) * item.getCantidad();
        }

        lblTotal.setText("Total: $" + total);
    }

    // ---------------------------------------------------
    // BOTONES
    // ---------------------------------------------------

    @FXML
    void onActualizarCantidad(ActionEvent event) {

        carritoItem seleccionado = tablaCarrito.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            alert("Seleccione un producto");
            return;
        }

        TextInputDialog dialog = new TextInputDialog("" + seleccionado.getCantidad());
        dialog.setTitle("Actualizar cantidad");
        dialog.setHeaderText("Modificar cantidad");
        dialog.setContentText("Nueva cantidad:");

        dialog.showAndWait().ifPresent(valor -> {
            try {
                int nuevaCantidad = Integer.parseInt(valor);
                manager.actualizarCantidad(idCarrito, seleccionado.getId_item(), nuevaCantidad);
                cargarCarrito();
            } catch (Exception e) {
                alert("Cantidad inválida");
            }
        });
    }

    @FXML
    void onAgregarProducto(ActionEvent event) {
        abrirVentana("/application/MainPage.fxml", event);
    }

    @FXML
    void onEliminarItem(ActionEvent event) {
        carritoItem seleccionado = tablaCarrito.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            alert("Seleccione un producto");
            return;
        }

        manager.eliminarItem(seleccionado.getId_item());
        cargarCarrito();
    }

    @FXML
    void onVaciarCarrito(ActionEvent event) {
        manager.eliminarCarrito(idCarrito);
        cargarCarrito();
    }

    @FXML
    void onVolverMenu(ActionEvent event) {
        abrirVentana("/application/MainPage.fxml", event);
    }

    @FXML
    void onPagar(ActionEvent event) {
        abrirVentana("/co/edu/poli/view/PagoView.fxml", event);
    }

    // ---------------------------------------------------

    private void abrirVentana(String ruta, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }
}
