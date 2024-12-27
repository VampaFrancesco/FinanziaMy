package it.univaq.cdvd.test_logica_business;

import it.univaq.cdvd.controller.RegistrazioneController;
import it.univaq.cdvd.dao.UtenteDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrazioneControllerTest {

    // test per verificare che non si possano inserire campi vuoti nella registrazione
    @Test
    public void testUnvalidRegistration() {
        UtenteDAO utenteDAO = new UtenteDAO();
        assertFalse( utenteDAO.saveUser("", "", "",0.00));
    }

    // test che verifica che non si possano inserire utenti già esistenti
    @Test
    public void testExistingUser() {
        UtenteDAO utenteDAO = new UtenteDAO();
        assertFalse(utenteDAO.saveUser("prova", "x", "x",0));

    }
}