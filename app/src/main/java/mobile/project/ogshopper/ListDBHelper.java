package mobile.project.ogshopper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Ryan Lildhar on 11/25/2015.
 */
public class ListDBHelper extends SQLiteOpenHelper {
    private static String LOGTAG = "DBHelper";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "list.db";

    public static final String TABLE_NAME = "items";

    public static final String COLUMN_ID = "list_id";
    public static final String COLUMN_NAME = "item_name";
    public static final String COLUMN_CHECK = "checked"; //COLUMN_CHECK is used to mark if the item is checked off uses tinyint 1 for checked 0 for unchecked
    public static final String COLUMN_PRICE = "price";

    // Create the table
    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_CHECK + " TINYINT DEFAULT 0, " +
                    COLUMN_PRICE + " DECIMAL(10,5)" +
                    ")";

    public ListDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // if the database is updated then drop table to recreate new one
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    // Initialize the database with values
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATEMENT);
        Log.i(LOGTAG, "Table has been created");

        //test data
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, "Banana");
        values.put(COLUMN_CHECK, 1);
        values.put(COLUMN_PRICE, 10.20);
        db.insert(TABLE_NAME, null, values);
        Log.i(LOGTAG, "Banana has been added");
        values.clear();
        values.put(COLUMN_NAME, "apples 2 ");
        values.put(COLUMN_CHECK, 0);
        values.put(COLUMN_PRICE, 5.55);
        db.insert(TABLE_NAME, null, values);
        Log.i(LOGTAG, "apple has been added");
        values.clear();
        values.put(COLUMN_NAME, "Pears 3");
        values.put(COLUMN_CHECK, 1);
        values.put(COLUMN_PRICE, 15.00);
        db.insert(TABLE_NAME, null, values);
        Log.i(LOGTAG, "Banana has been added");
    }

    // get an ArrayList of all items in the users list
    public ArrayList<Item> getList(){
        ArrayList<Item> ARItem = new ArrayList<>();

        //open db connection
        SQLiteDatabase db = this.getWritableDatabase();
        // retrieve the contact from the database
        String[] columns = new String[] { COLUMN_ID, COLUMN_NAME, COLUMN_CHECK, COLUMN_PRICE };
        Cursor cursor = db.query(TABLE_NAME, columns, "", new String[]{}, "", "", "");
        while (cursor.moveToNext()) {
            long id = Long.parseLong(cursor.getString(0));
            String name = cursor.getString(1);
            int check = Integer.parseInt(cursor.getString(2));
            String price = cursor.getString(3);

            Item item = new Item(name, check, price);
            item.setId(id);
            ARItem.add(item);
        }


        return  ARItem;
    }

    // add an item into the database by name, check, and price
    public void addItem (String name, int check, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CHECK, check);
        values.put(COLUMN_PRICE, price);
        db.insert(TABLE_NAME, null, values);
        Log.i(LOGTAG,name + "::Added");
    }

    // remove a single item from the database and user list
    public void deleteItem(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        //convert long to string to be used in delete
        String STRid = String.valueOf(id);

        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{STRid});

        Log.i(LOGTAG, "item deleted id::" + id);

    }
}
