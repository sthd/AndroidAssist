package com.er.aa.whatsappscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.er.aa.accservices.WhatsAppAccService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;

import com.er.aa.R;

public class WhatsappSchedulerActivity extends AppCompatActivity {

    Button mButton;
    EditText mTextBox;
    FloatingActionButton help;

    EditText msg1;
    EditText msg2;
    EditText msg3;

    private static final int REQUEST_PERMISSION = 1;
    private static final int REQUEST_CONTACT = 2;
    private ListView contactListView;
    private ArrayList<String> contacts;
    private ArrayAdapter<String> adapter;
    private String selectedContactNumber;

    @SuppressLint("MissingInflatedId") //added by ide
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp_scheduler);

        String targetPackageName = "com.whatsapp";

        contactListView = findViewById(R.id.contactListView);
        contacts = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        contactListView.setAdapter(adapter);

        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedContactNumber = contacts.get(position);
                Toast.makeText(getApplicationContext(), "Selected contact: " + selectedContactNumber, Toast.LENGTH_SHORT).show();
            }
        });



        mButton = findViewById(R.id.button);
        mTextBox = findViewById(R.id.name);
        help = findViewById(R.id.helpButton);

        msg1 = findViewById(R.id.msg1);
        msg2 = findViewById(R.id.msg2);
        msg3 = findViewById(R.id.msg3);
        mButton.setOnClickListener((view) -> {
            setName(mTextBox.getText().toString());
            setMessage(msg1.getText().toString(), msg2.getText().toString(), msg3.getText().toString());
            Toast toast = Toast.makeText(getApplicationContext(), "Name is set! Go to Whatsapp", Toast.LENGTH_SHORT);
            toast.show();
            mTextBox.setText("");
            // Check if the required permissions are granted

            //browseContacts();
            launchWhatsApp();
            //if (hasPermissions()) {
            //    launchApp(targetPackageName);
            //} else {
            //    requestPermissions();
            //}


        });

        help.setOnClickListener((v) -> {
            Toast t = Toast.makeText(getApplicationContext(), "If its not working, go to....", Toast.LENGTH_SHORT);
            t.show();
        });


    }


    private void setName(@NonNull String newName){
        WhatsAppAccService.targetName = newName;
        //Toast.makeText(getApplicationContext(), newName.toString(), Toast.LENGTH_SHORT).show();
        WhatsAppAccService.convIndex = 0;
    }

    private void setMessage(@NonNull String s1, @NonNull String s2, @NonNull String s3){
        WhatsAppAccService.convs[0] = s1;
        WhatsAppAccService.convs[1] = s2;
        WhatsAppAccService.convs[2] = s3;
        //Toast.makeText(getApplicationContext(), WhatsAppAccService.convs[0].toString() + WhatsAppAccService.convs[1].toString() , Toast.LENGTH_SHORT).show();
    }


    private void browseContacts() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, ask for permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_PERMISSION);
        } else {
            // Permission already granted, browse contacts
            getContacts();
        }
    }

    private void getContacts() {
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        );

        if (cursor != null) {
            HashSet<String> uniqueContacts = new HashSet<>();

            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Toast.makeText(getApplicationContext(), name.toString(), Toast.LENGTH_SHORT).show();
                //String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                //String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                // Generate a unique identifier for each contact
                String contactIdentifier = name + number;

                // Check if the contact already exists
                if (!uniqueContacts.contains(contactIdentifier)) {
                    contacts.add(name + ": " + number);
                    uniqueContacts.add(contactIdentifier);
                }
            }

            cursor.close();
            adapter.notifyDataSetChanged();
        }
    }


    private boolean hasPermissions() {
        // Check if the required permissions are granted
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    //private void requestPermissions() {
    //    // Request the required permissions
    //    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
    //}

    private void launchWhatsApp() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, ask for permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, REQUEST_PERMISSION);
        } else {
            // Permission already granted, launch WhatsApp
            openWhatsApp();
        }
    }

    private void openWhatsApp() {
        // Launch WhatsApp
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=0546671574"));  // Replace with desired phone number
        startActivity(intent);
        finish();
    }

    public void selectContact(View view) {
        if (selectedContactNumber != null) {
            // Perform any desired action with the selected contact number
            Toast.makeText(this, "Selected contact: " + selectedContactNumber, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No contact selected.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, launch WhatsApp
                openWhatsApp();
            } else {
                // Permission not granted, show a toast message and open app settings
                Toast.makeText(this, "Permission denied. Please grant permission to launch WhatsApp.", Toast.LENGTH_SHORT).show();
                openAppSettings();
            }
        }
    }

    private void openAppSettings() {
        // Open app settings
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
        finish();
    }
    //@Override
    //public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    //    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    //    if (requestCode == PERMISSION_REQUEST_CODE) {
    //        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
    //            // Permission granted, launch the app
    //            String targetPackageName = "com.example.targetapp";
    //            launchApp(targetPackageName);
    //        } else {
    //            // Permission denied, show a toast and open the app settings to enable permissions manually
    //            Toast.makeText(this, "Permissions denied. Please enable them in Settings.", Toast.LENGTH_LONG).show();
    //            openAppSettings();
    //        }
    //    }
    //}

    //private void openAppSettings() {
    //    // Open the app settings so that the user can enable the required permissions manually
    //    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    //    intent.setData(Uri.parse("package:" + getPackageName()));
    //    startActivity(intent);
    //}

    private void launchApp(String packageName) {
        // Launch the target app
        PackageManager packageManager = getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);

        if (intent != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "App not found.", Toast.LENGTH_SHORT).show();
        }

        // Finish this activity after launching the app
        finish();
    }

}

