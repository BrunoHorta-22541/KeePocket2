package com.example.keepocket2.view.Category;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.keepocket2.R;
import com.example.keepocket2.data.Category;
import com.example.keepocket2.data.User;
import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.view.HomeFragmentDirections;
import com.example.keepocket2.view.Session.SessionManager;
import com.example.keepocket2.viewmodel.CategoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class CategoryFragment extends Fragment implements CategoryAdapter.CategoryAdapterEventListener{
    private View root;
    private CategoryAdapter adapter;
    private long userId;
    private NavController navController;
    private FloatingActionButton addCategory;
    private CategoryViewModel viewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_category, container, false);
        this.viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        navController = NavHostFragment.findNavController(CategoryFragment.this);
        RecyclerView recyclerView = root.findViewById(R.id.categoryRecyclerView);
        this.adapter = new CategoryAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        //this.updateCategoryList();
        addCategory=root.findViewById(R.id.floatingActionButton2);
        addCategory.setOnClickListener(view->{
            NavDirections action= CategoryFragmentDirections.actionCategoryFragmentToAddCategoryFragment(userId);
            navController.navigate(action);
        });
        return root;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User activeSession = SessionManager.getActiveSession(getContext());
        userId = activeSession.getId();
        this.viewModel.getCategoryById(userId).observe(getViewLifecycleOwner(), categories -> adapter.updateCategoryList(categories));

    }

    @Override
    public void onStart() {
        super.onStart();
    }

  /*  public void updateCategoryList(){
        User activeSession = SessionManager.getActiveSession(getContext());
        userId = activeSession.getId();
        this.adapter.updateCategoryList(categoryList);
    }*/

    @Override
    public void onCategoryClicked(long categoryId) {
        NavDirections action = CategoryFragmentDirections.actionCategoryFragmentToCategoryDetailsFragment(userId,categoryId);
        navController.navigate(action);
    }

    @Override
    public void onCategoryLongClicked(long categoryId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Delete Category?");
        builder.setMessage("Do you really want to delete this Category?");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Código a ser executado quando o utilizador clica em Cancel
            }
        });
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Código a ser executado quando o utilizador clica em Delete

                CategoryFragment.this.viewModel.deleteCategory(categoryId);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}