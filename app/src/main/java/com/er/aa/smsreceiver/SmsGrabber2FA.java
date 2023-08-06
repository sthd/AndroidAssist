package com.er.aa.smsreceiver;


import android.util.Log;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsGrabber2FA {

    private static final String SENDER_CLALIT = "CLALIT";
    private static final String NUMBER_REGEX = "\\b\\d{6}\\b";

    public static String extractNumberIfClalit(String sender, String message) {
        String number = "1";
        if (sender != null && sender.contains(SENDER_CLALIT)) {
            Log.d("SmsGrabber2FA", "Sender: " + sender + ", Message: " + message);

            // Search for a 6-digit number in the message body
            Pattern pattern = Pattern.compile(NUMBER_REGEX);
            Matcher matcher = pattern.matcher(message);

            while (matcher.find()) {
                number = matcher.group().trim().replaceAll("\\s+", "");
                if (number.length() == 6) {
                    Log.d("SmsGrabber2FA", "Matched group: " + matcher.group());
                    Log.d("SmsGrabber2FA", "Extracted number: " + number);
                    return number;
                }
            }
        }
        Log.d("SmsGrabber2FA", "No number extracted or sender is not CLALIT");
        Log.d("SmsGrabber2FA", "out and the number: " + number);
        return null;
    }

}
