package com.example.keepocket2.view.Expense;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
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
import com.example.keepocket2.view.Category.CategoryFragment;
import com.example.keepocket2.view.Session.SessionManager;
import com.example.keepocket2.viewmodel.MovementViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ExpenseFragment extends Fragment implements ExpensesAdapter.ExpensesAdapterEventListener{
    private ExpensesAdapter adapter;
    private long userId;
    private NavController navController;
    private FloatingActionButton addExpense;
    private MovementViewModel viewModel;

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
        this.viewModel = new ViewModelProvider(this).get(MovementViewModel.class);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User activeSession = SessionManager.getActiveSession(getContext());
        userId = activeSession.getId();
        this.viewModel.getExpenseById(userId).observe(getViewLifecycleOwner(), categories -> adapter.updateExpenseList(categories));
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
                ExpenseFragment.this.viewModel.deleteMovement(movementsId);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.viewModel.refreshTicket();
    }
}