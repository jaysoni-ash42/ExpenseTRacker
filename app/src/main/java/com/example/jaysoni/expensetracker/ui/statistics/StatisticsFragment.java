package com.example.jaysoni.expensetracker.ui.statistics;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;


import com.example.jaysoni.expensetracker.R;
import com.example.jaysoni.expensetracker.Roomdatabase.PiechartData;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StatisticsFragment extends Fragment {
    StatisticsViewModel statisticsViewModel;
    TextView textView;
    String date1, date2;
    PieChart expensechart, incomechart;
    ArrayList<PieEntry> expenseEntry, incomeEntry;
    PieDataSet expensedataset, incomedataset;
    PieData expensedata, incomedata;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        statisticsViewModel = new ViewModelProvider(requireActivity()).get(StatisticsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statistics, container, false);
        expensechart = root.findViewById(R.id.expense_piechart);
        incomechart = root.findViewById(R.id.income_piechart);
        textView = root.findViewById(R.id.date);
        expenseEntry = new ArrayList<>();
        incomeEntry = new ArrayList<>();
        date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        todayData();
        return root;
    }

    public void todayData() {
        expenseData(date1);
        incomeData(date1);
    }

    private void insertdata() {
        if (expenseEntry.isEmpty() && incomeEntry.isEmpty()) {
            return;
        } else {
            expensechart.setExtraOffsets(0.f, 5.f, 0.f, 5.f);
            incomechart.setExtraOffsets(0.f, 5.f, 0.f, 5.f);
            //expensechart.getLegend().setEnabled(false);
            //incomechart.getLegend().setEnabled(false);
            expensechart.setUsePercentValues(true);
            expensechart.getDescription().setEnabled(false);
            expensechart.setDrawHoleEnabled(true);
            expensechart.setTransparentCircleRadius(61f);
            expensedataset = new PieDataSet(expenseEntry, "Expense");
            expensedataset.setSliceSpace(5f);
            expensedataset.setSelectionShift(20f);
            expensedataset.setColors(ColorTemplate.JOYFUL_COLORS);
            expensedata = new PieData(expensedataset);
            expensedataset.setValueTextColor(Color.BLACK);
            expensedataset.setValueTextSize(20f);
            expensedataset.setValueLinePart1OffsetPercentage(100f);
            expensedataset.setValueLinePart1Length(0.6f);
            expensedataset.setValueLinePart2Length(0.6f);
            expensedataset.setValueTextColor(Color.BLACK);
            //expensedataset.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            expensechart.animateY(1000, Easing.EaseInOutCubic);
            expensechart.setEntryLabelColor(Color.BLACK);
            incomechart.setEntryLabelColor(Color.BLACK);
            expensechart.setData(expensedata);
            incomechart.setUsePercentValues(true);
            incomechart.getDescription().setEnabled(false);
            incomechart.setDrawHoleEnabled(true);
            incomechart.setTransparentCircleRadius(61f);
            incomedataset = new PieDataSet(incomeEntry, "Income");
            incomedataset.setSliceSpace(5f);
            incomedataset.setSelectionShift(20f);
            incomedataset.setColors(ColorTemplate.JOYFUL_COLORS);
            incomedata = new PieData(incomedataset);
            incomedata.setValueTextSize(20f);
            incomedata.setValueTextColor(Color.BLACK);
            incomedataset.setValueLinePart1OffsetPercentage(100f);
            incomedataset.setValueLinePart1Length(0.6f);
            incomedataset.setValueLinePart2Length(0.6f);
            incomedataset.setValueTextColor(Color.BLACK);
            incomedataset.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            incomechart.animateX(2000, Easing.EaseInOutCubic);
            incomechart.setData(incomedata);
            expensechart.invalidate();
            incomechart.invalidate();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.statictics_menu, menu);
        MenuItem item = menu.findItem(R.id.calender);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Calendar cal = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dayOfmonth = "" + dayOfMonth;
                        String Month = "" + (month + 1);
                        if ((month + 1) < 10) {
                            Month = "0" + (month + 1);
                        }
                        if (dayOfMonth < 10) {
                            dayOfmonth = "0" + dayOfMonth;
                        }
                        date2 = year + "-" + Month + "-" + dayOfmonth;
                        textView.setText(date1 + " To " + date2);
                        expenseData(date1, date2);
                        incomeData(date1, date2);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                datePickerDialog.setCancelable(false);
                return true;
            }
        });
    }

    public void expenseData(String date1, String date2) {
        statisticsViewModel.getExpense(date1, date2).observe(getViewLifecycleOwner(), new Observer<List<PiechartData>>() {
            @Override
            public void onChanged(List<PiechartData> piechartData) {
                Log.d("value", "" + piechartData);
                expenseEntry.clear();
                for (PiechartData data : piechartData) {
                    expenseEntry.add(new PieEntry(Float.parseFloat(data.getAmt()), data.getName()));
                }
                insertdata();
            }
        });

    }

    public void incomeData(String date1, String date2) {
        statisticsViewModel.getIncome(date1, date2).observe(getViewLifecycleOwner(), new Observer<List<PiechartData>>() {
            @Override
            public void onChanged(List<PiechartData> piechartData) {
                Log.d("value1", "" + piechartData);
                incomeEntry.clear();
                for (PiechartData data : piechartData) {
                    incomeEntry.add(new PieEntry(Float.parseFloat(data.getAmt()), data.getName()));
                }
                insertdata();
            }
        });

    }

    public void expenseData(String date1) {
        statisticsViewModel.getExpense(date1).observe(getViewLifecycleOwner(), new Observer<List<PiechartData>>() {
            @Override
            public void onChanged(List<PiechartData> piechartData) {
                Log.d("value1", "" + piechartData);
                expenseEntry.clear();
                for (PiechartData data : piechartData) {
                    expenseEntry.add(new PieEntry(Float.parseFloat(data.getAmt()), data.getName()));
                }
                insertdata();
            }
        });

    }

    public void incomeData(String date1) {
        statisticsViewModel.getIncome(date1).observe(getViewLifecycleOwner(), new Observer<List<PiechartData>>() {
            @Override
            public void onChanged(List<PiechartData> piechartData) {
                Log.d("value1", "" + piechartData);
                incomeEntry.clear();
                for (PiechartData data : piechartData) {
                    incomeEntry.add(new PieEntry(Float.parseFloat(data.getAmt()), data.getName()));
                }
                insertdata();
            }
        });

    }

}
