package com.example.keepocket2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.keepocket2.R;
import com.example.keepocket2.data.User;
import com.example.keepocket2.view.Session.LoginManager;
import com.example.keepocket2.view.Session.SessionManager;
import com.example.keepocket2.viewmodel.UserViewModel;

public class SignUp extends AppCompatActivity {
    private UserViewModel userViewModel;
    private Context context;
    private EditText editTextEmail;
    private EditText editTextPassword;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, SignUp.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.context = this;
        this.userViewModel = new UserViewModel(this.getApplication());
    }

    public void signup(View view) {
        this.editTextEmail= findViewById(R.id.editTextEmail);
        this.editTextPassword = findViewById(R.id.editTextPassword);
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(this, "Email cannot be empty!", Toast.LENGTH_LONG).show();

        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Password cannot be empty!", Toast.LENGTH_LONG).show();
            SignUp.startActivity(this);
        }else if(!email.isEmpty() && !password.isEmpty()){
            boolean isSignedUp = userViewModel.createUserApi(email, password);
            Toast.makeText(context, "Cadastrado com sucesso", Toast.LENGTH_LONG).show();
            LoginActivity.startActivity(context);
        } else {
            // show error message
            SignUp.startActivity(this);
            Toast.makeText(context, "Erro ao cadastrar", Toast.LENGTH_LONG).show();
        }
    }
}