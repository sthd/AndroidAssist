package com.er.aa;
import com.er.aa.drivemode.DriveModeActivity;
import com.er.aa.drivemode.RegularModeActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.er.aa.drivemode.DriveModeButton;
import com.er.aa.voiceassitant.VoiceCommandButton;

public class MainActivity extends AppCompatActivity {
    private Button driveModeButton;
    private Button voiceCommandButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        driveModeButton = findViewById(R.id.driveModeButton);
        DriveModeButton driveModeButtonHandler = new DriveModeButton(driveModeButton, this);

        voiceCommandButton = findViewById(R.id.voiceCommandButton);
        VoiceCommandButton voiceCommandButtonHandler = new VoiceCommandButton(voiceCommandButton, this);

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