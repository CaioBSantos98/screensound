package br.com.alura.screensoundmusicas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "musicas", uniqueConstraints = {@UniqueConstraint(columnNames = {"nome", "artista_id"})})
public class Musica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToOne
    private Artista artista;

    public Musica() {}

    public Musica(String nome, Artista artista) {
        this.nome = nome;
        this.artista = artista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    @Override
    public String toString() {
        return "Musica{" +
                "nome='" + nome + '\'' +
                ", artista=" + artista +
                '}';
    }
}
