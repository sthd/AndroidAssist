package com.er.aa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.er.aa.drivemode.DriveModeActivity;
import com.er.aa.drivemode.RegularModeActivity;
import com.er.aa.voiceassistant.SpeechRecognitionService;
import com.er.aa.voiceassistant.VoiceCommandButton;


import android.Manifest;



public class MainActivity extends AppCompatActivity {
    private Button driveModeButton;
    private Button voiceCommandButton;
    private VoiceCommandButton voiceCommandButtonHandler;

    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        driveModeButton = findViewById(R.id.driveModeButton);
        driveModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDriveModeActivity();
            }
        });

        voiceCommandButton = findViewById(R.id.voiceCommandButton);
        voiceCommandButtonHandler = new VoiceCommandButton(voiceCommandButton, this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
        } else {
            startSpeechRecognitionService();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_RECORD_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startSpeechRecognitionService();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startSpeechRecognitionService() {
        Intent intent = new Intent(this, SpeechRecognitionService.class);
        startForegroundService(intent);
    }

    // Start DriveModeActivity
    private void startDriveModeActivity() {
        Intent intent = new Intent(this, DriveModeActivity.class);
        startActivity(intent);
    }

    // Start RegularModeActivity
    private void startRegularModeActivity() {
        Intent intent = new Intent(this, RegularModeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        voiceCommandButtonHandler.onActivityResult(requestCode, resultCode, data);
    }
}
