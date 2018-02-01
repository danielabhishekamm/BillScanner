package com.example.chhaya_pc.billscanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

TextView setLimitTextView;
EditText setLimitEditText;
Button okButton;
Button camButton;
TextView maxMonthLimit;
TextView lastBillValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lastBillValue=(TextView)findViewById(R.id.last_bill_value);
        camButton=(Button) findViewById(R.id.cam_button);
        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, com.example.chhaya_pc.billscanner.CamActivity.class);
                startActivity(intent);
            }
        });
        if(getIntent().getStringExtra("total")!=null && getIntent().getStringExtra("total").length()!=0)
        {
            lastBillValue.setText(getIntent().getStringExtra("total").replace(",",""));
            Toast.makeText(this,getIntent().getStringExtra("total").replace(",",""),Toast.LENGTH_SHORT ).show();

        }

        setLimitTextView=(TextView)findViewById(R.id.setMaxTextView);
        setLimitEditText=(EditText)findViewById(R.id.setMaxEditText);
        okButton=(Button)findViewById(R.id.setMaxSubmit);
        maxMonthLimit =(TextView)findViewById(R.id.maxMonthValue);
        setLimitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLimitEditText.setVisibility(View.VISIBLE);
                okButton.setVisibility(View.VISIBLE);
            }
        });


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setLimitEditText.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(),"Enter a valid value",Toast.LENGTH_SHORT).show();
                else {
                    maxMonthLimit.setText(setLimitEditText.getText().toString());
                    setLimitEditText.setVisibility(View.INVISIBLE);
                    okButton.setVisibility(View.INVISIBLE);
                }
            }
        });







    }
}
