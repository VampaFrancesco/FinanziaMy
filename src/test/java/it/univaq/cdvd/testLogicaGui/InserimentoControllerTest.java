package it.univaq.cdvd.testLogicaGui;

import it.univaq.cdvd.controller.InserimentoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InserimentoControllerTest extends ApplicationTest {

    private InserimentoController controller;

    @Override
    public void start(Stage stage) throws Exception {
        // Carica l'FXML direttamente
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/inserimento.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testInitialize() {
        // Verifica che gli elementi dell'interfaccia siano inizializzati correttamente
        assertNotNull(controller.text);
        assertNotNull(controller.importo);
        assertNotNull(controller.causale);
        assertNotNull(controller.inserisciImporto);
        assertNotNull(controller.categoriaList);
        assertNotNull(controller.data);
        assertNotNull(controller.inserisci);
        // Verifica che le categorie siano caricate
    }

    @Test
    public void testInserisciTransazione() {
        // Simula l'inserimento dei dati
        clickOn(controller.causale).write("Pagamento affitto");
        clickOn(controller.inserisciImporto).write("500.00");
        // Simula la selezione di una data dal DatePicker
        interact(() -> controller.data.setValue(java.time.LocalDate.of(2024, 12, 15)));
        clickOn(controller.inserisci);

        // Verifica che i dati siano stati correttamente acquisiti
        assertEquals("Pagamento affitto", controller.causale.getText());
        assertEquals("500.00", controller.inserisciImporto.getText());
        assertEquals(java.time.LocalDate.of(2024, 12, 15), controller.data.getValue());
    }

    @Test
    public void testInserisciTransazioneCampoVuoto() {
        // Lascio il campo causale vuoto
        clickOn(controller.inserisciImporto).write("500.00");
        interact(() -> controller.data.setValue(java.time.LocalDate.of(2024, 12, 15)));
        clickOn(controller.inserisci);

        // Verifica che venga mostrato un alert di errore
        assertEquals("", controller.causale.getText());

    }

    @Test
    public void testInserisciTransazioneImportoNonValido() {
        // Inserisco un importo non valido
        clickOn(controller.causale).write("Pagamento affitto");
        clickOn(controller.inserisciImporto).write("abc");
        interact(() -> controller.data.setValue(java.time.LocalDate.of(2024, 12, 15)));
        clickOn(controller.inserisci);

        // Verifica che il campo importo sia vuoto dopo il tentativo
        assertEquals("abc", controller.inserisciImporto.getText());
    }

    @Test
    public void testInserisciTransazioneDataNonSelezionata() {
        // Lascio la data vuota
        clickOn(controller.causale).write("Pagamento affitto");
        clickOn(controller.inserisciImporto).write("500.00");
        clickOn(controller.inserisci);

        // Verifica che non venga impostata una data
        Assertions.assertNull(controller.data.getValue());
    }
}
