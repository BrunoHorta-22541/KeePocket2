package com.example.keepocket2.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.example.keepocket2.R;
import com.example.keepocket2.data.Category;
import com.example.keepocket2.data.User;
import com.example.keepocket2.data.ValueSum;
import com.example.keepocket2.data.localDatabase.CategoryDAO;
import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.data.localDatabase.MovementDAO;
import com.example.keepocket2.view.Session.SessionManager;
import com.example.keepocket2.viewmodel.CategoryViewModel;
import com.example.keepocket2.viewmodel.MovementViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class HomeFragment extends Fragment {
    private TextView textView;
    private MovementDAO movementDAO;
    private CategoryDAO categoryDAO;
    private Map<String, Integer> pieColorsMap = new HashMap<>();
    private PieChart pieChart;
    private ArrayList<PieEntry> pieEntries;
    private Map<String, Integer> expensesDataDataset = new HashMap<>();
    private AlertDialog alertDialog;
    private NavController navController;
    private final int[] pieColors = ColorTemplate.MATERIAL_COLORS;
    private MovementViewModel movementViewModel;
    private CategoryViewModel categoryViewModel;
    private long userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        // Create a new ArrayList for colors
        ArrayList<Integer> colors = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : expensesDataDataset.entrySet()) {
            String label = entry.getKey();
            int value = entry.getValue();
            int randomColor = getRandomColor(); // Custom method to get random colors
            colors.add(randomColor);
            pieEntries.add(new PieEntry(value, entry.getKey()));
        }

        // Create a PieDataSet with the entries and colors
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setValueTextSize(16);

        // Set the colors for the dataset, ensuring that they match the order of the entries
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        // Configure legend
        Legend legend = pieChart.getLegend();
        legend.setTextSize(13);
        legend.setTextColor(R.color.colorText);
        legend.setWordWrapEnabled(true);

        // Create legend entries
        List<LegendEntry> legendEntries = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : pieColorsMap.entrySet()) {
            String label = entry.getKey();
            int color = entry.getValue();

            LegendEntry legendEntry = new LegendEntry();
            legendEntry.label = label;
            legendEntry.formColor = color;

            legendEntries.add(legendEntry);
        }

        legend.setCustom(legendEntries);

        pieChart.animateXY(2000, 2000);
        pieChart.invalidate();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String label = ((PieEntry) e).getLabel();
                int value = expensesDataDataset.get(label);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                String sales = NumberFormat.getCurrencyInstance(Locale.GERMANY).format(value);
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

    private int getRandomColor() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    private void fillExpensesArrayList() {
        this.movementDAO = Database.getInstance(this.getContext()).getmovementsDAO();
        this.categoryDAO = Database.getInstance(this.getContext()).getcategoryDAO();
        List<ValueSum> movement = movementDAO.getExpenseGroup(userId);

        int colorIndex = 0;
        for (ValueSum group : movement) {
            Category category = categoryDAO.getById(Long.parseLong(group.getCategoryId()));
            int valor = Integer.parseInt(group.getSumValue());
            String nameCategory = category.getCategoryName();


            int positiveValue = Math.abs(valor);
            expensesDataDataset.put(nameCategory, positiveValue);
        }
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