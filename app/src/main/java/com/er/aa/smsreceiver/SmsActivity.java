package com.er.aa.smsreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.er.aa.R;

public class SmsActivity extends AppCompatActivity {


    private static final int SMS_PERMISSION_REQUEST_CODE = 1;
    private TextView smsTextView;
    private EditText editText;
    private Button smsCopyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        //smsTextView = findViewById(R.id.smsTextView);
        editText = findViewById(R.id.edit_text);
        smsCopyButton = findViewById(R.id.smsCopyButton);

        // Request SMS permissions if not already granted
        if (!hasSmsPermission()) {
            requestSmsPermission();
        } else {
            // Check if the activity was started by the SmsReceiver intent
            Intent intent = getIntent();
            if (intent != null) {
                String sender = intent.getStringExtra("sender");
                String message = intent.getStringExtra("message");
                String number = intent.getStringExtra("number");

                if (sender != null && message != null) {
                    updateSmsButton(number);
                }
            }
        }

        // Set click listener for the copy button
        smsCopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToCopy = smsCopyButton.getText().toString();
                copyToClipboard(textToCopy);





            }
        });



        // Inflate the custom layout for the toast
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, null);

        // Set the custom layout as the view for the toast
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        // Set the gravity to center horizontally and near the bottom
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, getResources().getDimensionPixelSize(R.dimen.toast_vertical_margin));

        // Set the text size of the toast message
        TextView toastText = layout.findViewById(R.id.toastText);
        toastText.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.toast_text_size));

        toast.show();

    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    private boolean hasSmsPermission() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestSmsPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, SMS_PERMISSION_REQUEST_CODE);
    }

    private void updateSmsButton(String message) {
        smsCopyButton.setText(message);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, handle the SMS logic
                // Check if the activity was started by the SmsReceiver intent
                Intent intent = getIntent();
                if (intent != null) {
                    String sender = intent.getStringExtra("sender");
                    String message = intent.getStringExtra("message");
                    String number = intent.getStringExtra("number");

                    if (sender != null && message != null) {
                        String smsText = "Sender: " + sender + "\nnumber: " + number;
                        updateSmsButton(number);
                    }
                }
            } else {
                // Permission denied, handle accordingly or exit the app
                // For simplicity, we'll just finish the activity
                finish();
            }
        }
    }
}
