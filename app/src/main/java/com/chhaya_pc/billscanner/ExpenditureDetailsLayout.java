package com.chhaya_pc.billscanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpenditureDetailsLayout extends AppCompatActivity {

    float totalOnCat=0f;
    float currentYearOnCat=0f;
    int numberOfYears;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure_details_layout);






        BarChart entertainmentBarChart = (BarChart) findViewById(R.id.entertainment_barchart);

        setVariablesForYearlyGraph("Entertainment and Leisure");
        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
        bargroup1.add(new BarEntry(totalOnCat/numberOfYears, 0));


        ArrayList<BarEntry> bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(currentYearOnCat, 0));


        BarDataSet barDataSet1 = new BarDataSet(bargroup1, "Average Yearly Expenditure");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        BarDataSet barDataSet2 = new BarDataSet(bargroup2, "Yearly Expenditure-2018");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Entertainment and Leisure");


        ArrayList<BarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

        BarData data = new BarData((List)labels, (List)dataSets);
        entertainmentBarChart.setData(data);

        BarChart travelBarChart = (BarChart) findViewById(R.id.travel_barchart);

        setVariablesForYearlyGraph("Travel");
        bargroup1 = new ArrayList<>();
        bargroup1.add(new BarEntry(totalOnCat/numberOfYears, 0));


        bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(currentYearOnCat, 0));



         barDataSet1 = new BarDataSet(bargroup1, "Average Yearly Expenditure");
        barDataSet1.setColors(ColorTemplate.JOYFUL_COLORS);
        barDataSet2 = new BarDataSet(bargroup2, "Yearly Expenditure-2018");
        barDataSet2.setColors(ColorTemplate.JOYFUL_COLORS);

        labels = new ArrayList<String>();
        labels.add("Travel");


        dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

         data = new BarData((List)labels, (List)dataSets);

        travelBarChart.setData(data);


        BarChart foodBarChart = (BarChart) findViewById(R.id.food_barchart);


        setVariablesForYearlyGraph("Food");
        bargroup1 = new ArrayList<>();
        bargroup1.add(new BarEntry(totalOnCat/numberOfYears, 0));


        bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(currentYearOnCat, 0));



        barDataSet1 = new BarDataSet(bargroup1, "Average Yearly Expenditure");
        barDataSet1.setColors(ColorTemplate.LIBERTY_COLORS);
        barDataSet2 = new BarDataSet(bargroup2, "Yearly Expenditure-2018");
        barDataSet2.setColors(ColorTemplate.LIBERTY_COLORS);

        labels = new ArrayList<String>();
        labels.add("Food and Drinks");


        dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

        data = new BarData((List)labels, (List)dataSets);
        foodBarChart.setData(data);

        BarChart shoppingBarChart = (BarChart) findViewById(R.id.shopping_barchart);


        setVariablesForYearlyGraph("Shopping");
        bargroup1 = new ArrayList<>();
        bargroup1.add(new BarEntry(totalOnCat/numberOfYears, 0));


        bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(currentYearOnCat, 0));



        barDataSet1 = new BarDataSet(bargroup1, "Average Yearly Expenditure");
        barDataSet1.setColors(ColorTemplate.PASTEL_COLORS);
        barDataSet2 = new BarDataSet(bargroup2, "Yearly Expenditure-2018");
        barDataSet2.setColors(ColorTemplate.PASTEL_COLORS);

        labels = new ArrayList<String>();
        labels.add("Shopping");


        dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

        data = new BarData((List)labels, (List)dataSets);
        shoppingBarChart.setData(data);

        BarChart medicineBarChart = (BarChart) findViewById(R.id.medicine_barchart);


        setVariablesForYearlyGraph("Medicine");
        bargroup1 = new ArrayList<>();
        bargroup1.add(new BarEntry(totalOnCat/numberOfYears, 0));


        bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(currentYearOnCat, 0));



        barDataSet1 = new BarDataSet(bargroup1, "Average Yearly Expenditure");
        barDataSet1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        barDataSet2 = new BarDataSet(bargroup2, "Yearly Expenditure-2018");
        barDataSet2.setColors(ColorTemplate.VORDIPLOM_COLORS);

        labels = new ArrayList<String>();
        labels.add("Medicine");


        dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

        data = new BarData((List)labels, (List)dataSets);
        medicineBarChart.animateY(1000);
        medicineBarChart.animateX(1000);
        medicineBarChart.setData(data);

        BarChart miscallaneousBarChart = (BarChart) findViewById(R.id.miscallaneous_barchart);


        setVariablesForYearlyGraph("Miscallaneous");
        bargroup1 = new ArrayList<>();
        bargroup1.add(new BarEntry(totalOnCat/numberOfYears, 0));


        bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(currentYearOnCat, 0));



        barDataSet1 = new BarDataSet(bargroup1, "Average Yearly Expenditure");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet2 = new BarDataSet(bargroup2, "Yearly Expenditure-2018");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        labels = new ArrayList<String>();
        labels.add("Miscallaneous");


        dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

        data = new BarData((List)labels, (List)dataSets);
        miscallaneousBarChart.setData(data);
    }

    public void setVariablesForYearlyGraph(String cat){

        SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
        List<MonthlyTrackingBean> beansList=sqLiteHelper.getAllSpends();

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        List<String>years=new ArrayList<>();
        totalOnCat=0f;
        currentYearOnCat=0f;
        for(int i=0;i<beansList.size();i++){
            MonthlyTrackingBean bean = beansList.get(i);
            String date = bean.getTimeStamp();
            String splits[]=date.split("-");
            years.add(splits[2]);
            if(bean.getCategory().equalsIgnoreCase(cat))
            {
                totalOnCat+=Double.parseDouble(bean.getAmount());
                if(splits[2].equalsIgnoreCase(formattedDate.split("-")[2]))
                    currentYearOnCat+=Double.parseDouble(bean.getAmount());
            }

        }
        Set<String> uniqueyears = new HashSet<String>(years);
        years.clear();
        years.addAll(uniqueyears);
        numberOfYears=years.size();
    }

}
