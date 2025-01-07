package it.univaq.cdvd.controller;

import it.univaq.cdvd.dao.CategoriaDAO;
import it.univaq.cdvd.dao.TransazioneDAO;
import it.univaq.cdvd.dao.UtenteDAO;
import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.model.Transazione;
import it.univaq.cdvd.util.SessionManager;
import it.univaq.cdvd.util.ShowAlert;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.hibernate.Session;

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
    CategoriaDAO cdao = new CategoriaDAO();
    ShowAlert sa = new ShowAlert();
    UtenteDAO utenteDAO = new UtenteDAO();



    @FXML public void initialize() {
        text.setAlignment(Pos.CENTER);
        labelCausale.setAlignment(Pos.CENTER);
        importo.setAlignment(Pos.CENTER);
        if (System.getProperty("MYAPP_ENV").equals("test")) {
            Categoria c1 = new Categoria();
            Categoria c2 = new Categoria();
            Categoria c3 = new Categoria();
            c1.setNome("Cibo");
            c2.setNome("Svago");
            c3.setNome("Lavoro");
            categoriaList.setItems(FXCollections.observableArrayList(c1.getNome(), c2.getNome(), c3.getNome()));
        }else{
            categoriaList.setItems(cdao.listaCategoria());
        }
    }

    /**
     * Metodo che, al click del pulsante sul bottone "Inserisci", permette di salvare una transazione all'interno del database richiamando il metodo save in TransazioneDAO
     * @param event
     * @throws Exception
     */
    @FXML public void inserisciTransazione(ActionEvent event) throws Exception{
        try {

            if(importo.getText().isEmpty() || causale.getText().isEmpty() || categoriaList.getValue() == null || data.getValue() == null){
                sa.showAlert("Errore", "Compila tutti i campi obbligatoriamente", Alert.AlertType.ERROR);
                return;
            }

            Transazione tx = new Transazione();

            tx.setId(1L);
            tx.setImporto(inserisciImporto.getText().isEmpty() ? 0 : Double.parseDouble(inserisciImporto.getText()));
            tx.setCausale(causale.getText());
            tx.setNomeCategoria(categoriaList.getValue());
            tx.setData(data.getValue());
            tx.setCategoria(cdao.cercaCategoria(categoriaList.getValue()));
            tx.setUtente(SessionManager.getInstance().getUtente());

            //Aggiorna saldo utente
            utenteDAO.updateSaldo(SessionManager.getInstance().getUtente(), Double.parseDouble(inserisciImporto.getText()) );


            System.out.println(tx);

            if(tdao.save(tx)){
                sa.showAlert("Transazione inserita", "Transazione inserita con successo", Alert.AlertType.INFORMATION);
            }else{
                sa.showAlert("Transazione non inserita", "Transazione non inserita", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            sa.showAlert("Errore","Importo non valido", Alert.AlertType.ERROR);
        }
        catch (Exception e){
            sa.showAlert("Si è verificato un errore", e.getMessage(), Alert.AlertType.ERROR);
        }

    }


}



