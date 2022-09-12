import java.util.*;

public class KitchenApp {
    private static final Scanner scanner = new Scanner(System.in);
    private final Fridge fridge = new Fridge();

    public KitchenApp() {
//        ConnectionToDB.initializeRecipes();
        System.out.println("All right, you've done a great job, now the fun begins!");
        boolean isTrue = true;
        while (isTrue) {
            printMenuItems();
            int choice = scanner.nextInt();
            switch (choice) {
                case -1 -> {
                    isTrue = false;
                }
                case 1 -> {
                    fridge.addIngredient();
                }
                case 2 -> {
                    fridge.deleteIngredient();
                }
                case 3 -> {
                    List<Recipe> allRecipes = allTheMealsUCanMakeFromFridge();
                    if (allRecipes.isEmpty()) {
                        System.out.println("No recipes");
                    } else {
                        printList(allRecipes);
                    }
                }
                case 4 -> {
                    List<Recipe> allScaledRecipes = allScaledMealsUCanMakeFromFridge();
                    if (allScaledRecipes.isEmpty()) {
                        System.out.println("No recipes");
                    } else {
                        printList(allScaledRecipes);
                    }
                }
                case 5 -> {
                    makeAMeal();
                }
                case 6 -> {
                    List<Recipe> recipesWithXMoney = allTheMealsUCanMakeWithXMoney();
                    if (recipesWithXMoney.isEmpty()) {
                        System.out.println("No recipes");
                    } else {
                        printList(recipesWithXMoney);
                    }
                }
                case 7 -> {
                    List<Recipe> recipesWithXDifficulty = allTheMealsWithXDifficulty();
                    if (recipesWithXDifficulty.isEmpty()) {
                        System.out.println("No recipes");
                    } else {
                        printList(recipesWithXDifficulty);
                    }
                }
                case 8 -> {
                    List<Recipe> recipesWithXDifficultAndXMoney = allTheMealsWithXDifficultyAndXMoney();
                    if (recipesWithXDifficultAndXMoney.isEmpty()) {
                        System.out.println("No recipes");
                    } else {
                        printList(recipesWithXDifficultAndXMoney);
                    }
                }
                case 9 -> {
                    List<Recipe> sortedRecipes = ConnectionToDB.allRecipes;
                    sortedRecipes.sort((o1, o2) -> o1.getPrice() == o2.getPrice() ? 0 : 1);
                    printList(sortedRecipes);
                }
                case 10 -> {
                    List<Recipe> sortedRecipes = ConnectionToDB.allRecipes;
                    sortedRecipes.sort(Comparator.comparing(Recipe::getDifficulty));
                    printList(sortedRecipes);
                }
                case 11 -> {
                    fridge.printAllWeightedIngredients();
                }
                default -> {
                    System.out.println("Wrong input!");
                }
            }
        }

    }

    public void makeAMeal() {
        ConnectionToDB.printAllRecipes();
        System.out.println("This is a list of meal u can make, chose one! Enter the id of the meal u want to make!");
        int id = scanner.nextInt();
        fridge.makeMeal(ConnectionToDB.allRecipes.get(id));
    }

    public void printMenuItems() {
        System.out.println("Enter numbers from 1 to " + (fridge.getWeightedIngredientList().size() - 1) + " to access different abilities and -1 to exit the app!");
        System.out.println("Enter 1 to add ingredients to your fridge!");
        System.out.println("Enter 2 to remove ingredients to your fridge!");
        System.out.println("Enter 3 to check all the recipes u can make from ingredients in your fridge!");
        System.out.println("Enter 4 to check all the scaled meals u can make!");
        System.out.println("Enter 5 to make a specific meal!");
        System.out.println("Enter 6 to check all the meals u can make whit X money!");
        System.out.println("Enter 7 to check all the meals that have X difficulty to make");
        System.out.println("Enter 8 to combine 6 and 7");
        System.out.println("Enter 9 to sort recipes by price");
        System.out.println("Enter 9 to sort recipes by difficulty");
        System.out.println("Enter 11 to print all the ingredients from the fridge!");
    }

    public List<Recipe> allTheMealsUCanMakeWithXMoney() {
        System.out.println("Enter the amount of money u have for the meal!");
        double money = scanner.nextDouble();
        List<Recipe> meals = new ArrayList<>();
        for (int i = 0; i < ConnectionToDB.allRecipes.size(); i++) {
            if (ConnectionToDB.allRecipes.get(i).getPrice() <= money) {
                meals.add(ConnectionToDB.allRecipes.get(i));
            }
        }
        return meals;
    }

    public List<Recipe> allTheMealsWithXDifficulty() {
        boolean isTrue = true;
        RecipeDifficulty difficulty = null;
        List<Recipe> meals = new ArrayList<>();
        while (isTrue) {
            System.out.println("Enter from 1 to 5 to chose the difficulty(1:Beginner, 2:Easy, 3:Medium, 4:Hard, 5:Pro)");
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
                default -> {
                    System.out.println("Wrong input!");
                }
            }
        }
        for (int i = 0; i < ConnectionToDB.allRecipes.size(); i++) {
            if (ConnectionToDB.allRecipes.get(i).getDifficulty().equals(difficulty)) {
                meals.add(ConnectionToDB.allRecipes.get(i));
            }
        }
        return meals;
    }

    public List<Recipe> allTheMealsUCanMakeFromFridge() {
        List<Recipe> meals = new ArrayList<>();
        for (int i = 0; i < ConnectionToDB.allRecipes.size(); i++) {
            if (fridge.canMakeAMeal(ConnectionToDB.allRecipes.get(i))) {
                meals.add(ConnectionToDB.allRecipes.get(i));
            }
        }
        return meals;
    }

    public List<Recipe> allScaledMealsUCanMakeFromFridge() {
        List<Recipe> meals = new ArrayList<>();
        for (int i = 0; i < ConnectionToDB.allRecipes.size(); i++) {
            if (fridge.canMakeAMeal(ConnectionToDB.allRecipes.get(i).getScaledRecipe(0.5))) {
                meals.add(ConnectionToDB.allRecipes.get(i).getScaledRecipe(0.5));
            }
        }
        return meals;
    }

    public List<Recipe> allTheMealsWithXDifficultyAndXMoney() {
        List<Recipe> mealsWithXMoney = allTheMealsUCanMakeWithXMoney();
        List<Recipe> mealsWithXDifficulty = allTheMealsWithXDifficulty();
        List<Recipe> combination = new ArrayList<>();
        for (var meal : mealsWithXMoney) {
            for (var meal1 : mealsWithXDifficulty) {
                if (meal.getDifficulty().equals(meal1.getDifficulty())) {
                    combination.add(meal);
                }
            }
        }
        return combination;
    }

    public void printList(List<Recipe> meals) {
        for (var meal : meals) {
            System.out.println(meal);
        }
    }
}