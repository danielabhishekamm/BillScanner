package com.example.chhaya_pc.billscanner;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.chhaya_pc.billscanner.NotificationUtils.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnChartValueSelectedListener, OnChartGestureListener {
    Button camButton;
    TextView totalMonthlySpent;
    TextView lastBillValue;
    TextView monthlyIncome;
    ImageView editIncome;
    EditText addMonthlyIncome;
    TextView submitIncome;
    Preferences preferences;

    TextView submitPledge, pledgeTextView, pledgeEditText;
    ImageView editPledge;
    Double scannedBillAmount;
    TextView lastBillCategory;
    TextView moveToFixedSpends;
    Double currentMonthlySpend = 0d;
    RelativeLayout expendituresLayout;
    Float totalforMonth = 0f;
    static int NOTIFICATION_ID = 10;
    float entertainmentPerc = 0f, travelPerc = 0f, foodPerc = 0f, shoppingPerc = 0f, medicinePerc = 0f, miscPerc = 0f, fixedPerc = 0f;
    final String[] categories = new String[]{"Entertainment and Leisure", "Travel", "Food", "Shopping", "Medicine", "Miscallaneous"};
    public static final int[] PIE_COLORS = {
            Color.rgb(192, 255, 140), Color.rgb(255, 247, 140), Color.rgb(255, 208, 140),

    };
    Double totalFixed = 0d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fixedBillsNotify();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Home");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_activity_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        preferences = new Preferences(getApplicationContext());
        lastBillValue = (TextView) findViewById(R.id.last_bill_value);
        camButton = (Button) findViewById(R.id.cam_button);

        submitIncome = (TextView) findViewById(R.id.submit_income);
        addMonthlyIncome = (EditText) findViewById(R.id.add_monthly_income);
        monthlyIncome = (TextView) findViewById(R.id.monthly_income);
        editIncome = (ImageView) findViewById(R.id.edit_monthly_income);
        // totalMonthlySpent=(TextView)findViewById(R.id.total_spent);
        expendituresLayout = (RelativeLayout) findViewById(R.id.expenditure_layout);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        setCategorySpendsValues();
        setPieChartValues();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setLineChartValues();
        }


        List<MonthlyTrackingBean> monthlyTrackingBeans = new SQLiteHelper(getApplicationContext()).getAllSpends();
        Log.d("TAG", monthlyTrackingBeans.size() + "");
        for (int i = 0; i < monthlyTrackingBeans.size(); i++) {
            currentMonthlySpend += Double.parseDouble(monthlyTrackingBeans.get(i).getAmount());
        }


        //  totalMonthlySpent.setText("Rs. "+currentMonthlySpend+"");
        submitPledge = (TextView) findViewById(R.id.submitpledge);
        pledgeEditText = (EditText) findViewById(R.id.pledgeamountedittext);
        pledgeTextView = (TextView) findViewById(R.id.pledgeamounttextview);
        editPledge = (ImageView) findViewById(R.id.edit_pledge);
        lastBillCategory = (TextView) findViewById(R.id.last_bill_category);

        editPledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitPledge.setVisibility(View.VISIBLE);
                pledgeEditText.setVisibility(View.VISIBLE);
                editPledge.setVisibility(View.GONE);
                pledgeTextView.setVisibility(View.GONE);
            }
        });
        lastBillValue.setText("\u20B9 " + PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("lastBillValue", "0.00"));
        lastBillCategory.setText("Spent on: " + PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("lastBillCategory", ""));
        // moveToFixedSpends=(TextView)findViewById(R.id.move_to_fixed_spends_text);
