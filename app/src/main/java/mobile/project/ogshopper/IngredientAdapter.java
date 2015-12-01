package mobile.project.ogshopper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Parth Patel on 11/30/2015.
 */
public class IngredientAdapter extends BaseAdapter {
    private Context context;
    private JSONArray ingredients;

    public IngredientAdapter(Context context, JSONArray ingredients) {
        this.ingredients = ingredients;
        this.context = context;
    }

    public int getCount() {
        return ingredients.length();
    }

    // Obtain an object from the JSONArray of ingredients
    public Object getItem(int position) {
        Object obj = null;
        try {
            obj = ingredients.get(position);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        JSONObject ingredientToDisplay = null;
        Ingredient ing = null;
        // catch any exceptions when obtaining the JSONObject for each ingredient containing a string name, qty, and unit
        try {
            ingredientToDisplay = ingredients.getJSONObject(position);
            // only try to get JSONString from JSONObject if the object key is not null
            if(ingredientToDisplay.isNull("unit") && ingredientToDisplay.isNull("qty")) {
                ing = new Ingredient(ingredientToDisplay.getString("name"), "", "");
            }
            else if(ingredientToDisplay.isNull("unit")) {
                ing = new Ingredient(ingredientToDisplay.getString("name"), ingredientToDisplay.getString("qty"), "");
            }
            else {
                ing = new Ingredient(ingredientToDisplay.getString("name"), ingredientToDisplay.getString("qty"), ingredientToDisplay.getString("unit"));
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }

        if (convertView == null) {
            // create the layout
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_ingredient_item, parent, false);
        }

        // populate the views with the ingredients from recipe
        TextView ingName = (TextView)convertView.findViewById(R.id.ingNameLbl);
        ingName.setText(ing.getName());

        TextView ingQty = (TextView)convertView.findViewById(R.id.ingQtyLbl);
        ingQty.setText(ing.getQty());

        TextView ingUnit = (TextView)convertView.findViewById(R.id.ingUnitLbl);
        ingUnit.setText(ing.getUnit());

        return convertView;
    }
}
