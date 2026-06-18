//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        MovieRepository repository = new MovieRepository();
        FavoritesService favoritesService = new FavoritesService(repository);
        MenuHandler menu = new MenuHandler(repository, favoritesService);

        // inicio
        menu.start();
    }
}