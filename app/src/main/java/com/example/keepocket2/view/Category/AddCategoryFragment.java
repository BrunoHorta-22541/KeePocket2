package com.example.keepocket2.view.Category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.keepocket2.R;
import com.example.keepocket2.data.Category;
import com.example.keepocket2.data.User;
import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.view.Session.SessionManager;
import com.example.keepocket2.viewmodel.CategoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class AddCategoryFragment extends Fragment {

    private EditText editTextCategoryName;
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
        View root = inflater.inflate(R.layout.fragment_add_category, container, false);
        this.viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        AddCategoryFragmentArgs args = AddCategoryFragmentArgs.fromBundle(getArguments());
        userId = args.getUserId();


        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = NavHostFragment.findNavController(AddCategoryFragment.this);
        this.editTextCategoryName = view.findViewById(R.id.categoryNameEditText);
        save = view.findViewById(R.id.button2);
        save.setOnClickListener(view1-> {

            String nameCategory = this.editTextCategoryName.getText().toString();
            if (nameCategory.isEmpty()) {
                // TODO dar erro
            } else {
                try{
                    Category category = new Category(0,nameCategory,0,userId);
                    this.viewModel.createCategoryApi(category);
                    NavDirections action = AddCategoryFragmentDirections.actionAddCategoryFragmentToCategoryFragment();
                    navController.navigate(action);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}