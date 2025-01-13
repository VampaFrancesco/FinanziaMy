package it.univaq.cdvd.testLogicaGui;

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
import org.testfx.service.query.PointQuery;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HomeControllerTest extends ApplicationTest {

    HomeController controller;
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
        controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();

        // Inizializza i componenti della UI da testare
        tabellaTransazioni = controller.tabellaTransazioni;
        aggiungiCategoria = controller.aggiungiCategoria;
        eliminaCategoria = controller.eliminaCategoria;
        nuovaTransazione = controller.nuovaTransazione;
    }


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

/*    @Test
    public void testEliminaTransazione() throws Exception {

        clickOn("Transazioni");
        clickOn((PointQuery) controller.nuovaTransazione);

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
    }*/

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