package co.edu.poli.controller;

import java.io.IOException;

import co.edu.poli.negocio.usuarioManager;
import co.edu.poli.model.usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class GestionUsuarioController {

    @FXML
    private TableView<usuario> tablaUsuarios;

    @FXML
    private TableColumn<usuario, Integer> colId;

    @FXML
    private TableColumn<usuario, String> colNombre;

    @FXML
    private TableColumn<usuario, String> colCorreo;

    @FXML
    private TableColumn<usuario, String> colRol;

    @FXML
    private TableColumn<usuario, String> colDireccion;

    private usuarioManager usuarioManager = new usuarioManager();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        cargarUsuarios();
    }

    private void cargarUsuarios() {
        ObservableList<usuario> lista =
                FXCollections.observableArrayList(usuarioManager.listar());
        tablaUsuarios.setItems(lista);
    }

    @FXML
    private void abrirRegistrarUsuario() {
        cambiarVista("/co/edu/poli/view/RegistrarUsuario.fxml");
    }

    @FXML
    private void eliminarUsuario() {
        usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Debe seleccionar un usuario para eliminar.");
            return;
        }

        Alert confirm = new Alert(
                Alert.AlertType.CONFIRMATION,
                "¿Eliminar usuario permanentemente?",
                ButtonType.YES, ButtonType.NO
        );

        confirm.showAndWait();

        if (confirm.getResult() == ButtonType.YES) {
            usuarioManager.eliminar(seleccionado.getIdUsuario());
            cargarUsuarios();
        }
    }

    @FXML
    private void volverMenu() {
        cambiarVista("/co/edu/poli/view/AdminMenu.fxml");
    }

    private void cambiarVista(String ruta) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(ruta));
            Stage stage = (Stage) tablaUsuarios.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

