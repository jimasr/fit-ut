package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AuthActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {
    private FirebaseAuth auth;
    private TextView signUpLink;
    private Button signInButton;
    private  TextInputEditText emailInput;

    private TextInputEditText passwordInput;
    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        signUpLink = view.findViewById(R.id.signUpLink);
        signInButton = view.findViewById(R.id.signInButton);
        emailInput = view.findViewById(R.id.emailLoginInput);
        passwordInput = view.findViewById(R.id.passwordLoginInput);

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpFragment signUpFragment = new SignUpFragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.relativelayout, signUpFragment)
                        .commit();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                Log.w("AuthActivity", "Click");

                if(!email.isEmpty() && !password.isEmpty()) {
                    Log.w("AuthActivity", "Going to sign in method");

                    signInEmail(email, password);
                } else {
                    Toast.makeText(getContext(), "Email or password cannot be empty",
                            Toast.LENGTH_SHORT).show();
                }




            }
        });



        return view;
    }
    private void signInEmail(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AuthActivity", "Signed in!");
                            FirebaseUser user = auth.getCurrentUser();

                            Toast.makeText(getContext(), "Successfully signed in.",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AuthActivity", "Failure in signing in", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}