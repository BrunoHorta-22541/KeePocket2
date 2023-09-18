package com.example.keepocket2.view.limit;

import android.os.Bundle;
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
import com.example.keepocket2.data.User;
import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.view.Category.AddCategoryFragmentDirections;
import com.example.keepocket2.view.Category.CategoryDetailsFragmentArgs;
import com.example.keepocket2.view.Session.SessionManager;

import java.util.ArrayList;
import java.util.List;
import com.example.keepocket2.viewmodel.CategoryViewModel;

public class AddLimitFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerLimit;

    private EditText editTextLimit;
    private String itemSelected;
    ArrayAdapter<String> spinnerAdapter;
    private List<String> categoryList;
    private long userId;
    private Button save;
    private CategoryViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_add_limit, container, false);
        NavController navController = NavHostFragment.findNavController(AddLimitFragment.this);
        AddLimitFragmentArgs arg = AddLimitFragmentArgs.fromBundle(getArguments());
        userId = arg.getUserId();
        this.viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        this.editTextLimit = root.findViewById(R.id.editTextLimitValue);
        this.spinnerLimit = root.findViewById(R.id.spinnerLimit);
        categoryList = Database.getInstance(getContext()).getcategoryDAO().getUserCategoryName(userId);
        spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<String>());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLimit.setAdapter(spinnerAdapter);
        spinnerAdapter.addAll(categoryList);
        spinnerAdapter.notifyDataSetChanged();
        spinnerLimit.setOnItemSelectedListener(this);
        save = root.findViewById(R.id.button6);
        save.setOnClickListener(view->{
            String limitValueString = this.editTextLimit.getText().toString();
            //int limitValueInt = Integer.parseInt(limitValueString);

                    Category category = Database.getInstance(getContext()).getcategoryDAO().getCategoryByName(userId,itemSelected);
                    Category categoryUpdate = new Category(category.getIdCategory(),category.getCategoryName(),limitValueString,String.valueOf(userId));
                    this.viewModel.updateCategoryApi(categoryUpdate);
                    NavDirections action = AddLimitFragmentDirections.actionAddLimitFragmentToLimitFragment2();
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