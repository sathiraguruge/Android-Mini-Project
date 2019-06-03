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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import Database.FoodDB;
import Entities.Food;

public class food extends AppCompatActivity {

    private Calendar calendar;
    EditText addFoodDate;
    EditText addFoodName;
    EditText addFoodDescription;
    EditText addFoodPrice;
    ImageView addFoodButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        initialize();
        initializeListners();
    }

    private void initialize() {
        this.addFoodDate = (EditText) findViewById(R.id.addFoodDate);
        this.addFoodName = (EditText) findViewById(R.id.addFoodName);
        this.addFoodPrice = (EditText) findViewById(R.id.addFoodPrice);
        this.addFoodDescription = (EditText) findViewById(R.id.addFoodDescription);
        this.addFoodButton = (ImageView) findViewById(R.id.addFoodButton);
    }

    private void initializeListners() {

        this.addFoodButton.setOnClickListener(new View.OnClickListener() {
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

        addFoodDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(food.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(){
        String format = "dd/MM/YY";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        addFoodDate.setText(sdf.format((calendar.getTime())));
    }


    public void insertData(View view){
        FoodDB foodDB = new FoodDB(getBaseContext());
        Food food = new Food();

        food.setName(addFoodName.getText().toString());
        food.setDescription(addFoodDescription.getText().toString());
        food.setDate(addFoodDate.getText().toString());
        food.setPrice(Double.parseDouble(addFoodPrice.getText().toString()));

        if(foodDB.create(food)){
            toastMessage("Food Added Sucessfully");
            Intent intent = new Intent(food.this, foodsum.class);
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
        if(addFoodName.getText().toString().equals("")|| addFoodName.getText().toString() == null) {
            errorMessage("Name field cannot be empty!", view);
            return -1;
        }

        if(addFoodDate.getText().toString().equals("")||addFoodDate.getText().toString() == null){
            errorMessage("Date field cannot be empty!", view);
            return -1;
        }

        if(addFoodPrice.getText().toString().equals("")||addFoodPrice.getText().toString() == null){
            errorMessage("Price field cannot be empty!", view);
            return -1;
        }

        if(addFoodPrice.getText().toString().equals("0")){
            errorMessage("Price field cannot be zero!", view);
            return -1;
        }

        return 0;
    }
}
