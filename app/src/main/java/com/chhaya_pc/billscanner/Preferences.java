package com.chhaya_pc.billscanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by SAMUEL-pc on 2/3/2018.
 */

public class Preferences {

    private SharedPreferences prefs;

    public Preferences(Context context) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(context);

    }


    public void setMonthlyIncome(String monthlyIncome){ prefs.edit().putString("monthly_income",monthlyIncome);}

    public String getMonthlyIncome(){ return prefs.getString("monthly_income","DEFAULT");}

    public void setFixedSpendsNames(String fixedSpendsNames){ prefs.edit().putString("fixed_spends_names",fixedSpendsNames);}

    public String getFixedSpendsNames(){ return prefs.getString("fixed_spends_names","");}

    public void setFixedSpendsValues(String fixedSpendsValues){ prefs.edit().putString("fixed_spends_values",fixedSpendsValues);}

    public String getFixedSpendsValues(){ return prefs.getString("fixed_spends_values","");}

    public String getPledge(){ return prefs.getString("pledge","");}
    public void clearLocalSession(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

}