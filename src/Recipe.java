import interfaces.Priceable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

enum RecipeDifficulty {
    BEGINNER,
    EASY,
    MEDIUM,
    HARD,
    PRO
}

public class Recipe implements Priceable {
    private static final Scanner scanner = new Scanner(System.in);
    private String recipeName;
    RecipeDifficulty difficulty;
    private List<WeightedIngredient> weightedIngredientList = new ArrayList<>();

    public Recipe(String recipeName, RecipeDifficulty difficulty, List<WeightedIngredient> weightedIngredientList) {
        this.recipeName = recipeName;
        this.difficulty = difficulty;
        this.weightedIngredientList = weightedIngredientList;
    }

    public Recipe() {
    }

    public RecipeDifficulty getDifficulty() {
        return difficulty;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public List<WeightedIngredient> getWeightedIngredientList() {
        return weightedIngredientList;
    }

    @Override
    public double getPrice() {
        double recipePrice = 0;
        for (var ingredient : weightedIngredientList) {
            recipePrice += ingredient.getPrice();
        }
        return recipePrice;
    }

    public Recipe getScaledRecipe(double scale) {
        List<WeightedIngredient> scaledWeight = new ArrayList<>();
        for (var ingredient : weightedIngredientList) {
            scaledWeight.add(ingredient.scaledWeightedIngredient(scale));
        }
        return new Recipe(this.getRecipeName(), this.getDifficulty(), scaledWeight);
    }

    public List<WeightedIngredient> addIngredient() {
        List<WeightedIngredient> weightedIngredients = new ArrayList<>();
        while (true) {
            ConnectionToDB.printAllIngredientsFromDB();
            System.out.println("Enter a id number from 0 to " + (ConnectionToDB.elementsCount() - 1) +" to add Ingredient");
            int id = scanner.nextInt();
            if (id >= 0 && id <= (ConnectionToDB.elementsCount() - 1)) {
                weightedIngredients.add(ConnectionToDB.ingredientFromDBById(id));
            } else {
                System.out.println("Wrong input, enter numbers from 0 to 14");
            }
            System.out.println("If u are done whit your ingredients pls enter 'exit' or 'add' if u want to add more ingredients.");
            String exit = scanner.next();
            if (exit.equals("exit")) {
                break;
            }
        }
        return weightedIngredients;
    }

    public void addIngredientToRecipe() {
        while (true) {
            ConnectionToDB.printAllIngredientsFromDB();
            System.out.println("Enter a id number from 0 to " + (ConnectionToDB.elementsCount() - 1) +" to add Ingredient");
            int id = scanner.nextInt();
            if (id >= 0 && id <= (ConnectionToDB.elementsCount() - 1)) {
                weightedIngredientList.add(ConnectionToDB.ingredientFromDBById(id));
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

    public void deleteIngredientFromRecipe() {
        int id = 0;
        for (var ingredient : weightedIngredientList) {
            System.out.println(ingredient + "ID = " + id++);
        }
        while (true) {
            System.out.println("Enter the ID of the Ingredient u want to delete: ");
            id = scanner.nextInt();
            if (id >= 0 && id < weightedIngredientList.size()) {
                weightedIngredientList.remove(weightedIngredientList.get(id));
                break;
            } else {
                System.out.println("U have entered the wrong ID, pls enter ID' from 0 to " + weightedIngredientList.size());
            }
        }
    }

    public void createRecipe() {
        System.out.println("Enter the recipe name: ");
        boolean isTrue = true;
        String recipeName = scanner.next();
        RecipeDifficulty difficulty = null;
        while (isTrue) {
            System.out.println("Enter from 1 to 5 the difficulty to make this recipe(1:Beginner, 2:Easy, 3:Medium, 4:Hard, 5:Pro)");
            int recipeDif = scanner.nextInt();
            switch (recipeDif) {
                case 1 -> {
                    difficulty = RecipeDifficulty.BEGINNER;
                    isTrue = false;
                }
                case 2 -> {
                    difficulty = RecipeDifficulty.EASY;
                    isTrue = false;
                }
                case 3 -> {
                    difficulty = RecipeDifficulty.MEDIUM;
                    isTrue = false;
                }
                case 4 -> {
                    difficulty = RecipeDifficulty.HARD;
                    isTrue = false;
                }
                case 5 -> {
                    difficulty = RecipeDifficulty.PRO;
                    isTrue = false;
                }
            }
        }
        List<WeightedIngredient> weightedIngredients = addIngredient();
        ConnectionToDB.allRecipes.add(new Recipe(recipeName, difficulty, weightedIngredients));
    }

    public String weightedIngredientsPrint() {
        StringBuilder allIngredients = new StringBuilder();
        for (var ingredient : weightedIngredientList) {
            allIngredients.append(ingredient.toString());
            allIngredients.append("\n");
        }
        allIngredients.append("Total Cost: ").append(getPrice());
        allIngredients.append("\n");
        return allIngredients.toString();
    }

    @Override
    public String toString() {
        return "RecipeName = " + recipeName + " " +
                "RecipeDifficulty = " + difficulty + "\n" +
                "WeightedIngredients: " + "\n" + weightedIngredientsPrint();
    }
}