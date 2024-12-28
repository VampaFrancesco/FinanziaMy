package it.univaq.cdvd.model;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Utente") // Nome della tabella nel database
public class Utente {

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false) // Non può essere null
    private String password;

    @Id
    @Column(name = "username", nullable = false, unique = true) // Non può essere null e deve essere univoco
    private String username;


    @Id
    @Column(name = "saldo", nullable = false) // Non può essere null e deve essere univoco
    private Double saldo = 0.0;


    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transazione> transazioni = new ArrayList<>();


    public Utente() {
    }

    public Utente(String username, String email, String password, Double saldo) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.saldo = saldo;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<Transazione> getTransazioni() {
        return transazioni;
    }

    public void setTransazioni(List<Transazione> transazioni) {
        this.transazioni = transazioni;
    }


    @Override
    public String toString() {
        return "User{" +

                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}