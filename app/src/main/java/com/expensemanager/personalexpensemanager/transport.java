package com.expensemanager.personalexpensemanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import Database.CardDB;
import Database.TransportDB;
import Entities.Card;
import Entities.Transport;

public class transport extends AppCompatActivity {

    private Calendar calendar;
    EditText addTransportDate;
    EditText addTransportName;
    EditText addTransportDescription;
    EditText addTransportPrice;
    ImageView addTransportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
        initialize();
        initializeListners();
    }

    private void initialize() {
        this.addTransportDate = (EditText) findViewById(R.id.addTransportDate);
        this.addTransportName = (EditText) findViewById(R.id.addTransportName);
        this.addTransportPrice = (EditText) findViewById(R.id.addTransportPrice);
        this.addTransportDescription = (EditText) findViewById(R.id.addTransportDescription);
        this.addTransportButton = (ImageView) findViewById(R.id.addTransportButton);
    }

    private void initializeListners() {

        this.addTransportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               int validatorValue = validator(view);
               if(validatorValue != -1)
                    insertData(view);
            }
        });

        calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        addTransportDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(transport.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }



    public void insertData(View view){
        TransportDB transportDB = new TransportDB(getBaseContext());
        Transport transport = new Transport();

        transport.setName(addTransportName.getText().toString());
        transport.setDescription(addTransportDescription.getText().toString());
        transport.setDate(addTransportDate.getText().toString());
        transport.setPrice(Double.parseDouble(addTransportPrice.getText().toString()));

        if(transportDB.create(transport)){
            toastMessage("Transport Added Sucessfully");
            Intent intent = new Intent(transport.this, transportsum.class);
            startActivity(intent);
        }
        else {
            errorMessage("Fail", view);
        }

    }

    private void updateLabel(){
        String format = "dd/MM/YY";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        addTransportDate.setText(sdf.format((calendar.getTime())));
    }

    public void toastMessage(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void errorMessage(String message, View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }

    public int validator(View view){
        if(addTransportName.getText().toString().equals("")|| addTransportName.getText().toString() == null) {
            errorMessage("Name field cannot be empty!", view);
            return -1;
        }

        if(addTransportDate.getText().toString().equals("")||addTransportDate.getText().toString() == null){
            errorMessage("Date field cannot be empty!", view);
            return -1;
        }

        if(addTransportPrice.getText().toString().equals("")||addTransportPrice.getText().toString() == null){
            errorMessage("Price field cannot be empty!", view);
            return -1;
        }

        if(addTransportPrice.getText().toString().equals("0")){
            errorMessage("Price field cannot be zero!", view);
            return -1;
        }

        return 0;
    }
}
