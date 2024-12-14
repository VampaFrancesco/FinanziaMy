package it.univaq.cdvd.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
public class HomeController {

    @FXML public MenuBar menu = new MenuBar();
    @FXML public Button login = new Button();
    @FXML public Label saldo = new Label();
    @FXML public TableView tabellaTransazioni = new TableView();
    @FXML public MenuItem logout = new MenuItem();
    @FXML public MenuItem modificaAccount = new MenuItem();
    @FXML public MenuItem nuovaTransazione = new MenuItem();
    @FXML public MenuItem eliminaTransazione = new MenuItem();
    @FXML public MenuItem elencoTransazioni = new MenuItem();
    @FXML public MenuItem casa = new MenuItem();
    @FXML public MenuItem saluteBenessere = new MenuItem();
    @FXML public MenuItem ciboSpesa = new MenuItem();
    @FXML public MenuItem bancaFinanza = new MenuItem();
    @FXML public MenuItem motoriTrasporti = new MenuItem();
    @FXML public MenuItem varie = new MenuItem();
    @FXML public MenuItem aggiungiCategoria = new MenuItem();
    @FXML public MenuItem eliminaCategoria = new MenuItem();
    @FXML public MenuItem graficoTorta = new MenuItem();
    @FXML public MenuItem istogramma = new MenuItem();
    @FXML public MenuItem graficoBarre = new MenuItem();
    @FXML public MenuItem graficoLinee = new MenuItem();
    @FXML public MenuItem creaReport = new MenuItem();
    @FXML public MenuItem info = new MenuItem();

}
