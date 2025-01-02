package it.univaq.cdvd.controller;

import it.univaq.cdvd.model.Transazione;
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

import java.io.IOException;

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


    @FXML public void initialize(){
        nuovaTransazione.setOnAction(this::nuovaTransazioneOnAction);
        aggiungiCategoria.setOnAction(this::aggiuntaCategoriaOnAction);
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

    @FXML
    public void aggiuntaCategoriaOnAction(ActionEvent event) {

        aggiungiCategoria.setOnAction(evento -> {
            try {
                apriDialogCategoria();
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

    public void apriDialogCategoria() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/aggiuntacategoria.fxml"));
            Parent root = loader.load();

            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Aggiungi Categoria");
            dialog.getDialogPane().setContent(root);

            dialog.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
            dialog.getDialogPane().setMinWidth(Region.USE_COMPUTED_SIZE);

            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

            dialog.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}