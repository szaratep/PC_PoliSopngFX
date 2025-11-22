package co.edu.poli.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import javafx.scene.Node;

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

    @FXML
    private Button btnLogin;

    private final viniloDAO viniloDao = new viniloDAO();
    private final discoMP3DAO discomp3Dao = new discoMP3DAO();

    // ======================================================
    //                    INICIALIZAR UI
    // ======================================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColumnas();
        cargarProductos();
        actualizarBotonLogin(); // ← cambia el texto del botón
    }

    // ======================================================
    //           CONFIGURACIÓN DE TABLA DE PRODUCTOS
    // ======================================================
    private void configurarColumnas() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
    }

    private void cargarProductos() {
        ObservableList<Producto> productos = FXCollections.observableArrayList();

        List<vinilo> vinilos = viniloDao.listarVinilos();
        for (vinilo v : vinilos) {
            productos.add(new Producto(v.getNombre(), "Vinilo", v.getPrecio()));
        }

        List<discoMP3> mp3s = discomp3Dao.listarDiscosMp3();
        for (discoMP3 m : mp3s) {
            productos.add(new Producto(m.getNombre(), "MP3", 0.0));
        }

        tablaProductos.setItems(productos);
    }

    // ======================================================
    //                ACTUALIZAR BOTÓN LOGIN
    // ======================================================
    private void actualizarBotonLogin() {

        if (Session.haySesion()) {

            String rol = Session.getRolActual();
            Object obj = Session.getUsuarioActual();

            switch (rol) {
                case "usuario":
                    usuario u = (usuario) obj;
                    btnLogin.setText("Hola, " + u.getNombre());
                    break;

                case "proveedor":
                    proveedor p = (proveedor) obj;
                    btnLogin.setText("Proveedor: " + p.getNombre());
                    break;

                case "admin":
                    administrador a = (administrador) obj;
                    btnLogin.setText("Admin: " + a.getNombre());
                    break;
            }

        } else {
            btnLogin.setText("Iniciar Sesión / Registrar");
        }
    }

    // ======================================================
    //                  ABRIR VENTANAS
    // ======================================================
    private void abrirVentana(String ruta, Node source) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("PoliSong");
            stage.setScene(new Scene(root));
            stage.show();

            // ❌ cerrar ventana actual
            Stage actual = (Stage) source.getScene().getWindow();
            actual.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al abrir ventana: " + ruta);
        }
    }

    private void abrirLogin(Node source) {
        abrirVentana("/co/edu/poli/view/LoginView.fxml", source);
    }

    private void abrirCarrito(Node source) {
        abrirVentana("/co/edu/poli/view/CarritoView.fxml", source);
    }

    // ======================================================
    //                VALIDACIÓN DE SESIÓN
    // ======================================================
    private boolean validarSesion(Node source) {
        if (!Session.haySesion()) {
            System.out.println("No hay sesión activa. Abriendo login...");
            abrirLogin(source);
            return false;
        }
        return true;
    }

    // ======================================================
    //                       BOTONES
    // ======================================================

    @FXML
    private void onLogin(javafx.event.ActionEvent event) {
        abrirLogin((Node) event.getSource());
    }

    @FXML
    private void onSalir() {
        Platform.exit();
    }

    @FXML
    private void onAgregarCarrito(javafx.event.ActionEvent event) {

        if (!validarSesion((Node) event.getSource())) return;

        usuario user = (usuario) Session.getUsuarioActual();
        int idCarrito = user.getId_usuario(); 

        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            System.out.println("No hay producto seleccionado.");
            return;
        }

        carritoManager manager = new carritoManager();

        try {

            if (seleccionado.getTipo().equals("Vinilo")) {
                for (vinilo v : viniloDao.listarVinilos()) {
                    if (v.getNombre().equals(seleccionado.getNombre())) {
                        manager.agregarVinilo(0, idCarrito, v.getId_vinilo(), 1);
                        System.out.println("Vinilo agregado: " + v.getNombre());
                        break;
                    }
                }
            }
            else if (seleccionado.getTipo().equals("MP3")) {
                for (discoMP3 m : discomp3Dao.listarDiscosMp3()) {
                    if (m.getNombre().equals(seleccionado.getNombre())) {
                        manager.agregarMP3(0, idCarrito, m.getId_MP3(), 1);
                        System.out.println("MP3 agregado: " + m.getNombre());
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al agregar al carrito: " + e.getMessage());
        }
    }

    @FXML
    private void onVerCarrito(javafx.event.ActionEvent event) {
        if (!validarSesion((Node) event.getSource())) return;
        abrirCarrito((Node) event.getSource());
    }

    @FXML
    private void onRefrescar() {
        cargarProductos();
    }

    // ======================================================
    //                 CLASE INTERNA PRODUCTO
    // ======================================================
    public static class Producto {
        private final String nombre;
        private final String tipo;
        private final Double precio;

        public Producto(String nombre, String tipo, Double precio) {
            this.nombre = nombre;
            this.tipo = tipo;
            this.precio = precio;
        }

        public String getNombre() { return nombre; }
        public String getTipo() { return tipo; }
        public Double getPrecio() { return precio; }
    }
}
