package it.univaq.cdvd.testgui;

import it.univaq.cdvd.Runner;
import it.univaq.cdvd.controller.StartController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.testfx.assertions.api.Assertions.assertThat;

public class StartControllerTest extends ApplicationTest {


    StartController controller;

    @BeforeEach
    void setup() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Runner::new); // Sostituisci con la tua classe principale
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void testButtonClick() {
        clickOn("#print"); // Simula il clic sul pulsante
        assertThat(lookup("#print").queryButton()).hasText("andato"); // Verifica il testo
    }
}

