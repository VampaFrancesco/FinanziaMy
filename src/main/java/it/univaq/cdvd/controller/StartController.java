package it.univaq.cdvd.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class StartController {

    @FXML
    public Button print;

    @FXML
    public void handleClick(){
        print.setText("andato");
    }
    public Scene getScene(){
        return new Scene(print);
    }
}
