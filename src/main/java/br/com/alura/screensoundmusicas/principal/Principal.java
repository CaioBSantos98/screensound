package br.com.alura.screensoundmusicas.principal;

import br.com.alura.screensoundmusicas.model.Artista;
import br.com.alura.screensoundmusicas.model.Musica;
import br.com.alura.screensoundmusicas.repository.ArtistaRepository;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner leitor = new Scanner(System.in);
    private ArtistaRepository repositorio;

    public Principal(ArtistaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                    *** Screen Sound Músicas ***
                    1- Cadastrar artistas
                    2- Cadastrar músicas
                    3- Listar artistas
                    4- Listar todas as músicas por artista
                    5- Buscar música por artista
                    
                    0- Sair
                    """;
            System.out.println(menu);
            opcao = leitor.nextInt();
            leitor.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarArtistas();
                    break;
                case 4:
                    listarMusicasPorArtista();
                    break;
                case 5:
                    buscarMusicaPorArtista();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private void cadastrarArtistas() {
        var cadastrarOutro = "s";

        while(!cadastrarOutro.equals("n")) {
            System.out.println("Informe o nome do artista a ser cadastrado: ");
            var nomeArtista = leitor.nextLine();
            System.out.println("Informe o tipo desse artista: (solo, dupla, banda)");
            var tipoArtista = leitor.nextLine();

            try {
                Artista artista = new Artista(nomeArtista, tipoArtista);
                repositorio.save(artista);
                System.out.println("Artista cadastrado com sucesso!");
            } catch (Exception e) {
                throw new RuntimeException("Artista não cadastrado!", e);
            }

            System.out.println("Cadastrar outro artista? (S/N)");
            cadastrarOutro = leitor.nextLine().toLowerCase();
        }
    }

    private void listarArtistas(){
        List<Artista> artistas = repositorio.findAll();
        artistas.forEach(System.out::println);
    }

    private void cadastrarMusicas() {
        listarArtistas();
        System.out.println("Escolha um artista para cadastrar uma música: ");
        var nomeArtista = leitor.nextLine();
        Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nomeArtista);

        if (artista.isPresent()) {
            var cadastrarOutro = "s";
            while(!cadastrarOutro.equals("n")) {
                List<Musica> musicasDoArtista = repositorio.buscarMusicasPorArtista(artista.get());
                System.out.println("Digite o nome da música a ser cadastrada: ");
                var nomeMusica = leitor.nextLine();
                Musica musica = new Musica(nomeMusica, artista.get());
                musicasDoArtista.add(musica);
                try {
                    artista.get().setMusicas(musicasDoArtista);
                    repositorio.save(artista.get());
                } catch (DataIntegrityViolationException e) {
                    System.out.println("Música já cadastrada para esse artista.");
                }
                System.out.println("Cadastrar outra musica? (S/N)");
                cadastrarOutro = leitor.nextLine().toLowerCase();
            }
        } else {
            System.out.println("Artista não encontrado");
        }
    }

    public void listarMusicasPorArtista() {
        listarArtistas();
        System.out.println("Escolha um artista para listar todas as suas música: ");
        var nomeArtista = leitor.nextLine();
        Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nomeArtista);

        if (artista.isPresent()) {
            List<Musica> musicasDoArtista = repositorio.buscarMusicasPorArtista(artista.get());
            musicasDoArtista.forEach(m -> System.out.println(m.getNome()));
        } else {
            System.out.println("Artista não encontrado");
        }
    }

    public void buscarMusicaPorArtista() {
        listarArtistas();
        System.out.println("Escolha um artista para buscar uma de suas músicas: ");
        var nomeArtista = leitor.nextLine();
        Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nomeArtista);

        if (artista.isPresent()) {
            System.out.println("Digite uma musica do artista selecionado para busca: ");
            var nomeMusica = leitor.nextLine();
            List<Musica> musicas = repositorio.buscarMusicaPorArtista(nomeMusica, artista.get());

            if(!musicas.isEmpty()) {
                musicas.forEach(System.out::println);
            } else {
                System.out.println("Musica não encontrada.");
            }

        } else {
            System.out.println("Artista não encontrado");
        }
    }
}
