package it.univaq.cdvd.controller;

import it.univaq.cdvd.dao.TransazioneDAO;
import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CancellazioneController {

    @FXML
    public ListView<String> lista;
    Alert alert;

    Utente utente = SessionManager.getInstance().getUtente();
    ObservableList<Transazione> transazioniUtente = (ObservableList<Transazione>) utente.getTransazioni();

    public void initialize() {
        transazioniUtente.clear();
        caricaTransazione();
    }

    public void caricaTransazione() {
        ObservableList<String> elementi = FXCollections.observableArrayList();
        for (Transazione transazione : transazioniUtente) {
            elementi.add(transazione.getCausale());
        }
        lista.setItems(elementi);
    }


    /**
     * Permette di visualizzare un alert se viene sollevata qualche eccezione
     *
     * @param title   titolo dell'alert
     * @param message messaggio da mostrare
     * @param type    tipo enum del messaggio (INFORMATION,ERROR...)
     */
    public void showAlert(String title, String message, Alert.AlertType type) {
        alert.setAlertType(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
