module PoliSongFX {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
	opens co.edu.poli.controller to javafx.fxml;
	opens co.edu.poli.model to javafx.base, javafx.fxml;
	
	exports co.edu.poli.controller;
    exports co.edu.poli.model;
}
