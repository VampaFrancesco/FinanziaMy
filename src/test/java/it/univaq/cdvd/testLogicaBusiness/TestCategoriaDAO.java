package it.univaq.cdvd.testLogicaBusiness;

import it.univaq.cdvd.TestDAO.CategoriaDAOTest;
import it.univaq.cdvd.TestDAO.UtenteDAOTest;
import it.univaq.cdvd.dao.CategoriaDAO;
import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.util.HibernateUtil;
import it.univaq.cdvd.util.SessionManager;
import it.univaq.cdvd.utilTest.HibernateUtilTest;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestCategoriaDAO {

    public CategoriaDAOTest categoriaDAO = new CategoriaDAOTest();

    @BeforeAll
    public void setup() {
        // Configura Hibernate con un database in memoria (H2)
        Configuration configuration = new Configuration()
                .configure("/hibernate-test.cfg.xml");// Specifica il file di configurazione di Hibernate
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        HibernateUtilTest.setSessionFactory(sessionFactory);

        UtenteDAOTest utenteDAO = new UtenteDAOTest();
        Utente user = new Utente();
        user.setUsername("Test 2");
        user.setPassword("Test 2");
        user.setEmail("Test 2");
        user.setSaldo(200.0);
        utenteDAO.save(user);

        Categoria cat = new Categoria();
        cat.setNome("Test 1");
        cat.setUtente(user);
        CategoriaDAOTest catDAO = new CategoriaDAOTest();
        catDAO.save(cat);
    }
    @Test
    void testSaveCategoria() {
        Categoria cat = new Categoria();
        cat.setNome("CategoriaTest");
        cat.setDescrizione("DescrizioneTest");

        boolean salvata = categoriaDAO.save(cat);
        assertTrue(salvata, "La categoria dovrebbe essere salvata correttamente");

        // Controllo che effettivamente ci sia nel DB
        Session session = HibernateUtilTest.getSessionFactory().openSession();
        Categoria fromDb = session.get(Categoria.class, cat.getId());
        assertNotNull(fromDb, "La categoria dovrebbe essere presente nel DB");
        assertEquals("CategoriaTest", fromDb.getNome());
        assertEquals("DescrizioneTest", cat.getDescrizione());
        session.close();
    }


    @Test
    void testSaveCategoriaConDescrizioneNull() {
        Categoria cat = new Categoria();
        cat.setNome("CategoriaTest");

        boolean salvata = categoriaDAO.save(cat);
        assertTrue(salvata, "La categoria dovrebbe essere salvata correttamente");

        // Controllo che effettivamente ci sia nel DB
        Session session = HibernateUtilTest.getSessionFactory().openSession();
        Categoria fromDb = session.get(Categoria.class, cat.getId());
        assertNotNull(fromDb, "La categoria dovrebbe essere presente nel DB");
        assertEquals("CategoriaTest", fromDb.getNome());
        session.close();

    }


    @Test
    void testListaCategoria() {
        // Il metodo listaCategoria() restituisce un ObservableList<String> con i nomi
        ObservableList<String> lista = categoriaDAO.listaCategoria();
        assertNotNull(lista, "La lista non dovrebbe essere nulla");

        assertTrue(lista.contains("Test 1"), "La lista dovrebbe contenere la categoria precedentemente salvata");
    }

    @Test
    void testCercaCategoria() {
        Categoria trovata = categoriaDAO.cercaCategoria("Test 1");
        assertNotNull(trovata, "Dovrebbe trovare la categoria con nome 'CategoriaTest'");
        assertEquals("Test 1", trovata.getNome());
    }

    @Test
    void testFindAll() {
        List<Categoria> all = categoriaDAO.findAll();
        assertFalse(all.isEmpty(), "La lista di tutte le categorie non dovrebbe essere vuota");
        // Stampa di controllo o asserzione su dimensione
        // assertTrue(all.size() >= 1);
    }

/*    @Test
    void testGetAllCategorie() {
        List<Categoria> categorieUtente = categoriaDAO.getAllCategorie();
        assertNotNull(categorieUtente, "Non deve essere null, anche se potrebbe essere vuoto se la Categoria non è associata a un utente");
    }*/

    @Test
    void testFindByUtente() {
        // Metodo che cerca le categorie associate all'utente con username specifico
        // Noi abbiamo settato come username "testUser"
        ObservableList<Categoria> categorieByUser = categoriaDAO.findByUtente("Test 2");
        assertNotNull(categorieByUser, "La lista di categorie per l'utente 'testUser' non deve essere null");
    }

    @Test
    void testEliminaCategoria() {
        // Recuperiamo la categoria "CategoriaTest" e la eliminiamo
        Categoria daEliminare = categoriaDAO.cercaCategoria("Test 1");
        assertNotNull(daEliminare, "Dovrebbe esistere prima di eliminarla");

        boolean eliminata = categoriaDAO.eliminaCategoria(daEliminare.getId());
        assertTrue(eliminata, "Dovrebbe eliminare la categoria correttamente");

        // Verifichiamo che non esista più
        Session session = HibernateUtilTest.getSessionFactory().openSession();
        Categoria fromDb = session.get(Categoria.class, daEliminare.getId());
        assertNull(fromDb, "La categoria non dovrebbe più essere presente nel DB");
        session.close();
    }

}

