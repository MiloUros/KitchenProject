import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Fridge {
    private static final Scanner scanner = new Scanner(System.in);

    private final List<WeightedIngredient> weightedIngredientList = new ArrayList<>();

    public Fridge() {
    }

    public List<WeightedIngredient> getWeightedIngredientList() {
        return weightedIngredientList;
    }

    public void addIngredient() {
        while (true) {
            ConnectionToDB.printAllIngredientsFromDB();
            System.out.println("Enter a id number from 0 to " + (ConnectionToDB.elementsCount() - 1) + " to add Ingredient");
            int id = scanner.nextInt();
            if (id >= 0 && id <= (ConnectionToDB.elementsCount() - 1)) {
                if (hasIngredient(ConnectionToDB.ingredientNameFromDBById(id)) != null) {
                    if (ConnectionToDB.isWeightedIngredientFromDBById(id)) {
                        System.out.println("U already have this ingredient, how much grams u want to add(1 = 1000g)?");
                        double weightToAdd = scanner.nextDouble();
                        hasIngredient(ConnectionToDB.ingredientNameFromDBById(id)).setWeight(hasIngredient(ConnectionToDB.ingredientNameFromDBById(id)).getWeight() + weightToAdd);
                    } else {
                        System.out.println("U already have this ingredient, how much items u want to add?");
                        int numberOfItemsToAdd = scanner.nextInt();
                        hasIngredient(ConnectionToDB.ingredientNameFromDBById(id)).setWeight(hasIngredient(ConnectionToDB.ingredientNameFromDBById(id)).getWeight() + numberOfItemsToAdd);
                    }
                } else {
                    if (ConnectionToDB.isWeightedIngredientFromDBById(id)) {
                        System.out.println("Enter the weight of " + ConnectionToDB.ingredientNameFromDBById(id) + " u want to add:");
                        double weight = scanner.nextDouble();
                        weightedIngredientList.add(new WeightedIngredient(ConnectionToDB.ingredientNameFromDBById(id), weight, ConnectionToDB.ingredientPriceFromDBById(id)));
                    } else {
                        System.out.println("Enter the number of " + ConnectionToDB.ingredientNameFromDBById(id) + "'s u want to add:");
                        int numberOfItems = scanner.nextInt();
                        weightedIngredientList.add(new WeightedIngredient(ConnectionToDB.ingredientNameFromDBById(id), numberOfItems, ConnectionToDB.ingredientPriceFromDBById(id)));
                    }
                }
            } else {
                System.out.println("Wrong input, enter numbers from 0 to 14");
            }
            System.out.println("If u are done whit your ingredients pls enter 'exit' or 'add' if u want to add more ingredients.");
            String exit = scanner.next();
            if (exit.equals("exit")) {
                break;
            }
        }
    }

    public void deleteIngredient() {
        int id = 0;
        for (var ingredient : weightedIngredientList) {
            System.out.println(ingredient + "ID = " + id++);
        }
        while (true) {
            System.out.println("Do u want to delete some ingredient or just reduce some weight? Enter delete/reduce");
            String decision = scanner.next();
            if (decision.equals("delete")) {
                System.out.println("Enter the id of the ingredient u want to delete!");
                id = scanner.nextInt();
                if (id >= 0 && id < weightedIngredientList.size()) {
                    weightedIngredientList.remove(weightedIngredientList.get(id));
                    break;
                } else {
                    System.out.println("U have entered the wrong ID, pls enter ID' from 0 to " + weightedIngredientList.size());
                }
            } else if (decision.equals("reduce")){
                System.out.println("Enter the id of the ingredient u want to reduce!");
                id = scanner.nextInt();
                if (id >= 0 && id < weightedIngredientList.size()) {
                    System.out.println("Enter the amount u want to reduce: ");
                    double amount = scanner.nextDouble();
                    if (weightedIngredientList.get(id).getWeight() - amount < 0) {
                        weightedIngredientList.get(id).setWeight(0);
                    } else {
                        weightedIngredientList.get(id).setWeight(weightedIngredientList.get(id).getWeight() - amount);
                    }
                    break;
                } else {
                    System.out.println("U have entered the wrong ID, pls enter ID' from 0 to " + weightedIngredientList.size());
                }
            }
        }
    }

    public boolean canMakeAMeal(Recipe recipe) {
        if (weightedIngredientList.isEmpty()) {
            System.out.println("Your fridge is empty, add some ingredients!");
            return false;
        }
        List<WeightedIngredient> recipeIngredients = recipe.getWeightedIngredientList();
        for (WeightedIngredient recipeIngredient : recipeIngredients) {
            for (WeightedIngredient weightedIngredient : weightedIngredientList) {
                if (recipeIngredient.equals(weightedIngredient)) {
                    if (recipeIngredient.getWeight() > weightedIngredient.getWeight()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void makeMeal(Recipe recipe) {
        List<WeightedIngredient> recipeIngredients = recipe.getWeightedIngredientList();
        if (canMakeAMeal(recipe)) {
            for (WeightedIngredient recipeIngredient : recipeIngredients) {
                for (WeightedIngredient weightedIngredient : weightedIngredientList) {
                    if (recipeIngredient.equals(weightedIngredient)) {
                        weightedIngredient.setWeight(weightedIngredient.getWeight() - recipeIngredient.getWeight());
                    }
                }
            }
        } else {
            System.out.println("U cant make " + recipe.getRecipeName() + " u don't have enough ingredients");
        }
    }

    public WeightedIngredient hasIngredient(String ingredientName) {
        for (var ingredient : weightedIngredientList) {
            if (ingredient.getIngredientName().equals(ingredientName)) {
                return ingredient;
            }
        }
        return null;
    }

    public void printAllWeightedIngredients() {
        for (var ingredient : weightedIngredientList) {
            System.out.println(ingredient);
        }
    }

    @Override
    public String toString() {
        return "weightedIngredientList=" + weightedIngredientList.toString();
    }
}