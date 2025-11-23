package co.edu.poli.controller;

import co.edu.poli.datos.pedidoDetalleDAO;
import co.edu.poli.model.pedidoDetalle;
import co.edu.poli.model.usuario;
import co.edu.poli.negocio.Session;
import co.edu.poli.negocio.pedidoManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class CalificarProductoController {

    @FXML
    private TableView<pedidoDetalle> tablaProductos;

    @FXML
    private TableColumn<pedidoDetalle, Integer> colIdDetalle;

    @FXML
    private TableColumn<pedidoDetalle, String> colNombreProducto;

    @FXML
    private TableColumn<pedidoDetalle, Integer> colCantidad;

    @FXML
    private TableColumn<pedidoDetalle, Double> colPrecio;

    private pedidoManager manager = new pedidoManager();
    private pedidoDetalleDAO detalleDAO = new pedidoDetalleDAO();

    // Variable temporal para guardar la última calificación
    private int ultimaCalificacion = 0;

    @FXML
    private void initialize() {
        colIdDetalle.setCellValueFactory(new PropertyValueFactory<>("id_detalle"));
        colNombreProducto.setCellValueFactory(cd -> {
            String nombre = getNombreProductoPorId(cd.getValue().getTipo_Producto(), cd.getValue().getId_producto());
            return new javafx.beans.property.SimpleStringProperty(nombre);
        });
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio_unitario"));

        cargarProductos();
    }

    private void cargarProductos() {
        if (!Session.haySesion()) return;

        usuario u = (usuario) Session.getUsuarioActual();
        ObservableList<pedidoDetalle> data = FXCollections.observableArrayList();

        for (var p : manager.getPedidosUsuario(u.getId_usuario())) {
            data.addAll(detalleDAO.readDetallesPedido(p.getId_pedido()));
        }

        tablaProductos.setItems(data);
    }

    private String getNombreProductoPorId(String tipo, int idProducto) {
        // TODO: Implementar según tu DAO de productos
        // Ejemplo:
        // if(tipo.equals("cancion")) return cancionDAO.readCancion(idProducto).getNombre();
        // if(tipo.equals("vinilo")) return viniloDAO.readVinilo(idProducto).getNombre();
        // if(tipo.equals("discomp3")) return discoDAO.readDiscoMP3(idProducto).getNombre();
        return tipo + " #" + idProducto; // fallback simple mientras implementas
    }

    @FXML
    private void calificarProducto() {
        pedidoDetalle seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING, "Debes seleccionar un producto primero.", ButtonType.OK);
            alerta.showAndWait();
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Calificar Producto");
        dialog.setHeaderText("Producto: " + getNombreProductoPorId(seleccionado.getTipo_Producto(), seleccionado.getId_producto()));
        dialog.setContentText("Ingresa tu calificación de 1 a 5:");

        dialog.showAndWait().ifPresent(input -> {
            try {
                int calificacion = Integer.parseInt(input);
                if (calificacion < 1 || calificacion > 5) throw new NumberFormatException();
                ultimaCalificacion = calificacion;

                Alert info = new Alert(Alert.AlertType.INFORMATION,
                        "Has calificado " + getNombreProductoPorId(seleccionado.getTipo_Producto(), seleccionado.getId_producto()) +
                                " con " + calificacion + " estrellas.", ButtonType.OK);
                info.showAndWait();
            } catch (NumberFormatException e) {
                Alert error = new Alert(Alert.AlertType.ERROR, "La calificación debe ser un número entre 1 y 5.", ButtonType.OK);
                error.showAndWait();
            }
        });
    }

    @FXML
    private void volverMenu() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/co/edu/poli/view/UserMenuView.fxml"));
            Stage stage = (Stage) tablaProductos.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
