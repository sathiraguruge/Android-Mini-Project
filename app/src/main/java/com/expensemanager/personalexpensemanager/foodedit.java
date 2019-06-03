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

import Database.FoodDB;
import Entities.Food;


public class foodedit extends AppCompatActivity {

    private Food food;
    private Calendar calendar;
    EditText editFoodDate;
    EditText editFoodName;
    EditText editFoodDescription;
    EditText editFoodPrice;
    ImageView editFoodButton;
    ImageView deleteFoodbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodedit);
        Intent intent = getIntent();
        this.food = (Food) intent.getSerializableExtra("Food");
        setTexts();
        initializeListners();
    }

    public void setTexts() {
        this.editFoodName = (EditText) findViewById(R.id.editFoodName);
        this.editFoodName.setText(food.getName());

        this.editFoodDescription = (EditText) findViewById(R.id.editFoodDescription);
        this.editFoodDescription.setText(food.getDescription());

        this.editFoodDate = (EditText) findViewById(R.id.editFoodDate);
        this.editFoodDate.setText(food.getDate());

        this.editFoodPrice = (EditText) findViewById(R.id.editFoodPrice);
        this.editFoodPrice.setText(String.valueOf(food.getPrice()));

        this.editFoodButton = (ImageView) findViewById(R.id.editFoodButton);
        this.deleteFoodbutton = (ImageView) findViewById(R.id.FoodEditDelete);
    }

    public void initializeListners() {

        this.editFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validator(view) != -1)
                    updateData(view);
            }
        });

        this.deleteFoodbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(false);
                builder.setTitle("Confirm");
                builder.setMessage("Are You Sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FoodDB foodDB = new FoodDB(getBaseContext());
                        if (foodDB.delete(food.getID())) {
                            Context context = getApplicationContext();
                            CharSequence text = "Food Deleted Sucessfully";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            Intent intent1 = new Intent(foodedit.this, foodsum.class);
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

        editFoodDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(foodedit.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String format = "dd/MM/YY";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        editFoodDate.setText(sdf.format((calendar.getTime())));
    }

    public void updateData(View view) {

        FoodDB foodDB = new FoodDB(getBaseContext());
        food.setName(editFoodName.getText().toString());
        food.setDescription(editFoodDescription.getText().toString());
        food.setDate(editFoodDate.getText().toString());
        food.setPrice(Double.parseDouble(editFoodPrice.getText().toString()));

        if (foodDB.update(food)) {
            Context context = getApplicationContext();
            CharSequence text = "Transport Updated Sucessfully";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Intent intent1 = new Intent(foodedit.this, foodsum.class);
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
        if(editFoodName.getText().toString().equals("")|| editFoodName.getText().toString() == null) {
            errorMessage("Name field cannot be empty!", view);
            return -1;
        }

        if(editFoodDate.getText().toString().equals("")||editFoodDate.getText().toString() == null){
            errorMessage("Date field cannot be empty!", view);
            return -1;
        }

        if(editFoodPrice.getText().toString().equals("")||editFoodPrice.getText().toString() == null){
            errorMessage("Price field cannot be empty!", view);
            return -1;
        }

        if(editFoodPrice.getText().toString().equals("0")){
            errorMessage("Price field cannot be zero!", view);
            return -1;
        }

        return 0;
    }
}
