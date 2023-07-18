package com.er.aa.drivemode;

import android.content.Context;
import android.widget.Toast;

public class DriveModeState implements StateInterface {
    @Override
    public void performAction(Context context) {
        Toast.makeText(context, "Drive Mode Action", Toast.LENGTH_SHORT).show();
    }
}
