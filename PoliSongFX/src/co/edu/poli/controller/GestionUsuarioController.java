package co.edu.poli.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import co.edu.poli.negocio.*;
import co.edu.poli.model.usuario;
import co.edu.poli.model.proveedor;

import java.io.IOException;
import java.util.List;

public class GestionUsuarioController {

    @FXML private TableView<UnifiedUsuario> tablaUsuarios;
    @FXML private TableColumn<UnifiedUsuario, Integer> colId;
    @FXML private TableColumn<UnifiedUsuario, String> colNombre;
    @FXML private TableColumn<UnifiedUsuario, String> colCorreo;
    @FXML private TableColumn<UnifiedUsuario, String> colRol;

    private usuarioManager usuarioMgr = new usuarioManager();
    private proveedorManager proveedorMgr = new proveedorManager();
    private ObservableList<UnifiedUsuario> listaUsuarios = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        colNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
        colCorreo.setCellValueFactory(data -> data.getValue().correoProperty());
        colRol.setCellValueFactory(data -> data.getValue().rolProperty());

        cargarUsuarios();
    }

    private void cargarUsuarios() {
        listaUsuarios.clear();

        List<usuario> usuarios = usuarioMgr.obtenerUsuarios();
        for (usuario u : usuarios) {
            listaUsuarios.add(new UnifiedUsuario(
                    u.getId_usuario(),
                    u.getNombre(),
                    u.getCorreo(),
                    "Usuario"
            ));
        }

        List<proveedor> proveedores = proveedorMgr.obtenerProveedores();
        for (proveedor p : proveedores) {
            listaUsuarios.add(new UnifiedUsuario(
                    p.getId_proveedor(),
                    p.getNombre(),
                    p.getCorreo(),
                    "Proveedor"
            ));
        }

        tablaUsuarios.setItems(listaUsuarios);
    }

    @FXML
    private void eliminarUsuario() {
        UnifiedUsuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING, "Seleccione un usuario.", ButtonType.OK);
            alerta.showAndWait();
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Confirmar Eliminación");
        dialog.setHeaderText("Para eliminar este usuario escriba: ELIMINAR");
        dialog.setContentText("Ingrese confirmación:");

        dialog.showAndWait().ifPresent(text -> {
            if (!text.equalsIgnoreCase("ELIMINAR")) {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Confirmación incorrecta.", ButtonType.OK);
                alerta.showAndWait();
                return;
            }

            if (seleccionado.getRol().equals("Usuario")) {
                usuarioMgr.eliminarUsuario(seleccionado.getId());
            } else {
                proveedorMgr.eliminarProveedor(seleccionado.getId());
            }

            cargarUsuarios();

            Alert alertaOk = new Alert(Alert.AlertType.INFORMATION, "Usuario eliminado correctamente.", ButtonType.OK);
            alertaOk.showAndWait();
        });
    }

    @FXML
    private void volverMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/AdminMenuView.fxml"));
            Stage stage = (Stage) tablaUsuarios.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Clase wrapper
    public static class UnifiedUsuario {
        private javafx.beans.property.IntegerProperty id;
        private javafx.beans.property.StringProperty nombre;
        private javafx.beans.property.StringProperty correo;
        private javafx.beans.property.StringProperty rol;

        public UnifiedUsuario(int id, String nombre, String correo, String rol) {
            this.id = new javafx.beans.property.SimpleIntegerProperty(id);
            this.nombre = new javafx.beans.property.SimpleStringProperty(nombre);
            this.correo = new javafx.beans.property.SimpleStringProperty(correo);
            this.rol = new javafx.beans.property.SimpleStringProperty(rol);
        }

        public int getId() { return id.get(); }
        public javafx.beans.property.IntegerProperty idProperty() { return id; }

        public String getNombre() { return nombre.get(); }
        public javafx.beans.property.StringProperty nombreProperty() { return nombre; }

        public String getCorreo() { return correo.get(); }
        public javafx.beans.property.StringProperty correoProperty() { return correo; }

        public String getRol() { return rol.get(); }
        public javafx.beans.property.StringProperty rolProperty() { return rol; }
    }
}
