package it.univaq.cdvd.testgui;

import it.univaq.cdvd.Runner;
import it.univaq.cdvd.controller.AuthController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.assertions.api.Assertions.assertThat;

public class AuthControllerTest extends ApplicationTest {


    AuthController controller;

    @BeforeEach
    void setup() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Runner::new); // Sostituisci con la tua classe principale
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("auth.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void testButtonClick() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // Salva l'output originale
        System.setOut(new PrintStream(outputStream));

        try {
            // Simula il clic sul pulsante
            clickOn("#login");

            // Recupera l'output della console
            String consoleOutput = outputStream.toString();

            // Verifica che l'output contenga "Clicked"
            assertTrue(consoleOutput.contains("Clicked"), "La console non contiene il testo 'Clicked'");
        } finally {
            // Ripristina l'output originale della console
            System.setOut(originalOut);
        }
    }
}

