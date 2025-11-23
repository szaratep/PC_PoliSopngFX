package co.edu.poli.controller;

import co.edu.poli.negocio.Session;
import co.edu.poli.negocio.pedidoManager;
import co.edu.poli.datos.viniloDAO;
import co.edu.poli.datos.cancionDAO;
import co.edu.poli.model.*;
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

    @FXML
    private TableView<pedidoDetalle> tablaHistorial;

    private pedidoManager manager = new pedidoManager();
    private viniloDAO vdao = new viniloDAO();
    private cancionDAO cdao = new cancionDAO();

    @FXML
    private void initialize() {

        TableColumn<pedidoDetalle, Integer> colIdPedido = (TableColumn<pedidoDetalle, Integer>) tablaHistorial.getColumns().get(0);
        colIdPedido.setCellValueFactory(new PropertyValueFactory<>("id_pedido"));

        TableColumn<pedidoDetalle, String> colTipo = (TableColumn<pedidoDetalle, String>) tablaHistorial.getColumns().get(1);
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo_Producto"));

        TableColumn<pedidoDetalle, Integer> colCantidad = (TableColumn<pedidoDetalle, Integer>) tablaHistorial.getColumns().get(2);
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<pedidoDetalle, Double> colPrecio = (TableColumn<pedidoDetalle, Double>) tablaHistorial.getColumns().get(3);
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio_unitario"));

        cargarHistorial();
    }


    private void cargarHistorial() {

        if (!Session.haySesion())
            return;

        usuario u = (usuario) Session.getUsuarioActual();

        ObservableList<pedidoDetalle> data = FXCollections.observableArrayList();

        // ✅ LEER pedidos desde manager
        for (pedido p : manager.getPedidosUsuario(u.getId_usuario())) {

            // ✅ LEER detalles desde manager
            data.addAll(manager.getDetallesPedido(p.getId_pedido()));
        }

        tablaHistorial.setItems(data);
    }

    @FXML
    private void volverMenu() {
        try {
            // Cargar la vista del menú
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/UserMenuView.fxml"));
            Parent root = loader.load();

            // Crear un nuevo Stage para el menú
            Stage menuStage = new Stage();
            menuStage.setTitle("Menú de Usuario");
            menuStage.setScene(new Scene(root));
            menuStage.show();

            // Cerrar la ventana actual
            Stage currentStage = (Stage) tablaHistorial.getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
