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

import Adapters.TransportAdapter;
import Database.TransportDB;
import Entities.Transport;

public class transportsum extends AppCompatActivity {

    ImageView transport_add_btn;
    private ListView listViewTransports;
    TextView noOfTransports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportsum);

        transport_add_btn = findViewById(R.id.transport_add_btn);
        noOfTransports = (TextView) findViewById(R.id.noOfTransports);
        String sum = String.valueOf(new TransportDB(this).findTot());
        noOfTransports.setText("Total Transport Records   :   " + sum);

        transport_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(new Intent(transportsum.this,transport.class));
            }
        });


        final TransportDB transportDB = new TransportDB(this);
        this.listViewTransports = (ListView) findViewById(R.id.listViewTransports);
        this.listViewTransports.setAdapter(new TransportAdapter(this, transportDB.findAll()));

        this.listViewTransports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Transport transport = transportDB.findAll().get(i);
                Intent intent = new Intent(transportsum.this, transportedit.class);
                intent.putExtra("Transport",  transport);
                startActivity(intent);
            }
        });
    }

}
