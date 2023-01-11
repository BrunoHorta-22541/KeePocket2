package com.example.keepocket2.view.Income;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;


public class IncomeDetailsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private List<String> categoryList;
    private long userId;
    private EditText descriptionIncome;
    private EditText valueIncome;
    private Spinner spinner;
    private String itemSelected;
    private long categoryId;
    private String spinnerStringValue;
    ArrayAdapter<String> spinnerAdapter;
    private long originalInsertDate;
    private  long idincome;
    private Button edit;
    private long incomeId;
    private Movement movement;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_income_details, container, false);

        IncomeDetailsFragmentArgs args = IncomeDetailsFragmentArgs.fromBundle(getArguments());
        NavController  navController = NavHostFragment.findNavController(IncomeDetailsFragment.this);
        idincome = args.getId();
        descriptionIncome = root.findViewById(R.id.editTextIncomeDescriptionDetails);
        valueIncome = root.findViewById(R.id.editTextIncomeDetailsValue);
        this.spinner = root.findViewById(R.id.incomeDetailsCategorySpinner);
        userId = args.getUserId();
        categoryList = Database.getInstance(getContext()).getcategoryDAO().getUserCategoryName(userId);
        movement = Database.getInstance(getContext()).getmovementsDAO().getById(idincome);
        categoryId = movement.getIdCategory();
        Category category = Database.getInstance(getContext()).getcategoryDAO().getById(categoryId);
        spinnerStringValue = category.getCategoryName();
        this.descriptionIncome.setText(movement.getDescription());
        this.valueIncome.setText(Integer.toString(movement.getValue()));
        this.originalInsertDate = movement.getMovementsDate();



        spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<String>());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.addAll(categoryList);
        spinnerAdapter.notifyDataSetChanged();
        spinner.setSelection(getIndex(spinner, spinnerStringValue));
        spinner.setOnItemSelectedListener(this);
        edit= root.findViewById(R.id.button5);
        edit.setOnClickListener(view->{
            String description = this.descriptionIncome.getText().toString();
            String valueExpenseString = this.valueIncome.getText().toString();
            int valueExpenseInt = Integer.parseInt(valueExpenseString);
            Category category2 = Database.getInstance(getContext()).getcategoryDAO().getCategoryByName(userId, itemSelected);
            Movement movements = new Movement(this.movement.getIdMovement(), userId, category2.getIdCategory(),valueExpenseInt, description, originalInsertDate);
            Database.getInstance(getContext()).getmovementsDAO().update(movements);

            NavDirections action= IncomeDetailsFragmentDirections.actionIncomeDetailsFragmentToIncomeFragment2();
            navController.navigate(action);

        });
        return root;
    }

    private int getIndex(Spinner spinner, String spinnerStringValue) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(spinnerStringValue)) {
                return i;
            }
        }
        return 0;
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