/*
package it.univaq.cdvd.test_logica_gui;

import it.univaq.cdvd.controller.ReportController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReportControllerGuiTest extends ApplicationTest {

    private ReportController controller;

    private ChoiceBox<String> categoryChoiceBox;
    private DatePicker datainizioPicker;
    private DatePicker datafinePicker;
    private Button createPDFButton;
    private Label reportMessageLabel;

    @Override
    public void start(Stage stage) throws Exception {
        // Carica l'FXML direttamente
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/report.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testInitialize() {
        assertNotNull(controller);
    }

    @Test
    void testAnnullaButtonDisplaysHomePage() throws Exception {
        // Click the annulButton
        clickOn(controller.annullaButton);

        // Load the auth.fxml to compare
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
        Parent expectedRoot = loader.load();

        // Get the current scene root
        Parent actualRoot = FxToolkit.toolkitContext().getRegisteredStage().getScene().getRoot();

        // Compare the roots
        assertEquals(expectedRoot.getId(), actualRoot.getId(), "The home.fxml page should be displayed.");
    }

    @Test
    public void testHandleCreatePDF() {
        // Simula la selezione della categoria "Casa"
        clickOn(controller.categoryChoiceBox);
        //clickOn("casa");

        // Simula la selezione delle date
        interact(() -> {
            controller.datainizioPicker.setValue(LocalDate.of(2023, 1, 1));
            controller.datafinePicker.setValue(LocalDate.of(2023, 12, 31));
        });

        // Clicca sul pulsante "Crea PDF"
        clickOn(controller.creaButton);

        // Verifica che il messaggio nella label sia corretto
        Assertions.assertThat(controller.reportMessageLabel.getText()).isEqualTo("Report genearto con successo");
    }
    @Test
    public void testHandleCreatePDFMissingFields() {
        // Lascia i campi vuoti e clicca sul pulsante "Crea PDF"
        clickOn(controller.creaButton);

        // Verifica che il messaggio nella label sia corretto
        Assertions.assertThat(controller.reportMessageLabel.getText()).isEqualTo("Tutti i campi devono essere compilati.");
    }
}
*/
