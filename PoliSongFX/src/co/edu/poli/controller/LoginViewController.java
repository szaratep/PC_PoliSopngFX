package co.edu.poli.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import co.edu.poli.negocio.authManager;
import co.edu.poli.negocio.busquedaManager;
import co.edu.poli.negocio.Session;
import co.edu.poli.model.usuario;
import co.edu.poli.model.proveedor;
import co.edu.poli.model.administrador;

public class LoginViewController {

    @FXML
    private Label mensajeSesion;

    @FXML
    private TextField passwordSesion;

    @FXML
    private TextField userSesion;

    private authManager auth = new authManager();
    private busquedaManager buscar = new busquedaManager();

    // -------------------------------------
    //     INICIAR SESIÓN
    // -------------------------------------
    @FXML
    void inicioSesion(ActionEvent event) {
        String correo = userSesion.getText();
        String pass = passwordSesion.getText();

        if (correo.isEmpty() || pass.isEmpty()) {
            mensajeSesion.setText("Por favor completa todos los campos.");
            return;
        }

        // ----------- LOGIN COMO USUARIO -----------
        if (auth.loginUsuario(correo, pass)) {

            usuario u = buscar.buscarUsuarioPorCorreo(correo); // USANDO BUSQUEDA MANAGER
            Session.setSesion(u, "usuario");

            mensajeSesion.setText("Sesión iniciada como USUARIO");
            abrirVentana("/application/UsuarioPage.fxml", event);
            return;
        }

        // ----------- LOGIN COMO PROVEEDOR -----------
        if (auth.loginProveedor(correo, pass)) {

            proveedor p = buscar.buscarProveedorPorCorreo(correo);
            Session.setSesion(p, "proveedor");

            mensajeSesion.setText("Sesión iniciada como PROVEEDOR");
            abrirVentana("/application/ProveedorPage.fxml", event);
            return;
        }

        // ----------- LOGIN COMO ADMINISTRADOR -----------
        if (auth.loginAdmin(correo, pass)) {

            administrador a = buscar.buscarAdminPorCorreo(correo);
            Session.setSesion(a, "admin");

            mensajeSesion.setText("Sesión iniciada como ADMINISTRADOR");
            abrirVentana("/application/AdminPage.fxml", event);
            return;
        }

        mensajeSesion.setText("Credenciales incorrectas.");
    }


    // -------------------------------------
    //     VOLVER AL MENÚ PRINCIPAL
    // -------------------------------------
    @FXML
    void volverInicio(ActionEvent event) {
        abrirVentana("/application/mainPage.fxml", event);
    }

    // -------------------------------------
    //     ABRIR VENTANAS
    // -------------------------------------
    private void abrirVentana(String ruta, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("PoliSong");
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al abrir ventana: " + ruta + " -> " + e.getMessage());
        }
    }
    
    @FXML
    private void RegistroSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/RegistroView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Registro de Usuario");
            stage.setScene(new Scene(root));
            stage.show();

            // Opcional: cerrar ventana anterior
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al abrir RegistroView.fxml: " + e.getMessage());
        }
    }
}
