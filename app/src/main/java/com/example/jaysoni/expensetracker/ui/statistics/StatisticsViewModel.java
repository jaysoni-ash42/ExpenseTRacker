package com.example.jaysoni.expensetracker.ui.statistics;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jaysoni.expensetracker.Roomdatabase.ExpenseModel;
import com.example.jaysoni.expensetracker.Roomdatabase.IncomeModel;
import com.example.jaysoni.expensetracker.Roomdatabase.PiechartData;
import com.example.jaysoni.expensetracker.Roomdatabase.Repository;

import java.util.List;

public class StatisticsViewModel extends AndroidViewModel{
    Repository repository;

    public StatisticsViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
    }

    public LiveData<List<IncomeModel>> getAllIncome(String date) {
        return repository.getAllIncome(date);
    }

    public LiveData<List<ExpenseModel>> getAllExpense(String date) {
        return repository.getAllExpense(date);
    }
    public LiveData<List<PiechartData>> getExpense(String date1,String date2)
    {
       return repository.getExpense(date1,date2);
    }
    public LiveData<List<PiechartData>> getIncome(String date1, String date2)
    {
        return repository.getIncome(date1,date2);
    }

}
