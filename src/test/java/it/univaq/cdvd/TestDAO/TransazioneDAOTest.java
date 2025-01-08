package it.univaq.cdvd.TestDAO;

import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.utilTest.HibernateUtilTest;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class TransazioneDAOTest {

    public boolean save(Transazione transazione) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtilTest.getSessionFactory().openSession();
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


    public boolean eliminaTransazione(long idTransazione) {
        Transaction transaction = null;

        try (Session session = HibernateUtilTest.getSessionFactory().openSession()) {
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

    public boolean modifica(Transazione transazione) {
        Transaction tx = null;
        try (Session session = HibernateUtilTest.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(transazione);
            tx.commit();
            return true;
        }catch(Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return false;
    }


    public List<Transazione> findTransactionByUser(Utente utente) {
        try (Session session = HibernateUtilTest.getSessionFactory().openSession()) {
            String hql = "FROM Transazione t WHERE t.utente = :utente";
            Query<Transazione> query = session.createQuery(hql, Transazione.class);
            query.setParameter("utente", utente);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Transazione> getTransazioni(String categoria, LocalDate dataInizio, LocalDate dataFine, Utente utenteCorrente) {
        try (Session session = HibernateUtilTest.getSessionFactory().openSession()) {

            return session.createQuery(
                            "FROM Transazione WHERE nome_categoria = :categoria AND data >= :dataInizio AND data <= :dataFine AND utente = :utenteId",
                            Transazione.class
                    )
                    .setParameter("categoria", categoria)
                    .setParameter("dataInizio", dataInizio)
                    .setParameter("dataFine", dataFine)
                    .setParameter("utenteId", utenteCorrente)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
