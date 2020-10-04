package com.example.jaysoni.expensetracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaysoni.expensetracker.R;
import com.example.jaysoni.expensetracker.Roomdatabase.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

public class Home_ExpenseAdapter extends RecyclerView.Adapter<Home_ExpenseAdapter.Home_ExpenseViewHolder> {
    List<ExpenseModel> expenseModelList = new ArrayList<>();

    @NonNull
    @Override
    public Home_ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_datahome, parent, false);
        return new Home_ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Home_ExpenseViewHolder holder, int position) {
        holder.Category.setText(expenseModelList.get(position).getCategory());
        holder.Amount.setText(expenseModelList.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return expenseModelList.size();
    }

    public void getList(List<ExpenseModel> expenseModels) {
        expenseModelList = expenseModels;
    }

    public void clearList() {
        if (expenseModelList.size() == 0) {

        } else {
            expenseModelList.clear();
        }

    }

    public class Home_ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView Amount, Category;

        public Home_ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            Amount = itemView.findViewById(R.id.amount);
            Category = itemView.findViewById(R.id.category);
        }
    }
}
