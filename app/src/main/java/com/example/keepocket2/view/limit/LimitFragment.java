package com.example.keepocket2.view.limit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.keepocket2.R;
import com.example.keepocket2.data.Category;
import com.example.keepocket2.data.User;
import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.view.Session.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class LimitFragment extends Fragment implements LimitAdapter.LimitAdapterEventListener{
    private View root;
    private LimitAdapter adapter;
    private long userId;
    private NavController navController;
    private FloatingActionButton addLimit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_limit, container, false);
        navController = NavHostFragment.findNavController(LimitFragment.this);
        RecyclerView recyclerViewLimit = root.findViewById(R.id.recyclerViewLimit);
        this.adapter = new LimitAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewLimit.setAdapter(adapter);
        recyclerViewLimit.setLayoutManager(layoutManager);
        addLimit=root.findViewById(R.id.floatingActionButton3);
        addLimit.setOnClickListener(view -> {
            NavDirections action = LimitFragmentDirections.actionLimitFragment2ToAddLimitFragment(userId);
            navController.navigate(action);
        });
        this.updateCategoryList();

        return root;
    }

    public void updateCategoryList(){
        User activeSession = SessionManager.getActiveSession(getContext());
        userId = activeSession.getId();
        List<Category> categoryList = Database.getInstance(getContext()).getcategoryDAO().getUserCategoryLimit(userId);
        this.adapter.updateCategoryList(categoryList);

    }

    @Override
    public void onLimitClicked(long categoryId) {
        NavDirections action = LimitFragmentDirections.actionLimitFragment2ToAddLimitFragment(categoryId);
        navController.navigate(action);
    }

    @Override
    public void onLimitLongClicked(long categoryId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Delete Limit?");
        builder.setMessage("Do you really want to delete this Limit?");

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
                Category categoryname = Database.getInstance(LimitFragment.this.getContext()).getcategoryDAO().getById(categoryId);
                Category category = new Category(categoryId,categoryname.getCategoryName(),0,userId);
                Database.getInstance(LimitFragment.this.getContext()).getcategoryDAO().updateCategory(category);
                LimitFragment.this.updateCategoryList();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}