package co.edu.poli.controller;

import co.edu.poli.negocio.Session;
import co.edu.poli.negocio.pedidoManager;
import co.edu.poli.model.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HistorialComprasController {

	@FXML private TableColumn<pedidoDetalle, Integer> colIdPedido;
	@FXML private TableColumn<pedidoDetalle, String> colTipo;
	@FXML private TableColumn<pedidoDetalle, Integer> colCantidad;
	@FXML private TableColumn<pedidoDetalle, Double> colPrecio;
	@FXML private TableColumn<?, ?> colFecha; // si existe

    @FXML
    private TableView<pedidoDetalle> tablaHistorial;

    private pedidoManager manager = new pedidoManager();

    @FXML
    private void initialize() {

        colIdPedido.setCellValueFactory(new PropertyValueFactory<>("id_pedido"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo_Producto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        colPrecio.setCellValueFactory(cellData -> {
            pedidoDetalle det = cellData.getValue();
            double total = det.getCantidad() * det.getPrecio_unitario();
            return new SimpleDoubleProperty(total).asObject();
        });

        if (colFecha != null)
            colFecha.setVisible(false);

        Platform.runLater(this::cargarHistorial);
    }


    private void cargarHistorial() {

        System.out.println("DEBUG: cargarHistorial()");
        System.out.println("Session activa: " + Session.haySesion());

        if (!Session.haySesion())
            return;

        usuario u = (usuario) Session.getUsuarioActual();
        System.out.println("DEBUG usuario id=" + u.getId_usuario());

        ObservableList<pedidoDetalle> data = FXCollections.observableArrayList();

        for (pedido p : manager.getPedidosUsuario(u.getId_usuario())) {
            System.out.println("DEBUG pedido id=" + p.getId_pedido());
            data.addAll(manager.getDetallesPedido(p.getId_pedido()));
        }

        tablaHistorial.setItems(data);
    }

    @FXML
    private void volverMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/UserMenuView.fxml"));
            Parent root = loader.load();

            Stage menuStage = new Stage();
            menuStage.setTitle("Menú de Usuario");
            menuStage.setScene(new Scene(root));
            menuStage.show();

            Stage currentStage = (Stage) tablaHistorial.getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
