package com.expensemanager.personalexpensemanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import Database.CardDB;
import Entities.Card;

public class creditcard extends AppCompatActivity {

    private ImageView buttonSave;
    private EditText addCreditName;
    private EditText addCardNumber;
    private EditText addCreditDescription;
    private EditText addCreditDate;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private int selectedID;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditcard);
        this.initialize();
        this.startListener();
    }

    public void initialize(){
        this.addCreditName = (EditText) findViewById(R.id.addCreditName);
        this.addCardNumber = (EditText) findViewById(R.id.addCardNumber);
        this.addCreditDate = (EditText) findViewById(R.id.addCreditDate);
        this.addCreditDescription = (EditText) findViewById(R.id.addCreditDescription);
        this.radioGroup = (RadioGroup) findViewById(R.id.cardradioButtongroup);
        this.buttonSave = findViewById(R.id.buttonSave);
    }


    public void startListener(){
        this.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedID = radioGroup.getCheckedRadioButtonId();
                radioButton =  (RadioButton) findViewById(selectedID);

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

        addCreditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(creditcard.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(){
        String format = "MM/YY";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        addCreditDate.setText(sdf.format((calendar.getTime())));
    }

    public void insertData(View view){
        CardDB cardDB = new CardDB(getBaseContext());
        Card card = new Card();
        card.setName(addCreditName.getText().toString());
        card.setCardNo(addCardNumber.getText().toString());
        card.setExpDate(addCreditDate.getText().toString());
        card.setType(radioButton.getText().toString());
        card.setDescription(addCreditDescription.getText().toString());

        if(cardDB.create(card)){
            toastMessage("Card Added Sucessfully");
            Intent intent = new Intent(creditcard.this, creditcardsum.class);
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

    public int validator(View view){
        if(addCreditName.getText().toString().equals("")|| addCreditName.getText().toString() == null) {
            errorMessage("Name field cannot be empty!", view);
            return -1;
        }

        if(addCardNumber.getText().toString().equals("")||addCardNumber.getText().toString() == null){
            errorMessage("Number field cannot be empty!", view);
            return -1;
        }

        if(addCreditDate.getText().toString().equals("")||addCreditDate.getText().toString() == null){
            errorMessage("Expiry Date field cannot be empty!", view);
            return -1;
        }

        if(radioGroup.getCheckedRadioButtonId() == -1){
            errorMessage("Please Choose card Type!", view);
            return -1;
        }

        if(addCreditDescription.getText().toString().equals("")||addCreditDescription.getText().toString() == null){
            addCreditDescription.setText("");
        }

        String cardType = radioButton.getText().toString();
        String cardNo = addCardNumber.getText().toString();
        int cardLength = cardNo.length();

        if(cardType.equals("AMEX")) {

            if ((cardLength != 15)) {
                errorMessage("AMEX Card Number should be of 15 digits!", view);
                return -1;
            }
            String firstTwo = cardNo.substring(0,2);
            int firstTwoNum = Integer.parseInt(firstTwo);

            if( firstTwoNum != 34 && firstTwoNum != 37 ) {
                errorMessage("AMEX Card Number should start with 34 or 37", view);
                return -1;
            }
        }else if(cardType.equals("MASTERCARD")){
            if ((cardLength != 16)) {
                errorMessage("Master Card Number should be of 16 digits!", view);
                return -1;
            }

            String firstTwo = cardNo.substring(0,2);
            String firstVals = cardNo.substring(0,6);
            int firstTwoNum = Integer.parseInt(firstTwo);
            int firstvalues = Integer.parseInt(firstVals);

            if((firstTwoNum < 51 || firstTwoNum > 55) && (firstvalues < 222100 || firstvalues > 272099)) {
                errorMessage("MASTERCARD Card Number should start within 51-55 or 222100-272099 range", view);
                return -1;
            }
        }else if (cardType.equals("VISA")){
            if ((cardLength != 13 && cardLength != 16 && cardLength != 19)) {
                errorMessage("VISA Card Number should be of 13/16/19 digits!", view);
                return -1;
            }
            String first = cardNo.substring(0,1);
            int firstNum = Integer.parseInt(first);
            if( firstNum != 4 ) {
                errorMessage("VISA Card Number should start with 4", view);
                return -1;
            }

        }

        return 0;
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

}
