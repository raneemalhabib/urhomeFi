package com.example.urhome;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
//adp
public class ApartmentAdapter extends ArrayAdapter<apart> {
    List<apart> itemList;
    Activity context;
    boolean showDeleteButton;
    public ApartmentAdapter(Activity context, List<apart> items, boolean showDeleteButton) {

        super(context, 0, items);
        itemList = items;
        this.context = context;
        this.showDeleteButton = showDeleteButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View lisItemView = convertView;
        if (lisItemView == null) {
            lisItemView = LayoutInflater.from(getContext()).inflate(R.layout.apartment, parent, false);
        }
        TextView nameTextView = lisItemView.findViewById(R.id.apart_item_name2);
        nameTextView.setText(itemList.get(position).getName());

        TextView priceTextView = lisItemView.findViewById(R.id.apart_item_price2);
        priceTextView.setText(String.valueOf(itemList.get(position).getPrice()));

        TextView locationTextView = lisItemView.findViewById(R.id.apart_item_location2);
        locationTextView.setText(itemList.get(position).getLocation());

        TextView roomsTextView = lisItemView.findViewById(R.id.apart_item_rooms_count2);
        roomsTextView.setText(String.valueOf(itemList.get(position).getRooms()));

        TextView rentToTextView = lisItemView.findViewById(R.id.apart_item_rented_to2);
        rentToTextView.setText(itemList.get(position).getRentEmail());

        if(showDeleteButton){
            ImageButton imageButton = lisItemView.findViewById(R.id.delete_btn);
            imageButton.setVisibility(View.VISIBLE);
             imageButton.setOnClickListener(view -> {

                 new AlertDialog.Builder(context)
                         .setTitle("delete confirmation")
                         .setPositiveButton(android.R.string.yes, (dialog, which) -> {

                             new DatabaseHelper(context).DeleteOne(itemList.get(position));
                             Toast.makeText(context,   itemList.get(position).getName() + " is DELETED", Toast.LENGTH_SHORT).show();
                             itemList.remove(position);
                             notifyDataSetChanged();
                             dialog.cancel();
                         }).setNegativeButton(android.R.string.no, (dialog, which) -> {
                             dialog.cancel();
                         }).show();
             });
        }
        return lisItemView;
    }
}
