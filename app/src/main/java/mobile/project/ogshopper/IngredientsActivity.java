package mobile.project.ogshopper;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class IngredientsActivity extends Activity {
    ListView listView;
    ListDBHelper dbhelper = new ListDBHelper(this);
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        mp = MediaPlayer.create(this, R.raw.worthwhile);

        listView = (ListView) findViewById(R.id.showIngredientsListView);
        // Parse JSONArray of ingredients from the extras in the activity Intent
        JSONTokener tokener = new JSONTokener(getIntent().getExtras().getString("ingredients"));
        JSONArray ingredients = null;
        try {
            ingredients = new JSONArray(tokener);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Instantiate adapter for ingredients listview
        IngredientAdapter adapter = new IngredientAdapter(this, ingredients);
        listView.setAdapter(adapter);

        // clickListener for each ingredient in list view, upon tapping an ingredient call addIngredientToList
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                try {
                    JSONTokener tokener = new JSONTokener(getIntent().getExtras().getString("ingredients"));
                    JSONArray ingredients = new JSONArray(tokener);
                    JSONObject ingredientObj = ingredients.getJSONObject(position);
                    Ingredient ingredient = null;
                    // Only get JSONStrings from JSONObject keys that are defined and not null
                    if (ingredientObj.isNull("unit") && ingredientObj.isNull("qty")) {
                        ingredient = new Ingredient(ingredientObj.getString("name"), "", "");
                    } else if (ingredientObj.isNull("unit")) {
                        ingredient = new Ingredient(ingredientObj.getString("name"), ingredientObj.getString("qty"), "");
                    } else {
                        ingredient = new Ingredient(ingredientObj.getString("name"), ingredientObj.getString("qty"), ingredientObj.getString("unit"));
                    }
                    addIngredientToList(ingredient);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * add item to database and create toast for user
     * @param ingredient the ingredient to add to the database and list
     */
    public void addIngredientToList(Ingredient ingredient) {
        dbhelper.addItem(ingredient.getName(), 0, "");
        mp.start();
        Toast itemAdded = Toast.makeText(this, "\"" + ingredient.getName() + "\" - Succesfully added to your list", Toast.LENGTH_SHORT);
        itemAdded.show();
    }
}
