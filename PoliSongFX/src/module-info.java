module PoliSongFX {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
	opens co.edu.poli.controller to javafx.fxml;
	
	exports co.edu.poli.controller;
}
