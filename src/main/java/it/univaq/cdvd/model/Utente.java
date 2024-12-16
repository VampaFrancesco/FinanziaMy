package it.univaq.cdvd.model;

import jakarta.persistence.*;

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

    public Utente() {
    }

    public Utente(String username, String password, String email) {
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