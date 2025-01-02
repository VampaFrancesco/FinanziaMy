package it.univaq.cdvd.test_logica_gui;


import it.univaq.cdvd.controller.LoginController;
import it.univaq.cdvd.util.SessionManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerGuiTest extends ApplicationTest {

    LoginController controller = new LoginController();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testInitialize() {
        assertNotNull(controller);
    }


    @Test
    void testLoginButtonDisplaysHomePage() throws Exception {

        clickOn(controller.usernameTextField).write("MarioRossi");
        clickOn(controller.passwordPasswordField).write("esempio@gmail.com");

        clickOn(controller.loginButton);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
        Parent expectedRoot = loader.load();

        // Get the current scene root
        Parent actualRoot = FxToolkit.toolkitContext().getRegisteredStage().getScene().getRoot();

        // Compare the roots
        assertEquals(expectedRoot.getId(), actualRoot.getId(), "The home.fxml page should be displayed.");
    }

    @Test
    void testAnnulButtonDisplaysLandingPage() throws Exception {
        // Click the annulButton
        clickOn(controller.annulButton);

        // Load the auth.fxml to compare
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/landing.fxml"));
        Parent expectedRoot = loader.load();

        // Get the current scene root
        Parent actualRoot = FxToolkit.toolkitContext().getRegisteredStage().getScene().getRoot();

        // Compare the roots
        assertEquals(expectedRoot.getId(), actualRoot.getId(), "The landing.fxml page should be displayed.");
    }
    @Test
    public void testSession () {
        //aggiungi che mocki il salvataggio al db
        clickOn(controller.usernameTextField).write("prova3");
        clickOn(controller.passwordPasswordField).write("p");
        clickOn(controller.loginButton);
        assertEquals(SessionManager.getInstance().getUtente().getUsername(),"prova3");
    }
}

