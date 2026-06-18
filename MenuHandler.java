import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MenuHandler {
    private final MovieRepository movieRepository;
    private final FavoritesService favoritesService;
    private final Scanner scanner;

    public MenuHandler(MovieRepository movieRepository, FavoritesService favoritesService) {
        this.movieRepository = movieRepository;
        this.favoritesService = favoritesService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            exibirMenu();
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1": cadastrarFilme(); break;
                case "2": listarFilmes(); break;
                case "3": buscarFilmePorId(); break;
                case "4": editarFilme(); break;
                case "5": removerFilme(); break;
                case "6": verFavoritos(); break;
                case "7": adicionarFavoritos(); break;
                case "8": removerDosFavoritos(); break;
                case "9": buscarPorTitulo(); break; // Bônus
                case "10": topFilmes(); break;      // Bônus
                case "11": filtrarPorGenero(); break; // Bônus
                case "0":
                    System.out.println("Encerrando o CineLog. Até a próxima!");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void exibirMenu() {
        System.out.println("\n=== CineLog - Menu Principal ===");
        System.out.println("1 Cadastrar filme");
        System.out.println("2 Listar todos os filmes");
        System.out.println("3 Buscar filme por ID");
        System.out.println("4 Editar filme");
        System.out.println("5 Remover filme");
        System.out.println("--- favoritos ---");
        System.out.println("6 Ver favoritos");
        System.out.println("7 Adicionar favorito(s)");
        System.out.println("8 Remover dos favoritos");
        System.out.println("--- extras ---");
        System.out.println("9 Buscar filme por Título");
        System.out.println("10 Top Filmes (ordenado por nota)");
        System.out.println("11 Filtrar por Gênero");
        System.out.println("0 Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void cadastrarFilme() {
        System.out.print("Título (obrigatório): ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Diretor (obrigatório): ");
        String diretor = scanner.nextLine().trim();

        if (titulo.isEmpty() || diretor.isEmpty()) {
            System.out.println("Erro: Título e Diretor são obrigatórios.");
            return;
        }

        System.out.print("Ano de Lançamento: ");
        int ano = lerInteiro();
        System.out.print("Gênero: ");
        String genero = scanner.nextLine().trim();
        System.out.print("Nota (0.0 a 10.0): ");
        double nota = lerDouble();

        Movie movie = new Movie(titulo, diretor, ano, genero, nota);
        movie = movieRepository.save(movie);
        System.out.println("Filme cadastrado com sucesso! ID gerado: " + movie.getId());
    }

    private void listarFilmes() {
        List<Movie> filmes = movieRepository.findAll();
        if (filmes.isEmpty()) {
            System.out.println("Nenhum filme cadastrado.");
        } else {
            filmes.forEach(System.out::println);
        }
    }

    private void buscarFilmePorId() {
        System.out.print("Informe o ID do filme: ");
        Long id = lerLong();
        if (id != null) {
            Movie movie = movieRepository.findById(id);
            if (movie != null) {
                System.out.println(movie);
            } else {
                System.out.println("Filme não encontrado.");
            }
        }
    }

    private void editarFilme() {
        System.out.print("Informe o ID do filme a ser editado: ");
        Long id = lerLong();
        if (id == null) return;

        Movie movie = movieRepository.findById(id);
        if (movie == null) {
            System.out.println("Filme não encontrado.");
            return;
        }

        System.out.println("Editando filme: " + movie);
        System.out.println("Pressione [Enter] se quiser manter o valor atual.");

        System.out.print("Novo título (" + movie.getTitulo() + "): ");
        String titulo = scanner.nextLine().trim();
        if (!titulo.isEmpty()) movie.setTitulo(titulo);

        System.out.print("Novo diretor (" + movie.getDiretor() + "): ");
        String diretor = scanner.nextLine().trim();
        if (!diretor.isEmpty()) movie.setDiretor(diretor);

        System.out.print("Novo ano de lançamento (" + movie.getAnoDeLancamento() + "): ");
        String anoStr = scanner.nextLine().trim();
        if (!anoStr.isEmpty()) movie.setAnoDeLancamento(Integer.parseInt(anoStr));

        System.out.print("Novo gênero (" + movie.getGenero() + "): ");
        String genero = scanner.nextLine().trim();
        if (!genero.isEmpty()) movie.setGenero(genero);

        System.out.print("Nova nota (" + movie.getNota() + "): ");
        String notaStr = scanner.nextLine().trim();
        if (!notaStr.isEmpty()) movie.setNota(Double.parseDouble(notaStr.replace(",", ".")));

        movieRepository.save(movie);
        System.out.println("Filme atualizado com sucesso!");
    }

    private void removerFilme() {
        System.out.print("Informe o ID do filme a ser removido: ");
        Long id = lerLong();
        if (id == null) return;

        Movie movie = movieRepository.findById(id);
        if (movie == null) {
            System.out.println("Filme não encontrado.");
            return;
        }

        System.out.print("Você deseja remover o filme '" + movie.getTitulo() + "'? (S/N): ");
        String confirmacao = scanner.nextLine().trim().toUpperCase();

        if (confirmacao.equals("S")) {
            movieRepository.delete(id);
            favoritesService.getFavoritesIds().remove(id); // Remove dos favoritos se estiver lá
            System.out.println("Filme removido do catálogo principal e dos favoritos.");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private void verFavoritos() {
        Set<Long> favIds = favoritesService.getFavoritesIds();
        if (favIds.isEmpty()) {
            System.out.println("Nenhum favorito adicionado ainda.");
            return;
        }

        System.out.println("=== Seus Filmes Favoritos ===");
        for (Long id : favIds) {
            Movie movie = movieRepository.findById(id);
            if (movie != null) {
                System.out.println(movie);
            }
        }
    }

    private void adicionarFavoritos() {
        System.out.println("Informe os IDs separados por vírgula (ex: 1, 3, 7): ");
        String input = scanner.nextLine();
        favoritesService.addFavorites(input);
    }

    private void removerDosFavoritos() {
        System.out.print("Informe o ID a ser removido dos favoritos: ");
        Long id = lerLong();
        if (id != null) {
            favoritesService.removeFavorite(id);
        }
    }

    private void buscarPorTitulo() {
        System.out.print("Digite parte do título: ");
        String query = scanner.nextLine().trim();
        List<Movie> resultados = movieRepository.searchByTitle(query);
        resultados.forEach(System.out::println);
        if(resultados.isEmpty()) System.out.println("Nenhum filme corresponde à busca.");
    }

    private void topFilmes() {
        List<Movie> rank = movieRepository.sortByRatingDesc();
        rank.forEach(System.out::println);
        if(rank.isEmpty()) System.out.println("Nenhum filme no catálogo.");
    }

    private void filtrarPorGenero() {
        System.out.print("Digite o gênero desejado: ");
        String genero = scanner.nextLine().trim();
        List<Movie> filtrados = movieRepository.filterByGenre(genero);
        filtrados.forEach(System.out::println);
        if(filtrados.isEmpty()) System.out.println("Nenhum filme encontrado para esse gênero.");
    }

    private Long lerLong() {
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite um número válido.");
            return null;
        }
    }

    private int lerInteiro() {
        try {
            String input = scanner.nextLine().trim();
            return input.isEmpty() ? 0 : Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Aviso: Formato inválido. Ano definido como 0.");
            return 0;
        }
    }

    private double lerDouble() {
        try {
            String input = scanner.nextLine().trim().replace(",", ".");
            return input.isEmpty() ? 0.0 : Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Aviso: Formato inválido. Nota definida como 0.0.");
            return 0.0;
        }
    }
}