package it.univaq.cdvd.test_logica_business;

import it.univaq.cdvd.TestDAO.CategoriaDAOTest;
import it.univaq.cdvd.TestDAO.TransazioneDAOTest;
import it.univaq.cdvd.TestDAO.UtenteDAOTest;
import it.univaq.cdvd.dao.CategoriaDAO;
import it.univaq.cdvd.dao.TransazioneDAO;
import it.univaq.cdvd.dao.UtenteDAO;
import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.util.HibernateUtil;
import it.univaq.cdvd.utilTest.HibernateUtilTest;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class TestTransazioneDAO extends ApplicationTest {

    private TransazioneDAOTest transazioneDAO;

    @BeforeAll
    public void setup() {
        // Configura Hibernate con un database in memoria (H2)
        Configuration configuration = new Configuration()
                .configure("/hibernate-test.cfg.xml");// Specifica il file di configurazione di Hibernate
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        HibernateUtilTest.setSessionFactory(sessionFactory);

        transazioneDAO = new TransazioneDAOTest();

    }

    @Test
    public void testTransazione() {

        Transazione transazione = new Transazione();
        UtenteDAOTest utenteDAOTest = new UtenteDAOTest();
        CategoriaDAOTest categoriaDAOTest = new CategoriaDAOTest();

        // Crea un'istanza di Utente
        Utente utente = new Utente();
        utente.setUsername("Test1");
        utente.setEmail("test1@example.com");
        utente.setPassword("password");
        utente.setSaldo(200.0);

        // Salva l'utente nel database
        utenteDAOTest.save(utente);

        //creo categoria
        Categoria categoria = new Categoria();
        categoria.setNome("Test1");
        categoriaDAOTest.save(categoria);

        // Crea un'istanza di Transazione
        transazione.setId(1L);
        transazione.setCausale("Test causale");
        transazione.setData(LocalDate.now());
        transazione.setImporto(200.0);
        transazione.setUtente(utente); // Associa l'utente salvato
        transazione.setCategoria(categoria);
        transazione.setNomeCategoria(categoria.getNome());

        // Salva la transazione nel database
       boolean result = transazioneDAO.save(transazione);

       assertTrue(result, "La transazione non viene salvata correttamente.");

    }
}
