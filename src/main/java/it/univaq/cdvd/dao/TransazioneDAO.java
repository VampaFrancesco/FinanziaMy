package it.univaq.cdvd.dao;

import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
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

    public List<Transazione> findTransactionByUser(Utente utente) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Transazione t WHERE t.utente = :utente";
            Query<Transazione> query = session.createQuery(hql, Transazione.class);
            query.setParameter("utente", utente);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
