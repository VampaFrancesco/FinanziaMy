package it.univaq.cdvd.controller;

import it.univaq.cdvd.dao.TransazioneDAO;
import it.univaq.cdvd.dao.UtenteDAO;
import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.util.HibernateUtil;
import it.univaq.cdvd.util.SessionManager;
import it.univaq.cdvd.util.ShowAlert;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.query.Query;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CancellazioneController {

    @FXML
    public TableView<Transazione> lista;
    @FXML
    private TableColumn<Transazione, String> codiceTr;
    @FXML
    private TableColumn<Transazione, Double> importo;
    @FXML
    private TableColumn<Transazione, String> causale;
    @FXML
    private TableColumn<Transazione, LocalDate> data;
    @FXML
    private TableColumn<Transazione, String> categoria;
    @FXML
    private Button elimina;

    private ShowAlert sa = new ShowAlert();

    private Utente utente;

    private ObservableList<Transazione> transazioniUtente;


    @FXML
    public void initialize() {


        try {
            // Recupera l'utente corrente dalla sessione
            utente = SessionManager.getInstance().getUtente();

            // Configura le colonne della TableView
            configuraColonne();

            // Carica le transazioni dal database
            caricaTransazioni();
        } catch (Exception e) {
            e.printStackTrace();
            sa.showAlert("Errore", "Errore durante l'inizializzazione: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void configuraColonne() {
        // Collega le colonne ai campi del modello Transazione
        codiceTr.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        importo.setCellValueFactory(new PropertyValueFactory<>("importo"));
        causale.setCellValueFactory(new PropertyValueFactory<>("causale"));
        data.setCellValueFactory(new PropertyValueFactory<>("data"));
        categoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria().getNome()));
    }

    private void caricaTransazioni() {
        try {
            // Recupera le transazioni legate all'utente dal DAO
            TransazioneDAO transazioneDAO = new TransazioneDAO();
            List<Transazione> transazioni = transazioneDAO.findTransactionByUser(SessionManager.getInstance().getUtente());

            // Converte la lista in un ObservableList
            transazioniUtente = FXCollections.observableArrayList(transazioni);

            // Associa i dati alla TableView
            lista.setItems(transazioniUtente);
        } catch (Exception e) {
            e.printStackTrace();
            sa.showAlert("Errore", "Errore durante il caricamento delle transazioni: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void cancella(ActionEvent event) {
        TransazioneDAO transazioneDAO = new TransazioneDAO();

        Transazione transazione = lista.getSelectionModel().getSelectedItem();
        System.out.println("Transazione selezionata: " + transazione);

        if (transazione == null) {
            sa.showAlert("Errore", "Seleziona una transazione", Alert.AlertType.WARNING);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferma eliminazione");
            alert.setHeaderText("Conferma eliminazione");
            alert.setContentText("Sei sicuro di voler eliminare la transazione selezionata?");
            ButtonType buttonTypeSi = new ButtonType("Si");
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeSi) {
                    boolean eliminata = transazioneDAO.eliminaTransazione(transazione.getId());
                    if (eliminata) {
                        transazioniUtente.remove(transazione);
                        sa.showAlert("Successo", "Transazione eliminata", Alert.AlertType.INFORMATION);
                    } else {
                        sa.showAlert("Errore", "Errore durante l'eliminazione della transazione", Alert.AlertType.ERROR);
                    }
                }
            });
        }
    }

    public void handleAnnulClick(ActionEvent actionEvent) {
        try {
            // Carica il file auth.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
            Parent root = loader.load();

            // Ottieni la finestra corrente e imposta la nuova scena
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Pagina Home");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
