package com.example.chhaya_pc.billscanner;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class  FixedSpendsActivity extends AppCompatActivity {
    TextView submitHouseRent, submitMilkAmount, submitNewspaperAmount, submitElectricityAmount, submitOtherAmount;
    EditText houseRent, milkAmount,newspaperAmount, electricityAmount, otherAmount;
    TextView fixedHouseRent, fixedMilkAmount, fixedNewspaperAmount, fixedElectricityAmount, fixedOtherAmount;
    Double totalFixed=0d;
    ImageView editHouseRent, editMilkAmount, editNewspaperAmount, editElectricityAmount, editOtherFixed;
    Button rentPaid,milkPaid,newsPaid, electricityPaid, otherPaid;
    static int NOTIFICATION_ID = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_spends);


        getSupportActionBar().setTitle("Regular Bills");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        rentPaid = (Button) findViewById(R.id.house_rent_paid);
        milkPaid = (Button) findViewById(R.id.milk_paid);
        newsPaid = (Button) findViewById(R.id.newspaper_paid);
        electricityPaid = (Button) findViewById(R.id.electricity_paid);
        otherPaid = (Button) findViewById(R.id.other_paid);

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
                    rentPaid.setVisibility(View.VISIBLE);
                    break;
                case "Other fixed expenses":
                    fixedOtherAmount.setVisibility(View.VISIBLE);
                    otherAmount.setVisibility(View.GONE);
                    submitOtherAmount.setVisibility(View.GONE);
                    fixedOtherAmount.setText(fixedSpends.get(i).getExpenseName()+": "+fixedSpends.get(i).getValue());
                    editOtherFixed.setVisibility(View.VISIBLE);
                    otherPaid.setVisibility(View.VISIBLE);
                    break;
                case "Newspaper amount":
                    fixedNewspaperAmount.setVisibility(View.VISIBLE);
                    newspaperAmount.setVisibility(View.GONE);
                    submitNewspaperAmount.setVisibility(View.GONE);
                    fixedNewspaperAmount.setText(fixedSpends.get(i).getExpenseName()+": "+fixedSpends.get(i).getValue());
                    editNewspaperAmount.setVisibility(View.VISIBLE);
                    newsPaid.setVisibility(View.VISIBLE);
                    break;
                case "Electricity Amount":
                    fixedElectricityAmount.setVisibility(View.VISIBLE);
                    electricityAmount.setVisibility(View.GONE);
                    submitElectricityAmount.setVisibility(View.GONE);
                    fixedElectricityAmount.setText(fixedSpends.get(i).getExpenseName()+": "+fixedSpends.get(i).getValue());
                    editElectricityAmount.setVisibility(View.VISIBLE);
                    electricityPaid.setVisibility(View.VISIBLE);
                    break;
                case "Milk Amount":
                    fixedMilkAmount.setVisibility(View.VISIBLE);
                    milkAmount.setVisibility(View.GONE);
                    submitMilkAmount.setVisibility(View.GONE);
                    fixedMilkAmount.setText(fixedSpends.get(i).getExpenseName()+": "+fixedSpends.get(i).getValue());
                    editMilkAmount.setVisibility(View.VISIBLE);
                    milkPaid.setVisibility(View.VISIBLE);
                    break;

            }
        }

        //check if fixed spends paid in current month
        List<MonthlyTrackingBean> beansList = new SQLiteHelper(getApplicationContext()).getAllSpends();
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        String splits[] = formattedDate.split("-");
        String year = splits[2];
        String month = splits[1];
        List<String> paidFixed = new ArrayList<>();
        for(int i=0 ; i <beansList.size(); i++){
            Log.d("TAG-fixed",beansList.get(i).getCategory()+" "+beansList.get(i).getAmount());
            if(beansList.get(i).getCategory().contains("Fixed Spends")){
                Log.d(beansList.get(i).getTimeStamp(),"TAG DATE");
                if (beansList.get(i).getTimeStamp().split("-")[1].equalsIgnoreCase(month)){

                    paidFixed.add(beansList.get(i).getCategory());
                }
            }
        }
        if(paidFixed.contains("Fixed Spends - House Rent"))
        {
            rentPaid.setBackgroundColor(Color.BLACK);
            editHouseRent.setVisibility(View.GONE);
            rentPaid.setText("Already Paid");
            rentPaid.setEnabled(false);
        }

