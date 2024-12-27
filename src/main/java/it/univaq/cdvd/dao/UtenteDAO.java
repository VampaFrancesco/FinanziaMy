package it.univaq.cdvd.dao;

import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;


public class UtenteDAO {

    /**
     * Metodo che verifica le credenziali dell'utente nel database.
     *
     * @param username il nome utente fornito.
     * @param plainPassword la password fornita.
     * @return l'oggetto Utente se trovato, altrimenti null.
     */
    public Utente findUserByUsernameAndPassword(String username, String plainPassword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Recupera l'utente con lo username specificato
            String hql = "FROM Utente WHERE username = :username";
            Query<Utente> query = session.createQuery(hql, Utente.class);
            query.setParameter("username", username);
            Utente utente = query.uniqueResult();

            // Verifica se l'utente esiste e se la password corrisponde
            if (utente != null && BCrypt.checkpw(plainPassword, utente.getPassword())) {
                return utente; // Restituisce l'utente se la password corrisponde
            } else {
                return null; // Utente non trovato o password errata
            }
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

        // Crittografia della password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        Utente nuovoUtente = new Utente(username, email, hashedPassword);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(nuovoUtente); // Salva il nuovo utente con la password crittografata
        transaction.commit();
        session.close(); // Chiudi la sessione
    }
}
