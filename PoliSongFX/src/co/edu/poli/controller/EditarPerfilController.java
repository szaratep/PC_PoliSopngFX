package co.edu.poli.controller;

import co.edu.poli.model.usuario;
import co.edu.poli.negocio.usuarioManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;

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

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Perfil actualizado");
        alerta.setHeaderText(null);
        alerta.setContentText("Perfil modificado correctamente ✅");
        alerta.showAndWait();

        volverAMiPerfil();
    }

    @FXML
    private void cancelar() {
        volverAMiPerfil();
    }

    private void volverAMiPerfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/MiPerfilView.fxml"));
            Parent root = loader.load();

            Stage nueva = new Stage();
            nueva.setTitle("Mi Perfil");
            nueva.setScene(new Scene(root));
            nueva.show();

            ((Stage) txtNombre.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
