package com.er.aa.accservices;

import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.List;

public class WhatsAppAccService extends AccessibilityService {
    //String [] convs = new String [] {"hey, how are you?", "it has been quite a while", "that is the 3rd message", "trying 4"};
    public static String [] convs = new String [3];
    public static String targetName = "default";
    static final String TAG = "ChatsAccService";
    static final String nameRefId = "com.whatsapp:id/conversation_contact_name";
    static final String sendButtonRefId = "com.whatsapp:id/send";
    static final String chatBoxRefId = "com.whatsapp:id/entry";
    public static int convIndex = 0;
    static int takeAction = 0;
    final int chatTimeLapseInMs = 5000;
    final int stdTimeLApse = 600;
    boolean sentLast = false;
    //TextView textView = findViewById(R.id.myTextView);

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "Started Service" );
        Toast.makeText(getApplicationContext(), "started the service", Toast.LENGTH_SHORT).show();
        if (event == null){
            return;
        }
        AccessibilityNodeInfo rootNode = event.getSource();

        if (rootNode == null){
            return;
        }
        try{

            //String viewId = rootNode.getViewIdResourceName();
            //CharSequence text = rootNode.getText();
            //CharSequence contentDescription = rootNode.getContentDescription();
            //Toast.makeText(getApplicationContext(), "View ID: " + viewId + "   Text:  " + text + "  Description: " + contentDescription, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), "Text: " + text, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), "Content Description: " + contentDescription, Toast.LENGTH_SHORT).show();

            //textView.setText(text);
            //textView = findViewById(R.id.msg3);
            //Toast tmpyToast = Toast.makeText(getApplicationContext(), "node text " + text + " and desc " + contentDescription, Toast.LENGTH_SHORT);
            //tmpyToast.show();

            AccessibilityNodeInfo tmpNode = getNode(rootNode, "com.whatsapp:id/conversation_contact_name");
            Toast tmpToast = Toast.makeText(getApplicationContext(), "node text " + tmpNode.getText(), Toast.LENGTH_SHORT);
            tmpToast.show();
            tmpToast = Toast.makeText(getApplicationContext(), "node string " + tmpNode.toString(), Toast.LENGTH_SHORT);
            tmpToast.show();

            //List<AccessibilityNodeInfo.AccessibilityAction> theActionList = rootNode.getActionList();
            //Toast someAction = Toast.makeText(getApplicationContext(), "A0: " + theActionList.get(0).toString(), Toast.LENGTH_SHORT);
            //someAction.show();
            //someAction = Toast.makeText(getApplicationContext(), "A1: " + theActionList.get(1).toString(), Toast.LENGTH_SHORT);
            //someAction.show();
            //someAction = Toast.makeText(getApplicationContext(), "viewIDres " + rootNode.getViewIdResourceName(), Toast.LENGTH_SHORT);
            //someAction.show();
            //someAction = Toast.makeText(getApplicationContext(), "A3: " + rootNode., Toast.LENGTH_SHORT);
            //someAction.show();
            String name = getName(rootNode);
            if (name == null){
                return;
            }
            Toast currName = Toast.makeText(getApplicationContext(), "The current Name is: " + name, Toast.LENGTH_SHORT);
            currName.show();
            if (!name.equalsIgnoreCase(targetName)){
                Log.d(TAG, "Not target");
                return;
            }
            else{
                AccessibilityNodeInfo textBox = getNode(rootNode, chatBoxRefId);
                Bundle arguments = new Bundle();
                if (convIndex == convs.length && !sentLast){
                    //arguments.putString(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "<3");
                    sentLast = true;
                    Toast ta = Toast.makeText(getApplicationContext(), "That was the last message to: " + targetName, Toast.LENGTH_SHORT);
                    ta.show();
                }
                else{
                    arguments.putString(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, convs[convIndex]);
                    convIndex++;
                }
                textBox.performAction(AccessibilityNodeInfoCompat.ACTION_SET_TEXT, arguments);
                AccessibilityNodeInfo sendButton = getNode(rootNode, sendButtonRefId);
                sendButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                Thread.sleep(2500);
            }
        }
        catch (Exception e){
            Log.d(TAG, "Some exception occured!" + e.getMessage());
        }


    }

    @Nullable
    public static String getName(@NonNull AccessibilityNodeInfo rootNodeInfo){
        List<AccessibilityNodeInfo> urlNodeInfo = rootNodeInfo.findAccessibilityNodeInfosByViewId(nameRefId);
        //AccessibilityNodeInfo newNod = rootNodeInfo.
        if (urlNodeInfo != null && !urlNodeInfo.isEmpty()) {
            AccessibilityNodeInfo urlNode = urlNodeInfo.get(0);
            CharSequence charArray = urlNode.getText();
            if (charArray != null && charArray.length() > 0) {
                Log.d(TAG, "Name found: " + charArray.toString());
                return charArray.toString();
            }
        }
        Log.d(TAG, "Name NOT found");
        return null;
    }

    public AccessibilityNodeInfo getNode(@NonNull AccessibilityNodeInfo rootNodeInfo, @NonNull String refId){
        AccessibilityNodeInfo textBoxNode = null;
        List<AccessibilityNodeInfo> urlNodeInfo = rootNodeInfo.findAccessibilityNodeInfosByViewId(refId);
        if (urlNodeInfo != null && !urlNodeInfo.isEmpty()) {
            textBoxNode = urlNodeInfo.get(0);
            Log.d(TAG, "Found " + refId);
            return textBoxNode;
        }
        Log.d(TAG, "Not found " + refId);
        return textBoxNode;
    }

    public AccessibilityNodeInfo getTheNode(@NonNull AccessibilityNodeInfo rootNodeInfo, @NonNull String refId){
        AccessibilityNodeInfo tmpNode = null;
        List<AccessibilityNodeInfo> urlNodeInfo = rootNodeInfo.findAccessibilityNodeInfosByViewId(refId);
        if (urlNodeInfo != null && !urlNodeInfo.isEmpty()) {
            tmpNode = urlNodeInfo.get(0);
            Log.d(TAG, "Found " + refId);

            return tmpNode;
        }
        Log.d(TAG, "Not found " + refId);
        Toast tmpToast = Toast.makeText(getApplicationContext(), "no node", Toast.LENGTH_SHORT);
        tmpToast.show();
        return tmpNode;
    }



    @Override
    public void onInterrupt() {

    }


}



//Bundle marguments = new Bundle();
//marguments.putString(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "this is argument");
//List<AccessibilityNodeInfo> urlNodesInfo = rootNode.findAccessibilityNodeInfosByViewId("com.whatsapp:id/conversation_contact_name");
//if (urlNodesInfo != null && !urlNodesInfo.isEmpty()) {
//    AccessibilityNodeInfo urlNode = urlNodesInfo.get(0);
//    CharSequence charArray = urlNode.getText();
//    if (charArray != null && charArray.length() > 0) {
//        Log.d(TAG, "Name found: " + charArray.toString());
//        Toast mcharArray = Toast.makeText(getApplicationContext(), "The beg: " + charArray.toString(), Toast.LENGTH_SHORT);
//        mcharArray.show();
//    }
//}


//AccessibilityNodeInfo mtextBox = getNode(rootNode, chatBoxRefId);
//AccessibilityNodeInfo msendButton = getNode(rootNode, sendButtonRefId);
//mtextBox.performAction(AccessibilityNodeInfoCompat.ACTION_SET_TEXT, marguments);
//msendButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);