package mobile.project.ogshopper;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShoppingListActivity extends Activity {

    ListDBHelper dbhelper = new ListDBHelper(this);
    ListView lv;
    ItemAdapter itemAdapter;
    ArrayList<Item> ARItemList;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        // create list view and populate it with adapter
        lv = (ListView)findViewById(R.id.listView);
        populateListView();

        // set item click listener so tapping the item removes it from the list, then update adapter data and listview
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemID = ((TextView) view.findViewById(R.id.textView_ID)).getText().toString();
                dbhelper.deleteItem(Long.parseLong(itemID));
                populateListView();

            }
        });
        mp = MediaPlayer.create(this, R.raw.worthwhile);
    }


    // in the event of coming back from the recipes or gps lookups update the list to ensure synced with database
    @Override
    public void onResume() {
        super.onResume();

        populateListView();
    }

    // make a db call to get the latest list items and set the new value to the adapter to update list view
    private void populateListView(){
        ARItemList = dbhelper.getList();
        if(itemAdapter != null)
            itemAdapter.clear();
        itemAdapter = new ItemAdapter(ARItemList, this);
        lv.setAdapter(itemAdapter);

    }

    // add an item to the list using the add button and text field, a sound will play when added
    public void addItem(View v) {
        EditText newItem = (EditText)findViewById(R.id.ED_new_item);
        if(newItem.getText().toString().equals("")) {
            return;
        }
        dbhelper.addItem(newItem.getText().toString(), 0, "");
        mp.start();
        newItem.setText("");
        populateListView();
    }

    // create context menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_list, menu);
        return true;
    }

    // wait for menu item to be selected and do appropriate actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // if recipe lookup selected start recipesActivity
        if (id == R.id.recipeLookup) {
            Intent recipeLookupIntent = new Intent(this, RecipesActivity.class);
            startActivity(recipeLookupIntent);
        }
        // if gps lookup selected start searchActivity
        else if (id == R.id.gpsLookup) {
            Intent gpsLookupIntent = new Intent(this, SearchActivity.class);
            startActivity(gpsLookupIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
