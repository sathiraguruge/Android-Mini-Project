package com.expensemanager.personalexpensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import Adapters.ShoppingAdapter;
import Database.ShoppingDB;
import Entities.Shopping;

public class shoppingsum extends AppCompatActivity {

    ImageView shopping_add_btn;
    private ListView listViewShops;
    TextView noOfShops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingsum);

        shopping_add_btn = findViewById(R.id.shopping_add_btn);
        noOfShops = (TextView) findViewById(R.id.noOfShops);
        String sum = String.valueOf(new ShoppingDB(this).findTot());
        noOfShops.setText("Total Shopping Records   :   " + sum);

        shopping_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(new Intent(shoppingsum.this,shopping.class));
            }
        });

        final ShoppingDB shoppingDB = new ShoppingDB(this);

        this.listViewShops = (ListView) findViewById(R.id.listViewShopping);
        this.listViewShops.setAdapter(new ShoppingAdapter(this, shoppingDB.findAll()));

        this.listViewShops.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shopping shopping = shoppingDB.findAll().get(i);
                Intent intent = new Intent(shoppingsum.this, shoppingedit.class);
                intent.putExtra("Shopping",  shopping);
                startActivity(intent);
            }
        });
    }

}
