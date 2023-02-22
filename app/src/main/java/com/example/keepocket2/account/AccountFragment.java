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
        ImageView imageView = view.findViewById(R.id.imageViewAccountImage);
        TextView username = view.findViewById(R.id.textViewAccountUsername);
        TextView logout = view.findViewById(R.id.textViewAccountLogout);
        TextView help = view.findViewById(R.id.textViewAccountHelp);

        imageView.setImageResource(R.drawable.profile_picture);
        String user = SessionManager.getActiveSession(this.context);
        username.setText(user);

        User userActive = AppDataBase.getInstance(this.context).getUserDAO().getUserByUsername(user);
        email.setText(userActive.getEmail());

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = (NavDirections) AccountFragmentDirections.actionAccountFragmentToChangePasswordFragment2();
                Navigation.findNavController(view).navigate(action);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SessionManager.clearSession(context);
                PreLoginActivity.startActivity(context);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = (NavDirections) AccountFragmentDirections.actionAccountFragmentToHelpFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

}