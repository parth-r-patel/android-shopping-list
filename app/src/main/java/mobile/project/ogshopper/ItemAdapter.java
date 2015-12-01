package mobile.project.ogshopper;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Ryan Lildhar on 11/27/2015.
 */

/**
 * Adapter that will populate the list view containing itms on the users to buy list
 */
public class ItemAdapter extends ArrayAdapter<Item> {

    private List<Item> itemList;
    private Context context;

    public ItemAdapter(List<Item> ItemList, Context context) {
        super(context, R.layout.item_layout, ItemList);
        this.itemList = ItemList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Item getItem(int pos) {
        return itemList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return itemList.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    private static class ItemHolder {
        public ImageButton imageButton_Delete;
        public TextView textView_ItemName;
        public TextView textView_ID;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;

        ItemHolder holder = new ItemHolder();


        // Update all the views with the list item information
        if(convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_layout, null);
            holder.textView_ItemName = (TextView) view.findViewById(R.id.textView_ItemName);
            holder.textView_ID = (TextView) view.findViewById(R.id.textView_ID);


            Item item = itemList.get(position);
            holder.textView_ItemName.setText(item.getName());
            holder.textView_ID.setText("" + item.getId());

        } else {
            holder = (ItemHolder)view.getTag();
        }

        return view;
    }
}