package com.er.aa.drivemode;

public class AppModeContext {
    private StateInterface currentMode;

    public AppModeContext() {
        currentMode = new RegularModeState();
    }

    public StateInterface getCurrentMode() {
        return currentMode;
    }

    public void toggleMode() {
        if (currentMode instanceof RegularModeState) {
            currentMode = new DriveModeState();
        } else if (currentMode instanceof DriveModeState) {
            currentMode = new RegularModeState();
        }
    }
}
