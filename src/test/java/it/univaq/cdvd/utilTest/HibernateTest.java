package it.univaq.cdvd.utilTest;

import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.model.Utente;
import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.Init;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateTest {

    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    public static void init() throws FileNotFoundException, SQLException {

        // Connessione al database in memoria
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "admin", "admin");

        // Esegui uno script di DROP per eliminare tutte le tabelle esistenti
        try {
            RunScript.execute(connection, new StringReader("DROP ALL OBJECTS;"));
        } catch (SQLException e) {
            System.out.println("Nessun oggetto da eliminare o errore durante il DROP.");
        }

        // Carica lo script per ricreare e popolare il database
        RunScript.execute(connection, new FileReader("src/test/resources/finanziamy.sql"));
    }


    @BeforeAll
    public static void setUp() {
        sessionFactory = new Configuration().configure("/hibernate-test.cfg.xml").buildSessionFactory();
    }

    @BeforeEach
    public void openSession() throws SQLException, FileNotFoundException {
        session = sessionFactory.openSession();

    }

    @AfterEach
    public void closeSession() {
        session.close();
    }

    @AfterAll
    public static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

     @Test
    public void testTransazione() {
        Transaction tx = session.beginTransaction();
        Transazione transazione = new Transazione();


         // Crea un'istanza di Utente
         Utente utente = new Utente();
         utente.setUsername("Test1");
         utente.setEmail("test1@example.com");
         utente.setPassword("password");
         utente.setSaldo(200.0);

         // Salva l'utente nel database
         session.save(utente);

         //creo categoria
         Categoria categoria = new Categoria();
         categoria.setNome("Test1");
         session.save(categoria);

         // Crea un'istanza di Transazione
         transazione.setId(1L);
         transazione.setCausale("Test causale");
         transazione.setData(LocalDate.now());
         transazione.setImporto(200.0);
         transazione.setUtente(utente); // Associa l'utente salvato
         transazione.setCategoria(categoria);
         transazione.setNomeCategoria(categoria.getNome());

         // Salva la transazione nel database
         session.save(transazione);


        tx.commit();

         Transazione retrieved = session.get(Transazione.class, 1L);

         Assertions.assertNotNull(retrieved, "La transazione salvata non dovrebbe essere null.");
         Assertions.assertEquals(200, retrieved.getImporto(), "L'importo della transazione non corrisponde.");
         Assertions.assertEquals("Test1", retrieved.getUtente().getUsername(), "Il nome dell'utente non corrisponde.");
         Assertions.assertEquals("Test1", retrieved.getCategoria().getNome(), "La categoria non corrisponde.");
         Assertions.assertEquals("Test causale", retrieved.getCausale(), "La causale non corrisponde.");
         Assertions.assertEquals(LocalDate.now(), retrieved.getData(), "La data della transazione non corrisponde.");
     }

}
