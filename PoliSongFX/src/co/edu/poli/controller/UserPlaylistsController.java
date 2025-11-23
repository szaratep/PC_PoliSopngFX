package co.edu.poli.controller;

import co.edu.poli.model.playList;
import co.edu.poli.model.usuario;
import co.edu.poli.negocio.Session;
import co.edu.poli.negocio.playListManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class UserPlaylistsController {

    @FXML private TableView<playList> tablaPlaylists;
    @FXML private TableColumn<playList, Integer> colIdPlaylist;
    @FXML private TableColumn<playList, String> colNombrePlaylist;
    @FXML private TableColumn<playList, Boolean> colPublica;

    private final playListManager playListManager = new playListManager();
    private final ObservableList<playList> playlistsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar columnas de la tabla
        colIdPlaylist.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId_playlist()).asObject()
        );
        colNombrePlaylist.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre())
        );
        colPublica.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleBooleanProperty(cellData.getValue().isPublica())
        );

        tablaPlaylists.setItems(playlistsList);

        cargarPlaylistsUsuario();
    }

    private void cargarPlaylistsUsuario() {
        playlistsList.clear();
        Object objUsuario = Session.getUsuarioActual();
        if (objUsuario != null && objUsuario instanceof usuario) {
            int idUsuario = ((usuario) objUsuario).getId_usuario();
            playlistsList.addAll(playListManager.playListDao.readPlaylistsByUsuario(idUsuario));
        }
    }

    @FXML
    private void onActualizarPlaylist() {
        playList selected = tablaPlaylists.getSelectionModel().getSelectedItem();
        if (selected != null) {
            TextInputDialog nombreDialog = new TextInputDialog(selected.getNombre());
            nombreDialog.setTitle("Actualizar Playlist");
            nombreDialog.setHeaderText("Actualizar nombre de la playlist");
            nombreDialog.setContentText("Nombre:");
            Optional<String> resultado = nombreDialog.showAndWait();

            resultado.ifPresent(nuevoNombre -> {
                ChoiceDialog<String> publicaDialog = new ChoiceDialog<>(
                    selected.isPublica() ? "Sí" : "No", "Sí", "No"
                );
                publicaDialog.setTitle("Actualizar Playlist");
                publicaDialog.setHeaderText("Actualizar visibilidad de la playlist");
                publicaDialog.setContentText("Pública:");
                Optional<String> resp = publicaDialog.showAndWait();
                resp.ifPresent(valor -> {
                    boolean publica = valor.equals("Sí");
                    int idUsuario = ((usuario) Session.getUsuarioActual()).getId_usuario();
                    playListManager.actualizarPlayList(selected.getId_playlist(), idUsuario, nuevoNombre, publica);
                    cargarPlaylistsUsuario();
                });
            });
        } else {
            mostrarAlerta("Seleccione una playlist", "Debe seleccionar una playlist para actualizar.");
        }
    }

    @FXML
    private void onEliminarPlaylist() {
        playList selected = tablaPlaylists.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Eliminar Playlist");
            confirm.setHeaderText("¿Está seguro que desea eliminar la playlist: " + selected.getNombre() + "?");
            Optional<ButtonType> resp = confirm.showAndWait();
            if (resp.isPresent() && resp.get() == ButtonType.OK) {
                playListManager.eliminarPlayList(selected.getId_playlist());
                cargarPlaylistsUsuario();
            }
        } else {
            mostrarAlerta("Seleccione una playlist", "Debe seleccionar una playlist para eliminar.");
        }
    }

    @FXML
    private void onVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/PlayListVUserView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) tablaPlaylists.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista de menú de usuario.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
