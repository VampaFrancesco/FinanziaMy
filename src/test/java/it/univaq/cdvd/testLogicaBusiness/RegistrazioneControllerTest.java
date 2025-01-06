
package it.univaq.cdvd.testLogicaBusiness;

import it.univaq.cdvd.TestDAO.TransazioneDAOTest;
import it.univaq.cdvd.TestDAO.UtenteDAOTest;
import it.univaq.cdvd.controller.RegistrazioneController;
import it.univaq.cdvd.dao.UtenteDAO;
import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.utilTest.HibernateUtilTest;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistrazioneControllerTest {

    @BeforeAll
    public void setup() {
        // Configura Hibernate con un database in memoria (H2)
        Configuration configuration = new Configuration()
                .configure("/hibernate-test.cfg.xml");// Specifica il file di configurazione di Hibernate
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        HibernateUtilTest.setSessionFactory(sessionFactory);

        UtenteDAOTest utenteDAOTest = new UtenteDAOTest();
        Utente utenteProva = new Utente("prova","x", "x", 0.0);
        utenteDAOTest.save(utenteProva);
    }
    // test per verificare che non si possano inserire campi vuoti nella registrazione
    @Test
    public void testUnvalidRegistration() {
        UtenteDAOTest utenteDAOTest= new UtenteDAOTest();
        assertFalse( utenteDAOTest.saveUser("", "", "",0.0));
    }

    // test che verifica che non si possano inserire utenti già esistenti
    @Test
    public void testExistingUser() {
        UtenteDAOTest utenteDAOTest = new UtenteDAOTest();
        assertFalse(utenteDAOTest.saveUser("prova", "x", "x", 0.0));

    }
}
