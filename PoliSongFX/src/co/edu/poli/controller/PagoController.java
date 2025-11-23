package co.edu.poli.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.poli.negocio.Session;
import co.edu.poli.negocio.carritoManager;
import co.edu.poli.negocio.pedidoManager;
import co.edu.poli.model.carritoItem;
import co.edu.poli.model.usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PagoController implements Initializable {

    @FXML private Label lblPedidoId;
    @FXML private Label lblTotal;

    private carritoManager carritoM = new carritoManager();
    private pedidoManager pedidoM = new pedidoManager();

    private int idUsuario;
    private double total;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    private void cargarDatos() {

        if (!Session.haySesion()) {
            lblPedidoId.setText("Sin sesión");
            return;
        }

        usuario user = (usuario) Session.getUsuarioActual();
        idUsuario = user.getId_usuario();

        List<carritoItem> itemsCarrito = carritoM.listarItems(idUsuario);

        lblPedidoId.setText("Pedido #" + idUsuario);

        total = calcularTotal(itemsCarrito);

        lblTotal.setText("Total: $" + total);
    }

    private double calcularTotal(List<carritoItem> items) {

        double suma = 0;

        for (carritoItem item : items) {
            suma += obtenerPrecio(item) * item.getCantidad();
        }

        return suma;
    }

    private double obtenerPrecio(carritoItem item) {

        if (item.getTipo_producto().equalsIgnoreCase("Vinilo")) {
            return 25000; // simulado o puedes usar la lógica del carrito
        }

        if (item.getTipo_producto().equalsIgnoreCase("Cancion")) {
            return 5000;
        }

        if (item.getTipo_producto().equalsIgnoreCase("MP3")) {
            return 7000;
        }

        return 0;
    }

    @FXML
    private void onConfirmarPago(ActionEvent event) {

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar pago");
        confirm.setHeaderText("¿Está seguro de realizar el pago?");
        confirm.setContentText("Total: $" + total);

        if (confirm.showAndWait().get() == ButtonType.OK) {

            registrarPedido();

            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setTitle("Pago realizado");
            ok.setHeaderText("¡Pago confirmado!");
            ok.setContentText("Gracias por tu compra ❤️");
            ok.showAndWait();

            abrirMenuPrincipal();
        }
    }

    private void registrarPedido() {

        pedidoM.crearPedido(idUsuario, "Pagado");

        List<carritoItem> itemsCarrito = carritoM.listarItems(idUsuario);

        int idDetalle = 1;

        for (carritoItem item : itemsCarrito) {

            int idProducto = 0;

            if (item.getId_cancion() != null)
                idProducto = item.getId_cancion();
            if (item.getId_vinilo() != null)
                idProducto = item.getId_vinilo();
            if (item.getId_mp3() != null)
                idProducto = item.getId_mp3();

            pedidoM.agregarDetalle(
                    idDetalle++,
                    idUsuario,
                    item.getTipo_producto(),
                    idProducto,
                    item.getCantidad(),
                    obtenerPrecio(item)
            );
        }

        carritoM.eliminarCarrito(idUsuario);
        carritoM.crearCarrito(idUsuario, idUsuario);
    }

    @FXML
    private void onCancelar(ActionEvent event) {
        abrirMenuPrincipal();
    }

    private void abrirMenuPrincipal() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/MainPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            lblTotal.getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
