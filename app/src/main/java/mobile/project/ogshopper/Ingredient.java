package mobile.project.ogshopper;

/**
 * Created by Parth Patel on 11/30/2015.
 */

// structure for ingredients in recipes
public class Ingredient {
    private String name;
    private String qty;
    private String unit;

    public Ingredient(String name, String qty, String unit) {
        this.name = name;
        this.qty = qty;
        this.unit = unit;
    }

    public String getName() {
        return this.name;
    }

    public String getQty() {
        return this.qty;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
