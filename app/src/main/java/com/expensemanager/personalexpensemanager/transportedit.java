package com.expensemanager.personalexpensemanager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


import Database.TransportDB;
import Entities.Transport;

public class transportedit extends AppCompatActivity {

    private Transport transport;
    private Calendar calendar;
    EditText editTransportDate;
    EditText editTransportName;
    EditText editTransportDescription;
    EditText editTransportPrice;
    ImageView editTransportButton;
    ImageView deleteTransportbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportedit);
        Intent intent = getIntent();
        this.transport = (Transport) intent.getSerializableExtra("Transport");
        setTexts();
        initializeListners();
    }

    public void setTexts() {
        this.editTransportName = (EditText) findViewById(R.id.editTransportName);
        this.editTransportName.setText(transport.getName());

        this.editTransportDescription = (EditText) findViewById(R.id.editTransportDescription);
        this.editTransportDescription.setText(transport.getDescription());

        this.editTransportDate = (EditText) findViewById(R.id.editTransportDate);
        this.editTransportDate.setText(transport.getDate());

        this.editTransportPrice = (EditText) findViewById(R.id.editTransportPrice);
        this.editTransportPrice.setText(String.valueOf(transport.getPrice()));

        this.editTransportButton = (ImageView) findViewById(R.id.editTransportButton);
        this.deleteTransportbutton = (ImageView) findViewById(R.id.transportEditDelete);
    }

    public void initializeListners() {

        this.editTransportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validator(view) != -1)
                    updateData(view);
            }
        });

        this.deleteTransportbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(false);
                builder.setTitle("Confirm");
                builder.setMessage("Are You Sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TransportDB transportDB = new TransportDB(getBaseContext());
                        if (transportDB.delete(transport.getID())) {
                            Context context = getApplicationContext();
                            CharSequence text = "Transport Deleted Sucessfully";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            Intent intent1 = new Intent(transportedit.this, transportsum.class);
                            startActivity(intent1);
                        } else {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getBaseContext());
                            builder.setCancelable(false);
                            builder1.setMessage("Fail");
                            builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
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

        editTransportDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(transportedit.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String format = "dd/MM/YY";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        editTransportDate.setText(sdf.format((calendar.getTime())));
    }

    public void updateData(View view) {

        TransportDB transportDB = new TransportDB(getBaseContext());
        transport.setName(editTransportName.getText().toString());
        transport.setDescription(editTransportDescription.getText().toString());
        transport.setDate(editTransportDate.getText().toString());
        transport.setPrice(Double.parseDouble(editTransportPrice.getText().toString()));

        if (transportDB.update(transport)) {
            Context context = getApplicationContext();
            CharSequence text = "Transport Updated Sucessfully";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Intent intent1 = new Intent(transportedit.this, transportsum.class);
            startActivity(intent1);
        } else {
            errorMessage("Fail", view);
        }
    }

    public void errorMessage(String message, View view) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(view.getContext());
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
        if(editTransportName.getText().toString().equals("")|| editTransportName.getText().toString() == null) {
            errorMessage("Name field cannot be empty!", view);
            return -1;
        }

        if(editTransportDate.getText().toString().equals("")||editTransportDate.getText().toString() == null){
            errorMessage("Date field cannot be empty!", view);
            return -1;
        }

        if(editTransportPrice.getText().toString().equals("")||editTransportPrice.getText().toString() == null){
            errorMessage("Price field cannot be empty!", view);
            return -1;
        }

        if(editTransportPrice.getText().toString().equals("0")){
            errorMessage("Price field cannot be zero!", view);
            return -1;
        }

        return 0;
    }
}
