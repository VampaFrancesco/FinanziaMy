package it.univaq.cdvd.DAO;

import it.univaq.cdvd.model.Utente;
import org.hibernate.Session;
import org.hibernate.query.Query;
import static it.univaq.cdvd.util.HibernateUtil.sessionFactory;

public class UtenteDAO {

    /**
     * Metodo che verifica le credenziali dell'utente nel database.
     *
     * @param username il nome utente fornito.
     * @param password la password fornita.
     * @return l'oggetto Utente se trovato, altrimenti null.
     */
    public Utente findUserByUsernameAndPassword(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Utente WHERE username = :username AND password = :password";
            Query<Utente> query = session.createQuery(hql, Utente.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            return query.uniqueResult(); // Restituisce l'utente o null se non trovato
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
