package com.example.chhaya_pc.billscanner;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FixedSpendsActivity extends AppCompatActivity {
    TextView submitHouseRent, submitMilkAmount, submitNewspaperAmount, submitElectricityAmount, submitOtherAmount;
    EditText houseRent, milkAmount,newspaperAmount, electricityAmount, otherAmount;
    TextView fixedHouseRent, fixedMilkAmount, fixedNewspaperAmount, fixedElectricityAmount, fixedOtherAmount;
    Double totalFixed=0d;
    ImageView editHouseRent, editMilkAmount, editNewspaperAmount, editElectricityAmount, editOtherFixed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_spends);

        submitHouseRent=(TextView)findViewById(R.id.submit_house_rent);

        submitMilkAmount=(TextView)findViewById(R.id.submit_milk_amount);
        submitElectricityAmount=(TextView)findViewById(R.id.submit_electricity_amount);
        submitNewspaperAmount=(TextView)findViewById(R.id.submit_newspaper_amount);
        submitOtherAmount=(TextView)findViewById(R.id.submit_other_amount);

        houseRent=(EditText)findViewById(R.id.houserent);
        milkAmount=(EditText)findViewById(R.id.milkamount);
        newspaperAmount=(EditText)findViewById(R.id.newspaper_amount);
        electricityAmount=(EditText)findViewById(R.id.electricity_amount);
        otherAmount=(EditText)findViewById(R.id.other_amount);

        fixedHouseRent=(TextView)findViewById(R.id.houserent_textview);
        fixedElectricityAmount=(TextView)findViewById(R.id.electricity_textview);
        fixedMilkAmount=(TextView)findViewById(R.id.milk_textview);
        fixedNewspaperAmount=(TextView)findViewById(R.id.newspaper_textview);
        fixedOtherAmount=(TextView)findViewById(R.id.other_textview);

        editElectricityAmount=(ImageView)findViewById(R.id.electricity_edit);
        editHouseRent=(ImageView)findViewById(R.id.houserent_edit);
        editMilkAmount=(ImageView)findViewById(R.id.milk_edit);
        editNewspaperAmount=(ImageView)findViewById(R.id.newspaper_edit);
        editOtherFixed=(ImageView)findViewById(R.id.other_edit);
        SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase database = sqLiteHelper.getReadableDatabase();
        Cursor cursor = database.query("FIXED_EXPENSES", null, null, null, null, null, null);
        ArrayList<FixedSpends> fixedSpends = new ArrayList<FixedSpends>();
        FixedSpends fixedSpend;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                if(!cursor.getString(2).equals("")){
                fixedSpend = new FixedSpends();
                fixedSpend.setID(cursor.getInt(0));
                fixedSpend.setExpenseName(cursor.getString(1));
                fixedSpend.setValue(cursor.getString(2));
                fixedSpends.add(fixedSpend);
                Log.d("TAG: EXPENSE NAME: ",fixedSpend.getExpenseName());
                Log.d("TAG: Value: ",fixedSpend.getValue());}
            }
        }
        cursor.close();
        database.close();
        for(int i=0;i<fixedSpends.size();i++){
            totalFixed+=Double.parseDouble(fixedSpends.get(i).getValue().toString());

            switch (fixedSpends.get(i).getExpenseName()){
                case "House Rent":
                    fixedHouseRent.setVisibility(View.VISIBLE);
                    houseRent.setVisibility(View.GONE);
                    submitHouseRent.setVisibility(View.GONE);
                    fixedHouseRent.setText(fixedSpends.get(i).getExpenseName()+": "+fixedSpends.get(i).getValue());
                    editHouseRent.setVisibility(View.VISIBLE);
                    break;
                case "Other fixed expenses":
                    fixedOtherAmount.setVisibility(View.VISIBLE);
                    otherAmount.setVisibility(View.GONE);
                    submitOtherAmount.setVisibility(View.GONE);
                    fixedOtherAmount.setText(fixedSpends.get(i).getExpenseName()+": "+fixedSpends.get(i).getValue());
                    editOtherFixed.setVisibility(View.VISIBLE);
                    break;
                case "Newspaper amount":
                    fixedNewspaperAmount.setVisibility(View.VISIBLE);
                    newspaperAmount.setVisibility(View.GONE);
                    submitNewspaperAmount.setVisibility(View.GONE);
                    fixedNewspaperAmount.setText(fixedSpends.get(i).getExpenseName()+": "+fixedSpends.get(i).getValue());
                    editNewspaperAmount.setVisibility(View.VISIBLE);
                    break;
                case "Electricity Amount":
                    fixedElectricityAmount.setVisibility(View.VISIBLE);
                    electricityAmount.setVisibility(View.GONE);
                    submitElectricityAmount.setVisibility(View.GONE);
                    fixedElectricityAmount.setText(fixedSpends.get(i).getExpenseName()+": "+fixedSpends.get(i).getValue());
                    editElectricityAmount.setVisibility(View.VISIBLE);
                    break;
                case "Milk Amount":
                    fixedMilkAmount.setVisibility(View.VISIBLE);
                    milkAmount.setVisibility(View.GONE);
                    submitMilkAmount.setVisibility(View.GONE);
                    fixedMilkAmount.setText(fixedSpends.get(i).getExpenseName()+": "+fixedSpends.get(i).getValue());
                    editMilkAmount.setVisibility(View.VISIBLE);
                    break;

            }
        }

        editMilkAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                milkAmount.setVisibility(View.VISIBLE);
                submitMilkAmount.setVisibility(View.VISIBLE);
                editMilkAmount.setVisibility(View.GONE);
                fixedMilkAmount.setVisibility(View.GONE);
                new SQLiteHelper(getApplicationContext()).updateFixedSpends("Milk Amount","");
            }
        });

        editElectricityAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                electricityAmount.setVisibility(View.VISIBLE);
                submitElectricityAmount.setVisibility(View.VISIBLE);
                editElectricityAmount.setVisibility(View.GONE);
                fixedElectricityAmount.setVisibility(View.GONE);
                new SQLiteHelper(getApplicationContext()).updateFixedSpends("Electricity Amount","");
            }
        });

        editNewspaperAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newspaperAmount.setVisibility(View.VISIBLE);
                submitNewspaperAmount.setVisibility(View.VISIBLE);
                editNewspaperAmount.setVisibility(View.GONE);
                fixedNewspaperAmount.setVisibility(View.GONE);
                new SQLiteHelper(getApplicationContext()).updateFixedSpends("Newspaper amount","");
            }
        });

        editOtherFixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otherAmount.setVisibility(View.VISIBLE);
                submitOtherAmount.setVisibility(View.VISIBLE);
                editOtherFixed.setVisibility(View.GONE);
                fixedOtherAmount.setVisibility(View.GONE);
                new SQLiteHelper(getApplicationContext()).updateFixedSpends("Other fixed expenses","");
            }
        });

        editHouseRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                houseRent.setVisibility(View.VISIBLE);
                submitHouseRent.setVisibility(View.VISIBLE);
                editHouseRent.setVisibility(View.GONE);
                fixedHouseRent.setVisibility(View.GONE);
                new SQLiteHelper(getApplicationContext()).updateFixedSpends("House Rent","");
            }
        });


        submitHouseRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG: Total fixed",totalFixed+"");
                if(houseRent.getText()!=null) {
                    if(Double.parseDouble(houseRent.getText().toString())+totalFixed>Double.parseDouble(new Preferences(getApplicationContext()).getMonthlyIncome()))
                        Toast.makeText(getApplicationContext(), "Exceeding income funds!", Toast.LENGTH_SHORT).show();
                    else {
                        SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
                        SQLiteDatabase database = sqLiteHelper.getReadableDatabase();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("EXPENSE_NAME", "House Rent");
                        contentValues.put("VALUE", houseRent.getText().toString());
                        database.insert("FIXED_EXPENSES", null, contentValues);
                        database.close();
                        fixedHouseRent.setVisibility(View.VISIBLE);
                        houseRent.setVisibility(View.GONE);
                        submitHouseRent.setVisibility(View.GONE);
                        editHouseRent.setVisibility(View.VISIBLE);
                        fixedHouseRent.setText("House Rent: " + houseRent.getText().toString());
                        totalFixed+=Double.parseDouble(houseRent.getText().toString());
                    }

                }

            }
        });

        submitOtherAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otherAmount.getText()!=null) {
                    if (Double.parseDouble(otherAmount.getText().toString()) + totalFixed > Double.parseDouble(new Preferences(getApplicationContext()).getMonthlyIncome()))
                        Toast.makeText(getApplicationContext(), "Exceeding income funds!", Toast.LENGTH_SHORT).show();
                    else {
                        SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
                        SQLiteDatabase database = sqLiteHelper.getReadableDatabase();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("EXPENSE_NAME", "Other fixed expenses");
                        contentValues.put("VALUE", otherAmount.getText().toString());
                        database.insert("FIXED_EXPENSES", null, contentValues);
                        database.close();
                        fixedOtherAmount.setVisibility(View.VISIBLE);
                        otherAmount.setVisibility(View.GONE);
                        submitOtherAmount.setVisibility(View.GONE);
                        editOtherFixed.setVisibility(View.VISIBLE);
                        fixedOtherAmount.setText("Other fixed expenses: " + otherAmount.getText().toString());
                        totalFixed+=Double.parseDouble(otherAmount.getText().toString());
                    }
                }
            }
        });

        submitNewspaperAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newspaperAmount.getText()!=null) {
                    if (Double.parseDouble(newspaperAmount.getText().toString()) + totalFixed > Double.parseDouble(new Preferences(getApplicationContext()).getMonthlyIncome()))
                        Toast.makeText(getApplicationContext(), "Exceeding income funds!", Toast.LENGTH_SHORT).show();
                    else {
                        SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
                        SQLiteDatabase database = sqLiteHelper.getReadableDatabase();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("EXPENSE_NAME", "Newspaper amount");
                        contentValues.put("VALUE", newspaperAmount.getText().toString());
                        database.insert("FIXED_EXPENSES", null, contentValues);
                        database.close();
                        fixedNewspaperAmount.setVisibility(View.VISIBLE);
                        newspaperAmount.setVisibility(View.GONE);
                        submitNewspaperAmount.setVisibility(View.GONE);
                        editNewspaperAmount.setVisibility(View.VISIBLE);
                        fixedNewspaperAmount.setText("Newspaper amount: " + newspaperAmount.getText().toString());
                        totalFixed+=Double.parseDouble(newspaperAmount.getText().toString());
                    }
                }
            }
        });

        submitElectricityAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(electricityAmount.getText()!=null) {
                    if (Double.parseDouble(electricityAmount.getText().toString()) + totalFixed > Double.parseDouble(new Preferences(getApplicationContext()).getMonthlyIncome()))
                        Toast.makeText(getApplicationContext(), "Exceeding income funds!", Toast.LENGTH_SHORT).show();
                    else {
                        SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
                        SQLiteDatabase database = sqLiteHelper.getReadableDatabase();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("EXPENSE_NAME", "Electricity Amount");
                        contentValues.put("VALUE", electricityAmount.getText().toString());
                        database.insert("FIXED_EXPENSES", null, contentValues);
                        database.close();
                        fixedElectricityAmount.setVisibility(View.VISIBLE);
                        electricityAmount.setVisibility(View.GONE);
                        submitElectricityAmount.setVisibility(View.GONE);
                        editElectricityAmount.setVisibility(View.VISIBLE);
                        fixedElectricityAmount.setText("Electricity Amount: " + electricityAmount.getText().toString());
                        totalFixed+=Double.parseDouble(electricityAmount.getText().toString());

                    }
                }
            }
        });

        submitMilkAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(milkAmount.getText()!=null) {
                    if (Double.parseDouble(milkAmount.getText().toString()) + totalFixed > Double.parseDouble(new Preferences(getApplicationContext()).getMonthlyIncome()))
                        Toast.makeText(getApplicationContext(), "Exceeding income funds!", Toast.LENGTH_SHORT).show();
                    else {
                        SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
                        SQLiteDatabase database = sqLiteHelper.getReadableDatabase();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("EXPENSE_NAME", "Milk Amount");
                        contentValues.put("VALUE", milkAmount.getText().toString());
                        database.insert("FIXED_EXPENSES", null, contentValues);
                        database.close();
                        fixedMilkAmount.setVisibility(View.VISIBLE);
                        milkAmount.setVisibility(View.GONE);
                        submitMilkAmount.setVisibility(View.GONE);
                        editMilkAmount.setVisibility(View.VISIBLE);
                        fixedMilkAmount.setText("Milk Amount: " + milkAmount.getText().toString());
                        totalFixed+=Double.parseDouble(milkAmount.getText().toString());
                    }
                }
            }
        });


    }
}
