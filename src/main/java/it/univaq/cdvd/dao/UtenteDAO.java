package it.univaq.cdvd.dao;

import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class UtenteDAO {

    /**
     * Metodo che verifica le credenziali dell'utente nel database.
     *
     * @param username il nome utente fornito.
     * @param password la password fornita.
     * @return l'oggetto Utente se trovato, altrimenti null.
     */
    public Utente findUserByUsernameAndPassword(String username, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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

    /**
     * Metodo per restituire un utente dal DB dato il suo username
     * @param username
     * @return
     */
    public Utente findUserByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Utente WHERE username = :username";
            Query<Utente> query = session.createQuery(hql, Utente.class);
            query.setParameter("username", username);
            return query.uniqueResult(); // Restituisce l'utente o null se non trovato
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * metodo per inserire un utente nel DB
     * @param username
     * @param email
     * @param password
     * @throws Exception
     */
    public void saveUser(String username, String email, String password) throws Exception {
        if ((email.trim().isEmpty()) || (username.trim().isEmpty()) || (password.trim().isEmpty())) {
            throw new Exception("Email, username e password non possono essere vuoti!");

        }
        Utente nuovoUtente = new Utente(username, email, password);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(nuovoUtente); // Salva il nuovo utente
        transaction.commit();
        session.close(); // Chiudi la sessione
    }
}
