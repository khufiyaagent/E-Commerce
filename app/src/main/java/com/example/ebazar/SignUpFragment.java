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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUpFragment extends Fragment {

    private TextView alreadyHaveAnAccount;
    private FrameLayout parentFrameLayout;
    private EditText signUpMail;
    private EditText personName;
    private EditText password;
    private EditText confirmPassword;
    private ImageButton closeButton;
    private ProgressBar signUpProgressBar;
    private Button btnSignUp;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    public SignUpFragment() {
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

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        alreadyHaveAnAccount = view.findViewById(R.id.tvAlreadyHaveAnAccount);
        parentFrameLayout = getActivity().findViewById(R.id.register_frameLayout);
        signUpMail = view.findViewById(R.id.singUpEmail);
        personName = view.findViewById(R.id.singUpFullname);
        password = view.findViewById(R.id.signUpPassword);
        confirmPassword = view.findViewById(R.id.signUpReenterPassword);
        closeButton = view.findViewById(R.id.signUpCloseButton);

        signUpProgressBar = view.findViewById(R.id.signUpProgressBar);
        btnSignUp = view.findViewById(R.id.signUpButton);
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseFirestore = FirebaseFirestore.getInstance();

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });

        signUpMail.addTextChangedListener(new TextWatcher() {
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
        personName.addTextChangedListener(new TextWatcher() {
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
        password.addTextChangedListener(new TextWatcher() {
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
        confirmPassword.addTextChangedListener(new TextWatcher() {
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

        btnSignUp.setOnClickListener(new View.OnClickListener() {
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

       /* Drawable customErrorIcon=getResources().getDrawable(R.mipmap.error_icon);
        customErrorIcon.setBounds(0,0,customErrorIcon.getIntrinsicWidth(),customErrorIcon.getIntrinsicHeight());
*/

        if (signUpMail.getText().toString().matches(emailPattern)) {
            if (password.getText().toString().equals(confirmPassword.getText().toString())) {

                signUpProgressBar.setVisibility(View.VISIBLE);
                btnSignUp.setEnabled(true);
                btnSignUp.setTextColor(Color.argb(50, 255, 255, 255));

                firebaseAuth.createUserWithEmailAndPassword(signUpMail.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Map<Object, String> userdata = new HashMap<>();

                                    userdata.put("full name", personName.getText().toString());

                                    firebaseFirestore.collection("USERS")
                                            .add(userdata)
                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if (task.isSuccessful()) {
                                                        mainIntent();
                                                    } else {
                                                        signUpProgressBar.setVisibility(View.INVISIBLE);
                                                        btnSignUp.setEnabled(true);
                                                        btnSignUp.setTextColor(Color.rgb(255, 255, 255));
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    signUpProgressBar.setVisibility(View.INVISIBLE);
                                    btnSignUp.setEnabled(true);
                                    btnSignUp.setTextColor(Color.rgb(255, 255, 255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {

                confirmPassword.setError("Password doesn't matched!");   //add second parameter as customErrorIcon
            }
        } else {
            signUpMail.setError("Invalid Email!");   //add second parameter as customErrorIcon
        }
    }

    private void checkInputs() {

        if (!TextUtils.isEmpty(signUpMail.getText())) {
            if (!TextUtils.isEmpty(personName.getText())) {
                if (!TextUtils.isEmpty(password.getText()) && password.length() >= 8) {
                    if (!TextUtils.isEmpty(confirmPassword.getText())) {

                        btnSignUp.setEnabled(true);
                        btnSignUp.setTextColor(Color.rgb(255, 255, 255));

                    } else {
                        btnSignUp.setEnabled(true);
                        btnSignUp.setTextColor(Color.argb(50, 255, 255, 255));
                    }
                } else {
                    btnSignUp.setEnabled(true);
                    btnSignUp.setTextColor(Color.argb(50, 255, 255, 255));
                }
            } else {
                btnSignUp.setEnabled(true);
                btnSignUp.setTextColor(Color.argb(50, 255, 255, 255));
            }

        } else {
            btnSignUp.setEnabled(true);
            btnSignUp.setTextColor(Color.argb(50, 255, 255, 255));
        }

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void mainIntent() {
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();
    }
}