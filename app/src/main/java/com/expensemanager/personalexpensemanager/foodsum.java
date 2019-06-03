package com.expensemanager.personalexpensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import Adapters.FoodAdapter;
import Database.FoodDB;
import Entities.Food;

public class foodsum extends AppCompatActivity {

    ImageView food_add_btn;
    private ListView listViewFoods;
    TextView noOfFoods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodsum);

        food_add_btn = findViewById(R.id.food_add_btn);
        noOfFoods = (TextView) findViewById(R.id.noOfFoods);
        String sum = String.valueOf(new FoodDB(this).findTot());
        noOfFoods.setText("Total Food Records   :   " + sum);

        food_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(new Intent(foodsum.this,food.class));
            }
        });

        final FoodDB foodDB = new FoodDB(this);

        this.listViewFoods = (ListView) findViewById(R.id.listViewFoods);
        this.listViewFoods.setAdapter(new FoodAdapter(this, foodDB.findAll()));

        this.listViewFoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Food food = foodDB.findAll().get(i);
                Intent intent = new Intent(foodsum.this, foodedit.class);
                intent.putExtra("Food",  food);
                startActivity(intent);
            }
        });

    }

}
