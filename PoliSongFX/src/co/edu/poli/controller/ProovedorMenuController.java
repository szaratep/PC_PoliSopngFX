package co.edu.poli.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProovedorMenuController {

    @FXML
    private Label labelProveedorNombre;

    public void inicializarProveedor(String nombre) {
        labelProveedorNombre.setText("Proveedor: " + nombre);
    }

    @FXML
    private void abrirGestionInventario() {
        System.out.println("Abrir gestión de inventario...");
        // cambiarVista("/co/edu/poli/view/GestionInventario.fxml");
    }

    @FXML
    private void abrirPedidosPendientes() {
        System.out.println("Abrir lista de pedidos pendientes...");
    }

    @FXML
    private void abrirPerfil() {
        System.out.println("Abrir perfil proveedor...");
    }

    @FXML
    private void abrirRegistrarProducto() {
        System.out.println("Abrir registro de producto nuevo...");
    }

    @FXML
    private void abrirReportes() {
        System.out.println("Abrir reportes...");
    }

    @FXML
    private void cerrarSesion() {
        System.out.println("Cerrar sesión proveedor...");
    }
}
