public class Movie {
    private Long id;
    private String titulo;
    private String diretor;
    private int anoDeLancamento;
    private String genero;
    private double nota;

    public Movie(String titulo, String diretor, int anoDeLancamento, String genero, double nota) {
        this.titulo = titulo;
        this.diretor = diretor;
        this.anoDeLancamento = anoDeLancamento;
        this.genero = genero;
        this.nota = nota;
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDiretor() { return diretor; }
    public void setDiretor(String diretor) { this.diretor = diretor; }
    public int getAnoDeLancamento() { return anoDeLancamento; }
    public void setAnoDeLancamento(int anoDeLancamento) { this.anoDeLancamento = anoDeLancamento; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }

    @Override
    public String toString() {
        return String.format("[%d] %s (Dir: %s, Ano: %d, Gênero: %s, Nota: %.1f)",
                id, titulo, diretor, anoDeLancamento, genero, nota);
    }
}