//        {
//            if(Integer.parseInt(splits[0])>5){
//                //notification for house rent
//
//                    Notification builder;
//                    Intent intent = new Intent(this, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                    String NOTIFICATION_CHANNEL_ID = "my_notification_channel";
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
//
//                        // Configure the notification channel.
//                        notificationChannel.setDescription("Channel description");
//                        notificationChannel.enableLights(true);
//                        notificationChannel.setLightColor(Color.RED);
//                        notificationChannel.setVibrationPattern(new long[]{0, 1000});
//                        notificationChannel.enableVibration(true);
//                        notificationManager.createNotificationChannel(notificationChannel);
//
//
//                        builder = new Notification.Builder(this)
//                                .setSmallIcon(R.drawable.billscanner_icon)
//
//                                .setContentTitle("Bill Payment - Reminder")
//                                .setContentText("Looks like you haven't paid house rent. Click to mark it paid.").setChannelId(NOTIFICATION_CHANNEL_ID)
//                                .setContentIntent(pendingIntent)
//                                .build();
//                    } else {
//                        builder = new Notification.Builder(this)
//                                .setSmallIcon(R.drawable.billscanner_icon)
//                                .setContentTitle("Bill Payment - Reminder")
//                                .setContentText("Looks like you haven't paid house rent. Click to mark it paid.").setContentIntent(pendingIntent)
//                                .build();
//                    }
//
//                    notificationManager.notify(NOTIFICATION_ID++, builder);
//                    Log.d("Tag:", "Notif sent");
//
//
//            }
//
//        }
        if(paidFixed.contains("Fixed Spends - Milk Amount"))
        {
            milkPaid.setBackgroundColor(Color.BLACK);
            editMilkAmount.setVisibility(View.GONE);
            milkPaid.setText("Already Paid");
            milkPaid.setEnabled(false);
        }

//        {
//            if(Integer.parseInt(splits[0])>5){
//                //notification for milk amount
//
//
//                    Notification builder;
//                    Intent intent = new Intent(this, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                    String NOTIFICATION_CHANNEL_ID = "my_notification_channel";
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
//
//                        // Configure the notification channel.
//                        notificationChannel.setDescription("Channel description");
//                        notificationChannel.enableLights(true);
//                        notificationChannel.setLightColor(Color.RED);
//                        notificationChannel.setVibrationPattern(new long[]{0, 1000});
//                        notificationChannel.enableVibration(true);
//                        notificationManager.createNotificationChannel(notificationChannel);
//
//
//                        builder = new Notification.Builder(this)
//                                .setSmallIcon(R.drawable.billscanner_icon)
//
//                                .setContentTitle("Bill Payment - Reminder")
//                                .setContentText("Looks like you haven't paid milk bill. Click to mark it paid.").setChannelId(NOTIFICATION_CHANNEL_ID)
//                                .setContentIntent(pendingIntent)
//                                .build();
//                    } else {
//                        builder = new Notification.Builder(this)
//                                .setSmallIcon(R.drawable.billscanner_icon)
//                                .setContentTitle("Bill Payment - Reminder")
//                                .setContentText("Looks like you haven't paid milk bill. Click to mark it paid.").setContentIntent(pendingIntent)
//                                .build();
//                    }
//
//                    notificationManager.notify(NOTIFICATION_ID++, builder);
//                    Log.d("Tag:", "Notif sent");
//
//
//
//        }
//
//        }
        if(paidFixed.contains("Fixed Spends - Newspaper Amount"))
        {
            newsPaid.setBackgroundColor(Color.BLACK);
            editNewspaperAmount.setVisibility(View.GONE);
            newsPaid.setText("Already Paid");
            newsPaid.setEnabled(false);
        }
