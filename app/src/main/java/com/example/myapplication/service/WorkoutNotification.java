package com.example.myapplication.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class WorkoutNotification extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("WorkoutNotification", "Refreshed token: " + token);
    }

}
