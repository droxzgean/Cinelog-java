import java.util.LinkedHashSet;
import java.util.Set;

public class FavoritesService {
    private final Set<Long> favorites = new LinkedHashSet<>();
    private final MovieRepository repository;

    public FavoritesService(MovieRepository repository) {
        this.repository = repository;
    }

    public void addFavorites(String input) {
        String[] ids = input.split(",");
        for (String idStr : ids) {
            try {
                Long id = Long.parseLong(idStr.trim());
                if (repository.findById(id) != null) {
                    favorites.add(id);
                    System.out.println("-> Filme + id + adicionado aos favoritos.");
                } else {
                    System.out.println("-> Aviso: Filme ID + id + não encontrado no catálogo. Ignorado.");
                }
            } catch (NumberFormatException e) {
                System.out.println("-> Aviso: '" + idStr.trim() + "' não é um ID válido. Ignorado.");
            }
        }
    }

    public Set<Long> getFavoritesIds() {
        return favorites;
    }

    public void removeFavorite(Long id) {
        if (favorites.remove(id)) {
            System.out.println("Filme removido dos favoritos.");
        } else {
            System.out.println("O ID informado não está na sua lista de favoritos.");
        }
    }
}