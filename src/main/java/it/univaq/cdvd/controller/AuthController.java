package it.univaq.cdvd.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthController {


    @FXML public Button login;
    @FXML public Button register;
    @FXML public TextField email;
    @FXML public PasswordField password;

    @FXML
    public void handleClick(ActionEvent event) {
        System.out.println("Clicked");
    }

}