//        else{
//            if(Integer.parseInt(splits[0])>5){
//                //notification for newspaper amount
//
//
//                    Notification builder;
//                    Intent intent = new Intent(this, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                    String NOTIFICATION_CHANNEL_ID = "my_notification_channel";
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
//
//                        // Configure the notification channel.
//                        notificationChannel.setDescription("Channel description");
//                        notificationChannel.enableLights(true);
//                        notificationChannel.setLightColor(Color.RED);
//                        notificationChannel.setVibrationPattern(new long[]{0, 1000});
//                        notificationChannel.enableVibration(true);
//                        notificationManager.createNotificationChannel(notificationChannel);
//
//
//                        builder = new Notification.Builder(this)
//                                .setSmallIcon(R.drawable.billscanner_icon)
//
//                                .setContentTitle("Bill Payment - Reminder")
//                                .setContentText("Looks like you haven't paid newspaper bill. Click to mark it paid.").setChannelId(NOTIFICATION_CHANNEL_ID)
//                                .setContentIntent(pendingIntent)
//                                .build();
//                    } else {
//                        builder = new Notification.Builder(this)
//                                .setSmallIcon(R.drawable.billscanner_icon)
//                                .setContentTitle("Bill Payment - Reminder")
//                                .setContentText("Looks like you haven't paid newspaper bill. Click to mark it paid.").setContentIntent(pendingIntent)
//                                .build();
//                    }
//
//                    notificationManager.notify(NOTIFICATION_ID++, builder);
//                    Log.d("Tag:", "Notif sent");
//
//
//
//
//            }
//
//        }
        if(paidFixed.contains("Fixed Spends - Electricity Amount"))
        {
            electricityPaid.setBackgroundColor(Color.BLACK);
            editElectricityAmount.setVisibility(View.GONE);
            electricityPaid.setText("Already Paid");
            electricityPaid.setEnabled(false);
        }