//        moveToFixedSpends.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!new Preferences(getApplicationContext()).getMonthlyIncome().equals("DEFAULT")){
//                Intent intent = new Intent(MainActivity.this,FixedSpendsActivity.class);
//                startActivity(intent);}
//                else
//                {
//                    Toast.makeText(getApplicationContext(),"Enter monthly income",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        findViewById(R.id.move_to_fixed_spends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!new Preferences(getApplicationContext()).getMonthlyIncome().equals("DEFAULT")) {
                    Intent intent = new Intent(MainActivity.this, FixedSpendsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Enter monthly income", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.check_fixed_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!new Preferences(getApplicationContext()).getMonthlyIncome().equals("DEFAULT")) {
                    Intent intent = new Intent(MainActivity.this, FixedSpendsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Enter monthly income", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (!new Preferences(getApplicationContext()).getPledge().equals("")) {
            pledgeTextView.setVisibility(View.VISIBLE);
            editPledge.setVisibility(View.VISIBLE);
            pledgeEditText.setVisibility(View.GONE);
            submitPledge.setVisibility(View.GONE);
            pledgeTextView.setText("\u20B9 " + new Preferences(getApplicationContext()).getPledge());
        }
        submitPledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pledgeEditText.getText() != null && pledgeEditText.getText().toString().length() > 0)
                    if (Double.parseDouble(pledgeEditText.getText().toString()) >= 0d) {
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("pledge", pledgeEditText.getText().toString()).commit();
                        pledgeTextView.setVisibility(View.VISIBLE);
                        pledgeEditText.setVisibility(View.GONE);
                        editPledge.setVisibility(View.VISIBLE);
                        submitPledge.setVisibility(View.GONE);
                        pledgeTextView.setText("\u20B9 " + pledgeEditText.getText() + "");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            setLineChartValues();
                        }
                    } else
                        Toast.makeText(getApplicationContext(), "Pledge percentage should be valid.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Pledge percentage should be valid.", Toast.LENGTH_SHORT).show();


            }
        });


        editIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMonthlyIncome.setVisibility(View.VISIBLE);
                submitIncome.setVisibility(View.VISIBLE);
                monthlyIncome.setVisibility(View.GONE);
                editIncome.setVisibility(View.GONE);
            }
        });

        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("monthly_income", null) != null) {
            Log.d("TAG", "monthly income NOT NULL" + preferences.getMonthlyIncome());
            addMonthlyIncome.setVisibility(View.GONE);
            submitIncome.setVisibility(View.GONE);
            monthlyIncome.setVisibility(View.VISIBLE);
            editIncome.setVisibility(View.VISIBLE);
            monthlyIncome.setText("\u20B9 " + new Preferences(getApplicationContext()).getMonthlyIncome());

        } else
            Log.d("TAG", "monthly income NULL");

        submitIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addMonthlyIncome.getText().toString() != null && !addMonthlyIncome.getText().toString().equals("")) {
                    Log.d("TAG", "monthly income " + addMonthlyIncome.getText().toString());
                    //preferences.setMonthlyIncome(addMonthlyIncome.getText().toString());
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("monthly_income", addMonthlyIncome.getText().toString()).commit();
                    addMonthlyIncome.setVisibility(View.GONE);
                    submitIncome.setVisibility(View.GONE);
                    monthlyIncome.setVisibility(View.VISIBLE);
                    editIncome.setVisibility(View.VISIBLE);
                    Log.d("TAG", "pref: " + preferences.getMonthlyIncome());
                    monthlyIncome.setText("\u20B9 " + new Preferences(getApplicationContext()).getMonthlyIncome());
                }
            }
        });
        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CamActivity.class);
                startActivity(intent);
            }
        });
        if (getIntent().getStringExtra("total") != null && getIntent().getStringExtra("total").length() != 0) {


            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
            dialogBuilder.setView(dialogView);

            final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

            dialogBuilder.setTitle("Confirm Amount");
            dialogBuilder.setMessage("Bill Amount: Rs" + getIntent().getStringExtra("total").replace(",", ""));
            edt.setText(getIntent().getStringExtra("total").replace(",", ""));
            final Spinner dropdown = dialogView.findViewById(R.id.spinner1);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
            dropdown.setAdapter(adapter);
            dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //do something with edt.getText().toString();
                    if (edt.getText().toString() != null && !edt.getText().toString().equals("")) {
                        scannedBillAmount = Double.parseDouble(edt.getText().toString());
                        Log.d("TAG if", scannedBillAmount + "");
                    } else {
                        scannedBillAmount = Double.parseDouble(getIntent().getStringExtra("total").replace(",", ""));
                        Log.d("TAG else", scannedBillAmount + "");
                    }
                    lastBillValue.setText("\u20B9 " + scannedBillAmount + "");
                    lastBillCategory.setText("Spent on: " + categories[dropdown.getSelectedItemPosition()]);
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("lastBillValue", scannedBillAmount + "").commit();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("lastBillCategory", categories[dropdown.getSelectedItemPosition()]).commit();
                    SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());

                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => " + c.getTime());

                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(c.getTime());
                    MonthlyTrackingBean bean = new MonthlyTrackingBean();
                    bean.setTimeStamp(formattedDate);
                    bean.setAmount(scannedBillAmount + "");
                    bean.setCategory(categories[dropdown.getSelectedItemPosition()]);
                    sqLiteHelper.addSpend(bean);
                    setCategorySpendsValues();
                    setPieChartValues();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        setLineChartValues();
                    }

                }
            });

            dialogBuilder.setNegativeButton("Scan again", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //pass
                    Intent intent = new Intent(MainActivity.this, CamActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog b = dialogBuilder.create();
            b.show();


            Toast.makeText(this, getIntent().getStringExtra("total").replace(",", ""), Toast.LENGTH_SHORT).show();

        }


        expendituresLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SpendDetailsActivity.class);
                startActivity(intent);
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setLineChartValues() {
        LineChart mChart = (LineChart) findViewById(R.id.linechart);
        List<MonthlyTrackingBean> beansList = new SQLiteHelper(getApplicationContext()).getAllSpends();
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<String> xVals1 = new ArrayList<String>();
        xVals.add("1");
        xVals.add("2");
        xVals.add("3");
        xVals.add("4");
        xVals.add("5");
        xVals.add("6");
        xVals.add("7");
        xVals.add("8");
        xVals.add("9");
        xVals.add("10");
        xVals.add("11");
        xVals.add("12");
        xVals.add("13");
        xVals.add("14");
        xVals.add("15");
        xVals.add("16");
        xVals.add("17");
        xVals.add("18");
        xVals.add("19");
        xVals.add("20");
        xVals.add("21");
        xVals.add("22");
        xVals.add("23");
        xVals.add("24");
        xVals.add("25");
        xVals.add("26");
        xVals.add("27");
        xVals.add("28");
        xVals.add("29");
        xVals.add("30");


        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        String splits[] = formattedDate.split("-");
        String year = splits[2];
        String month = splits[1];
        float day1spends = 0;
        float day2spends = 0;
        float day3spends = 0;
        float day4spends = 0;
        float day5spends = 0;
        float day6spends = 0;
        float day7spends = 0;
        float day8spends = 0;
        float day9spends = 0;
        float day10spends = 0;
        float day11spends = 0;
        float day12spends = 0;
        float day13spends = 0;
        float day14spends = 0;
        float day15spends = 0;
        float day16spends = 0;
        float day17spends = 0;
        float day18spends = 0;
        float day19spends = 0;
        float day20spends = 0;
        float day21spends = 0;
        float day22spends = 0;
        float day23spends = 0;
        float day24spends = 0;
        float day25spends = 0;
        float day26spends = 0;
        float day27spends = 0;
        float day28spends = 0;
        float day29spends = 0;
        float day30spends = 0;


        for (int i = 0; i < beansList.size(); i++) {
            if (beansList.get(i).getTimeStamp().contains(year) && beansList.get(i).getTimeStamp().contains(month)) {
                if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("1") || beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("01")) {
                    day1spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("2") || beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("02")) {
                    day2spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("3") || beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("03")) {
                    day3spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("4") || beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("04")) {
                    day4spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("5") || beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("05")) {
                    day5spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("6") || beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("06")) {
                    day6spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("7") || beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("07")) {
                    day7spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("8") || beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("08")) {
                    day8spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("9") || beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("09")) {
                    day9spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("10")) {
                    day10spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("11")) {
                    day11spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("12")) {
                    day12spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("13")) {
                    day13spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("14")) {
                    day14spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("15")) {
                    day15spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("16")) {
                    day16spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("17")) {
                    day17spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("18")) {
                    day18spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("19")) {
                    day19spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("20")) {
                    day20spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("21")) {
                    day21spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("22")) {
                    day22spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("23")) {
                    day23spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("24")) {
                    day24spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("25")) {
                    day25spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("26")) {
                    day26spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("27")) {
                    day27spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("28")) {
                    day28spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("29")) {
                    day29spends += Float.parseFloat(beansList.get(i).getAmount());
                } else if (beansList.get(i).getTimeStamp().split("-")[0].equalsIgnoreCase("30")) {
                    day30spends += Float.parseFloat(beansList.get(i).getAmount());
                }
            }
        }
        List<Float> listOfDailySpendsAccumulative = new ArrayList<>();
        listOfDailySpendsAccumulative.add(day1spends);
        day2spends += day1spends;
        listOfDailySpendsAccumulative.add(day2spends);
        day3spends += day2spends;
        listOfDailySpendsAccumulative.add(day3spends);
        day4spends += day3spends;
        listOfDailySpendsAccumulative.add(day4spends);
        day5spends += day4spends;
        listOfDailySpendsAccumulative.add(day5spends);
        day6spends += day5spends;
        listOfDailySpendsAccumulative.add(day6spends);
        day7spends += day6spends;
        listOfDailySpendsAccumulative.add(day7spends);
        day8spends += day7spends;
        listOfDailySpendsAccumulative.add(day8spends);
        day9spends += day8spends;
        listOfDailySpendsAccumulative.add(day9spends);
        day10spends += day9spends;
        listOfDailySpendsAccumulative.add(day10spends);
        day11spends += day10spends;
        listOfDailySpendsAccumulative.add(day11spends);
        day12spends += day11spends;
        listOfDailySpendsAccumulative.add(day12spends);
        day13spends += day12spends;
        listOfDailySpendsAccumulative.add(day13spends);
        day14spends += day13spends;
        listOfDailySpendsAccumulative.add(day14spends);
        day15spends += day14spends;
        listOfDailySpendsAccumulative.add(day15spends);
        day16spends += day15spends;
        listOfDailySpendsAccumulative.add(day16spends);
        day17spends += day16spends;
        listOfDailySpendsAccumulative.add(day17spends);
        day18spends += day17spends;
        listOfDailySpendsAccumulative.add(day18spends);
        day19spends += day18spends;
        listOfDailySpendsAccumulative.add(day19spends);
        day20spends += day19spends;
        listOfDailySpendsAccumulative.add(day20spends);
        day21spends += day20spends;
        listOfDailySpendsAccumulative.add(day21spends);
        day22spends += day21spends;
        listOfDailySpendsAccumulative.add(day22spends);
        day23spends += day22spends;
        listOfDailySpendsAccumulative.add(day23spends);
        day24spends += day23spends;
        listOfDailySpendsAccumulative.add(day24spends);
        day25spends += day24spends;
        listOfDailySpendsAccumulative.add(day25spends);
        day26spends += day25spends;
        listOfDailySpendsAccumulative.add(day26spends);
        day27spends += day26spends;
        listOfDailySpendsAccumulative.add(day27spends);
        day28spends += day27spends;
        listOfDailySpendsAccumulative.add(day28spends);
        day29spends += day28spends;
        listOfDailySpendsAccumulative.add(day29spends);
        day30spends += day29spends;
        listOfDailySpendsAccumulative.add(day30spends);

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        float total = 0f;
        for (int i = 0; i < Integer.parseInt(splits[0]); i++) {
            total += listOfDailySpendsAccumulative.get(i);
            yVals.add(new Entry(listOfDailySpendsAccumulative.get(i), i));
            xVals1.add(xVals.get(i));
        }
        if (new Preferences(getApplicationContext()).getPledge() != null && !new Preferences(getApplicationContext()).getPledge().equals("")) {
            if (total > Float.parseFloat(new Preferences(getApplicationContext()).getPledge())) {
                Notification builder;
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                String NOTIFICATION_CHANNEL_ID = "my_notification_channel";
                int NOTIFICATION_ID = 1;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

                    // Configure the notification channel.
                    notificationChannel.setDescription("Channel description");
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.setVibrationPattern(new long[]{0, 1000});
                    notificationChannel.enableVibration(true);
                    notificationManager.createNotificationChannel(notificationChannel);


                    builder = new Notification.Builder(this)
                            .setSmallIcon(R.drawable.billscanner_icon)

                            .setContentTitle("Budget Limit Crossed! ")
                            .setContentText("Check the App to know how it happened").setChannelId(NOTIFICATION_CHANNEL_ID)
                            .setContentIntent(pendingIntent)
                            .build();
                } else {
                    builder = new Notification.Builder(this)
                            .setSmallIcon(R.drawable.billscanner_icon)
                            .setContentTitle("Budget Limit Crossed! ")
                            .setContentText("Check the App to know how it happened").setContentIntent(pendingIntent)
                            .build();
                }

                notificationManager.notify(NOTIFICATION_ID, builder);
                Log.d("Tag:", "Notif sent");

            }
        }


        LineDataSet set1;
