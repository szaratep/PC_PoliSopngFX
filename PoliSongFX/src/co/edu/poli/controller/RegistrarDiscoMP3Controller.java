package co.edu.poli.controller;

import co.edu.poli.datos.cancionDAO;
import co.edu.poli.datos.discoMP3DAO;
import co.edu.poli.datos.discomp3_cancionDAO;
import co.edu.poli.model.cancion;
import co.edu.poli.model.discoMP3;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class RegistrarDiscoMP3Controller {

    @FXML private TableView<discoMP3> tablaDiscosMP3;
    @FXML private TableColumn<discoMP3, String> colNombre;
    @FXML private TableColumn<discoMP3, String> colFecha;

    private discoMP3DAO discoDAO = new discoMP3DAO();
    private discomp3_cancionDAO relacionDAO = new discomp3_cancionDAO();
    private cancionDAO cancionDAO = new cancionDAO();

    private ObservableList<discoMP3> listaDiscos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getNombre()));
        colFecha.setCellValueFactory(cell -> {
            Date fecha = (Date) cell.getValue().getFecha_salida();
            return new javafx.beans.property.SimpleStringProperty(fecha != null ? fecha.toString() : "");
        });
        cargarDiscos();
    }

    private void cargarDiscos() {
        listaDiscos.clear();
        listaDiscos.addAll(discoDAO.listarDiscosMp3());
        tablaDiscosMP3.setItems(listaDiscos);
    }

    @FXML
    private void registrarDiscoMP3() {
        Dialog<discoMP3> dialog = new Dialog<>();
        dialog.setTitle("Registrar Disco MP3");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);

        TextField tfNombre = new TextField();
        DatePicker dpFecha = new DatePicker();

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(tfNombre, 1, 0);
        grid.add(new Label("Fecha de lanzamiento:"), 0, 1);
        grid.add(dpFecha, 1, 1);

        // Lista de canciones con checkboxes
        List<cancion> cancionesDisponibles = cancionDAO.readAllCanciones();
        List<CheckBox> checkBoxes = new ArrayList<>();
        int row = 2;
        for(cancion c : cancionesDisponibles){
            CheckBox cb = new CheckBox(c.getNombre());
            grid.add(cb, 1, row++);
            checkBoxes.add(cb);
        }

        dialog.getDialogPane().setContent(grid);
        ButtonType btnRegistrar = new ButtonType("Registrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnRegistrar, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if(btn == btnRegistrar){
                discoMP3 disco = new discoMP3();
                disco.setNombre(tfNombre.getText());
                LocalDate fecha = dpFecha.getValue();
                if(fecha != null) disco.setFecha_salida(Date.valueOf(fecha));
                return disco;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(disco -> {
            int idGenerado = discoDAO.createMP3(disco); // Guardamos disco y obtenemos ID
            disco.setId_MP3(idGenerado);

            // Agregar canciones seleccionadas
            for(int i=0; i<checkBoxes.size(); i++){
                if(checkBoxes.get(i).isSelected()){
                    relacionDAO.addCancionToDisco(disco.getId_MP3(), cancionesDisponibles.get(i).getId_cancion());
                }
            }
            cargarDiscos();
        });
    }

    @FXML
    private void editarDiscoMP3() {
        discoMP3 seleccionado = tablaDiscosMP3.getSelectionModel().getSelectedItem();
        if(seleccionado == null){
            mostrarAlerta("Seleccione un disco para editar");
            return;
        }

        Dialog<discoMP3> dialog = new Dialog<>();
        dialog.setTitle("Editar Disco MP3");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);

        TextField tfNombre = new TextField(seleccionado.getNombre());
        DatePicker dpFecha = new DatePicker();
        Date fecha = (Date) seleccionado.getFecha_salida();
        if(fecha != null){
            dpFecha.setValue(fecha.toLocalDate());
        }

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(tfNombre, 1, 0);
        grid.add(new Label("Fecha de lanzamiento:"), 0, 1);
        grid.add(dpFecha, 1, 1);

        // Cargar canciones
        List<cancion> cancionesDisponibles = cancionDAO.readAllCanciones();
        List<Integer> cancionesDelDisco = relacionDAO.getCancionesByDisco(seleccionado.getId_MP3());
        List<CheckBox> checkBoxes = new ArrayList<>();
        int row = 2;
        for(cancion c : cancionesDisponibles){
            CheckBox cb = new CheckBox(c.getNombre());
            if(cancionesDelDisco.contains(c.getId_cancion())) cb.setSelected(true);
            grid.add(cb, 1, row++);
            checkBoxes.add(cb);
        }

        dialog.getDialogPane().setContent(grid);
        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if(btn == btnGuardar){
                seleccionado.setNombre(tfNombre.getText());
                LocalDate nuevaFecha = dpFecha.getValue();
                if(nuevaFecha != null) seleccionado.setFecha_salida(Date.valueOf(nuevaFecha));
                return seleccionado;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(disco -> {
            discoDAO.updateMP3(disco);

            // Actualizar canciones
            for(int i=0; i<checkBoxes.size(); i++){
                int idCancion = cancionesDisponibles.get(i).getId_cancion();
                if(checkBoxes.get(i).isSelected() && !cancionesDelDisco.contains(idCancion)){
                    relacionDAO.addCancionToDisco(disco.getId_MP3(), idCancion);
                } else if(!checkBoxes.get(i).isSelected() && cancionesDelDisco.contains(idCancion)){
                    relacionDAO.removeCancionFromDisco(disco.getId_MP3(), idCancion);
                }
            }

            cargarDiscos();
        });
    }

    @FXML
    private void volverMenu(javafx.event.ActionEvent event){
        try{
            Stage stageActual = (Stage)((Node)event.getSource()).getScene().getWindow();
            stageActual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/AdminMenuView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Menú Administrador");
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
