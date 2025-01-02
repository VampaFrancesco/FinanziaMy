package it.univaq.cdvd.dao;
import it.univaq.cdvd.model.Categoria;
import it.univaq.cdvd.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

public class CategoriaDAO {

    //per inserire le categoria nella combobox posso fare un metodo che mi torna una lista di categorie e prendere il nome, per quanto riguarda l'inserimento basta inserire solo il nome
    //e volendo posso associare ad ogni categoria un colore, si potrebbe fare.

    public boolean save(Categoria categoria) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(categoria);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) {
            }
            e.printStackTrace();
        }
        return false;
    }


    public ObservableList<String> listaCategoria() {
        ObservableList<String> categorieList = FXCollections.observableArrayList();
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query<Categoria> query = session.createQuery("from Categoria");
            List<Categoria> categorie = query.list();
            for(Categoria c : categorie){
                categorieList.add(c.getNome());
            };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return categorieList;
    }

    public Categoria cercaCategoria(String nome){
        Categoria categoria = null;
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query<Categoria> query = session.createQuery("from Categoria where nome = :nome");
            query.setParameter("nome", nome);
            categoria = query.uniqueResult();
        }catch(HibernateException e){
            throw new RuntimeException(e);
        }
        return categoria;
    }

}
