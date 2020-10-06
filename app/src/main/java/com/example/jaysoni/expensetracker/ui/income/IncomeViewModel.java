package com.example.jaysoni.expensetracker.ui.income;

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

public class IncomeViewModel extends AndroidViewModel {
    Repository repository;

    public IncomeViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }


    public LiveData<List<IncomeModel>> getAllIncome(String date) {
        return repository.getAllIncome(date);
    }

    public LiveData<List<IncomeModel>> getAllIncome(String date1, String date2) {
        return repository.getAllIncome(date1, date2);
    }

    public void insertIncome(IncomeModel incomeModel) {
        repository.insertIncome(incomeModel);
    }

    public void deleteIncome(IncomeModel incomeModel) {
        repository.deleteIncome(incomeModel);
    }
    public LiveData<String> getSumIncome(String date)
    {
        return repository.getSumIncome(date);
    }

    public LiveData<String> getSumIncome(String date1,String date2)
    {
        return repository.getSumIncome(date1,date2);
    }
    public LiveData<List<IncomeModel>> getAllIncome()
    {
        return repository.getAllIncome();
    }
    public LiveData<String> getSumAllIncome()
    {
        return repository.getSumIncome();
    }

    public void updateIncome(IncomeModel model)
    {
        repository.updateIncome(model);
    }
}
