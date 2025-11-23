package co.edu.poli.controller;

import java.io.IOException;

import co.edu.poli.datos.cancionDAO;
import co.edu.poli.datos.viniloDAO;
import co.edu.poli.datos.vinilo_cancionDAO;
import co.edu.poli.model.cancion;
import co.edu.poli.model.vinilo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegistrarProductoController {

    @FXML private TextField txtNombre;
    @FXML private TextField tipoProd;
    @FXML private TextField txtArtista;
    @FXML private TextField txtAnio;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;

    @FXML private TableView<cancion> tablaCanciones;
    @FXML private TableColumn<cancion, Integer> colId;
    @FXML private TableColumn<cancion, String> colTitulo;
    @FXML private TableColumn<cancion, String> colDuracion; // muestra duración en minutos

    private cancionDAO cancionDAO = new cancionDAO();
    private viniloDAO viniloDAO = new viniloDAO();
    private vinilo_cancionDAO vinCancionDAO = new vinilo_cancionDAO();

    private ObservableList<cancion> listaCanciones = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        tipoProd.setText("Vinilo");
        tipoProd.setEditable(false);

        // Configurar columnas de la tabla
        colId.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId_cancion()).asObject()
        );
        colTitulo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre())
        );
        colDuracion.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        String.valueOf(cellData.getValue().getDuracion()) + " min"
                )
        );

        // Cargar canciones del sistema
        cargarCanciones();

        // Permitir seleccionar varias canciones
        tablaCanciones.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void cargarCanciones() {
        listaCanciones.clear();
        listaCanciones.addAll(cancionDAO.readAllCanciones());
        tablaCanciones.setItems(listaCanciones);
    }

    @FXML
    private void registrarProducto(ActionEvent event) {
        try {
            // Validación de campos
            if (txtNombre.getText().isEmpty() || txtArtista.getText().isEmpty()
                    || txtAnio.getText().isEmpty() || txtPrecio.getText().isEmpty()
                    || txtStock.getText().isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, completa todos los campos.");
                return;
            }

            String nombre = txtNombre.getText();
            String artista = txtArtista.getText();
            int anio = Integer.parseInt(txtAnio.getText());
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            // Crear vinilo
            vinilo nuevoVinilo = new vinilo();
            nuevoVinilo.setNombre(nombre);
            nuevoVinilo.setArtista(artista);
            nuevoVinilo.setAnio(anio);
            nuevoVinilo.setPrecio(precio);
            nuevoVinilo.setInventario(stock);

            viniloDAO.createVinilo(nuevoVinilo);

            int idVinilo = nuevoVinilo.getId_vinilo();

            // Asociar canciones seleccionadas
            ObservableList<cancion> cancionesSeleccionadas = tablaCanciones.getSelectionModel().getSelectedItems();
            for (cancion c : cancionesSeleccionadas) {
                vinCancionDAO.addCancionToVinilo(idVinilo, c.getId_cancion());
            }

            mostrarAlerta(Alert.AlertType.INFORMATION, "Vinilo registrado", "El vinilo ha sido registrado correctamente ✅");
            limpiarFormulario();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "Año, Precio y Stock deben ser numéricos.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al registrar el vinilo.");
            e.printStackTrace();
        }
    }

    @FXML
    private void volver(ActionEvent event) {
        try {
            // Cerrar ventana actual
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.close();
            // Abrir ventana del menú proveedor
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/view/ProveedorMenuVIew.fxml"));
            Parent root = loader.load();
            Stage menuStage = new Stage();
            menuStage.setTitle("Menú Proveedor");
            menuStage.setScene(new Scene(root));
            menuStage.show();
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir el menú del proveedor.");
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtArtista.clear();
        txtAnio.clear();
        txtPrecio.clear();
        txtStock.clear();
        tablaCanciones.getSelectionModel().clearSelection();
    }
}
