package co.edu.poli.controller;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import co.edu.poli.datos.discoMP3DAO;
import co.edu.poli.datos.viniloDAO;
import co.edu.poli.model.discoMP3;
import co.edu.poli.model.vinilo;
import co.edu.poli.negocio.carritoManager;

public class CatalogoController {

    @FXML
    private javafx.scene.control.TableView<Producto> tablaProductos;

    @FXML
    private javafx.scene.control.TableColumn<Producto, String> colNombre;

    @FXML
    private javafx.scene.control.TableColumn<Producto, String> colTipo;

    @FXML
    private javafx.scene.control.TableColumn<Producto, Double> colPrecio;

    private final viniloDAO viniloDao = new viniloDAO();
    private final discoMP3DAO mp3Dao = new discoMP3DAO();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNombre()));
        colTipo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTipo()));
        colPrecio.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getPrecio()));

        cargarProductos();
    }

    private void cargarProductos() {
        ObservableList<Producto> productos = FXCollections.observableArrayList();

        // VINILOS
        List<vinilo> vinilos = viniloDao.listarVinilos();
        for (vinilo v : vinilos) {
            productos.add(new Producto(v.getNombre(), "Vinilo", v.getPrecio(), v.getId_vinilo()));
        }

        // MP3
        List<discoMP3> mp3s = mp3Dao.listarDiscosMp3();
        for (discoMP3 m : mp3s) {
            productos.add(new Producto(m.getNombre(), "MP3", 0.0, m.getId_MP3()));
        }

        tablaProductos.setItems(productos);
    }

    @FXML
    private void agregarCarrito() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            System.out.println("Seleccione un producto.");
            return;
        }

        carritoManager manager = new carritoManager();
        int idCarrito = 1; // → usar el carrito del usuario actual si lo implementas

        try {
            if (seleccionado.getTipo().equals("Vinilo")) {
                manager.agregarVinilo(0, idCarrito, seleccionado.getIdProducto(), 1);
            } else {
                manager.agregarMP3(0, idCarrito, seleccionado.getIdProducto(), 1);
            }

            System.out.println("Producto agregado al carrito.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void refrescar() {
        cargarProductos();
    }

    @FXML
    private void volverMenu(javafx.event.ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/co/edu/poli/views/UserMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // -----------------------
    // CLASE PRODUCTO → INTERNA
    // -----------------------
    public static class Producto {
        private final String nombre;
        private final String tipo;
        private final Double precio;
        private final int idProducto;

        public Producto(String nombre, String tipo, Double precio, int idProducto) {
            this.nombre = nombre;
            this.tipo = tipo;
            this.precio = precio;
            this.idProducto = idProducto;
        }

        public String getNombre() { return nombre; }
        public String getTipo() { return tipo; }
        public Double getPrecio() { return precio; }
        public int getIdProducto() { return idProducto; }
    }
}
