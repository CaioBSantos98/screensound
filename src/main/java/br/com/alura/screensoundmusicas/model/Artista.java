package br.com.alura.screensoundmusicas.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "artistas")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Enumerated(EnumType.STRING)
    private CategoriaArtista categoria;

    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL)
    private List<Musica> musicas;

    public Artista() {}

    public Artista(String nome, String categoria) {
        this.nome = nome;
        this.categoria = CategoriaArtista.fromString(categoria);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CategoriaArtista getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaArtista categoria) {
        this.categoria = categoria;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        musicas.forEach(m -> m.setArtista(this));
        this.musicas = musicas;
    }

    @Override
    public String toString() {
        return "Artista{" +
                "nome='" + nome + '\'' +
                ", categoria=" + categoria +
                '}';
    }
}
