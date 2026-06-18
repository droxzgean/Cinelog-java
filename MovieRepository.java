import java.util.*;
import java.util.stream.Collectors;

public class MovieRepository {
    private final Map<Long, Movie> movies = new HashMap<>();
    private long nextId = 1L; // ID autoincremental

    public Movie save(Movie movie) {
        if (movie.getId() == null) {
            movie.setId(nextId++);
        }
        movies.put(movie.getId(), movie);
        return movie;
    }

    public List<Movie> findAll() {
        return new ArrayList<>(movies.values());
    }

    public Movie findById(Long id) {
        return movies.get(id);
    }

    public void delete(Long id) {
        movies.remove(id);
    }

    public List<Movie> searchByTitle(String query) {
        return movies.values().stream()
                .filter(m -> m.getTitulo().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Movie> sortByRatingDesc() {
        return movies.values().stream()
                .sorted(Comparator.comparingDouble(Movie::getNota).reversed())
                .collect(Collectors.toList());
    }

    //filtro por gênero
    public List<Movie> filterByGenre(String genero) {
        return movies.values().stream()
                .filter(m -> m.getGenero().equalsIgnoreCase(genero))
                .collect(Collectors.toList());
    }
}