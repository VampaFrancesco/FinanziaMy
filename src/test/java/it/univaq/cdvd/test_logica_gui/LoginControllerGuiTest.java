package it.univaq.cdvd.test_logica_gui;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerGuiTest extends ApplicationTest {

    @BeforeEach
    void setup() throws Exception {
        FxToolkit.registerPrimaryStage(); // Registra lo stage principale

        FxToolkit.setupStage(stage -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(new Scene(root));
            stage.show();
        });
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void testAnnulButtonDisplaysAuthPage() throws Exception {
        // Click the annulButton
        clickOn("#annulButton");

        // Load the auth.fxml to compare
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/auth.fxml"));
        Parent expectedRoot = loader.load();

        // Get the current scene root
        Parent actualRoot = FxToolkit.toolkitContext().getRegisteredStage().getScene().getRoot();

        // Compare the roots
        assertEquals(expectedRoot.getId(), actualRoot.getId(), "The auth.fxml page should be displayed.");
    }
    @Test
    void testLoginButtonDisplaysOtherPage() throws Exception {

        clickOn("#usernameTextField").write("MarioRossi");
        clickOn("#passwordPasswordField").write("esempio@gmail.com");

        clickOn("#loginButton");

        // Recupera il messaggio visualizzato nella loginMessageLabel
        Label loginMessageLabel = lookup("#loginMessageLabel").query();

        // Verifica che il messaggio sia corretto
        assertEquals("Benvenuto MarioRossi!", loginMessageLabel.getText(),
                "Il messaggio della loginMessageLabel non è corretto.");
    }
}

