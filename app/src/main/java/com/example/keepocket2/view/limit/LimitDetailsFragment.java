package com.example.keepocket2.view.limit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.keepocket2.R;
import com.example.keepocket2.data.Category;
import com.example.keepocket2.data.User;
import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.view.Session.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class LimitDetailsFragment extends Fragment {
    private Category categoryList;
    private long limitId;
    private long userId;
    private String categoryName;
    private  long idCategory;
    private EditText valueLimit;
    private Button edit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_limit_details, container, false);
        LimitDetailsFragmentArgs args = LimitDetailsFragmentArgs.fromBundle(getArguments());
        NavController  navController = NavHostFragment.findNavController(LimitDetailsFragment.this);
        idCategory = args.getCategoryId();
        this.categoryList = Database.getInstance(getContext()).getcategoryDAO().getById(limitId);
        User activeSession = SessionManager.getActiveSession(getContext());
        userId = activeSession.getId();
        valueLimit = root.findViewById(R.id.editTextLimitDetailsValue);
        this.valueLimit.setText(categoryList.getLimit());
        edit= root.findViewById(R.id.button5);
        edit.setOnClickListener(view->{
            String limitValueString = this.valueLimit.getText().toString();
            String nameCategory = categoryList.getCategoryName();
            int categoryLimit = Integer.parseInt(limitValueString);

            Category categoryedit = new Category(idCategory, nameCategory, categoryLimit, userId);
            Database.getInstance(getContext()).getcategoryDAO().updateCategory(categoryedit);
            NavDirections action = LimitDetailsFragmentDirections.actionLimitDetailsFragmentToLimitFragment2();
            navController.navigate(action);
        });


        return root;
    }
}