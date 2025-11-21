package co.edu.poli.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import co.edu.poli.datos.*;
import co.edu.poli.model.*;
import co.edu.poli.negocio.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, String> colTipo;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    private final viniloDAO viniloDao = new viniloDAO();
    private final discoMP3DAO discomp3Dao = new discoMP3DAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColumnas();
        cargarProductos();
    }

    private void configurarColumnas() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
    }

    private void cargarProductos() {
        ObservableList<Producto> productos = FXCollections.observableArrayList();

        // Convertir vinilos a Producto
        List<vinilo> vinilos = viniloDao.listarVinilos();
        for (vinilo v : vinilos) {
            productos.add(new Producto(v.getNombre(), "Vinilo", v.getPrecio()));
        }

        // Convertir discos MP3 a Producto (precio = 0)
        List<discoMP3> mp3s = discomp3Dao.listarDiscosMp3();
        for (discoMP3 m : mp3s) {
            productos.add(new Producto(m.getNombre(), "MP3", 0.0));
        }

        tablaProductos.setItems(productos);
    }
    // ---------------------------
    // BOTONES → los dejas vacíos
    // ---------------------------

    @FXML
    private void onLogin() {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/LoginView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al abrir LoginView.fxml: " + e.getMessage());
        }
    }

    @FXML
    private void onSalir() {
        Platform.exit(); // Termina la aplicación JavaFX
    }

    @FXML
    private void onAgregarCarrito() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            System.out.println("No se ha seleccionado ningún producto.");
            return;
        }

        int idCarrito = 1; // Aquí deberías usar el carrito del usuario actual
        carritoManager manager = new carritoManager();

        try {
            if (seleccionado.getTipo().equals("Vinilo")) {
                List<vinilo> vinilos = viniloDao.listarVinilos();
                for (vinilo v : vinilos) {
                    if (v.getNombre().equals(seleccionado.getNombre())) {
                        manager.agregarVinilo(0, idCarrito, v.getId_vinilo(), 1);
                        System.out.println("Vinilo agregado al carrito: " + v.getNombre());
                        break;
                    }
                }
            } else if (seleccionado.getTipo().equals("MP3")) {
                List<discoMP3> mp3s = discomp3Dao.listarDiscosMp3();
                for (discoMP3 m : mp3s) {
                    if (m.getNombre().equals(seleccionado.getNombre())) {
                        manager.agregarMP3(0, idCarrito, m.getId_MP3(), 1);
                        System.out.println("MP3 agregado al carrito: " + m.getNombre());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al agregar producto al carrito: " + e.getMessage());
        }
    }


    @FXML
    private void onRefrescar() {
        cargarProductos(); // vuelve a cargar la tabla
    }

    // ---------------------------
    // CLASE INTERNA Producto
    // ---------------------------
    public static class Producto {
        private final String nombre;
        private final String tipo;
        private final Double precio;

        public Producto(String nombre, String tipo, Double precio) {
            this.nombre = nombre;
            this.tipo = tipo;
            this.precio = precio;
        }

        public String getNombre() {
            return nombre;
        }

        public String getTipo() {
            return tipo;
        }

        public Double getPrecio() {
            return precio;
        }
    }
}
