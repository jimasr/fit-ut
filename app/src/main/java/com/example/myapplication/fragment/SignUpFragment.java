package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AuthActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {
    private FirebaseAuth auth;
    private TextView signInLink;
    private Button signUpButton;
    private TextInputEditText nameSignUpInput;
    private TextInputEditText emailSignUpInput;
    private TextInputEditText passwordSignUpInput;

    public SignUpFragment() {
    }

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        signInLink = view.findViewById(R.id.signInLink);
        signUpButton = view.findViewById(R.id.signUpButton);
        nameSignUpInput = view.findViewById(R.id.nameSignUpInput);
        emailSignUpInput = view.findViewById(R.id.emailSignUpInput);
        passwordSignUpInput = view.findViewById(R.id.passwordSignUpInput);

        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInFragment signInFragment = new SignInFragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.relativelayout, signInFragment)
                        .commit();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameSignUpInput.getText().toString();
                String email = emailSignUpInput.getText().toString();
                String password = passwordSignUpInput.getText().toString();

                final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";

                boolean valid = true;

                if(name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getActivity(), "Input cannot be empty!",
                            Toast.LENGTH_SHORT).show();
                    valid = false;
                }

                if(valid && !Patterns.EMAIL_ADDRESS.matcher(email).matches() ) {
                    Toast.makeText(getActivity(), "Email must be valid!",
                            Toast.LENGTH_SHORT).show();
                    valid = false;
                }

                if(!Pattern.compile(PASSWORD_PATTERN).matcher(password).matches()) {
                    Toast.makeText(getActivity(), "Password must contain a digit, an uppercase and lowercase letter, a special character and be above 6 characters!",
                            Toast.LENGTH_SHORT).show();
                }

                if(valid) {
                    signUpEmail(email, password);
                }
            }
        });

        return view;
    }

    public void signUpEmail(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AuthActivity", "User signed up!");

                            Toast.makeText(getContext(), "Successfully created an account",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AuthActivity", "Account creation failed", task.getException());
                            Toast.makeText(getActivity(), "User not valid",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}