package com.example.keepocket2.account;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;


import com.example.keepocket2.R;
import com.example.keepocket2.data.User;
import com.example.keepocket2.data.localDatabase.Database;
import com.example.keepocket2.view.LoginActivity;
import com.example.keepocket2.view.Session.SessionManager;

import java.util.Random;
//codigo baseado no projeto https://github.com/Capa03/Five-Meals
public class AccountFragment extends Fragment {

    private Context context;
    private View view;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Navigation.findNavController(view).popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.context = container.getContext();
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    private Random random = new Random();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        TextView username = view.findViewById(R.id.user);
        TextView logout = view.findViewById(R.id.logout);
        TextView help = view.findViewById(R.id.help);

        User activeSession = SessionManager.getActiveSession(getContext());
        long userId = activeSession.getId();
        User getemail= Database.getInstance(this.context).getuserDAO().getById(userId);
        String email = getemail.getEmail();
        User userActive = Database.getInstance(this.context).getuserDAO().getByEmail(email);
        username.setText(email);


        username.setText(userActive.getEmail());
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = (NavDirections) AccountFragmentDirections.actionAccountFragmentToHelpFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SessionManager.clearSession(context);
                LoginActivity.startActivity(context);
            }
        });
    }

}