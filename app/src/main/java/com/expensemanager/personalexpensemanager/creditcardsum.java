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

import Adapters.CardAdapter;
import Database.CardDB;
import Entities.Card;

public class creditcardsum extends AppCompatActivity {

    ImageView cc_add_btn;
    private ListView listViewCards;
    TextView noOfCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditcardsum);

        cc_add_btn = findViewById(R.id.cc_add_btn);
        noOfCards = (TextView) findViewById(R.id.noOfCards);
        String sum = String.valueOf(new CardDB(this).findTot());
        noOfCards.setText("Total Credit Cards   :   " + sum);

        cc_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(new Intent(creditcardsum.this,creditcard.class));
            }
        });


        final CardDB cardDB = new CardDB(this);
        this.listViewCards = (ListView) findViewById(R.id.listViewCards);
        this.listViewCards.setAdapter(new CardAdapter(this, cardDB.findAll()));

        this.listViewCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Card card = cardDB.findAll().get(i);
                Intent intent = new Intent(creditcardsum.this, creditcardedit.class);
                intent.putExtra("Card",  card);
                startActivity(intent);
            }
        });


    }

}
