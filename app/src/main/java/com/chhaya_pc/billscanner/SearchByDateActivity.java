package com.example.chhaya_pc.billscanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SearchByDateActivity extends AppCompatActivity {

    DatePicker datePickerStart;
    DatePicker datePickerEnd;
    int startMonth,startYear,startDayOfMonth;
    int endMonth,endYear,endDayOfMonth;
    TextView next;
    String startDateString, endDateString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_date);
        getSupportActionBar().setTitle("Select Start Date");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        datePickerStart = (DatePicker)findViewById(R.id.date_picker_start);
        datePickerEnd = (DatePicker)findViewById(R.id.date_picker_end);
        next =(TextView)findViewById(R.id.date_next);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        datePickerStart.setMaxDate(System.currentTimeMillis());

        datePickerStart.init(year,month,day,null);
        datePickerEnd.setMaxDate(System.currentTimeMillis());
        datePickerEnd.init(year,month,day,null);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(datePickerStart.getVisibility()==View.VISIBLE) {
                    getSupportActionBar().setTitle("Select End Date");
                    startMonth = datePickerStart.getMonth();
                    startDayOfMonth = datePickerStart.getDayOfMonth();
                    startYear = datePickerStart.getYear();

                    if(startDayOfMonth<10)
                    startDateString = "0"+startDayOfMonth+"/0"+(startMonth+1)+"/"+startYear+" 00:00:00";
                    else
                        startDateString = startDayOfMonth+"/0"+(startMonth+1)+"/"+startYear+" 00:00:00";


                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    try {
                        Date date = sdf.parse(startDateString);
                        datePickerEnd.setMinDate(date.getTime());
                        datePickerEnd.setMaxDate(System.currentTimeMillis());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    datePickerStart.setVisibility(View.GONE);
                    datePickerEnd.setVisibility(View.VISIBLE);

                }
                else{
                    endDayOfMonth=datePickerEnd.getDayOfMonth();
                    endMonth=datePickerEnd.getMonth();
                    endYear=datePickerEnd.getYear();

                    if(endDayOfMonth<10)
                        endDateString = "0"+endDayOfMonth+"/0"+(endMonth+1)+"/"+endYear+" 23:59:59";
                    else
                        endDateString = endDayOfMonth+"/0"+(endMonth+1)+"/"+endYear+" 23:59:59";
                    Intent intent = new Intent(SearchByDateActivity.this, MonthlyDetailsActivity.class);
                    intent.putExtra("origin","search");
                    intent.putExtra("startDate",startDateString);
                    intent.putExtra("endDate",endDateString);

                    startActivity(intent);
                    finish();
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
