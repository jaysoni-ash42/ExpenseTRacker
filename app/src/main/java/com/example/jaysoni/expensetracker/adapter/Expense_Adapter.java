package com.example.jaysoni.expensetracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaysoni.expensetracker.R;
import com.example.jaysoni.expensetracker.Roomdatabase.ExpenseDatabase;
import com.example.jaysoni.expensetracker.Roomdatabase.ExpenseModel;
import com.example.jaysoni.expensetracker.Roomdatabase.IncomeModel;
import com.google.android.material.textview.MaterialTextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Expense_Adapter extends RecyclerView.Adapter<Expense_Adapter.Expense_ViewHolder> implements Filterable {

    List<ExpenseModel> expenseModelList = new ArrayList<>();
    List<ExpenseModel> data;


    @NonNull
    @Override
    public Expense_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_example, parent, false);
        return new Expense_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Expense_ViewHolder holder, int position) {
        holder.date.setText(expenseModelList.get(position).getTime());
        holder.category.setText(expenseModelList.get(position).getCategory());
        holder.amount.setText(expenseModelList.get(position).getAmount());
        if (expenseModelList.get(position).getNote().equals("")) {
            holder.note.setText("Null");
        } else {
            holder.note.setText(expenseModelList.get(position).getNote());
        }
    }

    @Override
    public int getItemCount() {
        return expenseModelList.size();
    }

    public void getList(List<ExpenseModel> expenseModels) {
        expenseModelList = expenseModels;
        data = new ArrayList<>(expenseModelList);
    }

    public void clearList() {
        if (expenseModelList.size() == 0) {
            return;
        } else {
            expenseModelList.clear();
            data.clear();
        }

    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ExpenseModel> filterable = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filterable.addAll(data);
            } else {
                for (ExpenseModel movie : data) {
                    if (movie.getCategory().toLowerCase().contains(constraint.toString().toLowerCase()) || movie.getNote().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterable.add(movie);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterable;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            expenseModelList.clear();
            if(results.values==null)
            {
                return;
            }
            else {
                expenseModelList.addAll((Collection<? extends ExpenseModel>) results.values);
                notifyDataSetChanged();
            }


        }
    };

    public class Expense_ViewHolder extends RecyclerView.ViewHolder {
        TextView date, category, amount, note;

        public Expense_ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            category = itemView.findViewById(R.id.category);
            amount = itemView.findViewById(R.id.amount);
            note = itemView.findViewById(R.id.Note);
        }

    }
}
