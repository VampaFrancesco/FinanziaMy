package it.univaq.cdvd.testLogicaGui;
/*
import it.univaq.cdvd.controller.HomeController;
import it.univaq.cdvd.controller.LandingController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.service.query.PointQuery;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeControllerTest extends ApplicationTest {

    HomeController controller = new HomeController();

    @BeforeAll
    public static void setUp() {
        // Avvia la piattaforma JavaFX prima dell'esecuzione dei test
        Platform.startup(() -> {});
    }

    @Test
    void testLogoutButtonDisplaysByHomePage() throws Exception {

        clickOn((PointQuery) controller.logout);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        Parent expectedRoot = loader.load();

        // Get the current scene root
        Parent actualRoot = FxToolkit.toolkitContext().getRegisteredStage().getScene().getRoot();

        // Compare the roots
        assertEquals(expectedRoot.getId(), actualRoot.getId(), "The login.fxml page should be displayed.");
    }


}*/

import it.univaq.cdvd.controller.AggiuntaCategoriaController;
import it.univaq.cdvd.controller.HomeController;
import it.univaq.cdvd.model.Transazione;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HomeControllerTest extends ApplicationTest {

    HomeController homeController;
    TableView<Transazione> tabellaTransazioni;
    Menu menu = new Menu();
    MenuItem aggiungiCategoria;
    MenuItem eliminaCategoria;
    MenuItem nuovaTransazione;
    MenuItem logout;




    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
        Parent root = loader.load();
        homeController = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();

        // Inizializza i componenti della UI da testare
        tabellaTransazioni = homeController.tabellaTransazioni;
        aggiungiCategoria = homeController.aggiungiCategoria;
        eliminaCategoria = homeController.eliminaCategoria;
        nuovaTransazione = homeController.nuovaTransazione;
    }

/*
        @Test
        public void testTabellaTransazioniPopolata() {
            // Verifica che la tabella delle transazioni non sia vuota
            assertNotNull(tabellaTransazioni);
            assertFalse(tabellaTransazioni.getItems().isEmpty(), "La tabella delle transazioni dovrebbe essere popolata.");
        }
/*
        @Test
        void testClickOnMenuItem() {
            // Apri il menu "Categoria"
            clickOn("Categoria");

            // Clicca su un menu item specifico, come "Aggiungi Categoria"
            clickOn("aggiungiCategoria");

            // Aggiungi ulteriori asserzioni per verificare l'effetto del clic
            assertTrue(someCondition); // Verifica che l'azione associata a "Aggiungi Categoria" sia stata eseguita correttamente
        }

*/

    @Test
    public void testNuovaTransazione() throws Exception {

        clickOn("Transazioni");
        clickOn("Nuova transazione");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/inserimento.fxml"));
        Parent expectedRoot = loader.load();

        // Get the current scene root
        Parent actualRoot = FxToolkit.toolkitContext().getRegisteredStage().getScene().getRoot();

        // Compare the roots
        assertEquals(expectedRoot.getClass(), actualRoot.getClass(), "La pagina inserimento.fxml dovrebbe essere visualizzata.");

    }

    @Test
    public void testEliminaTransazione() throws Exception {

        clickOn("Transazioni");
        clickOn("Elimina transazione");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cancellazione.fxml"));
        Parent expectedRoot = loader.load();

        // Get the current scene root
        Parent actualRoot = FxToolkit.toolkitContext().getRegisteredStage().getScene().getRoot();

        // Compare the roots
        assertTrue(actualRoot instanceof javafx.scene.layout.AnchorPane, "La root non è anchor pane");
    }

    @Test
    public void testModificaTransazione() throws Exception {

        clickOn("Transazioni");
        clickOn("Modifica transazione");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifica.fxml"));
        Parent expectedRoot = loader.load();

        Parent actualRoot = FxToolkit.toolkitContext().getRegisteredStage().getScene().getRoot();

        // Compare the roots
        assertTrue(actualRoot instanceof javafx.scene.layout.AnchorPane, "La root non è anchor pane");
    }

    @Test
    public void testAggiungiCategoria() throws Exception {

        clickOn("Categoria");
        clickOn("Aggiungi Categoria");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/aggiuntacategoria.fxml"));
        Parent expectedRoot = loader.load();

        // Get the current scene root
        Parent actualRoot = FxToolkit.toolkitContext().getRegisteredStage().getScene().getRoot();

        // Compare the roots
        assertEquals(expectedRoot.getClass(), actualRoot.getClass(), "La pagina aggiuntacategoria.fxml dovrebbe essere visualizzata.");

    }

/*
    @Test
    public void testEliminaCategoria() throws Exception {

        clickOn("Categoria");
        clickOn("Elimina Categoria");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/eliminaCategoria.fxml"));
        Parent expectedRoot = loader.load();

        Parent actualRoot = FxToolkit.toolkitContext().getRegisteredStage().getScene().getRoot();

        // Compare the roots
        assertTrue(actualRoot instanceof javafx.scene.layout.AnchorPane, "La root non è anchor pane");

    }

    @Test
    public void testCreaReport() throws Exception {

        clickOn("Statistiche");
        clickOn("Crea Report");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/report.fxml"));
        Parent expectedRoot = loader.load();

        // Get the current scene root
        Parent actualRoot = FxToolkit.toolkitContext().getRegisteredStage().getScene().getRoot();

        // Compare the roots
        assertTrue(actualRoot instanceof javafx.scene.layout.AnchorPane, "La root non è anchor pane");

    }*/

/*
    @Test
    public void testSaldo() {
            // Verifica che il saldo sia correttamente visualizzato
            homeController.showSaldo();

            // Verifica che l'etichetta del saldo non sia vuota e contenga un valore
            assertNotNull(homeController.saldo.getText(), "Il saldo dovrebbe essere visualizzato.");
            assertFalse(homeController.saldo.getText().isEmpty(), "Il saldo non dovrebbe essere vuoto.");
        }
*/
    @Test
    void testLogoutMenuUtemDisplaysByHomePage() throws Exception {

        clickOn("Gestione account");
        clickOn("Logout");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        Parent expectedRoot = loader.load();

        // Get the current scene root
        Parent actualRoot = FxToolkit.toolkitContext().getRegisteredStage().getScene().getRoot();

        assertEquals(expectedRoot.getId(), actualRoot.getId(), "The login.fxml page should be displayed.");
    }
}