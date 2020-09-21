package com.example.jaysoni.expensetracker.Roomdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Income")
public class IncomeModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    private int Id;
    @ColumnInfo(name = "Time")
    private String Time;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    @ColumnInfo(name = "Category")
    private String Category;
    @ColumnInfo(name = "Amount")
    private String Amount;
    @ColumnInfo(name = "Note")
    private String Note;

    public IncomeModel(String time, String category, String amount, String note) {
        Time = time;
        Category = category;
        Amount = amount;
        Note = note;
    }

    @Ignore
    public IncomeModel(int id, String time, String category, String amount, String note) {
        Id = id;
        Time = time;
        Category = category;
        Amount = amount;
        Note = note;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public IncomeModel() {
    }
}
