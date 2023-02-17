package com.example.keepocket2.view;

import android.os.Bundle;

<<<<<<< HEAD
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
=======
import androidx.appcompat.app.AlertDialog;
>>>>>>> d8e44c8b449c70228d0e42ce602218e23f77fd7e
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
<<<<<<< HEAD
import com.example.keepocket2.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.Map;

public class HomeFragment extends Fragment {
private TextView textView;
    private UserViewModel viewModel;
=======
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
private TextView textView;
private PieChart pieChart;
private ArrayList<PieEntry> pieEntries;
private Map<String, Integer> expensesDataDataset = new HashMap<>();
private AlertDialog alertDialog;
private NavController navController;

private com.example.keepocket2.viewmodel.MovementViewModel viewModel;

private long userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = new UserViewModel(this.requireActivity().getApplication());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        User activeSession = SessionManager.getActiveSession(getContext());
        userId = activeSession.getId();
        this.viewModel = new ViewModelProvider(this).get(com.example.keepocket2.viewmodel.MovementViewModel.class);
        textView = root.findViewById(R.id.textViewUsername);
        textView.setText(activeSession.getEmail());
        if (!SessionManager.sessionExists(getContext())) {
            navController = NavHostFragment.findNavController(HomeFragment.this);
        }

        pieChart = root.findViewById(R.id.chart);
        pieEntries = new ArrayList<>();
        fillExpensesArrayList();

        for (Map.Entry<String, Integer> entry : expensesDataDataset.entrySet()){
            pieEntries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Month Expenses");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(16);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        Legend legend = pieChart.getLegend();
        legend.setTextSize(13);
        legend.setTextColor(com.google.android.material.R.color.design_default_color_primary_dark);
        legend.setWordWrapEnabled(true);
        pieChart.animateXY(2000, 2000);
        pieChart.invalidate();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String label =((PieEntry)e).getLabel();
                int value = expensesDataDataset.get(label);
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
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
        LiveData<List<Movement>> list = this.viewModel.getExpenseById(userId);
        ArrayList<LiveData<List<Movement>>> arrayList = new ArrayList<>();
        arrayList.add(list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.viewModel.refreshUser();
    }
}