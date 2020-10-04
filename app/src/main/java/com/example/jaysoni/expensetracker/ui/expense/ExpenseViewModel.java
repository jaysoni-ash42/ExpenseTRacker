package com.example.jaysoni.expensetracker.ui.expense;

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

public class ExpenseViewModel extends AndroidViewModel {
    Repository repository;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }


    public LiveData<List<ExpenseModel>> getAllExpense(String date) {
        return repository.getAllExpense(date);
    }

    public LiveData<List<ExpenseModel>> getAllExpense(String date1, String date2) {
        return repository.getAllExpense(date1, date2);
    }

    public void insertExpense(ExpenseModel expenseModel) {
        repository.insertExpense(expenseModel);
    }

    public void deleteExpense(ExpenseModel expenseModel) {
        repository.deleteExpense(expenseModel);
    }

    public LiveData<Integer> getSumExpense(String date) {
        return repository.getSumExpense(date);
    }


    public LiveData<Integer> getSumExpense(String date1, String date2) {
        return repository.getSumExpense(date1, date2);
    }
    public LiveData<List<ExpenseModel>> getAllExpense()
    {
        return repository.getAllExpense();
    }
    public LiveData<Integer> getAllSumExpense()
    {
        return repository.getSumExpense();
    }
    public LiveData<Integer> getAllSumIncome()
    {
        return repository.getSumIncome();
    }



}
