package co.edu.poli.controller;

import co.edu.poli.model.usuario;
import co.edu.poli.model.proveedor;
import co.edu.poli.model.administrador;
import co.edu.poli.negocio.usuarioManager;
import co.edu.poli.negocio.proveedorManager;
import co.edu.poli.negocio.Session;
import co.edu.poli.negocio.adminManager;
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

    private Object usuarioLogueado;

    private usuarioManager usuarioMgr = new usuarioManager();
    private proveedorManager proveedorMgr = new proveedorManager();
    private adminManager adminMgr = new adminManager();

    public void setUsuario(Object obj) {
        this.usuarioLogueado = obj;

        if (obj instanceof usuario u) {
            txtNombre.setText(u.getNombre());
            txtCorreo.setText(u.getCorreo());
            txtContrasena.setText(u.getContrasena());

        } else if (obj instanceof proveedor p) {
            txtNombre.setText(p.getNombre());
            txtCorreo.setText(p.getCorreo());
            txtContrasena.setText(p.getContrasena());

        } else if (obj instanceof administrador a) {
            txtNombre.setText(a.getNombre());
            txtCorreo.setText(a.getCorreo());
            txtContrasena.setText(a.getContrasena());

            // Bloquear edición si quieres que el administrador no pueda modificar
            txtNombre.setEditable(false);
            txtCorreo.setEditable(false);
            txtContrasena.setEditable(false);
        }
    }

    @FXML
    private void guardarCambios() {

        if (usuarioLogueado instanceof usuario u) {
            usuarioMgr.actualizarUsuario(u.getId_usuario(), txtNombre.getText(), txtCorreo.getText(), txtContrasena.getText());

            // Actualizar objeto en memoria
            u.setNombre(txtNombre.getText());
            u.setCorreo(txtCorreo.getText());
            u.setContrasena(txtContrasena.getText());

            // Actualizar sesión
            Session.setSesion(u, "usuario");

        } else if (usuarioLogueado instanceof proveedor p) {
            proveedorMgr.actualizarProveedor(p.getId_proveedor(), txtNombre.getText(), txtCorreo.getText(), txtContrasena.getText(), 0);

            // Actualizar objeto en memoria
            p.setNombre(txtNombre.getText());
            p.setCorreo(txtCorreo.getText());
            p.setContrasena(txtContrasena.getText());

            // Actualizar sesión
            Session.setSesion(p, "proveedor");

        } else if (usuarioLogueado instanceof administrador a) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("No permitido");
            alerta.setHeaderText(null);
            alerta.setContentText("El administrador no puede editar su perfil ");
            alerta.showAndWait();
            return;
        }

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
