package com.example.urhome;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

public class ApartmentsListActivity extends AppCompatActivity {
    ApartmentAdapter arrayAdapter;
    List<apart> apartList;

    int clickedIndex = -1;

    private final ActivityResultLauncher<Intent> viewApartLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if(result.getData() != null){
                    apartList.get(clickedIndex).setRentEmail(result.getData().getStringExtra("result"));
                    arrayAdapter.notifyDataSetChanged();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments_list);
        apartList = new DatabaseHelper(this).getEveryoneWithoutRented();
        arrayAdapter = new ApartmentAdapter(this,apartList, false);
        ListView listView = findViewById(R.id.apartments_list_view);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(ApartmentsListActivity.this, ViewApartActivity.class);
            intent.putExtra("apart", apartList.get(i));
            clickedIndex = i;
            viewApartLauncher.launch(intent);
        });
    }
}