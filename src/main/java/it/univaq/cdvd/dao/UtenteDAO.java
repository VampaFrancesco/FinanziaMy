package it.univaq.cdvd.dao;

import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.util.HibernateUtil;
import it.univaq.cdvd.util.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
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
     *
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
     * Metodo per inserire un utente nel DB
     *
     * @param username
     * @param email
     * @param password
     * @throws Exception
     */
    public boolean saveUser(String username, String email, String password, Double saldoIniziale) {
        // Verifica se i campi obbligatori sono vuoti
        if (email.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty()) {
            System.err.println("Email, username e password non possono essere vuoti!");
            return false;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Controlla se l'utente esiste già
            Utente utenteEsistente = findUserByUsername(username);
            if (utenteEsistente != null) {
                System.err.println("Username già esistente.");
                return false;
            }

            // Crea un nuovo utente e salva
            Utente nuovoUtente = new Utente(username, email, password, saldoIniziale);
            Transaction transaction = session.beginTransaction();
            session.save(nuovoUtente); // Salva il nuovo utente
            transaction.commit();
            return true; // Salvataggio riuscito
        } catch (ConstraintViolationException e) {
            // Gestione della violazione del vincolo di unicità
            System.err.println("Errore: Username già esistente. " + e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    public ArrayList<Transazione> getTransazione() {
//        Session session = null;
//        Utente utente = SessionManager.getInstance().getUtente();
//        try {
//
//
//        }
//    }

}