//        else {
//
//            if(Integer.parseInt(splits[0])>5){
//                //notification for electricity amount
//
//
//
//                    Notification builder;
//                    Intent intent = new Intent(this, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                    String NOTIFICATION_CHANNEL_ID = "my_notification_channel";
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
//
//                        // Configure the notification channel.
//                        notificationChannel.setDescription("Channel description");
//                        notificationChannel.enableLights(true);
//                        notificationChannel.setLightColor(Color.RED);
//                        notificationChannel.setVibrationPattern(new long[]{0, 1000});
//                        notificationChannel.enableVibration(true);
//                        notificationManager.createNotificationChannel(notificationChannel);
//
//
//                        builder = new Notification.Builder(this)
//                                .setSmallIcon(R.drawable.billscanner_icon)
//
//                                .setContentTitle("Bill Payment - Reminder")
//                                .setContentText("Looks like you haven't paid electricity bill. Click to mark it paid.").setChannelId(NOTIFICATION_CHANNEL_ID)
//                                .setContentIntent(pendingIntent)
//                                .build();
//                    } else {
//                        builder = new Notification.Builder(this)
//                                .setSmallIcon(R.drawable.billscanner_icon)
//                                .setContentTitle("Bill Payment - Reminder")
//                                .setContentText("Looks like you haven't paid electricity bill. Click to mark it paid.").setContentIntent(pendingIntent)
//                                .build();
//                    }
//
//                    notificationManager.notify(NOTIFICATION_ID++, builder);
//                    Log.d("Tag:", "Notif sent");
//
//
//
//
//            }
//        }
        if(paidFixed.contains("Fixed Spends - Other Amount"))
        {
            otherPaid.setBackgroundColor(Color.BLACK);
            editOtherFixed.setVisibility(View.GONE);
            otherPaid.setText("Already Paid");
            otherPaid.setEnabled(false);
        }


        editMilkAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                milkAmount.setVisibility(View.VISIBLE);
                submitMilkAmount.setVisibility(View.VISIBLE);
                editMilkAmount.setVisibility(View.GONE);
                fixedMilkAmount.setVisibility(View.GONE);
                milkPaid.setVisibility(View.GONE);
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
                electricityPaid.setVisibility(View.GONE);
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
                newsPaid.setVisibility(View.GONE);
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
                otherPaid.setVisibility(View.GONE);
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
                rentPaid.setVisibility(View.GONE);
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
                        rentPaid.setVisibility(View.VISIBLE);
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
                        otherPaid.setVisibility(View.VISIBLE);
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
                        newsPaid.setVisibility(View.VISIBLE);
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
                        electricityPaid.setVisibility(View.VISIBLE);
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
                        milkPaid.setVisibility(View.VISIBLE);
                        totalFixed+=Double.parseDouble(milkAmount.getText().toString());
                    }
                }
            }
        });

    rentPaid.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(rentPaid.getText().toString().equalsIgnoreCase("Mark Paid")) {
                Double fixedValue = Double.parseDouble(fixedHouseRent.getText().toString().split(": ")[1]);
                // inserting into db
                SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());

                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c.getTime());
                MonthlyTrackingBean bean = new MonthlyTrackingBean();
                bean.setTimeStamp(formattedDate);
                bean.setAmount(fixedValue + "");
                bean.setCategory("Fixed Spends - House Rent");
                sqLiteHelper.addSpend(bean);

                rentPaid.setText("Already Paid");
                rentPaid.setBackgroundColor(Color.BLACK);
                editHouseRent.setVisibility(View.GONE);
            }


        }
    });

        milkPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(milkPaid.getText().toString().equalsIgnoreCase("Mark Paid")) {
                    Double fixedValue = Double.parseDouble(fixedMilkAmount.getText().toString().split(": ")[1]);
                    // inserting into db
                    SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());

                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => " + c.getTime());

                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(c.getTime());
                    MonthlyTrackingBean bean = new MonthlyTrackingBean();
                    bean.setTimeStamp(formattedDate);
                    bean.setAmount(fixedValue + "");
                    bean.setCategory("Fixed Spends - Milk Amount");
                    sqLiteHelper.addSpend(bean);

                    milkPaid.setText("Already Paid");
                    milkPaid.setBackgroundColor(Color.BLACK);
                    editMilkAmount.setVisibility(View.GONE);
                }
            }
        });

        newsPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newsPaid.getText().toString().equalsIgnoreCase("Mark Paid")) {
                    Double fixedValue = Double.parseDouble(fixedNewspaperAmount.getText().toString().split(": ")[1]);
                    // inserting into db
                    SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());

                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => " + c.getTime());

                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(c.getTime());
                    MonthlyTrackingBean bean = new MonthlyTrackingBean();
                    bean.setTimeStamp(formattedDate);
                    bean.setAmount(fixedValue + "");
                    bean.setCategory("Fixed Spends - Newspaper Amount");
                    sqLiteHelper.addSpend(bean);

                    newsPaid.setText("Already Paid");
                    newsPaid.setBackgroundColor(Color.BLACK);
                    editNewspaperAmount.setVisibility(View.GONE);
                }
            }
        });
        electricityPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(electricityPaid.getText().toString().equalsIgnoreCase("Mark Paid")) {
                    Double fixedValue = Double.parseDouble(fixedElectricityAmount.getText().toString().split(": ")[1]);
                    // inserting into db
                    SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());

                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => " + c.getTime());

                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(c.getTime());
                    MonthlyTrackingBean bean = new MonthlyTrackingBean();
                    bean.setTimeStamp(formattedDate);
                    bean.setAmount(fixedValue + "");
                    bean.setCategory("Fixed Spends - Electricity Amount");
                    sqLiteHelper.addSpend(bean);

                    electricityPaid.setText("Already Paid");
                    electricityPaid.setBackgroundColor(Color.BLACK);
                    editElectricityAmount.setVisibility(View.GONE);
                }
            }
        });
        otherPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otherPaid.getText().toString().equalsIgnoreCase("Mark Paid")) {
                    Double fixedValue = Double.parseDouble(fixedOtherAmount.getText().toString().split(": ")[1]);
                    // inserting into db
                    SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());

                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => " + c.getTime());

                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(c.getTime());
                    MonthlyTrackingBean bean = new MonthlyTrackingBean();
                    bean.setTimeStamp(formattedDate);
                    bean.setAmount(fixedValue + "");
                    bean.setCategory("Fixed Spends - Other Amount");
                    sqLiteHelper.addSpend(bean);

                otherPaid.setText("Already Paid");
                otherPaid.setBackgroundColor(Color.BLACK);
                editOtherFixed.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
