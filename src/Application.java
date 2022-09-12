import java.util.Scanner;

public class Application {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Recipe recipe = new Recipe();

        recipe.createRecipe();
        recipe.createRecipe();
        recipe.createRecipe();
        recipe.createRecipe();

        new KitchenApp();
    }
}