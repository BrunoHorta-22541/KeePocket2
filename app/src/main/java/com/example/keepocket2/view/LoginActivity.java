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

import androidx.lifecycle.ViewModelProvider;
public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private CheckBox checkBoxRemeberMe;
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
            SessionManager.saveSession(this, email, checkBoxRemeberMe.isChecked(), user.getId(),password);
            MainActivity.startActivity(this);
        } else {
            // mostrar erro
            Toast.makeText(this, "Credenciais inválidas", Toast.LENGTH_LONG).show();
        }
    }

    public void signuplogin(View view) {
        SignUp.startActivity(this);
    }
}