package br.com.alura.screensoundmusicas.model;

public enum CategoriaArtista {
    SOLO("solo"),
    DUPLA("dupla"),
    BANDA("banda");

    private String categoria;

    CategoriaArtista(String categoria) {
        this.categoria = categoria;
    }

    public static CategoriaArtista fromString(String text) {
        for (CategoriaArtista categoriaArtista : CategoriaArtista.values()) {
            if (categoriaArtista.categoria.equalsIgnoreCase(text)) {
                return categoriaArtista;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
