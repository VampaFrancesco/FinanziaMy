package it.univaq.cdvd.dao;

import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.util.SessionManager;
import it.univaq.cdvd.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransazioneDAO {


    public boolean save(Transazione transazione) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            // Salva l'entità Categoria se necessario
            if (transazione.getCategoria() != null && transazione.getCategoria().getId() == null) {
                session.save(transazione.getCategoria());
            }

            session.save(transazione);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

    public List<Transazione> findAll() {
        List<Transazione> transazioni = new ArrayList<>();


        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Transazione");
            for (Object o : query.list()) {
                System.out.println(o.toString());
                transazioni.add((Transazione) o);
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        return transazioni;
    }


    public boolean eliminaTransazione(long idTransazione) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Inizia una transazione
            transaction = session.beginTransaction();

            // Recupera l'entità da eliminare
            Transazione transazione = session.get(Transazione.class, idTransazione);
            if (transazione != null) {
                // Elimina l'entità
                session.delete(transazione);
                // Conferma la transazione
                transaction.commit();
                return true;
            } else {
                System.out.println("Transazione non trovata con ID: " + idTransazione);
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

    public List<Transazione> getTransazioni(String categoria, LocalDate dataInizio, LocalDate dataFine) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Utente utenteCorrente = SessionManager.getInstance().getUtente();

            return session.createQuery(
                            "FROM Transazione WHERE nome_categoria = :categoria AND data >= :dataInizio AND data <= :dataFine AND utente = :utenteId",
                            Transazione.class
                    )
                    .setParameter("categoria", categoria)
                    .setParameter("dataInizio", dataInizio)
                    .setParameter("dataFine", dataFine)
                    .setParameter("utenteId", utenteCorrente.getUtente())
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
