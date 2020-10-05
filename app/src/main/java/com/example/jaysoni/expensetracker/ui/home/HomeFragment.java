package com.example.jaysoni.expensetracker.ui.home;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaysoni.expensetracker.R;
import com.example.jaysoni.expensetracker.Roomdatabase.ExpenseModel;
import com.example.jaysoni.expensetracker.Roomdatabase.IncomeModel;
import com.example.jaysoni.expensetracker.adapter.Expense_Adapter;
import com.example.jaysoni.expensetracker.adapter.Home_ExpenseAdapter;
import com.example.jaysoni.expensetracker.adapter.Home_IncomeAdapter;
import com.example.jaysoni.expensetracker.adapter.Income_Adapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    LinearLayout linearlayoutincome, linearlayoutexpense;
    RecyclerView expense_Recyclerview, income_Recyclerview;
    TextView expenseamount, incomeamount, profile_name;
    CircleImageView circleImageView;
    Home_IncomeAdapter income_adapter;
    Home_ExpenseAdapter expense_adapter;
    List<IncomeModel> incomeModelList;
    List<ExpenseModel> expenseModelList;
    LineChart lineChart;
    LineData lineData;
    LineDataSet incomedataset, expensedataset;
    List<Entry> lineEntries, lineEntries2;
    List<ILineDataSet> dataSets;
    FirebaseAuth mauth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        profile_name = root.findViewById(R.id.profile_name);
        circleImageView = root.findViewById(R.id.profile_image);
        expenseModelList = new ArrayList<>();
        incomeModelList = new ArrayList<>();
        lineChart = root.findViewById(R.id.line_chart);
        linearlayoutexpense = root.findViewById(R.id.linearlayoutexpense);
        linearlayoutincome = root.findViewById(R.id.linearlayoutinome);
        expenseamount = root.findViewById(R.id.Expense_amount);
        incomeamount = root.findViewById(R.id.Income_Amount);
        expense_Recyclerview = root.findViewById(R.id.Expense_recyclerview);
        income_Recyclerview = root.findViewById(R.id.Income_recyclerview);
        income_adapter = new Home_IncomeAdapter();
        expense_adapter = new Home_ExpenseAdapter();
        mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        profile_name.setText(user.getDisplayName());
        Picasso.get().load(user.getPhotoUrl()).into(circleImageView);
        Log.d("debug", "" + user.getPhotoUrl());
        insertData();
        return root;
    }

    private void getGraph() {
        lineEntries = new ArrayList<>();
        lineEntries2 = new ArrayList<>();
        int i = 0, j = 0;
        if (expenseModelList.isEmpty() && incomeModelList.isEmpty()) {
            lineEntries.add(new Entry(0, 0));
            lineEntries2.add(new Entry(0, 0));
        } else {
            for (ExpenseModel model : expenseModelList) {
                lineEntries.add(new Entry(i, Float.parseFloat(model.getAmount())));
                i++;
            }
            for (IncomeModel model : incomeModelList) {
                lineEntries2.add(new Entry(j, Float.parseFloat(model.getAmount())));
                j++;
            }
        }
        Log.d("data", "" + lineEntries);
        expensedataset = new LineDataSet(lineEntries, "Expense");
        expensedataset.setAxisDependency(YAxis.AxisDependency.LEFT);
        expensedataset.setColor(Color.BLACK);
        expensedataset.setCircleColor(Color.BLACK);
        expensedataset.setLineWidth(2f);
        expensedataset.setCircleRadius(8f);
        expensedataset.setFillAlpha(65);
        expensedataset.setFillColor(ColorTemplate.getHoloBlue());
        expensedataset.setHighLightColor(Color.rgb(244, 117, 117));
        expensedataset.setDrawCircleHole(false);
        incomedataset = new LineDataSet(lineEntries2, "Income");
        incomedataset.setAxisDependency(YAxis.AxisDependency.LEFT);
        incomedataset.setColor(Color.RED);
        incomedataset.setCircleColor(Color.RED);
        incomedataset.setLineWidth(2f);
        incomedataset.setCircleRadius(7f);
        incomedataset.setFillAlpha(65);
        incomedataset.setFillColor(Color.RED);
        incomedataset.setDrawCircleHole(false);
        incomedataset.setHighLightColor(Color.rgb(244, 117, 117));
        dataSets = new ArrayList<>();
        dataSets.add(expensedataset);
        dataSets.add(incomedataset);
        lineData = new LineData(dataSets);
        lineChart.setData(lineData);
        expensedataset.setColors(ColorTemplate.JOYFUL_COLORS);
        expensedataset.setValueTextColor(Color.BLACK);
        expensedataset.setValueTextSize(8f);
        incomedataset.setColors(ColorTemplate.COLORFUL_COLORS);
        incomedataset.setValueTextColor(Color.BLACK);
        incomedataset.setValueTextSize(8f);
        lineChart.getDescription().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return "Rs." + String.valueOf(value);
            }
        });
        lineChart.invalidate();

    }

    private void insertData() {
        homeViewModel.getAllExpense(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())).observe(getViewLifecycleOwner(), new Observer<List<ExpenseModel>>() {
            @Override
            public void onChanged(List<ExpenseModel> expenseModels) {
                if (expenseModels.size() == 0) {
                    linearlayoutexpense.setVisibility(View.VISIBLE);
                    expense_adapter.clearList();
                    expense_adapter.notifyDataSetChanged();
                } else {
                    expenseModelList = expenseModels;
                    linearlayoutexpense.setVisibility(View.INVISIBLE);
                    expense_adapter.getList(expenseModels);
                    expense_Recyclerview.setAdapter(expense_adapter);
                    expense_adapter.notifyDataSetChanged();
                }
                getGraph();
            }
        });
        homeViewModel.getSumExpense(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())).observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String integer) {
                if (integer == null) {
                    expenseamount.setText("Amount: " + "$" + 0.00);
                } else {
                    expenseamount.setText("Amount: " + "$" + String.valueOf(integer));
                }
            }
        });
        homeViewModel.getAllIncome(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())).observe(getViewLifecycleOwner(), new Observer<List<IncomeModel>>() {
            @Override
            public void onChanged(List<IncomeModel> incomeModels) {
                if (incomeModels.size() == 0) {
                    linearlayoutincome.setVisibility(View.VISIBLE);
                    income_adapter.clearList();
                    income_adapter.notifyDataSetChanged();
                } else {
                    incomeModelList = incomeModels;
                    linearlayoutincome.setVisibility(View.INVISIBLE);
                    income_adapter.getList(incomeModels);
                    income_Recyclerview.setAdapter(income_adapter);
                    expense_adapter.notifyDataSetChanged();


                }
                getGraph();
            }
        });
        homeViewModel.getSumIncome(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())).observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String integer) {
                if (integer == null) {
                    incomeamount.setText("Amount: " + "$" + 0.00);
                } else {
                    incomeamount.setText("Amount: " + "$" + String.valueOf(integer));
                }
            }
        });
    }
}