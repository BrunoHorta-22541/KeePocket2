package com.example.keepocket2.view.Category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.keepocket2.R;
import com.example.keepocket2.data.Category;
import com.example.keepocket2.data.User;
import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.view.Session.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.keepocket2.viewmodel.CategoryViewModel;

public class CategoryDetailsFragment extends Fragment {
    private long userId;
    private TextView categoryName;
    private Button edit;
    private  long idCategory;
    private CategoryViewModel viewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View root = inflater.inflate(R.layout.fragment_category_details, container, false);
        this.viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        CategoryDetailsFragmentArgs args = CategoryDetailsFragmentArgs.fromBundle(getArguments());
        NavController  navController = NavHostFragment.findNavController(CategoryDetailsFragment.this);
        idCategory = args.getCategoryId();
        Category category =this.viewModel.getCategoryWithID(idCategory);
      
        userId = args.getId();
        categoryName = root.findViewById(R.id.categoryNameEditText2);
        this.categoryName.setText(category.getCategoryName());
        edit= root.findViewById(R.id.button7);
        edit.setOnClickListener(view->{
            String nameCategory = this.categoryName.getText().toString();
            if (nameCategory.isEmpty()) {
                // TODO dar erro
            } else {
                try{
                    String actuallimit = category.getLimit();
                    Category categoryedit = new Category(idCategory, nameCategory, actuallimit,String.valueOf(userId));
                    CategoryDetailsFragment.this.viewModel.updateCategoryApi(categoryedit);
                    NavDirections action = CategoryDetailsFragmentDirections.actionCategoryDetailsFragmentToCategoryFragment();
                    navController.navigate(action);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });


        return root;
    }


}