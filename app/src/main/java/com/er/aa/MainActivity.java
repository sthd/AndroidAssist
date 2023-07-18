package com.er.aa;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.er.aa.drivemode.DriveModeActivity;
import com.er.aa.drivemode.RegularModeActivity;
import com.er.aa.voiceassistant.VoiceCommandButton;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button driveModeButton;
    private Button voiceCommandButton;
    private VoiceCommandButton voiceCommandButtonHandler;

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
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        voiceCommandButtonHandler.handleActivityResult(requestCode, resultCode, data);
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
}
