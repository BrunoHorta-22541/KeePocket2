package com.example.keepocket2.view.Income;

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
import com.example.keepocket2.data.Movement;
import com.example.keepocket2.data.User;
import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.view.Session.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class IncomeFragment extends Fragment implements IncomeAdapter.IncomeAdapterEventListener{
    private View root;
    private IncomeAdapter adapter;
    private long userId;
    private NavController navController;
    private FloatingActionButton addIncome;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_income, container, false);
        navController = NavHostFragment.findNavController(com.example.keepocket2.view.Income.IncomeFragment.this);
        RecyclerView recyclerViewIncome = root.findViewById(R.id.incomeRecyclerView);
        this.adapter = new IncomeAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewIncome.setAdapter(adapter);
        recyclerViewIncome.setLayoutManager(layoutManager);
        addIncome=root.findViewById(R.id.floatingActionButton4);
        addIncome.setOnClickListener(view -> {
            NavDirections action = IncomeFragmentDirections.actionIncomeFragment2ToAddIncomeFragment();
            navController.navigate(action);
        });
        this.updateCategoryList();

        return root;
    }

    public void updateCategoryList(){
        User activeSession = SessionManager.getActiveSession(getContext());
        userId = activeSession.getId();
        List<Movement> movementsList = Database.getInstance(getContext()).getmovementsDAO().getIncome(this.userId);
        this.adapter.updateIncomeList(movementsList);

    }

    @Override
    public void onIncomeClicked(long categoryId) {
        NavDirections action = IncomeFragmentDirections.actionIncomeFragment2ToAddIncomeFragment();
        navController.navigate(action);
    }

    @Override
    public void onIncomeLongClicked(long categoryId) {
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
                Category categoryname = Database.getInstance(IncomeFragment.this.getContext()).getcategoryDAO().getById(categoryId);
                Category category = new Category(categoryId,categoryname.getCategoryName(),0,userId);
                Database.getInstance(IncomeFragment.this.getContext()).getcategoryDAO().update(category);
                IncomeFragment.this.updateCategoryList();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}