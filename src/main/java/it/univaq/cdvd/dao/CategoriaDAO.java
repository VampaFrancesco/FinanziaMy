package it.univaq.cdvd.dao;

import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CategoriaDAO {

    public boolean save(Categoria categoria) {
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

}
