package it.univaq.cdvd.controller;

import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import jdk.internal.org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.Objects;

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

SessionManager session = SessionManager.getInstance();

    @FXML public void initialize(){
        nuovaTransazione.setOnAction(this::nuovaTransazioneOnAction);
        eliminaTransazione.setOnAction(this::eliminaTransazione);
    }

    @FXML public void nuovaTransazioneOnAction(ActionEvent event) {
            System.out.println("nuovaTransazione");
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
    public void eliminaTransazione(ActionEvent event)  {
        System.out.println("eliminaTransazione");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cancellazione.fxml"));
                Parent root = loader.load();

                Dialog<Void> dialog = new Dialog<>();
                dialog.setTitle("Elimina Transazione");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/landing.fxml"));
            Parent root = loader.load();

            // Ottieni la finestra corrente e imposta la nuova scena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Pagina Iniziale");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void reportButtonOnAction(ActionEvent event) {

        try {
            // Carica il file report.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/report.fxml"));
            Parent root = loader.load();

            // Ottieni lo stage corrente
            Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

            // Imposta la nuova scena con il contenuto del file FXML
            stage.setScene(new Scene(root));

            // Mostra la scena
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il caricamento della pagina report.fxml", e);
        }
    }
}