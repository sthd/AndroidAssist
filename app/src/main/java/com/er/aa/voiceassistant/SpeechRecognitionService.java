package com.er.aa.voiceassistant;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Toast;

import java.util.ArrayList;

import android.app.Service;
import android.os.Handler;
import androidx.annotation.Nullable;

public class SpeechRecognitionService extends Service {

    private SpeechRecognizer speechRecognizer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Speech Service Created", Toast.LENGTH_LONG).show();
        //super.onCreate();
        startListening();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Speech Service Stopped", Toast.LENGTH_LONG).show();
        stopListening();
        super.onDestroy();
    }

    private void startListening() {
        Toast toast_try = Toast.makeText(getApplicationContext(), "try", Toast.LENGTH_SHORT);
        toast_try.show();
        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            Toast toast_start = Toast.makeText(getApplicationContext(), "started listening", Toast.LENGTH_SHORT);
            toast_start.show();
            speechRecognizer.setRecognitionListener(new RecognitionListener() {



                @Override
                public void onReadyForSpeech(Bundle params) {
                    // Speech recognition is ready
                }

                @Override
                public void onBeginningOfSpeech() {
                    // Speech started
                }

                @Override
                public void onEndOfSpeech() {
                    // Speech ended
                }

                @Override
                public void onError(int error) {
                    // Speech recognition error
                }

                @Override
                public void onResults(Bundle results) {
                    ArrayList<String> speechResults = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    if (speechResults != null && !speechResults.isEmpty()) {
                        String spokenText = speechResults.get(0);
                        if (spokenText.contains("darling")) {
                            Toast toast_darling = Toast.makeText(getApplicationContext(), "heard darling", Toast.LENGTH_SHORT);
                            toast_darling.show();
                            //showToast("Hi");
                        }
                    }
                }

                @Override
                public void onRmsChanged(float rmsdB) {
                    // RMS (Root Mean Square) changed
                }

                @Override
                public void onPartialResults(Bundle partialResults) {
                    // Partial speech recognition results
                }

                @Override
                public void onBufferReceived(byte[] buffer) {
                    // Audio buffer received
                }

                @Override
                public void onEvent(int eventType, Bundle params) {
                    // Speech recognition event
                }
            });
        }

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        if (speechRecognizer != null)
            speechRecognizer.startListening(intent);
    }

    private void stopListening() {
        if (speechRecognizer != null) {
            Toast toast_destroy = Toast.makeText(getApplicationContext(), "NOT listening", Toast.LENGTH_SHORT);
            toast_destroy.show();
            speechRecognizer.stopListening();
            speechRecognizer.destroy();
            speechRecognizer = null;
        }
    }

    private void showToast(final String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
