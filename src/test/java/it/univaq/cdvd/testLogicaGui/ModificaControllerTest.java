package it.univaq.cdvd.testLogicaGui;

import it.univaq.cdvd.controller.ModificaController;
import it.univaq.cdvd.dao.CategoriaDAO;
import it.univaq.cdvd.dao.TransazioneDAO;
import it.univaq.cdvd.dao.UtenteDAO;
import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.util.HibernateUtil;
import it.univaq.cdvd.util.SessionManager;
import it.univaq.cdvd.util.ShowAlert;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModificaControllerTest extends ApplicationTest {

    private ModificaController controller;
    private Categoria categoria;
    private Transazione transazione;
    private Utente user;
    private TransazioneDAO transazioneDAO;
    private CategoriaDAO categoriaDAO;
    private UtenteDAO utenteDAO;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifica.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.lista.setItems(FXCollections.observableArrayList(transazioneDAO.findTransactionByUser(user)));
        controller.categoriaList.setItems(categoriaDAO.listaCategoria(user.getUsername()));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeAll
    public void setupDatabase() {
        HibernateUtil.setDbms("/hibernate-test.cfg.xml");
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "admin", "admin")) {
            // Esegui lo script per eliminare solo i record
            try (Statement statement = connection.createStatement()) {
                statement.execute("SET REFERENTIAL_INTEGRITY FALSE;"); // Disabilita i vincoli per evitare errori
                statement.execute("TRUNCATE TABLE transazione;"); // Svuota la tabella
                statement.execute("TRUNCATE TABLE utente;"); // Ripeti per ogni tabella necessaria
                statement.execute("TRUNCATE TABLE categoria;");
                statement.execute("SET REFERENTIAL_INTEGRITY TRUE;"); // Riabilita i vincoli
            }

            // Esegui uno script SQL esterno se necessario
            RunScript.execute(connection, new FileReader("src/test/resources/finanziamy.sql"));

            System.out.println("Record eliminati con successo!");
        } catch (Exception e) {
            e.printStackTrace();
        }


        transazioneDAO = new TransazioneDAO();
        categoriaDAO = new CategoriaDAO();
        utenteDAO = new UtenteDAO();

        // Crea un utente di test
        user = new Utente("test", "password", "testUser", 1000.0);
        utenteDAO.save(user);

        // Crea una categoria di test
        categoria = new Categoria();
        categoria.setNome("Svago");
        categoria.setDescrizione("Spese per il tempo libero");
        categoriaDAO.save(categoria);


        // Crea una transazione di test
        transazione = new Transazione();
        transazione.setCausale("Spesa iniziale");
        transazione.setImporto(100.0);
        transazione.setNomeCategoria("Svago");
        transazione.setData(LocalDate.now());
        transazione.setUtente(user);
        transazione.setCategoria(categoria);
        transazioneDAO.save(transazione);

        // Imposta l'utente nel SessionManager
        SessionManager.getInstance().setUtente(user);


    }

    @Test
    public void testModificaTransazioneSuccesso() {
        Platform.runLater(() -> {
            // Verifica che ci sia almeno una transazione
            assertFalse(controller.lista.getItems().isEmpty(), "La lista delle transazioni non dovrebbe essere vuota.");

            // Seleziona la prima transazione
            controller.lista.getSelectionModel().select(0);

            // Imposta i nuovi valori nei campi
            controller.nuovoImporto.setText("200.0");
            controller.nuovoCausale.setText("Causale aggiornata");
            controller.nuovoData.setValue(LocalDate.now());

            // Simula il click sul pulsante "modifica"
            controller.modifica.fire();

            // Recupera la transazione aggiornata
            Transazione transazioneAggiornata = transazioneDAO.findTransactionByUser(user).get(0);

            // Verifica che i dati siano stati aggiornati
            assertEquals(200.0, transazioneAggiornata.getImporto(), "L'importo dovrebbe essere aggiornato.");
            assertEquals("Causale aggiornata", transazioneAggiornata.getCausale(), "La causale dovrebbe essere aggiornata.");
        });
    }

    @Test
    public void testNessunaTransazioneSelezionata() {
        Platform.runLater(() -> {
            // Deseleziona tutte le transazioni
            controller.lista.getSelectionModel().clearSelection();

            // Simula il click sul pulsante "modifica"
            controller.modifica.fire();

            // Verifica che venga mostrato un alert
            assertNotNull(ShowAlert.lastAlert, "Nessun alert mostrato.");
            assertEquals(Alert.AlertType.WARNING, ShowAlert.lastAlert.getAlertType(), "Dovrebbe mostrare un alert di errore per nessuna transazione selezionata.");
        });
    }

    @Test
    public void testModificaConCampiVuoti() {
        Platform.runLater(() -> {
            // Seleziona la prima transazione
            controller.lista.getSelectionModel().select(0);

            // Svuota i campi
            controller.nuovoImporto.clear();
            controller.nuovoCausale.clear();
            controller.nuovoData.setValue(null);

            // Simula il click sul pulsante "modifica"
            controller.modifica.fire();

            // Verifica che venga mostrato un alert
            assertNotNull(ShowAlert.lastAlert, "Nessun alert mostrato.");
            assertEquals(Alert.AlertType.WARNING, ShowAlert.lastAlert.getAlertType(), "Dovrebbe mostrare un alert di errore per campi vuoti.");
        });
    }
}

