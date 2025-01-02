package it.univaq.cdvd.controller;

import it.univaq.cdvd.dao.TransazioneDAO;
import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.model.Utente;
import it.univaq.cdvd.util.SessionManager;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
//import jdk.internal.org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class HomeController {

    @FXML
    public MenuBar menu = new MenuBar();
    @FXML
    public Button login = new Button();
    @FXML
    public Label saldo = new Label();
    @FXML
    public TableView<Transazione> tabellaTransazioni = new TableView<>();
    @FXML
    public MenuItem logout = new MenuItem();
    @FXML
    public MenuItem modificaAccount = new MenuItem();
    @FXML
    public MenuItem nuovaTransazione = new MenuItem();
    @FXML
    public MenuItem eliminaTransazione = new MenuItem();
    @FXML
    public MenuItem elencoTransazioni = new MenuItem();
    @FXML
    public MenuItem casa = new MenuItem();
    @FXML
    public MenuItem saluteBenessere = new MenuItem();
    @FXML
    public MenuItem ciboSpesa = new MenuItem();
    @FXML
    public MenuItem bancaFinanza = new MenuItem();
    @FXML
    public MenuItem motoriTrasporti = new MenuItem();
    @FXML
    public MenuItem varie = new MenuItem();
    @FXML
    public MenuItem aggiungiCategoria = new MenuItem();
    @FXML
    public MenuItem eliminaCategoria = new MenuItem();
    @FXML
    public MenuItem graficoTorta = new MenuItem();
    @FXML
    public MenuItem istogramma = new MenuItem();
    @FXML
    public MenuItem graficoBarre = new MenuItem();
    @FXML
    public MenuItem graficoLinee = new MenuItem();
    @FXML
    public MenuItem creaReport = new MenuItem();
    @FXML
    public MenuItem info = new MenuItem();
    @FXML
    private TableColumn<Transazione, String> categoriaColumn;
    @FXML
    private TableColumn<Transazione, Double> importoColumn;
    @FXML
    private TableColumn<Transazione, LocalDate> dataColumn;
    @FXML
    private TableColumn<Transazione, String> causaleColumn;

SessionManager session = SessionManager.getInstance();
TransazioneDAO transazioneDAO = new TransazioneDAO();


    @FXML
    public void initialize() {
        // Lega i valori alle proprietà della classe Transazione
        categoriaColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty((cellData.getValue().getCategoria().getNome())));

        importoColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getImporto()));

        dataColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getData()));

        causaleColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCausale()));

        // Popola la tabella con i dati dell'utente corrente
        popolaTabellaTransazioni();
    }
    @FXML
    public void nuovaTransazioneOnAction(ActionEvent event) {

        nuovaTransazione.setOnAction(evento -> {
            try {
                apriDialog();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void apriDialog() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/inserimento.fxml"));
            Parent root = loader.load();

            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Nuova Transazione");
            dialog.getDialogPane().setContent(root);

            dialog.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
            dialog.getDialogPane().setMinWidth(Region.USE_COMPUTED_SIZE);

            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

            dialog.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void handleLogoutClick (ActionEvent event) {
    session.clearSession();
    System.out.println("utente logout" + session.getUtente());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent root = loader.load();

            // Ottieni la finestra corrente
            Stage stage = (Stage) tabellaTransazioni.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showSaldo () {
        Utente utente = session.getUtente();
        String saldoUtente = Double.toString(utente.getSaldo());
        saldo.setText("$ " + saldoUtente);
    }
    private void popolaTabellaTransazioni() {
        // Ottieni l'utente dalla sessione
        Utente utenteCorrente = SessionManager.getInstance().getUtente();

        if (utenteCorrente != null) {
            // Ottieni le transazioni dal database per l'utente
            List<Transazione> transazioni = transazioneDAO.findTransactionByUser(utenteCorrente);
            // Converti la lista in ObservableList e imposta nella tabella
            ObservableList<Transazione> transazioniObservable = FXCollections.observableArrayList(transazioni);
            tabellaTransazioni.setItems(transazioniObservable);
        }
    }

}