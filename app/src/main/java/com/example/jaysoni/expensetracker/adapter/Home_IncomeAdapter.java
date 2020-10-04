package com.example.jaysoni.expensetracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaysoni.expensetracker.R;
import com.example.jaysoni.expensetracker.Roomdatabase.IncomeModel;

import java.util.ArrayList;
import java.util.List;

public class Home_IncomeAdapter extends RecyclerView.Adapter<Home_IncomeAdapter.Home_IncomeViewHolder> {
    List<IncomeModel> incomeModelList = new ArrayList<>();

    @NonNull
    @Override
    public Home_IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_datahome, parent, false);
        return new Home_IncomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Home_IncomeViewHolder holder, int position) {
        holder.category.setText(incomeModelList.get(position).getCategory());
        holder.amount.setText(incomeModelList.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return incomeModelList.size();
    }

    public void getList(List<IncomeModel> incomeModels) {
        incomeModelList = incomeModels;
    }

    public void clearList() {
        if (incomeModelList.size() == 0) {

        } else {
            incomeModelList.clear();
        }

    }

    public class Home_IncomeViewHolder extends RecyclerView.ViewHolder {
        TextView category, amount;

        public Home_IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
