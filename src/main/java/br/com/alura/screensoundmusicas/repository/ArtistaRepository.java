package br.com.alura.screensoundmusicas.repository;

import br.com.alura.screensoundmusicas.model.Artista;
import br.com.alura.screensoundmusicas.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    Optional<Artista> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT m FROM Artista a JOIN Musica m ON a = m.artista WHERE a = :artista")
    List<Musica> buscarMusicasPorArtista(Artista artista);

    @Query("SELECT m FROM Artista a JOIN Musica m ON a = m.artista WHERE a = :artista AND m.nome ILIKE %:nomeMusica%")
    List<Musica> buscarMusicaPorArtista(String nomeMusica, Artista artista);
}
