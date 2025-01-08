package it.univaq.cdvd.controller;

import it.univaq.cdvd.dao.CategoriaDAO;
import it.univaq.cdvd.dao.TransazioneDAO;
import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.util.HibernateUtil;
import it.univaq.cdvd.util.SessionManager;
import it.univaq.cdvd.util.ShowAlert;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.util.List;

public class ModificaController {

    @FXML AnchorPane anchorModifica = new AnchorPane();
    @FXML public TableView<Transazione> lista;
    @FXML public TableColumn<Transazione, String> codiceTr;
    @FXML public TableColumn<Transazione, Double> importo;
    @FXML public TableColumn<Transazione, String> causale;
    @FXML public TableColumn<Transazione, LocalDate> data;
    @FXML public TableColumn<Transazione, String> categoria;
    @FXML public ComboBox<String> categoriaList = new ComboBox<>();
    @FXML public TextField nuovoImporto = new TextField();
    @FXML public TextField nuovoCausale = new TextField();
    @FXML public DatePicker nuovoData = new DatePicker();
    @FXML public Button modifica = new Button();
    @FXML public Label modificaTransazionelabel = new Label();

    CategoriaDAO cdao = new CategoriaDAO();
    ShowAlert sa = new ShowAlert();


    @FXML public void initialize() {
        modificaTransazionelabel.setAlignment(Pos.CENTER);
        if (System.getProperty("MYAPP_ENV").equals("test")) {
            Categoria c1 = new Categoria();
            Categoria c2 = new Categoria();
            Categoria c3 = new Categoria();
            c1.setNome("Cibo");
            c2.setNome("Svago");
            c3.setNome("Lavoro");
            categoriaList.setItems(FXCollections.observableArrayList(c1.getNome(), c2.getNome(), c3.getNome()));

            configuraColonne();
        }else{
            configuraColonne();
            caricaTransazioni();
            categoriaList.setItems(cdao.listaCategoria());
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
            ObservableList<Transazione> transazioniUtente = FXCollections.observableArrayList(transazioni);

            // Associa i dati alla TableView
            lista.setItems(transazioniUtente);
        } catch (Exception e) {
            e.printStackTrace();
            sa.showAlert("Errore", "Errore durante il caricamento delle transazioni: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML public void modificaTransazione() {

        try {
            Transazione transazioneSelezionata = lista.getSelectionModel().getSelectedItem();
            if (transazioneSelezionata == null) {
                sa.showAlert("Errore", "Nessuna transazione selezionata", Alert.AlertType.WARNING);
                return;
            }
            if (nuovoImporto.getText().isEmpty() || nuovoCausale.getText().isEmpty() || nuovoData.getValue() == null) {
                sa.showAlert("Errore", "Tutti i campi devono essere compilati.", Alert.AlertType.WARNING);
                return;
            }

            TransazioneDAO transazioneDAO = new TransazioneDAO();

            transazioneSelezionata.setId(lista.getSelectionModel().getSelectedItem().getId());
            transazioneSelezionata.setUtente(SessionManager.getInstance().getUtente());
            transazioneSelezionata.setImporto(Double.parseDouble(nuovoImporto.getText()));
            transazioneSelezionata.setCausale(nuovoCausale.getText().trim());
            transazioneSelezionata.setData(nuovoData.getValue());
            transazioneSelezionata.setCategoria(lista.getSelectionModel().getSelectedItem().getCategoria());

            transazioneDAO.modifica(transazioneSelezionata);
            sa.showAlert("Successo", "Transazione modificata con successo", Alert.AlertType.INFORMATION);
            caricaTransazioni();
        } catch (NumberFormatException e) {
            sa.showAlert("Errore","Il campo importo deve contenere un valore numerico: "+e.getMessage(), Alert.AlertType.ERROR);
        }catch (Exception e) {
            sa.showAlert("Errore", "Errore durante la modifica della transazione: " + e.getMessage(), Alert.AlertType.ERROR);
        }

    }
}
