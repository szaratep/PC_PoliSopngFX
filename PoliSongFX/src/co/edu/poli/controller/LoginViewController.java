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

public class LoginViewController {

    @FXML
    private Label mensajeSesion;

    @FXML
    private TextField passwordSesion;

    @FXML
    private TextField userSesion;

    // Manager de autenticación
    private authManager auth = new authManager();


    // -------------------------
    //  BOTÓN REGISTRARSE
    // -------------------------
    @FXML
    void RegistroSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/RegisterView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Registro");
            stage.setScene(new Scene(root));
            stage.show();

            // cerrar ventana actual
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al abrir RegisterView.fxml: " + e.getMessage());
        }
    }


    // -------------------------
    //      INICIAR SESIÓN
    // -------------------------
    @FXML
    void inicioSesion(ActionEvent event) {
        String correo = userSesion.getText();
        String pass = passwordSesion.getText();

        // Validación de campos vacíos
        if (correo.isEmpty() || pass.isEmpty()) {
            mensajeSesion.setText("Por favor completa todos los campos.");
            return;
        }else if (auth.loginUsuario(correo, pass)) {
            mensajeSesion.setText("Sesión iniciada como USUARIO");
            abrirVentana("/application/UsuarioPage.fxml", event);
            return;
        }else if(auth.loginProveedor(correo, pass)) {
            mensajeSesion.setText("Sesión iniciada como PROVEEDOR");
            abrirVentana("/application/ProveedorPage.fxml", event);
            return;
        }else if(auth.loginAdmin(correo, pass)) {
            mensajeSesion.setText("Sesión iniciada como ADMINISTRADOR");
            abrirVentana("/application/AdminPage.fxml", event);
            return;
        }else {
        	mensajeSesion.setText("Credenciales incorrectas.");        	
        }
    }


    // -------------------------
    //  BOTÓN VOLVER AL MENÚ
    // -------------------------
    @FXML
    void volverInicio(ActionEvent event) {
        abrirVentana("/application/mainPage.fxml", event);
    }


    // -------------------------
    // MÉTODO UTIL PARA ABRIR VENTANAS
    // -------------------------
    private void abrirVentana(String ruta, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("PoliSong");
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar ventana actual
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al abrir ventana: " + ruta + " -> " + e.getMessage());
        }
    }
}
