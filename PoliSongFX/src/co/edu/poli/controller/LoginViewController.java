package co.edu.poli.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import co.edu.poli.negocio.*;
public class LoginViewController {

    @FXML
    private Label mensajeSesion;

    @FXML
    private TextField passwordSesion;

    @FXML
    private TextField userSesion;
    
    authManager auth = new authManager();
    @FXML
    void RegistroSesion(ActionEvent event) {
    	try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/RegisterView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al abrir RegisterView.fxml: " + e.getMessage());
        }
    	
    }
    
    @FXML 
    void inicioSesion(ActionEvent event) {
    	if (auth.loginUsuario(userSesion.getText(), passwordSesion.getText()) == true){
    		mensajeSesion.setText("Se inicio sesion correctamente como Usuario");
    	}
    	else {
    		mensajeSesion.setText("Usuario o contraseña incorrecta");
    	}
    }
    
    @FXML
    void volverInicio(ActionEvent event) {
    	try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/mainPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al abrir mainPage.fxml: " + e.getMessage());
        }
    }

}