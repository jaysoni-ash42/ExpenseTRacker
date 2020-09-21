package com.example.jaysoni.expensetracker.Roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;



import java.util.List;

@Dao
public interface DatabaseDao {
    @Insert
    void insertIncome(IncomeModel incomeModel);

    @Insert
    void insertExpense(ExpenseModel expenseModel);

    @Delete
    void deleteIncome(IncomeModel incomeModel);

    @Delete
    void deleteExpense(ExpenseModel expenseModel);

    @Query("select * from Income order by Time Desc")
    LiveData<List<IncomeModel>> getAllIncome();

    @Query(("select * from Expense order by Time Desc"))
    LiveData<List<ExpenseModel>> getAllExpense();

    @Query("select * from Income where Time = :date order by Time Desc")
    LiveData<List<IncomeModel>> getAllIncome(String date);

    @Query("select * from Expense where Time = :date order by Time Desc")
    LiveData<List<ExpenseModel>> getAllExpense(String date);

    @Query("select * from Expense where Time between :date2 and :date1 order by Time Desc")
    LiveData<List<ExpenseModel>> getAllExpense(String date1, String date2);

    @Query("select * from Income where Time between :date2 and :date1 order by Time Desc")
    LiveData<List<IncomeModel>> getAllIncome(String date1, String date2);

    @Query("select Sum(Amount) from Income where Time= :date")
    LiveData<Integer> getSumIncome(String date);

    @Query("select Sum(Amount) from Expense where Time= :date")
    LiveData<Integer> getSumExpense(String date);

    @Query("select Sum(Amount) from Income where Time between :date2 and :date1")
    LiveData<Integer> getSumIncome(String date1, String date2);

    @Query("select Sum(Amount) from Expense where Time between :date2 and :date1")
    LiveData<Integer> getSumExpense(String date1, String date2);
    @Query("select Sum(Amount) from Expense")
    LiveData<Integer> getSumExpense();
    @Query("select Sum(Amount) from Income")
    LiveData<Integer> getSumIncome();
    @Query("select sum(Amount) as Amt,Category as Name from Expense where Time between :date2 and :date1 group by category order by Sum(Amount)")
    LiveData<List<PiechartData>> getExpense(String date1,String date2);
    @Query("select sum(Amount) as Amt,Category as Name from Income where Time between :date2 and :date1 group by category order by Sum(Amount)")
    LiveData<List<PiechartData>> getIncome(String date1,String date2);


}
