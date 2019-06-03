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

import Database.ShoppingDB;
import Entities.Shopping;

public class shoppingedit extends AppCompatActivity {

    private Shopping shopping;
    private Calendar calendar;
    EditText editShopDate;
    EditText editShopName;
    EditText editShopDescription;
    EditText editShopPrice;
    ImageView editShopButton;
    ImageView deleteShopbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingedit);
        Intent intent = getIntent();
        this.shopping = (Shopping) intent.getSerializableExtra("Shopping");
        setTexts();
        initializeListners();
    }

    public void setTexts() {
        this.editShopName = (EditText) findViewById(R.id.editShopName);
        this.editShopName.setText(shopping.getName());

        this.editShopDescription = (EditText) findViewById(R.id.editShopDescription);
        this.editShopDescription.setText(shopping.getDescription());

        this.editShopDate = (EditText) findViewById(R.id.editShopDate);
        this.editShopDate.setText(shopping.getDate());

        this.editShopPrice = (EditText) findViewById(R.id.editShopPrice);
        this.editShopPrice.setText(String.valueOf(shopping.getPrice()));

        this.editShopButton = (ImageView) findViewById(R.id.editShopButton);
        this.deleteShopbutton = (ImageView) findViewById(R.id.ShopEditDelete);
    }

    public void initializeListners() {

        this.editShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validator(view) != -1)
                    updateData(view);
            }
        });

        this.deleteShopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(false);
                builder.setTitle("Confirm");
                builder.setMessage("Are You Sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ShoppingDB shoppingDB = new ShoppingDB(getBaseContext());
                        if (shoppingDB.delete(shopping.getID())) {
                            Context context = getApplicationContext();
                            CharSequence text = "Shopping Deleted Sucessfully";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            Intent intent1 = new Intent(shoppingedit.this, shoppingsum.class);
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

        editShopDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(shoppingedit.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String format = "dd/MM/YY";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        editShopDate.setText(sdf.format((calendar.getTime())));
    }

    public void updateData(View view) {

        ShoppingDB shoppingDB = new ShoppingDB(getBaseContext());
        shopping.setName(editShopName.getText().toString());
        shopping.setDescription(editShopDescription.getText().toString());
        shopping.setDate(editShopDate.getText().toString());
        shopping.setPrice(Double.parseDouble(editShopPrice.getText().toString()));

        if (shoppingDB.update(shopping)) {
            Context context = getApplicationContext();
            CharSequence text = "Shopping Updated Sucessfully";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Intent intent1 = new Intent(shoppingedit.this, shoppingsum.class);
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
        if(editShopName.getText().toString().equals("")|| editShopName.getText().toString() == null) {
            errorMessage("Name field cannot be empty!", view);
            return -1;
        }

        if(editShopDate.getText().toString().equals("")||editShopDate.getText().toString() == null){
            errorMessage("Date field cannot be empty!", view);
            return -1;
        }

        if(editShopPrice.getText().toString().equals("")||editShopPrice.getText().toString() == null){
            errorMessage("Price field cannot be empty!", view);
            return -1;
        }

        if(editShopPrice.getText().toString().equals("0")){
            errorMessage("Price field cannot be zero!", view);
            return -1;
        }

        return 0;
    }
}
