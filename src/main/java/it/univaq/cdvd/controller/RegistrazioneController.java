package it.univaq.cdvd.controller;

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
    public PasswordField passwordPasswordField;
    @FXML
    public Label registerMessage;

    @FXML
    public void handleAnnullaClick(ActionEvent event) {
        try {
            // Carica il file auth.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/auth.fxml"));
            Parent root = loader.load();

            // Ottieni la finestra corrente e imposta la nuova scena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Pagina Auth");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRegistrazioneClick(ActionEvent event) {
        // Recupera i dati inseriti dall'utente
        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();

        // Controlla che i campi non siano vuoti
        if (username.isEmpty() || password.isEmpty()) {
            registerMessage.setText("Username o password non possono essere vuoti.");
            return;
        }

        // Crea un nuovo oggetto Utente
        Utente nuovoUtente = new Utente(username, password);

        // Salva l'utente nel database
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(nuovoUtente); // Salva il nuovo utente
            transaction.commit();
            registerMessage.setText("Utente registrato con successo!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in caso di errore
            }
            registerMessage.setText("Errore durante la registrazione: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close(); // Chiudi la sessione
        }
    }
}






