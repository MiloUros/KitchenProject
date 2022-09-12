public class WeightedIngredient extends Ingredients {

    private double weight;
    private double pricePerUnit;

    public WeightedIngredient(String ingredientName, double weight, double pricePerUnit) {
        super(ingredientName);
        this.weight = weight;
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public double getPrice() {
        return weight * pricePerUnit;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public WeightedIngredient scaledWeightedIngredient(double scale) {
        return new WeightedIngredient(this.getIngredientName(), this.getWeight() * scale, this.getPricePerUnit());
    }
    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    @Override
    public String toString() {
        return super.toString() +
                " weight = " + weight +
                " pricePerUnit = " + pricePerUnit;
    }
}