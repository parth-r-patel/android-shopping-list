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
// Adapter to display recipes in the recipes list view
public class RecipeAdapter extends BaseAdapter {
    private Context context;
    private JSONArray recipes;

    public RecipeAdapter(Context context, JSONArray recipes) {
        this.recipes = recipes;
        this.context = context;
    }

    public int getCount() {
        return recipes.length();
    }

    // catch exceptions for getting JSONArray.get(index) object
    public Object getItem(int position) {
        Object obj = null;
        try {
            obj = recipes.get(position);
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
        Recipe recipeToDisplay = null;
        // try to get the JSONObject of a single recipe in the array of recipes
        try {
            JSONObject recipe = recipes.getJSONObject(position);
            recipeToDisplay = new Recipe(recipe.get("name").toString(), recipe.getJSONArray("ingredients"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (convertView == null) {
            // create the layout
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_recipe_item, parent, false);
        }

        // populate the views with the recipes from the overall JSONArray of recipes
        TextView recipeTitle = (TextView)convertView.findViewById(R.id.recipeItemTitle);
        recipeTitle.setText(recipeToDisplay.getName());

        return convertView;
    }
}
