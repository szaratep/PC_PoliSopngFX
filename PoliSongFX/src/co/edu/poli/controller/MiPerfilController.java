package co.edu.poli.controller;

import co.edu.poli.model.usuario;
import co.edu.poli.negocio.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.net.URL;
import java.util.ResourceBundle;

public class MiPerfilController implements Initializable {

    @FXML private Label lblIdUsuario;
    @FXML private Label lblNombre;
    @FXML private Label lblCorreo;
    @FXML private Label lblContrasena;

    private usuario usuarioLogueado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // ✅ Obtener usuario desde Session
        if (Session.haySesion()) {

            Object obj = Session.getUsuarioActual();

            if (obj instanceof usuario) {
                usuarioLogueado = (usuario) obj;

                lblIdUsuario.setText(String.valueOf(usuarioLogueado.getId_usuario()));
                lblNombre.setText(usuarioLogueado.getNombre());
                lblCorreo.setText(usuarioLogueado.getCorreo());
                lblContrasena.setText(usuarioLogueado.getContrasena());
            }

        } else {
            lblNombre.setText("Sin sesión");
        }
    }

    @FXML
    private void abrirEditarPerfil(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/EditarPerfilView.fxml"));
            Parent root = loader.load();

            // ✅ obtener controlador y pasar el usuario
            EditarPerfilController controller = loader.getController();
            controller.setUsuario(usuarioLogueado);

            Stage nueva = new Stage();
            nueva.setTitle("Editar Perfil");
            nueva.setScene(new Scene(root));
            nueva.show();

            // ✅ cerrar ventana actual
            Stage actual = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            actual.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void volverMenu(javafx.event.ActionEvent event) {
        abrirVentana("/co/edu/poli/view/UserMenuView.fxml", event);
    }
    
    private void abrirVentana(String ruta, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            Stage nueva = new Stage();
            nueva.setTitle("PoliSong");
            nueva.setScene(new Scene(root));
            nueva.show();

            // ✅ Cerrar solo la ventana actual
            Stage actual = (Stage) ((javafx.scene.Node) event.getSource())
                    .getScene()
                    .getWindow();
            actual.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al abrir ventana: " + ruta);
        }
    }


}
