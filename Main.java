public class Main {
    public static void main(String[] args) {
        MovieRepository repository = new MovieRepository();
        FavoritesService favoritesService = new FavoritesService(repository);
        MenuHandler menu = new MenuHandler(repository, favoritesService);

        // inicio
        menu.start();
    }
}
