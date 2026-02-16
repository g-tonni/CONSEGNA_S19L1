package giadatonni.CONSEGNA_S19L1.entities;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "dipendenti")
public class Dipendente {

    @Id
    @GeneratedValue
    @Column(name = "dipendente_id")
    private UUID dipendenteId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private String email;

    @Column(name = "foto_profilo", nullable = false)
    private String fotoProfilo;

    public Dipendente() {}

    public Dipendente(String username, String nome, String cognome, String email, String fotoProfilo) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.fotoProfilo = fotoProfilo;
    }

    public UUID getDipendenteId() {
        return dipendenteId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFotoProfilo() {
        return fotoProfilo;
    }

    public void setFotoProfilo(String fotoProfilo) {
        this.fotoProfilo = fotoProfilo;
    }

    @Override
    public String toString() {
        return "Dipendente{" +
                "dipendenteId=" + dipendenteId +
                ", username='" + username + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", fotoProfilo='" + fotoProfilo + '\'' +
                '}';
    }
}
