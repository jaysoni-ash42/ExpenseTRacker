package com.example.jaysoni.expensetracker.Roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {IncomeModel.class, ExpenseModel.class}, version = 1, exportSchema = false)
public abstract class ExpenseDatabase extends RoomDatabase {
    private static String Db_name = "ExpenseDatabase";

    public abstract DatabaseDao dao();

    private static ExpenseDatabase Instance;

    public static synchronized ExpenseDatabase getInstance(Context mcontext) {
        if (Instance == null) {
            Instance = Room.databaseBuilder(mcontext, ExpenseDatabase.class, Db_name)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return Instance;
    }

}
