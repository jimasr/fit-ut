package com.example.myapplication.util;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class StorageManager {
    public StorageReference storageRef;
    public FirebaseStorage storage;
    private static StorageManager instance;

    private StorageManager() {
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child("users");
    }

    public static StorageManager getInstance() {
        if(instance != null) {
            return instance;

        }
        instance = new StorageManager();
        return instance;
    }

    /**
     * Upload a file to database
     * @param file
     */
    private void uploadData(Uri file) {
        StorageReference riversRef = storageRef.child(file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("StorageManager", "Image fail to be uploaded");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i("StorageManager", "Image uploaded successfully");
                Log.i("StorageManager", String.valueOf(taskSnapshot.getMetadata()));
            }
        });
    }

    public void retrieveData(FirebaseUser user) {

    }

    /**
     * Delete data in database
     * @param file
     */
    private void deleteData(Uri file) {
        StorageReference desertRef = storageRef.child(file.getLastPathSegment());

        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("StorageManager", "Image deleted!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("StorageManager", "Image fail to be deleted");
            }
        });
    }

    /**
     * Update user image in database
     * @param user User in question
     * @param file Path to the new file
     */
    public void updateImage(FirebaseUser user, Uri file) {
        Uri oldImage = user.getPhotoUrl();
        uploadData(file);
        deleteData(oldImage);
    }
}
