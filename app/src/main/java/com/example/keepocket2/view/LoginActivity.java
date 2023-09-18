package com.example.keepocket2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.keepocket2.R;
import com.example.keepocket2.data.User;
import com.example.keepocket2.view.Session.LoginManager;
import com.example.keepocket2.view.Session.SessionManager;
import com.example.keepocket2.viewmodel.CategoryViewModel;
import com.example.keepocket2.viewmodel.MovementViewModel;
import com.example.keepocket2.viewmodel.UserViewModel;

import androidx.lifecycle.ViewModelProvider;
public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private CheckBox checkBoxRemeberMe;
    private UserViewModel userViewModel;
    private MovementViewModel movementViewModel;
    private CategoryViewModel categoryViewModel;
    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.editTextEmail = findViewById(R.id.editTextEmail);
        this.editTextPassword = findViewById(R.id.editTextPassword);
        this.checkBoxRemeberMe = findViewById(R.id.checkBoxRemeberMe);
        LoginManager loginManager = new LoginManager(getApplication());
        this.userViewModel = new UserViewModel(this.getApplication());
        this.categoryViewModel = new CategoryViewModel(this.getApplication());
        this.movementViewModel = new MovementViewModel(this.getApplication());
        userViewModel.refreshUser();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SessionManager.sessionExists(getApplicationContext())) {
            MainActivity.startActivity(this);
            finish();
        }
    }

    public void thelogin(View view) {

        // Código para efetuar login
        String email = this.editTextEmail.getText().toString();
        String password = this.editTextPassword.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Email cannot be empty!", Toast.LENGTH_LONG).show();
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Password cannot be empty!", Toast.LENGTH_LONG).show();
        }
        User user = LoginManager.validateUser(email, password);
        if (user != null) {
            // logado
            SessionManager.saveSession(this, email, checkBoxRemeberMe.isChecked(), user.getId(),password,user.getName(),user.getEmail_verified_at(),user.getRemember_token(),user.getCreated_at(),user.getUpdated_at());
            MainActivity.startActivity(this);

        } else if(user == null) {
            // mostrar erro
            Toast.makeText(this, "Credenciais inválidas", Toast.LENGTH_LONG).show();
        }
    }

    public void signuplogin(View view) {
        SignUp.startActivity(this);
    }
}