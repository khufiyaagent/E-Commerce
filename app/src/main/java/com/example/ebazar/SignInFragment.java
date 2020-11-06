package com.example.ebazar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.ebazar.RegisterActivity.onResetPasswordFragment;

public class SignInFragment extends Fragment {


    private TextView dontHaveAnAccount;
    private FrameLayout parentFrameLayout;
    private EditText signInEmail;
    private EditText signInPassword;
    private ImageButton closeButton;
    private Button signButton;
    private ProgressBar progressBar;
    private TextView forgetPassword;
    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAnAccount = view.findViewById(R.id.tvDontHaveAnAccount);
        parentFrameLayout = getActivity().findViewById(R.id.register_frameLayout);
        signInEmail = view.findViewById(R.id.signInEmail);
        signInPassword = view.findViewById(R.id.signInPassword);
        progressBar = view.findViewById(R.id.signInProgressBar);
        signButton = view.findViewById(R.id.signInButton);
        closeButton = view.findViewById(R.id.signInCloseButton);
        forgetPassword = view.findViewById(R.id.singInForgotPassword);
        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignUpFragment());
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResetPasswordFragment=true;
                setFragment(new ResetPasswordFragment());
            }
        });

        signInEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signInPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailAndPassword();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainIntent();
            }
        });

    }


    private void checkEmailAndPassword() {

        if (signInEmail.getText().toString().matches(emailPattern)) {
            if (signInPassword.length() >= 8) {

                progressBar.setVisibility(View.VISIBLE);
                signButton.setEnabled(false);
                signButton.setTextColor(Color.argb(50, 255, 255, 255));

                firebaseAuth.signInWithEmailAndPassword(signInEmail.getText().toString(), signInPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mainIntent();
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            signButton.setEnabled(true);
                            signButton.setTextColor(Color.rgb(255, 255, 255));
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            } else {
                Toast.makeText(getActivity(), "Incorrect or Password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Incorrect or Password", Toast.LENGTH_SHORT).show();
        }
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(signInEmail.getText())) {
            if (!TextUtils.isEmpty(signInPassword.getText())) {
                signButton.setEnabled(true);
                signButton.setTextColor(Color.rgb(255, 255, 255));
            } else {
                signButton.setEnabled(true);
                signButton.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            signButton.setEnabled(true);
            signButton.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    private void mainIntent() {
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();
    }
}