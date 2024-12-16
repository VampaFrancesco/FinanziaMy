package it.univaq.cdvd.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "transazione")
public class Transazione {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double importo;

    @Column(nullable = false)
    private String causale;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private String nome_categoria;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_categoria", referencedColumnName = "id")
    private Categoria categoria;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public String getCausale() {
        return causale;
    }

    public void setCausale(String causale) {
        this.causale = causale;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getNomeCategoria() {
        return this.nome_categoria;
    }

    public void setNomeCategoria(String nomeC) {
        this.nome_categoria = nomeC;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Transazione{" +
                "id=" + id +
                ", importo=" + importo +
                ", causale='" + causale + '\'' +
                ", data=" + data +
                ", categoria='" + nome_categoria + '\'' +
                '}';
    }
}

