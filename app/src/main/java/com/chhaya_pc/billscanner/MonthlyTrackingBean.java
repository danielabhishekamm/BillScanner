package com.example.chhaya_pc.billscanner;

/**
 * Created by SAMUEL-pc on 2/8/2018.
 */

public class MonthlyTrackingBean {
int ID;
String timeStamp;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    String category;
String amount;
}
