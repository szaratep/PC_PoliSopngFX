package co.edu.poli.controller;

import co.edu.poli.datos.viniloDAO;
import co.edu.poli.datos.discoMP3DAO;
import co.edu.poli.datos.cancionDAO;
import co.edu.poli.model.vinilo;
import co.edu.poli.model.discoMP3;
import co.edu.poli.model.cancion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.List;

public class GestionarProductosController {

    @FXML private TableView<Object> tablaProductos;
    @FXML private TableColumn<Object, String> colNombre;
    @FXML private TableColumn<Object, String> colTipo;
    @FXML private TableColumn<Object, String> colPrecio;
    @FXML private TableColumn<Object, String> colStock;

    private viniloDAO viniloDAO = new viniloDAO();
    private discoMP3DAO discoMP3DAO = new discoMP3DAO();
    private cancionDAO cancionDAO = new cancionDAO();
    private ObservableList<Object> listaProductos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(cell -> {
            Object item = cell.getValue();
            if(item instanceof vinilo) return new javafx.beans.property.SimpleStringProperty(((vinilo) item).getNombre());
            else if(item instanceof discoMP3) return new javafx.beans.property.SimpleStringProperty(((discoMP3) item).getNombre());
            else if(item instanceof cancion) return new javafx.beans.property.SimpleStringProperty(((cancion) item).getNombre());
            else return new javafx.beans.property.SimpleStringProperty("");
        });

        colTipo.setCellValueFactory(cell -> {
            Object item = cell.getValue();
            if(item instanceof vinilo) return new javafx.beans.property.SimpleStringProperty("Vinilo");
            else if(item instanceof discoMP3) return new javafx.beans.property.SimpleStringProperty("Disco MP3");
            else if(item instanceof cancion) return new javafx.beans.property.SimpleStringProperty("Canción");
            else return new javafx.beans.property.SimpleStringProperty("");
        });

        colPrecio.setCellValueFactory(cell -> {
            Object item = cell.getValue();
            if(item instanceof vinilo) return new javafx.beans.property.SimpleStringProperty(String.valueOf(((vinilo) item).getPrecio()));
            else if(item instanceof discoMP3) return new javafx.beans.property.SimpleStringProperty("2000"); // Precio fijo MP3
            else if(item instanceof cancion) return new javafx.beans.property.SimpleStringProperty(String.valueOf(((cancion) item).getPrecio()));
            else return new javafx.beans.property.SimpleStringProperty("");
        });

        colStock.setCellValueFactory(cell -> {
            Object item = cell.getValue();
            if(item instanceof vinilo) return new javafx.beans.property.SimpleStringProperty(String.valueOf(((vinilo) item).getInventario()));
            else return new javafx.beans.property.SimpleStringProperty("-"); // MP3 y canciones no tienen stock
        });

        cargarProductos();
    }

    private void cargarProductos() {
        listaProductos.clear();
        List<vinilo> vinilos = viniloDAO.listarVinilos();
        List<discoMP3> discos = discoMP3DAO.listarDiscosMp3();
        List<cancion> canciones = cancionDAO.readAllCanciones();

        listaProductos.addAll(vinilos);
        listaProductos.addAll(discos);
        listaProductos.addAll(canciones);
        tablaProductos.setItems(listaProductos);
    }

    @FXML
    private void crearProducto(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Crear Producto");
        alert.setHeaderText("Seleccione el tipo de producto a crear");

        ButtonType btnDiscoMP3 = new ButtonType("Disco MP3");
        ButtonType btnCancion = new ButtonType("Canción");
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(btnDiscoMP3, btnCancion, btnCancelar);

        alert.showAndWait().ifPresent(res -> {
            try {
                if(res == btnDiscoMP3 || res == btnCancion) {
                    // Cerrar ventana actual
                    Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stageActual.close();

                    // Abrir nueva ventana
                    FXMLLoader loader = null;
                    if(res == btnDiscoMP3) loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/RegistrarDiscoMP3View.fxml"));
                    else if(res == btnCancion) loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/RegistrarCancionView.fxml"));

                    if(loader != null) {
                        Stage stage = new Stage();
                        stage.setScene(new Scene(loader.load()));
                        stage.setTitle("Registrar Producto");
                        stage.showAndWait(); // Espera a que se cierre la ventana
                        cargarProductos(); // Recargar tabla al volver
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void eliminarProducto() {
        Object seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if(seleccionado == null) {
            mostrarAlerta("Seleccione un producto para eliminar.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText(null);
        confirm.setContentText("¿Desea eliminar este producto?");
        confirm.showAndWait().ifPresent(res -> {
            if(res == ButtonType.OK) {
                if(seleccionado instanceof vinilo) viniloDAO.deleteVinilo(((vinilo) seleccionado).getId_vinilo());
                else if(seleccionado instanceof discoMP3) discoMP3DAO.deleteMP3(((discoMP3) seleccionado).getId_MP3());
                else if(seleccionado instanceof cancion) cancionDAO.deleteCancion(((cancion) seleccionado).getId_cancion());
                cargarProductos();
            }
        });
    }

    @FXML
    private void volverMenu(ActionEvent event) {
        try {
            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageActual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/AdminMenuView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Menú Administrador");
            stage.show();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
