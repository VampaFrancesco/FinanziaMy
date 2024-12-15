package it.univaq.cdvd.controller;

import it.univaq.cdvd.dao.CategoriaDAO;
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

    public void inserisciCategoria(String nomeCategoria) {
        try {
            if (nomeCategoria == null || nomeCategoria.trim().isEmpty()) {
                showAlert("Errore", "Il nome della categoria non può essere vuoto.", Alert.AlertType.ERROR);
                return;
            }

            CategoriaDAO categoriaDAO = new CategoriaDAO();
            Categoria nuovaCategoria = new Categoria(null, nomeCategoria);

            if (categoriaDAO.save(nuovaCategoria)) {
                showAlert("Categoria inserita", "Categoria inserita con successo.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Errore", "Impossibile inserire la categoria.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Si è verificato un errore", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML AnchorPane anchorInserimento = new AnchorPane();
    @FXML Label text = new Label();
    @FXML Label importo = new Label();
    @FXML Label labelCausale = new Label();
    @FXML TextField causale = new TextField();
    @FXML TextField inserisciImporto = new TextField();
    @FXML Button inserisci = new Button();
    @FXML ComboBox<String> categoriaList = new ComboBox<>();
    @FXML DatePicker data = new DatePicker();

    
    TransazioneDAO tdao = new TransazioneDAO();
    Categoria casa = new Categoria(1L,"casa");
    Alert alert = new Alert(Alert.AlertType.INFORMATION);


    @FXML public void initialize() {
        text.setAlignment(Pos.CENTER);
        importo.setAlignment(Pos.CENTER);
        Categoria casa = new Categoria("casa");
        Categoria spesa = new Categoria(2L,"spesa");
        Categoria viaggio = new Categoria(3L,"viaggio");
        categoriaList.setItems(FXCollections.observableArrayList(casa.getNome(), spesa.getNome(), viaggio.getNome()));
    }
    

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
            tx.setData(data.getValue());// Set the current date in MariaDB-compatible format
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
    public void showAlert(String title, String message, Alert.AlertType type){
        alert.setAlertType(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

}



