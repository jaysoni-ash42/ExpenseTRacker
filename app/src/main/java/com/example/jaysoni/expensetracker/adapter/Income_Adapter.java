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
import com.example.jaysoni.expensetracker.Roomdatabase.IncomeModel;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Income_Adapter extends RecyclerView.Adapter<Income_Adapter.Income_ViewHolder> implements Filterable {
List<IncomeModel> incomeModelList=new ArrayList<>();
    List<IncomeModel> data;
    @NonNull
    @Override
    public Income_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_income,parent,false);
        return new Income_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Income_ViewHolder holder, int position) {
    holder.date.setText(incomeModelList.get(position).getTime());
        holder.category.setText(incomeModelList.get(position).getCategory());
        holder.amount.setText(incomeModelList.get(position).getAmount());
    if(incomeModelList.get(position).getNote().equals(""))
    {
        holder.note.setText("Null");
    }
    else
    {
        holder.note.setText(incomeModelList.get(position).getNote());
    }
    }

    @Override
    public int getItemCount() {
        return incomeModelList.size();
    }
    public void getList(List<IncomeModel> incomeModels)
    {
        incomeModelList=incomeModels;
        data = new ArrayList<>(incomeModelList);
    }
    public void clearList() {
        if (incomeModelList.size()==0) {

        } else {
            incomeModelList.clear();
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
            List<IncomeModel> filterable = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filterable.addAll(data);
            } else {
                for (IncomeModel movie : data) {
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
            incomeModelList.clear();
            if(results.values==null)
            {
                return;
            }
            else {
                incomeModelList.addAll((Collection<? extends IncomeModel>) results.values);
                notifyDataSetChanged();
            }
        }
    };

    public class Income_ViewHolder extends RecyclerView.ViewHolder
    {
        TextView date,category,amount,note;

        public Income_ViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            category=itemView.findViewById(R.id.category);
            amount=itemView.findViewById(R.id.amount);
            note=itemView.findViewById(R.id.Note);
        }
    }
}
