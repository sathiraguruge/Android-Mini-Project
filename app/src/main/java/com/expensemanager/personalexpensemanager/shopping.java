package com.expensemanager.personalexpensemanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class shopping extends AppCompatActivity {

    private Calendar calendar;
    EditText addShopDate;
    EditText addShopName;
    EditText addShopDesc;
    EditText addShopPrice;
    ImageView addShopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        initialize();
        initializeListners();
    }

    private void initialize() {
        this.addShopDate = (EditText) findViewById(R.id.addShopDate);
        this.addShopName = (EditText) findViewById(R.id.addShopName);
        this.addShopPrice = (EditText) findViewById(R.id.addShopPrice);
        this.addShopDesc = (EditText) findViewById(R.id.addShopDesc);
        this.addShopBtn = (ImageView) findViewById(R.id.addShopBtn);
    }

    private void initializeListners() {

        this.addShopBtn.setOnClickListener(new View.OnClickListener() {
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

        addShopDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(shopping.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(){
        String format = "dd/MM/YY";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        addShopDate.setText(sdf.format((calendar.getTime())));
    }


    public void insertData(View view){
        ShoppingDB shoppingDB = new ShoppingDB(getBaseContext());
        Shopping shopping = new Shopping();

        shopping.setName(addShopName.getText().toString());
        shopping.setDescription(addShopDesc.getText().toString());
        shopping.setDate(addShopDate.getText().toString());
        shopping.setPrice(Double.parseDouble(addShopPrice.getText().toString()));

        if(shoppingDB.create(shopping)){
            toastMessage("Shopping Added Sucessfully");
            Intent intent = new Intent(shopping.this, shoppingsum.class);
            startActivity(intent);
        }
        else {
            errorMessage("Fail", view);
        }

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
        if(addShopName.getText().toString().equals("")|| addShopName.getText().toString() == null) {
            errorMessage("Name field cannot be empty!", view);
            return -1;
        }

        if(addShopDate.getText().toString().equals("")||addShopDate.getText().toString() == null){
            errorMessage("Date field cannot be empty!", view);
            return -1;
        }

        if(addShopPrice.getText().toString().equals("")||addShopPrice.getText().toString() == null){
            errorMessage("Price field cannot be empty!", view);
            return -1;
        }

        if(addShopPrice.getText().toString().equals("0")){
            errorMessage("Price field cannot be zero!", view);
            return -1;
        }

        return 0;
    }
}

