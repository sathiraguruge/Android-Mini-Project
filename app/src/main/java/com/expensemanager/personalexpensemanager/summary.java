package com.expensemanager.personalexpensemanager;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import Database.FoodDB;
import Database.ShoppingDB;
import Database.TransportDB;

public class summary extends AppCompatActivity {

    TextView textViewTransportSum;
    TextView textViewFoodSum;
    TextView textViewShopSum;
    TextView totSum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        this.textViewTransportSum = (TextView) findViewById(R.id.textViewTransportSum);
        textViewTransportSum.setText(String.valueOf(new TransportDB(this).findSum()));

        this.textViewFoodSum = (TextView) findViewById(R.id.textViewFoodSum);
        textViewFoodSum.setText(String.valueOf(new FoodDB(this).findSum()));

        this.textViewShopSum = (TextView) findViewById(R.id.textViewShopSum);
        textViewShopSum.setText(String.valueOf(new ShoppingDB(this).findSum()));

        Double total = new TransportDB(this).findSum();
        total += new FoodDB(this).findSum();
        total += new ShoppingDB(this).findSum();

        this.totSum = (TextView) findViewById(R.id.totSum);
        totSum.setText(String.valueOf(total));
    }
}
