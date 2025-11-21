package co.edu.poli.controller;

import co.edu.poli.model.usuario;
import co.edu.poli.negocio.usuarioManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class MiPerfilController {

    @FXML private Label lblIdUsuario;
    @FXML private Label lblNombre;
    @FXML private Label lblCorreo;
    @FXML private Label lblContrasena;

    private usuario usuarioLogueado;

    public void setUsuario(usuario u) {
        this.usuarioLogueado = u;

        lblIdUsuario.setText(String.valueOf(u.getId_usuario()));
        lblNombre.setText(u.getNombre());
        lblCorreo.setText(u.getCorreo());
        lblContrasena.setText(u.getContrasena());
    }

    @FXML
    private void abrirEditarPerfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/EditarPerfilView.fxml"));
            Scene scene = new Scene(loader.load());

            EditarPerfilController controller = loader.getController();
            controller.setUsuario(usuarioLogueado);

            Stage stage = new Stage();
            stage.setTitle("Editar Perfil");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void volverMenu(javafx.event.ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
}
