package it.univaq.cdvd.testLogicaGui;

import it.univaq.cdvd.TestDAO.TransazioneDAOTest;
import it.univaq.cdvd.controller.CancellazioneController;
import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.model.Transazione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.time.LocalDate;

import static com.sun.javafx.util.Utils.runOnFxThread;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CancellazioneControllerTest extends ApplicationTest {

    private CancellazioneController controller;
    private Transazione transazione;
    private Categoria categoria;

    @Override
    public void start(Stage stage) throws Exception {
        // Carica la scena da FXML e inizializza il controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cancellazione.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setup(){
        categoria = new Categoria();
        categoria.setNome("Svago");

        transazione = new Transazione();
        transazione.setId(2L);
        transazione.setCausale("Causale di test");
        transazione.setImporto(100.0);
        transazione.setData(LocalDate.now());
        transazione.setCategoria(categoria); // Associa la categoria

        runOnFxThread(() -> {
            controller.lista.getSelectionModel().select(0); // Seleziona la transazione
        });
    }



    @Test
    public void testInizializzazioneTabella() {

        assertNotNull(controller);
        assertEquals(1, controller.lista.getItems().size());
        assertNotNull(controller.lista);
    }


}