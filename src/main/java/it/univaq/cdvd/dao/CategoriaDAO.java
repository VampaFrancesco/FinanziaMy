package it.univaq.cdvd.dao;

import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.util.HibernateUtil;
import it.univaq.cdvd.util.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class CategoriaDAO {

    //per inserire le categoria nella combobox posso fare un metodo che mi torna una lista di categorie e prendere il nome, per quanto riguarda l'inserimento basta inserire solo il nome
    //e volendo posso associare ad ogni categoria un colore, si potrebbe fare.

    /*public boolean save(Categoria categoria) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
*/
    public List<Categoria> getAllCategorie() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Utente utenteCorrente = SessionManager.getInstance().getUtente();
            return session.createQuery("FROM Categoria WHERE utente = :utenteId", Categoria.class)
                    .setParameter("utenteId", utenteCorrente.getUtente())
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
