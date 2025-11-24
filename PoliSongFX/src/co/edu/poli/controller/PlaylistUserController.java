package co.edu.poli.controller;

import co.edu.poli.model.cancion;
import co.edu.poli.model.playList;
import co.edu.poli.model.usuario;
import co.edu.poli.negocio.Session;
import co.edu.poli.negocio.playListManager;
import co.edu.poli.datos.playListDAO;
import co.edu.poli.datos.playlist_cancionDAO;
import co.edu.poli.datos.cancionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class PlaylistUserController {

    @FXML private TextField txtNombre;
    @FXML private CheckBox chkPublica;

    @FXML private TableView<cancion> tablaTodasCanciones;
    @FXML private TableView<cancion> tablaCanciones;

    @FXML private TableColumn<cancion, Integer> colIdCancionSistema;
    @FXML private TableColumn<cancion, String> colNombreCancionSistema;
    @FXML private TableColumn<cancion, Double> colDuracionSistema;

    @FXML private TableColumn<cancion, Integer> colIdCancion;
    @FXML private TableColumn<cancion, String> colNombreCancion;
    @FXML private TableColumn<cancion, Double> colDuracion;

    private final ObservableList<cancion> cancionesSistema = FXCollections.observableArrayList();
    private final ObservableList<cancion> cancionesPlaylist = FXCollections.observableArrayList();

    private final playListDAO playlistDAO = new playListDAO();
    private final playlist_cancionDAO playlistCancionDAO = new playlist_cancionDAO();
    private final cancionDAO cancionDAO = new cancionDAO();

    private int idPlaylistActual = -1;

    @FXML
    public void initialize() {

        colIdCancionSistema.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getId_cancion()).asObject());

        colNombreCancionSistema.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getNombre()));

        colDuracionSistema.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getDuracion()).asObject());

        tablaTodasCanciones.setItems(cancionesSistema);

        colIdCancion.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getId_cancion()).asObject());

        colNombreCancion.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getNombre()));

        colDuracion.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getDuracion()).asObject());

        tablaCanciones.setItems(cancionesPlaylist);

        cargarCancionesSistema();
    }

    private void cargarCancionesSistema() {
        cancionesSistema.clear();
        cancionesSistema.addAll(cancionDAO.readAllCanciones());
    }

    @FXML
    private void onCrearPlaylist() {
        String nombre = txtNombre.getText();
        boolean publica = chkPublica.isSelected();

        if (nombre.isEmpty()) {
            mostrarAlerta("Error", "Ingrese un nombre para la playlist.");
            return;
        }

        usuario u = (usuario) Session.getUsuarioActual();
        idPlaylistActual = playlistDAO.crearPlayListReturnId(nombre, publica, u.getId_usuario());

        if (idPlaylistActual != -1) {
            mostrarAlerta("Playlist creada", "Playlist creada correctamente.");
        } else {
            mostrarAlerta("Error", "No se pudo crear la playlist.");
        }
    }

    @FXML
    private void onAgregarCancion() {
        if (idPlaylistActual == -1) {
            mostrarAlerta("Error", "Debe crear la playlist primero.");
            return;
        }

        cancion selected = tablaTodasCanciones.getSelectionModel().getSelectedItem();
        if (selected == null) {
            mostrarAlerta("Seleccione una canción", "Debe seleccionar una canción.");
            return;
        }

        playlistCancionDAO.addCancionToPlaylist(idPlaylistActual, selected.getId_cancion());
        cancionesPlaylist.add(selected);
    }

    @FXML
    private void onEliminarCancion() {
        if (idPlaylistActual == -1) {
            mostrarAlerta("Error", "Debe crear la playlist primero.");
            return;
        }

        cancion selected = tablaCanciones.getSelectionModel().getSelectedItem();
        if (selected == null) {
            mostrarAlerta("Seleccione una canción", "Debe seleccionar una canción.");
            return;
        }

        playlistCancionDAO.removeCancionFromPlaylist(idPlaylistActual, selected.getId_cancion());
        cancionesPlaylist.remove(selected);
    }

    @FXML
    private void onVerPlaylists() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/UserPlaylistsView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la vista de playlists.");
        }
    }

    @FXML
    private void onVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/UserMenuView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo volver al menú.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }
}
