package com.example.chhaya_pc.billscanner;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthlyDetailsActivity extends AppCompatActivity {

    private List<Integer> images = new ArrayList<Integer>();
    private List<String> amounts = new ArrayList<String>();
    private List<String> dates = new ArrayList<String>();
    private List<String> categories = new ArrayList<String>();
    private MonthlyDetailsAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_details);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.monthlysalesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MonthlyDetailsAdapter(this,images,categories,amounts,dates);
        recyclerView.setAdapter(mAdapter);

        List<MonthlyTrackingBean> beansList = new SQLiteHelper(getApplicationContext()).getAllSpends();
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        String splits[] = formattedDate.split("-");
        String year = splits[2];
        String month = splits[1];

        for(int i = 0 ; i < beansList.size(); i++){
            if( (getIntent().getStringExtra("origin").equalsIgnoreCase("monthly") && beansList.get(i).getTimeStamp().contains(year) && beansList.get(i).getTimeStamp().contains(month))
                    || (getIntent().getStringExtra("origin").equalsIgnoreCase("weekly") && beansList.get(i).getTimeStamp().contains(year) && beansList.get(i).getTimeStamp().contains(month) &&( Integer.parseInt(splits[0])-Integer.parseInt(beansList.get(i).getTimeStamp().split("-")[0]) <=7))
                    || (getIntent().getStringExtra("origin").equalsIgnoreCase("daily") && beansList.get(i).getTimeStamp().contains(year) && beansList.get(i).getTimeStamp().contains(month) &&( Integer.parseInt(splits[0])-Integer.parseInt(beansList.get(i).getTimeStamp().split("-")[0]) ==0))){
               switch (beansList.get(i).getCategory()){
                   case "Entertainment and Leisure":
                       images.add(R.drawable.entertainment);
                       break;
                   case "Travel":
                       images.add(R.drawable.travel);
                       break;
                   case "Food":
                       images.add(R.drawable.food);
                       break;
                   case "Shopping":
                       images.add(R.drawable.shopping);
                       break;
                   case "Medicine":
                       images.add(R.drawable.medicine);
                       break;
                   default: //"Miscallaneous"
                       images.add(R.drawable.miscellaneous);
                       break;
               }
                dates.add("Entered on: "+beansList.get(i).getTimeStamp());
                categories.add(beansList.get(i).getCategory());
                amounts.add("₹ "+beansList.get(i).getAmount());
            }

        }
        Log.d("TAG amounts", amounts.size()+"");
        Log.d("TAG images", images.size()+"");


        mAdapter.notifyDataSetChanged();



    }
}
