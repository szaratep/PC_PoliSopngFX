package co.edu.poli.controller;

import co.edu.poli.model.usuario;
import co.edu.poli.negocio.usuarioManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarPerfilController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;

    private usuario usuarioActual;
    private usuarioManager manager = new usuarioManager();

    public void setUsuario(usuario u) {
        this.usuarioActual = u;

        txtNombre.setText(u.getNombre());
        txtCorreo.setText(u.getCorreo());
        txtContrasena.setText(u.getContrasena());
    }

    @FXML
    private void guardarCambios() {
        manager.actualizarUsuario(
                usuarioActual.getId_usuario(),
                txtNombre.getText(),
                txtCorreo.getText(),
                txtContrasena.getText()
        );

        ((Stage) txtNombre.getScene().getWindow()).close();
    }

    @FXML
    private void cancelar() {
        ((Stage) txtNombre.getScene().getWindow()).close();
    }
}
