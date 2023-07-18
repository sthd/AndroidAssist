package com.er.aa.drivemode;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class DriveModeButton implements View.OnClickListener {
    private Button button;
    private Context context;
    private boolean isInDriveMode;

    public DriveModeButton(Button button, Context context) {
        this.button = button;
        this.context = context;
        this.isInDriveMode = false;
        this.button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        isInDriveMode = !isInDriveMode;

        if (isInDriveMode) {
            button.setText("Exit Drive Mode");
            Intent intent = new Intent(context, DriveModeActivity.class);
            context.startActivity(intent);
        } else {
            button.setText("Enter Drive Mode");
            Intent intent = new Intent(context, RegularModeActivity.class);
            context.startActivity(intent);
        }
    }
}
