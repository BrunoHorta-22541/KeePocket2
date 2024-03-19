package com.example.keepocket2.view.Income;

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
import com.example.keepocket2.view.Category.AddCategoryFragmentDirections;
import com.example.keepocket2.view.Session.SessionManager;
import com.example.keepocket2.viewmodel.CategoryViewModel;
import com.example.keepocket2.viewmodel.MovementViewModel;
import com.example.keepocket2.viewmodel.UserViewModel;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class AddIncomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerIncome;
    private EditText editTextDescription;
    private EditText editTextValue;
    private Button save;

    private long userId;
    private List<String> categoryList;
    private String itemSelected;
    ArrayAdapter<String> spinnerAdapter;
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
        View root= inflater.inflate(R.layout.fragment_add_income, container, false);
        NavController navController = NavHostFragment.findNavController(com.example.keepocket2.view.Income.AddIncomeFragment.this);
        User activeSession = SessionManager.getActiveSession(getContext());
        this.viewModel = new ViewModelProvider(this).get(MovementViewModel.class);
        userId = activeSession.getId();
        this.editTextDescription = root.findViewById(R.id.editTextIncomeDescription);
        this.spinnerIncome = root.findViewById(R.id.incomeCategorySpinner);
        this.editTextValue = root.findViewById(R.id.editTextIncomeValue);
        categoryList = Database.getInstance(getContext()).getcategoryDAO().getUserCategoryName(userId);
        spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<String>());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIncome.setAdapter(spinnerAdapter);
        spinnerAdapter.addAll(categoryList);
        spinnerAdapter.notifyDataSetChanged();
        spinnerIncome.setOnItemSelectedListener(this);
        save = root.findViewById(R.id.button4);
        save.setOnClickListener(view->{
            String description = this.editTextDescription.getText().toString();
            String valueIncomeString = this.editTextValue.getText().toString();
            int valueIncomeInt = Integer.parseInt(valueIncomeString);
            String dateString = String.valueOf(System.currentTimeMillis());
            long millis = Long.parseLong(dateString); // parse the string value as a long value

            Instant instant = Instant.ofEpochMilli(millis); // create an Instant from the millis value

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // define a formatter for the output

            LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime(); // convert the Instant to a LocalDateTime in the default time zone
            String output = formatter.format(localDateTime); // format the LocalDateTime as a string using the formatter
            Category category = Database.getInstance(getContext()).getcategoryDAO().getCategoryByName(userId, itemSelected);
            Movement movements = new Movement(0, String.valueOf(userId), String.valueOf(category.getIdCategory()), String.valueOf(valueIncomeInt), description, output);
            this.viewModel.createMovementApi(movements);
            NavDirections action= AddIncomeFragmentDirections.actionAddIncomeFragmentToIncomeFragment2();
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
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getContext(), "Nenhum item selecionado", Toast.LENGTH_SHORT).show();
    }

}