package com.example.jaysoni.expensetracker.ui.expense;

import android.graphics.Canvas;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaysoni.expensetracker.R;
import com.example.jaysoni.expensetracker.RecyclerInterface;
import com.example.jaysoni.expensetracker.Roomdatabase.ExpenseModel;
import com.example.jaysoni.expensetracker.adapter.Expense_Adapter;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ExpenseFragment extends Fragment {

    ExpenseViewModel expenseViewModel;
    TextView textView, amount, toast_text_view;
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    Expense_Adapter expense_adapter;
    Toast toast;
    SearchView searchView;
    ItemTouchHelper itemTouchHelper;
    List<ExpenseModel> expensemodels;
    String category;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_expense, container, false);
        textView = root.findViewById(R.id.date);
        amount = root.findViewById(R.id.expense_amount);
        recyclerView = root.findViewById(R.id.expense_recyclerview);
        linearLayout = root.findViewById(R.id.linearlayout);
        expense_adapter = new Expense_Adapter(new RecyclerInterface() {
            @Override
            public void Longclick(int position) {
                updatedata(expensemodels.get(position));
            }
        });
        toast = new Toast(getContext());
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_toast, container, false);
        toast_text_view = v.findViewById(R.id.toast_text);
        toast.setView(v);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        searchView = root.findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                expense_adapter.getFilter().filter(newText);
                return true;
            }
        });
        Itemtouchhelper();
        return root;

    }

    private void updatedata(final ExpenseModel expenseModel) {
        final BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        dialog.setContentView(R.layout.layout_dailog);
        final EditText date, amount, note;
        Spinner spinner;
        Button ok;
        ok = dialog.findViewById(R.id.ok);
        date = dialog.findViewById(R.id.date);
        spinner = dialog.findViewById(R.id.spinner);
        String[] var = new String[]{"--Category--", "Others", "Groceries", "Clothing", "Medical", "Personal", "Restaurant", "Loan", "Merchandise"};
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, var);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
        amount = dialog.findViewById(R.id.amount);
        note = dialog.findViewById(R.id.note);
        dialog.setCancelable(true);
        date.setText(expenseModel.getTime());
        amount.setText(expenseModel.getAmount());
        note.setText(expenseModel.getNote());
        dialog.show();
        assert date != null;
        date.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
        assert ok != null;
        category = expenseModel.getCategory();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = adapterView.getAdapter().getItem(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(date.getText().toString())) {
                    date.setError("Invalid Date");
                } else if (TextUtils.isEmpty(amount.getText().toString())) {
                    amount.setError("No amount submitted");
                } else if (category.equals("--Category--")) {
                    toast_text_view.setText("invalid Category");
                    toast.show();
                } else {
                    expenseModel.setAmount(amount.getText().toString());
                    expenseModel.setNote(note.getText().toString().isEmpty() ? "Null" : note.getText().toString());
                    expenseModel.setTime(date.getText().toString());
                    expenseModel.setCategory(category);
                    expenseViewModel.updateExpense(expenseModel);
                    dialog.dismiss();
                    toast_text_view.setText("Item Updated");
                    toast.show();
                }

            }
        });
    }

    private void Itemtouchhelper() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final ExpenseModel expenseModel;
                if (direction == ItemTouchHelper.LEFT) {
                    final int position = viewHolder.getAdapterPosition();
                    expenseModel = expensemodels.get(position);
                    expensemodels.remove(viewHolder.getAdapterPosition());
                    expense_adapter.notifyDataSetChanged();
                    toast_text_view.setText("Item Deleted");
                    final Snackbar snackBar = Snackbar.make(recyclerView, "Click Undo to Revert", Snackbar.LENGTH_SHORT);

                    snackBar.addCallback(new Snackbar.Callback() {

                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                                expenseViewModel.deleteExpense(expenseModel);
                                expense_adapter.notifyDataSetChanged();
                                toast.show();
                            }
                        }

                        @Override
                        public void onShown(Snackbar snackbar) {

                            snackBar.setBackgroundTint(ContextCompat.getColor(getActivity(), R.color.colorPrimary))
                                    .setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark))
                                    .setActionTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark))
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            expensemodels.add(position, expenseModel);
                                            expense_adapter.notifyDataSetChanged();
                                        }
                                    });
                        }
                    });
                    snackBar.show();
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary))
                        .addActionIcon(R.drawable.ic_baseline_delete_24)
                        .addSwipeLeftLabel("Delete")
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.frgment_menu, menu);
        MenuItem add = menu.findItem(R.id.add);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) item.getActionView();
        add.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        String[] var = new String[]{"Today", "Yesterday", "Last 30 Days", "Last 6 Months", "All"};
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(requireContext(), R.layout.support_simple_spinner_dropdown_item, var);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String data = parent.getAdapter().getItem(position).toString();
                if (data.equals("Today")) {
                    textView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
                    expenseViewModel.getAllExpense(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())).observe(getViewLifecycleOwner(), new Observer<List<ExpenseModel>>() {
                        @Override
                        public void onChanged(List<ExpenseModel> expenseModels) {
                            if (expenseModels.isEmpty()) {
                                linearLayout.setVisibility(View.VISIBLE);
                                expense_adapter.clearList();
                                expense_adapter.notifyDataSetChanged();
                            } else {
                                expensemodels = expenseModels;
                                linearLayout.setVisibility(View.INVISIBLE);
                                expense_adapter.getList(expensemodels);
                                recyclerView.setAdapter(expense_adapter);
                                expense_adapter.notifyDataSetChanged();
                                Log.d("data", "" + expenseModels);
                            }
                        }
                    });
                    expenseViewModel.getSumExpense(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s == null) {
                                amount.setText("Amount: " + 0.00);
                            } else {
                                amount.setText("Amount: " + String.valueOf(s));
                            }
                        }
                    });

                } else if (data.equals("Yesterday")) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, -1);
                    textView.setText(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                    expenseViewModel.getAllExpense(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())).observe(getViewLifecycleOwner(), new Observer<List<ExpenseModel>>() {
                        @Override
                        public void onChanged(List<ExpenseModel> expenseModels) {
                            if (expenseModels.isEmpty()) {
                                linearLayout.setVisibility(View.VISIBLE);
                                expense_adapter.clearList();
                                expense_adapter.notifyDataSetChanged();
                            } else {
                                expensemodels = expenseModels;
                                linearLayout.setVisibility(View.INVISIBLE);
                                expense_adapter.getList(expensemodels);
                                recyclerView.setAdapter(expense_adapter);
                                expense_adapter.notifyDataSetChanged();
                                Log.d("data", "" + expenseModels);
                            }
                        }
                    });
                    expenseViewModel.getSumExpense(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String integer) {
                            if (integer == null) {
                                amount.setText("Amount: " + 0.00);
                            } else {
                                amount.setText("Amount: " + String.valueOf(integer));
                            }
                        }
                    });
                } else if (data.equals("Last 30 Days")) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, -30);
                    textView.setText(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                    expenseViewModel.getAllExpense(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())).observe(getViewLifecycleOwner(), new Observer<List<ExpenseModel>>() {
                        @Override
                        public void onChanged(List<ExpenseModel> expenseModels) {
                            if (expenseModels.isEmpty()) {
                                linearLayout.setVisibility(View.VISIBLE);
                                expense_adapter.clearList();
                                expense_adapter.notifyDataSetChanged();
                            } else {
                                expensemodels = expenseModels;
                                linearLayout.setVisibility(View.INVISIBLE);
                                expense_adapter.getList(expensemodels);
                                recyclerView.setAdapter(expense_adapter);
                                expense_adapter.notifyDataSetChanged();
                                Log.d("data", "" + expenseModels);
                            }
                        }
                    });
                    expenseViewModel.getSumExpense(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String integer) {
                            if (integer == null) {
                                amount.setText("Amount: " + 0.00);
                            } else {
                                amount.setText("Amount: " + String.valueOf(integer));
                            }
                        }
                    });


                } else if (data.equals("Last 6 Months")) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MONTH, -6);
                    textView.setText(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                    expenseViewModel.getAllExpense(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())).observe(getViewLifecycleOwner(), new Observer<List<ExpenseModel>>() {
                        @Override
                        public void onChanged(List<ExpenseModel> expenseModels) {
                            if (expenseModels.isEmpty()) {
                                linearLayout.setVisibility(View.VISIBLE);
                                expense_adapter.clearList();
                                expense_adapter.notifyDataSetChanged();
                            } else {
                                expensemodels = expenseModels;
                                linearLayout.setVisibility(View.INVISIBLE);
                                expense_adapter.getList(expensemodels);
                                recyclerView.setAdapter(expense_adapter);
                                expense_adapter.notifyDataSetChanged();
                                Log.d("data", "" + expenseModels);
                            }
                        }
                    });
                    expenseViewModel.getSumExpense(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String integer) {
                            if (integer == null) {
                                amount.setText("Amount: " + 0.00);
                            } else {
                                amount.setText("Amount: " + String.valueOf(integer));
                            }
                        }
                    });
                } else if (data.equals("All")) {
                    textView.setText(("All"));
                    expenseViewModel.getAllExpense().observe(getViewLifecycleOwner(), new Observer<List<ExpenseModel>>() {
                        @Override
                        public void onChanged(List<ExpenseModel> expenseModels) {
                            if (expenseModels.isEmpty()) {
                                linearLayout.setVisibility(View.VISIBLE);
                                expense_adapter.clearList();
                                expense_adapter.notifyDataSetChanged();
                            } else {
                                expensemodels = expenseModels;
                                linearLayout.setVisibility(View.INVISIBLE);
                                expense_adapter.getList(expensemodels);
                                recyclerView.setAdapter(expense_adapter);
                                expense_adapter.notifyDataSetChanged();
                                Log.d("data", "" + expenseModels);
                            }
                        }
                    });
                    expenseViewModel.getAllSumExpense().observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String integer) {
                            if (integer == null) {
                                amount.setText("Amount: " + 0.00);
                            } else {
                                amount.setText("Amount: " + integer);
                            }
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        add.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                insertitem();
                return true;
            }
        });
    }

    private void insertitem() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(R.layout.layout_data);
        final EditText date, amount, note;
        Spinner spinnerbottom;
        Button ok;
        ok = bottomSheetDialog.findViewById(R.id.ok);
        date = bottomSheetDialog.findViewById(R.id.date);
        spinnerbottom = bottomSheetDialog.findViewById(R.id.spinner);
        String[] var = new String[]{"--Category--", "Others", "Groceries", "Clothing", "Medical", "Personal", "Restaurant", "Loan", "Merchandise"};
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, var);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerbottom.setAdapter(arrayAdapter);
        spinnerbottom.setSelection(0);
        amount = bottomSheetDialog.findViewById(R.id.amount);
        note = bottomSheetDialog.findViewById(R.id.note);
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();
        assert date != null;
        date.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
        assert ok != null;
        spinnerbottom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = adapterView.getAdapter().getItem(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(date.getText().toString())) {
                    date.setError("Invalid Date");
                } else if (TextUtils.isEmpty(amount.getText().toString())) {
                    amount.setError("No amount submitted");
                } else if (category.equals("--Category--")) {
                    toast_text_view.setText("invalid Category");
                    toast.show();
                } else {
                    ExpenseModel expenseModel = new ExpenseModel(date.getText().toString(), category, amount.getText().toString(), note.getText().toString().isEmpty() ? "Null" : note.getText().toString());
                    expenseViewModel.insertExpense(expenseModel);
                    bottomSheetDialog.dismiss();
                    toast_text_view.setText("Item Added");
                    toast.show();
                }

            }
        });

    }
}
