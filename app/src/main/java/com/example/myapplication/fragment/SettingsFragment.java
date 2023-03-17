package com.example.myapplication.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private TextInputEditText nameTextInput;
    private TextInputEditText emailTextInput;
    private TextInputEditText passwordTextInput;
    private TextView editPhoto;
    private ImageView profilePhoto;
    private FirebaseStorage storage;
    private Uri imageUri;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        nameTextInput = view.findViewById(R.id.nameTextInput);
        emailTextInput = view.findViewById(R.id.emailTextInput);
        passwordTextInput = view.findViewById(R.id.passwordTextInput);
        editPhoto = view.findViewById(R.id.editPhoto);
        profilePhoto = view.findViewById(R.id.profileView);
        storage = FirebaseStorage.getInstance();
        updateUI();


        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        imageUri = intent.getData();
                        profilePhoto.setImageURI(imageUri);
                    }
                }
        );

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        profilePhoto.setImageURI(imageUri);
                    }
                }
        );
        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchImagePicker();
            }
        });


        return view;
    }

    private void updateUI() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            String password = "*******";

            if(photoUrl != null) {
                profilePhoto.setImageURI(photoUrl);
            }

            nameTextInput.setHint(name);
            emailTextInput.setHint(email);
            passwordTextInput.setHint(password);

        }
    }

    private void launchImagePicker() {
        String[] choice = {"Gallery", "Camera"};

        AlertDialog.Builder alertImage = new AlertDialog.Builder(getActivity());

        alertImage.setTitle("Pick the photo from")
                .setItems(choice, (dialogInterface, i) -> {
                    switch (i) {
                        case 0:
                            Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            galleryIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                            galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            galleryLauncher.launch(galleryIntent);
                            break;
                        case 1:
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                            Uri imagePath = createImage();
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imagePath);
                            if(requestCameraPermission()) {
                                cameraLauncher.launch(cameraIntent);
                            }
                            break;
                    }
                });

        AlertDialog dialog = alertImage.create();
        dialog.show();
    }

    private Uri createImage(){
        StorageReference imageReference = storage.getReference().child("images");

        Uri uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        ContentResolver contentResolver = getContext().getContentResolver();
        String imageName = String.valueOf(System.currentTimeMillis());
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, imageName + ".jpg");
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/");

        Uri finalURI = contentResolver.insert(uri, contentValues);
        imageUri = finalURI;

        return finalURI;
    }

    private boolean requestCameraPermission() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }
        return true;
    }
}