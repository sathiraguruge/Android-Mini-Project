package com.expensemanager.personalexpensemanager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import Database.CardDB;
import Entities.Card;

public class creditcardedit extends AppCompatActivity {

    private ImageView buttonSave;
    private ImageView buttonDelete;
    private EditText addCreditName;
    private EditText addCreditDescription;
    private EditText addCardNumber;
    private EditText addCreditDate;
    private RadioGroup radioGroup;
    private RadioButton buttonAmex;
    private RadioButton buttonMaster;
    private RadioButton buttonVisa;
    private RadioButton radioButton;
    private Card card;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditcardedit);
        Intent intent = getIntent();
        this.card = (Card) intent.getSerializableExtra("Card");
        setTexts();
        initializeListners();
    }

    public void setTexts() {
        this.addCreditName = (EditText) findViewById(R.id.editCreditName);
        this.addCreditName.setText(card.getName());

        this.addCardNumber = (EditText) findViewById(R.id.editCardNumber);
        this.addCardNumber.setText(card.getCardNo());

        this.addCreditDate = (EditText) findViewById(R.id.editCardDate);
        this.addCreditDate.setText(card.getDate());

        this.addCreditDescription = (EditText) findViewById(R.id.editCreditDescription);
        this.addCreditDescription.setText(card.getDescription());


        buttonAmex = (RadioButton) findViewById(R.id.buttonAmex);
        buttonMaster = (RadioButton) findViewById(R.id.buttonMaster);
        buttonVisa = (RadioButton) findViewById(R.id.buttonVisa);

        String type = card.getType();
        if (type.equals("VISA"))
            buttonVisa.setChecked(true);
        else if (type.equals("MASTERCARD"))
            buttonMaster.setChecked(true);
        else if (type.equals("AMEX"))
            buttonAmex.setChecked(true);

        this.radioGroup = (RadioGroup) findViewById(R.id.editcardradioButtongroup);
        this.buttonDelete = (ImageView) findViewById(R.id.cardEditDelete);
        this.buttonSave = (ImageView) findViewById(R.id.cardEditSave);
    }

    public void updateData(View view) {

        CardDB cardDB = new CardDB(getBaseContext());
        card.setName(addCreditName.getText().toString());
        card.setCardNo(addCardNumber.getText().toString());
        card.setDescription(addCreditDescription.getText().toString());
        card.setExpDate(addCreditDate.getText().toString());
        card.setType(radioButton.getText().toString());

        if (cardDB.update(card)) {
            Context context = getApplicationContext();
            CharSequence text = "Card Updated Sucessfully";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Intent intent1 = new Intent(creditcardedit.this, creditcardsum.class);
            startActivity(intent1);
        } else {
            errorMessage("Fail", view);
        }
    }

    public void initializeListners() {

        this.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedID = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedID);

                int validateValue = validator(view);
                if (validateValue != -1)
                    updateData(view);
            }
        });


        this.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(false);
                builder.setTitle("Confirm");
                builder.setMessage("Are You Sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CardDB contactDB = new CardDB(getBaseContext());
                        if (contactDB.delete(card.getID())) {
                            Context context = getApplicationContext();
                            CharSequence text = "Card Deleted Sucessfully";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            Intent intent1 = new Intent(creditcardedit.this, creditcardsum.class);
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

        addCreditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(creditcardedit.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String format = "MM/YY";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        addCreditDate.setText(sdf.format((calendar.getTime())));
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

    public int validator(View view) {
        if (addCreditName.getText().toString().equals("") || addCreditName.getText().toString() == null) {
            errorMessage("Name field cannot be empty!", view);
            return -1;
        }

        if (addCardNumber.getText().toString().equals("") || addCardNumber.getText().toString() == null) {
            errorMessage("Number field cannot be empty!", view);
            return -1;
        }

        if (addCreditDate.getText().toString().equals("") || addCreditDate.getText().toString() == null) {
            errorMessage("Expiry Date field cannot be empty!", view);
            return -1;
        }

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            errorMessage("Please Choose card Type!", view);
            return -1;
        }

        if (addCreditDescription.getText().toString().equals("") || addCreditDescription.getText().toString() == null) {
            addCreditDescription.setText("");
        }


        String cardType = radioButton.getText().toString();
        String cardNo = addCardNumber.getText().toString();
        int cardLength = cardNo.length();

        if (cardType.equals("AMEX")) {

            if ((cardLength != 15)) {
                errorMessage("AMEX Card Number should be of 15 digits!", view);
                return -1;
            }
            String firstTwo = cardNo.substring(0, 2);
            int firstTwoNum = Integer.parseInt(firstTwo);

            if (firstTwoNum != 34 && firstTwoNum != 37) {
                errorMessage("AMEX Card Number should start with 34 or 37", view);
                return -1;
            }
        } else if (cardType.equals("MASTERCARD")) {
            if ((cardLength != 16)) {
                errorMessage("Master Card Number should be of 16 digits!", view);
                return -1;
            }

            String firstTwo = cardNo.substring(0, 2);
            String firstVals = cardNo.substring(0, 6);
            int firstTwoNum = Integer.parseInt(firstTwo);
            int firstvalues = Integer.parseInt(firstVals);

            if ((firstTwoNum < 51 || firstTwoNum > 55) && (firstvalues < 222100 || firstvalues > 272099)) {
                errorMessage("MASTERCARD Card Number should start within 51-55 or 222100-272099 range", view);
                return -1;
            }
        } else if (cardType.equals("VISA")) {
            if ((cardLength != 13 && cardLength != 16 && cardLength != 19)) {
                errorMessage("VISA Card Number should be of 13/16/19 digits!", view);
                return -1;
            }
            String first = cardNo.substring(0, 1);
            int firstNum = Integer.parseInt(first);
            if (firstNum != 4) {
                errorMessage("VISA Card Number should start with 4", view);
                return -1;
            }
        }
        return 0;
    }
}

