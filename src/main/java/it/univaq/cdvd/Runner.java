package it.univaq.cdvd;

import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Objects;

public class Runner extends Application {

    public Button print;

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/view/registrazione.fxml"))));
            Scene scene = new Scene(root);
            Image icon = new Image(Objects.requireNonNull(getClass().getResource("/img/logoApp.jpg")).toExternalForm());
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) { launch(args);
        // Crea un'istanza del nuovo utente
      /*  Utente nuovoUtente = new Utente("MarioRossi", "esempio@gmail.com", "password123");

        // Ottieni la sessione di Hibernate
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Inizia la transazione
            transaction = session.beginTransaction();

            // Salva l'utente nel database
            session.save(nuovoUtente);

            // Conferma la transazione
            transaction.commit();

            System.out.println("Utente aggiunto con successo!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in caso di errore
            }
            e.printStackTrace();
        } finally {
             session.close(); // Chiudi la sessione */
        }
    }




