package com.er.aa.voiceassitant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.IntentFilter;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.er.aa.drivemode.DriveModeActivity;
import com.er.aa.drivemode.RegularModeActivity;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceCommandButton implements View.OnClickListener {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    private Button button;
    private Context context;
    //private boolean isInDriveMode;

    public VoiceCommandButton(Button button, Context context) {
        this.button = button;
        this.context = context;
        //this.isInDriveMode = false;
        this.button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        promptSpeechInput();

        //isInDriveMode = !isInDriveMode;
        //
        //if (isInDriveMode) {
        //    button.setText("Exit Drive Mode");
        //    Intent intent = new Intent(context, DriveModeActivity.class);
        //    context.startActivity(intent);
        //} else {
        //    button.setText("Enter Drive Mode");
        //    Intent intent = new Intent(context, RegularModeActivity.class);
        //    context.startActivity(intent);
        //}
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak a command...");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Speech recognition not supported on your device", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = result.get(0);

            processVoiceCommand(spokenText);
        }
    }

    private void processVoiceCommand(String command) {
        // You can define your own set of commands and actions here
        if (command.contains("open camera")) {
            openCamera();
        } else if (command.contains("open calculator")) {
            openCalculator();
        } else {
            Toast.makeText(getApplicationContext(), "Unknown command: " + command, Toast.LENGTH_SHORT).show();
        }
    }

    private void openCamera() {
        // Add your camera-opening logic here
        Toast.makeText(getApplicationContext(), "Opening camera...", Toast.LENGTH_SHORT).show();
    }

    private void openCalculator() {
        // Add your calculator-opening logic here
        Toast.makeText(getApplicationContext(), "Opening calculator...", Toast.LENGTH_SHORT).show();
    }


}
