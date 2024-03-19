package com.example.keepocket2.view.Expense;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
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
import com.example.keepocket2.viewmodel.CategoryViewModel;
import com.example.keepocket2.viewmodel.MovementViewModel;

import java.util.ArrayList;
import java.util.List;


public class EspenseFragmentDetails extends Fragment implements AdapterView.OnItemSelectedListener {
    private List<String> categoryList;
    private long expenseMovementId;
    private long userId;
    private EditText descriptionExpense;
    private EditText valueExpense;
    private Spinner spinner;
    private String itemSelected;
    private Movement movements;
    private long categoryId;
    private String spinnerStringValue;
    ArrayAdapter<String> spinnerAdapter;
    private String originalInsertDate;
    private Button edit;
    private NavController navController;
    private MovementViewModel viewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_espense_details, container, false);

        navController = NavHostFragment.findNavController(EspenseFragmentDetails.this);
        this.viewModel = new ViewModelProvider(this).get(MovementViewModel.class);
        EspenseFragmentDetailsArgs args = EspenseFragmentDetailsArgs.fromBundle(getArguments());
        expenseMovementId = args.getMovementId();
        User activeSession = SessionManager.getActiveSession(getContext());
        userId = activeSession.getId();
        this.descriptionExpense = root.findViewById(R.id.editTextExpenseDescriptionDetails);
        this.spinner = root.findViewById(R.id.expenseDetailsCategorySpinner);
        this.valueExpense = root.findViewById(R.id.editTextExpenseDetailsValue);

        categoryList = Database.getInstance(getContext()).getcategoryDAO().getUserCategoryName(userId);
        movements = Database.getInstance(getContext()).getmovementsDAO().getById(expenseMovementId);
        categoryId = Integer.parseInt(movements.getIdCategory());
        Category category = Database.getInstance(getContext()).getcategoryDAO().getById(categoryId);
        spinnerStringValue = category.getCategoryName();
        this.descriptionExpense.setText(movements.getDescription());
        int positive = Integer.parseInt(movements.getValue()) * (-1);
        String stringValueExpenses = Integer.toString(positive);
        this.valueExpense.setText(stringValueExpenses);
        this.originalInsertDate = movements.getMovementsDate();
        spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<String>());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.addAll(categoryList);
        spinnerAdapter.notifyDataSetChanged();
        spinner.setSelection(getIndex(spinner, spinnerStringValue));
        spinner.setOnItemSelectedListener(this);
        edit = root.findViewById(R.id.button5);
        edit.setOnClickListener(view->{
            String description = this.descriptionExpense.getText().toString();
            String valueExpenseString = this.valueExpense.getText().toString();
            int valueExpenseInt = Integer.parseInt(valueExpenseString);
            int valueExpenseNegative= valueExpenseInt * (-1);
            Category categorygetid = Database.getInstance(getContext()).getcategoryDAO().getCategoryByName(userId, itemSelected);
            Movement movements = new Movement(this.movements.getIdMovement(), String.valueOf(userId), String.valueOf(categorygetid.getIdCategory()),String.valueOf(valueExpenseNegative), description, originalInsertDate);
            viewModel.updateExpenseApi(movements);
            NavDirections action = EspenseFragmentDetailsDirections.actionEspenseFragmentDetailsToExpenseFragment();
            navController.navigate(action);
        });
        return root;
    }
    private int getIndex(Spinner spinner, String spinnerStringValue){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(spinnerStringValue)){
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
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getContext(), "Nenhum item selecionado", Toast.LENGTH_SHORT).show();
    }
}