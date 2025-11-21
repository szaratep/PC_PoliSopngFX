package co.edu.poli.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    	
    }

    Boolean flag = auth.loginUsuario(userSesion.getText(), passwordSesion.getText());
    
    @FXML
    void inicioSesion(ActionEvent event) {
    	if (auth.loginUsuario(userSesion.getText(), passwordSesion.getText()) == true){
    		mensajeSesion.setText("hola");
    	}
    }

}