package it.univaq.cdvd.testLogicaGui;

import it.univaq.cdvd.TestDAO.TransazioneDAOTest;
import it.univaq.cdvd.controller.ModificaController;
import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.util.SessionManager;
import it.univaq.cdvd.util.ShowAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testfx.framework.junit5.ApplicationTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ModificaControllerTest extends ApplicationTest {


    public ModificaController controller ;
    public ShowAlert sa = new ShowAlert();


    @Override
    public void start(Stage stage) throws Exception {
        // Carica l'FXML direttamente
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifica.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        try {
            // Recupera le transazioni legate all'utente dal DAO
            TransazioneDAOTest transazioneDAO = new TransazioneDAOTest();
            List<Transazione> transazioni = transazioneDAO.findTransactionByUser(SessionManager.getInstance().getUtente());

            // Converte la lista in un ObservableList
            ObservableList<Transazione> transazioniUtente = FXCollections.observableArrayList(transazioni);

            // Associa i dati alla TableView
            controller.lista.setItems(transazioniUtente);
        } catch (Exception e) {
            e.printStackTrace();
            sa.showAlert("Errore", "Errore durante il caricamento delle transazioni: " + e.getMessage(), Alert.AlertType.ERROR);
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Test
    void testInitializeWhenEnvIsTest() {
        // Verifichiamo che, essendo MYAPP_ENV = "test", vengano caricate 3 categorie (Cibo, Svago, Lavoro)
        assertNotNull(controller, "Il controller non dovrebbe essere null");
        assertNotNull(controller.categoriaList, "La ComboBox delle categorie non dovrebbe essere null");
        assertEquals(3, controller.categoriaList.getItems().size(),
                "Con MYAPP_ENV = test ci aspettiamo 3 categorie fittizie");
    }

    @Test
    void testModificaTransazioneNessunaSelezione() {
        assertDoesNotThrow(() -> {
            interact(() -> controller.modificaTransazione());
        }, "Il metodo non dovrebbe lanciare eccezioni se nessuna transazione è selezionata");
    }


    @Test
    void testModificaTransazioneCampiVuoti() {
        // Seleziono la transazione finta
        Transazione transFake = new Transazione();
        transFake.setId(1L);

        interact(() -> {
            controller.lista.getItems().add(transFake);
            controller.lista.getSelectionModel().select(transFake);
            // Campi vuoti -> non li tocco
        });

        assertDoesNotThrow(() -> {
            interact(() -> controller.modificaTransazione());
        }, "Il metodo non deve esplodere se i campi sono vuoti");
    }

    @Test
    void testModificaTransazioneImportoNonNumerico() {
        Transazione transFake = new Transazione();
        transFake.setId(2L);

        // Inizializzo (sempre con interact)
        interact(() -> {
            controller.lista.getItems().add(transFake);
            controller.lista.getSelectionModel().select(transFake);

            controller.nuovoImporto.setText("abc");   // Non numerico
            controller.nuovoCausale.setText("Causale test");
            controller.nuovoData.setValue(LocalDate.now());
        });

        // Eseguo la modifica
        assertDoesNotThrow(() -> {
            interact(() -> controller.modificaTransazione());
        }, "Il metodo dovrebbe gestire la NumberFormatException");
    }


}
