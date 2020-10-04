package com.example.jaysoni.expensetracker.Roomdatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Query;


import java.util.List;

public class Repository {
    DatabaseDao databaseDao;
    LiveData<List<IncomeModel>> incomemodel;
    LiveData<List<ExpenseModel>> expensemodel;

    public Repository(Application Application) {
        databaseDao = ExpenseDatabase.getInstance(Application).dao();
        incomemodel = databaseDao.getAllIncome();
        expensemodel = databaseDao.getAllExpense();
    }

    public void insertIncome(IncomeModel incomeModel) {
        new InsertIncome(databaseDao).execute(incomeModel);

    }

    public void insertExpense(ExpenseModel expenseModel) {
        new InsertExpense(databaseDao).execute(expenseModel);
    }

    public void deleteIncome(IncomeModel incomeModel) {
        new DeleteIncome(databaseDao).execute(incomeModel);
    }

    public void deleteExpense(ExpenseModel expenseModel) {
        new DeleteExpense(databaseDao).execute(expenseModel);
    }

    public LiveData<List<IncomeModel>> getAllIncome() {
        return incomemodel;
    }

    public LiveData<List<ExpenseModel>> getAllExpense() {
        return expensemodel;
    }

    public LiveData<List<ExpenseModel>> getAllExpense(String date) {
        return databaseDao.getAllExpense(date);

    }

    public LiveData<List<IncomeModel>> getAllIncome(String date) {
        return databaseDao.getAllIncome(date);
    }

    public LiveData<List<ExpenseModel>> getAllExpense(String date1, String date2) {
        return databaseDao.getAllExpense(date1, date2);
    }

    public LiveData<List<IncomeModel>> getAllIncome(String date1, String date2) {
        return databaseDao.getAllIncome(date1, date2);
    }

    public LiveData<Integer> getSumIncome(String date) {
        return databaseDao.getSumIncome(date);
    }

    public LiveData<Integer> getSumExpense(String date) {
        return databaseDao.getSumExpense(date);
    }

    public LiveData<Integer> getSumIncome(String date1, String date2) {
        return databaseDao.getSumIncome(date1, date2);
    }

    public LiveData<Integer> getSumExpense(String date1, String date2) {
        return databaseDao.getSumExpense(date1, date2);
    }

    public LiveData<Integer> getSumIncome() {
        return databaseDao.getSumIncome();
    }

    public LiveData<Integer> getSumExpense() {
        return databaseDao.getSumExpense();
    }

    public LiveData<List<PiechartData>> getExpense(String date1, String date2) {
        return databaseDao.getExpense(date1, date2);
    }

    public LiveData<List<PiechartData>> getIncome(String date1, String date2) {
        return databaseDao.getIncome(date1, date2);
    }

    public LiveData<List<PiechartData>> getIncome(String date1) {
        return databaseDao.getIncome(date1);
    }

    public LiveData<List<PiechartData>> getExpense(String date1) {
        return databaseDao.getExpense(date1);
    }


    public static class InsertIncome extends AsyncTask<IncomeModel, Void, Void> {
        DatabaseDao databaseDao;

        public InsertIncome(DatabaseDao dao) {
            databaseDao = dao;
        }

        @Override
        protected Void doInBackground(IncomeModel... incomeModels) {
            databaseDao.insertIncome(incomeModels[0]);
            return null;
        }
    }


    public static class DeleteIncome extends AsyncTask<IncomeModel, Void, Void> {
        DatabaseDao databaseDao;

        public DeleteIncome(DatabaseDao dao) {
            databaseDao = dao;
        }

        @Override
        protected Void doInBackground(IncomeModel... incomeModels) {
            databaseDao.deleteIncome(incomeModels[0]);
            return null;
        }
    }

    public static class InsertExpense extends AsyncTask<ExpenseModel, Void, Void> {
        DatabaseDao databaseDao;

        public InsertExpense(DatabaseDao dao) {
            databaseDao = dao;
        }

        @Override
        protected Void doInBackground(ExpenseModel... expenseModels) {
            databaseDao.insertExpense(expenseModels[0]);
            return null;
        }
    }

    public static class DeleteExpense extends AsyncTask<ExpenseModel, Void, Void> {
        DatabaseDao databaseDao;

        public DeleteExpense(DatabaseDao dao) {
            databaseDao = dao;
        }

        @Override
        protected Void doInBackground(ExpenseModel... expenseModels) {
            databaseDao.deleteExpense(expenseModels[0]);
            return null;
        }
    }

}
