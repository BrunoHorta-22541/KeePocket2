package com.example.keepocket2.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.keepocket2.R;
import com.example.keepocket2.data.User;
import com.example.keepocket2.view.Session.LoginManager;
import com.example.keepocket2.view.Session.SessionManager;


public class LoginFragment extends Fragment {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private CheckBox checkBoxRemeberMe;
    private NavController navController;
    private Button login;
    private View root;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_login, container, false);
        navController = NavHostFragment.findNavController(LoginFragment.this);
        login = root.findViewById(R.id.button);
        this.editTextEmail = root.findViewById(R.id.editTextEmail);
        this.editTextPassword = root.findViewById(R.id.editTextPassword);
        this.checkBoxRemeberMe = root.findViewById(R.id.checkBoxRemeberMe);

        // login.setOnClickListener(view -> {
        //                navController.navigate(R.id.action_loginFragment_to_homeFragment);
        //            });
        if (SessionManager.persistedSession(getActivity().getApplicationContext())) {
            //acessar a home fragment se a sessao persistir
            navController.navigate(R.id.action_loginFragment_to_homeFragment);
        }



        return root;
    }

    public void thelogin(View view) {
        String email = this.editTextEmail.getText().toString();
        String password = this.editTextPassword.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(getActivity(), "Email cannot be empty!", Toast.LENGTH_LONG).show();
        }
        if (password.isEmpty()) {
            Toast.makeText(getActivity(), "Password cannot be empty!", Toast.LENGTH_LONG).show();
        }
        User loggedInUser = LoginManager.validateUser(email, password);
        if (loggedInUser != null) {
            SessionManager.saveSession(getActivity(), email, checkBoxRemeberMe.isChecked(), loggedInUser.getId());
            Toast.makeText(getActivity(), "Login com sucesso", Toast.LENGTH_LONG).show();
            login.setOnClickListener(root -> {
                NavDirections action =
                        LoginFragmentDirections.actionLoginFragmentToHomeFragment();

                navController.navigate(action);
            });
        } else {
            Toast.makeText(getActivity(), "Credenciais inválidas", Toast.LENGTH_LONG).show();
        }
    }
}