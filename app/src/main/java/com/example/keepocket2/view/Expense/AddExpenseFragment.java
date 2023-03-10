package com.example.keepocket2.view.Expense;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.keepocket2.R;
import com.example.keepocket2.data.Category;
import com.example.keepocket2.data.Movement;
import com.example.keepocket2.data.User;
import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.view.Session.SessionManager;
import com.example.keepocket2.viewmodel.MovementViewModel;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AddExpenseFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private List<String> categoryList;
    private long userId;
    private EditText descriptionExpense;
    private EditText valueExpense;
    private Spinner spinner;
    private String itemSelected;
    ArrayAdapter<String> spinnerAdapter;
    private Button save;
    private NavController navController;
    private MovementViewModel viewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_expense, container, false);
        navController = NavHostFragment.findNavController(AddExpenseFragment.this);
        User activeSession = SessionManager.getActiveSession(getContext());
        this.viewModel = new ViewModelProvider(this).get(MovementViewModel.class);
        userId = activeSession.getId();
        this.descriptionExpense = root.findViewById(R.id.editTextExpenseDescription);
        this.spinner = root.findViewById(R.id.expenseCategorySpinner);
        this.valueExpense = root.findViewById(R.id.editTextExpenseValue);
        categoryList = Database.getInstance(getContext()).getcategoryDAO().getUserCategoryName(userId);

        spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<String>());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.addAll(categoryList);
        spinnerAdapter.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(this);
        save= root.findViewById(R.id.button6);
        save.setOnClickListener(view->{
            String description = this.descriptionExpense.getText().toString();
            String valueExpenseString = this.valueExpense.getText().toString();
            int valueExpenseInt = Integer.parseInt(valueExpenseString);
            Category category = Database.getInstance(getContext()).getcategoryDAO().getCategoryByName(userId, itemSelected);
            int valueExpenseNegative= valueExpenseInt * (-1);
            String dateString = String.valueOf(System.currentTimeMillis());
            long millis = Long.parseLong(dateString); // parse the string value as a long value

            Instant instant = Instant.ofEpochMilli(millis); // create an Instant from the millis value

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // define a formatter for the output

            LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime(); // convert the Instant to a LocalDateTime in the default time zone
            String output = formatter.format(localDateTime); // format the LocalDateTime as a string using the formatter
            Movement movements = new Movement(0, String.valueOf(userId), String.valueOf(category.getIdCategory()),String.valueOf(valueExpenseNegative), description, output);
            this.viewModel.createMovementApi(movements);
            NavDirections action = AddExpenseFragmentDirections.actionAddExpenseFragmentToExpenseFragment();
            navController.navigate(action);
        });
        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        itemSelected = (String)adapterView.getItemAtPosition(i);
        Toast.makeText(adapterView.getContext(),
                "OnItemSelectedListener : " + adapterView.getItemAtPosition(i).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getContext(), "Nenhum item selecionado", Toast.LENGTH_SHORT).show();
    }
}