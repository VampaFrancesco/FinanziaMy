package it.univaq.cdvd.controller;
import it.univaq.cdvd.DAO.UtenteDAO;
import it.univaq.cdvd.model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LoginControllerTest extends ApplicationTest {

    private UtenteDAO loginDao;

    @BeforeEach
    public void setUp() {
        // Inizializza l'istanza di UtenteDAO
        loginDao = new UtenteDAO();
    }

    @Test
    public void testLoginWithValidCredentials() {
        Utente user =loginDao.findUserByUsernameAndPassword("MarioRossi", "Password123");

        assertEquals("MarioRossi", user.getUsername(), "Il nome utente o password non corrisponde.");
    }
    @Test
    public void testLoginWithUnValidCredentials() {
        Utente user =loginDao.findUserByUsernameAndPassword("Abdul", "Rest");

        assertNull(user, "L'utente non dovrebbe essere trovato con credenziali errate.");
    }
    @Test
    public void testLoginWithEmptyPassword() {
        Utente user = loginDao.findUserByUsernameAndPassword("", "");

        // Verifica che l'utente non venga trovato
        assertNull(user, "L'utente non dovrebbe essere trovato con nome utente e password vuoti.");
    }

}
