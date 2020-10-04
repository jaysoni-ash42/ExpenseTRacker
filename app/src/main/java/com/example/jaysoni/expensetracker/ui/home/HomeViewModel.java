package com.example.jaysoni.expensetracker.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jaysoni.expensetracker.Roomdatabase.ExpenseModel;
import com.example.jaysoni.expensetracker.Roomdatabase.IncomeModel;
import com.example.jaysoni.expensetracker.Roomdatabase.Repository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {


    Repository repository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }


    public LiveData<List<IncomeModel>> getAllIncome(String date) {
        return repository.getAllIncome(date);
    }

    public LiveData<List<ExpenseModel>> getAllExpense(String date) {
        return repository.getAllExpense(date);
    }
    public LiveData<Integer> getSumIncome(String date)
    {
        return repository.getSumIncome(date);
    }
    public LiveData<Integer> getSumExpense(String date)
    {
        return repository.getSumExpense(date);
    }


}