package co.edu.poli.controller;

import co.edu.poli.model.playList;
import co.edu.poli.negocio.playListManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PlaylistUserController {

    @FXML private TextField txtIdPlaylist;
    @FXML private TextField txtNombre;
    @FXML private TextField txtUsuarioId;
    @FXML private CheckBox chkPublica;

    @FXML private TableView<Integer> tablaCanciones;
    @FXML private TableColumn<Integer, Integer> colIdCancion;

    private playListManager manager = new playListManager();
    private ObservableList<Integer> cancionesObservable = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colIdCancion.setCellValueFactory(
            data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue()).asObject()
        );

        tablaCanciones.setItems(cancionesObservable);
    }

    @FXML
    private void onCargarPlaylist() {
        int id = Integer.parseInt(txtIdPlaylist.getText());

        playList p = manager.playListDao.readPlayList(id);

        if (p == null) {
            alert("No existe playlist con ese ID.");
            return;
        }

        // Llenar datos
        txtNombre.setText(p.getNombre());
        txtUsuarioId.setText(String.valueOf(p.getId_usuario()));
        chkPublica.setSelected(p.isPublica());

        // Cargar canciones
        cancionesObservable.setAll(manager.playlistCancionDao.getCancionesByPlaylist(id));
    }

    @FXML
    private void onCrearPlaylist() {
        int id = Integer.parseInt(txtIdPlaylist.getText());
        String nombre = txtNombre.getText();
        int idUsuario = Integer.parseInt(txtUsuarioId.getText());
        boolean publica = chkPublica.isSelected();

        manager.crearPlayList(id, nombre, publica, idUsuario);
        alert("Playlist creada correctamente.");
    }

    @FXML
    private void onActualizarPlaylist() {
        int id = Integer.parseInt(txtIdPlaylist.getText());
        int idUsuario = Integer.parseInt(txtUsuarioId.getText());
        String nombre = txtNombre.getText();
        boolean publica = chkPublica.isSelected();

        manager.actualizarPlayList(id, idUsuario, nombre, publica);
        alert("Playlist actualizada correctamente.");
    }

    @FXML
    private void onAgregarCancion() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Agregar Canción");
        dialog.setContentText("ID de la canción:");

        dialog.showAndWait().ifPresent(idStr -> {
            int idCancion = Integer.parseInt(idStr);
            int idPlaylist = Integer.parseInt(txtIdPlaylist.getText());

            manager.agregarCancion(idPlaylist, idCancion);

            cancionesObservable.add(idCancion);
        });
    }

    @FXML
    private void onEliminarCancion() {
        Integer seleccion = tablaCanciones.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            alert("Seleccione una canción para eliminar.");
            return;
        }

        int idPlaylist = Integer.parseInt(txtIdPlaylist.getText());
        manager.eliminarCancion(idPlaylist, seleccion);

        cancionesObservable.remove(seleccion);
    }

    @FXML
    private void onEliminarPlaylist() {
        int id = Integer.parseInt(txtIdPlaylist.getText());

        manager.eliminarPlayList(id);
        alert("Playlist eliminada correctamente.");

        // Limpiar vista
        txtNombre.clear();
        txtUsuarioId.clear();
        chkPublica.setSelected(false);
        cancionesObservable.clear();
    }

    private void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }
}
