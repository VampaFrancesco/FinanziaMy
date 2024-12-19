package it.univaq.cdvd.test_logica_gui;

import it.univaq.cdvd.controller.RegistrazioneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrazioneGUITest extends ApplicationTest {

    RegistrazioneController controller = new RegistrazioneController();
    @Override
    public void start(Stage stage) throws Exception {
        // Carica l'FXML direttamente
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/registrazione.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
   /* @Test
    void registerButtonTest() throws Exception {

        clickOn(controller.usernameTextField).write("Paola");
        clickOn(controller.passwordPasswordField).write("p");
        clickOn(controller.emailTextField).write("esempio@gmail.com");

        clickOn(controller.registratiButton);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
        Parent expectedRoot = loader.load();

        // Get the current scene root
        Parent actualRoot = FxToolkit.toolkitContext().getRegisteredStage().getScene().getRoot();

        // Compare the roots
        assertEquals(expectedRoot.getId(), actualRoot.getId(), "The auth.fxml page should be displayed.");
    }

    */
    @Test

    void testAnnulButtonDisplaysLandingPage() throws Exception {
        // Click the annulButton
        clickOn("#annullaButton");

        // Load the auth.fxml to compare
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/landing.fxml"));
        Parent expectedRoot = loader.load();

        // Get the current scene root
        Parent actualRoot = FxToolkit.toolkitContext().getRegisteredStage().getScene().getRoot();

        // Compare the roots
        assertEquals(expectedRoot.getId(), actualRoot.getId(), "The landing.fxml page should be displayed.");
    }

}
