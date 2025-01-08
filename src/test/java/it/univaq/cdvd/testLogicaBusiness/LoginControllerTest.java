package it.univaq.cdvd.testLogicaBusiness;

import it.univaq.cdvd.TestDAO.UtenteDAOTest;
import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.utilTest.HibernateUtilTest;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class LoginControllerTest{

    public UtenteDAOTest utenteDAOTest = new UtenteDAOTest();
    public UtenteDAOTest loginDao = new UtenteDAOTest();
    public Utente utente;
    @BeforeEach
    public void setUp() {
        // Configura Hibernate con un database in memoria (H2)
        Configuration configuration = new Configuration()
                .configure("/hibernate-test.cfg.xml");// Specifica il file di configurazione di Hibernate
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        HibernateUtilTest.setSessionFactory(sessionFactory);

        // Crea un'istanza di Utente
        utente = new Utente();
        utente.setUsername("franvam");
        utente.setEmail("test1@example.com");
        utente.setPassword("x");
        utente.setSaldo(0.0);

        // Salva l'utente nel database
        utenteDAOTest.save(utente);

    }

    @Test
    public void testLoginWithValidCredentials() {
        Utente user = loginDao.findUserByUsernameAndPassword("franvam", "x");

        assertEquals("franvam", user.getUsername(), "Il nome utente o password non corrisponde.");
    }
    @Test
    public void testLoginWithUnValidCredentials() {
        Utente user = loginDao.findUserByUsernameAndPassword("Abdul", "Rest");

        assertNull(user, "L'utente non dovrebbe essere trovato con credenziali errate.");
    }
    @Test
    public void testLoginWithEmptyCredentials() {
        Utente user = loginDao.findUserByUsernameAndPassword("", "");

        // Verifica che l'utente non venga trovato
        assertNull(user, "L'utente non dovrebbe essere trovato con nome utente e password vuoti.");
    }

}

