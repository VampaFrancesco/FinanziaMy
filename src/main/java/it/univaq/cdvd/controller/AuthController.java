package it.univaq.cdvd.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthController {


    @FXML
    public Button login;
    @FXML
    public Button register;

    @FXML
    public void handleLoginOnAction(ActionEvent event) {
        try {
            // Carica il file auth.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent root = loader.load();

            // Ottieni la finestra corrente e imposta la nuova scena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Pagina Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRegistrazioneOnAction(ActionEvent event) {
        try {
            // Carica il file auth.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/registrazione.fxml"));
            Parent root = loader.load();

            // Ottieni la finestra corrente e imposta la nuova scena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Pagina Registrazione");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
