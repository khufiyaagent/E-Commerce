package com.example.ebazar;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordFragment extends Fragment {


    private EditText registeredEmail;
    private Button resetPasswordButton;
    private TextView goBack;
    private FrameLayout parentFrameLayout;
    private FirebaseAuth firebaseAuth;
    private ImageView forgetEmailIcon;
    private TextView forgetPasswordEmailIconText;
    private ProgressBar forgetProgressBar;
    private ViewGroup emailIconContainer;

    public ResetPasswordFragment() {
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

        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        registeredEmail = view.findViewById(R.id.forgetPasswordEmail);
        resetPasswordButton = view.findViewById(R.id.resetPasswordButton);
        goBack = view.findViewById(R.id.forgetPasswordGoback);
        parentFrameLayout = getActivity().findViewById(R.id.register_frameLayout);
        firebaseAuth = FirebaseAuth.getInstance();
        forgetEmailIcon=view.findViewById(R.id.forgetPasswordEmailIcon);
        forgetPasswordEmailIconText=view.findViewById(R.id.forgetPasswordEmailIconText);
        forgetProgressBar=view.findViewById(R.id.forgetPasswordProgressBar);
        emailIconContainer=view.findViewById(R.id.forgetPasswordIconContainer);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registeredEmail.addTextChangedListener(new TextWatcher() {
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
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.sendPasswordResetEmail(registeredEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                TransitionManager.beginDelayedTransition(emailIconContainer);
                                forgetPasswordEmailIconText.setVisibility(View.GONE);

                                forgetEmailIcon.setVisibility(View.VISIBLE);
                                forgetProgressBar.setVisibility(View.VISIBLE);
                                resetPasswordButton.setEnabled(false);
                                resetPasswordButton.setTextColor(Color.argb(50, 255, 255, 255));

                                if (task.isSuccessful()){
                                    /*ScaleAnimation scaleAnimation=new ScaleAnimation(1,0,1,0);
                                    scaleAnimation.setDuration(100);
                                    scaleAnimation.setInterpolator(new AccelerateInterpolator());
                                    scaleAnimation.setRepeatMode(Animation.REVERSE);
                                    scaleAnimation.setRepeatCount(1);
                                    scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });
                                    forgetEmailIcon.startAnimation(scaleAnimation);*/
                                    forgetEmailIcon.setImageResource(R.drawable.mail_outline_green24);
                                    TransitionManager.beginDelayedTransition(emailIconContainer);
                                    forgetPasswordEmailIconText.setVisibility(View.VISIBLE);
                                    forgetPasswordEmailIconText.setText("Recovery mail sent successfully! Open Inbox");
                                    forgetPasswordEmailIconText.setTextColor(getResources().getColor(R.color.successGreen));

                                }else {
                                    String Error=task.getException().getMessage();
                                    resetPasswordButton.setEnabled(true);
                                    resetPasswordButton.setTextColor(Color.rgb(255, 255, 255));
                                    forgetEmailIcon.setImageResource(R.drawable.mail_outline_red_24);
                                    forgetPasswordEmailIconText.setText(Error);
                                    forgetPasswordEmailIconText.setTextColor(getResources().getColor(R.color.colorRed));
                                    TransitionManager.beginDelayedTransition(emailIconContainer);
                                    forgetPasswordEmailIconText.setVisibility(View.VISIBLE);
                                }
                                forgetProgressBar.setVisibility(View.GONE);

                            }
                        });
            }
        });

    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(registeredEmail.getText())) {
            resetPasswordButton.setEnabled(true);
            resetPasswordButton.setTextColor(Color.rgb(255, 255, 255));

        } else {
            resetPasswordButton.setEnabled(false);
            resetPasswordButton.setTextColor(Color.argb(50, 255, 255, 255));

        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}