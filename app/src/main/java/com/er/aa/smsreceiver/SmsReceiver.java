package com.er.aa.smsreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;


public class SmsReceiver extends BroadcastReceiver {

    private static final String SMS_EXTRA_NAME = "pdus";
    private String number = "0";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();

        if (extras != null) {
            Object[] smsExtras = (Object[]) extras.get(SMS_EXTRA_NAME);

            if (smsExtras != null) {
                for (Object smsExtra : smsExtras) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) smsExtra);

                    String messageBody = smsMessage.getMessageBody();
                    String senderAddress = smsMessage.getOriginatingAddress();

                    SmsGrabber2FA smsGrabber2FA = new SmsGrabber2FA();
                    // Extract number if sender is "CLALIT"
                    if (number == "0") {
                        number = smsGrabber2FA.extractNumberIfClalit(senderAddress, messageBody);
                    }

                    // Start MainActivity with SMS data as extras
                    Intent mainIntent = new Intent(context, SmsActivity.class);

                    mainIntent.putExtra("sender", senderAddress);
                    mainIntent.putExtra("message", messageBody);
                    mainIntent.putExtra("number", number);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(mainIntent);
                }
            }
        }
    }

}
