
public class Application {

    public static void main(String[] args) {
        Recipe recipe = new Recipe();

        recipe.createRecipe();
        recipe.createRecipe();
        recipe.createRecipe();
        recipe.createRecipe();

        new KitchenApp();
    }
}
