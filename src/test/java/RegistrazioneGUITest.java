

import it.univaq.cdvd.Runner;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeoutException;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class RegistrazioneGUITest extends ApplicationTest {
    @BeforeEach
    void setup() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Runner::new);
        // Eventuali setup specifici prima di ogni test
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/registrazione.fxml")); // Cambia con il tuo file FXML
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Pagina di Registrazione");
        stage.show();
    }


    @Test
    void testRegistrazioneCampiVuoti() {
        // Simula il clic sul pulsante di registrazione senza inserire alcun dato
        clickOn("#registratiButton"); // Cambia con l'id del tuo pulsante di registrazione

        // Verifica che il messaggio di errore sia visualizzato
        verifyThat("#registerMessage", hasText("Username, email, o password non validi.")); // Cambia con l'id e il testo del messaggio
    }

    @Test
    void testRegistrazioneConDatiValidi() {
        // Inserisce dati validi nei campi di testo
        clickOn("#usernameTextField").write("utenteTest"); // ogni volta uno nuovo
        clickOn("#emailTextField").write("test@example.com"); // Cambia con l'id del tuo campo email
        clickOn("#passwordPasswordField").write("password123"); // Cambia con l'id del tuo campo password

        // Simula il clic sul pulsante di registrazione
        clickOn("#registratiButton");

        // Verifica che il messaggio di benvenuto sia visualizzato
        verifyThat("#registerMessage", hasText("Benvenuto, utenteTest!")); // Cambia con l'id e il testo atteso
    }

    @Test
    void testRegistrazioneUsernameEsistente() {
        // Inserisce un username già presente nel database
        clickOn("#usernameTextField").write("utenteTest"); // utilizza stesso nome del metodo sopra
        clickOn("#emailTextField").write("email@esistente.com");
        clickOn("#passwordPasswordField").write("password123");

        // Simula il clic sul pulsante di registrazione
        clickOn("#registratiButton");

        // Verifica che il messaggio di errore sia visualizzato
        verifyThat("#registerMessage", hasText("Username già esistente.")); // Cambia con l'id e il testo atteso
    }


}