// create a dataset and give it a type
        set1 = new LineDataSet(yVals, "Actual Spends");
        set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(0f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(0f);
        set1.setDrawFilled(true);

        if (new Preferences(getApplicationContext()).getPledge() != null && !new Preferences(getApplicationContext()).getPledge().equals("")) {
            LimitLine upper_limit = new LimitLine(Float.parseFloat(new Preferences(getApplicationContext()).getPledge()), "Budget: \u20B9" + Float.parseFloat(new Preferences(getApplicationContext()).getPledge()));
            upper_limit.setLineWidth(4f);
            upper_limit.enableDashedLine(10f, 10f, 0f);
            upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
            upper_limit.setTextSize(10f);
            YAxis leftAxis = mChart.getAxisLeft();
// reset all limit lines to avoid overlapping lines
            leftAxis.removeAllLimitLines();
            leftAxis.addLimitLine(upper_limit);

//leftAxis.setYOffset(20f);
            leftAxis.enableGridDashedLine(10f, 10f, 0f);
            leftAxis.setDrawZeroLine(false);

// limit lines are drawn behind data (and not on top)
            leftAxis.setDrawLimitLinesBehindData(true);

            mChart.getAxisRight().setEnabled(false);
        }
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1);// add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        // set data
        mChart.setData(data);
        mChart.setDescription("Monthly Expenditure");

        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
    }


    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }


    public void setPieChartValues() {

        PieChart pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(entertainmentPerc / totalforMonth * 100, 0));
        yvalues.add(new Entry(travelPerc / totalforMonth * 100, 1));
        yvalues.add(new Entry(foodPerc / totalforMonth * 100, 2));
        yvalues.add(new Entry(shoppingPerc / totalforMonth * 100, 3));
        yvalues.add(new Entry(medicinePerc / totalforMonth * 100, 4));
        yvalues.add(new Entry(miscPerc / totalforMonth * 100, 5));
        yvalues.add(new Entry(fixedPerc / totalforMonth * 100, 6));
        PieDataSet dataSet = new PieDataSet(yvalues, "Expenditure Details");
        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("");
        xVals.add("");
        xVals.add("");
        xVals.add("");
        xVals.add("");
        xVals.add("");
        xVals.add("");

        PieData data = new PieData(xVals, dataSet);

        data.setValueFormatter(new PercentFormatter());
        // Default value/number
        //data.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setData(data);
        pieChart.setDescription("Monthly Expenditure");

        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(10f);
        pieChart.setHoleRadius(10f);

        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        pieChart.setOnChartValueSelectedListener(this);

        pieChart.animateXY(1400, 1400);


    }


    public void setCategorySpendsValues() {

        List<MonthlyTrackingBean> beansList = new SQLiteHelper(getApplicationContext()).getAllSpends();
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        String splits[] = formattedDate.split("-");
        String year = splits[2];
        String month = splits[1];

        Log.d("year", splits[2]);
        Log.d("month", splits[1]);
        Log.d("day", splits[0]);
        Double yearlySpends = 0d;
        Double monthlySpends = 0d;
        Double weeklySpends = 0d;
        Double todaysSpends = 0d;


        for (int i = 0; i < beansList.size(); i++) {
            MonthlyTrackingBean bean = beansList.get(i);
            String date = bean.getTimeStamp();
            String dateSplits[] = date.split("-");
            if (date.contains(year) && date.contains(month)) {
                switch (bean.getCategory()) {
                    case "Entertainment and Leisure":
                        entertainmentPerc += Float.parseFloat(bean.getAmount());
                        break;
                    case "Travel":
                        travelPerc += Float.parseFloat(bean.getAmount());
                        break;
                    case "Food":
                        foodPerc += Float.parseFloat(bean.getAmount());
                        break;
                    case "Shopping":
                        shoppingPerc += Float.parseFloat(bean.getAmount());
                        break;
                    case "Medicine":
                        medicinePerc += Float.parseFloat(bean.getAmount());
                        break;
                    case "Miscallaneous":
                        miscPerc += Float.parseFloat(bean.getAmount());
                        break;

                }
                if (bean.getCategory().contains("Fixed Spends"))
                    fixedPerc += Float.parseFloat(bean.getAmount());

                totalforMonth += Float.parseFloat(bean.getAmount());
            }

        }


    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        setPieChartValues();
        setCategorySpendsValues();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setLineChartValues();
        }
    }

    void fixedBillsNotify() {
        List<MonthlyTrackingBean> beansList = new SQLiteHelper(getApplicationContext()).getAllSpends();
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        String splits[] = formattedDate.split("-");
        String year = splits[2];
        String month = splits[1];
        List<String> paidFixed = new ArrayList<>();
        for (int i = 0; i < beansList.size(); i++) {
            Log.d("TAG-fixed", beansList.get(i).getCategory() + " " + beansList.get(i).getAmount());
            if (beansList.get(i).getCategory().contains("Fixed Spends")) {
                Log.d(beansList.get(i).getTimeStamp(), "TAG DATE");
                if (beansList.get(i).getTimeStamp().split("-")[1].equalsIgnoreCase(month)) {

                    paidFixed.add(beansList.get(i).getCategory());
                }
            }
        }
        if (paidFixed.contains("Fixed Spends - House Rent")) {
        } else {
            if (Integer.parseInt(splits[0]) > 5) {
                //notification for house rent

                Notification builder;
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                String NOTIFICATION_CHANNEL_ID = "my_notification_channel";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

                    // Configure the notification channel.
                    notificationChannel.setDescription("Channel description");
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.setVibrationPattern(new long[]{0, 1000});
                    notificationChannel.enableVibration(true);
                    notificationManager.createNotificationChannel(notificationChannel);


                    builder = new Notification.Builder(this)
                            .setSmallIcon(R.drawable.billscanner_icon)

                            .setContentTitle("Bill Payment - Reminder")
                            .setContentText("Looks like you haven't paid house rent. Click to mark it paid.").setChannelId(NOTIFICATION_CHANNEL_ID)
                            .setContentIntent(pendingIntent)
                            .build();
                } else {
                    builder = new Notification.Builder(this)
                            .setSmallIcon(R.drawable.billscanner_icon)
                            .setContentTitle("Bill Payment - Reminder")
                            .setContentText("Looks like you haven't paid house rent. Click to mark it paid.").setContentIntent(pendingIntent)
                            .build();
                }

                notificationManager.notify(NOTIFICATION_ID++, builder);
                Log.d("Tag:", "Notif sent");


            }

        }
        if (paidFixed.contains("Fixed Spends - Milk Amount")) {
        } else {
            if (Integer.parseInt(splits[0]) > 5) {
                //notification for milk amount


                Notification builder;
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                String NOTIFICATION_CHANNEL_ID = "my_notification_channel";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

                    // Configure the notification channel.
                    notificationChannel.setDescription("Channel description");
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.setVibrationPattern(new long[]{0, 1000});
                    notificationChannel.enableVibration(true);
                    notificationManager.createNotificationChannel(notificationChannel);


                    builder = new Notification.Builder(this)
                            .setSmallIcon(R.drawable.billscanner_icon)

                            .setContentTitle("Bill Payment - Reminder")
                            .setContentText("Looks like you haven't paid milk bill. Click to mark it paid.").setChannelId(NOTIFICATION_CHANNEL_ID)
                            .setContentIntent(pendingIntent)
                            .build();
                } else {
                    builder = new Notification.Builder(this)
                            .setSmallIcon(R.drawable.billscanner_icon)
                            .setContentTitle("Bill Payment - Reminder")
                            .setContentText("Looks like you haven't paid milk bill. Click to mark it paid.").setContentIntent(pendingIntent)
                            .build();
                }

                notificationManager.notify(NOTIFICATION_ID++, builder);
                Log.d("Tag:", "Notif sent");


            }

        }
        if (paidFixed.contains("Fixed Spends - Newspaper Amount")) {

        } else {
            if (Integer.parseInt(splits[0]) > 5) {
                //notification for newspaper amount


                Notification builder;
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                String NOTIFICATION_CHANNEL_ID = "my_notification_channel";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

                    // Configure the notification channel.
                    notificationChannel.setDescription("Channel description");
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.setVibrationPattern(new long[]{0, 1000});
                    notificationChannel.enableVibration(true);
                    notificationManager.createNotificationChannel(notificationChannel);


                    builder = new Notification.Builder(this)
                            .setSmallIcon(R.drawable.billscanner_icon)

                            .setContentTitle("Bill Payment - Reminder")
                            .setContentText("Looks like you haven't paid newspaper bill. Click to mark it paid.").setChannelId(NOTIFICATION_CHANNEL_ID)
                            .setContentIntent(pendingIntent)
                            .build();
                } else {
                    builder = new Notification.Builder(this)
                            .setSmallIcon(R.drawable.billscanner_icon)
                            .setContentTitle("Bill Payment - Reminder")
                            .setContentText("Looks like you haven't paid newspaper bill. Click to mark it paid.").setContentIntent(pendingIntent)
                            .build();
                }

                notificationManager.notify(NOTIFICATION_ID++, builder);
                Log.d("Tag:", "Notif sent");


            }

        }
        if (paidFixed.contains("Fixed Spends - Electricity Amount")) {
        } else {

            if (Integer.parseInt(splits[0]) > 5) {
                //notification for electricity amount


                Notification builder;
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                String NOTIFICATION_CHANNEL_ID = "my_notification_channel";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

                    // Configure the notification channel.
                    notificationChannel.setDescription("Channel description");
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.setVibrationPattern(new long[]{0, 1000});
                    notificationChannel.enableVibration(true);
                    notificationManager.createNotificationChannel(notificationChannel);


                    builder = new Notification.Builder(this)
                            .setSmallIcon(R.drawable.billscanner_icon)

                            .setContentTitle("Bill Payment - Reminder")
                            .setContentText("Looks like you haven't paid electricity bill. Click to mark it paid.").setChannelId(NOTIFICATION_CHANNEL_ID)
                            .setContentIntent(pendingIntent)
                            .build();
                } else {
                    builder = new Notification.Builder(this)
                            .setSmallIcon(R.drawable.billscanner_icon)
                            .setContentTitle("Bill Payment - Reminder")
                            .setContentText("Looks like you haven't paid electricity bill. Click to mark it paid.").setContentIntent(pendingIntent)
                            .build();
                }

                notificationManager.notify(NOTIFICATION_ID++, builder);
                Log.d("Tag:", "Notif sent");


            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_activity_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_scan_bill) {
            // Handle the camera action
            Intent intent = new Intent(MainActivity.this, CamActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_search_by_date) {
            Intent intent = new Intent(MainActivity.this, SearchByDateActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_expense_details) {
            Intent intent = new Intent(MainActivity.this, SpendDetailsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_activity_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}