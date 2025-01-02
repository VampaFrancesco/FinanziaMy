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

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Categoria> categoria = new ArrayList<>();

    public Utente() {
    }

    public Utente(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
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


    @Override
    public String toString() {
        return "User{" +

                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}