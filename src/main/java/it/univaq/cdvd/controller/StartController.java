package it.univaq.cdvd.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartController {

    @FXML
    private Button print;

    @FXML
    public void printF(){
        System.out.println("Ciao");
    }
}
