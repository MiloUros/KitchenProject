import interfaces.Priceable;

import java.util.Objects;

public abstract class Ingredients implements Priceable {
    private String ingredientName;

    public Ingredients(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public String toString() {
        return "ingredientName = " + ingredientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredients that = (Ingredients) o;
        return Objects.equals(getIngredientName(), that.getIngredientName());
    }
}