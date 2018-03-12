package com.example.chhaya_pc.billscanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class SpendDetailsActivity extends AppCompatActivity {

    RelativeLayout yearlyLayout;
    RelativeLayout monthlyLayout;
    RelativeLayout weeklyLayout;
    RelativeLayout toodayLayout;
    TextView yearlySpendsText;
    TextView monthlySpendsText;
    TextView weekelySpendsText;
    TextView todaySpendsText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend_details);

        yearlyLayout=(RelativeLayout)findViewById(R.id.yearly_spends_layout);
        monthlyLayout=(RelativeLayout)findViewById(R.id.monthly_layout);
        weeklyLayout=(RelativeLayout)findViewById(R.id.weekly_spends_layout);
        toodayLayout=(RelativeLayout)findViewById(R.id.todays_spends_layout);


        yearlySpendsText=(TextView)findViewById(R.id.yealry_spends);
        monthlySpendsText=(TextView)findViewById(R.id.monthly_spends);
        weekelySpendsText=(TextView)findViewById(R.id.weekly_spends);
        todaySpendsText=(TextView)findViewById(R.id.todays_spends);
        SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
        List<MonthlyTrackingBean> beansList = sqLiteHelper.getAllSpends();
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        String splits[]=formattedDate.split("-");
        String year = splits[2];
        String month = splits[1];

        Log.d("year",splits[2]);
        Log.d("month",splits[1]);
        Log.d("day",splits[0]);
        Double yearlySpends=0d;
        Double monthlySpends=0d;
        Double weeklySpends=0d;
        Double todaysSpends=0d;


        for (int i = 0; i < beansList.size(); i++) {
            MonthlyTrackingBean bean = beansList.get(i);
            String date = bean.getTimeStamp();
            String dateSplits[]=date.split("-");
            if(date.contains(year))
                yearlySpends+=Double.parseDouble(bean.getAmount());

            if(date.contains(month))
                monthlySpends+=Double.parseDouble(bean.getAmount());

            if(date.equalsIgnoreCase(formattedDate))
                todaysSpends+=Double.parseDouble(bean.getAmount());
            if(Double.parseDouble(splits[0])-Double.parseDouble(dateSplits[0])<=7)
                weeklySpends+=Double.parseDouble(bean.getAmount());
        }

        yearlySpendsText.setText("Rs. "+yearlySpends+"");
        monthlySpendsText.setText("Rs. "+monthlySpends+"");
        weekelySpendsText.setText("Rs. "+weeklySpends+"");
        todaySpendsText.setText("Rs. "+todaysSpends+"");

        yearlyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpendDetailsActivity.this, ExpenditureDetailsLayout.class);
                startActivity(intent);
            }
        });

    }
}
