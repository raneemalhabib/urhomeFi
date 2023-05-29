package com.example.urhome;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


  Button move;
  Button logout;

/*
    ListView lv_all;
    ArrayAdapter apartArrayAdapter;
*/




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        move=findViewById(R.id.btn_move);
        logout=findViewById(R.id.logout);

        Button button = findViewById(R.id.view_apartment_btn);


        button.setOnClickListener(view -> startActivity(new Intent(this, ApartmentsListActivity.class)));





        move.setOnClickListener(v -> {
            Intent intentL = new Intent( MainActivity.this, AddDelete.class);
            startActivity(intentL);
        });


       logout.setOnClickListener(v -> {
           Intent intentL = new Intent( MainActivity.this, LoginActivity.class);
           startActivity(intentL);
           MyApplication.getInstance().removeUserEmail();
           finish();
           Toast.makeText(MainActivity.this, " Successfully log out " , Toast.LENGTH_SHORT).show();
       });



    }


}