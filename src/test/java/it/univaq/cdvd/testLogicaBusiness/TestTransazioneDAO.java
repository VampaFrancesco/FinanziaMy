package it.univaq.cdvd.testLogicaBusiness;

import it.univaq.cdvd.TestDAO.CategoriaDAOTest;
import it.univaq.cdvd.TestDAO.TransazioneDAOTest;
import it.univaq.cdvd.TestDAO.UtenteDAOTest;
import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.utilTest.HibernateUtilTest;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class TestTransazioneDAO extends ApplicationTest {

    private TransazioneDAOTest transazioneDAOTest;
    public Transazione transazione = new Transazione();
    private UtenteDAOTest utenteDAOTest = new UtenteDAOTest();
    private CategoriaDAOTest categoriaDAOTest = new CategoriaDAOTest();
    public Utente utente;

    @BeforeAll
    public void setup() {
        // Configura Hibernate con un database in memoria (H2)
        Configuration configuration = new Configuration()
                .configure("/hibernate-test.cfg.xml");// Specifica il file di configurazione di Hibernate
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        HibernateUtilTest.setSessionFactory(sessionFactory);

        transazioneDAOTest = new TransazioneDAOTest();

        // Crea un'istanza di Utente
        utente = new Utente();
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
        transazioneDAOTest.save(transazione);

    }


    @Test
    public void testSalva() {
        // Salva la transazione nel database
       boolean result = transazioneDAOTest.save(transazione);

       assertNotNull(transazione);
       assertTrue(result, "La transazione non viene salvata correttamente.");
    }

    @Test
    public void testElimina(){
        // Salva la transazione nel database
        boolean result = transazioneDAOTest.eliminaTransazione(transazione.getId());

        assertNotNull(transazione);
        assertEquals(1L, transazione.getId(), "Sono due transazioni diverse, non viene eliminata quella del test");
        assertTrue(result, "La transazione non viene eliminata correttamente.");
    }

    @Test
    public void testModifica(){

        transazione.setCausale("Causale modificata");
        System.out.println(transazione.getCausale());
        boolean result = transazioneDAOTest.modifica(transazione);

        assertNotNull(transazione);
        assertNotEquals("Test causale", transazione.getCausale(), "La transazione non viene modificata");
        assertTrue(result, "La transazione viene salvata correttamente.");
    }

    @Test
    void testGetTransazioni() {

        String categoria = "Test1";
        LocalDate dataInizio = LocalDate.of(2023, 1, 1);
        LocalDate dataFine = LocalDate.now();


        List<Transazione> result = transazioneDAOTest.getTransazioni(categoria, dataInizio, dataFine,utente);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Causale modificata", result.get(0).getCausale());
        assertEquals(200.0, result.get(0).getImporto());

    }
}
