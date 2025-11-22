package co.edu.poli.controller;

import co.edu.poli.model.*;
import co.edu.poli.negocio.proveedorManager;
import co.edu.poli.negocio.usuarioManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterViewController {

    @FXML
    private Button AceptarRegistro;

    @FXML
    private Button CancelarRegistro;

    @FXML
    private TextField ContraseñaRegistro;

    @FXML
    private TextField CorreoRegistro;

    @FXML
    private Label Label;

    @FXML
    private TextField NombreRegistro;

    @FXML
    private ChoiceBox<String> Selecciona;

    @FXML
    private Button VolverRegristro;

    private final usuarioManager um = new usuarioManager();
    private final proveedorManager pm = new proveedorManager();

    @FXML
    void PresionarAceptar(ActionEvent event) {
        String nombre = NombreRegistro.getText().trim();
        String correoStr = CorreoRegistro.getText().trim();
        String contrasena = ContraseñaRegistro.getText().trim();
        String tipoUsuario = Selecciona.getValue(); // "Comprador" o "Proveedor"

        if (nombre.isEmpty() || correoStr.isEmpty() || contrasena.isEmpty() || tipoUsuario == null) {
            Label.setText("Todos los campos son obligatorios");
            return;
        }

        try {
            // Crear correo si no existe
            correo correoExistente = um.getCorreoDao().readCorreo(correoStr);
            if (correoExistente == null) {
                um.getCorreoDao().createCorreo(new correo(correoStr));
            }

            if (tipoUsuario.equalsIgnoreCase("Comprador")) {
                um.registrarUsuario(0, nombre, correoStr, contrasena);
                Label.setText("Comprador registrado correctamente");
            } else if (tipoUsuario.equalsIgnoreCase("Proveedor")) {
                pm.registrarProveedor(0, nombre, correoStr, contrasena, 0);
                Label.setText("Proveedor registrado correctamente");
            } else {
                Label.setText("Tipo de usuario no válido");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Label.setText("Error al registrar usuario: " + e.getMessage());
        }
    }

    @FXML
    void PresionarCancelar(ActionEvent event) {
        cerrarYVolverMenu();
    }

    @FXML
    void PresionarVolverAlMenu(ActionEvent event) {
        cerrarYVolverMenu();
    }

    /**
     * Método que cierra la ventana actual y abre MainPage.fxml
     */
    private void cerrarYVolverMenu() {
        try {
            // Cerrar la ventana actual
            Stage stageActual = (Stage) CancelarRegistro.getScene().getWindow();
            stageActual.close();

            // Abrir MainPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/MainPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Menu Principal");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            Label.setText("Error al abrir MainPage.fxml: " + e.getMessage());
        }
    }

}
