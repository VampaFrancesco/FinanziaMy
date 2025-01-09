package it.univaq.cdvd.testLogicaGui;

import it.univaq.cdvd.controller.ModificaController;
import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.util.ShowAlert;
import javafx.scene.control.Alert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.time.LocalDate;

import static com.sun.javafx.util.Utils.runOnFxThread;
import static org.junit.jupiter.api.Assertions.*;

public class ModificaControllerTest extends ApplicationTest {

    private ModificaController controller;
    private Transazione transazione;

    @Override
    public void start(Stage stage) throws Exception {
        // Carica la scena da FXML e inizializza il controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifica.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        stage.setScene(new Scene(root));
        stage.show();
    }

    // Metodo di setup che viene eseguito prima di ogni test
    @BeforeEach
    public void setup() {
        // Crea la categoria e la transazione
        Categoria categoria = new Categoria();
        categoria.setNome("Svago");

        transazione = new Transazione();
        transazione.setId(1L);
        transazione.setCausale("Causale di test");
        transazione.setImporto(100.0);
        transazione.setData(LocalDate.now());
        transazione.setCategoria(categoria); // Associa la categoria

        // Prepara i dati nel controller
        runOnFxThread(() -> {
            controller.lista.getItems().clear(); // Pulisce la lista per ogni test
            controller.lista.getItems().add(transazione); // Aggiunge la transazione
            controller.lista.getSelectionModel().select(0); // Seleziona la transazione
        });
    }

    @Test
    public void testModificaTransazioneSuccesso() {
        // Modifica i campi della transazione
        runOnFxThread(() -> {
            controller.nuovoImporto.setText("200.0");
            controller.nuovoCausale.setText("Causale aggiornata");
            controller.nuovoData.setValue(LocalDate.now());
        });

        clickOn(controller.modifica); // Simula il clic sul pulsante "modifica"

        // Verifica che i dati siano stati aggiornati
        assertEquals(200.0, controller.lista.getItems().get(0).getImporto(), "L'importo dovrebbe essere aggiornato.");
        assertEquals("Causale aggiornata", controller.lista.getItems().get(0).getCausale(), "La causale dovrebbe essere aggiornata.");
    }

    @Test
    public void testModificaTransazioneCampiVuoti() {
        // Pulisce i campi
        runOnFxThread(() -> {
            controller.nuovoImporto.clear();
            controller.nuovoCausale.clear();
            controller.nuovoData.setValue(null);
        });

        clickOn(controller.modifica); // Simula clic sul pulsante "modifica"

        // Verifica che venga mostrato un alert di errore
        assertSame(Alert.AlertType.WARNING, ShowAlert.lastAlert.getAlertType(), "Dovrebbe mostrare un alert di errore per campi vuoti.");
    }

    @Test
    public void testNessunaTransazioneSelezionata() {
        // Non seleziona nessuna transazione
        runOnFxThread(() -> {
            controller.lista.getSelectionModel().clearSelection();
        });

        clickOn(controller.modifica); // Simula clic sul pulsante "modifica"

        // Verifica che venga mostrato un alert di errore
        assertSame(Alert.AlertType.WARNING, ShowAlert.lastAlert.getAlertType(), "Dovrebbe mostrare un alert di errore per nessuna transazione selezionata.");
    }
}
