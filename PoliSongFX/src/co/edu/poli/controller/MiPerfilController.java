package co.edu.poli.controller;

import co.edu.poli.model.usuario;
import co.edu.poli.model.proveedor;
import co.edu.poli.model.administrador;
import co.edu.poli.negocio.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.net.URL;
import java.util.ResourceBundle;

public class MiPerfilController implements Initializable {

    @FXML private Label lblIdUsuario;
    @FXML private Label lblNombre;
    @FXML private Label lblCorreo;
    @FXML private Label lblContrasena;

    private Object usuarioLogueado; // Puede ser usuario, proveedor o admin

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Session.haySesion()) {
            usuarioLogueado = Session.getUsuarioActual();
            mostrarInformacion(usuarioLogueado);
        } else {
            lblNombre.setText("Sin sesión");
            lblCorreo.setText("-");
            lblContrasena.setText("-");
            lblIdUsuario.setText("-");
        }
    }

    private void mostrarInformacion(Object obj) {
        if (obj instanceof usuario) {
            usuario u = (usuario) obj;
            lblIdUsuario.setText(String.valueOf(u.getId_usuario()));
            lblNombre.setText(u.getNombre());
            lblCorreo.setText(u.getCorreo());
            lblContrasena.setText(u.getContrasena());

        } else if (obj instanceof proveedor) {
            proveedor p = (proveedor) obj;
            lblIdUsuario.setText(String.valueOf(p.getId_proveedor()));
            lblNombre.setText(p.getNombre());
            lblCorreo.setText(p.getCorreo());
            lblContrasena.setText(p.getContrasena());

        } else if (obj instanceof administrador) {
            administrador a = (administrador) obj;
            lblIdUsuario.setText(String.valueOf(a.getId_admin()));
            lblNombre.setText(a.getNombre());
            lblCorreo.setText(a.getCorreo());
            lblContrasena.setText(a.getContrasena());
        }
    }

    @FXML
    private void abrirEditarPerfil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/EditarPerfilView.fxml"));
            Parent root = loader.load();

            // Pasar el usuario logueado al controlador de edición
            EditarPerfilController controller = loader.getController();
            controller.setUsuario(usuarioLogueado);

            Stage nueva = new Stage();
            nueva.setTitle("Editar Perfil");
            nueva.setScene(new Scene(root));
            nueva.show();

            // Cerrar ventana actual
            Stage actual = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            actual.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void volverMenu(ActionEvent event) {
        try {
            String ruta = "";
            if (usuarioLogueado instanceof usuario) {
                ruta = "/co/edu/poli/view/UserMenuView.fxml";
            } else if (usuarioLogueado instanceof proveedor) {
                ruta = "/co/edu/poli/view/ProveedorMenuView.fxml";
            } else if (usuarioLogueado instanceof administrador) {
                ruta = "/co/edu/poli/view/AdminMenuView.fxml";
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("PoliSong");
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar ventana actual
            Stage actual = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            actual.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
