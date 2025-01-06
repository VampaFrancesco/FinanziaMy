/*
package it.univaq.cdvd.test_logica_business;

import it.univaq.cdvd.dao.CategoriaDAO;
import it.univaq.cdvd.dao.TransazioneDAO;
import it.univaq.cdvd.dao.UtenteDAO;
import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransazioneDAOTest {
UtenteDAO utenteDAO = new UtenteDAO();
    private TransazioneDAO transazioneDAO;

    @BeforeAll
    public void setup() {
        // Configura Hibernate con un database in memoria (H2)
        Configuration configuration = new Configuration()
                .configure("config/hibernate.cfg.xml");// Specifica il file di configurazione di Hibernate
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        HibernateUtil.setSessionFactory(sessionFactory);

        transazioneDAO = new TransazioneDAO();

    }

    @Test
    public void testSave() {

        CategoriaDAO categoriaDAO = new CategoriaDAO();
        Utente utente = new Utente();
        utente.setUsername("testuser");
        utente.setEmail("test@example.com");
        utente.setPassword("password");
        utente.setSaldo(1000.0);

        // Salva l'utente nel database (necessario per mantenere l'integrità referenziale), lo devi mockare
        utenteDAO.save(utente);
        // Crea una nuova transazione di test
        Transazione transazione = new Transazione();
        transazione.setId(1L);
        transazione.setImporto(100.50);
        transazione.setCausale("Pagamento test");
        transazione.setNomeCategoria("casa");
        transazione.setData(java.time.LocalDate.now());
        transazione.setCategoria(categoriaDAO.cercaCategoria("casa"));
        transazione.setUtente(utente);

        // Verifica che la transazione venga salvata con successo
        boolean result = transazioneDAO.save(transazione);
        assertTrue(result, "La transazione non è stata salvata correttamente");
    }


    @AfterAll
    public void tearDown() {
        // Chiudi la SessionFactory al termine dei test
        HibernateUtil.getSessionFactory().close();
    }
}
*/
