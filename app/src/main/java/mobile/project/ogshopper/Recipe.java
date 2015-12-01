package mobile.project.ogshopper;

import org.json.JSONArray;

/**
 * Created by Parth Patel on 11/30/2015.
 */
// Simple structure for Recipes
public class Recipe {
    private String name;
    private JSONArray ingredients;

    public Recipe(String name, JSONArray ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public JSONArray getIngredients() {
        return ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(JSONArray ingredients) {
        this.ingredients = ingredients;
    }
}
