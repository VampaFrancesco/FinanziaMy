package it.univaq.cdvd.TestDAO;

import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.utilTest.HibernateUtilTest;
import it.univaq.cdvd.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOTest {

    //per inserire le categoria nella combobox posso fare un metodo che mi torna una lista di categorie e prendere il nome, per quanto riguarda l'inserimento basta inserire solo il nome
    //e volendo posso associare a ogni categoria un colore, si potrebbe fare.

    public boolean save(Categoria categoria) {
        Transaction tx = null;
        try (Session session = HibernateUtilTest.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(categoria);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) {
            }
            e.printStackTrace();
        }
        return false;
    }


    public ObservableList<String> listaCategoria() {
        ObservableList<String> categorieList = FXCollections.observableArrayList();
        try{
            Session session = HibernateUtilTest.getSessionFactory().openSession();
            Query<Categoria> query = session.createQuery("from Categoria");
            List<Categoria> categorie = query.list();
            for(Categoria c : categorie){
                categorieList.add(c.getNome());
            };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return categorieList;
    }

    public Categoria cercaCategoria(String nome){
        Categoria categoria = null;
        try{
            Session session = HibernateUtilTest.getSessionFactory().openSession();
            Query<Categoria> query = session.createQuery("from Categoria where nome = :nome");
            query.setParameter("nome", nome);
            categoria = query.uniqueResult();
        }catch(HibernateException e){
            throw new RuntimeException(e);
        }
        return categoria;
    }


    public List<Categoria> getAllCategorie() {
        try (Session session = HibernateUtilTest.getSessionFactory().openSession()) {
            Utente utenteCorrente = SessionManager.getInstance().getUtente();
            return session.createQuery("FROM Categoria WHERE utente = :utenteId", Categoria.class)
                    .setParameter("utenteId", utenteCorrente.getUtente())
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Categoria> findAll() {
        List<Categoria> categorie = new ArrayList<>();

        Session session = null;

        try {
            session = HibernateUtilTest.getSessionFactory().openSession();
            Query query = session.createQuery("from Categoria");
            for (Object o : query.list()) {
                System.out.println(o.toString());
                categorie.add((Categoria) o);
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        return categorie;
    }

    public boolean eliminaCategoria(long idCategoria) {
        Transaction transaction = null;

        try (Session session = HibernateUtilTest.getSessionFactory().openSession()) {
            // Inizia una transazione
            transaction = session.beginTransaction();

            // Recupera l'entità da eliminare
            Categoria categoria = session.get(Categoria.class, idCategoria);
            if (categoria != null) {
                // Elimina l'entità
                session.delete(categoria);
                // Conferma la transazione
                transaction.commit();
                return true;
            } else {
                System.out.println("Transazione non trovata con ID: " + idCategoria);
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in caso di errore
            }
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Categoria> findByUtente(String username) {
        List<Categoria> categorie = new ArrayList<>();
        ObservableList<Categoria> categorieList = FXCollections.observableArrayList();

        Session session = null;

        try {
            session = HibernateUtilTest.getSessionFactory().openSession();
            // Modifica della query per utilizzare il nome corretto della proprietà
            Query query = session.createQuery("from Categoria where utente.username = :username");
            query.setParameter("username", username);  // Impostazione del parametro username
            for (Object o : query.list()) {
                System.out.println(o.toString());
                categorie.add((Categoria) o);
            }
            categorieList.addAll(categorie);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // Chiudere la sessione Hibernate
            }
        }
        return categorieList;
    }

}
