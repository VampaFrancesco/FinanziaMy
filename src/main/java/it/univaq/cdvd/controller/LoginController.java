package it.univaq.cdvd.controller;

import it.univaq.cdvd.model.Utente;
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
import org.hibernate.query.Query;

import java.io.IOException;

import static it.univaq.cdvd.util.HibernateUtil.sessionFactory;

public class LoginController {

    @FXML private Button annullaButton;
    @FXML private Button loginButton;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordPasswordField;
    @FXML private Label loginMessageLabel;

    @FXML
    public void annullaButtonOnAction(ActionEvent event) {
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
    public void loginButtonOnAction(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            loginMessageLabel.setText("inserisci username e password.");
            return;
        }

        // Apertura della sessione Hibernate
        try (Session session = sessionFactory.openSession()) {
            // Costruisci la query per verificare l'utente
            String hql = "FROM Utente WHERE username = :username AND password = :password";
            Query<Utente> query = session.createQuery(hql, Utente.class);
            query.setParameter("username", username);
            query.setParameter("password", password);

            // Esegui la query e controlla il risultato
            Utente user = query.uniqueResult();
            if (user != null) {
                loginMessageLabel.setText("Accesso confermato. Benvenuto, " + user.getUsername() + "!");
            } else {
                loginMessageLabel.setText("Accesso negato. Username o password errati.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
