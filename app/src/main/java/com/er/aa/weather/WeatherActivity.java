package com.er.aa.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.er.aa.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    private Button voiceCommandButton;

    private static final String WEATHERAPI_KEY = "0083b0fcdd9241aba23190957232706";
    //private static final String API_URL = "https://api.weatherapi.com/v1/current.json?key=" + WEATHERAPI_KEY + "&q=Haifa";
    private String API_URL = "https://api.weatherapi.com/v1/current.json?key=" + WEATHERAPI_KEY;
    private String city = "Haifa";

    private TextView textViewTemperature;
    private Button buttonRefresh;
    private Button buttonSecondActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        voiceCommandButton = findViewById(R.id.voice_command_button);
        voiceCommandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        textViewTemperature = findViewById(R.id.textViewTemperature);
        buttonRefresh = findViewById(R.id.buttonRefresh);
        buttonSecondActivity = findViewById(R.id.buttonSecondActivity);

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = textViewTemperature.getText().toString();
                new FetchWeatherTask().execute();
            }
        });

        new FetchWeatherTask().execute();
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




    //public void openSecondActivity(View view) {
    //    Intent intent = new Intent(this, MainActivity.class);
    //    startActivity(intent);
    //}

    public void setCity(){

    }


    private class FetchWeatherTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {

                String tmp_API_URL = API_URL + "&q=" + city;
                URL url = new URL(tmp_API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject currentObject = jsonObject.getJSONObject("current");
                    double temperature = currentObject.getDouble("temp_c");

                    buttonSecondActivity.setText(getString(R.string.temperature_only_format, temperature));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



    }
}