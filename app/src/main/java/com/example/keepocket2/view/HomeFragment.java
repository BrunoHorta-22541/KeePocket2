package com.example.keepocket2.view;

import android.os.Bundle;


import androidx.appcompat.app.AlertDialog;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AlertDialog;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.keepocket2.R;
import com.example.keepocket2.data.Movement;
import com.example.keepocket2.data.User;
import com.example.keepocket2.view.Session.SessionManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.keepocket2.viewmodel.CategoryViewModel;
import com.example.keepocket2.viewmodel.MovementViewModel;
import com.example.keepocket2.viewmodel.UserViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.NumberFormat;
import java.util.HashMap;

public class HomeFragment extends Fragment {
private TextView textView;

private PieChart pieChart;
private ArrayList<PieEntry> pieEntries;
private Map<String, Integer> expensesDataDataset = new HashMap<>();
private AlertDialog alertDialog;
private NavController navController;

private MovementViewModel movementViewModel;
private CategoryViewModel categoryViewModel;
private long userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        User activeSession = SessionManager.getActiveSession(getContext());
        userId = activeSession.getId();
        this.movementViewModel = new ViewModelProvider(this).get(MovementViewModel.class);
        this.categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        textView = root.findViewById(R.id.textViewUsername);
        textView.setText(activeSession.getName());
        if (!SessionManager.sessionExists(getContext())) {
            navController = NavHostFragment.findNavController(HomeFragment.this);
        }

        pieChart = root.findViewById(R.id.chart);
        pieEntries = new ArrayList<>();
        fillExpensesArrayList();

        for (Map.Entry<String, Integer> entry : expensesDataDataset.entrySet()) {
            pieEntries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Month Expenses");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(16);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        Legend legend = pieChart.getLegend();
        legend.setTextSize(13);
        legend.setTextColor(R.color.colorText);
        legend.setWordWrapEnabled(true);
        pieChart.animateXY(2000, 2000);
        pieChart.invalidate();
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String label =((PieEntry)e).getLabel();
                int value = expensesDataDataset.get(label);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                String sales = NumberFormat.getCurrencyInstance().format(value);
                View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_pie_chart_detail, null);
                TextView categoryTxtView = view.findViewById(R.id.chartCategory);
                TextView expensesTxtView = view.findViewById(R.id.chartExpenses);
                categoryTxtView.setText(label);
                expensesTxtView.setText(sales);
                builder.setView(view);
                alertDialog = builder.create();
                alertDialog.show();
            }
            @Override
            public void onNothingSelected() {

            }
        });
        return root;
    }
    private void fillExpensesArrayList() {
        expensesDataDataset.put("Gaming", 15);
        expensesDataDataset.put("Sa??de", 55);
        expensesDataDataset.put("Luz", 60);
        expensesDataDataset.put("??gua", 30);
        expensesDataDataset.put("Alimenta????o", 150);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        this.categoryViewModel.refreshCategory();
        this.movementViewModel.refreshMovements();
    }
}