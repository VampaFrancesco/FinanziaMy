package it.univaq.cdvd.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "transazione")
public class Transazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double importo;

    @Column(nullable = false)
    private String casuale;

    @Column(nullable = false)
    private Date data;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCasuale() {
        return casuale;
    }

    public void setCasuale(String casuale) {
        this.casuale = casuale;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
