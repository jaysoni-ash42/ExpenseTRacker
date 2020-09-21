package com.example.jaysoni.expensetracker.Roomdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
@Entity(tableName = "piechartdata")
public class PiechartData {

    @ColumnInfo(name="Amt")
    private String Amt;
    @ColumnInfo(name="Name")
    private String Name;

    public String getAmt() {
        return Amt;
    }

    public void setAmt(String amt) {
        Amt = amt;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public PiechartData(String amt, String name) {
        Amt = amt;
        Name = name;
    }

    public PiechartData(){}
}
