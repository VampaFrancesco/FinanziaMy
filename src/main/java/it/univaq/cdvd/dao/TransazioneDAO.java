package it.univaq.cdvd.dao;

import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
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

    public List<Transazione> getTransazioni(String categoria, LocalDate dataInizio, LocalDate dataFine) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Transazione WHERE nome_categoria = :categoria AND data >= :dataInizio AND data <= :dataFine",
                            Transazione.class
                    )
                    .setParameter("categoria", categoria)
                    .setParameter("dataInizio", dataInizio)
                    .setParameter("dataFine", dataFine)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
