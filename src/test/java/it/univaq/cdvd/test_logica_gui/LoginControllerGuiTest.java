package it.univaq.cdvd.test_logica_gui;


import it.univaq.cdvd.controller.LoginController;
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
    void testLoginButtonDisplaysOtherPage() throws Exception {

        clickOn(controller.usernameTextField).write("MarioRossi");
        clickOn(controller.passwordPasswordField).write("esempio@gmail.com");

        clickOn(controller.loginButton);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
        Parent expectedRoot = loader.load();

        // Get the current scene root
        Parent actualRoot = FxToolkit.toolkitContext().getRegisteredStage().getScene().getRoot();

        // Compare the roots
        assertEquals(expectedRoot.getId(), actualRoot.getId(), "The auth.fxml page should be displayed.");
    }

    @Test
    void testAnnulButtonDisplaysAuthPage() throws Exception {
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
}

