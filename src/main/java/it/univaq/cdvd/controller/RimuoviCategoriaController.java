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
import java.util.Optional;

public class RimuoviCategoriaController {

    @FXML
    public TableView<Categoria> tabellaCategorie;
    @FXML
    private TableColumn<Categoria, String> nomeCategoriaTabella;
    @FXML
    private TableColumn<Categoria, String> descCategoriaTabella;

    private ObservableList<Categoria> listaCategorie;

    public ShowAlert sa = new ShowAlert();

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
        descCategoriaTabella.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
    }

    private void caricaCategoria() {
        try {
            // Recupera le transazioni legate all'utente dal DAO
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            List<Categoria> categorie = categoriaDAO.findByUtente(SessionManager.getInstance().getUtente().getUsername());

            // Converte la lista in un ObservableList
            listaCategorie = FXCollections.observableArrayList(categorie);

            // Associa i dati alla TableView
            tabellaCategorie.setItems(listaCategorie);
        } catch (Exception e) {
            e.printStackTrace();
            sa.showAlert("Errore", "Errore durante il caricamento delle transazioni: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

        @FXML
        public void eliminaCategoriaTabella (ActionEvent event){
            Categoria categoriaSelezionata = tabellaCategorie.getSelectionModel().getSelectedItem();
            if (categoriaSelezionata == null) {
                sa.showAlert("Errore", "Seleziona una categoria", Alert.AlertType.WARNING);
                return;
            }

            // Crea una finestra di dialogo di conferma
            Alert confermaAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confermaAlert.setTitle("Conferma Eliminazione");
            confermaAlert.setHeaderText("Sei sicuro di voler eliminare questa categoria?");
            confermaAlert.setContentText("L'operazione non può essere annullata.");

            // Mostra la finestra di dialogo e aspetta la risposta dell'utente
            Optional<ButtonType> result = confermaAlert.showAndWait();

            // Se l'utente conferma (clicca "OK"), procedi con l'eliminazione
            if (result.isPresent() && result.get() == ButtonType.OK) {
                CategoriaDAO categoriaDAO = new CategoriaDAO();
                boolean eliminata = categoriaDAO.eliminaCategoria(categoriaSelezionata.getNome(), SessionManager.getInstance().getUtente());

                if (eliminata) {
                    listaCategorie.remove(categoriaSelezionata);
                    sa.showAlert("Successo", "Categoria eliminata", Alert.AlertType.INFORMATION);
                } else {
                    sa.showAlert("Errore", "Errore durante l'eliminazione della categoria", Alert.AlertType.ERROR);
                }
            } else {
                // Se l'utente annulla (clicca "Annulla"), non fare nulla
                sa.showAlert("Operazione annullata", "L'eliminazione della categoria è stata annullata.", Alert.AlertType.INFORMATION);
            }
        }
    }


