import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConnectionToDB {
    static Connection connection = null;
    static List<Recipe> allRecipes = new ArrayList<>();
    static List<Recipe> favouriteRecipes = new ArrayList<>();

    public static Connection connectDB() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/kitchen", "root", "Popokatepetl123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static int elementsCount() {
        Connection connection = ConnectionToDB.connectDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int elements = 0;
        try {
            String sql = "select count(*) from Ingredients";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            elements = resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return elements;
    }

    public static void printAllIngredientsFromDB() {
        Connection connection = ConnectionToDB.connectDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "select * from Ingredients";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Ingredients_ID");
                String ingredientName = resultSet.getString("Ingredient_Name");
                String ingredientPrice = resultSet.getString("Ingredent_Price");

                System.out.println("Ingredient id = " + id + " || Ingredient Name = " + ingredientName + " || Ingredient price per unit = " + ingredientPrice);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static WeightedIngredient ingredientFromDBById(int id) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = ConnectionToDB.connectDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        WeightedIngredient weightedIngredient = new WeightedIngredient(null, 0, 0);
        try {
            String sql = "SELECT Ingredient_Name, Ingredent_Price, IsWeightedIngredient FROM ingredients WHERE Ingredients_ID = " + id;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String ingredientName = resultSet.getString("Ingredient_Name");
                double ingredientPrice = resultSet.getDouble("Ingredent_Price");
                boolean isWeightedIngredient = resultSet.getBoolean("IsWeightedIngredient");
                if (isWeightedIngredient) {
                    System.out.println("Enter the weight of " + resultSet.getString("Ingredient_Name") + " u want to add:");
                    double weight = scanner.nextDouble();
                    weightedIngredient.setIngredientName(ingredientName);
                    weightedIngredient.setWeight(weight);
                    weightedIngredient.setPricePerUnit(ingredientPrice);
                } else {
                    System.out.println("Enter the number of " + resultSet.getString("Ingredient_Name") + "'s u want to add:");
                    int numberOfItems = scanner.nextInt();
                    weightedIngredient.setIngredientName(ingredientName);
                    weightedIngredient.setWeight(numberOfItems);
                    weightedIngredient.setPricePerUnit(ingredientPrice);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return weightedIngredient;
    }

    public static String ingredientNameFromDBById(int id) {
        Connection connection = ConnectionToDB.connectDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String ingredientName = "";
        try {
            String sql = "SELECT Ingredient_Name FROM ingredients WHERE Ingredients_ID = " + id;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ingredientName = resultSet.getString("Ingredient_Name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredientName;
    }

    public static double ingredientPriceFromDBById(int id) {
        Connection connection = ConnectionToDB.connectDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        double ingredientPrice = 0;
        try {
            String sql = "SELECT Ingredent_Price FROM ingredients WHERE Ingredients_ID = " + id;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ingredientPrice = resultSet.getDouble("Ingredent_Price");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredientPrice;
    }

    public static boolean isWeightedIngredientFromDBById(int id) {
        Connection connection = ConnectionToDB.connectDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isWeighted = false;
        try {
            String sql = "SELECT IsWeightedIngredient FROM ingredients WHERE Ingredients_ID = " + id;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isWeighted = resultSet.getBoolean("IsWeightedIngredient");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isWeighted;
    }

    public static void printAllRecipes() {
        for (int i = 0; i < allRecipes.size(); i++) {
            System.out.println("Recipe ID = " + i + " " +allRecipes.get(i).toString());
        }
        System.out.println(" ");
    }

    public static void printAllFavouriteRecipes() {
        for (int i = 0; i < favouriteRecipes.size(); i++) {
            System.out.println("Recipe ID = " + i + " " +favouriteRecipes.get(i).toString());
        }
        System.out.println(" ");
    }

    public static void initializeRecipes() {
        System.out.println("Welcome to the Kitchen, to start coking u first need to write 10 of your recipes in your cookbook");
        for (int i = 0; i < 10; i++) {
            System.out.println("Recipe number " + (i + 1));
            Recipe recipe = new Recipe();
            recipe.createRecipe();
        }
    }

}