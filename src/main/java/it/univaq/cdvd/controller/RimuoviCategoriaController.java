package it.univaq.cdvd.controller;

import it.univaq.cdvd.dao.CategoriaDAO;
import it.univaq.cdvd.dao.TransazioneDAO;
import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.util.SessionManager;
import it.univaq.cdvd.util.ShowAlert;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class RimuoviCategoriaController {

    @FXML private TableView<Categoria> tabellaCategorie;
    @FXML private TableColumn<Categoria, String> nomeCategoriaTabella;

    private ObservableList<Categoria> listaCategorie;

    ShowAlert sa = new ShowAlert();

    @FXML
    public void initialize() {
        try {
            // Configura le colonne della TableView
            configuraColonne();

            // Carica le transazioni dal database
            caricaCategoria();
        } catch (Exception e) {
            e.printStackTrace();
            sa.showAlert("Errore", "Errore durante l'inizializzazione: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void configuraColonne() {
        // Collega le colonne ai campi del modello Categoria
        nomeCategoriaTabella.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }

    private void caricaCategoria() {
        try {
            // Recupera le transazioni legate all'utente dal DAO
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            List<Categoria> categorie = categoriaDAO.findAll();

            // Converte la lista in un ObservableList
            listaCategorie = FXCollections.observableArrayList(categorie);

            // Associa i dati alla TableView
            tabellaCategorie.setItems(listaCategorie);
        } catch (Exception e) {
            e.printStackTrace();
            sa.showAlert("Errore", "Errore durante il caricamento delle transazioni: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void rimuoviCategoria(MouseEvent event) {

        Categoria categorie = tabellaCategorie.getSelectionModel().getSelectedItem();
        if( categorie == null){
            sa.showAlert("Errore", "Seleziona una categoria", Alert.AlertType.WARNING);
        }

        CategoriaDAO categoriaDAO = new CategoriaDAO();

        boolean eliminata = categoriaDAO.eliminaCategoria(categorie.getId());
        if(eliminata){
            listaCategorie.remove(categorie);
            sa.showAlert("Successo", "Categoria eliminata", Alert.AlertType.INFORMATION);
        }else{
            sa.showAlert("Errore", "Errore durante l'eliminazione della categoria", Alert.AlertType.ERROR);
        }
    }
    @FXML
    private void gestisciClick(MouseEvent event) {
        // Verifica che l'evento sia un doppio clic
        if (event.getClickCount() == 2) {
            rimuoviCategoria(event);
        }
    }
}
