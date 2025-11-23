package co.edu.poli.controller;

import co.edu.poli.datos.cancionDAO;
import co.edu.poli.model.cancion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.util.List;

public class RegistrarCancionController {

    @FXML private TableView<cancion> tablaCanciones;
    @FXML private TableColumn<cancion, String> colNombre;
    @FXML private TableColumn<cancion, String> colDuracion;
    @FXML private TableColumn<cancion, String> colPrecio;
    @FXML private TableColumn<cancion, String> colTamano;

    private cancionDAO cancionDAO = new cancionDAO();
    private ObservableList<cancion> listaCanciones = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getNombre()));
        colDuracion.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cell.getValue().getDuracion())));
        colPrecio.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cell.getValue().getPrecio())));
        colTamano.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cell.getValue().getTamano_mb())));
        
        cargarCanciones();
    }

    private void cargarCanciones() {
        listaCanciones.clear();
        List<cancion> canciones = cancionDAO.readAllCanciones();
        listaCanciones.addAll(canciones);
        tablaCanciones.setItems(listaCanciones);
    }

    @FXML
    private void registrarCancion() {
        Dialog<cancion> dialog = new Dialog<>();
        dialog.setTitle("Registrar Canción");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);

        TextField tfNombre = new TextField();
        TextField tfDuracion = new TextField();
        TextField tfPrecio = new TextField();
        TextField tfTamano = new TextField();

        grid.add(new Label("Nombre:"), 0, 0); grid.add(tfNombre, 1, 0);
        grid.add(new Label("Duración (min):"), 0, 1); grid.add(tfDuracion, 1, 1);
        grid.add(new Label("Precio:"), 0, 2); grid.add(tfPrecio, 1, 2);
        grid.add(new Label("Tamaño (MB):"), 0, 3); grid.add(tfTamano, 1, 3);

        dialog.getDialogPane().setContent(grid);

        ButtonType btnRegistrar = new ButtonType("Registrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnRegistrar, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if(btn == btnRegistrar){
                try{
                    cancion nueva = new cancion(
                        tfNombre.getText(),
                        Double.parseDouble(tfDuracion.getText()),
                        Double.parseDouble(tfPrecio.getText()),
                        Double.parseDouble(tfTamano.getText())
                    );
                    int idGenerado = cancionDAO.createCancion(nueva);
                    nueva.setId_cancion(idGenerado);
                    return nueva;
                } catch(NumberFormatException e){
                    mostrarAlerta("Ingrese valores numéricos válidos para duración, precio y tamaño.");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(c -> cargarCanciones());
    }

    @FXML
    private void editarCancion() {
        cancion seleccionado = tablaCanciones.getSelectionModel().getSelectedItem();
        if(seleccionado == null){
            mostrarAlerta("Seleccione una canción para editar.");
            return;
        }

        Dialog<cancion> dialog = new Dialog<>();
        dialog.setTitle("Editar Canción");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);

        TextField tfNombre = new TextField(seleccionado.getNombre());
        TextField tfDuracion = new TextField(String.valueOf(seleccionado.getDuracion()));
        TextField tfPrecio = new TextField(String.valueOf(seleccionado.getPrecio()));
        TextField tfTamano = new TextField(String.valueOf(seleccionado.getTamano_mb()));

        grid.add(new Label("Nombre:"), 0, 0); grid.add(tfNombre, 1, 0);
        grid.add(new Label("Duración (min):"), 0, 1); grid.add(tfDuracion, 1, 1);
        grid.add(new Label("Precio:"), 0, 2); grid.add(tfPrecio, 1, 2);
        grid.add(new Label("Tamaño (MB):"), 0, 3); grid.add(tfTamano, 1, 3);

        dialog.getDialogPane().setContent(grid);

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if(btn == btnGuardar){
                try{
                    seleccionado.setNombre(tfNombre.getText());
                    seleccionado.setDuracion(Double.parseDouble(tfDuracion.getText()));
                    seleccionado.setPrecio(Double.parseDouble(tfPrecio.getText()));
                    seleccionado.setTamano_mb(Double.parseDouble(tfTamano.getText()));
                    cancionDAO.updateCancion(seleccionado);
                    return seleccionado;
                } catch(NumberFormatException e){
                    mostrarAlerta("Ingrese valores numéricos válidos para duración, precio y tamaño.");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(c -> cargarCanciones());
    }

    @FXML
    private void volverMenu(javafx.event.ActionEvent event) {
        try {
            Stage stageActual = (Stage)((Node)event.getSource()).getScene().getWindow();
            stageActual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/AdminMenuView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Menú Administrador");
            stage.show();

        } catch(IOException e){
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
