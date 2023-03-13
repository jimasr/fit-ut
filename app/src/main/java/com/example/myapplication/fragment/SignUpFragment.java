package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {
    private TextView signInLink;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);


        signInLink = view.findViewById(R.id.signInLink);

        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInFragment signInFragment = new SignInFragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.relativelayout, signInFragment)
                        .commit();
            }
        });

        return view;
    }
}