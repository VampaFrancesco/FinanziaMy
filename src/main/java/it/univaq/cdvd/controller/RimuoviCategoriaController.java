package it.univaq.cdvd.controller;

import it.univaq.cdvd.dao.CategoriaDAO;
import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.model.Transazione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RimuoviCategoriaController {

    @FXML private TableView<Categoria> lista;
    @FXML private TableColumn<Categoria, String> nome;


}
