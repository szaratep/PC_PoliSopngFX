package co.edu.poli.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProveedorMenuController {

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
        // cambiarVista("/co/edu/poli/view/PedidosPendientes.fxml");
    }

    @FXML
    private void abrirPerfil() {
        System.out.println("Abrir perfil proveedor...");
        // cambiarVista("/co/edu/poli/view/MiPerfil.fxml");
    }

    @FXML
    private void abrirRegistrarProducto() {
        System.out.println("Abrir registro de producto nuevo...");
        // cambiarVista("/co/edu/poli/view/RegistrarProducto.fxml");
    }

    @FXML
    private void abrirReportes() {
        System.out.println("Abrir reportes de ventas...");
        // cambiarVista("/co/edu/poli/view/Reportes.fxml");
    }

    @FXML
    private void cerrarSesion() {
        System.out.println("Cerrar sesión proveedor...");
        // cambiarVista("/co/edu/poli/view/Login.fxml");
    }
}
