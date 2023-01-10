package com.example.keepocket2.view.Expense;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.keepocket2.R;
import com.example.keepocket2.data.Movement;
import com.example.keepocket2.data.User;
import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.view.Session.SessionManager;
import com.example.keepocket2.view.limit.LimitFragment;
import com.example.keepocket2.view.limit.LimitFragmentDirections;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class ExpenseFragment extends Fragment implements ExpensesAdapter.ExpensesAdapterEventListener{
    private ExpensesAdapter adapter;
    private long userId;
    private NavController navController;
    private FloatingActionButton addExpense;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_expense, container, false);
        navController = NavHostFragment.findNavController(ExpenseFragment.this);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewExpenses);
        this.adapter = new ExpensesAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        User activeSession = SessionManager.getActiveSession(getContext());
        userId = activeSession.getId();
        addExpense = root.findViewById(R.id.floatingActionButton);
        addExpense.setOnClickListener(view->{
            NavDirections action = ExpenseFragmentDirections.actionExpenseFragmentToAddExpenseFragment();
            navController.navigate(action);
        });
        return root;
    }

    @Override
    public void onExpenseClicked(long movementsId) {
        NavDirections action = ExpenseFragmentDirections.actionExpenseFragmentToEspenseFragmentDetails(movementsId);
        navController.navigate(action);

    }

    @Override
    public void onExpenseLongClicked(long movementsId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Delete Expense?");
        builder.setMessage("Do you really want to delete this Expense?");

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
                Movement movements = Database.getInstance(getContext()).getmovementsDAO().getById(movementsId);
                Database.getInstance(getContext()).getmovementsDAO().delete(movements);
                ExpenseFragment.this.updateExpenseList();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.updateExpenseList();
    }

    private void updateExpenseList() {
        List<Movement> movementsList = Database.getInstance(getContext()).getmovementsDAO().getExpense(this.userId);
        this.adapter.updateIncomeList(movementsList);
    }
}