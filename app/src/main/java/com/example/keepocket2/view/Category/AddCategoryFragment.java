package com.example.keepocket2.view.Category;

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

import com.example.keepocket2.R;
import com.example.keepocket2.data.Category;
import com.example.keepocket2.data.User;
import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.view.Session.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class AddCategoryFragment extends Fragment {

    private EditText editTextCategoryName;
    private long userId;
    private Button save;
    private FloatingActionButton previous;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View root= inflater.inflate(R.layout.fragment_add_category, container, false);
        NavController navController = NavHostFragment.findNavController(AddCategoryFragment.this);
        this.editTextCategoryName = root.findViewById(R.id.categoryNameEditText);
        save = root.findViewById(R.id.button2);
        save.setOnClickListener(view->{
            User activeSession = SessionManager.getActiveSession(getContext());
            userId = activeSession.getId();
            String nameCategory = this.editTextCategoryName.getText().toString();

            Category category = new Category(0,nameCategory,0,userId);
            Database.getInstance(getContext()).getcategoryDAO().insert(category);

            NavDirections action = AddCategoryFragmentDirections.actionAddCategoryFragmentToCategoryFragment();
            navController.navigate(action);
        });



       return root;
    }
}