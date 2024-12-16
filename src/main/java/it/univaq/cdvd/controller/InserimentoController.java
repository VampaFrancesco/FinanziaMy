package it.univaq.cdvd.controller;

import it.univaq.cdvd.dao.TransazioneDAO;
import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.model.Transazione;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class InserimentoController {


    @FXML public AnchorPane anchorInserimento = new AnchorPane();
    @FXML public Label text = new Label();
    @FXML public Label importo = new Label();
    @FXML public Label labelCausale = new Label();
    @FXML public TextField causale = new TextField();
    @FXML public TextField inserisciImporto = new TextField();
    @FXML public Button inserisci = new Button();
    @FXML public ComboBox<String> categoriaList = new ComboBox<>();
    @FXML public DatePicker data = new DatePicker();


    TransazioneDAO tdao = new TransazioneDAO();
    public Categoria casa = new Categoria(1L,"casa");
    public Alert alert = new Alert(Alert.AlertType.INFORMATION);


    @FXML public void initialize() {
        text.setAlignment(Pos.CENTER);
        importo.setAlignment(Pos.CENTER);
        Categoria casa = new Categoria("casa");
        Categoria spesa = new Categoria("spesa");
        Categoria viaggio = new Categoria("viaggio");
        categoriaList.setItems(FXCollections.observableArrayList(casa.getNome(), spesa.getNome(), viaggio.getNome()));
    }

    /**
     * Metodo che, al click del pulsante sul bottone "Inserisci", permette di salvare una transazione all'interno del database richiamando il metodo save in TransazioneDAO
     * @param event
     * @throws Exception
     */
    @FXML public void inserisciTransazione(ActionEvent event) throws Exception{
        try {

            if(importo.getText().isEmpty() || causale.getText().isEmpty() || categoriaList.getValue() == null || data.getValue() == null){
                showAlert("Errore", "Compila tutti i campi obbligatoriamente", Alert.AlertType.ERROR);
                return;
            }

            Transazione tx = new Transazione();

            tx.setId(1L);
            tx.setImporto(inserisciImporto.getText().isEmpty() ? 0 : Double.parseDouble(inserisciImporto.getText()));
            tx.setCausale(causale.getText());
            tx.setNomeCategoria(categoriaList.getValue());
            tx.setData(data.getValue());
            tx.setCategoria(casa);
            System.out.println(tx);

            if(tdao.save(tx)){
                showAlert("Transazione inserita", "Transazione inserita con successo", Alert.AlertType.INFORMATION);
            }else{
                showAlert("Transazione non inserita", "Transazione non inserita", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Errore","Importo non valido", Alert.AlertType.ERROR);
        }
        catch (Exception e){
            showAlert("Si è verificato un errore", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    /**
     * Permette di visualizzare un alert se viene sollevata qualche eccezione
     * @param title titolo dell'alert
     * @param message messaggio da mostrare
     * @param type tipo enum del messaggio (INFORMATION,ERROR...)
     */
    public void showAlert(String title, String message, Alert.AlertType type){
        alert.setAlertType(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

}



