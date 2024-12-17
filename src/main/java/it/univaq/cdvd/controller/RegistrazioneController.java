package it.univaq.cdvd.controller;

import it.univaq.cdvd.dao.UtenteDAO;
import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.util.HibernateUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;


public class RegistrazioneController {


    @FXML
    public Button registratiButton;
    @FXML
    public Button annullaButton;
    @FXML
    public TextField usernameTextField;
    @FXML
    public TextField emailTextField;
    @FXML
    public PasswordField passwordPasswordField;
    @FXML
    public Label registerMessage;

    private final UtenteDAO utenteDAO = new UtenteDAO();

    @FXML
    public void handleAnnullaClick(ActionEvent event) {
        try {
            // Carica il file auth.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/landing.fxml"));
            Parent root = loader.load();

            // Ottieni la finestra corrente e imposta la nuova scena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Pagina Iniziale");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRegistrazioneClick(ActionEvent event) throws Exception {
        // Recupera i dati inseriti dall'utente
        String username = usernameTextField.getText();
        System.out.println("username: " + username);
        String password = passwordPasswordField.getText();
        System.out.println("password: " + password);
        String email = emailTextField.getText();
        System.out.println("email: " + email);
        // Controlla che i campi non siano vuoti
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {

            registerMessage.setText("Username, email, o password non validi.");
            throw new Exception();
        }
        Utente utenteEsistente = null;
        utenteEsistente = utenteDAO.findUserByUsername(username);
        if (utenteEsistente != null) {
            registerMessage.setText("Username già esistente.");
            throw new Exception();
        }
        utenteDAO.saveUser(username, email, password);
        registerMessage.setText("Benvenuto, " + username + "!");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
            Parent root = loader.load();

            // Ottieni la finestra corrente e imposta la nuova scena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Pagina Main");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


