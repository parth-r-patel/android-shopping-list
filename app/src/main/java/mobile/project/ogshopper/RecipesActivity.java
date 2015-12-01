package mobile.project.ogshopper;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class RecipesActivity extends Activity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        listView = (ListView) findViewById(R.id.showRecipesListView);

        // Create and execute an async background task to download JSON file of recipes
        DownloadRecipes asyncDownload = new DownloadRecipes();
        asyncDownload.execute("http://team188.com/rest/recipes.json");
    }

    /**
     * When a recipe is tapped, set the ingredients as extras and start IngredientsActivity
     * @param recipe the recipe that was selected
     */
    public void showIngredientsForRecipe(JSONObject recipe) {
        try {
            Intent ingredientsActivityIntent = new Intent(this, IngredientsActivity.class);
            ingredientsActivityIntent.putExtra("ingredients", recipe.getJSONArray("ingredients").toString());
            startActivity(ingredientsActivityIntent);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * onPostExecute of the async task, this method will take the recipes from the JSON file as JSONArray and create the adapter and listview
     * @param recipes JSONArray of recipes from JSON file that was downloaded
     */
    public void showRecipes(final JSONArray recipes) {
        RecipeAdapter adapter = new RecipeAdapter(this, recipes);
        listView.setAdapter(adapter);

        // Listen for clicks on each recipe as a list view item to dive into ingredients list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                try {
                    JSONObject recipe = recipes.getJSONObject(position);
                    showIngredientsForRecipe(recipe);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class DownloadRecipes extends AsyncTask<String, Integer, JSONArray> {
        private JSONArray recipes;

        /**
         * create an input stream from URLConnection, then create a string of the entire JSON to be parsed and tokenize into JSONArray
         * @param params contains the parameters for async task namely params[0] which is the URL as a string of file
         * @return JSONArray of all recipes in JSON file
         */
        protected JSONArray doInBackground(String... params) {
            URLConnection feedUrl;
            try {
                feedUrl = new URL(params[0]).openConnection();
                InputStream is = feedUrl.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "");
                }
                is.close();
                JSONTokener jsonTokener = new JSONTokener(sb.toString());
                recipes = new JSONArray(jsonTokener);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return recipes;
        }

        // pass list of recipes to a function on UI thread to display in listview using adapter
        protected void onPostExecute(JSONArray result) {
            showRecipes(result);
        }
    }
}
