package com.er.aa.voiceassistant;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceCommandButton implements View.OnClickListener {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    private Button button;
    private Activity activity;

    private SpeechRecognizer speechRecognizer;

    public VoiceCommandButton(Button button, Activity activity) {
        this.button = button;
        this.activity = activity;
        this.button.setOnClickListener(this);
        this.speechRecognizer = SpeechRecognizer.createSpeechRecognizer(activity);

    }

    @Override
    public void onClick(View v) {
        promptSpeechInput();
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak a command...");

        try {
            activity.startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "Speech recognition not supported on your device", Toast.LENGTH_SHORT).show();
        }
    }

    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == Activity.RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = result.get(0);

            processVoiceCommand(spokenText);
        }
    }

    private void processVoiceCommand(String command) {
        if (command.contains("open camera")) {
            openCamera();
        } else if (command.contains("open calculator")) {
            openCalculator();
        } else {
            Toast.makeText(activity, "Unknown command: " + command, Toast.LENGTH_SHORT).show();
        }
    }

    private void openCamera() {
        Toast.makeText(activity, "Opening camera...", Toast.LENGTH_SHORT).show();
        // Add your camera-opening logic here
    }

    private void openCalculator() {
        Toast.makeText(activity, "Opening calculator...", Toast.LENGTH_SHORT).show();
        // Add your calculator-opening logic here
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        handleActivityResult(requestCode, resultCode, data);
    }
}
