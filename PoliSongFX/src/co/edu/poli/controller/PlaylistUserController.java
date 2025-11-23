package co.edu.poli.controller;

import co.edu.poli.datos.cancionDAO;
import co.edu.poli.model.cancion;
import co.edu.poli.negocio.playListManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.scene.Parent;

public class PlaylistUserController {

    @FXML private TextField txtNombre;
    @FXML private CheckBox chkPublica;

    @FXML private TableView<cancion> tablaTodasCanciones;
    @FXML private TableColumn<cancion, Integer> colIdCancionSistema;
    @FXML private TableColumn<cancion, String> colNombreCancionSistema;
    @FXML private TableColumn<cancion, Double> colDuracionSistema;

    @FXML private TableView<cancion> tablaCanciones;
    @FXML private TableColumn<cancion, Integer> colIdCancion;
    @FXML private TableColumn<cancion, String> colNombreCancion;
    @FXML private TableColumn<cancion, Double> colDuracion;

    private final cancionDAO cancionDao = new cancionDAO();
    public final playListManager playListManager = new playListManager();
    private int currentPlayListId = -1; // se asigna al crear o cargar una playlist

    private ObservableList<cancion> todasCancionesList = FXCollections.observableArrayList();
    private ObservableList<cancion> cancionesPlaylistList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar columnas tabla todas las canciones
        colIdCancionSistema.setCellValueFactory(new PropertyValueFactory<>("id_cancion"));
        colNombreCancionSistema.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDuracionSistema.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        // Configurar columnas tabla playlist
        colIdCancion.setCellValueFactory(new PropertyValueFactory<>("id_cancion"));
        colNombreCancion.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        // Inicializar listas en tablas
        tablaTodasCanciones.setItems(todasCancionesList);
        tablaCanciones.setItems(cancionesPlaylistList);

        // Cargar todas las canciones
        cargarTodasCanciones();
    }

    private void cargarTodasCanciones() {
        todasCancionesList.clear();
        try {
            // Aquí deberías implementar un método real en cancionDAO para leer todas las canciones
            // Temporal: cargamos solo una canción de ejemplo
            cancion c = cancionDao.readCancion(1); 
            if (c != null) {
                todasCancionesList.add(c);
            }
        } catch (Exception e) {
            System.out.println("Error cargando todas las canciones: " + e.getMessage());
        }
    }

    private void cargarCancionesPlaylist() {
        cancionesPlaylistList.clear();
        if (currentPlayListId != -1) {
            for (Integer idCancion : playListManager.playlistCancionDao.getCancionesByPlaylist(currentPlayListId)) {
                cancion c = cancionDao.readCancion(idCancion);
                if (c != null) cancionesPlaylistList.add(c);
            }
        }
    }

    @FXML
    private void onCrearPlaylist() {
        String nombre = txtNombre.getText();
        boolean esPublica = chkPublica.isSelected();
        int idUsuario = 1; // Cambia según el usuario logueado

        currentPlayListId = playListManager.crearPlayListReturnId(nombre, esPublica, idUsuario);
        cancionesPlaylistList.clear();
        System.out.println("Playlist creada con ID " + currentPlayListId);
    }

    @FXML
    private void onAgregarCancion() {
        cancion selected = tablaTodasCanciones.getSelectionModel().getSelectedItem();
        if (selected != null && currentPlayListId != -1) {
            playListManager.agregarCancion(currentPlayListId, selected.getId_cancion());
            cargarCancionesPlaylist();
        }
    }

    @FXML
    private void onEliminarCancion() {
        cancion selected = tablaCanciones.getSelectionModel().getSelectedItem();
        if (selected != null && currentPlayListId != -1) {
            playListManager.eliminarCancion(currentPlayListId, selected.getId_cancion());
            cargarCancionesPlaylist();
        }
    }

    // ----------------- NUEVOS BOTONES -----------------

    @FXML
    private void onVolver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/UserMenuView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error al volver a UserMenuView: " + e.getMessage());
        }
    }

    @FXML
    private void onVerPlaylists(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/UserPlaylistsView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error al abrir UserPlaylistsView: " + e.getMessage());
        }
    }
}
